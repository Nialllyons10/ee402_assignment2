//package assignmentTwo;
//
//import java.util.*;
//import java.io.*;
//import java.io.Serializable;
//
//public class SendTempReadings implements Serializable {
//	
//	private static final long serialVersionUID = 1L;
//	private float value; 
//	
//	public SendTempReadings() { 
//		this.value = value;
//	}
//	
//	public float getTempReading() { 
//		String fileName = "/sys/class/thermal/thermal_zone0/temp";
//        String line = null;
//        float tempC = 0;
//        try {
//            FileReader fileReader = new FileReader(fileName);
//
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            while((line = bufferedReader.readLine()) != null) {
//                tempC = (Integer.parseInt(line) / 1000);
//                System.out.println("Temp °C: " + tempC);
//            }
//            bufferedReader.close();
//            return tempC; 
//            
//        }
//        catch(FileNotFoundException ex) {
//            System.out.println("Unable to open file '" + fileName + "'");
//        }
//        catch(IOException ex) {
//            System.out.println("Error reading file '" + fileName + "'");
//        }
//	}
//}
