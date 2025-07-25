package java.model; 

import java.util.StatusAtendimento;

public class Pedido {
    private String descricao;

    public Pedido(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Pedido: " + descricao;
    }
}
