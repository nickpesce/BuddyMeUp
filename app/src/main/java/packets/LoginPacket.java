package packets;

public class LoginPacket extends Packet {

	
	/**
	 * 
	 * A login request which on success responds with user specific data
	 * 
	 * A login request which include the ip, port, requested ID, name request, and packet type
	 * @param ip
	 * @param port
	 * @param id
	 * @param name
	 * @param packetType
	 */
	
	String password;
	
	public LoginPacket(String ip, int port, String id, String name,String password) {
		
		
		super(ip, port, id, name, Packet.LOGIN_PACKET);
		this.password=password;
		
	}
	
	public String getPassword(){
		
		return password;
	}
}
