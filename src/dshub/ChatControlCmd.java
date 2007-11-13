/*
 * ChatControlCmd.java
 *
 * Created on 09 noiembrie 2007, 11:20
 *
 * DSHub ADC HubSoft
 * Copyright (C) 2007  Pietricica
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package dshub;

import java.util.StringTokenizer;

/**
 * An command interface to banned words.
 * 
 * @author Pietricica
 */
public class ChatControlCmd
{
    
    /** Creates a new instance of ChatControlCmd */
    public ChatControlCmd(ClientHandler cur_client,String cmd)
    {
        StringTokenizer ST=new StringTokenizer(cmd);
        ST.nextToken();
        if(!(ST.hasMoreTokens()))
        {
            String Help="\nThe chatcontrol command:\n"+
                    "Usage: chatcontrol list"+
                    "\n          -- brings up a list of chat controls and their unique ID\n"+
            "\nUsage: chatcontrol add \"regular expression\" flags [modification]"+
             
            "\n          -- adds the regular expression with corresponding flags. The expression must be enclosed in quotes.\n"+
            "The flags are : \n"        +
                    " Drop user 1\n"+" Kick user 2\n"+" Don't do anything to client 4\n"+" Hide the matching word from chat 8\n"+
    " Replace matching word with stars ( **** ) 16\n"+" Modify matching word with given modification 32\n"+
                    "As you can see, you must not select all flags, but one of each category.\n"+
                    "Example: Drop user and replace word with stars : Use flag 1+16=17\n"+
                    "Note: The modification parameter is only available for flag including 32.\n"+
             "\nUsage: chatcontrol mod ID/\"regular expression\" flags [modification]."+
                    "\n          -- mods the regular expression already listed given by string or it's unique ID. Parameters are same like on adding.\n"+
                    "\nUsage: chatcontrol del ID/\"regular expression\" "+
                    "\n          -- deletes the regular expression given by itself or by it's unique ID.";
                    ;
            cur_client.sendFromBot(Help);
            return;
        }
        String what=ST.nextToken();
        if(!(ST.hasMoreTokens()))
        if ( what.equalsIgnoreCase("list"))
        {
            String Help="\nChat Control List:"
                    
                    ;
            Help+=Main.listaBanate.List();
            
            cur_client.sendFromBot(Help);
            return;
        }
        else if(what.equalsIgnoreCase("add"))
        {
           String Help= "\nUsage: chatcontrol add \"regular expression\" flags [modification]"+
             
            "\n          -- mods the regular expression with corresponding flags. The expression must be enclosed in quotes.\n"+
            "The flags are : \n"        +
                    " Drop user 1\n"+" Kick user 2\n"+" Don't do anything to client 4\n"+" Hide the matching word from chat 8\n"+
    " Replace matching word with stars ( **** ) 16\n"+" Modify matching word with given modification 32\n"+
                    "As you can see, you must not select all flags, but one of each category.\n"+
                    "Example: Drop user and replace word with stars : Use flag 1+16=17\n"+
                    "Note: The modification parameter is only available for flag including 32.\n";
             
            cur_client.sendFromBot(Help);
            return;
        }
        else if(what.equalsIgnoreCase("mod"))
        {
           String Help= "\nUsage: chatcontrol mod \"regular expression\" flags [modification]"+
             
            "\n          -- mods the regular expression already listed given by string or it's unique ID. The expression must be enclosed in quotes.\n"+
            "The flags are : \n"        +
                    " Drop user 1\n"+" Kick user 2\n"+" Don't do anything to client 4\n"+" Hide the matching word from chat 8\n"+
    " Replace matching word with stars ( **** ) 16\n"+" Modify matching word with given modification 32\n"+
                    "As you can see, you must not select all flags, but one of each category.\n"+
                    "Example: Drop user and replace word with stars : Use flag 1+16=17\n"+
                    "Note: The modification parameter is only available for flag including 32.\n";
             
            cur_client.sendFromBot(Help);
            return;
        }
        else if(what.equalsIgnoreCase("del"))
        {
           String Help= "\nUsage: chatcontrol del ID/\"regular expression\" "+
             
           "\n          -- deletes the regular expression given by itself or by it's unique ID.";
             
            cur_client.sendFromBot(Help);
            return;
        }
        if ( what.equalsIgnoreCase("add"))
        {
            String regex=ST.nextToken();
            if(!regex.startsWith("\""))
            {
                cur_client.sendFromBot("Regular expression must be enclosed in quotes.");
                return;
            }
            
            String bla=regex;
            while(!bla.endsWith("\""))
            {
                if(!ST.hasMoreTokens())
                {
                    cur_client.sendFromBot("Regular expression must be enclosed in quotes.");
                    return;
                }
                bla=ST.nextToken();
                regex+=" "+bla;
                
            }
            regex=regex.substring(1,regex.length()-1);
            if(BanWordsList.ver_regex(regex)==false)
            {
                cur_client.sendFromBot("Invalid Regular Expression Pattern.");
                    return;
            }
            
            if(!ST.hasMoreTokens())
            {
                 cur_client.sendFromBot("Must specify the flags.");
                    return;
            }
            String x=ST.nextToken();
            if(!(x.equalsIgnoreCase("flags")))
            {
                cur_client.sendFromBot("Invalid parameter : "+x);
                    return;
            }
            if(!ST.hasMoreTokens())
            {
                 cur_client.sendFromBot("Must specify the flags.");
                    return;
            }
            String flags=ST.nextToken();
            int flag=0;
            try
            {
             flag=Integer.parseInt(flags);
            }
            catch (NumberFormatException nfe)
            {
                cur_client.sendFromBot("Invalid flags.");
                    return;
            }
            
            switch(flag)
            {
                /* 
                         static final long dropped=1;
    static final long kicked=2;
    static final long noAction=4;
    static final long hidden=8;
    static final long replaced=16;
    static final long modified=32;
    static final long allclient=7;
    static final long allword=56;*/
                case 9:
                case 17:
                case 10:
                case 18:
                case 20:
                
                    Main.listaBanate.add(regex,(long)flag,"x");
                     cur_client.sendFromBot("Successfully added.");
                    break;
                case 34:
                case 33:
                case 36:
                {
                    if(!ST.hasMoreTokens())
                    {
                            cur_client.sendFromBot("Must specify replacement string.");
                             return;
                    }
                    String repl="";
                    while(ST.hasMoreTokens())
                        repl+=ST.nextToken()+" ";
                    repl=repl.substring(0,repl.length()-1);
                    Main.listaBanate.add(regex,(long)flag,repl);
                     cur_client.sendFromBot("Successfully added.");
                    break;
                }
                default:
                    {
                cur_client.sendFromBot("Invalid flags.");
                    return;
                    }
                    
            }
            if(Main.GUIok)
            Main.GUI.refreshListaBanate();
            
            
        }
        
    }
    
}
