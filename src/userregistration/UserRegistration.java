/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package userregistration;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class UserRegistration extends JFrame {

     private int authenticatedUserId = -1;
    private String authenticatedUserRole;
    private JTextField nameField, emailField, loginEmailField, eventNameField, eventLocationField, eventCapacityField;
    private JPasswordField passwordField, loginPasswordField;
    private JComboBox<String> roleComboBox;
    private JDateChooser dateChooser;
    private JSpinner timeSpinner;
    private JButton switchToEventViewButton;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public UserRegistration() {
        setTitle("User Registration and Authentication");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel loginPanel = createLoginPanel();
        JPanel registerPanel = createRegisterPanel();
        JPanel createEventPanel = createEventPanel();

        cardPanel.add(loginPanel, "Login");
        cardPanel.add(registerPanel, "Register");
        cardPanel.add(createEventPanel, "CreateEvent");

        add(cardPanel, BorderLayout.CENTER);

        JButton switchToRegisterButton = new JButton("Register");
        switchToRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Register");
            }
        });

        JButton switchToLoginButton = new JButton("Login");
        switchToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Login");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(switchToRegisterButton);
        buttonPanel.add(switchToLoginButton);

        add(buttonPanel, BorderLayout.SOUTH);

        cardLayout.show(cardPanel, "Login");

        setVisible(true);
    }

    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name:");
        panel.add(nameLabel);
        nameField = new JTextField(20);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel);
        emailField = new JTextField(20);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        panel.add(roleLabel);
        String[] roles = {"Attendee", "Organizer"};
        roleComboBox = new JComboBox<>(roles);
        panel.add(roleComboBox);

        JButton registerButton = new JButton("Register");
        panel.add(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel loginEmailLabel = new JLabel("Email:");
        panel.add(loginEmailLabel);
        loginEmailField = new JTextField(20);
        panel.add(loginEmailField);

        JLabel loginPasswordLabel = new JLabel("Password:");
        panel.add(loginPasswordLabel);
        loginPasswordField = new JPasswordField(20);
        panel.add(loginPasswordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        return panel;
    }

    private JPanel createEventPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel eventNameLabel = new JLabel("Event Name:");
        panel.add(eventNameLabel);
        eventNameField = new JTextField(20);
        panel.add(eventNameField);

        JLabel eventDateLabel = new JLabel("Event Date:");
        panel.add(eventDateLabel);
        dateChooser = new JDateChooser();
        panel.add(dateChooser);

        JLabel eventTimeLabel = new JLabel("Event Time:");
        panel.add(eventTimeLabel);
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(spinnerDateModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        panel.add(timeSpinner);

        JLabel eventLocationLabel = new JLabel("Event Location:");
        panel.add(eventLocationLabel);
        eventLocationField = new JTextField(20);
        panel.add(eventLocationField);

        JLabel eventCapacityLabel = new JLabel("Event Capacity:");
        panel.add(eventCapacityLabel);
        eventCapacityField = new JTextField(20);
        panel.add(eventCapacityField);

        JButton createEventButton = new JButton("Create Event");
        panel.add(createEventButton);
        createEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createEvent(eventNameField.getText(), dateChooser.getDate(), ((SpinnerDateModel) timeSpinner.getModel()).getDate(), eventLocationField.getText(), eventCapacityField.getText());
            }
        });
        
        switchToEventViewButton = new JButton("View Events");
    panel.add(switchToEventViewButton);
    switchToEventViewButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadOrganizerEvents();
        }
    });

        return panel;
    }
    
    private void loadOrganizerEvents() {
    // Query the database for events created by the currently authenticated organizer
    try (Connection connection = createConnection()) {
        // Replace the following query with the appropriate SQL query to retrieve organizer's events
        String sql = "SELECT * FROM events WHERE organizer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, authenticatedUserId);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Create a DefaultTableModel with column names
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Event Name");
                model.addColumn("Date");
                model.addColumn("Time");
                model.addColumn("Location");
                model.addColumn("Capacity");

                // Populate the table model with organizer's events data
                while (resultSet.next()) {
                    String eventName = resultSet.getString("name");
                    Date eventDate = resultSet.getDate("date");
                    Time eventTime = resultSet.getTime("time");
                    String eventLocation = resultSet.getString("location");
                    int eventCapacity = resultSet.getInt("capacity");

                    // Add a row to the table model
                    model.addRow(new Object[]{eventName, eventDate, eventTime, eventLocation, eventCapacity});
                }

                // Create a JTable with the populated table model
                JTable organizerEventsTable = new JTable(model);

                // Create a JScrollPane to display the table
                JScrollPane scrollPane = new JScrollPane(organizerEventsTable);

                // Add the JScrollPane to a new panel or tab for organizer's events
                JPanel organizerEventsPanel = new JPanel();
                organizerEventsPanel.add(scrollPane);

                // Add the new panel or tab to your cardPanel
                cardPanel.add(organizerEventsPanel, "OrganizerEvents");

                // Switch to the tab or panel for organizer's events
                cardLayout.show(cardPanel, "OrganizerEvents");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}

    private void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();

        // Check if the email or name already exists in the database
        if (isUserExists(email, name)) {
            JOptionPane.showMessageDialog(this, "User with the same email or name already exists!");
            return; // Exit the method without registering the user
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "")) {
            String sql = "INSERT INTO users (name, email, password, role, user_role_choice) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, selectedRole.toLowerCase()); // Convert to lowercase for consistency
                statement.setString(5, selectedRole.toLowerCase()); // Store the role choice in user_role_choice

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed!");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    


    
    private boolean isUserExists(String email, String name) {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "")) {
        String sql = "SELECT * FROM users WHERE email = ? OR name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Returns true if a user with the same email or name exists
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}
    private Connection createConnection() throws SQLException {
    String url = "jdbc:mysql://localhost:3306/user";
    String username = "root";
    String password = ""; // Replace with your database password

    return DriverManager.getConnection(url, username, password);
}


   private void loginUser() {
    String email = loginEmailField.getText();
    String password = new String(loginPasswordField.getPassword());

    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "")) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    authenticatedUserId = resultSet.getInt("id");
                    authenticatedUserRole = resultSet.getString("role");

                    if ("attendee".equalsIgnoreCase(authenticatedUserRole)) {
                        JOptionPane.showMessageDialog(this, "Attendee login successful!");
                        // Load and display events for attendees
                        loadEventsForAttendees();
                    } else if ("organizer".equalsIgnoreCase(authenticatedUserRole)) {
                        JOptionPane.showMessageDialog(this, "Organizer login successful!");
                        cardLayout.show(cardPanel, "CreateEvent");
                    } else {
                        JOptionPane.showMessageDialog(this, "Unknown role: " + authenticatedUserRole);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Login failed!");
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
   private void loadEventsForAttendees() {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "")) {
        String sql = "SELECT * FROM events";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                // Create a DefaultTableModel with column names
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Event Name");
                model.addColumn("Date");
                model.addColumn("Time");
                model.addColumn("Location");
                model.addColumn("Capacity");
                model.addColumn("Available Slots"); // Add a new column for available slots

                // Populate the table model with event data
                while (resultSet.next()) {
                    String eventName = resultSet.getString("name");
                    Date eventDate = resultSet.getDate("date");
                    Time eventTime = resultSet.getTime("time");
                    String eventLocation = resultSet.getString("location");
                    int eventCapacity = resultSet.getInt("capacity");

                    // Get the available slots for the event
                    int availableSlots = getAvailableSlots(eventName);

                    // Add a row to the table model
                    model.addRow(new Object[]{eventName, eventDate, eventTime, eventLocation, eventCapacity, availableSlots});
                }

                // Create a JTable with the populated table model
                JTable eventsTable = new JTable(model);
                eventsTable.getSelectionModel().addListSelectionListener(e -> {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = eventsTable.getSelectedRow();
                        if (selectedRow != -1) {
                            // Get the selected event name and initiate registration
                            String selectedEventName = (String) eventsTable.getValueAt(selectedRow, 0);
                            registerForEvent(selectedEventName);
                        }
                    }
                });

                // Create a JScrollPane to display the table
                JScrollPane scrollPane = new JScrollPane(eventsTable);

                // Add the JScrollPane to a new panel or tab for attendees
                JPanel eventsPanel = new JPanel();
                eventsPanel.add(scrollPane);

                // Add the new panel or tab to your cardPanel
                cardPanel.add(eventsPanel, "EventsForAttendees");

                // Switch to the tab or panel for attendees
                cardLayout.show(cardPanel, "EventsForAttendees");
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
   private int getAvailableSlots(String eventName) {
    try (Connection connection = createConnection()) {
        int eventId = getEventIdByName(eventName, connection);

        if (eventId != -1) {
            // Get the event capacity
            int eventCapacity = getEventCapacity(eventId, connection);

            // Get the count of registered attendees for the event
            int registeredAttendeesCount = getRegisteredAttendeesCount(eventId, connection);

            // Calculate and return the available slots
            return eventCapacity - registeredAttendeesCount;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    // Return -1 in case of an error or event not found
    return -1;
}

private int getEventCapacity(int eventId, Connection connection) throws SQLException {
    String sql = "SELECT capacity FROM events WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, eventId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("capacity");
            }
        }
    }
    return -1; // Return -1 if capacity is not found (error case)
}

