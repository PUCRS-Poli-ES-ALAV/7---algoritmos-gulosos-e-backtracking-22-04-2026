// João Vitor Amaral Rangel
// Luis Flores Acosta
// Rodrigo Gagliardi


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    static class ProblemaRainhas {
        private int[] tabuleiro;
        private int n;
        private List<int[]> todasSolucoes;

        public ProblemaRainhas(int n) {
            this.n = n;
            this.tabuleiro = new int[n];
            this.todasSolucoes = new ArrayList<>();
        }

        public void resolver() {
            System.out.println("[DEBUG] Iniciando resolucao para " + n + "-Rainhas...");
            long inicio = System.currentTimeMillis();
            resolverRainhas(0);
            System.out.println("[DEBUG] Resolucao concluida em " + (System.currentTimeMillis() - inicio) + "ms\n");
        }

        private void resolverRainhas(int coluna) {
            if (coluna == n) {
                todasSolucoes.add(copiarTabuleiro());
                System.out.println("[DEBUG] Solucao #" + todasSolucoes.size() + " encontrada");
                return;
            }
            for (int linha = 0; linha < n; linha++) {
                if (ehSeguro(linha, coluna)) {
                    tabuleiro[coluna] = linha;
                    resolverRainhas(coluna + 1);
                    tabuleiro[coluna] = -1;
                }
            }
        }

        private int[] copiarTabuleiro() {
            int[] copia = new int[n];
            for (int i = 0; i < n; i++) {
                copia[i] = tabuleiro[i];
            }
            return copia;
        }

        private boolean ehSeguro(int linha, int coluna) {
            for (int col = 0; col < coluna; col++) {
                int linhaRainha = tabuleiro[col];
                if (linhaRainha == linha) return false;
                if (Math.abs(linhaRainha - linha) == Math.abs(col - coluna)) return false;
            }
            return true;
        }

        public void exibirSolucoes() {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("[RESULTADO] N-Rainhas: " + n);
            System.out.println("[RESULTADO] Solucoes: " + todasSolucoes.size());
            System.out.println("=".repeat(70) + "\n");
            if (todasSolucoes.isEmpty()) {
                System.out.println("[AVISO] Nenhuma solucao para " + n + " rainhas.");
                return;
            }
            for (int i = 0; i < todasSolucoes.size(); i++) {
                int[] solucao = todasSolucoes.get(i);
                System.out.print("[SOLUCAO #" + (i + 1) + "] ");
                for (int j = 0; j < n; j++) {
                    System.out.print("C" + j + ":L" + solucao[j]);
                    if (j < n - 1) System.out.print(" | ");
                }
                System.out.println();
            }
            System.out.println("\n" + "=".repeat(70) + "\n");
        }
    }

    static class ProblemaTraco {
        private int[] moedas;
        private int valor;
        private int iteracoes;

        public ProblemaTraco(int[] moedas, int valor) {
            this.moedas = moedas;
            this.valor = valor;
        }

        public void resolver() {
            System.out.println("[DEBUG] Iniciando troco para R$" + (valor / 100.0) + "...");
            long inicio = System.currentTimeMillis();
            iteracoes = 0;
            int[] contagem = calcularTraco();
            System.out.println("[RESULTADO] Valor: R$" + (valor / 100.0));
            System.out.println("[RESULTADO] Moedas usadas: " + totalMoedas(contagem));
            System.out.println("[RESULTADO] Iteracoes: " + iteracoes);
            System.out.println("[RESULTADO] Tempo: " + (System.currentTimeMillis() - inicio) + "ms\n");
            exibirDetalhes(contagem);
        }

        private int[] calcularTraco() {
            int[] contagem = new int[moedas.length];
            int restante = valor;
            for (int i = 0; i < moedas.length; i++) {
                while (restante >= moedas[i]) {
                    restante -= moedas[i];
                    contagem[i]++;
                    iteracoes++;
                }
            }
            return contagem;
        }

        private int totalMoedas(int[] contagem) {
            int total = 0;
            for (int x : contagem) {
                total += x;
            }
            return total;
        }

        private void exibirDetalhes(int[] contagem) {
            System.out.println("[DETALHES] Moedas:");
            for (int i = 0; i < moedas.length; i++) {
                if (contagem[i] > 0) {
                    System.out.println("  R$" + (moedas[i] / 100.0) + ": " + contagem[i]);
                }
            }
            System.out.println();
        }
    }

    static class ProblemaEscalonamento {
        private int[][] intervalos;

        public ProblemaEscalonamento(int[][] intervalos) {
            this.intervalos = intervalos;
        }

        public void resolver() {
            System.out.println("[DEBUG] Iniciando escalonamento de intervalos...");
            ordenarPorFim();
            int[] escolhidos = selecionar();
            exibirResultado(escolhidos);
        }

        private void ordenarPorFim() {
            for (int i = 0; i < intervalos.length - 1; i++) {
                for (int j = i + 1; j < intervalos.length; j++) {
                    if (intervalos[i][1] > intervalos[j][1]) {
                        int[] temp = intervalos[i];
                        intervalos[i] = intervalos[j];
                        intervalos[j] = temp;
                    }
                }
            }
        }

        private int[] selecionar() {
            int[] escolhidos = new int[intervalos.length];
            int cont = 0;
            int fimAtual = -1;
            for (int i = 0; i < intervalos.length; i++) {
                if (intervalos[i][0] >= fimAtual) {
                    escolhidos[cont] = i;
                    cont++;
                    fimAtual = intervalos[i][1];
                }
            }
            int[] resultado = new int[cont];
            for (int i = 0; i < cont; i++) {
                resultado[i] = escolhidos[i];
            }
            return resultado;
        }

        private void exibirResultado(int[] escolhidos) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("[RESULTADO] Escalonamento de intervalos");
            System.out.println("[RESULTADO] Intervalos escolhidos: " + escolhidos.length);
            System.out.println("=".repeat(70));
            for (int i = 0; i < escolhidos.length; i++) {
                int idx = escolhidos[i];
                System.out.println("Intervalo " + idx + ": [" + intervalos[idx][0] + ", " + intervalos[idx][1] + "]");
            }
            System.out.println();
        }
    }

    static void exibirMenu() {
        System.out.println("\n" + "#".repeat(70));
        System.out.println("# MENU PRINCIPAL - ESCOLHA UM PROBLEMA");
        System.out.println("#".repeat(70));
        System.out.println("1. Problema das N-Rainhas (Backtracking)");
        System.out.println("2. Problema do Troco (Algoritmo Guloso)");
        System.out.println("3. Escalonamento de Intervalos (Guloso)");
        System.out.println("4. Sair");
        System.out.println("#".repeat(70));
        System.out.print("Escolha uma opcao (1-4): ");
    }

    static void resolverRainhas(Scanner scanner) {
        System.out.println("\n[INFO] --- PROBLEMA DAS N-RAINHAS ---\n");
        System.out.print("Informe o tamanho do tabuleiro (ex: 4, 8, 6): ");
        try {
            int n = scanner.nextInt();
            if (n < 1) {
                System.out.println("[ERRO] Tamanho deve ser maior que 0!");
                return;
            }
            ProblemaRainhas rainhas = new ProblemaRainhas(n);
            rainhas.resolver();
            rainhas.exibirSolucoes();
        } catch (Exception e) {
            System.out.println("[ERRO] Entrada invalida!");
            scanner.nextLine();
        }
    }

    static void resolverTraco(Scanner scanner) {
        System.out.println("\n[INFO] --- PROBLEMA DO TROCO ---\n");
        System.out.println("[INFO] Moedas disponiveis (em centavos): 100, 50, 25, 10, 5, 1");
        System.out.print("Informe o valor do troco em centavos (ex: 289 para R$2,89): ");
        try {
            int valor = scanner.nextInt();
            if (valor < 1) {
                System.out.println("[ERRO] Valor deve ser maior que 0!");
                return;
            }
            int[] moedas = {100, 50, 25, 10, 5, 1};
            ProblemaTraco traco = new ProblemaTraco(moedas, valor);
            traco.resolver();
        } catch (Exception e) {
            System.out.println("[ERRO] Entrada invalida!");
            scanner.nextLine();
        }
    }

    static void resolverEscalonamento(Scanner scanner) {
        System.out.println("\n[INFO] --- ESCALONAMENTO DE INTERVALOS ---\n");
        System.out.print("Quantos intervalos deseja inserir? ");
        try {
            int n = scanner.nextInt();
            if (n < 1) {
                System.out.println("[ERRO] Quantidade deve ser maior que 0!");
                return;
            }
            int[][] intervalos = new int[n][2];
            for (int i = 0; i < n; i++) {
                System.out.print("Intervalo " + i + " inicio e fim (ex: 1 3): ");
                int inicio = scanner.nextInt();
                int fim = scanner.nextInt();
                if (inicio > fim) {
                    System.out.println("[ERRO] Inicio nao pode ser maior que fim!");
                    return;
                }
                intervalos[i][0] = inicio;
                intervalos[i][1] = fim;
            }
            ProblemaEscalonamento escalonamento = new ProblemaEscalonamento(intervalos);
            escalonamento.resolver();
        } catch (Exception e) {
            System.out.println("[ERRO] Entrada invalida!");
            scanner.nextLine();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        while (executando) {
            exibirMenu();
            try {
                int opcao = scanner.nextInt();
                switch (opcao) {
                    case 1:
                        resolverRainhas(scanner);
                        break;
                    case 2:
                        resolverTraco(scanner);
                        break;
                    case 3:
                        resolverEscalonamento(scanner);
                        break;
                    case 4:
                        System.out.println("\n[INFO] Programa finalizado!");
                        System.out.println("#".repeat(70) + "\n");
                        executando = false;
                        break;
                    default:
                        System.out.println("[ERRO] Opcao invalida! Digite 1, 2, 3 ou 4.");
                }
            } catch (Exception e) {
                System.out.println("[ERRO] Entrada invalida!");
                scanner.nextLine();
            }
        }
        scanner.close();
    }
}