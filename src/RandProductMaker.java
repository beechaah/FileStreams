import javax.swing.*;
import java.io.*;
import java.awt.*;

public class RandProductMaker
{
    private static final String FILE_PATH = "products.dat";
    private int recordCount = 0;

    public RandProductMaker()
    {
        JFrame frame = new JFrame("Product Entry");
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField nameField = new JTextField(25);
        JTextArea descField = new JTextArea(3, 40);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descField);
        JTextField idField = new JTextField(10);
        JTextField costField = new JTextField(10);
        JTextField countField = new JTextField(5);
        countField.setEditable(false);

        JButton addButton = new JButton("Add Product");

        addButton.addActionListener(e ->
        {
            try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw"))
            {
                raf.seek(raf.length());

                String name = String.format("%-35s", nameField.getText().trim());
                String desc = String.format("%-75s", descField.getText().trim());
                String id = String.format("%-6s", idField.getText().trim());
                double cost = Double.parseDouble(costField.getText());

                if (name.trim().isEmpty() || desc.trim().isEmpty() || id.trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled in!");
                    return;
                }

                raf.writeUTF(name);
                raf.writeUTF(desc);
                raf.writeUTF(id);
                raf.writeDouble(cost);

                recordCount++;
                countField.setText(String.valueOf(recordCount));


                nameField.setText("");
                descField.setText("");
                idField.setText("");
                costField.setText("");

                JOptionPane.showMessageDialog(frame, "Product added successfully!");
            } catch (IOException | NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });


        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Description:"));
        panel.add(descScrollPane);

        panel.add(new JLabel("ID:"));
        panel.add(idField);

        panel.add(new JLabel("Cost:"));
        panel.add(costField);

        panel.add(new JLabel("Records Entered:"));
        panel.add(countField);

        panel.add(new JLabel(""));
        panel.add(addButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new RandProductMaker();
    }
}