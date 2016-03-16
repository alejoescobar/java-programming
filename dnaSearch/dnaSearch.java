
/**
 * Write a description of dnaSearch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.File;

public class dnaSearch {
    public String findProtein(String dnaString, String genomeStart, String genomeEnd) {
        if (dnaString == dnaString.toUpperCase()) {
            genomeStart = genomeStart.toUpperCase();
            genomeEnd = genomeEnd.toUpperCase();
        } else {
            genomeStart = genomeStart.toLowerCase();
            genomeEnd = genomeEnd.toLowerCase();        
        }
        int startIndex = dnaString.indexOf(genomeStart);
        if (startIndex == -1) {
            return "";
        }
        int endIndex = dnaString.indexOf(genomeEnd);
        int genomeSize = endIndex - (startIndex + 3);
        if ( genomeSize % 3 == 0 ) {
            return dnaString.substring(startIndex, (endIndex + 3));
        } else {
            return "";
        }
    }
    public void testing() {
        //String a = "cccatggggtttaaataataataggagagagagagagagttt";
        //String ap = "atggggtttaaataataatag";
        //String a = "atgcctag";
        //String ap = "";
        String a = "ATGCCCTAG";
        String ap = "ATGCCCTAG";
        String result = findProtein(a, "atg", "tag");
        if (ap.equals(result)) {
          System.out.println("success for " + ap + " length " + ap.length());
        }
        else {
          System.out.println("mistake for input: " + a);
          System.out.println("got: " + result);
          System.out.println("not: " + ap);
    }
  }
  public void realTesting() {
      DirectoryResource dr = new DirectoryResource();
      for(File f : dr.selectedFiles()) {
          FileResource fr = new FileResource(f);
          String s = fr.asString();
          System.out.println("Read " + s.length() + " characters");
          String result = findProtein(s, "atg", "tag");
          System.out.println("found: " + result);
      }
  }
}
