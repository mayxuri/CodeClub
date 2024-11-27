package miniproject;

import java.sql.*;
import java.util.Scanner;


public class Main {
    private static final String URL = "jdbc:mysql://localhost:3306/society";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("MENU:");
                System.out.println("1. Insert");
                System.out.println("2. Display");
                System.out.println("3. Update");
                System.out.println("4. Delete");
                System.out.println("5. Check Interactions");
                System.out.println("6. parking slots available.");
                System.out.println("7. Check resident availablity.");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        insert(conn, scanner);
                        break;
                    case 2:
                        display(conn);
                        break;
                    case 3:
                        update(conn, scanner);
                        break;
                    case 4:
                        delete(conn, scanner);
                        break;
                    case 5:
                        guestinter(conn, scanner);
                        break;
                    case 6:
                    	parking(conn);
                    	break;
                    case 7:
                    	resguest(conn,scanner);
                    	break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter guest id: ");
        int Guest_ID = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter guest name: ");
        String Name = scanner.nextLine();
        System.out.print("Enter contact no: ");
        int Contact_Info = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter in time: ");
        String Check_In_Time = scanner.nextLine();
        System.out.print("Enter parking slot: ");
        String Parking_Slots = scanner.nextLine();

        String sql = "INSERT INTO Guests (Guest_ID, Name, Contact_Info, Check_In_Time, Parking_Slots) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Guest_ID);
            pstmt.setString(2, Name);
            pstmt.setInt(3, Contact_Info);
            pstmt.setString(4, Check_In_Time);
            pstmt.setString(5, Parking_Slots);
            pstmt.executeUpdate();
            System.out.println("Insertion successful.");
        }
    }

    private static void display(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Guests";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Guest Details:");
            while (rs.next()) {
                System.out.println("Guest_ID: " + rs.getInt("Guest_ID") +
                        ", Guest Name: " + rs.getString("Name") +
                        ", Contact no: " + rs.getInt("Contact_Info") +
                        ", In time: " + rs.getString("Check_In_Time") +
                        ", Parking slot: " + rs.getString("Parking_Slots"));
            }
        }
    }

    private static void update(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter guest id to update: ");
        int Guest_ID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter CORRECT resident ID: ");
        int R_ID = scanner.nextInt();

        String sql = "UPDATE Interactions SET R_ID = ? WHERE Guest_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, R_ID);
            pstmt.setInt(2, Guest_ID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Guest for resident updated.");
            } else {
                System.out.println("Guest not found.");
            }
        }
    }

    private static void delete(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Guest_id of guest exiting: ");
        int Guest_ID = scanner.nextInt();

        String sql = "DELETE FROM Guests WHERE Guest_ID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Guest_ID);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Verified guest. Guest can exit.");
            } else {
                System.out.println("Invalid guest id.");
            }
        }
    }

    public static void guestinter(Connection conn, Scanner scanner) throws SQLException {
        System.out.println("Enter guest_id of guest whose interactions you want to view: ");
        int Guest_ID = scanner.nextInt();

        // Correct SQL query to call the function
        String sql = "SELECT CountInteractions(?) AS InteractionCount";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Guest_ID);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int interactionCount = rs.getInt("InteractionCount");
                    System.out.println("Total interactions for Guest_ID " + Guest_ID + ": " + interactionCount);
                } else {
                    System.out.println("No interactions found for this guest.");
                }
            }
        }
    }
    
    private static void parking(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Parking";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Parking Details:");
            while (rs.next()) {
                System.out.println("Parking slot: " + rs.getString("Parking_Slot_ID") +
                        "\nIs avaiable " + rs.getInt("Is_Available") +
                        ",\nAlloted to " + rs.getInt("Assigned_To"));
                System.out.println("***********************");
            }
        }
    }
    
    private static void resguest(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter guest id: ");
        int Guest_ID = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter resident id: ");
        int R_ID = scanner.nextInt();
        System.out.print("Is resident at home?: ");
        int Is_Home = scanner.nextInt();

        String sql = "INSERT INTO Interactions (Guest_ID, R_ID, Is_Home, Date_Of_Interaction) VALUES (?, ?, ?, CURDATE())";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Guest_ID);
            pstmt.setInt(2, R_ID);
            pstmt.setInt(3, Is_Home);
            pstmt.executeUpdate();
            System.out.println("Insertion successful.");
        }
    }
}


