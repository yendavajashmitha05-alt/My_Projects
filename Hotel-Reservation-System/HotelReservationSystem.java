package com.project;

import java.sql.*;
import java.util.Scanner;

public class HotelReservationSystem {

    static final String URL = "jdbc:mysql://localhost:3306/hotel_db";
    static final String USERNAME = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    static void reserveRoom(Connection con, Scanner sc) throws SQLException {

        System.out.print("Enter guest name: ");
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int room = Integer.parseInt(sc.nextLine());

        System.out.print("Enter contact number: ");
        String contact = sc.nextLine();

        String sql = "INSERT INTO reservations " +
                "(guest_name, room_number, contact_number) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, room);
            ps.setString(3, contact);

            ps.executeUpdate();
            System.out.println("Room reserved successfully!");
        }
    }

    static void viewReservations(Connection con) throws SQLException {

        String sql = "SELECT * FROM reservations";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("Reservation ID: " + rs.getInt("reservation_id"));
                System.out.println("Guest Name: " + rs.getString("guest_name"));
                System.out.println("Room Number: " + rs.getInt("room_number"));
                System.out.println("Contact: " + rs.getString("contact_number"));
                System.out.println("Date: " + rs.getTimestamp("reservation_date"));
                System.out.println("-----------------------------");
            }
        }
    }

    static void getRoomNumber(Connection con, Scanner sc) throws SQLException {

        System.out.print("Enter reservation ID: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "SELECT room_number FROM reservations WHERE reservation_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Room Number: " + rs.getInt("room_number"));
                } else {
                    System.out.println("Reservation not found!");
                }
            }
        }
    }

    static boolean reservationExists(Connection con, int id) throws SQLException {

        String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    static void updateReservation(Connection con, Scanner sc) throws SQLException {

        System.out.print("Enter reservation ID: ");
        int id = Integer.parseInt(sc.nextLine());

        if (!reservationExists(con, id)) {
            System.out.println("Reservation not found!");
            return;
        }

        System.out.print("Enter new guest name: ");
        String name = sc.nextLine();

        System.out.print("Enter new room number: ");
        int room = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new contact number: ");
        String contact = sc.nextLine();

        String sql = "UPDATE reservations SET guest_name = ?, " +
                "room_number = ?, contact_number = ? WHERE reservation_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, room);
            ps.setString(3, contact);
            ps.setInt(4, id);

            ps.executeUpdate();
            System.out.println("Reservation updated successfully!");
        }
    }

    static void deleteReservation(Connection con, Scanner sc) throws SQLException {

        System.out.print("Enter reservation ID: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM reservations WHERE reservation_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Reservation not found!");
            }
        }
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in);
             Connection con = getConnection()) {

            while (true) {

                System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
                System.out.println("1. Reserve Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Search Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");

                int choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        reserveRoom(con, sc);
                        break;

                    case 2:
                        viewReservations(con);
                        break;

                    case 3:
                        getRoomNumber(con, sc);
                        break;

                    case 4:
                        updateReservation(con, sc);
                        break;

                    case 5:
                        deleteReservation(con, sc);
                        break;

                    case 6:
                        System.out.println("Thank you!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
}
