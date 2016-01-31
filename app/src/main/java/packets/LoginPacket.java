package packets;

public class LoginPacket extends Packet {

	
	/**
	 * A login request which include the ip, port, requested ID, name request, and packet type
	 * @param ip
	 * @param port
	 * @param id
	 * @param name
	 * @param packetType
	 */
	public LoginPacket(String ip, int port, String id, String name) {
		
		
		super(ip, port, id, name, Packet.LOGIN_PACKET);
		
		
	}

}
