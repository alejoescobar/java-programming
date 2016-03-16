/**
 * Write a description of dnaSearch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.File;

public class dnaSearchAssignment {
    public String findProtein(String dnaString, String genomeStart, String genomeEnd1, String genomeEnd2, String genomeEnd3) {
        if (dnaString == dnaString.toUpperCase()) {
            genomeStart = genomeStart.toUpperCase();
            genomeEnd1 = genomeEnd1.toUpperCase();
            genomeEnd2 = genomeEnd2.toUpperCase();            
            genomeEnd3 = genomeEnd3.toUpperCase();
        } else {
            genomeStart = genomeStart.toLowerCase();
            genomeEnd1 = genomeEnd1.toLowerCase();
            genomeEnd2 = genomeEnd2.toLowerCase();            
            genomeEnd3 = genomeEnd3.toLowerCase();    
        }
        int startIndex = dnaString.indexOf(genomeStart);
        if (startIndex == -1) {
            return "";
        }
        int endIndex1 = dnaString.indexOf(genomeEnd1, startIndex);
        int endIndex2 = dnaString.indexOf(genomeEnd2, startIndex);
        int endIndex3 = dnaString.indexOf(genomeEnd3, startIndex);        
        int genomeSize1 = endIndex1 - (startIndex + 3);
        int genomeSize2 = endIndex2 - (startIndex + 3); 
        int genomeSize3 = endIndex3 - (startIndex + 3);        
        if ( genomeSize1 % 3 == 0 ) {
            return dnaString.substring(startIndex, (endIndex1 + 3));
        } else if ( genomeSize2 % 3 == 0 ) {
            return dnaString.substring(startIndex, (endIndex2 + 3));            
        } else if ( genomeSize3 % 3 == 0 ) {
            return dnaString.substring(startIndex, (endIndex3 + 3));            
        } else {
            return "";
        }
    }
    public void testing() {
        //String a = "AATGCTAGTTTAAATCTGA";
        //String ap = "ATGCTAGTTTAAATCTGA";
        //String a = "ataaactatgttttaaatgt";
        //String ap = "atgttttaa";
        String a = "acatgataacctaag";
        String ap = "";
        String result = findProtein(a, "atg", "tag", "tga", "taa");
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
          String result = findProtein(s, "atg", "tag", "tga", "taa");
          System.out.println("found: " + result);
      }
  }
}
