package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Hotel {
	private static Connection conn = null;

	// Function to establish database connection
	private static Connection connectToDB()  {
		try {
			String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=HotelDBMS;" + "encrypt=true;"
					+ "trustServerCertificate=true";
			String user = "sa";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public static void readFromTable() {
		int limit;
		try {
			conn = connectToDB();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM hotel");

			System.out.print("Enter the number of rows to display: ");
			limit = Integer.parseInt(System.console().readLine());

			while (rs.next() && limit > 0) {
				System.out.println("ID: " + rs.getInt("id"));
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Address: " + rs.getString("address"));
				System.out.println("Rating: " + rs.getDouble("rating"));
				System.out.println("Is Active: " + rs.getBoolean("is_active"));
				System.out.println();
				limit--;
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void getById() {
		int id;
		try {
			conn = connectToDB();
			Statement stmt = conn.createStatement();

			System.out.print("Enter the ID to search for: ");
			id = Integer.parseInt(System.console().readLine());

			ResultSet rs = stmt.executeQuery("SELECT * FROM hotel WHERE id=" + id);

			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("id"));
				System.out.println("Name: " + rs.getString("name"));
				System.out.println("Address: " + rs.getString("address"));
				System.out.println("Rating: " + rs.getDouble("rating"));
				System.out.println("Is Active: " + rs.getBoolean("is_active"));
				System.out.println();
			}
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateById() {
		int id;
		String name;
		String address;
		double rating;
		boolean is_active;

		try {
			conn = connectToDB();
			Statement stmt = conn.createStatement();

			System.out.print("Enter the ID to update: ");
			id = Integer.parseInt(System.console().readLine());

			System.out.print("Enter the new name: ");
			name = System.console().readLine();

			System.out.print("Enter the new address: ");
			address = System.console().readLine();

			System.out.print("Enter the new rating: ");
			rating = Double.parseDouble(System.console().readLine());

			System.out.print("Enter the new active status (true/false): ");
			is_active = Boolean.parseBoolean(System.console().readLine());

			String sql = "UPDATE hotel SET name='" + name + "', address='" + address + "', rating=" + rating
					+ ", is_active=" + is_active + " WHERE id=" + id;

			int rowsAffected = stmt.executeUpdate(sql);
			System.out.println(rowsAffected + " row(s) affected.");
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	public static void deleteById(int id) {
	    try {
	        Connection conn = connectToDB();
	        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM hotel WHERE id=?");
	        pstmt.setInt(1, id);
	        int rowsDeleted = pstmt.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("Deleted row with id: " + id);
	        } else {
	            System.out.println("No rows found with id: " + id);
	        }
	        conn.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

	public static void makeIsActiveFalseById(int id) {
	    try {
	        Connection conn = connectToDB();
	        PreparedStatement pstmt = conn.prepareStatement("UPDATE hotel SET is_active=false WHERE id=?");
	        pstmt.setInt(1, id);
	        int rowsUpdated = pstmt.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("Updated row with id: " + id);
	        } else {
	            System.out.println("No rows found with id: " + id);
	        }
	        conn.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

	public static void insertIntoTable(int numRows) {
	    try {
	        Connection conn = connectToDB();
	        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO hotel (name, address, phone, is_active) VALUES (?, ?, ?, ?)");
	        Random rn = new Random();
	        for (int i = 1; i <= numRows; i++) {
	            String name = "yourName" + rn.nextInt(100);
	            String address = "Address" + rn.nextInt(100);
	            String phone = "123-456-78" + rn.nextInt(10);
	            boolean isActive = true;
	            pstmt.setString(1, name);
	            pstmt.setString(2, address);
	            pstmt.setString(3, phone);
	            pstmt.setBoolean(4, isActive);
	            pstmt.executeUpdate();
	        }
	        System.out.println("Inserted " + numRows + " rows into table hotel");
	        conn.close();
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
	}

}