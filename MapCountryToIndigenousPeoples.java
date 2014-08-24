import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.net.URL;
import java.util.Arrays;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;



/**
 * This is a class representing a solution to Wil Shortz NPR puzzle from
 * July 27th, 2014. n
 */
class MapCountryToIndigenousPeoples{
    private static final String CountryMapText = "countries_map.txt";    
    private HashMap<String,String> map;    
    /** 
     * This is just our entry point for running on the command line.
     */
    public static void main(String[] args){
        new MapCountryToIndigenousPeoples();
    }

    /** 
     * Our constructor it will basically read each file until it finds a match.
     */
    public MapCountryToIndigenousPeoples(){      
        this.map = new HashMap<String,String>();
        try{
            this.readCountryFile();           
            
            //this.processByPeoplePage();    
            //this.processFinishingTheTaskPage();
            //this.processWikiEthnicGroups();
            this.processNativePlanetEntry();


        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void checkPeople(String in){
        String people = in.replace("people","").replace("People","");
        char [] arr = people.toLowerCase().replaceAll("[^a-z]","").toCharArray();
        Arrays.sort(arr);
        String key = new String(arr);
        String country = this.map.get(key);
        if(country != null){
            System.out.println(country + " | " + people);
        };
    }


    public void processNativePlanetEntry()  throws IOException{
        Document doc = Jsoup.connect("http://www.nativeplanet.org/indigenous/ethnicdiversity/indigenous_data_ethnic.shtml").get();
        Elements items = doc.select("a");
        for(Element ele : items){      
            String text = ele.text().replaceAll("\\(.*\\)","");            
            this.checkPeople(text);
        }       

        
    }
    
    public void processWikiEthnicGroups() throws IOException {
        
        Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/List_of_contemporary_ethnic_groups").get();
        Elements rows = doc.select("tr");
        for(Element row : rows){
            Elements columns =  row.select("td a");
            if(columns.size() > 1){
                Element ele = columns.get(0);                
                this.checkPeople(ele.text());                                
            }
        }    
    }

    public void processFinishingTheTaskPage() throws IOException {
        
        Document doc = Jsoup.connect("http://finishingthetask.com/uupgs.php?sort=Country").get();
        Elements rows = doc.select("tr");
        for(Element row : rows){
            Elements columns =  row.select("td");
            if(columns.size() > 1){
                Element ele = columns.get(2);
                String[] splt = ele.text().split(", ");
                for(String people : splt){
                    this.checkPeople(people);
                }
                
            }
        }    
    }

    public void processByPeoplePage() throws IOException {
        
        Document doc = Jsoup.connect("http://www.peoplesoftheworld.org/bypeople").get();
        Elements items = doc.select("a");
        for(Element ele : items){            
            this.checkPeople(ele.text());
        }    
    }

    public void processWikiPediaPage1() throws IOException {
        
        Document doc = Jsoup.connect("http://en.wikipedia.org/wiki/List_of_indigenous_peoples").get();
        Elements items = doc.select("li b a");
        for(Element ele : items){
            this.checkPeople(ele.text());
        }    
    }
    
    public void readCountryFile() throws IOException{        
        BufferedReader br = null;
        
            br = new BufferedReader(new FileReader(this.CountryMapText));
            String line = br.readLine();
            while (line != null) {                
                String[] splt = line.split("#");

                this.map.put("a" + splt[0],splt[1]);
                // System.out.println(line + " = " + splt[0] + " | " + splt[1]);

                line = br.readLine();
            }
        
            br.close();
        
    }
    
}