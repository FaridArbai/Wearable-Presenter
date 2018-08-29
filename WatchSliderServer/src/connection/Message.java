package connection;

import java.util.HashMap;
import java.util.Map;


public class Message{
	private static String NEXT_CODE = "NEXT";
	private static String BACK_CODE = "BACK";
	
	public static Map<String,Type> MESSAGES_TYPE;
	
	public static enum Type {
		NEXT,
		BACK;
	}
	
	static{
		MESSAGES_TYPE = new HashMap<String,Type>();
		MESSAGES_TYPE.put(Message.NEXT_CODE, Type.NEXT);
		MESSAGES_TYPE.put(Message.BACK_CODE, Type.BACK);
	}
	
	static Type getTypeOfMessage(String message){
		Type type = Message.MESSAGES_TYPE.get(message);
		
		return type;
	}
	
}