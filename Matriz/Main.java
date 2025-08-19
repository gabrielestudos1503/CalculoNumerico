package Matriz;

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

        JLabel polinomioLabel = new JLabel("Tamanho da matriz: ");
        polinomioLabel.setBounds(50, 40, 200, 20);
        polinomioLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(polinomioLabel);

        JTextField polinomioTextField = new JTextField();
        polinomioTextField.setBounds(200, 40, 200, 30);
        panel.add(polinomioTextField);

        JButton teste = new JButton("Mostrar");
        teste.setBounds(420, 40, 100, 30);
        panel.add(teste);

        teste.addActionListener(e -> {
            int tamanho = Integer.parseInt(teste.getText());
            for (int i = 0; i < tamanho; i++) {
                for (int j = 0; j < tamanho; j++) {

                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}