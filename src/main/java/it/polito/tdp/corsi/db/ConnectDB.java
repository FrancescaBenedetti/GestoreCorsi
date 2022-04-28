package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	public static Connection getConnection() {
		
		String url = "jdbc:mysql://localhost:3306/iscritticorsi?user=root&password=Benedetti3005";
		
		try {
			Connection conn = DriverManager.getConnection(url);
			
			conn.close();
			return conn;
		} catch (SQLException e) {
			System.out.print(e);
			e.printStackTrace();
			return null;
		}
		
	}
	
}
