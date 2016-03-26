/**
 * Find the highest (hottest) temperature in a file of CSV weather data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (current Row) in the CSV File
        for(CSVRecord record : parser) {
            largestSoFar = getLargestOfTwo(record, largestSoFar);
        }
        return largestSoFar;
    }

    public void testHottestInDay () {
        FileResource fr = new FileResource("data/2015/weather-2015-01-02.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST"));
    }
    
    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord current = hottestHourInFile(fr.getCSVParser());
            largestSoFar =getLargestOfTwo(current, largestSoFar);
        }
        return largestSoFar;
    }
    
    public CSVRecord getLargestOfTwo(CSVRecord current, CSVRecord largestSoFar) {
       if (largestSoFar == null) {
           largestSoFar = current;
       } else {
           double currentTemp = Double.parseDouble(current.get("TemperatureF"));
           double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
           if (currentTemp > largestTemp) {
               largestSoFar = current;
           }
       }
       return largestSoFar;
    }
    
    public void testHottestInManyDays() {
        CSVRecord largest = hottestInManyDays();
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                            " at " + largest.get("DateUTC"));
    }
}
