/*
 * ExtMass.java
 *
 * Created on 07 septembrie 2007, 11:14
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
import java.util.regex.PatternSyntaxException;

/**
 * The client mass command, also with extended call.
 *
 * @author Pietricica
 */
public class ExtMass 
{
    
    /** Creates a new instance of ExtMass */
    public ExtMass(ClientHandler cur_client, String recvbuf) 
    {
            StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux="";
                if(!(ST.hasMoreTokens ()))
                {
                    String Text=
                                "\nMass Command:\nBroadcasting in DSHub is very simple now.\nClassic mass:\n"+
                                "     Mass message to all users, example: \"mass all text\".\n"+
                                "Extended mass has way more advantages and can be used very efficiently with a large hub.\n"+
                                "Extended mass features:\n"+
                                "   Sending to users that match a certain regular expression:\n"+
                                "      Example: !mass \\[RO\\].* text -- this command sends mass to all users that have their nick starting with [RO]\n" +
                                "      Example: !mass .. text --this command sends mass to all users with 2 letter nicks\n"+
                                "      This type of mass command accepts just any regular expression.\n"+
                                "   Sending to users that have their fields checked:\n"+
                                "      Example: !mass share<1024 text --this command just sends text to all users with share less then 1 gigabyte.\n"+
                                "      Example: !mass sl=1 text-- this command just sends text to all users with exactly one open slot.\n"+
                                "      Example: !mass su!tcp4 text -- this command just sends text to all passive users.\n"
                               +"Extended mass has the operators >, < , =, !\n"+
                                "   And a list of possible fields : all ( to everybody ) share, sl (slots), ni (nick length),su(supports, accepts only = or !, example: !mass su=tcp4 text),hn(normal hubs count),hr(registered hub count),ho(op hub count),aw(away, 1 means normal away, 2 means extended away),rg (1- registered, 0 otherwise, registered means not op),op ( 1 -op, 0 - otherwise , op means it has key).";
                    cur_client.sendFromBot(Text);
                    return;
                }
               String extmass=ST.nextToken ();
                while (ST.hasMoreTokens ())
                 aux=aux+ST.nextToken ()+" "; //the message to broadcast;
               
                
               if(extmass.equalsIgnoreCase ("all")) 
               {
               ClientHandler temp=ClientHandler.FirstClient.NextClient;
               while(temp!=null)
               {
                   temp.sendFromBotPM (aux);
                   temp=temp.NextClient;
               }
               cur_client.sendFromBot("Broadcast sent.");    
               return;
               }
                ClientHandler temp;
                try
               {
                            
                            "".matches(extmass);
                             temp=ClientHandler.FirstClient.NextClient;

                           
                                 temp=ClientHandler.FirstClient.NextClient;
                             
                            while(temp!=null)
                            {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().matches (extmass.toLowerCase ())))
                               temp.sendFromBotPM(aux);
                            temp=temp.NextClient;

                             }
                             if(temp==null)
                             {
                                 cur_client.sendFromBot("Done with matching users...");
                                  throw new PatternSyntaxException("whatever...","bla",1);
                             }
                       
                }
                         catch(PatternSyntaxException pse)
                             {
                                 //Not a valid Regular Expression...
                             /***********ok now must pass to field [share|sl|..][>|<|=|!=][number]*/
                             
                             int mark=extmass.indexOf ('>');
                             if(mark!=-1)
                             {
                                 if(extmass.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024>Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with share > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)>Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Normal Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)>Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Reg Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)>Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Op Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)>Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with share > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()>Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with nick length > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                             }
                              mark=extmass.indexOf ('<');
                             if(mark!=-1)
                             {
                                 if(extmass.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024<Number )//&& tempz.userok==1)
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with share < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)<Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Normal Hub Count < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)<Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Op Hub Count < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(extmass.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)<Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Reg Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()<Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with nick length < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("sl"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)<Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with slots < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                             }
                              mark=extmass.indexOf ('=');
                             if(mark!=-1)
                             {
                                 if(extmass.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024==Number )//&& tempz.userok==1)
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with share = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                   return;  
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Op Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(extmass.substring (0,mark).equalsIgnoreCase("rg"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(extmass.substring (0,mark).equalsIgnoreCase("aw"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all away users.");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("op"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.OP)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all Op users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Reg Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Normal Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("su"))
                                 {
                                     String Number="";
                                     
                                      Number=extmass.substring (mark+1,extmass.length ());
                                    
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.SU.toLowerCase ().contains (Number.toLowerCase () ))//&& tempz.userok==1)
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users supporting "+Number+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with nick length = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                if(extmass.substring (0,mark).equalsIgnoreCase("sl"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)==Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with slots = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                             }
                              mark=extmass.indexOf ('!');
                             if(mark!=-1)
                             {
                                    
                                 if(extmass.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024!=Number )//&& tempz.userok==1)
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with share not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("rg"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all not registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("aw"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.AW)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all not away users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("op"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all not registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Op Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Reg Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with Normal Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(extmass.substring (0,mark).equalsIgnoreCase("su"))
                                 {
                                     String Number="";
                                     
                                      Number=extmass.substring (mark+1,extmass.length ());
                                    
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(!tempz.SU.toLowerCase ().contains (Number.toLowerCase () ))//&& tempz.userok==1)
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users not supporting "+Number+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(extmass.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                   
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with nick length not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                if(extmass.substring (0,mark).equalsIgnoreCase("sl"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (extmass.substring (mark+1,extmass.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)!=Number )
                                             tempz.sendFromBotPM(aux);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Sent to all users with slots not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  
                             }
                             cur_client.sendFromBot("Invalid Extended Mass ...");cur_client.sendFromBot("Done.");
                             
                             }
    }
    
}