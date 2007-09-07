/*
 * ExtKick.java
 *
 * Created on 07 septembrie 2007, 11:20
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
 *
 * @author Pietricica
 */
public class ExtKick 
{
    
    /** Creates a new instance of ExtKick */
    public ExtKick(ClientHandler cur_client,String recvbuf) 
    {
         if(recvbuf.equalsIgnoreCase ("kick"))
                    {
                        
                                String Text=
                                "\nKick Command:\nKicking in DSHub is very simple now.\nClassic kick:\n"+
                                "     kicks just a user out in flames, with a kick_time temporary ban, default 5 minutes.\n"+
                                "Extended kick has way more advantages and can be used very efficiently with a large hub.\n"+
                                "Extended kick features:\n"+
                                "   Kicking users that match a certain regular expression:\n"+
                                "      Example: !kick \\[RO\\].* -- this command kicks all users that have their nick starting with [RO]\n" +
                                "      Example: !kick .. --this command kicks all users with 2 letter nicks\n"+
                                "      This type of kick accepts just any regular expression.\n"+
                                "   Kicking users that have their fields checked:\n"+
                                "      Example: !kick share<1024 --this command just kicks all users with share less then 1 gigabyte.\n"+
                                "      Example: !kick sl=1 -- this command kicks all users with exactly one open slot.\n"+
                                        "      Example: !kick su!tcp4 -- this command kicks all passive users.\n"
                               +"Extended kick has the operators >, < , =, !\n"+
                                "   And a list of possible fields : share, sl (slots), ni (nick length),su(supports, accepts only = or !, example: !kick su=tcp4),hn(normal hubs count),hr(registered hub count),ho(op hub count),aw(away, 1 means normal away, 2 means extended away),rg (1- registered, 0 otherwise, registered means not op),op ( 1 -op, 0 - otherwise , op means it has key).";
                               cur_client.sendFromBot(Text );
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                String aux=ST.nextToken ();
                
                aux=ADC.retADCStr(aux);
                
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                ClientHandler tempyprev=ClientHandler.FirstClient;
                String kickmsg="";
                    while(ST.hasMoreTokens ())
                        kickmsg=kickmsg+ST.nextToken ()+" ";
                    
                    if(!kickmsg.equals(""))
                   // kickmsg=kickmsg.substring (0,kickmsg.length ()-1);
                   // else
                        kickmsg=kickmsg.substring (0,kickmsg.length ()-1);
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            tempyprev=tempyprev.NextClient;
                        }
               if(temp==null)
               {
                            cur_client.sendFromBot("No such user online. Parsing to Extended Kick...");
                            /***************extended kick**********************/
                        try
                        {
                           //aux=aux.replaceAll ("\\\\\\\\","\\\\");
                           // System.out.println (aux);
                            "".matches(aux);
                             temp=ClientHandler.FirstClient.NextClient;
                             tempyprev=ClientHandler.FirstClient;
                            while(temp!=null)
                            {
                                 temp=ClientHandler.FirstClient.NextClient;
                             tempyprev=ClientHandler.FirstClient;
                            while(temp!=null)
                            {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().matches (aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            tempyprev=tempyprev.NextClient;
                             }
                             if(temp==null)
                             {
                                 cur_client.sendFromBot("Done with matching users...");
                                  throw new PatternSyntaxException("whatever...","bla",1);
                             }
                           
                            
                          temp.kickMeOut (cur_client,kickmsg,3);
                    
                    
                         
                           
                           
              }
                            
                            }
                         catch(PatternSyntaxException pse)
                             {
                                 //cur_client.sendFromBot("Not a valid Regular Expression...");
                             /***********ok now must pass to field [share|sl|..][>|<|=|!=][number]*/
                             
                             int mark=aux.indexOf ('>');
                             if(mark!=-1)
                             {
                                 if(aux.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)>Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with share > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)>Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Normal Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)>Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Reg Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)>Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Op Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)>Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with share > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()>Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with nick length > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                             }
                              mark=aux.indexOf ('<');
                             if(mark!=-1)
                             {
                                 if(aux.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024<Number )//&& tempz.userok==1)
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with share < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)<Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Normal Hub Count < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)<Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Op Hub Count < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(aux.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)<Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Reg Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()<Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with nick length < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("sl"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)<Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with slots < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                             }
                              mark=aux.indexOf ('=');
                             if(mark!=-1)
                             {
                                 if(aux.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024==Number )//&& tempz.userok==1)
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with share = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                   return;  
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Op Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(aux.substring (0,mark).equalsIgnoreCase("rg"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(aux.substring (0,mark).equalsIgnoreCase("aw"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all away users.");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("op"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.OP)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all Op users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Reg Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Normal Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("su"))
                                 {
                                     String Number="";
                                     
                                      Number=aux.substring (mark+1,aux.length ());
                                    
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.SU.toLowerCase ().contains (Number.toLowerCase () ))//&& tempz.userok==1)
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users supporting "+Number+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with nick length = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                if(aux.substring (0,mark).equalsIgnoreCase("sl"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)==Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with slots = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                             }
                              mark=aux.indexOf ('!');
                             if(mark!=-1)
                             {
                                    
                                 if(aux.substring (0,mark).equalsIgnoreCase("share"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024!=Number )//&& tempz.userok==1)
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with share not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("rg"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all not registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("aw"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.AW)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all not away users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("op"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all not registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ho"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Op Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hr"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Reg Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("hn"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with Normal Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(aux.substring (0,mark).equalsIgnoreCase("su"))
                                 {
                                     String Number="";
                                     
                                      Number=aux.substring (mark+1,aux.length ());
                                    
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(!tempz.SU.toLowerCase ().contains (Number.toLowerCase () ))//&& tempz.userok==1)
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users not supporting "+Number+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                     return;
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("ni"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with nick length not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                if(aux.substring (0,mark).equalsIgnoreCase("sl"))
                                 {
                                     long Number=0;
                                     try
                                     {
                                      Number=Long.parseLong (aux.substring (mark+1,aux.length ()));
                                     }
                                     catch(NumberFormatException nfe)
                                     {
                                         cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)!=Number )
                                             tempz.kickMeOut (cur_client,kickmsg,3);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Kicked all users with slots not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  
                             }
                             cur_client.sendFromBot("Invalid Extended Kick ...");cur_client.sendFromBot("Done.");
                             
                             }
                            /*****************extended kick*******************/
               }
               else if(temp.reg.isreg && Vars.kick_ops==0)
                         cur_client.sendFromBot("Can't kick other ops.");
                else
                  {
                    //actual kicking.
                    
                    temp.kickMeOut (cur_client,kickmsg,3);
                    
                    
                    
                }
    }
    
}
