package SCPMarket.Database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Properties;

import SCPMarket.Database.DatabaseParser;

public class AccountsParser {
	
	/*
	 * 
	 * i should go direct to the databaseparser
	 * but i feel like having the buffer for some reason
	 * so it will remain.
	 */
	
	static Properties ptProp = new Properties();
	static Properties shProp = new Properties();
	
	//constructor hopefully keeps it from being instantiated
	private AccountsParser() {
		
	}
	
	
	//methods
	public static void createAccount(String id) {
		//starting balance is 10k
		DatabaseParser.insertNewAccount(id);
	}
	
	
	public static boolean doesAccountExistAlready(String id) {
		return DatabaseParser.doesAccountExist(id);
	}
	
	/* we are going to put the burden
	 * of actually checking if there is an
	 * account made already on the executors of
	 * these methods
	 */
	
	
	public static int getBalance(String id, int requestedValue) {
		return DatabaseParser.getPointsOrShares(id, requestedValue);
	}
	
	
	public static void changeBalance(String id, int requestedValue, int newValue) {
		//0 = points, 1 = shares
		if(requestedValue == 0) {
			DatabaseParser.updatePoints(id, newValue);
		} else {
			DatabaseParser.updateShares(id, newValue);
		}
	}
	
	
	
}
