import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Optional;

public class Restaurante {
    private final List<Garcom> garcons;
    private final Queue<GrupoClientes> filaDeEspera;

    public Restaurante(List<Garcom> garcons) {
        // Garante que a lista de garçons seja imutável após a inicialização
        this.garcons = Collections.unmodifiableList(garcons);
        this.filaDeEspera = new LinkedList<>();
    }

    public List<Garcom> getGarcons() {
        return garcons;
    }

    public void chegadaDeGrupo(GrupoClientes grupo) {
        Optional<Garcom> garcomDisponivel = encontrarGarcomMenosOcupado();

        if (garcomDisponivel.isPresent() && garcomDisponivel.get().podeAtender()) {
            garcomDisponivel.get().adicionarGrupo(grupo);
        } else {
            System.out.println("Todos os garçons estão ocupados. Grupo " + grupo.getId() + " adicionado à fila de espera.");
            this.filaDeEspera.add(grupo);
        }
    }

    public void tentarAlocarDaFilaDeEspera() {
        if (!filaDeEspera.isEmpty()) {
            Optional<Garcom> garcomDisponivel = encontrarGarcomMenosOcupado();
            if (garcomDisponivel.isPresent() && garcomDisponivel.get().podeAtender()) {
                GrupoClientes proximoGrupo = filaDeEspera.poll(); // Pega o primeiro grupo da fila de espera
                System.out.println("Vaga liberada! Alocando grupo " + proximoGrupo.getId() + " da fila de espera.");
                garcomDisponivel.get().adicionarGrupo(proximoGrupo);
            }
        }
    }

    private Optional<Garcom> encontrarGarcomMenosOcupado() {
        return this.garcons.stream()
                .min(Comparator.comparingInt(g -> g.getFilaAtendimento().size()));
    }

    public Optional<Garcom> encontrarGarcomPorNome(String nome) {
        return this.garcons.stream()
                .filter(g -> g.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public void verificarTemposDeEspera(long limiteMinutos) {
        System.out.println("\n--- Verificando Tempos de Espera Excessivos (Limite: " + limiteMinutos + " min) ---");
        LocalDateTime agora = LocalDateTime.now();
        boolean encontrouAtraso = false;

        // Verifica grupos em atendimento
        for (Garcom garcom : garcons) {
            for (GrupoClientes grupo : garcom.getFilaAtendimento()) {
                long minutosEsperando = Duration.between(grupo.getHoraChegada(), agora).toMinutes();
                if (minutosEsperando > limiteMinutos) {
                    System.out.printf("ALERTA: O grupo %d (Garçom: %s) está esperando há %d minutos!\n",
                            grupo.getId(), garcom.getNome(), minutosEsperando);
                    encontrouAtraso = true;
                }
            }
        }

        // Verifica fila de espera
        for (GrupoClientes grupo : filaDeEspera) {
            long minutosEsperando = Duration.between(grupo.getHoraChegada(), agora).toMinutes();
            if (minutosEsperando > limiteMinutos) {
                System.out.printf("ALERTA: O grupo %d (Fila de Espera) está esperando há %d minutos!\n",
                        grupo.getId(), minutosEsperando);
                encontrouAtraso = true;
            }
        }

        if (!encontrouAtraso) {
            System.out.println("Nenhum grupo com tempo de espera excessivo encontrado.");
        }
    }

    public void exibirStatusGeral() {
        System.out.println("\n=============== STATUS DO RESTAURANTE ===============");
        for (Garcom garcom : garcons) {
            System.out.println("-----------------------------------------------------");
            System.out.printf("Garçom: %s | Atendimentos: %d/%d\n",
                    garcom.getNome(), garcom.getFilaAtendimento().size(), Garcom.CAPACIDADE_MAXIMA);
            if (garcom.getFilaAtendimento().isEmpty()) {
                System.out.println("  >> Nenhum grupo sendo atendido.");
            } else {
                garcom.getFilaAtendimento().forEach(grupo -> System.out.println("  -> " + grupo));
            }
        }
        System.out.println("-----------------------------------------------------");
        System.out.println("Fila de Espera: " + filaDeEspera.size() + " grupo(s)");
        if (!filaDeEspera.isEmpty()) {
            filaDeEspera.forEach(grupo -> System.out.println("  -> " + grupo));
        }
        System.out.println("=====================================================\n");
    }
}
