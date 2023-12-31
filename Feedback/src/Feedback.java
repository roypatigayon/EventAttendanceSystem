import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Feedback extends JFrame {

    private JTextField eventNameField;
    private JTextField emailField;
    private JComboBox<String> ratingComboBox;
    private JTextArea commentsArea;

    public Feedback() {
        super("Event Feedback Form");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Event Name:"));
        eventNameField = new JTextField();
        inputPanel.add(eventNameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Rating:"));
        String[] ratings = {"5", "4", "3", "2", "1"};
        ratingComboBox = new JComboBox<>(ratings);
        inputPanel.add(ratingComboBox);

        inputPanel.add(new JLabel("Comments:"));
        commentsArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(commentsArea);
        inputPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFeedback();
            }
        });
        buttonPanel.add(submitButton);

        JButton returnButton = new JButton("Return to Login");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to open the login screen
                openLoginScreen();
            }
        });
        buttonPanel.add(returnButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void saveFeedback() {
        // ... (unchanged)
    }

    private void openLoginScreen() {
        // Add code to open the login screen
        // For example, you might create a Login object and set it visible
        login login = new login();
        login.setVisible(true);

        // Close the current feedback screen
        dispose();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            Feedback feedbackForm = new Feedback();
            feedbackForm.setVisible(true);
        });
    }
}