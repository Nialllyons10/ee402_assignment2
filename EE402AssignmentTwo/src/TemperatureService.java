import java.util.*;
import java.io.*;
import java.io.Serializable;

public class TemperatureService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private float temperature; 
	
	public TemperatureService() {
		// TODO Auto-generated constructor stub
	}

	public float getTempReading() { 
		float tempC = this.getCurrentTempRPi();
		return tempC;
	}
	
	
	public float getCurrentTempRPi() { 
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
		return current_temp;
	}
	
	public void display(){
		System.out.print("Reading is " + getCurrentTempRPi() + "°C");
	}
}
