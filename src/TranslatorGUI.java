import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TranslatorGUI extends JFrame {

    private JButton saveCode;
    private JTextPane translatedCode;
    private JTextArea pseudocod;
    private JPanel mainPanel;
    private Timer debounceTimer;

    public TranslatorGUI() {

        saveCode.addActionListener(e -> {

            String javaCode = translatedCode.getText();
            FileManager.writeToOutputFile(javaCode, "output.txt");

            JOptionPane.showMessageDialog(null, "Codul a fost salvat cu succes!");

        });

        pseudocod.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (debounceTimer != null) {
                    debounceTimer.stop();
                }
                debounceTimer = new Timer(500, new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        translate();
                        debounceTimer.stop();
                    }
                });
                debounceTimer.start();
            }
        });

        translate();

    }

    private void translate() {
        String pseudoCode = pseudocod.getText();
        String javaCode = Parser.translateToJava(pseudoCode);
        translatedCode.setText(javaCode);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TranslatorGUI");
        frame.setContentPane(new TranslatorGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
