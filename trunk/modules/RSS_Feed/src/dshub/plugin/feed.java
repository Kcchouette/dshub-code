package dshub.plugin;
/* RSS Feed Plugin 0.01
 * Thx Pietry for the cleanup code and the help to make this plugin kickass
 * and taking the freaking time to teach me some java :D
 * without your help this couldn't be done.
 * Thanks to Mutor for this idea
 * http://board.ptokax.ath.cx/index.php?topic=7765.0
 */
import dshub.*;
import java.util.StringTokenizer;


 public class feed
        {
    
     static String Address="http://www.adcportal.com/?mode=rss";
     
    public feed ( ClientHandler cur_client, String Issued_Command )
        {
            StringTokenizer ST=new StringTokenizer( Issued_Command );
            ST.nextToken();
            
            if ( ! ( ST.hasMoreTokens() ) ) 
        {
            cur_client.sendFromBot("Available commands: !feed <switch>\n\nAvailable Switches:\nrss" +
                    "		- Shows RSS Feed\nchange <url>	- Changes RSS Feed" +
                    "\n\nCurrent feed address is: "+Address); 
            return; //1
        }
            String carrier = ST.nextToken();  

            if( carrier.equalsIgnoreCase("rss")) // Begining of rss Code
            {
            
            cur_client.sendFromBotPM("Fetching RSS Feed from "+Address+" ... (please be patient)");
            RSSReader.getInstance().writeNews(cur_client);
                        
            }   
           
            else if( carrier.equalsIgnoreCase("change")) // Begining of change Code
            {
            if ( ! ( ST.hasMoreTokens() ) )
                   
                    { 
                    cur_client.sendFromBot( "Error: please provide URL adress" );
                    return;
                    }
               
            String input = ST.nextToken();
            
            
            
               cur_client.sendFromBot( "[RSS Feed:]Feed checkout address changed from "+Address+" to " + input+".");
                Address=input;
                PluginMain.writeRSSAddress();
            }
            else  //unknown switch           
            {
                    cur_client.sendFromBot("Error: Unknown Switch use !feed for list of commands" );
                    return;
            }
            
            
    }
 }