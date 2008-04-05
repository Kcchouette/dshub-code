package dshub.plugin;
/* RSS Feed Plugin 0.01
 * Thx Pietry for the cleanup code
 */
import dshub.*;
import java.util.StringTokenizer;


 public class feed
        {
    
    public feed ( ClientHandler cur_client, String Issued_Command )
        {
            StringTokenizer ST=new StringTokenizer( Issued_Command );
            ST.nextToken();
            
            if ( ! ( ST.hasMoreTokens() ) ) 
        {
            cur_client.sendFromBot("RSS Feed Plugin\nUse !feed rss to recive feed"); 
            return; //1
        }
            String carrier = ST.nextToken();  

            if( carrier.equalsIgnoreCase("rss")) // Begining of Traceroute Code
            {
            
            cur_client.sendFromBotPM("Fetching RRS Feed... (please be patient)");
            RSSReader.getInstance().writeNews(cur_client);
                        
            }   
           
            else
                
                    {
                    cur_client.sendFromBot("Error: Unknown Switch use !feed for list of commands" );
                    return;
                    }
            
            }
    }