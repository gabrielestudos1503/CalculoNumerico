import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static List<double[]> testeIntervalos = new ArrayList<>();
    static Map<String, Number> resultados;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Método de Bisseção");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Inserir Informações

        JLabel polinomioLabel = new JLabel("f(x): ");
        polinomioLabel.setBounds(50, 40, 100, 20);
        polinomioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(polinomioLabel);

        JTextField polinomioTextField = new JTextField();
        polinomioTextField.setBounds(120, 40, 200, 30);
        panel.add(polinomioTextField);

        JLabel precisaoLabel = new JLabel("Precisão: ");
        precisaoLabel.setBounds(350, 40, 100, 20);
        precisaoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(precisaoLabel);

        JTextField precisaoTextField = new JTextField();
        precisaoTextField.setBounds(450, 40, 100, 30);
        panel.add(precisaoTextField);

        // Botão de calcular

        JButton teste = new JButton("Intervalos");
        teste.setBounds(170, 380, 100, 30);
        panel.add(teste);

        // Lista dos intervalos

        String[] colunas = { "Intervalos" };
        DefaultTableModel model = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBounds(120, 100, 200, 250);
        frame.add(scrollPane);

        teste.addActionListener(e -> {
            model.setRowCount(0);
            testeIntervalos = buscarIntervalos(polinomioTextField.getText());
            for (double[] par : testeIntervalos) {
                System.out.printf("[%.0f, %.0f]%n", par[0], par[1]);

                model.addRow(new Object[] { String.format("[%.0f, %.0f]%n", par[0], par[1])
                });
            }
        });

        // Seleção dos intervalos

        JLabel resultadoLabel = new JLabel("Resultado: ");
        resultadoLabel.setBounds(350, 100, 300, 20);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(resultadoLabel);

        JLabel erroLabel = new JLabel("Erro: ");
        erroLabel.setBounds(350, 150, 300, 20);
        erroLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(erroLabel);

        JLabel iteracaoLabel = new JLabel("Iteração: ");
        iteracaoLabel.setBounds(350, 200, 100, 20);
        iteracaoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(iteracaoLabel);

        JButton usarBotao = new JButton("Calcular");
        usarBotao.setBounds(600, 40, 100, 30);
        panel.add(usarBotao);

        usarBotao.addActionListener(e -> {
            int viewRow = tabela.getSelectedRow();
            if (viewRow == -1) {
                JOptionPane.showMessageDialog(frame, "Selecione um intervalo.");
                return;
            }
            int row = tabela.convertRowIndexToModel(viewRow);

            String s = (String) model.getValueAt(row, 0);
            Matcher m = Pattern.compile("\\[\\s*([-\\d.]+)\\s*,\\s*([-\\d.]+)\\s*\\]").matcher(s);
            if (m.find()) {
                double esquerda = Double.parseDouble(m.group(1));
                double direita = Double.parseDouble(m.group(2));
                System.out.printf("Selecionado: [%.2f, %.2f]%n", esquerda, direita);

                if (precisaoTextField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Insira uma precisão.");
                    return;
                }

                resultados = bissecao(esquerda, direita, Double.parseDouble(precisaoTextField.getText()),
                        polinomioTextField.getText());
                resultadoLabel.setText("Resultado: " + resultados.get("xn"));
                erroLabel.setText("Erro: " + resultados.get("erro"));
                iteracaoLabel.setText("Iteração: " + resultados.get("iteracao"));

            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static Map<String, Number> bissecao(double Xa, double Xb, double precisao, String polinomio) {
        // Mesmo a atividade pedindo para printar na tela apenas os resultados finais
        // dos intervalos, decidi fazer um sout para teste no prompt
        double fXa = calcularPolinomio(polinomio, Xa);
        double fXb = calcularPolinomio(polinomio, Xb);

        int iteracao = 1;
        double media = (Xa + Xb) / 2.0;
        double fxn = calcularPolinomio(polinomio, media);
        double erro = Xb - Xa;

        System.out.println(iteracao + " | " + Xa + " | " + Xb + " | " + media + " | " + fxn + " | " + erro);

        while (erro > precisao) {
            if (fXa * fxn <= 0) {
                Xb = media;
                fXb = fxn;
            } else {
                Xa = media;
                fXa = fxn;
            }
            iteracao++;
            media = (Xa + Xb) / 2.0;
            fxn = calcularPolinomio(polinomio, media);
            erro = Xb - Xa;
            System.out.println(iteracao + " | " + Xa + " | " + Xb + " | " + media + " | " + fxn + " | " + erro);

        }

        return Map.of("xn", media, "iteracao", iteracao, "erro", erro);

    }

    public static List<double[]> buscarIntervalos(String polinomio) {
        List<double[]> intervalos = new ArrayList<>();

        int xAnterior = -1000;
        double anterior = calcularPolinomio(polinomio, (double) xAnterior);

        for (int x = -999; x <= 1000; x++) {
            double fx = calcularPolinomio(polinomio, (double) x);

            if ((fx > 0 && anterior < 0) || (fx < 0 && anterior > 0)) {
                intervalos.add(new double[] { xAnterior, x });
            }

            xAnterior = x;
            anterior = fx;
        }

        return intervalos;
    }

    public static double calcularPolinomio(String polinomio, double x) {
        // Exemplo de polinomio: x^3-9*x+3

        // Validação
        polinomio = polinomio.replace(" ", "").toLowerCase().replace("*", "");
        polinomio = polinomio.replace("+-", "-").replace("-+", "-").replace("++", "+").replace("--", "+");
        polinomio = polinomio.replaceAll("([+\\-])x(?=(?:\\^|[+\\-]|$))", "$11x");
        polinomio = polinomio.replaceAll("^x(?=(?:\\^|[+\\-]|$))", "+1x");
        if (!polinomio.isEmpty() && polinomio.charAt(0) != '+' && polinomio.charAt(0) != '-') {
            polinomio = "+" + polinomio;
        }
        String[] termos = polinomio.split("(?=[+\\-])");

        double total = 0.0;

        for (String termo : termos) {
            if (termo.isEmpty()) {
                continue;
            }

            // Vendo se é negativo ou positivo
            int sinal = 1;
            if (termo.charAt(0) == '+')
                termo = termo.substring(1);
            else if (termo.charAt(0) == '-') {
                sinal = -1;
                termo = termo.substring(1);
            }
            if (termo.isEmpty()) {
                continue;
            }

            // tratando o x
            if (termo.contains("x")) {
                int indexX = termo.indexOf('x');
                String coeficienteString = termo.substring(0, indexX);
                double coeficiente = coeficienteString.isEmpty() ? 1.0 : Double.parseDouble(coeficienteString);

                int expoente = 1;
                int indexElevado = termo.indexOf('^');
                if (indexElevado != -1) {
                    String expoStr = termo.substring(indexElevado + 1);
                    expoente = Integer.parseInt(expoStr);
                }

                // tratando elevado
                double elevado = 1;
                for (int i = 0; i < expoente; i++) {
                    elevado *= x;
                }

                // Fazendo calculo
                total += sinal * coeficiente * elevado;
            } else {
                // caso seja um numero sozinho sem nada
                double termoSolo = Double.parseDouble(termo);
                total += sinal * termoSolo;
            }
        }
        return total;
        // // 1^3-9*1+3
    }
}
