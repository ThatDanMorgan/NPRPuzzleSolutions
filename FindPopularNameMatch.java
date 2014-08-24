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
class FindPopularNameMatch{
    public HashMap<String,StringBuffer> map;
    /** 
     * This is just our entry point for running on the command line.
     */
    public static void main(String[] args){
        new FindPopularNameMatch();
    }

    /** 
     * Our constructor it will basically read each file until it finds a match.
     */
    public FindPopularNameMatch(){
        // Initialize the map
        this.map = new HashMap<String,StringBuffer>();
        // This flag will let us short circuit if we found a match.
        boolean found = false;
        // Now we'll read the data.
        found = this.readData("http://www.galbithink.org/names/s1950m.txt");    
        if(!found) found = this.readData("http://www.galbithink.org/names/s1960m.txt");
        if(!found) found = this.readData("http://www.galbithink.org/names/s1970m.txt");
        if(!found) found = this.readData("http://www.galbithink.org/names/s1980m.txt");
        if(!found) found = this.readData("http://www.galbithink.org/names/s1990m.txt");
        // I really hope this doesn't happen!
        if(!found){
            System.out.println("Nothing found... sad");
        }
    }
    /**
     * This will read in all the data from a URL.
     * @param String The URL String we can find the data at.
     * @returns boolean representing that we found a match and can stop searching.
     */
    public boolean readData(String in){
        try{            
            // Load URL
            URL url = new URL(in);
            // Open an input stream
            InputStream is = url.openStream();
            // Send that the a buffered reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));        
            String str = null;
            if (is!=null) {
                // For each line
                while ((str = reader.readLine()) != null) { 
                    // split the read line
                    String[] splt = str.split(",");
                    if(this.parseString(splt[0])){
                        return true;
                    }                               
                }               
            }            
        }catch(Exception e){
            // This is bad practice.  Don't do this!
            System.out.println(e);
        }

        return false;
    }    
    /**
     * This will read in all the data from a URL.
     * @param char This is the character to check if it is a variable.
     */
    public boolean isVowel(char x){
        return x == 'A' || x == 'E' || x == 'I' || x == 'O' || x == 'U';
    }

    /**
     * This ensures we only have one vowel in our set.
     * @param StringBuffer The buffer to check within.
     */
    public boolean hasOneVowel(StringBuffer buffer){
        String testString = buffer.toString();
        int count = 0;
        // So I wanted to do a regex but I couldn't get it to work.
        if(this.isVowel(buffer.charAt(0))) count ++;
        if(this.isVowel(buffer.charAt(1))) count ++;
        if(this.isVowel(buffer.charAt(2))) count ++;
        return count == 1;
    }

    /** 
     * This method parses the string and determines if it is a match worth keeping.  If 
     * this method determines that it found the answer, we print out the answer.  
     * @param String The string to parse
     * @returns boolean representing that we found a match and can stop searching.
     */
    public boolean parseString(String str) {        
        if(str.length() == 6){
            String key = str.substring(1);
            String val = str.substring(0,1);
            StringBuffer sample = this.map.get(key);
            if(sample == null){
                sample = new StringBuffer(val);
                this.map.put(key,sample);
            }else if(sample.indexOf(val) < 0){
                sample.append(val);
            }
            
            if(sample.length() == 3 && this.hasOneVowel(sample)){ 
                System.out.println(sample.charAt(0) + key);
                System.out.println(sample.charAt(1) + key);
                System.out.println(sample.charAt(2) + key); 
                return true; 
                                      
            }

            return false;
        }        
        return false;

    }
}