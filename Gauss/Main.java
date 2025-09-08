package Gauss;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {
    private final JTextField nField = new JTextField("3", 3);
    private final JButton gerarBtn = new JButton("Gerar campos");
    private final JButton lerBtn = new JButton("Ler matriz (debug)");
    private final JPanel gridPanel = new JPanel(new GridBagLayout());
    private final JButton resolverBtn = new JButton("Resolver");
    private final JTextArea logArea = new JTextArea(12, 70);

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
        topo.add(resolverBtn);
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

        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        // Ação do botão Resolver
        resolverBtn.addActionListener(e -> {
            double[][] A = lerMatrizA();
            double[] b = lerVetorB();

            logArea.setText("");
            escalonar(A, b, logArea); 
            double[] x = retroSubstituicao(A, b);

            StringBuilder sb = new StringBuilder();
            sb.append("Solução:\n");
            for (int i = 0; i < x.length; i++) {
                sb.append(String.format("x%d = %.6f%n", i + 1, x[i]));
            }
            logArea.append(sb.toString());
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

    static void escalonar(double[][] A, double[] b, JTextArea log) {
        int n = A.length;
        StringBuilder stringBuilder = new StringBuilder();

        for (int k = 0; k < n - 1; k++) {
            if (A[k][k] == 0.0) {
                int p = -1;
                for (int i = k + 1; i < n; i++) {
                    if (A[i][k] != 0.0) {
                        p = i;
                        break;
                    }
                }
                if (p == -1) {
                    stringBuilder.append("Iteração ").append(k + 1)
                            .append(": pivô zero e sem linha pra trocar. Sistema singular.\n\n");
                    log.append(stringBuilder.toString());
                    return;
                }
                double[] tmp = A[k];
                A[k] = A[p];
                A[p] = tmp;
                double tb = b[k];
                b[k] = b[p];
                b[p] = tb;
                stringBuilder.append(String.format("Iteração %d: TROCA L%d <-> L%d%n", k + 1, k + 1, p + 1));
                appendEstado(A, b, stringBuilder, "Após troca");
            }

            for (int i = k + 1; i < n; i++) {
                if (A[i][k] == 0.0)
                    continue;
                double m = -(A[i][k] / A[k][k]); 
                for (int j = k; j < n; j++) {
                    A[i][j] += m * A[k][j];
                }
                b[i] += m * b[k];
                A[i][k] = 0.0;

                stringBuilder.append(String.format("Iteração %d: L%d = L%d + (%.6f) * L%d%n",
                        k + 1, i + 1, i + 1, m, k + 1));
            }

            appendEstado(A, b, stringBuilder, "Após Iteração " + (k + 1));
            log.append(stringBuilder.toString());
            stringBuilder.setLength(0); 
        }
    }

    static double[] retroSubstituicao(double[][] A, double[] b) {
        int n = A.length;
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double soma = b[i];
            for (int j = i + 1; j < n; j++)
                soma -= A[i][j] * x[j];
            x[i] = soma / A[i][i];
        }
        return x;
    }

    static void appendEstado(double[][] A, double[] b, StringBuilder stringBuilder, String titulo) {
        stringBuilder.append(titulo).append('\n');
        int n = A.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                stringBuilder.append(String.format("%10.4f ", A[i][j]));
            stringBuilder.append(" | ").append(String.format("%10.4f", b[i])).append('\n');
        }
        stringBuilder.append('\n');
    }
}
