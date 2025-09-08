import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<double[]> testeIntervalos = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Método de Bisseção");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Inserir Polinomio

        JLabel polinomioLabel = new JLabel("f(x): ");
        polinomioLabel.setBounds(50, 40, 100, 20);
        polinomioLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(polinomioLabel);

        JTextField polinomioTextField = new JTextField();
        polinomioTextField.setBounds(120, 40, 200, 30);
        panel.add(polinomioTextField);

        JButton teste = new JButton("Mostrar");
        teste.setBounds(350, 40, 100, 30);
        panel.add(teste);

        teste.addActionListener(e -> {
            // testando intervalos
            testeIntervalos = buscarIntervalos(polinomioTextField.getText());
            for (double[] par : testeIntervalos) {
                System.out.printf("[%.0f, %.0f]%n", par[0], par[1]);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
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