private int getRegisteredAttendeesCount(int eventId, Connection connection) throws SQLException {
    String sql = "SELECT COUNT(*) AS attendees_count FROM event_attendees WHERE event_id = ? AND registration_status = 'Registered'";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, eventId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("attendees_count");
            }
        }
    }
    return 0; // Return 0 if no attendees are registered (available slots equal to capacity)
}
   private String determineRegistrationStatus(String eventName) {
    try (Connection connection = createConnection()) {
        int eventId = getEventIdByName(eventName, connection);

        if (eventId != -1) {
            String sql = "SELECT registration_status FROM event_attendees WHERE event_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, eventId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getString("registration_status");
                    }
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return "Unknown";
}

  private String determineWaitlistStatus(String eventName, int maxCapacity, int eventCapacity) {
    try (Connection connection = createConnection()) {
        int eventId = getEventIdByName(eventName, connection);

        if (eventId != -1) {
            String sql = "SELECT COUNT(*) AS attendees_count FROM event_attendees WHERE event_id = ? AND registration_status = 'Registered'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, eventId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int attendeesCount = resultSet.getInt("attendees_count");
                        if (attendeesCount >= eventCapacity && attendeesCount < maxCapacity) {
                            return "On waitlist";
                        } else if (attendeesCount >= maxCapacity) {
                            return "Waitlist full";
                        }
                    }
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return "Not on waitlist";
}
   
   public void registerForEvent(String eventName) {
    int confirm = JOptionPane.showConfirmDialog(this, "Do you want to register for event: " + eventName + "?", "Confirmation", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        try (Connection connection = createConnection()) {
            // Get the ID of the currently authenticated attendee
            int attendeeId = authenticatedUserId;

            // Get the ID of the selected event using its name
            int eventId = getEventIdByName(eventName, connection);

            if (eventId != -1) {
                // Check available slots before allowing registration
                int availableSlots = getAvailableSlots(eventName);

                if (availableSlots > 0) {
                    // Insert a new record in the event_attendees table
                    String sql = "INSERT INTO event_attendees (attendee_id, event_id) VALUES (?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setInt(1, attendeeId);
                        statement.setInt(2, eventId);

                        int rowsAffected = statement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Successfully registered for event: " + eventName);
                        } else {
                            JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No available slots for event: " + eventName);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Event not found. Please select a valid event.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

   private int getEventIdByName(String eventName, Connection connection) throws SQLException {
    // Retrieve the ID of the event based on its name (case-insensitive)
    String sql = "SELECT id, LOWER(name) AS lower_name FROM events WHERE LOWER(name) = ?";

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, eventName.toLowerCase());

        System.out.println("Executing SQL query: " + statement.toString()); // Debug output

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int eventId = resultSet.getInt("id");
                return eventId;
            } else {
                System.out.println("Event not found: " + eventName);
                return -1;
            }
        }
    } catch (SQLException ex) {
        // Log the error or display a user-friendly message
        System.err.println("Error retrieving event ID: " + ex.getMessage());
        return -1; // Return -1 if an error occurs
    }
}
    private void createEvent(String eventName, Date eventDate, Date eventTime, String eventLocation, String eventCapacity) {
        // Convert eventDate and eventTime to java.sql.Date and java.sql.Time
        java.sql.Date sqlDate = new java.sql.Date(eventDate.getTime());
        java.sql.Time sqlTime = new java.sql.Time(eventTime.getTime());

        // Get the currently authenticated user's ID and role
        int userId = authenticatedUserId;
        String userRole = authenticatedUserRole;

        if (userId != -1 && userRole != null) {
            // Check if the user is an organizer
            if ("organizer".equals(userRole)) {
                // Use the method to create the event
                boolean success = createEventInDatabase(userId, eventName, sqlDate, sqlTime, eventLocation, eventCapacity);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Event creation successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Event creation failed!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "You do not have the permission to create an event.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "User not authenticated.");
        }
    }
    
    private boolean createEventInDatabase(int organizerId, String eventName, java.sql.Date eventDate, java.sql.Time eventTime, String eventLocation, String eventCapacity) {
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "")) {
        String sql = "INSERT INTO events (organizer_id, name, date, time, location, capacity) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, organizerId);
            statement.setString(2, eventName);
            statement.setDate(3, eventDate);
            statement.setTime(4, eventTime);
            statement.setString(5, eventLocation);
            statement.setInt(6, Integer.parseInt(eventCapacity));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}



    public static void main(String[] args) {
        new UserRegistration();
    }
}






