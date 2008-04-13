package dshub.plugin;
/* RSS Feed Plugin 0.01
 * Thx Pietry for the cleanup code
 * Thanks to Mutor for this idea
 * http://board.ptokax.ath.cx/index.php?topic=7765.0
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
            cur_client.sendFromBot("Available commands: !feed <switch>\n\nAvailable Switches:\nrss		- Shows RSS Feed\nchange <url>	- Changes RSS Feed"); 
            return; //1
        }
            String carrier = ST.nextToken();  

            if( carrier.equalsIgnoreCase("rss")) // Begining of Traceroute Code
            {
            
            cur_client.sendFromBotPM("Fetching RRS Feed... (please be patient)");
            RSSReader.getInstance().writeNews(cur_client);
                        
            }   
           
            else if( carrier.equalsIgnoreCase("change")) // Begining of Whois Code
            {
            if ( ! ( ST.hasMoreTokens() ) )
                   
                    { 
                    cur_client.sendFromBot( "Error: couldn't change URL adress" );
                    return;
                    }
               
            String input = ST.nextToken();
            
            ClientNod user = null;
            for ( ClientNod x : SimpleHandler.Users )
        {
            if( x .cur_client.NI.equalsIgnoreCase(input ))
            user=x;
             
        }
            if ( user == null ) // we did not find any user matching the nick 
            {
                cur_client.sendFromBot("Error: couldn't change URL adress" );
                return;
            }

                
                    {
                    cur_client.sendFromBot("Error: Unknown Switch use !feed for list of commands" );
                    return;
                    }
            
            }
    }
 }