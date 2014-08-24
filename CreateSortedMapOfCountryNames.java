import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import java.util.Arrays;

/**
 * This is a class representing a solution to Wil Shortz NPR puzzle from
 * July 27th, 2014. 
 */
class CreateSortedMapOfCountryNames{
    public static final String TextFile = "http://www.textfixer.com/resources/dropdowns/country-list-array.txt";    
    private ArrayList<String> list;
    /** 
     * This is just our entry point for running on the command line.
     */
    public static void main(String[] args){
        new CreateSortedMapOfCountryNames();
    }

    /** 
     * Our constructor it will basically read each file until it finds a match.
     */
    public CreateSortedMapOfCountryNames(){
        this.list = new ArrayList<String>();
        this.readData();                        
        this.writeCountries();
    }

    public void writeCountries(){
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream("countries_map.txt"), "utf-8"));            
            for(String item : this.list){
                writer.write(item);
                writer.write("\n");
            }
        } catch (IOException ex) {
          // report
        } finally {
           try {writer.close();} catch (Exception ex) {}
        }
        
    }

    /**
     * This will read in all the data from a URL.
     * @param String The URL String we can find the data at.
     * @returns boolean representing that we found a match and can stop searching.
     */
    public void readData(){
        try{            
            // Load URL
            URL url = new URL(this.TextFile);
            // Open an input stream
            InputStream is = url.openStream();
            // Send that the a buffered reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));        
            
            String str = null;
            if (is!=null) {
                
                while ((str = reader.readLine()) != null) {                     
                    String[] splt = str.split(";");
                    for(String country : splt){
                        // Convert to character array
                        char[] arr = country.toLowerCase().replaceAll("[^a-z]","").toCharArray();
                        Arrays.sort(arr);
                        String key = new String(arr);
                        list.add(key+"#"+country);
                    }                    
                }    
                System.out.println(this.list.toString());           
            }            
        }catch(Exception e){
            // This is bad practice.  Don't do this!
            System.out.println(e);
        }
        
    }    
}