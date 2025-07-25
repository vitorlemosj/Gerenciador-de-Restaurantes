import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GrupoClientes {
    private static int proximoId = 1;
    private final int id;
    private final int quantidadePessoas;
    private final List<Pedido> pedidos;
    private StatusAtendimento status;
    private final LocalDateTime horaChegada;

    public GrupoClientes(int quantidadePessoas) {
        this.id = proximoId++;
        this.quantidadePessoas = quantidadePessoas;
        this.pedidos = new ArrayList<>(); // Exigência: Uso de uma implementação de List
        this.status = StatusAtendimento.AGUARDANDO_CHEGADA;
        this.horaChegada = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        System.out.println("Pedido \"" + pedido + "\" adicionado ao grupo " + this.id);
    }

    public StatusAtendimento getStatus() {
        return status;
    }

    public void setStatus(StatusAtendimento status) {
        this.status = status;
        System.out.println("Status do grupo " + this.id + " atualizado para: " + status.getDescricao());
    }

    public LocalDateTime getHoraChegada() {
        return horaChegada;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("Grupo ID: %d | Pessoas: %d | Status: %s | Chegada: %s | Pedidos: %d",
                id, quantidadePessoas, status.getDescricao(), horaChegada.format(formatter), pedidos.size());
    }
}
