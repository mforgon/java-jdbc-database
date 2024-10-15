package com.example.jdbclesson1;

import java.util.Scanner;
import java.sql.*;

public class UniversityManagement {
    // Database Credentials
    private static final String URL = "jdbc:mysql://localhost:3306/universityDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println("Welcome to University Management System");
            System.out.println("1. Insert Student");
            System.out.println("2. View all Student");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    insertStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 5);

        scanner.close();

    }

    public static void insertStudent(Scanner scanner) {
        System.out.println("Let's insert a new student");
        System.out.println("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter DOB (yyyy-mm-dd): ");
        String dateOfBirth = scanner.nextLine();

        String sql = "INSERT INTO Student (FirstName, LastName, Email, DateOfBirth) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, dateOfBirth);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Good Job, new student inserted successfully!");

            }

        } catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
        }
    }

    public static void viewStudents() {

        String sql = "SELECT * FROM Student";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("StudentID");
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                String dateOfBirth = resultSet.getString("DateOfBirth");
                System.out.println(id + " " + firstName + " " + lastName + " " + email + " " + dateOfBirth);
            }
        }

        catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    public static void updateStudent(Scanner scanner) {
        System.out.println("Enter the ID of the student you want to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.println("Enter the new First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter the new Last Name: ");
        String lastName = scanner.nextLine();
        System.out.println("Enter the new Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter the new DOB (yyyy-mm-dd): ");
        String dateOfBirth = scanner.nextLine();

        String sql = "UPDATE Student SET FirstName = ?, LastName = ?, Email = ?, DateOfBirth = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, dateOfBirth);
            statement.setInt(5, studentId);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    public static void deleteStudent(Scanner scanner) {
        System.out.println("Enter the ID of the student you want to delete: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String sql = "DELETE FROM Student WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with the given ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }
}
