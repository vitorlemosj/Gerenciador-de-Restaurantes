
# 🍽️ App Gerenciador de Atendimento de Restaurante

Bem-vindo ao nosso sistema de gerenciamento de atendimentos em restaurantes, criado como projeto final para a disciplina de **Linguagem de Programação II** no IFBA – Campus Vitória da Conquista. Este sistema, implementado via console, foca em seguir estritamente os requisitos propostos para organizar o fluxo de trabalho dos garçons e gerenciar a fila de clientes de forma eficiente.

---

## 🎯 Objetivo

Simplificar o atendimento em restaurantes por meio de um sistema que:

- 📋 Gerencia uma equipe fixa de garçons e uma fila de espera para grupos de clientes.
- ⏳ Controla o tempo de espera e emite alertas para atendimentos demorados.
- ⚙️ Utiliza estruturas de dados clássicas como **Filas (Queue)** e **Listas (List)** conforme especificado.
- 📊 Acompanha o ciclo de atendimento com múltiplos status detalhados.

---

## 🛠️ Tecnologias Utilizadas

- Java 21
- Estruturas de Dados da Biblioteca Padrão do Java (`Queue`, `List`, etc.)

---

## 🧠 Estruturas de Dados e Requisitos Implementados

| Requisito                    | Implementação no Sistema |
|-----------------------------|---------------------------|
| Garçons Imutáveis           | A lista de garçons é definida na inicialização do sistema e tornada imutável. |
| Fila de Atendimento         | Cada garçom gerencia seus atendimentos através de uma Fila (`Queue` com `LinkedList`). |
| Lista de Pedidos            | Cada grupo de clientes possui uma Lista (`List`) com `ArrayList`. |
| Limite de Atendimento       | Um garçom não pode atender mais do que 3 grupos simultaneamente. |
| Controle de Tempo           | O sistema registra a hora de chegada e alerta sobre esperas excessivas. |
| Status do Atendimento       | O sistema gerencia múltiplos status como “Aguardando Pedido”, “Pedido Entregue”, “Aguardando Conta”. |

---

## ⚙️ Interface do Sistema

A interação com o sistema é realizada via **menu no console**, permitindo ao usuário:

- Registrar a chegada de novos grupos de clientes.
- Adicionar pedidos a um grupo em atendimento.
- Atualizar o status do atendimento de um grupo.
- Finalizar o atendimento de um grupo, liberando o garçom.
- Exibir um painel com o status geral de todos os garçons e da fila de espera.
- Verificar se algum grupo ultrapassou o tempo de espera excessivo.

---

## 🔁 Fluxo de Atendimento

1. Um novo grupo de clientes chega e é registrado no sistema.
2. O sistema tenta alocar o grupo para o garçom menos ocupado. Se todos estiverem ocupados, vai para a fila de espera.
3. O garçom anota os pedidos e o status é atualizado.
4. O status do grupo muda conforme o andamento (ex: pedido entregue).
5. Ao finalizar o atendimento, o garçom é liberado, e o próximo grupo da fila entra.

---

## 🚀 Como Executar

O projeto pode ser executado diretamente através da classe `Main`:

```bash
# Clone o projeto
git clone https://github.com/vitorlemosj/gerenciador-de-restaurantes.git

# Acesse a pasta do projeto
cd gerenciador-de-restaurantes/src

# Compile os arquivos Java
javac *.java

# Execute o sistema
java Main
```

**Pré-requisitos:** JDK 21 ou superior instalado.

---

## 📚 Créditos

Trabalho desenvolvido por:

- João Gabriel O. Magalhães  
- João Vitor M. Lemos  
- Vinicius D. Oliveira Rocha  

**Professor orientador:** Alexandro dos Santos Silva

---

## 🧠 Aprendizados

Este projeto permitiu aplicar conceitos fundamentais de **Programação Orientada a Objetos** e **Estruturas de Dados** em um contexto prático e real. Focamos em uma implementação limpa e alinhada aos requisitos propostos. Reforçamos conhecimentos sobre Filas, Listas e a importância de modelar sistemas com foco em eficiência.
