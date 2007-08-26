/*
 * CommandParser.java
 *
 * Created on 29 aprilie 2007, 11:55
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

import java.util.Date;
import java.util.StringTokenizer;
import java.net.*;
import java.util.regex.PatternSyntaxException;


/**
 *
 * @author Pietricica
 */
public class CommandParser extends Thread
{
    ClientHandler cur_client;
    String cmd;
    /**for !cmdhistory*/
    static line FirstCommand=null;
    static line LastCommand=null;
    static int size=0;
    
    public static String retNormStr(String blah)
    {
        return blah.replaceAll ("\\\\s"," ").replaceAll ("\\\\n","\n").replaceAll ("\\\\\\\\","\\\\");
    }
    public static String retADCStr(String blah)
    {
         return blah.replaceAll ("\\\\","\\\\\\\\").replaceAll (" ","\\\\s").replaceAll ("\n","\\\\n");
    }
    
    public static boolean isIP(String blah)
    {
        return blah.matches("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
        
       
    }
    public static boolean isCID(String blah)
    {
        if(blah.length ()!=39)
            return false;
        try
        {
            Base32.decode (blah);
        }
        catch(IllegalArgumentException iae)
        {
            return false;
        }
        return true;
       
    }
    
    
    /** Creates a new instance of CommandParser */
    public CommandParser (ClientHandler CH, String cmd)
    {
        cur_client=CH;
        this.cmd=cmd;
        this.setPriority (NORM_PRIORITY);
        start();
    }
    public void run()
    {
        String recvbuf=retNormStr(cmd.substring (1));
        String STR=cmd;
        String NI=cur_client.NI;
                ;
                if(!STR.substring (1).startsWith ("password"))
         if(FirstCommand==null)
        {
                 line bla;
              
             bla=new line("<"+
                    NI+"> "+
                    STR+"\n");
              
            LastCommand=bla;
            FirstCommand=LastCommand;
            size++;
        }
        
        else
        {
           line bla;
                //BMSG AAAA message
                 
             bla=new line("<"+NI+"> "+STR+"\n");
                
           
             
            LastCommand.Next=bla;
            
            size++;
            
            
            LastCommand=bla;
            
            while(size>=Vars.history_lines)
            {
                FirstCommand=FirstCommand.Next;  
                size--;
            }
           
        }
        
        if(recvbuf.toLowerCase ().equals("quit"))
        {
                Main.Server.rewriteregs();
                Main.Server.rewriteconfig();
                cur_client.sendFromBot("Closing down hub...");
                Main.PopMsg ("Hub is being shut down by "+cur_client.NI);
                try
                {
                this.sleep (500);
                }
                catch (Exception e)
                {
                    System.exit(0);
                }
                  System.exit(0);
        }
           
       
       else if(recvbuf.toLowerCase ().equals ("restart"))
            {
            cur_client.sendFromBot("Restarting.... Wait 5 seconds...");
                 Main.Server.rewriteregs();
               Main. Server.rewriteconfig();
               Main.Server.rewritebans ();
             Main.Server.restart=true;
              reg_config.First=null;
             BanList.First=null;
                 try{Main.Server.sleep (1500);}catch(Exception e) {}
             System.gc (); //calling garbage collectors
                 if(ClientHandler.FirstClient!=null)
                {
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
               
                try{
                Socket asock=new Socket("127.0.0.1",Main.Server.port);}catch(Exception e) {}
                while(temp!=null)
                {
                    //closing all sockets...
                    try
            {
                //this.sleep (100);
                temp.ClientSock.close();
            }
            catch (Exception e)
            {            }
                    temp=temp.NextClient;
                }
                 }
                try{Main.Server.sleep (1000);}catch(Exception e) {}
                Main.PopMsg ("Hub restarted by "+cur_client.NI);
             
             
            Main.Server=new HubServer();
         Main.curtime=System.currentTimeMillis();
         Main.Proppies=System.getProperties();
          }
        if(recvbuf.toLowerCase ().startsWith("password"))
        {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken ();
                nod temp=reg_config.First;
                while(!temp.CID.equals (   cur_client.ID))
                   temp=temp.Next;
                temp.Password=aux;
                cur_client.reg.Password=aux;
                
                Main.Server.rewriteregs();
                cur_client.sendFromBot("Your password is now "+aux+".");
               
        } 
        else if(recvbuf.toLowerCase ().equals("gui"))
        {
                
            if(Main.GUIok)
             if(Main.GUI.isDisplayable ()&& !Main.GUI.isShowing ())
             {
                Main.GUI.setVisible (true);
                cur_client.sendFromBot("GUI launched...");
                //GUIok=true;
                Main.PopMsg ("GUI was launched by "+cur_client.NI);
             }
             else cur_client.sendFromBot("GUI not viewable.");
        }
        else if(recvbuf.toLowerCase ().startsWith("hideme"))
        {
                    if(!cur_client.reg.HideMe)
                    {
               new Broadcast("BINF "+cur_client.SessionID+" HI1");
               cur_client.sendFromBot("You are now hidden, not appearing in userlist no more.");
               cur_client.reg.HideMe=true;
                    }
                    else
                     {
               new Broadcast("BINF "+cur_client.SessionID+" HI");
               cur_client.sendFromBot("You are now revealed, appearing again in userlist.");
               cur_client.reg.HideMe=false;
                    }
               
        } 
        else if(recvbuf.toLowerCase ().equals("listreg"))
        {
            String blah00="List :\n";
            nod n=reg_config.First;
            while(n!=null)
            {
                blah00=blah00+n.CID;
                if(n.LastNI!=null)
                    blah00=blah00+ " Last nick: "+n.LastNI+"\n";
                else
                    blah00=blah00+ " Never seen online."+"\n";
                n=n.Next;
            }
            blah00=blah00.substring (0,blah00.length ()-1);
            cur_client.sendFromBot(blah00);
        }
        else if(recvbuf.toLowerCase ().equals("listban"))
        {
            String blah00="Ban List :\n";
            ban n=BanList.First;
            while(n!=null)
            {
                if(n.bantype==1)
                blah00=blah00+n.nick;
                else if(n.bantype==2)
                 blah00=blah00+n.ip;   
                else if(n.bantype==3)
                 blah00=blah00+n.cid;
                long TL=System.currentTimeMillis ()-n.timeofban-n.time;
                if(n.banop!=null)
                    blah00=blah00+ " Banned by: "+n.banop;
                if(n.banreason!=null)
                    blah00=blah00+ " Reason: "+n.banreason;
                if(n.time==-1)
                    blah00=blah00+" Permanently";
                else if(TL<0)
                    blah00=blah00+" Time Left: "+Long.toString (-TL/1000)+" seconds.";
                else
                    blah00=blah00+" Expired";
                blah00=blah00+"\n";
                n=n.Next;
            }
            blah00=blah00.substring (0,blah00.length ()-1);
            cur_client.sendFromBot(blah00);
        }
        else if(recvbuf.toLowerCase ().startsWith("ureg"))
        {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken ();
                try
                {
                if(aux.length ()!=39)
                    throw new IllegalArgumentException();
                Base32.decode (aux);
                if(reg_config.unreg (aux))
                {
                    ClientHandler temp=ClientHandler.FirstClient.NextClient;
                     while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ID.equals(aux)))
                                break;
                            temp=temp.NextClient;
                        }
                    if(temp!=null)
                    {
                            temp.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.".replaceAll(" ","\\ "));
                            temp.sendToClient ("IQUI ABCD");
                            temp.OP="";
                            temp.HO=Integer.toString (Integer.parseInt(temp.HO)-1);
                            temp.HN=Integer.toString (Integer.parseInt(temp.HN)+1);
                            new Broadcast("BINF "+temp.SessionID+" OP"+" HO"+temp.HO+" HN"+temp.HN);
                            temp.reg=new nod();
                            cur_client.sendFromBot("User "+temp.NI+" with CID "+aux+" found, deleted.");
                    }
                    else
                        cur_client.sendFromBot("Reg deleted.");
                    Main.PopMsg (cur_client.NI+" deleted the reg "+aux);
                        
                }
                else
                    cur_client.sendFromBot("Reg not found.");
                }
                catch (IllegalArgumentException iae)
                {
                    cur_client.sendFromBot("Not a valid CID, checking for possible users...");
                      ClientHandler temp=ClientHandler.FirstClient.NextClient;
                        while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                            cur_client.sendFromBot("No such client online.");
                        else if(!temp.reg.isreg)
                          cur_client.sendFromBot("Client exists but not registered.");
                        else
                        {
                            reg_config.unreg(temp.ID);
                            Main.PopMsg (cur_client.NI+" deleted the reg "+temp.ID);
                            cur_client.sendFromBot("User "+temp.NI+" deleted.");
                            temp.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.".replaceAll(" ","\\ "));
                            temp.sendToClient ("IQUI ABCD");
                            temp.OP="";
                            temp.HO=Integer.toString (Integer.parseInt(temp.HO)-1);
                            temp.HN=Integer.toString (Integer.parseInt(temp.HN)+1);
                            new Broadcast("BINF "+temp.SessionID+" OP"+" HO"+temp.HO+" HN"+temp.HN);
                            temp.reg=new nod();
                        }
                }
                Main.Server.rewriteregs ();
                
        }
        else if(recvbuf.toLowerCase ().startsWith("reg"))
        {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken ();
                if(aux.length ()==39) //possible CID, lets try
                {
                    try
                    {
                        Base32.decode (aux);
                        if(reg_config.isReg (aux)>0)
                        {
                            
                            cur_client.sendFromBot(""+reg_config.getnod (aux).getRegInfo ());
                            return;
                        }
                        ClientHandler temp=ClientHandler.FirstClient.NextClient;
                        while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ID.equals(aux)))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                        {
                            reg_config.addReg (aux,null,cur_client.NI);
                            cur_client.sendFromBot("CID added. No password set, login does not require pass, however, its recomandable to set one...");
                            
                        }   
                        else
                        {
                            reg_config.addReg (temp.ID,temp.NI,cur_client.NI);
                            cur_client.sendFromBot("User "+temp.NI+" found with CID "+aux+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            temp.sendFromBot(""+"You have been registered by "+cur_client.NI+" . No password set, login does not require pass, however, its recomandable you to set one...".replaceAll(" ","\\ "));
                            temp.sendToClient ("BINF ABCD ID"+Main.Server.OpChatCid+" NI"+Vars.Opchat_name+" BO1 OP1 DE"+Vars.Opchat_desc);;
                            temp.OP="1";
                            temp.HO=String.valueOf(Integer.parseInt(temp.HO)+1);
                            temp.HN=String.valueOf(Integer.parseInt(temp.HN)-1);
                            new Broadcast("BINF "+temp.SessionID+" OP1"+" HO"+temp.HO+" HN"+temp.HN);
                            temp.reg=reg_config.getnod (temp.ID);
                            temp.reg.LastIP=temp.ClientSock.getInetAddress ().getHostAddress ();
                        
                        }
                        Main.PopMsg (cur_client.NI+" regged the CID "+aux);
                        
                    }
                    catch (IllegalArgumentException iae)
                    {
                        //cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                        ClientHandler temp=ClientHandler.FirstClient.NextClient;
                        while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                        { cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");cur_client.sendFromBot("No such client online.");}
                        else
                        {
                         if(reg_config.isReg (temp.ID)>0)
                        {
                            cur_client.sendFromBot(""+reg_config.getnod (temp.ID).getRegInfo ());
                            return;
                        }
                            reg_config.addReg (temp.ID,temp.NI,cur_client.NI);
                            cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");cur_client.sendFromBot("User "+temp.NI+" found with CID "+temp.ID+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            temp.sendFromBot(""+"You have been registered by "+cur_client.NI+" . No password set, login does not require pass, however, its recomandable you to set one...".replaceAll(" ","\\ "));
                            temp.sendToClient ("BINF ABCD ID"+Main.Server.OpChatCid+" NI"+Vars.Opchat_name+" BO1 OP1 DE"+Vars.Opchat_desc);;
                            temp.OP="1";
                            temp.HO=String.valueOf(Integer.parseInt(temp.HO)+1);
                            temp.HN=String.valueOf(Integer.parseInt(temp.HN)-1);
                            new Broadcast("BINF "+temp.SessionID+" OP1"+" HO"+temp.HO+" HN"+temp.HN);
                            temp.reg=reg_config.getnod (temp.ID);
                            temp.reg.LastIP=temp.ClientSock.getInetAddress ().getHostAddress ();
                            Main.PopMsg (cur_client.NI+" regged the CID "+temp.ID);
                        }
                            
                    }
                    catch (Exception e)
                    {
                      return ;
                    }
                }
                else 
                {
                    //cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                        ClientHandler temp=ClientHandler.FirstClient.NextClient;
                       while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                        {  cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");cur_client.sendFromBot("No such client online.");}
                        else
                        {
                            if(reg_config.isReg (temp.ID)>0)
                        {
                            cur_client.sendFromBot(""+reg_config.getnod (temp.ID).getRegInfo ());
                            return;
                        }
                            reg_config.addReg (temp.ID,temp.NI,cur_client.NI);
                            cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");cur_client.sendFromBot("User "+temp.NI+" found with CID "+temp.ID+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            temp.sendFromBot(""+"You have been registered by "+cur_client.NI+" . No password set, login does not require pass, however, its recomandable you to set one...".replaceAll(" ","\\ "));
                            temp.sendToClient ("BINF ABCD ID"+Main.Server.OpChatCid+" NI"+Vars.Opchat_name+" BO1 OP1 DE"+Vars.Opchat_desc);;
                            temp.OP="1";
                            temp.HO=String.valueOf(Integer.parseInt(temp.HO)+1);
                            temp.HN=String.valueOf(Integer.parseInt(temp.HN)-1);
                            new Broadcast("BINF "+temp.SessionID+" OP1"+" HO"+temp.HO+" HN"+temp.HN);
                            temp.reg=reg_config.getnod (temp.ID);
                            temp.reg.LastIP=temp.ClientSock.getInetAddress ().getHostAddress ();
                            Main.PopMsg (cur_client.NI+" regged the CID "+temp.ID);
                        }
                }
                
                Main.Server.rewriteregs();
                //sendFromBot("Your password is now "+aux+".");
               
        } 
        else if(recvbuf.toLowerCase ().equals("help"))
        {
           
                
            
                String blah="Death Squad Hub "+Vars.HubVersion+". Running on " + 
                        Main.Proppies.getProperty("os.name")
                       +" "+ Main.Proppies.getProperty("os.version")
                       +" "+Main.Proppies.getProperty("os.arch")+"\n"+
                        
                         Main.HelpText.substring(0,Main.HelpText.length ()-1).replaceAll ("\\x0a","\\\n");
                        ;
                cur_client.sendFromBot (blah);
               
        }
        else if(recvbuf.toLowerCase ().startsWith ("info "))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                String aux=ST.nextToken (); //the thing to check;
                while(ST.hasMoreTokens ())
                    aux+=ST.nextToken ();
                aux=retADCStr(aux);
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
              
                if(isIP(aux))//we have an IP address
                {
                    //ClientHandler temp=ClientHandler.FirstClient.NextClient;
              String Nicklist="";
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ClientSock.getInetAddress().getHostAddress().equals(aux.toLowerCase ())))
                                Nicklist=Nicklist+temp.NI+"\n";
                            temp=temp.NextClient;
                            
                        }
               if(!Nicklist.equals (""))
                  cur_client.sendFromBot("Users with IP "+aux+" :\n"+Nicklist.substring(0,Nicklist.length()-1));
               else 
                  cur_client.sendFromBot("No user with IP "+aux);
               
                }
                else
                {
                
                    //ok now lets see if its a valid CID
                    if(aux.length ()==39)
                    {
                        try
                        {
                            Base32.decode (aux);
                            //ok if we are here, its a CID
                             temp=ClientHandler.FirstClient.NextClient;
             
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ID.equals(aux)))
                                break;
                            temp=temp.NextClient;
                            
                        }
               if(temp!=null)
                  cur_client.sendFromBot("CID "+aux+" is used by:\n"+temp.NI);
               else 
                  cur_client.sendFromBot("Nobody is using "+aux);
                            return;
                        }
                        catch (IllegalArgumentException e)
                        {
                        //its a nick though...
                        }
                    }
                    
                    
                    
                temp=ClientHandler.FirstClient.NextClient;
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            
                        }
               if(temp==null)
                cur_client.sendFromBot("No such user online.");
               else
               {
                    
                    String blah11="User Info\nNick "+temp.NI+"\nCID "+temp.ID+"\nShare Size "+temp.SS+ " Bytes\n"+
                            "Description "+temp.DE+"\nTag ";
                    
                    String Tag="<"+temp.VE+",M:";
                    if(temp.ACTIVE==1)
                        Tag=Tag+"A";
                    else Tag=Tag+"P";
                    Tag=Tag+",H:"+temp.HN+"/";
                    if(temp.HR!=null)
                                Tag=Tag+temp.HR+"/";
                    else Tag=Tag+"?";
                    if(temp.HO!=null)
                                Tag=Tag+temp.HO;
                    else Tag=Tag+"?";
                            
                            Tag=Tag+",S:";
                    if(temp.SL!=null)
                            
                            Tag=Tag+temp.SL+">";
                    else
                        Tag=Tag+"?>";
                            blah11=blah11+Tag+"\nSupports "
                                    + ((temp.SU!=null) ? temp.SU : "nothing special")+"\nIp address "+temp.ClientSock.getInetAddress().getHostAddress();
                     if(temp.reg.isreg)   
                     {
                                
                            blah11=blah11+temp.reg.getRegInfo();
                     }
                    
                     else
                         blah11=blah11+"\nNormal user.";
                      cur_client.sendFromBot(""+blah11);
               }
                }
        }
        
        else if(recvbuf.toLowerCase ().startsWith ("mass"))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
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
                                "      Example: !mass \\\\[RO\\\\].* text -- this command sends mass to all users that have their nick starting with [RO]\n" +
                                "      Example: !mass .. text --this command sends mass to all users with 2 letter nicks\n"+
                                "      This type of mass command accepts just any regular expression.\n"+
                                "   Sending to users that have their fields checked:\n"+
                                "      Example: !mass share<1024 text --this command just sends text to all users with share less then 1 gigabyte.\n"+
                                "      Example: !mass sl=1 text-- this command just sends text to all users with exactly one open slot.\n"+
                                "      Example: !mass su!tcp4 text -- this command just sends text to all passive users.\n"
                               +"Extended mass has the operators >, < , =, !\n"+
                                "   And a list of possible fields : all ( to everybody ) share, sl (slots), ni (nick length),su(supports, accepts only = or !, example: !mass su=tcp4 text),hn(normal hubs count),hr(registered hub count),ho(op hub count),aw(away, 1 means normal away, 2 means extended away),rg (1- registered, 0 otherwise, registered means not op),op ( 1 -op, 0 - otherwise , op means it has key).";
                    cur_client.sendToClient ("EMSG DCBA "+cur_client.SessionID+" "+Text);
                    return;
                }
               String extmass=ST.nextToken ();
                while (ST.hasMoreTokens ())
                 aux=aux+ST.nextToken ()+" "; //the message to broadcast;
               // aux=aux.substring (0,aux.length ()-1);
                aux=retADCStr(aux);
                
               if(extmass.equalsIgnoreCase ("all")) 
               {
               ClientHandler temp=ClientHandler.FirstClient.NextClient;
               while(temp!=null)
               {
                   temp.sendFromBotPM (aux);
                   temp=temp.NextClient;
               }
               cur_client.sendFromBot("Broadcast sent.");      
               }
                ClientHandler temp;
                try
               {
                            extmass=extmass.replaceAll ("\\\\\\\\","\\\\");
                           // System.out.println (aux);
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
                                 //cur_client.sendFromBot("Not a valid Regular Expression...");
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Mass ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
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
        else if(recvbuf.toLowerCase ().startsWith ("mynick "))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                String aux="";
                while (ST.hasMoreTokens ())
                 aux=aux+ST.nextToken ()+" "; //new nick
                if(!(aux.equals ("")))
                aux=aux.substring (0,aux.length ()-1);
                aux=retADCStr(aux);
                if(aux.length ()<Vars.min_ni)
                {
                    { 
                       cur_client.sendFromBot("Nick too small, please choose another.");
                       return;
                    }
                }
                if(aux.length ()>Vars.max_ni)
                {
                    {
                       cur_client.sendFromBot("Nick too large, please choose another.");
                       return;
                    }
                }
                if(!Vars.ValidateNick (aux) || isIP(aux) || isCID(aux))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       System.out.println (aux);
                       return;
                   }
                
                    ClientHandler tempy=ClientHandler.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if(tempy.userok==1) if( (tempy.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            tempy=tempy.NextClient;
                           
                        }
                    if(tempy!=null)
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                    if(aux.equalsIgnoreCase(Vars.Opchat_name))
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                    if(aux.equalsIgnoreCase(Vars.bot_name))
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
             
                    
               new Broadcast("BINF "+cur_client.SessionID+" NI"+aux);
                        
               new Broadcast("IMSG "+cur_client.NI+" is now known as "+aux);
               cur_client.NI=aux;
        }
        else if(recvbuf.toLowerCase ().startsWith ("rename "))
        {
                    //cur_client.sendFromBot(""+retADCStr("Sorry but renaming features are temporary disabled until DC++ has UCMD's ( because !rename mister bla new nick has 4 entities and its quite hard to guess what is first nick and what is 2nd nick."));
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken (); //the nick to rename;
               // aux=retADCStr(aux);
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           
                        }
               if(temp==null)
                         cur_client.sendFromBot("No such user online.");
               else if(temp.reg.key && Vars.rename_ops==0)
                         cur_client.sendFromBot("Can't rename other ops.");
              
                else
                  {
                    //actual renaming.
                    String newnick=ST.nextToken ();
                 if(newnick.length ()<Vars.min_ni)
                {
                    { 
                       cur_client.sendFromBot("Nick too small, please choose another.");
                       return;
                    }
                }
                if(newnick.length ()>Vars.max_ni)
                {
                    {
                       cur_client.sendFromBot("Nick too large, please choose another.");
                       return;
                    }
                }
                    if(!Vars.ValidateNick (newnick) || isIP(newnick) || isCID(newnick))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       return;
                   }
                    ClientHandler tempy=ClientHandler.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if(tempy.userok==1) if( (tempy.NI.toLowerCase ().equals(newnick.toLowerCase ())))
                                break;
                            tempy=tempy.NextClient;
                           
                        }
                    if(tempy!=null)
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                    if(newnick.equals(Vars.Opchat_name))
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                    if(newnick.equals(Vars.bot_name))
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                    new Broadcast("BINF "+temp.SessionID+" NI"+newnick);
                    
                    
                    
                    cur_client.sendFromBot("Renamed user "+temp.NI+" to "+newnick);
                     new Broadcast("IMSG "+temp.NI+" is now known as "+newnick);
                     temp.NI=newnick;
                    
                }
        }
        else if(recvbuf.toLowerCase ().startsWith ("kick"))
        {
                    if(recvbuf.equalsIgnoreCase ("kick"))
                    {
                        
                                String Text=
                                "\nKick Command:\nKicking in DSHub is very simple now.\nClassic kick:\n"+
                                "     kicks just a user out in flames, with a kick_time temporary ban, default 5 minutes.\n"+
                                "Extended kick has way more advantages and can be used very efficiently with a large hub.\n"+
                                "Extended kick features:\n"+
                                "   Kicking users that match a certain regular expression:\n"+
                                "      Example: !kick \\\\[RO\\\\].* -- this command kicks all users that have their nick starting with [RO]\n" +
                                "      Example: !kick .. --this command kicks all users with 2 letter nicks\n"+
                                "      This type of kick accepts just any regular expression.\n"+
                                "   Kicking users that have their fields checked:\n"+
                                "      Example: !kick share<1024 --this command just kicks all users with share less then 1 gigabyte.\n"+
                                "      Example: !kick sl=1 -- this command kicks all users with exactly one open slot.\n"+
                                        "      Example: !kick su!tcp4 -- this command kicks all passive users.\n"
                               +"Extended kick has the operators >, < , =, !\n"+
                                "   And a list of possible fields : share, sl (slots), ni (nick length),su(supports, accepts only = or !, example: !kick su=tcp4),hn(normal hubs count),hr(registered hub count),ho(op hub count),aw(away, 1 means normal away, 2 means extended away),rg (1- registered, 0 otherwise, registered means not op),op ( 1 -op, 0 - otherwise , op means it has key).";
                               cur_client.sendFromBot(""+Text.replaceAll(" ","\\ ") );
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                String aux=ST.nextToken ();
                
                aux=retADCStr(aux);
                
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
                            aux=aux.replaceAll ("\\\\\\\\","\\\\");
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
        else if(recvbuf.toLowerCase ().startsWith ("drop"))
        {
                    if(recvbuf.equalsIgnoreCase ("drop"))
                    {
                        
                                String Text=
                                "\nDrop Command:\nDropping in DSHub is also very simple now.\nClassic drop:\n"+
                                "     drop just a user down from the sky, with no temporary ban or message.\n"+
                                "Extended drop has way more advantages and can be used very efficiently with a large hub.\n"+
                                "Extended drop features:\n"+
                                "   Kicking users that match a certain regular expression:\n"+
                                "      Example: !drop \\\\[RO\\\\].* -- this command drops all users that have their nick starting with [RO]\n" +
                                "      Example: !drop .. --this command drops all users with 2 letter nicks\n"+
                                "      This type of drop accepts just any regular expression.\n"+
                                "   Kicking users that have their fields checked:\n"+
                                "      Example: !drop share<1024 --this command just drops all users with share less then 1 gigabyte.\n"+
                                "      Example: !drop sl=1 -- this command drops all users with exactly one open slot.\n"+
                                        "      Example: !drops su!tcp4 -- this command drops all passive users.\n"
                               +"Extended drop has the operators >, < , =, !\n"+
                                "   And a list of possible fields : share, sl (slots), ni (nick length),su(supports, accepts only = or !, example: !drop su=tcp4),hn(normal hubs count),hr(registered hub count),ho(op hub count),aw(away, 1 means normal away, 2 means extended away),rg (1- registered, 0 otherwise, registered means not op),op ( 1 -op, 0 - otherwise , op means it has key).";
                               cur_client.sendFromBot(""+Text.replaceAll(" ","\\ ") );
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken (); //the nick to drop;
                
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            //tempyprev=tempyprev.NextClient;
                        }
               if(temp==null)
               {
                            cur_client.sendFromBot("No such user online. Parsing to Extended Drop...");
                            /***************extended kick**********************/
                        try
                        {
                            aux=aux.replaceAll ("\\\\\\\\","\\\\");
                           // System.out.println (aux);
                            "".matches(aux);
                             temp=ClientHandler.FirstClient.NextClient;
                             //tempyprev=ClientHandler.FirstClient;
                            while(temp!=null)
                            {
                                 temp=ClientHandler.FirstClient.NextClient;
                            // tempyprev=ClientHandler.FirstClient;
                            while(temp!=null)
                            {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().matches (aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           // tempyprev=tempyprev.NextClient;
                             }
                             if(temp==null)
                             {
                                 cur_client.sendFromBot("Done with matching users...");
                                  throw new PatternSyntaxException("whatever...","bla",1);
                             }
                           
                            
                          temp.dropMe (cur_client);
                    
                    
                         
                           
                           
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Kick ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)>Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with share > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended drop ...\");cur_client.sendFromBot("Done.");
                                     //drop all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)>Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Normal Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended drop ...\");cur_client.sendFromBot("Done.");
                                     //drop all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)>Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Reg Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)>Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Op Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)>Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with share > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()>Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with nick length > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024<Number )//&& tempz.userok==1)
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with share < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)<Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Normal Hub Count < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)<Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Op Hub Count < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)<Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Reg Hub Count > "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()<Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with nick length < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)<Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with slots < "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024==Number )//&& tempz.userok==1)
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with share = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Op Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all away users.");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.OP)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all Op users .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Reg Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Normal Hub Count = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                 if(aux.substring (0,mark).equalsIgnoreCase("su"))
                                 {
                                     String Number="";
                                     
                                      Number=aux.substring (mark+1,aux.length ());
                                    
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.SU.toLowerCase ().contains (Number.toLowerCase () ))//&& tempz.userok==1)
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users supporting "+Number+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with nick length = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)==Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with slots = "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SS)/1024/1024!=Number )//&& tempz.userok==1)
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with share not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all not registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.AW)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all not away users .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.RG)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all not registered users .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HO)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Op Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HR)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Reg Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.HN)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with Normal Hub Count not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  if(aux.substring (0,mark).equalsIgnoreCase("su"))
                                 {
                                     String Number="";
                                     
                                      Number=aux.substring (mark+1,aux.length ());
                                    
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(!tempz.SU.toLowerCase ().contains (Number.toLowerCase () ))//&& tempz.userok==1)
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users not supporting "+Number+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(tempz.NI.length ()!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with nick length not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
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
                                         cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                                         return;
                                     }
                                    // cur_client.sendFromBot(""+Integer.toString (Number));//Invalid Extended Drop ...\");cur_client.sendFromBot("Done.");
                                     //kick all shared > number
                                     ClientHandler tempz=ClientHandler.FirstClient.NextClient;
                                     while(tempz!=null)
                                     {if(tempz.userok==1)
                                         if(Long.parseLong (tempz.SL)!=Number )
                                             tempz.dropMe (cur_client);
                                         tempz=tempz.NextClient;
                                     }
                                     cur_client.sendFromBot("Dropped all users with slots not "+Long.toString (Number)+" .");cur_client.sendFromBot("Done.");
                                     
                                     
                                    return; 
                                 }
                                  
                             }
                             cur_client.sendFromBot("Invalid Extended Drop ...");cur_client.sendFromBot("Done.");
                             
                             }
                            /*****************extended kick*******************/
               }
               else if(temp.reg.isreg && Vars.kick_ops==0)
                         cur_client.sendFromBot("Can't drop other ops.");
                else
                  {
                    //actual dropping.
                    
                    temp.dropMe (cur_client);
                    
                    
                    
                }
        }
        else if(recvbuf.toLowerCase ().startsWith ("unban"))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                {
                    cur_client.sendFromBot ("Nothing specified for unbanning.");
                    return;
                }
                String aux=ST.nextToken (); //the thing to unban;
                //al right,now must check if that is a nick, cid or ip
                //first if its a cid...
                aux=retADCStr(aux);
                try
                {
                    Base32.decode(aux);
                    //ok if we got here it really is a CID so:
                    if(aux.length ()!=39)
                        throw new IllegalArgumentException();
                    if(BanList.delban(3,aux))
                    {
                        cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+", unbanned.");
                        
                        
                    }
                    else
                    {cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+", not banned nothing to do.");}
                }
                catch(IllegalArgumentException iae)
                {
                    //ok its not a cid, lets check if its some IP address...
                    cur_client.sendFromBot("Not a CID, Searching...");
                    if(isIP(aux))
                    {
                        cur_client.sendFromBot("Is IP ...checking if banned...");
                        if(BanList.delban (2,aux))
                            cur_client.sendFromBot("Found IP address "+aux+", unbanned.");
                        else
                            cur_client.sendFromBot("Found IP address "+aux+", but is not banned, nothing to do.");
                    }
                    else 
                    {
                        cur_client.sendFromBot("Is not IP...Checking for nick...");
                        if(BanList.delban (1,aux))
                            cur_client.sendFromBot("Found nick "+aux+", unbanned.");
                        else
                            cur_client.sendFromBot("Nick "+aux+" is not banned, nothing to do.");
                    }
                }
                cur_client.sendFromBot("Done.");
                    Main.Server.rewritebans ();
                    
                
        }
        else if(recvbuf.toLowerCase ().startsWith ("bancid "))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to ban;
                aux=retADCStr (aux);
                //al right,now must check if that is a nick, cid or ip
                //first if its a cid...
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                reason=retADCStr(reason);
               // System.out.println (reason);
               if(isCID(aux))
               {
                    //ok if we got here it really is a CID so:
                    
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ID.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           // tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                       if(temp.reg.isreg && Vars.kick_ops==0)
                       { cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+" but it belongs to"+temp.NI+", can't ban other ops.Not banned.");}
                       
                       else
                       {
                        
                      cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+", banning..");
                     temp.kickMeOut (cur_client,reason,3,-1L);
                       }
                        
                    
                 else
                 {
                     if(reg_config.isReg (aux)==2 && Vars.kick_ops==0)
                     { cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+" but it belongs to"+temp.NI+", can't ban other ops.Not banned.");}
                     else
                     {
                         cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+", banning....");
                     
                        BanList.addban (3,aux,-1,cur_client.NI,reason);
                     }
                 }
                }
               else 
                {
                    //ok its not a cid, lets check if its some IP address...
                    cur_client.sendFromBot("Not a CID, Searching for a nick...");
                    ClientHandler temp=ClientHandler.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            //tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                       if(temp.reg.isreg && Vars.kick_ops==0)
                         cur_client.sendFromBot("Found user "+temp.NI+" with CID "+temp.ID+", can't ban other ops.Not banned.");
                       else
                       {
                        //BanList.addban (3,temp.ID,-1,cur_client.NI,reason);
                      cur_client.sendFromBot("Found user "+aux+", banning..");
                    temp.kickMeOut (cur_client,reason,3,-1L);
                       }
                else
                {
                    cur_client.sendFromBot("No user found with nick "+aux+". Not banned.");
                    
                }
            }//end catch
                cur_client.sendFromBot("Done.");
                    Main.Server.rewritebans ();
                    
                
        }
        else if(recvbuf.toLowerCase ().startsWith ("bannick "))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to ban;
                aux=retADCStr(aux);
                //al right,now must check if that is a nick online of offline
                
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                    reason=retADCStr(reason);
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           // tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                       if(temp.reg.isreg && Vars.kick_ops==0)
                       { cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found Nick "+aux+" but it belongs to an op, can't ban other ops.Not banned.");}
                       else
                       {
                        //BanList.addban (1,aux,-1,cur_client.NI,reason);
                      cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found Nick "+aux+", banning..");
                     temp.kickMeOut (cur_client,reason,1,-1L);
                    //Main.Server.rewritebans ();
                       }
                        
                    
                 else
                 {
                        cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found Nick "+aux+", banning....");
                        BanList.addban (1,aux,-1,cur_client.NI,reason);
                 }
                Main.Server.rewritebans ();
                cur_client.sendFromBot("Done.");
        }
        
        else if(recvbuf.toLowerCase ().startsWith ("banip "))
        {
                StringTokenizer ST=new StringTokenizer(retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to ban;
                aux=retADCStr(aux);
                //al right,now must check if that is a  ip or nick
                //first if its a ip...
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                reason=retADCStr(reason);
                 if(isIP(aux))
                 {
                    //ok if we got here it really is a IP so:
                    
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
               // ClientHandler tempyprev=ClientHandler.FirstClient;
                  while(temp!=null)
                  {
                            if(temp.userok==1) 
                                if( temp.ClientSock.getInetAddress().getHostAddress().equals(aux))
                                {
                         if(temp.reg.isreg && Vars.kick_ops==0)
                         {cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found IP "+aux+" but it belongs to "+temp.NI+", can't ban other ops. Not banned.");cur_client.sendFromBot("Done.");
                         return;}
                                }
                            temp=temp.NextClient;
                  }
                temp=ClientHandler.FirstClient.NextClient;
                int kickedsome=0;
                while(temp!=null)
                        {
                            if(temp.userok==1) 
                                if( temp.ClientSock.getInetAddress().getHostAddress().equals(aux))
                                {
                         
                        temp.kickMeOut (cur_client,reason,2,-1L);}
                                
                                
                            temp=temp.NextClient;
                            //tempyprev=tempyprev.NextClient;
                        }
               
               if(kickedsome==0)
               {
                        cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found IP "+aux+", banning....");
                        BanList.addban (2,aux,-1,cur_client.NI,reason);
               }
                 
                }
                 else
                {
                    //ok its not a ip, lets check if its some nick...
                    cur_client.sendFromBot("Not a IP, Searching for a nick...");
                    ClientHandler temp=ClientHandler.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.userok==1) if( temp.NI.toLowerCase ().equals(aux.toLowerCase ()))
                                break;
                            temp=temp.NextClient;
                            //tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                {  
                        if(temp.reg.isreg && Vars.kick_ops==0)
                         cur_client.sendFromBot("Found user "+temp.NI+" with IP "+temp.ClientSock.getInetAddress().getHostAddress()+", can't ban other ops.Not banned.");
                       else
                       {
                        
                      cur_client.sendFromBot("Found user "+aux+" with IP "+temp.ClientSock.getInetAddress ().getHostAddress ()+", banning..");
                     
                   
                        temp.kickMeOut (cur_client,reason,2,-1L);
                       }
                 }
                else
                {
                    cur_client.sendFromBot("No user found with nick "+aux+". Not banned.");
                    
                }
            }//end catch
                cur_client.sendFromBot("Done.");
                    Main.Server.rewritebans ();
                    
                
        }
       
       else if (recvbuf.toLowerCase ().startsWith("cfg"))
        {
                if(recvbuf.equals ("cfg"))
                {
                cur_client.sendFromBot("Usage: cfg <varname> <newval>. cfg list to see all.");
                //break;
                }
                else
                {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken ();
                if(aux.toLowerCase().equals ("timeout_login"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.Timeout_Login;
                        Vars.Timeout_Login=Integer.parseInt (aux);
                        cur_client.sendFromBot("Timeout_Login changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("hub_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Hub_name changed from \""+
                                Vars.HubName+"\" to \""+new_name+"\".");
                        
                        Vars.HubName=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("IINF NI"+retADCStr(Vars.HubName));
                        
                }
                else if(aux.toLowerCase().equals ("max_ni"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_ni;
                        Vars.max_ni=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_NI changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_ni"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.min_ni;
                        Vars.min_ni=Integer.parseInt (aux);
                        cur_client.sendFromBot("Min_NI changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_de"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_de;
                        Vars.max_de=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_DE changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_share"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_share;
                        Vars.max_share=Long.parseLong (aux);
                        cur_client.sendFromBot("Max_share changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_share"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.min_share;
                        Vars.min_share=Long.parseLong (aux);
                        cur_client.sendFromBot("Min_share changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_sl"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_sl;
                        Vars.max_sl=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_SL changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_sl"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_sl;
                        Vars.min_sl=Integer.parseInt (aux);
                        cur_client.sendFromBot("Min_SL changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_em"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_em;
                        Vars.max_em=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_EM changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_op"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_op;
                        Vars.max_hubs_op=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_hubs_op changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_reg"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_reg;
                        Vars.max_hubs_reg=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_hubs_reg changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_user"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_user;
                        Vars.max_hubs_user=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_hubs_user changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                       Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_sch_chars"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_sch_chars;
                        Vars.max_sch_chars=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_sch_chars changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_sch_chars"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.min_sch_chars;
                        Vars.min_sch_chars=Integer.parseInt (aux);
                        cur_client.sendFromBot("Min_sch_chars changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_chat_msg"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_chat_msg;
                        Vars.max_chat_msg=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_chat_msg changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("kick_ops"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.kick_ops;
                        Vars.kick_ops=Integer.parseInt (aux);
                        cur_client.sendFromBot("Kick_ops changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("history_lines"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.history_lines;
                       /* if(Integer.parseInt (aux)<10)
                        {
                          Vars.history_lines=10;
                        }
                        else if(Integer.parseInt (aux)>300)
                            Vars.history_lines=300;
                        else
                        Vars.history_lines=Integer.parseInt (aux);
                        */
                        
                        cur_client.sendFromBot("History lines changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("opchat_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                          // new_name=new_name+" "+ST.nextToken ();
                    if(!Vars.ValidateNick (new_name))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       return;
                   }
                    ClientHandler tempy=ClientHandler.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if(tempy.userok==1) if( (tempy.NI.toLowerCase ().equals(new_name.toLowerCase ())))
                                break;
                            tempy=tempy.NextClient;
                           
                        }
                    if(tempy!=null)
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                        cur_client.sendFromBot("Opchat_name changed from \""+
                                Vars.Opchat_name+"\" to \""+new_name+"\".");
                        
                        Vars.Opchat_name=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF ABCD NI"+retADCStr(Vars.Opchat_name),10);
                        
                }
                else if(aux.toLowerCase ().equals ("bot_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                          // new_name=new_name+" "+ST.nextToken ();
                    if(!Vars.ValidateNick (new_name))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       return;
                   }
                    ClientHandler tempy=ClientHandler.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if(tempy.userok==1) if( (tempy.NI.toLowerCase ().equals(new_name.toLowerCase ())))
                                break;
                            tempy=tempy.NextClient;
                           
                        }
                    if(tempy!=null)
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                        cur_client.sendFromBot("Bot_name changed from \""+
                                Vars.bot_name+"\" to \""+new_name+"\".");
                        
                        Vars.bot_name=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF DCBA NI"+retADCStr(Vars.bot_name));
                        
                }
                else if(aux.toLowerCase ().equals ("opchat_desc"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Opchat_desc changed from \""+
                                Vars.Opchat_desc+"\" to \""+new_name+"\".");
                        
                        Vars.Opchat_desc=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF ABCD DE"+retADCStr(Vars.Opchat_desc),10);
                        
                }
                else if(aux.toLowerCase ().equals ("bot_desc"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("bot_desc changed from \""+
                                Vars.bot_desc+"\" to \""+new_name+"\".");
                        
                        Vars.bot_desc=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF DCBA DE"+retADCStr(Vars.bot_desc));
                        
                }
                else if(aux.toLowerCase().equals ("kick_time"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.kick_time;
                        if(Integer.parseInt (aux)<0)
                        {
                            cur_client.sendFromBot("Invalid kick time number.");
                            return;
                        }
                        Vars.kick_time=Integer.parseInt (aux);
                        
                        cur_client.sendFromBot("Kick time changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("msg_banned"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Msg_Banned changed from \""+
                                Vars.Msg_Banned+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Banned=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                 else if(aux.toLowerCase().equals ("rename_ops"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.rename_ops;
                        Vars.rename_ops=Integer.parseInt (aux);
                        cur_client.sendFromBot("Rename_ops changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("reg_only"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.reg_only;
                        Vars.reg_only=Integer.parseInt (aux);
                        cur_client.sendFromBot("Reg_only changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("nick_chars"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                       //    new_name=new_name+" "+ST.nextToken ();
                       
                        
                        cur_client.sendFromBot("Nick_chars changed from \""+
                                Vars.nick_chars+"\" to \""+new_name+"\".");
                        
                        Vars.nick_chars=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                else if(aux.toLowerCase().equals ("ops_override_full"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.ops_override_full;
                        Vars.ops_override_full=Integer.parseInt (aux);
                        cur_client.sendFromBot("Ops_override_full changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("msg_full"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Msg_Full changed from \""+
                                Vars.Msg_Full+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Full=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
               else if(aux.toLowerCase().equals ("max_users"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_users;
                        Vars.max_users=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_Users changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("chat_interval"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.chat_interval;
                       int blahhh= Integer.parseInt (aux);
                       if(blahhh<20)
                       {
                            cur_client.sendFromBot("A bit small don't you think ?");
                            return;
                       }
                        Vars.chat_interval=blahhh;
                                
                        
                        cur_client.sendFromBot("Chat_Interval changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("ops_override_spam"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.ops_override_spam;
                        Vars.ops_override_spam=Integer.parseInt (aux);
                        cur_client.sendFromBot("Ops_override_spam changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("keep_alive_interval"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.keep_alive_interval;
                        int x=Integer.parseInt (aux);
                        if(x<30)
                        {
                            cur_client.sendFromBot("With that value, hub will have a lag...");
                            return;
                        }
                        Vars.keep_alive_interval=x;
                        cur_client.sendFromBot("Keep_Alive_Interval changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("save_logs"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.savelogs;
                        Vars.savelogs=Integer.parseInt (aux);
                        cur_client.sendFromBot("Save_logs changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("automagic_search"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.automagic_search;
                        Vars.automagic_search=Integer.parseInt (aux);
                        cur_client.sendFromBot("Automagic_search changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("search_log_base"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_log_base;
                        Vars.search_log_base=Integer.parseInt (aux);
                        cur_client.sendFromBot("Search_log_base changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("search_steps"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_steps;
                        Vars.search_steps=Integer.parseInt (aux);
                        cur_client.sendFromBot("Search_steps changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                 else if(aux.toLowerCase().equals ("search_spam_reset"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_spam_reset;
                        Vars.search_spam_reset=Integer.parseInt (aux);
                        cur_client.sendFromBot("Search_spam_reset changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("msg_search_spam"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Msg_Search_Spam changed from \""+
                                Vars.Msg_Search_Spam+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Search_Spam=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                else if(aux.toLowerCase ().equals ("list"))
                {
                    String blah="Cfg Variables list: \n"+
                                       "   timeout_login           "  + Integer.toString (Vars.Timeout_Login) +"         -- Number of seconds for hub to wait for connecting users until kick them out.\n"
                            +          "   hub_name                "  + Vars.HubName+ "         -- Hub name to display in main window.\n"
                            +          "   max_ni                  "  +Vars.max_ni+"        -- Maximum nick size, integer.\n"
                            +          "   min_ni                  "  +Vars.min_ni+"         -- Minimum nick size, integer.\n"
                            +          "   max_de                  "  +Vars.max_de+"        -- Maximum description size, integer.\n"
                            +          "   max_share               "  +Vars.max_share+"         -- Maximum share size, long integer.\n"
                            +          "   min_share               "  +Vars.min_share+"         -- Minimum share size, long integer.\n"
                            +          "   max_sl                  "  +Vars.max_sl+"         -- Maximum slot number, integer.\n"
                            +          "   min_sl                  "  +Vars.min_sl+"        -- Minimum slot number, integer.\n"
                            +          "   max_em                  "  +Vars.max_em+"        -- Maximum e-mail string size, integer.\n"
                            +          "   max_hubs_op             "  +Vars.max_hubs_op+"       -- Maximum hubs where user is op, integer.\n"
                            +          "   max_hubs_reg            "  +Vars.max_hubs_reg+"       -- Maximum hubs where user is reg, integer.\n"
                            +          "   max_hubs_user           "  +Vars.max_hubs_user+"         -- Maximum hubs where user is user, integer.\n"
                            +          "   max_sch_chars           "  +Vars.max_sch_chars+"      -- Maximum search chars, integer.\n"
                            +          "   min_sch_chars           "  +Vars.min_sch_chars+"      -- Minimum search chars, integer.\n"
                            +          "   max_chat_msg            "  +Vars.max_chat_msg+"       -- Maximum chat message size, integer.\n"
                            +          "   max_users               "  +Vars.max_users+"         -- Maximum number of online users, integer.\n"
                            +          "   kick_ops                "  +Vars.kick_ops+"      -- 0 = ops can't be kicked/banned, other value = they can be kicked/banned.\n"
                            +          "   rename_ops              "  +Vars.rename_ops+"        -- 0 = ops can't be renamed, other value = they can be.\n"
                            +          "   ops_override_full       "  +Vars.ops_override_full+"         -- 1 = ops can enter full hub, other value = they can't.\n"
                            +          "   ops_override_spam       "  +Vars.ops_override_spam+"         -- 1 = ops can override spam settings, other value = they can't.\n"
                            +          "   history_lines           "  +Vars.history_lines+"         -- Number of lines to keep in chat history.\n"
                            +          "   opchat_name             "  +Vars.Opchat_name+"       -- The Operator Chat Bot Nick.\n"
                            +          "   opchat_desc             "  +Vars.Opchat_desc+"       -- The Operator Chat Bot Description.\n"
                            +          "   kick_time               "  +Vars.kick_time+"         -- The time to ban a user with a kick, in seconds.\n"
                            +          "   msg_banned              "  +Vars.Msg_Banned+"        -- The aditional message to show to banned users when connecting.\n"
                            +          "   msg_full                "  +Vars.Msg_Full+"      -- Message to be shown to connecting users when hub full.\n"
                            +          "   reg_only                "  +Vars.reg_only+"      -- 1 = registered only hub. 0 = otherwise.\n"
                            +          "   nick_chars              "  +Vars.nick_chars+"         -- Chars that could be used for a nick, String.\n"
                            +          "   chat_interval           "  +Vars.chat_interval+"         -- Interval between chat lines, millis, Integer.\n"
                            +          "   keep_alive_interval     "  +Vars.keep_alive_interval+"         -- Interval between keep_alive messages, seconds, Integer.\n"
                            +          "   save_logs               "  +Vars.savelogs+"         -- 1 = logs are saved to file, 0 otherwise.\n"
                            +          "   automagic_search        "  +Vars.automagic_search+"         -- Interval between automagic searches for each user, seconds, Integer.\n"
                            +          "   search_log_base         "  +Vars.search_log_base+"         -- Logarithmic base for user searches interval, millis, Integer.\n"
                            +          "   search_steps            "  +Vars.search_steps+"         -- Maximum nr of search steps allowed until reset needed, Integer.\n"
                            +          "   search_spam_reset       "  +Vars.search_spam_reset+"         -- Interval until search_steps is being reset, seconds, Integer.\n"
                            +          "   msg_search_spam         "  +Vars.Msg_Search_Spam+"         -- Message that appears as a result when search is delayed, String.\n"
                            +          "   bot_name                "  +Vars.bot_name+"         -- Hub security bot name, String.\n"
                            +          "   bot_desc                "  +Vars.bot_desc+"         -- Hub security bot description, String."
                            
                            
                            
                            ;
                    cur_client.sendFromBot(""+blah.replaceAll ("\\x0a","\\\n"));
                }
                else
                    cur_client.sendFromBot("Invalid cfg variable. Use \"cfg list\" to see all.");
                }
        }
        else if(recvbuf.toLowerCase ().startsWith("topic"))
        {
               if(recvbuf.toLowerCase ().equals("topic"))
               {
               if(!Vars.HubDE.equals (""))
                  new Broadcast("IINF DE");
               if(!Vars.HubDE.equals(""))
               {
               cur_client.sendFromBot("Topic \""+Vars.HubDE+"\" deleted.");
               new Broadcast("IMSG Topic was deleted by "+cur_client.NI,cur_client);
               }
               else
               cur_client.sendFromBot("There wasn't any topic anyway.");
               Vars.HubDE="";
               Main.Server.vars.HubDE="";

               
               }
               else
               {
                   String auxbuf=recvbuf.substring(6);
                   
                   
                 // Vars.HubDE=Vars.HubDE.replaceAll("\\ "," ");
                   cur_client.sendFromBot("Topic changed from \""+Vars.HubDE+"\" "+"to \""+auxbuf+"\".");
                   auxbuf=auxbuf.replaceAll(" ","\\ ");
                   Vars.HubDE=auxbuf;
                   Main.Server.vars.HubDE=auxbuf;
                   new Broadcast ("IINF DE"+retADCStr(auxbuf));
                   new Broadcast("IMSG Topic was changed by "+cur_client.NI+" to \""+Vars.HubDE+"\"");
                   
               }
               
        }
        else if(recvbuf.toLowerCase ().startsWith("port "))
        {
                try
                {
            int x=Integer.parseInt(recvbuf.substring(5));
            if(x<1 || x>65000)
           {
               cur_client.sendFromBot("What kinda port is that ?");
            return;
           }
            cur_client.sendFromBot("New default port change from "+Vars.Default_Port+" to "+recvbuf.substring(5)+". Restart for settings to take effect.");
             Vars.Default_Port=x;
             Main.Server.vars.Default_Port=x;
             Main.Server.rewriteconfig();;
                }
                catch(NumberFormatException nfe)
                {
                    cur_client.sendFromBot("Invalid port number");
                }
        }
        else if(recvbuf.toLowerCase ().equals("usercount"))
        {
                int i=0,j=0;
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.userok==1)
                    i++;
                    else j++;
                    temp=temp.NextClient;
                }
                cur_client.sendFromBot("Current user count: "+Integer.toString (i)+". In progress users: "+Integer.toString (j)+".");
        }
        else if(recvbuf.toLowerCase ().equals("about"))
        {
                cur_client.sendFromBot("\n"+Vars.About.replaceAll(" ","\\ ").replaceAll ("\\x0a","\\\n"));
        }
        else if(recvbuf.toLowerCase ().equals("history"))
        {
            String blah00="History:\n";
            line bb=Broadcast.First;
            while(bb!=null)
            {
                blah00=blah00+bb.curline;
                bb=bb.Next;
            }
            
            cur_client.sendFromBot (blah00.substring (0,blah00.length ()-1));
        }
        else if(recvbuf.toLowerCase ().equals("cmdhistory"))
        {
            String blah00="Command History:\n";
            line bb=FirstCommand;
            while(bb!=null)
            {
                blah00=blah00+bb.curline;
                bb=bb.Next;
            }
            
            cur_client.sendFromBot (blah00.substring (0,blah00.length ()-1));
        }
        else if(recvbuf.toLowerCase ().equals("stats"))
        {
                Runtime myRun=Runtime.getRuntime();
               
                       //Proppies.getProperty();
                int i=0,j=0;
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.userok==1)
                    i++;
                    else j++;
                    temp=temp.NextClient;
                }
                
                long up=System.currentTimeMillis()-Main.curtime; //uptime in millis
               // up=345345343;
                long days=up/(3600000*24);
                long hours =up/3600000-24*days;
                long minutes=up/60000-60*hours-24*60*days;
                long seconds=up/1000-60*minutes-60*24*60*days-60*60*hours;
                long millis=up-1000*seconds-60*1000*24*60*days-1000*60*60*hours-1000*60*minutes;
                
                String uptime="";
                if(days!=0)
                    uptime=Long.toString (days)+" Days ";
                if(hours!=0 || (hours==0 && days!=0))
                    uptime=uptime+Long.toString (hours)+" Hours ";
                if(minutes!=0 || (minutes==0 && (days!=0 || hours!=0)))
                    uptime=uptime+Long.toString (minutes)+" Minutes ";
                if(seconds!=0 || (seconds==0 && (days!=0 || hours!=0 || minutes!=0)))
                    uptime=uptime+Long.toString (seconds)+" Seconds ";
                uptime=uptime+Long.toString (millis)+ " Millis";
                        
               String blah=
                  "Death Squad Hub. Version "+Vars.HubVersion+".\n"+
                  "  Running on "+Main.Proppies.getProperty("os.name")+" Version "+Main.Proppies.getProperty("os.version")+" on Architecture "+Main.Proppies.getProperty("os.arch")+"\n"+
                  "  Java Runtime Environment "+Main.Proppies.getProperty("java.version")+" from "+Main.Proppies.getProperty("java.vendor")+"\n"+
                  "  Java Virtual Machine "+Main.Proppies.getProperty("java.vm.specification.version")+"\n"+
                  "  Available CPU's to JVM "+Integer.toString (myRun.availableProcessors())+"\n"+
                  "  Available Memory to JVM: "+Long.toString(myRun.maxMemory())+" Bytes, where free: "+Long.toString(myRun.freeMemory())+" Bytes\n"+
                  "Hub Statistics:\n"+
                  "  Online users: "+Integer.toString (i)+"\n"+
                  "  Connecting users: "+Integer.toString(j)+"\n"+
                  "  Uptime: "+uptime+"."
                       
                    
                  ;
              
               cur_client.sendFromBot(""+blah);
        }
        else if(recvbuf.equals(""))
            ;
        else
        {
                cur_client.sendFromBot("Unknown Command. Type !help for info.");
        }
           
            }
        
        
        
    
    
}