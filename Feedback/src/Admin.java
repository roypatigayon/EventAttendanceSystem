import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends JFrame {

    public Admin() {
        super("Admin Panel");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea dataTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(dataTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton copyButton = new JButton("Copy Data");
        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyUserData(dataTextArea.getText());
            }
        });
        buttonPanel.add(copyButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        fetchData(dataTextArea);

        add(panel);
    }

    private void fetchData(JTextArea dataTextArea) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/feedbackk", "root", "");
            String sql = "SELECT * FROM feedbackk";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder result = new StringBuilder();
                while (resultSet.next()) {
                    result.append(resultSet.getString("event_name")).append("\t");
                    result.append(resultSet.getString("email")).append("\t");
                    result.append(resultSet.getString("rating")).append("\t");
                    result.append(resultSet.getString("comments")).append("\n");
                }

                dataTextArea.setText(result.toString());
            }
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data");
        }
    }

    private void copyUserData(String data) {
        StringSelection stringSelection = new StringSelection(data);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        JOptionPane.showMessageDialog(this, "User Data copied to clipboard!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Admin adminForm = new Admin();
            adminForm.setVisible(true);
        });
    }
}