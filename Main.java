import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Método de Bisseção");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
    
        JLabel text = new JLabel("Teste");
        text.setBounds(100, 40, 100, 20);
        text.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(text);

        frame.add(panel);
        frame.setVisible(true);
    }
}
