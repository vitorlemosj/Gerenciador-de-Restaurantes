public enum StatusAtendimento {
    AGUARDANDO_CHEGADA("Aguardando Chegada"),
    AGUARDANDO_PEDIDO("Aguardando Anotação do Pedido"),
    PEDIDO_ANOTADO("Pedido Anotado, Aguardando Entrega"),
    PEDIDO_ENTREGUE("Pedido Entregue"),
    AGUARDANDO_CONTA("Aguardando a Conta"),
    FINALIZADO("Atendimento Finalizado");

    private final String descricao;

    StatusAtendimento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
