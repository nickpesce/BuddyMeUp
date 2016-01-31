package packets;

import java.util.HashMap;

public class LoginResponsePacket extends Packet {

	private static final long serialVersionUID = 4622797224263524525L;

	public enum Result {LOGIN_SUCCESS,INCORRECT_INFO};
	
	private Result statusCode;
	private HashMap<String,String> friends;
	
	public LoginResponsePacket(String ip, int port, String id, String name, HashMap<String,String> friends,Result res) {
		
		super(ip, port, id, name,Packet.LOGIN_RESPONSE);
		statusCode=res;
		this.friends=friends;
	}

	public Result getStatusCode()
	{
		return statusCode;
	}

	public HashMap<String, String> getFriends()
	{
		return friends;
	}



}
