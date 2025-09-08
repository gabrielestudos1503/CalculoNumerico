package Gauss;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {
    private final JTextField nField = new JTextField("3", 3);
    private final JButton gerarBtn = new JButton("Gerar campos");
    private final JButton lerBtn = new JButton("Ler matriz (debug)");
    private final JPanel gridPanel = new JPanel(new GridBagLayout());

    private JTextField[][] aCells = new JTextField[0][0];
    private JTextField[] bCells = new JTextField[0];

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

    public Main() {
        super("Gauss – Inserção da Matriz (A | b)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        // Topo
        JPanel topo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        topo.add(new JLabel("Ordem (n):"));
        topo.add(nField);
        topo.add(gerarBtn);
        topo.add(lerBtn);
        add(topo, BorderLayout.NORTH);

        // Centro (grade)
        gridPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        // Ações
        gerarBtn.addActionListener(e -> {
            int n = Integer.parseInt(nField.getText().trim());
            montarGrade(n);
        });

        lerBtn.addActionListener(e -> {
            double[][] A = lerMatrizA();
            double[] b = lerVetorB();
            System.out.println("Matriz aumentada (A | b):");
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < A[i].length; j++)
                    System.out.print(A[i][j] + "\t");
                System.out.println("| " + b[i]);
            }
        });

        setSize(640, 420);
        setLocationRelativeTo(null);
    }

    private void montarGrade(int n) {
        aCells = new JTextField[n][n];
        bCells = new JTextField[n];
        gridPanel.removeAll();

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                c.gridx = j;
                c.gridy = i;
                JTextField tf = new JTextField(6);
                tf.setHorizontalAlignment(JTextField.CENTER);
                aCells[i][j] = tf;
                gridPanel.add(tf, c);
            }
            c.gridx = n;
            c.gridy = i;
            gridPanel.add(new JLabel("="), c);

            c.gridx = n + 1;
            c.gridy = i;
            JTextField tfb = new JTextField(6);
            tfb.setHorizontalAlignment(JTextField.CENTER);
            bCells[i] = tfb;
            gridPanel.add(tfb, c);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public double[][] lerMatrizA() {
        int n = aCells.length;
        double[][] A = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                String t = aCells[i][j].getText().trim();
                A[i][j] = t.isEmpty() ? 0.0 : Double.parseDouble(t);
            }
        return A;
    }

    public double[] lerVetorB() {
        int n = bCells.length;
        double[] b = new double[n];
        for (int i = 0; i < n; i++) {
            String t = bCells[i].getText().trim();
            b[i] = t.isEmpty() ? 0.0 : Double.parseDouble(t);
        }
        return b;
    }

}