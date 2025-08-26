package Gauss;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GaussGUI::new);
    }
}

class GaussGUI extends JFrame {
    private final JTextField nField = new JTextField("3", 4);
    private final JButton gerarBtn = new JButton("Gerar");
    private final JButton resolverBtn = new JButton("Resolver");
    private final JPanel gridPanel = new JPanel();
    private JTextField[][] celuluas = new JTextField[0][0];

    GaussGUI() {
        super("Gauss Matriz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.add(new JLabel("n:"));
        top.add(nField);
        top.add(gerarBtn);
        add(top, BorderLayout.NORTH);

        gridPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        bottom.add(resolverBtn);
        add(bottom, BorderLayout.SOUTH);

        gerarBtn.addActionListener(e -> {
            int n = Integer.parseInt(nField.getText().trim());
            if (n <= 0 || n > 20){
                System.out.println("TEste erro");
            }

        });

        resolverBtn.addActionListener(e -> {

        });

        setSize(720, 480);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
