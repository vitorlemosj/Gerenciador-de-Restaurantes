import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // 1. Inicialização dos garçons (imutáveis)
        List<Garcom> garcons = new ArrayList<>();
        garcons.add(new Garcom("Carlos"));
        garcons.add(new Garcom("Beatriz"));
        garcons.add(new Garcom("Daniel"));

        Restaurante restaurante = new Restaurante(garcons);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao sistema de gerenciamento do restaurante!");
        System.out.println("Garçons no turno: Carlos, Beatriz, Daniel.");

        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1:
                    System.out.print("Informe a quantidade de pessoas no grupo: ");
                    int pessoas = scanner.nextInt();
                    restaurante.chegadaDeGrupo(new GrupoClientes(pessoas));
                    break;
                case 2:
                    adicionarPedido(restaurante, scanner);
                    break;
                case 3:
                    atualizarStatusGrupo(restaurante, scanner);
                    break;
                case 4:
                    finalizarAtendimento(restaurante, scanner);
                    break;
                case 5:
                    restaurante.exibirStatusGeral();
                    break;
                case 6:
                    System.out.print("Informe o tempo limite de espera em minutos para o alerta: ");
                    long limite = scanner.nextLong();
                    restaurante.verificarTemposDeEspera(limite);
                    break;
                case 0:
                    System.out.println("Encerrando o sistema.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1. Registrar chegada de novo grupo de clientes");
        System.out.println("2. Adicionar pedido a um grupo");
        System.out.println("3. Atualizar status de um grupo");
        System.out.println("4. Finalizar atendimento de um grupo");
        System.out.println("5. Exibir status geral do restaurante");
        System.out.println("6. Verificar tempo de espera excessivo");
        System.out.println("0. Sair");
        System.out.print(">> ");
    }

    private static Optional<GrupoClientes> encontrarGrupoPorId(Restaurante r, int id) {
        return r.getGarcons().stream()
                .flatMap(g -> g.getFilaAtendimento().stream())
                .filter(grupo -> grupo.getId() == id)
                .findFirst();
    }

    private static void adicionarPedido(Restaurante restaurante, Scanner scanner) {
        System.out.print("Informe o ID do grupo para adicionar o pedido: ");
        int idGrupo = scanner.nextInt();
        scanner.nextLine();

        Optional<GrupoClientes> grupoOpt = encontrarGrupoPorId(restaurante, idGrupo);
        if (grupoOpt.isPresent()) {
            System.out.print("Digite a descrição do pedido: ");
            String descPedido = scanner.nextLine();
            grupoOpt.get().adicionarPedido(new Pedido(descPedido));
            grupoOpt.get().setStatus(StatusAtendimento.PEDIDO_ANOTADO);
        } else {
            System.out.println("Grupo com ID " + idGrupo + " não encontrado em atendimento.");
        }
    }

    private static void atualizarStatusGrupo(Restaurante restaurante, Scanner scanner) {
        System.out.print("Informe o ID do grupo para atualizar o status: ");
        int idGrupo = scanner.nextInt();
        scanner.nextLine();

        Optional<GrupoClientes> grupoOpt = encontrarGrupoPorId(restaurante, idGrupo);
        if (grupoOpt.isPresent()) {
            System.out.println("Selecione o novo status:");
            for (StatusAtendimento s : StatusAtendimento.values()) {
                System.out.println(s.ordinal() + ". " + s.getDescricao());
            }
            System.out.print(">> ");
            int statusIndex = scanner.nextInt();
            scanner.nextLine();
            if (statusIndex >= 0 && statusIndex < StatusAtendimento.values().length) {
                grupoOpt.get().setStatus(StatusAtendimento.values()[statusIndex]);
            } else {
                System.out.println("Índice de status inválido.");
            }
        } else {
            System.out.println("Grupo com ID " + idGrupo + " não encontrado em atendimento.");
        }
    }

    private static void finalizarAtendimento(Restaurante restaurante, Scanner scanner) {
        System.out.print("Informe o nome do garçom que irá finalizar um atendimento: ");
        String nomeGarcom = scanner.nextLine();

        Optional<Garcom> garcomOpt = restaurante.encontrarGarcomPorNome(nomeGarcom);
        if (garcomOpt.isPresent()) {
            Garcom garcom = garcomOpt.get();
            if (!garcom.getFilaAtendimento().isEmpty()) {
                GrupoClientes grupoFinalizado = garcom.finalizarAtendimento();
                System.out.println("Atendimento do grupo " + grupoFinalizado.getId() + " finalizado pelo garçom " + garcom.getNome());
                // Tenta alocar um novo grupo da fila de espera para o garçom que liberou vaga
                restaurante.tentarAlocarDaFilaDeEspera();
            } else {
                System.out.println("O garçom " + nomeGarcom + " não está atendendo nenhum grupo no momento.");
            }
        } else {
            System.out.println("Garçom com nome '" + nomeGarcom + "' não encontrado.");
        }
    }
}