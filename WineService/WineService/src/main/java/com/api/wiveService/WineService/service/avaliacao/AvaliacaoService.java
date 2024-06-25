package com.api.wiveService.WineService.service.avaliacao;

import com.api.wiveService.WineService.domain.avaliacao.bean.Avaliacao;
import com.api.wiveService.WineService.domain.avaliacao.dto.AvaliacaoMediaDTO;
import com.api.wiveService.WineService.domain.avaliacao.dto.CreateAvaliacaoDTO;
import com.api.wiveService.WineService.domain.avaliacao.dto.UpdateAvaliacaoDTO;
import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.wine.bean.Wine;
import com.api.wiveService.WineService.domain.wine.dto.ResponseWineDTO;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.AvaliacaoRepository;
import com.api.wiveService.WineService.repository.UserRepository;
import com.api.wiveService.WineService.repository.WineRepository;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService {
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WineRepository wineRepository;


    @Transactional
    public ResponsePadraoDTO createAvaliacao(CreateAvaliacaoDTO dto) {
        Optional<User> userExiste = Optional.ofNullable(userRepository.findById(dto.idUsuario()));
        Optional<Wine> wineExiste = wineRepository.buscarWineById(dto.idWine());
        Optional<Avaliacao> avaliacaoExiste = avaliacaoRepository.finByIdUserAndIdWine(dto.idUsuario(), dto.idWine());

        if (!avaliacaoExiste.isEmpty()) {
                throw new WineException("Já existe uma avaliação para o IdUser e IdWine informado.", HttpStatus.BAD_REQUEST);
        }

        if (userExiste.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(4), HttpStatus.BAD_REQUEST);
        }

        if (wineExiste.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(11), HttpStatus.BAD_REQUEST);
        }

        User user = (User) userExiste.get();
        Wine wine = (Wine) wineExiste.get();

        Avaliacao avaliacao = new Avaliacao();
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime now = LocalDateTime.now(zoneId);

        avaliacao.setAvaliacao(dto.avaliacao());
        avaliacao.setUser(user);
        avaliacao.setWine(wine);
        avaliacao.setStatus("Ativo");
        avaliacao.setDataCadastro(now);
        avaliacao.setDataUpdate(now);

        avaliacaoRepository.save(avaliacao);
        return ResponsePadraoDTO.sucesso("Avaliação Cadastrada com Sucesso");
    }


    @Transactional
    public ResponsePadraoDTO updateAvaliacao(UpdateAvaliacaoDTO dto) {
        Optional<Avaliacao> avaliacaoExiste = avaliacaoRepository.findById(dto.getIdAvaliacao());

        if (!avaliacaoExiste.isPresent()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(13), HttpStatus.BAD_REQUEST);
        }

        Avaliacao avaliacao = avaliacaoExiste.get();

        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime now = LocalDateTime.now(zoneId);

        avaliacao.setAvaliacao(dto.getAvaliacao());
        avaliacao.setDataUpdate(now);

        avaliacaoRepository.save(avaliacao);
        return ResponsePadraoDTO.sucesso("Avaliação alterada com Sucesso");
    }

    public List<Avaliacao> getAllAvaliacoes() {
        return avaliacaoRepository.findAll();
    }

    public double calculateMedia(Long wineId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByWineId(wineId);
        return avaliacoes.stream().mapToInt(Avaliacao::getAvaliacao).average().orElse(0);
    }

    public AvaliacaoMediaDTO getAvalicaoWine(Long wineId) {
        Optional<Wine> wineExiste = wineRepository.buscarWineById(wineId);
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByWineId(wineId);

        if (!wineExiste.isPresent()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(11), HttpStatus.BAD_REQUEST);
        }

        AvaliacaoMediaDTO avaliacaoMedia = new AvaliacaoMediaDTO();
        Wine wine = wineExiste.get();

        double somaDasAvaliacoes = avaliacoes.stream()
                .mapToInt(Avaliacao::getAvaliacao)
                .sum();
        double mediaAvaliacoes = avaliacoes.isEmpty() ? 0 : somaDasAvaliacoes / avaliacoes.size();

        double mediaFormatada = Math.round(mediaAvaliacoes * 10.0) / 10.0;

        avaliacaoMedia.setNomeVinho(wine.getNome());
        avaliacaoMedia.setIdVinho(wine.getId());
        avaliacaoMedia.setAvaliacaoMedia(mediaFormatada);

        return avaliacaoMedia;
    }



    public List<AvaliacaoMediaDTO> getAllAvaliacoesMedia() {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findAll();

        Map<Long, List<Avaliacao>> avaliacoesPorVinho = avaliacoes.stream()
                .collect(Collectors.groupingBy(a -> a.getWine().getId()));

        List<AvaliacaoMediaDTO> avaliacoesMedia = avaliacoesPorVinho.entrySet().stream()
                .map(entry -> {
                    Long wineId = entry.getKey();
                    List<Avaliacao> avaliacaoList = entry.getValue();
                    double somaDasAvaliacoes = avaliacaoList.stream()
                            .mapToInt(Avaliacao::getAvaliacao)
                            .sum();
                    double mediaAvaliacoes = avaliacaoList.isEmpty() ? 0 : somaDasAvaliacoes / avaliacaoList.size();
                    double mediaFormatada = Math.round(mediaAvaliacoes * 10.0) / 10.0;
                    Wine wine = wineRepository.findById(wineId).orElseThrow(() -> new WineException(new MsgCodWineApi().getCodigoErro(11), HttpStatus.BAD_REQUEST));
                    return new AvaliacaoMediaDTO(wineId, wine.getNome(), mediaFormatada);
                })
                .collect(Collectors.toList());

        return avaliacoesMedia;
    }


}
