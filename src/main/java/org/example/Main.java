package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    // Database connection
    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "root";

        return DriverManager.getConnection(url, username, password);
    }

    // CREATE TABLE
    public static void create() {
        String sql = "CREATE TABLE IF NOT EXISTS employee (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100), " +
                "age INT, " +
                "gender VARCHAR(20))";

        try (Connection con = getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate(sql);
            System.out.println("Table created successfully!");

        } catch (Exception e) {
            System.out.println("Table creation failed!");
            e.printStackTrace();
        }
    }

    // INSERT EMPLOYEE
    public static void insert(String name, int age, String gender) {
        String sql = "INSERT INTO employee (name, age, gender) VALUES (?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);

            ps.executeUpdate();
            System.out.println(name + " inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ ALL EMPLOYEES
    public static void getAll() {
        String sql = "SELECT * FROM employee";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        "ID: " + rs.getInt("id") +
                                ", Name: " + rs.getString("name") +
                                ", Age: " + rs.getInt("age") +
                                ", Gender: " + rs.getString("gender")
                );
            }

            if (!found) {
                System.out.println("No employee records found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // READ EMPLOYEE BY ID
    public static void getBy(int id) {
        String sql = "SELECT * FROM employee WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("Gender: " + rs.getString("gender"));
                } else {
                    System.out.println("No employee found with ID: " + id);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE EMPLOYEE
    public static void update(int id, String name, int age, String gender) {
        String sql = "UPDATE employee SET name = ?, age = ?, gender = ? WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("No employee found to update with ID: " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE EMPLOYEE
    public static void delete(int id) {
        String sql = "DELETE FROM employee WHERE id = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("No employee found to delete with ID: " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        // CREATE
        System.out.println("\n========== CREATE ==========");
        create();

        // INSERT
        System.out.println("\n========== INSERT ==========");
        insert("Harshitha", 21, "Female");
        insert("Nynitha", 21, "Female");
        insert("Varsha", 22, "Female");
        insert("Happy", 23, "Female");

        // READ ALL
        System.out.println("\n========== READ ALL EMPLOYEES ==========");
        getAll();

        // READ BY ID
        System.out.println("\n========== READ BY ID ==========");
        getBy(1);

        // UPDATE
        System.out.println("\n========== UPDATE ==========");
        update(1, "Harshitha", 21, "Female");

        // DELETE
        System.out.println("\n========== DELETE ==========");
        delete(4);

        // READ ALL AFTER UPDATE AND DELETE
        System.out.println("\n========== FINAL EMPLOYEE DATA ==========");
        getAll();
    }
}