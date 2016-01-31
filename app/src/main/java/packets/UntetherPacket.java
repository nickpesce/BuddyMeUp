package packets;

public class UntetherPacket extends Packet {

	private static final long serialVersionUID = 4193298926167288831L;
	private String friendId;
	
	public UntetherPacket(String ip, int port, String id, String name,String friendId) {
		
		super(ip, port, id, name, Packet.UNTETHER_PACKET);
		this.friendId=friendId;
	}
	
	public String getFriendId(){
		
		return friendId;
	}

}
