package com.api.wiveService.WineService.util;

import net.minidev.json.JSONObject;

import java.util.HashMap;

public class MsgCodWineApi {
    private HashMap<Integer, String> mapaCodigosErro = new HashMap<>();
    private String msgAuxiliar;

    public MsgCodWineApi() {
        this.msgAuxiliar = "";
        preencherCodigosErro();
    }

    public MsgCodWineApi(String msgAuxiliar) {
        this.msgAuxiliar = msgAuxiliar;
        preencherCodigosErro();
    }

    public String getCodigoErro(Integer codErro) {
        if (mapaCodigosErro.containsKey(codErro)) {
            return this.mapaCodigosErro.get(codErro);
        }
        return "Erro não documentado!";
    }


    private void preencherCodigosErro() {
        mapaCodigosErro.put(1, "Erro - Falha - Usuário já cadastrado para o email informado");
        mapaCodigosErro.put(2, "Erro - Falha - Data de nascimento inválida. Use o formato dd/MM/yyyy.");
        mapaCodigosErro.put(3, "Erro - Falha - Data de nascimento não pode ser no futuro.");
    }
}
