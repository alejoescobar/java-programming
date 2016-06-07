/**
 * Write a description of dnaSearch here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.File;

public class storingAllGenes {
    public void printAll(String dnaString, String genomeStart, String genomeEnd1, String genomeEnd2, String genomeEnd3) {
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
        while (true) {
            int stopIndex = findStopIndex(dnaString, (startIndex + 3), genomeEnd1, genomeEnd2, genomeEnd3);           
            if (startIndex == -1 || stopIndex == -1) {
                break;
            }
            if ((stopIndex + 3) < dnaString.length()) { 
                System.out.println(dnaString.substring(startIndex, stopIndex + 3));
                startIndex = dnaString.indexOf(genomeStart, stopIndex + 3);
            } else {
                startIndex = dnaString.indexOf(genomeStart, startIndex + 3);
            }
        }
    }
    public StorageResource storeAll(String dnaString, String genomeStart, String genomeEnd1, String genomeEnd2, String genomeEnd3) {
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
        StorageResource geneStore = new StorageResource();
        int startIndex = dnaString.indexOf(genomeStart);
        while (true) {
            int stopIndex = findStopIndex(dnaString, (startIndex + 3), genomeEnd1, genomeEnd2, genomeEnd3);           
            if (startIndex == -1 || stopIndex == -1) {
                break;
            }
            if ((stopIndex + 3) < dnaString.length()) { 
                geneStore.add(dnaString.substring(startIndex, stopIndex + 3));
                startIndex = dnaString.indexOf(genomeStart, stopIndex + 3);
            } else {
                startIndex = dnaString.indexOf(genomeStart, startIndex + 3);
            }
        }
        return geneStore;
    }
    public int findStopIndex(String dna, int index, String genomeEnd1, String genomeEnd2, String genomeEnd3) {
        int stopCodon1 = dna.indexOf(genomeEnd1, index);
        if (stopCodon1 == -1 || (stopCodon1 - index) % 3 != 0) {
            stopCodon1 = dna.length();
        }
        int stopCodon2 = dna.indexOf(genomeEnd2, index);
        if (stopCodon2 == -1 || (stopCodon2 - index) % 3 != 0) {
            stopCodon2 = dna.length();
        }        
        int stopCodon3 = dna.indexOf(genomeEnd3, index);
        if (stopCodon3 == -1 || (stopCodon3 - index) % 3 != 0) {
            stopCodon3 = dna.length();
        }
        return Math.min(stopCodon1, Math.min(stopCodon2, stopCodon3));
    }
    public float cgRatio(String dna) {
        StorageResource storeCG = new StorageResource();
        StorageResource storeG = new StorageResource();
        dna = dna.toUpperCase();
        char c = 'a';
        for (int i = 0; i < dna.length(); i++){
            c = dna.charAt(i);        
            if (c == 'C' || c == 'G') {
                String s = "" + c;
                storeCG.add(s);
            }
        }
        return (float) storeCG.size()/(dna.length()) ;
    }
    public void printGenes(StorageResource sr) {
        int counter = 0;
        System.out.println("Strings longer than 60 characters");
        for(String gene : sr.data()) {
            if (gene.length() > 60 ) {
                counter += 1;
                System.out.println(counter + " " + gene);
            }
        }
        counter = 0;
        System.out.println("String with CG-ration higher than 0.35");
        for(String gene : sr.data()) {
            if (cgRatio(gene) > 0.35 ) {
                counter += 1;
                System.out.println(counter + " " + gene);
            }
        }
    }
    public void testStorageFinder() {
        FileResource file = new FileResource();
        StorageResource allGenes = storeAll(file.asString(), "atg", "tag", "tga", "taa");
        printGenes(allGenes);
        System.out.println("Number of genes: " + allGenes.size());        
    }
    public void testing() {
        //String a = "AATGCTAGTTTAAATCTGA";
        //String ap = "ATGCTAGTTTAAATCTGA";
        //String a = "ataaactatgttttaaatgt";
        //String ap = "atgttttaa";
        String a = "acatgataacctaag";
        String ap = "";
        // String result = printAll(a, "atg", "tag", "tga", "taa");
        //if (ap.equals(result)) {
          //System.out.println("success for " + ap + " length " + ap.length());
        //}
        //else {
          //System.out.println("mistake for input: " + a);
          //System.out.println("got: " + result);
          //System.out.println("not: " + ap);
    //}
  }
  public void realTesting() {
      DirectoryResource dr = new DirectoryResource();
      for(File f : dr.selectedFiles()) {
          FileResource fr = new FileResource(f);
          String s = fr.asString();
          System.out.println("Read " + s.length() + " characters");
          //String result = printAll(s, "atg", "tag", "tga", "taa");
          //System.out.println("found: " + result);
      }
  }
}
