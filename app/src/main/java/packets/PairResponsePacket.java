package packets;

/**
 * Sent in response to a pairRequest attempt between two users
 * @author Tal
 *
 */

public class PairResponsePacket extends Packet{

	private static final long serialVersionUID = -6216868258486639294L;
	
	private String friendId;
	private boolean response;
	public final boolean isTether;
	
	public PairResponsePacket(String ip,int port, String id,String name, String friendId, boolean response, boolean isTether){
		
		super(ip,port,id,name,PAIR_RESPONSE_PACKET);
		this.friendId=friendId;
		this.response=response;
		this.isTether = isTether;
	}
	
	public String getFriendId(){
		
		return friendId;
	}
	
	public boolean response(){
		
		return response;
	}
}
