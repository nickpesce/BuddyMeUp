package packets;

public class AlertPacket extends LocationPacket {


	String friendId; //only used by application to know which friend needs help
	
	private static final long serialVersionUID = 8509007763502414861L;

	public AlertPacket(String ip, int port, String id, String name, double latitude, double longitude,String friendId) {
		
		super(ip, port, id, name, latitude, longitude);
		this.friendId=friendId;
	}
	
	public String getfriendId(){
		
		return friendId;
	}

}
