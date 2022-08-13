package SCPMarket;

public class MiscMethods {
	
	
	
	
	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
}
