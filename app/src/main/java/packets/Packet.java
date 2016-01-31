package packets;

import java.io.Serializable;

/**
 * The base packet that each datagram will extend when communicating between the computer and server
 *
 */

public abstract class Packet implements Serializable{

	public static final int FRIEND_UPDATE_PACKET = 0, LOCATION_PACKET=1,PAIR_REQUEST_PACKET=2,PAIR_RESPONSE_PACKET=3,LOGIN_PACKET=4,LOGIN_RESPONSE=5,NEW_USER_PACKET=6;
	
	private static final long serialVersionUID = -2191909857648827237L;
	
	private String ip;
	private int port;
	private String id;
	private String name;
	private int packetType;
	
	/**
	 * Constructor with all essential information
	 * @param ip
	 * @param port
	 * @param id
	 * @param name
	 */
	public Packet(String ip,int port, String id,String name, int packetType){
		
		this.ip=ip;
		this.port=port;
		this.id=id;
		this.name=name;
		this.packetType=packetType;
	}
	
	public String getIp(){
		
		return ip;
	}
	
	public int getPort(){
		
		return port;
	}
	
	public String getId(){
		
		return id;
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getPacketType(){
		
		return packetType;
	}
	
	public void setPort(int port){
		
		this.port=port;
	}
	
	public void setIp (String ip){
		
		this.ip=ip;
	}
}
