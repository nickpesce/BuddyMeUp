package packets;


/**
 * A packet sent from the user to server and server to user indicating a pairing request
 * @author Tal
 *
 */
public class PairRequestPacket extends Packet{

	private static final long serialVersionUID = 81583552670118854L;
	private String friendId;
	public final boolean isTether;
	
	public PairRequestPacket(String ip,int port, String id,String name, String friendId, boolean isTether){
		
		super(ip,port,id,name,PAIR_REQUEST_PACKET);

		this.friendId=friendId;
		this.isTether = isTether;
	}
	
	public String getFriendId(){
		
		return friendId;
	}
}
