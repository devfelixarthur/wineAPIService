package com.api.wiveService.WineService.service.wine;

import com.api.wiveService.WineService.domain.avaliacao.bean.Avaliacao;
import com.api.wiveService.WineService.domain.comments.bean.Comments;
import com.api.wiveService.WineService.domain.comments.dto.CommentsDTO;
import com.api.wiveService.WineService.domain.wine.bean.Wine;
import com.api.wiveService.WineService.domain.wine.dto.*;
import com.api.wiveService.WineService.exceptions.WineApiException;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.AvaliacaoRepository;
import com.api.wiveService.WineService.repository.CommentRepository;
import com.api.wiveService.WineService.repository.WineRepository;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WineService {

    @Autowired
    private WineRepository wineRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    private static final Logger logger = LoggerFactory.getLogger(WineService.class);

    @Value("${UPLOAD_IMAGE_URL}")
    private String apiUrl;

    @Transactional
    public ResponsePadraoDTO cadastrarWine(CadastrarWineDTO form) {
        Integer currentYear = Year.now().getValue();

        Optional<Wine> wineExiste = wineRepository.buscarWine(form.nome(), form.safra(), form.pais(), form.uva());

        if (wineExiste.isPresent()) {
            throw new WineApiException("Vinho já cadastrado", HttpStatus.BAD_REQUEST);
        }

        if (form.safra() > currentYear) {
            throw new WineApiException("A safra do vinho não pode ser maior que o ano corrente.", HttpStatus.BAD_REQUEST);
        }

        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime now = LocalDateTime.now(zoneId);

        Wine wine = new Wine();
        wine.setNome(form.nome());
        wine.setPais(String.valueOf(Pais.fromName(form.pais())));
        wine.setSafra(form.safra());
        wine.setAdega(form.adega());
        wine.setUva(form.uva());
        wine.setStatus("Ativo");
        wine.setDataCadastro(now);
        wine.setDataUpdate(now);

        String imageUrl = uploadImageToImgBB(form.imagem());
        wine.setImagem(imageUrl);

        wineRepository.save(wine);

        logger.info("Cadastrando Wine com nome: {}", wine.getNome());
        return ResponsePadraoDTO.sucesso("Wine cadastrado com sucesso");
    }

    private String uploadImageToImgBB(String base64Image) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", base64Image);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);
            Map<String, Object> responseData = (Map<String, Object>) response.getBody().get("data");
            return (String) responseData.get("url");
        } catch (Exception e) {
            logger.error("Erro ao fazer upload da imagem para o ImgBB", e);
            throw new WineApiException("Erro ao fazer upload da imagem para o ImgBB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponsePadraoDTO alterarWine(AlterarWineDTO form) {
        Optional<Wine> wineExiste = wineRepository.findById(form.id());

        if (!wineExiste.isPresent()) {
            throw new WineApiException("Vinho não encontrado", HttpStatus.NOT_FOUND);
        }

        Wine wine = wineExiste.get();

        wine.setNome(form.nome() != null ? form.nome() : wine.getNome());
        wine.setPais(form.pais() != null ? String.valueOf(Pais.fromName(form.pais())) : wine.getPais());
        wine.setAdega(form.adega() != null ? form.adega() : wine.getAdega());
        wine.setUva(form.uva() != null ? form.uva() : wine.getUva());
        wine.setSafra(form.safra() != null ? form.safra() : wine.getSafra());
        wine.setDataUpdate(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        if (form.imagem() != null) {
            String imageUrl = uploadImageToImgBB(form.imagem());
            wine.setImagem(imageUrl);
        }

        wineRepository.save(wine);

        logger.info("Alterando Wine com nome: {}", wine.getNome());
        return ResponsePadraoDTO.sucesso("Wine alterado com sucesso");

    }

    @Transactional
    public ResponseWineDTO getAllWines(Integer itemInicio, Integer itemFim) {
        if ((itemInicio == null) || (itemFim == null) || (itemInicio < 1) || (itemFim < 1) || (itemInicio > itemFim)) {
            throw new WineException("Parâmetros de Paginação invalidos.", HttpStatus.BAD_REQUEST);
        }

        int page = (itemInicio - 1) / itemFim;
        int size = itemFim;

        Page<Wine> winePage = wineRepository.findAll(PageRequest.of(page, size));
        List<WineDto> wineDtos = winePage.getContent().stream()
                .map(this::convertToWineDto)
                .collect(Collectors.toList());

        wineDtos.forEach(wineDto -> {
            List<Comments> comments = commentRepository.findByWineIdAndStatus(wineDto.getId());
            List<CommentsDTO> commentsDtos = comments.stream()
                    .map(comment -> new CommentsDTO(comment.getId(), comment.getUser().getId(), comment.getNomeUsuario(), comment.getDescricao(), comment.getDataCadastro()))
                    .collect(Collectors.toList());
            wineDto.setComments(commentsDtos);
        });


        return new ResponseWineDTO(winePage.getTotalElements(), wineDtos);
    }

    private double calculateMediaAvaliacao(Long wineId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByWineId(wineId);
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }
        double sum = avaliacoes.stream().mapToDouble(Avaliacao::getAvaliacao).sum();
        return Math.round((sum / avaliacoes.size()) * 10.0) / 10.0;
    }

    private WineDto convertToWineDto(Wine wine) {
        double mediaAvaliacao = calculateMediaAvaliacao(wine.getId());
        Integer qtnAvalicao = avaliacaoRepository.countAvaliacaoByWineId(wine.getId());
        return new WineDto(
                wine.getId(),
                wine.getNome(),
                wine.getDescricao(),
                wine.getUva(),
                wine.getPais(),
                wine.getAdega(),
                wine.getSafra(),
                wine.getImagem(),
                mediaAvaliacao,
                qtnAvalicao,
                wine.getStatus(),
                wine.getDataCadastro(),
                new ArrayList<>()
        );
    }

    @Transactional
    public ResponsePadraoDTO alterarStatus(AlterarStatusWineDTO form) {

        Optional<Wine> wineOptional = wineRepository.findById(form.getId());

        if (wineOptional.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(11), HttpStatus.BAD_REQUEST);
        }

        Wine wine = wineOptional.get();
        String novoStatus = form.getStatus().equals(0) ? "Inativo" : "Ativo";

        if (wine.getStatus().equals(novoStatus)) {
            throw new WineException("Vinho já encontra-se " + novoStatus.toLowerCase(), HttpStatus.BAD_REQUEST);
        }

        wine.setStatus(novoStatus);
        wineRepository.save(wine);
        logger.info("----------------------INICIO LOGGER INFO:----------------------");
        logger.info("Status do Vinho " + wine.getNome() + " alterado para " + novoStatus);
        logger.info("----------------------FIM LOGGER INFO:----------------------");
        return ResponsePadraoDTO.sucesso("Status do Vinho " + wine.getNome() + " alterado.");
    }

    @Transactional
    public ResponseCountryDTO getAllCountries() {
        List<ResponseCountryDTO.CountryDTO> countries = Arrays.stream(Pais.values())
                .map(pais -> new ResponseCountryDTO.CountryDTO(pais.getThreeDigitsCode(), pais.getName()))
                .collect(Collectors.toList());

        return new ResponseCountryDTO((long) countries.size(), countries);
    }

    @Transactional
    public ResponseAdegaDTO getAllAdegas() {
        List<String> adegas = wineRepository.getAllAdegas();

        ResponseAdegaDTO adegasDTO = new ResponseAdegaDTO();
        adegasDTO.setTotalAdegas((long) adegas.size());
        adegasDTO.setAdegas(adegas);

        return adegasDTO;
    }

    @Transactional
    public ResponseWineDTO getWinesByFields(int itemInicio, int itemFim, String wineName, String pais, String uva, String adega) {
        if (wineName != null && wineName.length() < 3) {
            throw new WineException("O Parâmetro wineName deve ter pelo menos 3 caracteres.", HttpStatus.BAD_REQUEST);
        }

        if (uva != null && uva.length() < 3) {
            throw new WineException("O Parâmetro uva deve ter pelo menos 3 caracteres.", HttpStatus.BAD_REQUEST);
        }

        if (adega != null && adega.length() < 3) {
            throw new WineException("O Parâmetro adega deve ter pelo menos 3 caracteres.", HttpStatus.BAD_REQUEST);
        }

        if ((itemInicio < 1) || (itemFim < 1) || (itemInicio > itemFim)) {
            throw new WineException("Parâmetros de Paginação inválidos.", HttpStatus.BAD_REQUEST);
        }

        String paisCode = null;
        if (pais != null) {
            try {
                Pais paisEnum = Pais.fromName(pais);
                paisCode = paisEnum.getThreeDigitsCode();
            } catch (WineApiException e) {
                throw new WineException("País inválido.", HttpStatus.BAD_REQUEST);
            }
        }

        int page = (itemInicio - 1) / itemFim;
        int size = itemFim;

        Page<Wine> winePage = wineRepository.findWinesByFields(wineName, paisCode, uva, adega, PageRequest.of(page, size));
        List<WineDto> wineDtos = winePage.getContent().stream()
                .map(this::convertToWineDto)
                .collect(Collectors.toList());

        wineDtos.forEach(wineDto -> {
            List<Comments> comments = commentRepository.findByWineIdAndStatus(wineDto.getId());
            List<CommentsDTO> commentsDtos = comments.stream()
                    .map(comment -> new CommentsDTO(comment.getId(), comment.getUser().getId(), comment.getNomeUsuario(), comment.getDescricao(), comment.getDataCadastro()))
                    .collect(Collectors.toList());
            wineDto.setComments(commentsDtos);
        });

        return new ResponseWineDTO(winePage.getTotalElements(), wineDtos);
    }

    @Transactional
    public ResponseUvasDTO getAllUvas() {
        List<String> uvas = wineRepository.getAllUvas();

        ResponseUvasDTO uvasDTO = new ResponseUvasDTO();
        uvasDTO.setTotaluvas((long) uvas.size());
        uvasDTO.setUvas(uvas);

        return uvasDTO;
    }
}
