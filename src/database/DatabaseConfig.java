package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class DatabaseConfig {

	// required for other operations
	public Connection con;
	public PreparedStatement ps;
	public Statement st;
	public ResultSet rs;
	public DatabaseMetaData dbmd;
	public ResultSetMetaData rsmd;

	public DatabaseConfig(boolean b) {

	}

	public DatabaseConfig() {

		try {
			// load a driver
			Class.forName("org.postgresql.Driver");

			// Establish Connection
			con = DriverManager.getConnection("jdbc:postgresql://localhost/mca", "postgres", "1234");

		} // try
		catch (Exception e) {
			System.out.println(e);
		} // catch

	}//

}// class DatabaseConfig
