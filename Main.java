import javax.swing.*;
import java.awt.*;

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
        polinomioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(polinomioLabel);

        JTextField polinomioTextField = new JTextField();
        polinomioTextField.setBounds(175, 40, 200, 30);
        panel.add(polinomioTextField);

        frame.add(panel);
        frame.setVisible(true);
    }
}
