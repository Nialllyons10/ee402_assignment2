import java.util.*;
import java.io.Serializable;
import java.net.*;


public class SendReadings implements Serializable{
	
	private static final long serialVersionUID = 1L;
	Vector<String> readings = new Vector<String>();
	DateTimeService dateAndTime;
	TemperatureService temp;

	public SendReadings(){
		readings = new Vector<>(20);
	}
	
	public String getServerName() throws UnknownHostException { 
		return InetAddress.getLocalHost().getHostName();
	}
	
	public String getDateAndTime(DateTimeService dateAndTime) { 
		return dateAndTime.getDateAndTime();
	}
	
	public String getTemp() { 
		return temp.getTempReading();
	}
	
	public Vector<String> buildVect() throws UnknownHostException { 
		readings.add(getServerName());
		readings.add(getDateAndTime(dateAndTime));
		readings.add(getTemp());
		return readings;
	}
	
	public String display()  { 
		String fullReading = "Reading is ";
		return fullReading;
	}
	
}