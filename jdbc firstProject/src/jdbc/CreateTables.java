package jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTables {
	public static void main(String[] args) {
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=JDBC_FirstPractice;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";
		Connection con = null;
		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			con = DriverManager.getConnection(url, user, pass);
			Statement statement = con.createStatement();
			// create TABLE Hotels 
            String createHotelsTable = "CREATE TABLE Hotels (\n" +
                    "id INT PRIMARY KEY,\n" +
                    "hotel_name VARCHAR(255) NOT NULL,\n" +
                    "hotel_location VARCHAR(255),\n" +
                    "created_date DATE NOT NULL,\n" +
                    "updated_date DATE,\n" +
                    "is_active bit NOT NULL\n" +
                    ");";
            statement.execute(createHotelsTable);

            // create Room_Type table
            String createRoomTypeTable = "CREATE TABLE Room_Type (\n" +
                    "id INT PRIMARY KEY,\n" +
                    "room_type_name VARCHAR(255) NOT NULL,\n" +
                    "created_date DATE,\n" +
                    "updated_date DATE,\n" +
                    "is_active bit NOT NULL\n" +
                    ");";
            statement.execute(createRoomTypeTable);

            // create Rooms table
            String createRoomsTable = "CREATE TABLE Rooms (\n" +
                    "id INT PRIMARY KEY,\n" +
                    "room_type_id INT,\n" +
                    "hotel_id INT,\n" +
                    "created_date DATE NOT NULL,\n" +
                    "updated_date DATE,\n" +
                    "is_active bit NOT NULL,\n" +
                    "FOREIGN KEY (room_type_id) REFERENCES Room_Type(id),\n" +
                    "FOREIGN KEY (hotel_id) REFERENCES Hotels(id)\n" +
                    ");";
            statement.execute(createRoomsTable);

            // create Guests table
            String createGuestsTable = "CREATE TABLE Guests (\n" +
                    "id INT PRIMARY KEY,\n" +
                    "guest_name VARCHAR(255) NOT NULL,\n" +
                    "guest_phone VARCHAR(255) NOT NULL,\n" +
                    "guest_accompanying_members INT NOT NULL,\n" +
                    "guest_payment_amount INT NOT NULL,\n" +
                    "room_id INT,\n" +
                    "hotel_id INT,\n" +
                    "created_date DATE NOT NULL,\n" +
                    "updated_date DATE,\n" +
                    "is_active bit NOT NULL,\n" +
                    "FOREIGN KEY (room_id) REFERENCES Rooms(id),\n" +
                    "FOREIGN KEY (hotel_id) REFERENCES Hotels(id)\n" +
                    ");";
            statement.execute(createGuestsTable);

            // create Employee_Type table
            String createEmployeeTypeTable = "CREATE TABLE Employee_Type (\n" +
                    "id INT PRIMARY KEY,\n" +
                    "employee_type_name VARCHAR(255) NOT NULL,\n" +
                    "created_date DATE NOT NULL,\n" +
                    "updated_date DATE,\n" +
                    "is_active bit NOT NULL\n" +
                    ");";
            statement.execute(createEmployeeTypeTable);
            // CREATE TABLE Employees
            String createEmployeesTable = "CREATE TABLE Employees (" +
                    "id INT PRIMARY KEY," +
                    "employee_type_id INT," +
                    "room_id INT," +
                    "created_date DATE NOT NULL," +
                    "updated_date DATE," +
                    "is_active bit NOT NULL," +
                    "FOREIGN KEY (employee_type_id) REFERENCES Employee_Type(id)," +
                    "FOREIGN KEY (room_id) REFERENCES Hotels(id))";
            statement.execute(createEmployeesTable);

            System.out.println("Tables created successfully.");
			
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}
}
