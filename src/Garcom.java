import java.util.LinkedList;
import java.util.Queue;

public class Garcom {
    public static final int CAPACIDADE_MAXIMA = 3;
    private final String nome;
    // Exigência: Uso de uma implementação de Fila (Queue)
    private final Queue<GrupoClientes> filaAtendimento;

    public Garcom(String nome) {
        this.nome = nome;
        this.filaAtendimento = new LinkedList<>();
    }

    public String getNome() {
        return nome;
    }

    public Queue<GrupoClientes> getFilaAtendimento() {
        return filaAtendimento;
    }

    public boolean podeAtender() {
        return this.filaAtendimento.size() < CAPACIDADE_MAXIMA;
    }

    public boolean adicionarGrupo(GrupoClientes grupo) {
        if (podeAtender()) {
            this.filaAtendimento.add(grupo);
            grupo.setStatus(StatusAtendimento.AGUARDANDO_PEDIDO);
            System.out.println("O grupo " + grupo.getId() + " foi alocado para o garçom " + this.nome);
            return true;
        }
        return false;
    }

    public GrupoClientes finalizarAtendimento() {
        GrupoClientes grupoFinalizado = this.filaAtendimento.poll(); // Remove o primeiro da fila
        if (grupoFinalizado != null) {
            grupoFinalizado.setStatus(StatusAtendimento.FINALIZADO);
        }
        return grupoFinalizado;
    }

    @Override
    public String toString() {
        return "Garçom: " + nome;
    }
}