package SCPMarket.Market;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PriceDatabaseParser {
	
	
	
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:pricehistory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
	/*
	 * 
	 * INSERTING PRICES (LOGGING PRICE OVER TIME)
	 * 
	 */

	public static void logPrice(String datetime, int price) {
		String query = "INSERT INTO pricehistory(timestamp,price) VALUES (?,?)";
		
		try(Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, datetime);
			pstmt.setInt(2, price);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
