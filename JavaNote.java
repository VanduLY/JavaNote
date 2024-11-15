import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class JavaNote {
    private JFrame frame;
    private JTextArea textArea;
    private JFileChooser fileChooser;

    public JavaNote() {
        createUI();
    }

    private void createUI() {
        frame = new JFrame("JavaNote - Note Taking Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create the text area
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Create the menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newNote = new JMenuItem("New Note");
        JMenuItem openNote = new JMenuItem("Open Note");
        JMenuItem saveNote = new JMenuItem("Save Note");
        JMenuItem exitApp = new JMenuItem("Exit");

        newNote.addActionListener(e -> newNote());
        openNote.addActionListener(e -> openNote());
        saveNote.addActionListener(e -> saveNote());
        exitApp.addActionListener(e -> System.exit(0));

        fileMenu.add(newNote);
        fileMenu.add(openNote);
        fileMenu.add(saveNote);
        fileMenu.addSeparator();
        fileMenu.add(exitApp);
        menuBar.add(fileMenu);

        // Set up file chooser
        fileChooser = new JFileChooser();

        // Add components to the frame
        frame.setJMenuBar(menuBar);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void newNote() {
        int confirmation = JOptionPane.showConfirmDialog(frame, 
            "Do you want to save the current note?", 
            "New Note", 
            JOptionPane.YES_NO_CANCEL_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            saveNote();
        }
        if (confirmation != JOptionPane.CANCEL_OPTION) {
            textArea.setText("");
        }
    }

    private void openNote() {
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, 
                    "Error reading file: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveNote() {
        int result = fileChooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(textArea.getText());
                JOptionPane.showMessageDialog(frame, 
                    "Note saved successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, 
                    "Error saving file: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JavaNote::new);
    }
}
