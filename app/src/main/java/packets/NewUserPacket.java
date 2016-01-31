package packets;
/**
 * When a new user registers, the must put in a unique id (username) and a password and name
 * @author Tal
 *
 */
public class NewUserPacket extends Packet {

	private String password;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3714675032500815345L;

	/**
	 * 
	 * @param ip response ip
	 * @param port response port
	 * @param id requested username
	 * @param name requesteed name,
	 */
	public NewUserPacket(String ip, int port, String id, String name, String password) {
		
		super(ip, port, id, name, Packet.NEW_USER_PACKET);
		this.password=password;
	}
	
	
	public String getPassword(){
		
		return password;
	}

}
