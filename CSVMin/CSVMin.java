
/**
 * Write a description of CSVMin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMin {
    public CSVRecord coldestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord coldestSoFar = null;
        //For each row (current Row) in the CSV File
        for(CSVRecord record : parser) {
            coldestSoFar = getColdestOfTwo(record, coldestSoFar);
        }
        return coldestSoFar;
    }
    
    public CSVRecord getColdestOfTwo(CSVRecord current, CSVRecord coldestSoFar) {
       String tempSize = current.get("TemperatureF");
       if (tempSize.length() > 4) {
           return coldestSoFar;
       }
       if (coldestSoFar == null) {
           coldestSoFar = current;
       } else {
           double currentTemp = Double.parseDouble(current.get("TemperatureF"));
           double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
           if (currentTemp < coldestTemp) {
               coldestSoFar = current;
           }
       }
       return coldestSoFar;
    }

    public void testColdestInDay () {
        FileResource fr = new FileResource("nc_weather/2014/weather-2014-05-01.csv");  
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + coldest.get("TemperatureF") +
                   " at " + coldest.get("DateUTC"));
    }
    
    public String fileWithColdestTemperature() {
        CSVRecord coldestSoFar = null;  
        String coldestFileName = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord current = coldestHourInFile(fr.getCSVParser());
            if (coldestSoFar != getColdestOfTwo(current, coldestSoFar)) {
                coldestFileName = f.getName();
                coldestSoFar = getColdestOfTwo(current, coldestSoFar);
            }
        }
        System.out.println(coldestFileName);
        return coldestFileName;
    }
    
    public void testFileWithColdestTemperature() {
        String coldestFileName = fileWithColdestTemperature();
        FileResource fr = new FileResource("nc_weather/" + coldestFileName.substring(8, 12) + "/" + coldestFileName);
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        CSVParser parser = fr.getCSVParser();
        System.out.println("Coldest day was in file " + coldestFileName);
        System.out.println("Coldest temperature on that day was " + coldest.get("TemperatureF"));
        for (CSVRecord record : parser) {
            System.out.println(record.get("DateUTC") + ": " + record.get("TemperatureF"));
        }
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord lowestHumiditySoFar = null;
        //For each row (current Row) in the CSV File
        for(CSVRecord record : parser) {
            lowestHumiditySoFar = getLowestHumidityOfTwo(record, lowestHumiditySoFar);
        }
        return lowestHumiditySoFar;
    }
    
    public CSVRecord getLowestHumidityOfTwo(CSVRecord current, CSVRecord lowestHumiditySoFar) {
       String tempSize = (current.get("Humidity"));
       if (tempSize.equals("N/A")) {
           System.out.println("Missing piece of data");
           return lowestHumiditySoFar;
       }
       
       if (lowestHumiditySoFar == null) {
           lowestHumiditySoFar = current;
       } else {
           double currentTemp = Double.parseDouble(current.get("Humidity"));
           double coldestTemp = Double.parseDouble(lowestHumiditySoFar.get("Humidity"));
           if (currentTemp < coldestTemp && tempSize != "N/A") {
               lowestHumiditySoFar = current;
           }
       }
       return lowestHumiditySoFar;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        CSVRecord lowestHumiditySoFar = null;  
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            System.out.println(f.getName());
            CSVRecord current = lowestHumidityInFile(fr.getCSVParser());
            lowestHumiditySoFar = getLowestHumidityOfTwo(current, lowestHumiditySoFar);
        }
        return lowestHumiditySoFar;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + lowest.get("Humidity") + " at " + lowest.get("DateUTC"));
    }
    
    public double averageDataInFile(CSVParser parser, String data) {
        float totalValue = 0;
        int counter = 0;
        for (CSVRecord record : parser) {
            counter += 1;
            totalValue += Double.parseDouble(record.get(data));
        }
        return totalValue/counter;
    }
    
    public void testAverageDataInFile() {
        FileResource fr = new FileResource();
        double average = averageDataInFile(fr.getCSVParser(), "TemperatureF");
        System.out.println("Average temperature in file is " + average);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        float totalValue = 0;
        int counter = 0;
        for (CSVRecord record : parser) {
            if (Double.parseDouble(record.get("Humidity")) >= value) {
                counter += 1;
                totalValue += Double.parseDouble(record.get("TemperatureF"));
            }
        }
        if (totalValue == 0) {
            return 0;
        } else {
            return totalValue/counter;
        }
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        double average = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), 80);
        if (average == 0) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + average);
        }
    }
}
