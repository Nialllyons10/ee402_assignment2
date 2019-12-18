import java.net.*;
import java.util.*;
import java.util.Random;
import java.util.Vector;
import javax.swing.SwingUtilities;
import java.io.*;

public class Client {
	
	private static int portNumber = 5050;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;

//    private static List<String> v;
    private static Vector<Double> temperatures = new Vector<Double>();
    
    
	// the constructor expects the IP address of the server - the port is fixed
    public Client(String serverIP) {
    	if (!connectToServer(serverIP)) {
    		System.out.println("XX. Failed to open socket connection to: " + serverIP);            
    	}
    }

    private boolean connectToServer(String serverIP) {
    	try { // open a new socket to the server 
    		this.socket = new Socket(serverIP,portNumber);
    		this.os = new ObjectOutputStream(this.socket.getOutputStream());
    		this.is = new ObjectInputStream(this.socket.getInputStream());
    		System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
    				+ " on port: " + this.socket.getPort());
    		System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
    				+ " and port: " + this.socket.getLocalPort());
    	} 
        catch (Exception e) {
        	System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
        	System.out.println("    Exception: " + e.toString());	
        	return false;
        }
		return true;
    }

    private void getDate() {
    	String theDateCommand = "GetDate", theDateAndTime;
    	System.out.println("01. -> Sending Command (" + theDateCommand + ") to the server...");
    	this.send(theDateCommand);
    	try{
    		theDateAndTime = (String) receive();
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + theDateAndTime);
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}
    	System.out.println("06. -- Disconnected from Server.");
    }
    
    private void getTemp() {
    	String theTemp = "GetTemp", theDateAndtimeTemp;
    	System.out.println("01. -> Sending Command get temp (" + theTemp + ") to the server...");
    	this.send(theTemp);
    	try{
    		theDateAndtimeTemp = (String) receiveTemp();
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + theDateAndtimeTemp);
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}
    	System.out.println("06. -- Disconnected from Server.");
    }
    
    private void getReadings() {
    	String theReadings = "GetReadings", theReadingData;
    	System.out.println("01. -> Sending Command get temp (" + theReadings + ") to the server...");
    	this.send(theReadings);
    	try{
    		theReadingData = (String) receive();
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + theReadingData);
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}
    	System.out.println("06. -- Disconnected from Server.");
    }
    
    // method to send a generic object.
    private void send(Object o) {
		try {
		    System.out.println("02. -> Sending an object...");
		    os.writeObject(o);
		    os.flush();
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Sending:" +  e.toString());
		}
    }

    // method to receive a generic object.
    private Object receiveTemp() 
    {
		Object o = null;
		try {
			System.out.println("03. -- About to receive an object...");
		    o = is.readObject();
		    System.out.println("04. <- Object received...");  
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		temperatures.add((Double) o);
		return o;
    }
    
    private Object receive() 
    {
		Object o = null;
		try {
			System.out.println("03. -- About to receive an object...");
		    o = is.readObject();
		    System.out.println("04. <- Object received...");
		    
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		return o;
    }

    public static void main(String args[]) throws InterruptedException 
    {
    	System.out.println("**. Java Client Application - EE402 OOP Module, DCU");
    	
    	if(args.length==1){
    		Client theApp = new Client(args[0]);
    		for(int i = 0; i <= 20; i++) { 
			    theApp.getDate();
			    theApp.getTemp();
			    theApp.getReadings();
			    Thread.sleep(400);
    		}
		}
    	else
    	{
    		System.out.println("Error: you must provide the address of the server");
    		System.out.println("Usage is:  java Client x.x.x.x  (e.g. java Client 192.168.7.2)");
    		System.out.println("      or:  java Client hostname (e.g. java Client localhost)");
    	}  
    	
    	
    	List<Double> temps = new ArrayList<>();
        Random random = new Random();
        int maxDataPoints = 20;
        int maxTemp = 50;
        int minTemp = 10;
        for (int i = 0; i < maxDataPoints; i++) {
        	temps.add(random.nextDouble() * (maxTemp - minTemp) + 1);
        }
        
        
    	Application app = new Application(temps);
    	SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			app.showGui(temps);
    		}
    	});
    	
    	Object max = Collections.max(temperatures); 
    	Object min = Collections.min(temperatures);
    	System.out.println("RPi last 20 read temperatures: " + temperatures + "");
    	System.out.println("RPi last 20 read temperatures maximum: " + max + "");
    	System.out.println("RPi last 20 read temperatures minimum: " + min + "");
    	
    	System.out.println("**. End of Application.");
    }
}