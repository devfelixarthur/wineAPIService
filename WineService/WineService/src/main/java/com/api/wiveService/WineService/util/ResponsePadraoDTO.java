package com.api.wiveService.WineService.util;

import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;

@Getter
@Setter
public class ResponsePadraoDTO {
    private String titulo;
    private String mensagem;

    public ResponsePadraoDTO(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("Titulo", titulo);
        json.put("Mensagem", mensagem);
        return json.toString();
    }

    public static ResponsePadraoDTO falha(String mensagem) {
        return new ResponsePadraoDTO("Falha.", mensagem);
    }

    public static ResponsePadraoDTO sucesso(String mensagem) {
        return new ResponsePadraoDTO("Sucesso", mensagem);
    }
}
