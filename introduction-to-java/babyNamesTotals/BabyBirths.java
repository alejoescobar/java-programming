/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalBoyNames = 0;
        int totalGirls = 0;
        int totalGirlNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoyNames += 1;
                totalBoys += numBorn;
            }
            else {
                totalGirlNames += 1;
                totalGirls += numBorn;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("total names = " + (totalGirlNames + totalBoyNames));
        System.out.println("female girls = " + totalGirls);
        System.out.println("female names = " + totalGirlNames);
        System.out.println("male boys = " + totalBoys);
            System.out.println("male names = " + totalBoyNames);
    }

    public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob2014.csv");
        totalBirths(fr);
    }
    
    public int getRank(int year, String name, String gender) {
        int nameRank = 0;
        // FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv");
        FileResource fr = new FileResource("us_babynames_test/yob" + year + "short.csv");
        for (CSVRecord record : fr.getCSVParser(false)) {
            if (record.get(1).equals(gender)) {
                nameRank += 1;
                if (record.get(0).equals(name)) {
                    return nameRank;
                }
            }
        }
        return -1;
    }
    
    public void testGetRank() {
        String name = "Alejandro";
        int nameRank = getRank(1992, name, "F");
        System.out.println("The name " + name + " is ranked: " + nameRank);
    }
    
    public String getName(int year, int rank, String gender) {
        int nameRank = 0;
        String nameAtRank = "";
        // FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv"); 
        FileResource fr = new FileResource("us_babynames_test/yob" + year + "short.csv");        
        for (CSVRecord record : fr.getCSVParser(false)) {
            if (record.get(1).equals(gender)) {
                nameRank += 1;
                if (nameRank == rank) {
                    return record.get(0);
                }
            }
        }
        return "NO NAME";
    }
    
    public void testGetName() {
        String nameAtRank = getName(2014, 3, "M");
        System.out.println("The male name ranked 3 is : " + nameAtRank);
    }
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int nameRank = getRank(year, name, gender);
        String newName = getName(newYear, nameRank, gender);
        System.out.println(name + " born in " + year + " would be " + newName + " if she was born in " + newYear);
    }
    
    public int yearOfHighestRank(String name, String gender) {
        int highestRankYear = 0;
        int yearHighestRank = -1;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            String fileName = f.getName();
            int fileYear = Integer.parseInt(fileName.substring(3, 7));
            if (highestRankYear == 0) {
                highestRankYear = getRank(fileYear, name, gender);
                if (highestRankYear != -1) {
                    yearHighestRank = fileYear ;                
                }
            } else if (highestRankYear > getRank(fileYear, name, gender)) {
                highestRankYear = getRank(fileYear, name, gender);
                if (highestRankYear != -1) {
                    yearHighestRank = fileYear ;                
                }
            }
        }
        return yearHighestRank;
    }
    
    public double getAverageRank(String name, String gender) {
        double averageRank = 0.0;
        int counter = 0;
        DirectoryResource dr = new DirectoryResource();    
        for (File f : dr.selectedFiles()) {
            String fileName = f.getName();
            int fileYear = Integer.parseInt(fileName.substring(3, 7));
            averageRank += getRank(fileYear, name, gender);
            counter += 1;
        }
        return averageRank / counter;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        int birthsRankedHigher = 0;
        int targetRank = getRank(year, name, gender);
        // FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob" + year + ".csv"); 
        FileResource fr = new FileResource("us_babynames_test/yob" + year + "short.csv");
        for (CSVRecord record : fr.getCSVParser(false)) {
            int currentRank = getRank(year, record.get(0), gender);
            if (currentRank < targetRank && currentRank != -1) {
                birthsRankedHigher += Integer.parseInt(record.get(2));
            }
        }
        return birthsRankedHigher;
    }
}
