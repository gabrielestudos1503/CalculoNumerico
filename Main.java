import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
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
            calcularPolinomio(polinomioTextField.getText(), 1);
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void buscarIntervalos(String polinomio) {
        for (int i = -1000; i < 1000; i++) {

        }
    }

    public static void calcularPolinomio(String polinomio, int variavel) {
        // Exemplo de polinomio: x^3-9*x+3

        // Validação e nom
        polinomio = polinomio.replace(" ", "");
        polinomio = polinomio.toLowerCase();
        polinomio = polinomio.replace("*", "");

        if (polinomio.contains("+-") || polinomio.contains("-+")) {
            polinomio = polinomio.replace("+-", "-");
            polinomio = polinomio.replace("-+", "-");
        }
        if (polinomio.contains("++") || polinomio.contains("--")) {
            polinomio = polinomio.replace("++", "+");
            polinomio = polinomio.replace("--", "+");
        }

        polinomio = polinomio.replaceAll("([+\\-])x(?=(?:\\^|[+\\-]|$))", "$11x");
        polinomio = polinomio.replaceAll("^x(?=(?:\\^|[+\\-]|$))", "+1x");

        String[] formas = polinomio.split("(?=[+\\-])");



        for(String f : formas){
            System.out.println(f);
        }
        System.out.println(polinomio);

        // 1^3-9*1+3
    }
}
