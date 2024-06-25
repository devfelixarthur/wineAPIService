package com.api.wiveService.WineService.service.comments;

import com.api.wiveService.WineService.domain.comments.bean.Comments;
import com.api.wiveService.WineService.domain.comments.dto.*;
import com.api.wiveService.WineService.domain.user.bean.User;
import com.api.wiveService.WineService.domain.wine.bean.Wine;
import com.api.wiveService.WineService.exceptions.WineException;
import com.api.wiveService.WineService.repository.CommentRepository;
import com.api.wiveService.WineService.repository.UserRepository;
import com.api.wiveService.WineService.repository.WineRepository;
import com.api.wiveService.WineService.service.wine.WineService;
import com.api.wiveService.WineService.util.MsgCodWineApi;
import com.api.wiveService.WineService.util.ResponsePadraoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WineRepository wineRepository;

    private static final Logger logger = LoggerFactory.getLogger(WineService.class);

    public ResponsePadraoDTO RegisterComment(CreateCommentDTO form) {
        Optional<User> userExiste = Optional.ofNullable(userRepository.findById(form.idUser()));
        Optional<Wine> wineExiste = wineRepository.buscarWineById(form.idWine());

        if (userExiste.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(4), HttpStatus.BAD_REQUEST);
        }
        if (wineExiste.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(11), HttpStatus.BAD_REQUEST);
        }

        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        LocalDateTime now = LocalDateTime.now(zoneId);

        Comments comments = new Comments();
        comments.setUser(userExiste.get());
        comments.setWine(wineExiste.get());
        comments.setStatus("Ativo");
        comments.setNomeUsuario(userExiste.get().getNome());
        comments.setDescricao(form.message());
        comments.setDataCadastro(now);
        comments.setDataUpdate(now);

        commentRepository.save(comments);

        return ResponsePadraoDTO.sucesso("Commentário cadastrado com sucesso.");

    }

    public ResponsePadraoDTO EditarComment(EditCommentDTO form) {
        Optional<Comments> commentsExiste = commentRepository.findById(form.getIdComment());

        if (commentsExiste.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(12), HttpStatus.BAD_REQUEST);
        }

        Comments comments = commentsExiste.get();

        comments.setDescricao(form.getMessage());

        commentRepository.save(comments);

        return ResponsePadraoDTO.sucesso("Comentário editado com sucesso.");
    }

    public ResponsePadraoDTO AlterarStatus(AlterarStatusDTO form) {
        Optional<Comments> comments = commentRepository.findById(form.getId());

        if (comments.isEmpty()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(12), HttpStatus.BAD_REQUEST);
        }

        Comments commentsAlter = comments.get();
        String novoStatus = form.getStatus().equals(0) ? "Inativo" : "Ativo";

        if (commentsAlter.getStatus().equals(novoStatus)) {
            throw new WineException("Comments já encontra-se " + novoStatus.toLowerCase(), HttpStatus.BAD_REQUEST);
        }

        commentsAlter.setStatus(novoStatus);
        commentRepository.save(commentsAlter);
        return ResponsePadraoDTO.sucesso("Status alterado com sucesso.");
    }

    public ResponseCommentDTO getAllComments(Integer itemInicio, Integer itemFim) {
        if (itemInicio == null || itemInicio < 1 || itemFim == null || itemFim < 1 || itemInicio > itemFim) {
            throw new WineException("Parâmetros de paginação inválidos.", HttpStatus.BAD_REQUEST);
        }

        int page = (itemInicio - 1) / itemFim;
        int size = itemFim;

        Page<Comments> commentsPage = commentRepository.findByStatus("Ativo", PageRequest.of(page, size));
        List<CommentsDTO> commentsDtos = commentsPage.getContent().stream()
                .map(comment -> new CommentsDTO(comment.getId(), comment.getUser().getId(), comment.getNomeUsuario(), comment.getDescricao(), comment.getDataCadastro()))
                .collect(Collectors.toList());

        return new ResponseCommentDTO(commentsPage.getTotalElements(), commentsDtos);
    }

    public ResponseCommentDTO getCommentsByWine(Integer itemInicio, Integer itemFim, Long wineId) {
        if (wineId < 1) {
            throw new WineException("O wineID deve ser um número inteiro e positivo.", HttpStatus.BAD_REQUEST);
        }

        if (itemInicio == null || itemInicio < 1 || itemFim == null || itemFim < 1 || itemInicio > itemFim) {
            throw new WineException("Parâmetros de paginação inválidos.", HttpStatus.BAD_REQUEST);
        }

        Optional<Wine> wine = wineRepository.findById(wineId);
        if (!wine.isPresent()) {
            throw new WineException(new MsgCodWineApi().getCodigoErro(11), HttpStatus.NOT_FOUND);
        }

        PageRequest pageRequest = PageRequest.of(itemInicio - 1, itemFim);

        Page<Comments> commentsPage = commentRepository.findByStatusAndWineId(wineId, "Ativo", pageRequest);
        List<CommentsDTO> commentsDtos = commentsPage.getContent().stream()
                .map(comment -> new CommentsDTO(comment.getId(), comment.getUser().getId(), comment.getNomeUsuario(), comment.getDescricao(), comment.getDataCadastro()))
                .collect(Collectors.toList());

        return new ResponseCommentDTO(commentsPage.getTotalElements(), commentsDtos);
    }
}
