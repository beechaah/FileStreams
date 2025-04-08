import javax.swing.*;
import java.io.*;

public class RandProductSearch
{
    private static final String FILE_PATH = "products.dat";

    public RandProductSearch()
    {
        JFrame frame = new JFrame("Product Search");
        JPanel panel = new JPanel();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField searchField = new JTextField(20);
        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e ->
        {
            String searchQuery = searchField.getText().toLowerCase();
            resultArea.setText("");

            try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r"))
            {
                while (raf.getFilePointer() < raf.length())
                {
                    String name = raf.readUTF().trim();
                    String desc = raf.readUTF().trim();
                    String id = raf.readUTF().trim();
                    double cost = raf.readDouble();

                    if (name.toLowerCase().contains(searchQuery))
                    {
                        resultArea.append(String.format("Name: %s | ID: %s | Cost: %.2f\n", name, id, cost));
                    }
                }
            } catch (IOException ex)
            {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        panel.add(new JLabel("Search by Name:"));
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(new JScrollPane(resultArea));

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new RandProductSearch();
    }
}