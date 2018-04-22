package main.java.utilities;

public class RESTUtility {

	public static boolean isDataNotNull(Object obj){
		boolean b = false;
		if(!(obj == null)){
			return b = true;
		}
		return b;
	}
	
	public static boolean isDataNotBlank(Object obj){
		boolean b = false;
		if(!(obj.equals(""))){
			return b = true;
		}
		return b;
	}
}
