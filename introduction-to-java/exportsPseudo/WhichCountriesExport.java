/**
 * Reads a chosen CSV file of country exports and prints each country that exports coffee.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class WhichCountriesExport {
    public void listExporters(CSVParser parser, String exportOfInterest) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            if (record.get("Exports").contains(exportOfInterest)) {
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public void countryInfo(CSVParser parser, String country) {
        boolean found = false;
        for (CSVRecord record : parser) {
            if(record.get("Country").contains(country)) {
                System.out.print(record.get("Country") + ": ");
                System.out.print(record.get("Exports") + ": ");
                System.out.println(record.get("Value (dollars)"));
                found = true;
                break;
            }
        }
        if (! found) {
            System.out.println("NOT FOUND");            
        }
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        //for each row in the CSV File
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                System.out.println(record.get("Country"));
            }
        }
    }
    
    public void numberOfExporters(CSVParser parser, String exportItem) {
        int counter = 0;
        for (CSVRecord record : parser) {
            if(record.get("Exports").contains(exportItem)) {
                counter += 1;
            }
        }
        System.out.println(counter);
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record: parser) {
            String value = record.get("Value (dollars)");
            if(value.length() > amount.length()) {
                System.out.print(record.get("Country") + " ");
                System.out.println(value);
            }
        }
    }

    public void whoExportsCoffee() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        listExporters(parser, "coffee");
    }
    
    public void tester() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println("Check Country:");
        countryInfo(parser, "Nauru");
        parser = fr.getCSVParser();
        System.out.println("Check exporters:");
        listExportersTwoProducts(parser, "gold", "diamonds"); 
        parser = fr.getCSVParser(); 
        System.out.println("Number sugar exporters:");
        numberOfExporters(parser, "sugar");
        parser = fr.getCSVParser();
        System.out.println("Big Exporters:");        
        bigExporters(parser, "$999,999,999,999");
    }
}
