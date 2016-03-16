
/**
 * Write a description of webLinksAssignment here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class webLinksAssignment {
    public void findVideo() {
        URLResource pageContent = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String line : pageContent.lines()) {
            int hasYoutube = (line.toLowerCase()).indexOf("youtube");
            if (hasYoutube == -1) {
            } else {
                int endIndex = line.indexOf("\"", hasYoutube);                
                int startIndex = line.lastIndexOf("\"", hasYoutube);
                System.out.println(line.substring((startIndex + 1), endIndex));
            }
        }
    }
}