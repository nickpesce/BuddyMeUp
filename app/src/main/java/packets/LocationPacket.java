package packets;
import java.io.Serializable;

/**
 * Sent from the client to the server indicating a new location
 * @author Tal
 *
 */

public class LocationPacket extends Packet implements Serializable {

	private static final long serialVersionUID = 6635532456090233256L;

	private double latitude;
	private double longitude;

	public LocationPacket(String ip, int port,String id,String name,double latitude,double longitude){
		
		super(ip,port,id,name,LOCATION_PACKET);
		this.latitude=latitude;
		this.longitude=longitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public double getLatitude(){
		
		return latitude;
	}
	
}
