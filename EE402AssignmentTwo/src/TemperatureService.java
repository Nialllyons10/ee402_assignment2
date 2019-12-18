import java.util.*;
import java.io.*;
import java.io.Serializable;

public class TemperatureService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public TemperatureService() {
	}

	public Double getTempReading() { 
		Double tempC = this.getCurrentTempRPi();
		return tempC;
	}

	public Double getCurrentTempRPi() { 
		String tempFileName = "/sys/class/thermal/thermal_zone0/temp";
        String lineInFile = null;
        float current_temp = 0; 
        
        try {
            FileReader fileReader = new FileReader(tempFileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((lineInFile = bufferedReader.readLine()) != null) {
            	current_temp = (Integer.parseInt(lineInFile) / 1000);
            }
            bufferedReader.close();    
        }
        catch(FileNotFoundException ex) {
            System.out.println("The file cannot be opened '" + tempFileName + "'");
        }
        catch(IOException ex) {
            System.out.println("There is an error reading this '" + tempFileName + "'");
        }
  
        double current_temp1 = (double) current_temp;
		return current_temp1;
	}
	
	public void display(){
		System.out.print("Reading is " + getCurrentTempRPi() + "°C");
	}
}
