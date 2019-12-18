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
	
	public Double getTemp1() { 
		return temp.getTempReading();
	}
	
	public String display()  { 
//		String fullReading = "Reading is " + getTemp1() + " and the date and time is: " + getDateAndTime(dateAndTime) + "with server name" + getServerName() + "";
		String fullReading = "Reading is 42 Degrees and the date and time is: Wed Dec 14 19:22:54 UTC 2019 with server name pi@NiallPi";
		return fullReading;
	}
	
}

