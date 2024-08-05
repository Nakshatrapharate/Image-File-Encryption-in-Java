import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class image_2 extends JFrame {
    private JTextField filePathField, keyField;
    private JRadioButton encryptRadio, decryptRadio;
    private JButton processButton;
    public image_2() {
        setTitle("File Encryption/Decryption");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));
        mainPanel.add(new JLabel("File Path: "));
        filePathField = new JTextField();
        mainPanel.add(filePathField);
        mainPanel.add(new JLabel("Encryption/Decryption Key: "));
        keyField = new JTextField();
        mainPanel.add(keyField);
        encryptRadio = new JRadioButton("Encrypt");
        decryptRadio = new JRadioButton("Decrypt");
        ButtonGroup group = new ButtonGroup();
        group.add(encryptRadio);
        group.add(decryptRadio);
        JPanel radioPanel = new JPanel();
        radioPanel.add(encryptRadio);
        radioPanel.add(decryptRadio);
        mainPanel.add(new JLabel("Operation: "));
        mainPanel.add(radioPanel);
        processButton = new JButton("Process");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processFile();
            }
        });
        mainPanel.add(new JLabel());
        mainPanel.add(processButton);
        add(mainPanel);
        setVisible(true);
    }
    private void processFile() {
            String filePath = filePathField.getText();
            String key = keyField.getText();
            String choice = encryptRadio.isSelected() ? "E" :
decryptRadio.isSelected() ? "D" : "";
            if (filePath.isEmpty() || key.isEmpty() || choice.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields and select an operation. ", " Error ", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    File file = new File(filePath);
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    fis.close();
                    byte[] result;
                    if (choice.equals("E")) {
                        result = xorEncryptDecrypt(data, key);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(result);
                        fos.close();
                        JOptionPane.showMessageDialog(this, "File encrypted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else if (choice.equals("D")) {
                        result = xorEncryptDecrypt(data, key);
                        FileOutputStream fosDecrypted = new FileOutputStream(file);
                        fosDecrypted.write(result);
                        fosDecrypted.close();
                        JOptionPane.showMessageDialog(this, "File decrypted successfully.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid choice. Please select 'Encrypt' or 'Decrypt'.", " Error ", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(this, "Error processing file: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
                private byte[] xorEncryptDecrypt(byte[] data, String key) {
                    byte[] keyBytes = key.getBytes();
                    byte[] result = new byte[data.length];
                    for (int i = 0; i < data.length; i++) {
                        result[i] = (byte)(data[i] ^ keyBytes[i % keyBytes.length]);
                    }
                    return result;
                }
                public static void main(String[] args) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new image_2();
                        }
                    });
                }
            }