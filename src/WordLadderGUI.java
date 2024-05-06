import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WordLadderGUI extends JFrame {
    private JTextField startWordField;
    private JTextField endWordField;
    private JComboBox<String> algorithmSelector;
    private JPanel resultPanel;
    private JButton solveButton;
    private Dictionary dictionary;
    private WordSolver solver;
    private JLabel statusLabel;

    public WordLadderGUI() {
        createView();
        setTitle("Word Ladder Solver");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        pack();
        setVisible(true);

        try {
            dictionary = new Dictionary("words.txt");
            solver = new WordSolver(dictionary);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load dictionary: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createView() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        Font font = new Font("SansSerif", Font.BOLD, 14);
        Font fontinput = new Font("SansSerif", Font.PLAIN, 13);
        Font fontselect = new Font("SansSerif", Font.BOLD, 13);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        startWordField = new JTextField(10);
        startWordField.setFont(fontinput);
        endWordField = new JTextField(10);
        endWordField.setFont(fontinput);
        algorithmSelector = new JComboBox<>(new String[]{"ucs", "gbfs", "a*"});
        algorithmSelector.setFont(fontselect);
        solveButton = new JButton("Solve");
        solveButton.setFont(fontselect);
        
        JLabel startWordLabel = new JLabel("Start Word:");
        startWordLabel.setFont(font);
        JLabel endWordLabel = new JLabel("End Word:");
        endWordLabel.setFont(font);
        JLabel algorithmLabel = new JLabel("Algorithm:");
        algorithmLabel.setFont(font);

        inputPanel.add(startWordLabel);
        inputPanel.add(startWordField);
        inputPanel.add(endWordLabel);
        inputPanel.add(endWordField);
        inputPanel.add(algorithmLabel);
        inputPanel.add(algorithmSelector);
        inputPanel.add(solveButton);
        panel.add(inputPanel, BorderLayout.NORTH);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        scrollPane.setPreferredSize(new Dimension(700, 700));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);

        solveButton.addActionListener(e -> {
            solveButton.setEnabled(false);
            solve();
            solveButton.setEnabled(true); 
        });
    }

    private void solve() {
        String startWord = startWordField.getText().trim().toLowerCase();
        String endWord = endWordField.getText().trim().toLowerCase();
        String algorithm = (String) algorithmSelector.getSelectedItem();
        if (startWord.isEmpty() || endWord.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both words.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (startWord.length() != endWord.length()) {
            JOptionPane.showMessageDialog(this, "Both size are not same", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        solveButton.setEnabled(false);
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long startTime = System.currentTimeMillis();
    
        try {
            List<String> path = solver.solve(startWord, endWord, algorithm, dictionary);
            long endTime = System.currentTimeMillis();
            long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = Math.max(0, (usedMemoryAfter - usedMemoryBefore) / 1024);
            long duration = (endTime - startTime);
    
            if (path.isEmpty()) {
                memoryUsed = 0;
                duration = 0;
            }
    
            displayPath(path);
            statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            statusLabel.setText("<html><div style='padding-top: 10px; padding-bottom: 10px; text-align: center;'>Solved in: " + duration + " ms &nbsp;&nbsp;|&nbsp;&nbsp; Nodes visited: " + solver.getVisitedNodesCount() + 
                                " &nbsp;&nbsp;|&nbsp;&nbsp; Memory used: " + memoryUsed + " </div></html>");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error solving word ladder: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            solveButton.setEnabled(true);
        }
    } 

    private void displayPath(List<String> path) {
        resultPanel.removeAll();
        if (path.isEmpty()) {
            resultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel noSolutionLabel = new JLabel("No solution found");
            noSolutionLabel.setFont(new Font("Serif", Font.BOLD, 24));
            resultPanel.add(noSolutionLabel);
        } else {
            resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
            String prevWord = null;
            int size = path.size();
            for (int j = 0; j < size; j++) {
                String word = path.get(j);
                JPanel wordPanel = new JPanel(new GridLayout(1, word.length() + 1, 2, 0));
                wordPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    
                JLabel numberLabel = new JLabel(String.valueOf(j + 1) + ".", SwingConstants.CENTER);
                numberLabel.setPreferredSize(new Dimension(30, 30));
                wordPanel.add(numberLabel);
    
                for (int i = 0; i < word.length(); i++) {
                    JLabel letterLabel = new JLabel(String.valueOf(word.charAt(i)), SwingConstants.CENTER);
                    letterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    letterLabel.setPreferredSize(new Dimension(30, 30));
                    letterLabel.setFont(letterLabel.getFont().deriveFont(18f));
                    if (prevWord != null && word.charAt(i) != prevWord.charAt(i)) {
                        letterLabel.setBackground(Color.GREEN);
                        letterLabel.setOpaque(true);
                    }
                    wordPanel.add(letterLabel);
                }
    
                if (j == 0) { // start word
                    wordPanel.setBackground(Color.LIGHT_GRAY);
                    JLabel startlabel = new JLabel("Start");
                    startlabel.setFont(new Font("Serif", Font.BOLD, 20));
                    resultPanel.add(startlabel);
                } else if (j == size - 1) { // stop word
                    resultPanel.add(Box.createVerticalStrut(20));
                    wordPanel.setBackground(Color.LIGHT_GRAY);
                    JLabel endlabel = new JLabel("End");
                    endlabel.setFont(new Font("Serif", Font.BOLD, 20));
                    resultPanel.add(endlabel);
                } else if (j == 1){
                    resultPanel.add(Box.createVerticalStrut(20)); 
                }
                resultPanel.add(wordPanel);
                prevWord = word;
            }
        }
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public static void main (String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordLadderGUI();
            }
        });
    }
}