package SCPMarket.Database;

import java.sql.*;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class DatabaseParser {
	
	
	/*
	 * 
	 * ESTABLISHING CONNECTION ------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * 
	 */
	
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:accountinfo.db";
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
	 * INSERTING------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 */
	public static void insertNewAccount(String id) {
        String query = "INSERT INTO accounts(id,points,shares) VALUES(?,?,?)";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setInt(2, 100000);
            pstmt.setInt(3, 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	
	/*
	 * 
	 * CHECKING IF AN ACCOUNT EXISTS UNDER THE ID------------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 *  mostly borrowed from: https://stackoverflow.com/questions/29640246/how-to-check-if-a-row-exist-in-the-sqlite-table-with-a-condition
	 *  
	 */
	
	public static boolean doesAccountExist(String id) {
		boolean output = true;
		try {
			Connection conn = connect();
			String query = "SELECT (count(*) > 0) FROM accounts WHERE id = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				// Only expecting a single result
				if (rs.next()) {
					boolean found = rs.getBoolean(1); // "found" column
					if (found) {
						output = true;
					} else {
						output = false;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	
	/*
	 * 
	 * GETTING POINTS OR SHARES ------------------------------------------------------------------------------------------------------------------------------------------------
	 *  0 = points, 1 = shares
	 * 
	 */	
	
	public static int getPointsOrShares(String id, int requestedValue) { 
		int output = -1;
		String sql = "SELECT * FROM accounts WHERE id = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, id);
				ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				if(requestedValue == 0) {
					output = rs.getInt("points");
				} else if(requestedValue == 1) {
					output = rs.getInt("shares");
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return output;
	}
	
	
	/*
	 * 
	 * UPDATING POOINTS AND SHARES-----------------------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * newValue: the value doing the replacing
	 * 
	 */
	//points
	public static void updatePoints(String id, int newValue) {
		String sql = "UPDATE accounts SET points = ? WHERE id = ?";
		
		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, newValue);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//shares
	public static void updateShares(String id, int newValue) {
		String sql = "UPDATE accounts SET shares = ? WHERE id = ?";
		
		try (Connection conn = connect();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setInt(1, newValue);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
