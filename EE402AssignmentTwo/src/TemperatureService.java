import java.util.*;
import java.io.*;
import java.io.Serializable;

public class TemperatureService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private float temperature; 
	
	public TemperatureService() {
		// TODO Auto-generated constructor stub
	}

	public String getTempReading() { 
		String tempC = this.getCurrentTempRPi();
		return tempC;
	}
	
	
	public String getCurrentTempRPi() { 
		String fileName = "/sys/class/thermal/thermal_zone0/temp";
        String line = null;
        float current_temp = 0; 
        
        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	current_temp = (Integer.parseInt(line) / 1000);
                System.out.println("Temp °C: " + current_temp);
            }
            
            bufferedReader.close();
            
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        
        String str = String.valueOf(current_temp);
		return str;
	}
	
	public void display(){
		System.out.print("Reading is " + getCurrentTempRPi() + "°C");
	}
}
