package miniproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainGUI extends JFrame {
    private static final String URL = "jdbc:mysql://localhost:3306/society";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private Connection conn;

    public MainGUI() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to database", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }
        
        setTitle("Society Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1));

        addButton("Insert", e -> openInsertDialog());
        addButton("Display", e -> displayGuests());
        addButton("Update", e -> openUpdateDialog());
        addButton("Delete", e -> openDeleteDialog());
        addButton("Check Interactions", e -> openInteractionsDialog());
        addButton("Parking Slots Available", e -> displayParkingSlots());
        addButton("Check Resident Availability", e -> openResidentDialog());
        addButton("Exit", e -> System.exit(0));
    }

    // Helper function to add buttons
    private void addButton(String title, ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        add(button);
    }

    // Open Insert dialog for adding a new guest
    private void openInsertDialog() {
        JTextField guestIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField checkInField = new JTextField();
        JTextField parkingSlotField = new JTextField();

        Object[] message = {
            "Guest ID:", guestIdField,
            "Name:", nameField,
            "Contact Info:", contactField,
            "Check-In Time:", checkInField,
            "Parking Slot:", parkingSlotField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Insert Guest", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "INSERT INTO Guests (Guest_ID, Name, Contact_Info, Check_In_Time, Parking_Slots) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, Integer.parseInt(guestIdField.getText()));
                    pstmt.setString(2, nameField.getText());
                    pstmt.setInt(3, Integer.parseInt(contactField.getText()));
                    pstmt.setString(4, checkInField.getText());
                    pstmt.setString(5, parkingSlotField.getText());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Insertion successful.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error inserting guest.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Open Update dialog for updating guest details
    private void openUpdateDialog() {
        JTextField guestIdField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField checkInField = new JTextField();
        JTextField parkingSlotField = new JTextField();

        Object[] message = {
            "Guest ID:", guestIdField,
            "New Name:", nameField,
            "New Contact Info:", contactField,
            "New Check-In Time:", checkInField,
            "New Parking Slot:", parkingSlotField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Update Guest", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "UPDATE Guests SET Name = ?, Contact_Info = ?, Check_In_Time = ?, Parking_Slots = ? WHERE Guest_ID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nameField.getText());
                    pstmt.setInt(2, Integer.parseInt(contactField.getText()));
                    pstmt.setString(3, checkInField.getText());
                    pstmt.setString(4, parkingSlotField.getText());
                    pstmt.setInt(5, Integer.parseInt(guestIdField.getText()));
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Update successful.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating guest.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Open Delete dialog for deleting a guest
    private void openDeleteDialog() {
        JTextField guestIdField = new JTextField();
        Object[] message = {"Enter Guest ID to delete:", guestIdField};

        int option = JOptionPane.showConfirmDialog(this, message, "Delete Guest", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "DELETE FROM Guests WHERE Guest_ID = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, Integer.parseInt(guestIdField.getText()));
                    int rows = pstmt.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Guest deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Guest not found.");
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting guest.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Open Interactions dialog to view guest interactions
    private void openInteractionsDialog() {
        JTextField guestIdField = new JTextField();
        Object[] message = {"Enter Guest ID to view interactions:", guestIdField};

        int option = JOptionPane.showConfirmDialog(this, message, "Check Interactions", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "SELECT CountInteractions(?) AS InteractionCount";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, Integer.parseInt(guestIdField.getText()));
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            int interactionCount = rs.getInt("InteractionCount");
                            JOptionPane.showMessageDialog(this, "Total interactions for Guest ID " + guestIdField.getText() + ": " + interactionCount);
                        } else {
                            JOptionPane.showMessageDialog(this, "No interactions found for this guest.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error retrieving interactions.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Open Resident dialog to check resident availability
//    private void openResidentDialog() {
//        JTextField residentIdField = new JTextField();
//        Object[] message = {"Enter Resident ID to check availability:", residentIdField};
//
//        int option = JOptionPane.showConfirmDialog(this, message, "Check Resident Availability", JOptionPane.OK_CANCEL_OPTION);
//        if (option == JOptionPane.OK_OPTION) {
//            try {
//                String sql = "SELECT * FROM Residents WHERE Resident_ID = ? AND Is_Available = 1";
//                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                    pstmt.setInt(1, Integer.parseInt(residentIdField.getText()));
//
//                    try (ResultSet rs = pstmt.executeQuery()) {
//                        if (rs.next()) {
//                            JOptionPane.showMessageDialog(this, "Resident is available.");
//                        } else {
//                            JOptionPane.showMessageDialog(this, "Resident is not available.");
//                        }
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Error checking resident availability.", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }
    
 // Open Resident dialog to check resident availability
    private void openResidentDialog() {
        JTextField residentIdField = new JTextField();
        Object[] message = {"Enter Resident ID to check availability:", residentIdField};

        int option = JOptionPane.showConfirmDialog(this, message, "Check Resident Availability", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "SELECT * FROM Residents WHERE R_ID = ? AND Avaliability = 'Home'";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, Integer.parseInt(residentIdField.getText()));

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            JOptionPane.showMessageDialog(this, "Resident is available.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Resident is not available.");
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error checking resident availability.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    // Display Guests data
    private void displayGuests() {
        try {
            String sql = "SELECT * FROM Guests";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                StringBuilder result = new StringBuilder("Guest Details:\n");
                while (rs.next()) {
                    result.append("Guest_ID: ").append(rs.getInt("Guest_ID"))
                            .append(", Name: ").append(rs.getString("Name"))
                            .append(", Contact Info: ").append(rs.getInt("Contact_Info"))
                            .append(", Check-In Time: ").append(rs.getString("Check_In_Time"))
                            .append(", Parking Slot: ").append(rs.getString("Parking_Slots"))
                            .append("\n");
                }
                JOptionPane.showMessageDialog(this, result.toString(), "Guest Details", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying guests.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Display parking slots
    private void displayParkingSlots() {
        try {
            String sql = "SELECT * FROM Parking";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                StringBuilder result = new StringBuilder("Parking Details:\n");
                while (rs.next()) {
                    result.append("Parking Slot ID: ").append(rs.getString("Parking_Slot_ID"))
                            .append(", Is Available: ").append(rs.getInt("Is_Available"))
                            .append(", Assigned to Resident ID: ").append(rs.getInt("Assigned_To"))
                            .append("\n");
                }
                JOptionPane.showMessageDialog(this, result.toString(), "Parking Details", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error displaying parking slots.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}