//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;


//public class Main {
//  private static final String URL = "jdbc:mysql://localhost:3306/society";
//  private static final String USER = "root";
//  private static final String PASSWORD = "password";
//
//  public static void main(String[] args) {
//      try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//           Scanner scanner = new Scanner(System.in)) {
//
//          while (true) {
//              System.out.println("MENU:");
//              System.out.println("1. Insert");
//              System.out.println("2. Display");
//              System.out.println("3. Update");
//              System.out.println("4. Delete");
//              System.out.println("5. Check Interactions");
//              System.out.println("6. parking slots available.");
//              System.out.println("7. Check resident availablity.");
//              System.out.println("0. Exit");
//              System.out.print("Enter your choice: ");
//              int choice = scanner.nextInt();
//              scanner.nextLine();
//
//              switch (choice) {
//                  case 1:
//                      insert(conn, scanner);
//                      break;
//                  case 2:
//                      display(conn);
//                      break;
//                  case 3:
//                      update(conn, scanner);
//                      break;
//                  case 4:
//                      delete(conn, scanner);
//                      break;
//                  case 5:
//                      guestinter(conn, scanner);
//                      break;
//                  case 6:
//                  	parking(conn);
//                  	break;
//                  case 7:
//                  	resguest(conn,scanner);
//                  	break;
//                  case 0:
//                      System.out.println("Exiting...");
//                      return;
//                  default:
//                      System.out.println("Invalid choice. Please try again.");
//              }
//          }
//      } catch (SQLException e) {
//          e.printStackTrace();
//      }
//  }
//
//  private static void insert(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter guest id: ");
//      int Guest_ID = scanner.nextInt();
//      scanner.nextLine();  
//      System.out.print("Enter guest name: ");
//      String Name = scanner.nextLine();
//      System.out.print("Enter contact no: ");
//      int Contact_Info = scanner.nextInt();
//      scanner.nextLine();
//      System.out.print("Enter in time: ");
//      String Check_In_Time = scanner.nextLine();
//      System.out.print("Enter parking slot: ");
//      String Parking_Slots = scanner.nextLine();
//
//      String sql = "INSERT INTO Guests (Guest_ID, Name, Contact_Info, Check_In_Time, Parking_Slots) VALUES (?, ?, ?, ?, ?)";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          pstmt.setString(2, Name);
//          pstmt.setInt(3, Contact_Info);
//          pstmt.setString(4, Check_In_Time);
//          pstmt.setString(5, Parking_Slots);
//          pstmt.executeUpdate();
//          System.out.println("Insertion successful.");
//      }
//  }
//
//  private static void display(Connection conn) throws SQLException {
//      String sql = "SELECT * FROM Guests";
//      try (Statement stmt = conn.createStatement();
//           ResultSet rs = stmt.executeQuery(sql)) {
//
//          System.out.println("Guest Details:");
//          while (rs.next()) {
//              System.out.println("Guest_ID: " + rs.getInt("Guest_ID") +
//                      ", Guest Name: " + rs.getString("Name") +
//                      ", Contact no: " + rs.getInt("Contact_Info") +
//                      ", In time: " + rs.getString("Check_In_Time") +
//                      ", Parking slot: " + rs.getString("Parking_Slots"));
//          }
//      }
//  }
//
//  private static void update(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter guest id to update: ");
//      int Guest_ID = scanner.nextInt();
//      scanner.nextLine();
//      System.out.print("Enter CORRECT resident ID: ");
//      int R_ID = scanner.nextInt();
//
//      String sql = "UPDATE Interactions SET R_ID = ? WHERE Guest_ID = ?";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, R_ID);
//          pstmt.setInt(2, Guest_ID);
//          int rows = pstmt.executeUpdate();
//          if (rows > 0) {
//              System.out.println("Guest for resident updated.");
//          } else {
//              System.out.println("Guest not found.");
//          }
//      }
//  }
//
//  private static void delete(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter Guest_id of guest exiting: ");
//      int Guest_ID = scanner.nextInt();
//
//      String sql = "DELETE FROM Guests WHERE Guest_ID = ?";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          int rows = pstmt.executeUpdate();
//          if (rows > 0) {
//              System.out.println("Verified guest. Guest can exit.");
//          } else {
//              System.out.println("Invalid guest id.");
//          }
//      }
//  }
//
//  public static void guestinter(Connection conn, Scanner scanner) throws SQLException {
//      System.out.println("Enter guest_id of guest whose interactions you want to view: ");
//      int Guest_ID = scanner.nextInt();
//
//     
//      String sql = "SELECT CountInteractions(?) AS InteractionCount";
//      
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          
//          try (ResultSet rs = pstmt.executeQuery()) {
//              if (rs.next()) {
//                  int interactionCount = rs.getInt("InteractionCount");
//                  System.out.println("Total interactions for Guest_ID " + Guest_ID + ": " + interactionCount);
//              } else {
//                  System.out.println("No interactions found for this guest.");
//              }
//          }
//      }
//  }
//  
//  private static void parking(Connection conn) throws SQLException {
//      String sql = "SELECT * FROM Parking";
//      try (Statement stmt = conn.createStatement();
//           ResultSet rs = stmt.executeQuery(sql)) {
//
//          System.out.println("Parking Details:");
//          while (rs.next()) {
//              System.out.println("Parking slot: " + rs.getString("Parking_Slot_ID") +
//                      "\nIs avaiable " + rs.getInt("Is_Available") +
//                      ",\nAlloted to " + rs.getInt("Assigned_To"));
//              System.out.println("***********************");
//          }
//      }
//  }
//  
//  private static void resguest(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter guest id: ");
//      int Guest_ID = scanner.nextInt();
//      scanner.nextLine();  
//      System.out.print("Enter resident id: ");
//      int R_ID = scanner.nextInt();
//      System.out.print("Is resident at home?: ");
//      int Is_Home = scanner.nextInt();
//
//      String sql = "INSERT INTO Interactions (Guest_ID, R_ID, Is_Home, Date_Of_Interaction) VALUES (?, ?, ?, CURDATE())";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          pstmt.setInt(2, R_ID);
//          pstmt.setInt(3, Is_Home);
//          pstmt.executeUpdate();
//          System.out.println("Insertion successful.");
//      }
//  }
//}
//
//package miniproject;
//
//import java.sql.*;
//import java.util.Scanner;
//
//public class Main {
//  private static final String URL = "jdbc:mysql://localhost:3306/society";
//  private static final String USER = "root";
//  private static final String PASSWORD = "enter your password";
//
//  public static void main(String[] args) {
//      try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
//           Scanner scanner = new Scanner(System.in)) {
//
//          while (true) {
//              System.out.println("MENU:");
//              System.out.println("1. Insert");
//              System.out.println("2. Display");
//              System.out.println("3. Update");
//              System.out.println("4. Delete");
//              System.out.println("5. Check Interactions");
//              System.out.println("6.parking slots available.");
//              System.out.println("0. Exit");
//              System.out.print("Enter your choice: ");
//              int choice = scanner.nextInt();
//              scanner.nextLine();
//
//              switch (choice) {
//                  case 1:
//                      insert(conn, scanner);
//                      break;
//                  case 2:
//                      display(conn);
//                      break;
//                  case 3:
//                      update(conn, scanner);
//                      break;
//                  case 4:
//                      delete(conn, scanner);
//                      break;
//                  case 5:
//                      guestinter(conn, scanner);
//                      break;
//                  case 6:
//                  	parking(conn);
//                  	break;
//                  case 0:
//                      System.out.println("Exiting...");
//                      return;
//                  default:
//                      System.out.println("Invalid choice. Please try again.");
//              }
//          }
//      } catch (SQLException e) {
//          e.printStackTrace();
//      }
//  }
//
//  private static void insert(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter guest id: ");
//      int Guest_ID = scanner.nextInt();
//      scanner.nextLine();  // Consume newline
//      System.out.print("Enter guest name: ");
//      String Name = scanner.nextLine();
//      System.out.print("Enter contact no: ");
//      int Contact_Info = scanner.nextInt();
//      scanner.nextLine();
//      System.out.print("Enter in time: ");
//      String Check_In_Time = scanner.nextLine();
//      System.out.print("Enter parking slot: ");
//      String Parking_Slots = scanner.nextLine();
//
//      String sql = "INSERT INTO Guests (Guest_ID, Name, Contact_Info, Check_In_Time, Parking_Slots) VALUES (?, ?, ?, ?, ?)";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          pstmt.setString(2, Name);
//          pstmt.setInt(3, Contact_Info);
//          pstmt.setString(4, Check_In_Time);
//          pstmt.setString(5, Parking_Slots);
//          pstmt.executeUpdate();
//          System.out.println("Insertion successful.");
//      }
//  }
//
//  private static void display(Connection conn) throws SQLException {
//      String sql = "SELECT * FROM Guests";
//      try (Statement stmt = conn.createStatement();
//           ResultSet rs = stmt.executeQuery(sql)) {
//
//          System.out.println("Guest Details:");
//          while (rs.next()) {
//              System.out.println("Guest_ID: " + rs.getInt("Guest_ID") +
//                      ", Guest Name: " + rs.getString("Name") +
//                      ", Contact no: " + rs.getInt("Contact_Info") +
//                      ", In time: " + rs.getString("Check_In_Time") +
//                      ", Parking slot: " + rs.getString("Parking_Slots"));
//          }
//      }
//  }
//
//  private static void update(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter guest id to update: ");
//      int Guest_ID = scanner.nextInt();
//      scanner.nextLine();
//      System.out.print("Enter new check-out time: ");
//      String Check_Out_Time = scanner.nextLine();
//
//      String sql = "UPDATE Guests SET Check_Out_Time = ? WHERE Guest_ID = ?";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setString(1, Check_Out_Time);
//          pstmt.setInt(2, Guest_ID);
//          int rows = pstmt.executeUpdate();
//          if (rows > 0) {
//              System.out.println("Check-out time updated successfully.");
//          } else {
//              System.out.println("Guest not found.");
//          }
//      }
//  }
//
//  private static void delete(Connection conn, Scanner scanner) throws SQLException {
//      System.out.print("Enter Guest_id of guest exiting: ");
//      int Guest_ID = scanner.nextInt();
//
//      String sql = "DELETE FROM Guests WHERE Guest_ID = ?";
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          int rows = pstmt.executeUpdate();
//          if (rows > 0) {
//              System.out.println("Verified guest. Guest can exit.");
//          } else {
//              System.out.println("Invalid guest id.");
//          }
//      }
//  }
//
//  public static void guestinter(Connection conn, Scanner scanner) throws SQLException {
//      System.out.println("Enter guest_id of guest whose interactions you want to view: ");
//      int Guest_ID = scanner.nextInt();
//
//      // Correct SQL query to call the function
//      String sql = "SELECT CountInteractions(?) AS InteractionCount";
//      
//      try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//          pstmt.setInt(1, Guest_ID);
//          
//          try (ResultSet rs = pstmt.executeQuery()) {
//              if (rs.next()) {
//                  int interactionCount = rs.getInt("InteractionCount");
//                  System.out.println("Total interactions for Guest_ID " + Guest_ID + ": " + interactionCount);
//              } else {
//                  System.out.println("No interactions found for this guest.");
//              }
//          }
//      }
//  }
//  
//  private static void parking(Connection conn) throws SQLException {
//      String sql = "SELECT * FROM Parking";
//      try (Statement stmt = conn.createStatement();
//           ResultSet rs = stmt.executeQuery(sql)) {
//
//          System.out.println("Parking Details:");
//          while (rs.next()) {
//              System.out.println("Parking slot: " + rs.getString("Parking_Slot_ID") +
//                      "\nIs avaiable " + rs.getInt("Is_Available") +
//                      ",\nAlloted to " + rs.getInt("Assigned_To"));
//              System.out.println("***************");
//          
//          }
//       }
//    }
//}