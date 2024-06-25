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
        mapaCodigosErro.put(1, "Erro - Falha - O email informado já está sendo usado por outro usuário.");
        mapaCodigosErro.put(2, "Erro - Falha - Data de nascimento inválida. Use o formato dd/MM/yyyy.");
        mapaCodigosErro.put(3, "Erro - Falha - Data de nascimento não pode ser no futuro.");
        mapaCodigosErro.put(4, "Erro - Falha - Usuário não encontrado para o Id informado.");
        mapaCodigosErro.put(5, "Erro - Falha - O Email informado já está em uso por outro usuário.");
        mapaCodigosErro.put(6, "Erro - Falha - Somente Usuários Administradores podem deletar dados.");
        mapaCodigosErro.put(7, "Erro - Falha - O itemInicio dever ser maior que 0.");
        mapaCodigosErro.put(8, "Erro - Falha - O itemFim dever ser maior que 0.");
        mapaCodigosErro.put(9, "Erro - Falha - O itemInicio não pode ser maior que o itemFim.");
        mapaCodigosErro.put(10, "Erro - Falha - O itemInicio e itemFim devem ser informados.");
        mapaCodigosErro.put(11, "Erro - Falha - Vinho não encontrado para o Id informado.");
        mapaCodigosErro.put(12, "Erro - Falha - Comment não encontrado para o Id informado.");
        mapaCodigosErro.put(13, "Erro - Falha - Avaliação não encontrado para o Id informado.");

    }
}
