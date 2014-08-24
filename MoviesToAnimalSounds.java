import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.HashMap;
import java.net.URL;


/**
 * This is a class representing a solution to Wil Shortz NPR puzzle from
 * July 27th, 2014. 
 * 
 */
class MoviesToAnimalSounds{
    private static final String MovieCSV = "dvd_csv.txt";
    public HashMap<String,StringBuffer> map;
    /** 
     * This is just our entry point for running on the command line.
     */
    public static void main(String[] args){
        new MoviesToAnimalSounds();
    }

    /** 
     * Our constructor it will basically read each file until it finds a match.
     */
    public MoviesToAnimalSounds(){
        // Initialize the map
        this.map = new HashMap<String,StringBuffer>();
        // This flag will let us short circuit if we found a match.
        
    }
    /**
     * This will read in all the data from a URL.
     * @param String The URL String we can find the data at.
     * @returns boolean representing that we found a match and can stop searching.
     */
    public boolean readMovieFile(){
        try{            
            BufferedReader br = null;
        
            br = new BufferedReader(new FileReader(this.MovieCSV));
            String line = br.readLine();
            while (line != null) {                
                String[] splt = line.split(",");
                // Regex found on Stack Overflow
                
                (?:(?<=")([^"]*)(?="))|(?<=,|^)([^,]*)(?=,|$)

                this.map.put("a" + splt[0],splt[1]);
                // System.out.println(line + " = " + splt[0] + " | " + splt[1]);

                line = br.readLine();
            }
        
            br.close();          
        }catch(Exception e){
            // This is bad practice.  Don't do this!
            System.out.println(e);
        }

        return false;
    }    
}