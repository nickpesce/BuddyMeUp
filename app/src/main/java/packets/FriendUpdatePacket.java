package packets;

import java.io.Serializable;

/**
 * This packet is sent from the server to the client periodically to update the locations 
 * of each person
 * @author Tal
 *
 */

public class FriendUpdatePacket extends Packet implements Serializable{

	private static final long serialVersionUID = -8009738421529170465L;
	private FriendLocation[] locs;
	
	public FriendUpdatePacket(String ip,int port, String id,String name, FriendLocation[] locs){
		
		super(ip,port,id,name,FRIEND_UPDATE_PACKET);
		this.locs=locs;
		
	}
	
	public FriendLocation[] getFriends(){
		
		return locs;
	}
	
	public static class FriendLocation implements Serializable{
	
		private static final long serialVersionUID = -2018482990945300394L;
		
		private String friendId;
		private double latitude;
		private double longitude;
		private double distance;
		private long lastUpdated;
		
		public FriendLocation(String friendId,double latitude,double longitude,double distance,long lastUpdated){
			
			this.friendId=friendId;
			this.latitude=latitude;
			this.longitude=longitude;
			this.distance=distance;
			this.lastUpdated=lastUpdated;
		}
		
		public String getFriendId(){
			
			return friendId;
		}
		
		public double getFriendLatitude(){
			
			return latitude;
		}
		
		public double getFriendLongitude(){
			
			return longitude;
		}
		
		public double getDistance(){
			
			return distance;
		}
	}
	
}
