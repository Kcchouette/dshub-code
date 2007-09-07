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
public class CommandParser 
{
    ClientHandler cur_client;
    String cmd;
    /**for !cmdhistory*/
    static line FirstCommand=null;
    static line LastCommand=null;
    static int size=0;
    
    
    
    
    /** Creates a new instance of CommandParser */
    public CommandParser (ClientHandler CH, String cmd)
    {
        cur_client=CH;
        this.cmd=cmd;
        //this.setPriority (NORM_PRIORITY);
        run();
    }
    public void run()
    {
        String recvbuf=ADC.retNormStr(cmd.substring (1));
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
                
                cur_client.sendFromBot("Closing down hub...");
                Main.Server.rewriteregs();
                Main.Server.rewriteconfig();
                Main.Server.rewritebans();
                while(cur_client.Queue.First!=null)
                {
                cur_client.PS.printf ("%s\n",cur_client.Queue.First.MSG);
                
                cur_client.Queue.First=cur_client.Queue.First.Next;
                }
                cur_client.PS.flush ();
                
                Main.PopMsg ("Hub is being shut down by "+cur_client.NI);
                try
                {
                cur_client.sleep (1000);
                }
                catch (Exception e)
                {
                    //System.exit(0);
                    System.out.println(e);
                }
                  //System.exit(0);
                cur_client.PS.close ();
                Main.Exit();
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
             while(cur_client.Queue.First!=null)
                {
                cur_client.PS.printf ("%s\n",cur_client.Queue.First.MSG);
                
                cur_client.Queue.First=cur_client.Queue.First.Next;
                }
                cur_client.PS.flush ();
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
                    if(!Main.GUI.isDisplayable())
                    {
                        try
    {
          Main.GUI=new TestFrame();
          Main.GUIok=true;
          Main.GUI.SetStatus("GUI restored...");
           
    }
    catch (Exception e)
    {
        cur_client.sendFromBot("GUI not viewable.");
        Main.GUIok=false;
    }
                    }
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
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                String aux=ST.nextToken (); //the thing to check;
                while(ST.hasMoreTokens ())
                    aux+=ST.nextToken ();
                aux=ADC.retADCStr(aux);
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
              
                if(ADC.isIP(aux))//we have an IP address
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
            new ExtMass(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("mynick "))
        {
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                String aux="";
                while (ST.hasMoreTokens ())
                 aux=aux+ST.nextToken ()+" "; //new nick
                if(!(aux.equals ("")))
                aux=aux.substring (0,aux.length ()-1);
                aux=ADC.retADCStr(aux);
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
                if(!Vars.ValidateNick (aux) || ADC.isIP(aux) || ADC.isCID(aux))
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
                    //cur_client.sendFromBot(""+ADC.retADCStr("Sorry but renaming features are temporary disabled until DC++ has UCMD's ( because !rename mister bla new nick has 4 entities and its quite hard to guess what is first nick and what is 2nd nick."));
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken (); //the nick to rename;
               // aux=ADC.retADCStr(aux);
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
                    if(!Vars.ValidateNick (newnick) || ADC.isIP(newnick) || ADC.isCID(newnick))
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
                   new ExtKick(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("drop"))
        {
                    new ExtDrop(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("unban"))
        {
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                {
                    cur_client.sendFromBot ("Nothing specified for unbanning.");
                    return;
                }
                String aux=ST.nextToken (); //the thing to unban;
                //al right,now must check if that is a nick, cid or ip
                //first if its a cid...
                aux=ADC.retADCStr(aux);
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
                    if(ADC.isIP(aux))
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
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to ban;
                aux=ADC.retADCStr (aux);
                //al right,now must check if that is a nick, cid or ip
                //first if its a cid...
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                reason=ADC.retADCStr(reason);
               // System.out.println (reason);
               if(ADC.isCID(aux))
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
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to ban;
                aux=ADC.retADCStr(aux);
                //al right,now must check if that is a nick online of offline
                
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                    reason=ADC.retADCStr(reason);
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
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to ban;
                aux=ADC.retADCStr(aux);
                //al right,now must check if that is a  ip or nick
                //first if its a ip...
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                reason=ADC.retADCStr(reason);
                 if(ADC.isIP(aux))
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
                new CFGConfig(cur_client,recvbuf);
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
                   new Broadcast ("IINF DE"+ADC.retADCStr(auxbuf));
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
                blah00=blah00+ADC.retNormStr(bb.curline);
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
                blah00=blah00+ADC.retNormStr(bb.curline);
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
