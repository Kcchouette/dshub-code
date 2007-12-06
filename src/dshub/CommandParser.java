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

import dshub.ExtendedCmds.ExtDrop;
import dshub.ExtendedCmds.ExtInfo;
import dshub.ExtendedCmds.ExtKick;
import dshub.ExtendedCmds.ExtMass;
import dshub.Modules.DSHubModule;
import dshub.TigerImpl.Base32;
import dshub.gui.TestFrame;
import dshub.hubtracker.HubtrackerCmd;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.net.*;
import java.util.regex.PatternSyntaxException;


/**
 * Basic client commands sent to hub via mainchat or pm to hub bot.
 * Provides configuration in hub functionality.
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
                    if(!cur_client.reg.myMask.quit)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                
                cur_client.sendFromBot("Closing down hub...");
                Main.Server.rewriteregs();
                Main.Server.rewriteconfig();
                Main.Server.rewritebans();
               //save Banned Words List
            Main.listaBanate.printFile(Main.myPath+"banwlist.txt");
                
                Main.PopMsg ("Hub is being shut down by "+cur_client.NI);
               
                
                Main.Exit();
        }
           
        
       else if(recvbuf.toLowerCase ().equals ("restart"))
            {
                    if(!cur_client.reg.myMask.restart)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
            cur_client.sendFromBot("Restarting.... Wait 5 seconds...");
                 Main.Server.rewriteregs();
               Main. Server.rewriteconfig();
               Main.Server.rewritebans ();
             Main.Server.restart=true;
              AccountsConfig.First=null;
             BanList.First=null;
             
             
                 ClientNod.FirstClient=null;
                Main.PopMsg ("Hub restarted by "+cur_client.NI);
             
             Main.Server.shutdown();
             System.gc (); //calling garbage collectors
            Main.Server=new HubServer();
         Main.curtime=System.currentTimeMillis();
         Main.Proppies=System.getProperties();
          }
        if(recvbuf.toLowerCase ().startsWith("password"))
        {
                    if(!cur_client.reg.myMask.password)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken ();
                Nod temp=AccountsConfig.First;
                while(!temp.CID.equals (   cur_client.ID))
                   temp=temp.Next;
                temp.Password=aux;
                cur_client.reg.Password=aux;
                
                Main.Server.rewriteregs();
                cur_client.sendFromBot("Your password is now "+aux+".");
               
        } 
       else if(recvbuf.toLowerCase ().startsWith("grant"))
        {
                    if(!cur_client.reg.myMask.grant)
                    {
                        cur_client.sendFromBot("Access denied.");
                        return;
                    }
                 new GrantCmd(cur_client,recvbuf);
               
        } 
         else if(recvbuf.toLowerCase().startsWith("backup"))
        {
                     if(!cur_client.reg.myMask.backup)
                    {
                        cur_client.sendFromBot("Access denied.");
                        return;
                    }
                 new BackupCmd(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().equals("gui"))
        {
                    if(!cur_client.reg.myMask.gui)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                    if(!Main.GUI.isDisplayable())
                    {
                        try
    {
          Main.GUI=new TestFrame();
          Main.GUIok=true;
         
           
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
                Main.GUI.SetStatus("GUI restored...");
             }
             else cur_client.sendFromBot("GUI not viewable.");
        }
        else if(recvbuf.toLowerCase ().startsWith("hideme"))
        {
                    if(!cur_client.reg.myMask.hideme)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                    if(!cur_client.reg.myMask.listreg)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
            String blah00="List :\n";
            Nod n=AccountsConfig.First;
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
                    if(!cur_client.reg.myMask.listban)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
            String blah00="Ban List :\n";
            Ban n=BanList.First;
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
                     if(!cur_client.reg.myMask.ureg)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                if(AccountsConfig.unreg (aux))
                {
                    ClientNod temp=ClientNod.FirstClient.NextClient;
                     while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.ID.equals(aux)))
                                break;
                            temp=temp.NextClient;
                        }
                    if(temp!=null)
                    {
                            temp.cur_client.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.".replaceAll(" ","\\ "));
                            temp.cur_client.putOpchat(false);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="";}else{temp.cur_client.RG="";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)-1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)-1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)+1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP":"RG")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            temp.cur_client.reg=new Nod();
                            cur_client.sendFromBot("User "+temp.cur_client.NI+" with CID "+aux+" found, deleted.");
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
                      ClientNod temp=ClientNod.FirstClient.NextClient;
                        while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                            cur_client.sendFromBot("No such client online.");
                        else if(!temp.cur_client.reg.isreg)
                          cur_client.sendFromBot("Client exists but not registered.");
                        else
                        {
                            AccountsConfig.unreg(temp.cur_client.ID);
                            Main.PopMsg (cur_client.NI+" deleted the reg "+temp.cur_client.ID);
                            cur_client.sendFromBot("User "+temp.cur_client.NI+" deleted.");
                            temp.cur_client.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.".replaceAll(" ","\\ "));
                            temp.cur_client.putOpchat(false);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="";}else{temp.cur_client.RG="";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)-1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)-1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)+1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP":"RG")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            
                            temp.cur_client.reg=new Nod();
                        }
                }
                Main.Server.rewriteregs ();
                
        }
        else if(recvbuf.toLowerCase ().startsWith("reg"))
        {
                     if(!cur_client.reg.myMask.reg)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                        if(AccountsConfig.isReg (aux)>0)
                        {
                            
                            cur_client.sendFromBot(""+AccountsConfig.getnod (aux).getRegInfo ());
                            return;
                        }
                        ClientNod temp=ClientNod.FirstClient.NextClient;
                        while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.ID.equals(aux)))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                        {
                            AccountsConfig.addReg (aux,null,cur_client.NI);
                            cur_client.sendFromBot("CID added. No password set, login does not require pass, however, its recomandable to set one...");
                            
                        }   
                        else
                        {
                            AccountsConfig.addReg (temp.cur_client.ID,temp.cur_client.NI,cur_client.NI);
                            temp.cur_client.reg=AccountsConfig.getnod (temp.cur_client.ID);
                            cur_client.sendFromBot("User "+temp.cur_client.NI+" found with CID "+aux+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            temp.cur_client.sendFromBot(""+"You have been registered by "+cur_client.NI+" . No password set, login does not require pass, however, its recomandable you to set one...");
                            temp.cur_client.putOpchat(true);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="1";}else{temp.cur_client.RG="1";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)+1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)+1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)-1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP1":"RG1")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            
                            temp.cur_client.reg.isreg=true;
                            temp.cur_client.LoggedAt=System.currentTimeMillis();
                            temp.cur_client.reg.LastIP=temp.cur_client.RealIP;
                        
                        }
                        Main.PopMsg (cur_client.NI+" regged the CID "+aux);
                        
                    }
                    catch (IllegalArgumentException iae)
                    {
                        //cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                        ClientNod temp=ClientNod.FirstClient.NextClient;
                        while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                        { cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");cur_client.sendFromBot("No such client online.");}
                        else
                        {
                         if(AccountsConfig.isReg (temp.cur_client.ID)>0)
                        {
                            cur_client.sendFromBot(""+AccountsConfig.getnod (temp.cur_client.ID).getRegInfo ());
                            return;
                        }
                            AccountsConfig.addReg (temp.cur_client.ID,temp.cur_client.NI,cur_client.NI);
                            temp.cur_client.reg=AccountsConfig.getnod (temp.cur_client.ID);
                            cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                            cur_client.sendFromBot("User "+temp.cur_client.NI+" found with CID "+temp.cur_client.ID+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            temp.cur_client.sendFromBot(""+"You have been registered by "+cur_client.NI+" . No password set, login does not require pass, however, its recomandable you to set one...");
                            temp.cur_client.putOpchat(true);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="1";}else{temp.cur_client.RG="1";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)+1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)+1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)-1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP1":"RG1")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            
                            temp.cur_client.reg.isreg=true;
                            temp.cur_client.LoggedAt=System.currentTimeMillis();
                            temp.cur_client.reg.LastIP=temp.cur_client.RealIP;
                            Main.PopMsg (cur_client.NI+" regged the CID "+temp.cur_client.ID);
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
                        ClientNod temp=ClientNod.FirstClient.NextClient;
                       while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                        }
                        if(temp==null)
                        {  cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");cur_client.sendFromBot("No such client online.");}
                        else
                        {
                            if(AccountsConfig.isReg (temp.cur_client.ID)>0)
                        {
                            cur_client.sendFromBot(""+AccountsConfig.getnod (temp.cur_client.ID).getRegInfo ());
                            return;
                        }
                            AccountsConfig.addReg (temp.cur_client.ID,temp.cur_client.NI,cur_client.NI);
                            temp.cur_client.reg=AccountsConfig.getnod (temp.cur_client.ID);
                            cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                            cur_client.sendFromBot("User "+temp.cur_client.NI+" found with CID "+temp.cur_client.ID+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            temp.cur_client.sendFromBot(""+"You have been registered by "+cur_client.NI+" . No password set, login does not require pass, however, its recomandable you to set one...");
                            temp.cur_client.putOpchat(true);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="1";}else{temp.cur_client.RG="1";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)+1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)+1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)-1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP1":"RG1")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            
                            temp.cur_client.LoggedAt=System.currentTimeMillis();
                            temp.cur_client.reg.LastIP=temp.cur_client.RealIP;
                            Main.PopMsg (cur_client.NI+" regged the CID "+temp.cur_client.ID);
                        }
                }
                
                Main.Server.rewriteregs();
                //sendFromBot("Your password is now "+aux+".");
               
        } 
        else if(recvbuf.toLowerCase ().equals("help"))
        {
           
                 if(!cur_client.reg.myMask.help)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
            
                
                        
                         //Main.HelpText.substring(0,Main.HelpText.length ()-1).replaceAll ("\\x0a","\\\n");
                        ;
                cur_client.sendFromBot (cur_client.reg.myHelp.getHelp());
               
        }
        else if(recvbuf.toLowerCase ().startsWith ("info "))
        {
                     if(!cur_client.reg.myMask.info)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                new ExtInfo(cur_client,recvbuf);
        }
        
        else if(recvbuf.toLowerCase ().startsWith ("mass"))
        {
                     if(!cur_client.reg.myMask.mass)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
            new ExtMass(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("mynick "))
        {
                     if(!cur_client.reg.myMask.mynick)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                if(!Vars.ValidateNick (aux))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       System.out.println (aux);
                       return;
                   }
                if(AccountsConfig.nickReserved(aux,cur_client.ID))
                {
                    
                   cur_client.sendFromBot("Nick reserved. Please choose another.");
                    return;
                }
                
                    ClientNod tempy=ClientNod.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if(tempy.cur_client.userok==1) if( (tempy.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
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
                     if(!cur_client.reg.myMask.rename)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                    //cur_client.sendFromBot(""+ADC.retADCStr("Sorry but renaming features are temporary disabled until DC++ has UCMD's ( because !rename mister bla new nick has 4 entities and its quite hard to guess what is first nick and what is 2nd nick."));
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken (); //the nick to rename;
               // aux=ADC.retADCStr(aux);
                ClientNod temp=ClientNod.FirstClient.NextClient;
                
                while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           
                        }
               if(temp==null)
                         cur_client.sendFromBot("No such user online.");
               else if(!temp.cur_client.reg.renameable)
                         cur_client.sendFromBot("This registered user cannot be renamed.");
              
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
                    if(!Vars.ValidateNick (newnick) )
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       return;
                   }
                 if(AccountsConfig.nickReserved(newnick,temp.cur_client.ID))
                {
                    
                   cur_client.sendFromBot("Nick reserved. Please choose another.");
                    return;
                }
                    ClientNod tempy=ClientNod.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if(tempy.cur_client.userok==1) if( (tempy.cur_client.NI.toLowerCase ().equals(newnick.toLowerCase ())))
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
                    new Broadcast("BINF "+temp.cur_client.SessionID+" NI"+newnick);
                    
                    
                    
                    cur_client.sendFromBot("Renamed user "+temp.cur_client.NI+" to "+newnick);
                     new Broadcast("IMSG "+temp.cur_client.NI+" is now known as "+newnick);
                     temp.cur_client.NI=newnick;
                    
                }
        }
        else if(recvbuf.toLowerCase ().startsWith ("kick"))
        {
                     if(!cur_client.reg.myMask.kick)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                   new ExtKick(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("chatcontrol"))
        {
                     if(!cur_client.reg.myMask.chatcontrol)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                   new ChatControlCmd(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("drop"))
        {
                     if(!cur_client.reg.myMask.drop)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                    new ExtDrop(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith ("unban"))
        {
                     if(!cur_client.reg.myMask.unban)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                     if(!cur_client.reg.myMask.bancid)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to Ban;
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
                    
                ClientNod temp=ClientNod.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.ID.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           // tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                       if(!temp.cur_client.reg.kickable )
                       { cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+" belonging to"+temp.cur_client.NI+", but is not kickable.");}
                       
                       else
                       {
                        
                      cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+", banning..");
                     temp.kickMeOut (cur_client,reason,3,-1L);
                       }
                        
                    
                 else
                 {
                     if(!(AccountsConfig.getnod (aux).kickable))
                     { cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found CID "+aux+" belonging to"+temp.cur_client.NI+", but is not kickable.");}
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
                    ClientNod temp=ClientNod.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            //tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                       if(!(temp.cur_client.reg.kickable ))
                         cur_client.sendFromBot("Found user "+temp.cur_client.NI+" with CID "+temp.cur_client.ID+", but its unkickable.");
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
                     if(!cur_client.reg.myMask.bannick)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to Ban;
                aux=ADC.retADCStr(aux);
                //al right,now must check if that is a nick online of offline
                
                String reason="";
                while(ST.hasMoreTokens ())
                 reason =reason+ST.nextToken ()+" ";
                
                 if(!reason.equals(""))
                
                        reason=reason.substring (0,reason.length ()-1);
                    reason=ADC.retADCStr(reason);
                ClientNod temp=ClientNod.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                           // tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                       if(!temp.cur_client.reg.kickable)
                       { cur_client.sendFromBot("Searching...");cur_client.sendFromBot("Found Nick "+aux+" but it belongs to an unkickable reg.");}
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
                     if(!cur_client.reg.myMask.banip)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                StringTokenizer ST=new StringTokenizer(ADC.retNormStr(recvbuf));
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken (); //the thing to Ban;
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
                    
                ClientNod temp=ClientNod.FirstClient.NextClient;
               // ClientHandler tempyprev=ClientHandler.FirstClient;
                  while(temp!=null)
                  {
                            if(temp.cur_client.userok==1) 
                                if( temp.cur_client.RealIP.equals(aux))
                                {
                         if(!temp.cur_client.reg.kickable)
                         {cur_client.sendFromBot("Searching...");
                          cur_client.sendFromBot("Found IP "+aux+" belonging to "+temp.cur_client.NI+", but its unkickable. Not banned.");
                          cur_client.sendFromBot("Done.");
                         return;}
                                }
                            temp=temp.NextClient;
                  }
                temp=ClientNod.FirstClient.NextClient;
                int kickedsome=0;
                while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) 
                                if( temp.cur_client.RealIP.equals(aux))
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
                    ClientNod temp=ClientNod.FirstClient.NextClient;
                //ClientHandler tempyprev=ClientHandler.FirstClient;
                while(temp!=null)
                        {
                            if(temp.cur_client.userok==1) if( temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ()))
                                break;
                            temp=temp.NextClient;
                            //tempyprev=tempyprev.NextClient;
                        }
                if(temp!=null)
                {  
                        if(!temp.cur_client.reg.kickable)
                         cur_client.sendFromBot("Found user "+temp.cur_client.NI+" with IP "+temp.cur_client.RealIP+", but its unkickable.Not banned.");
                       else
                       {
                        
                      cur_client.sendFromBot("Found user "+aux+" with IP "+temp.cur_client.RealIP+", banning..");
                     
                   
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
                     if(!cur_client.reg.myMask.cfg)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                new CFGConfig(cur_client,recvbuf);
        }
        else if(recvbuf.toLowerCase ().startsWith("topic"))
        {
                     if(!cur_client.reg.myMask.topic)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
               if(recvbuf.toLowerCase ().equals("topic"))
               {
               if(!Vars.HubDE.equals (""))
                  new Broadcast("IINF DE");
               if(!Vars.HubDE.equals(""))
               {
               cur_client.sendFromBot("Topic \""+Vars.HubDE+"\" deleted.");
               new Broadcast("IMSG Topic was deleted by "+cur_client.NI,cur_client.myNod);
               }
               else
               cur_client.sendFromBot("There wasn't any topic anyway.");
               Vars.HubDE="";
               

               
               }
               else
               {
                   String auxbuf=recvbuf.substring(6);
                   
                   
                 // Vars.HubDE=Vars.HubDE.replaceAll("\\ "," ");
                   cur_client.sendFromBot("Topic changed from \""+Vars.HubDE+"\" "+"to \""+auxbuf+"\".");
                   auxbuf=auxbuf.replaceAll(" ","\\ ");
                   Vars.HubDE=auxbuf;
                   
                   new Broadcast ("IINF DE"+ADC.retADCStr(auxbuf));
                   new Broadcast("IMSG Topic was changed by "+cur_client.NI+" to \""+Vars.HubDE+"\"");
                   
               }
               
        }
        else if(recvbuf.toLowerCase ().startsWith("port "))
        {
                     if(!cur_client.reg.myMask.port)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
            
             Main.Server.rewriteconfig();;
                }
                catch(NumberFormatException nfe)
                {
                    cur_client.sendFromBot("Invalid port number");
                }
        }
        else if(recvbuf.toLowerCase ().equals("usercount"))
        {
                     if(!cur_client.reg.myMask.usercount)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                int i=0,j=0;
                ClientNod temp=ClientNod.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.cur_client.userok==1)
                    i++;
                    else j++;
                    temp=temp.NextClient;
                }
                cur_client.sendFromBot("Current user count: "+Integer.toString (i)+". In progress users: "+Integer.toString (j)+".");
        }
        else if(recvbuf.toLowerCase ().equals("about"))
        {
                     if(!cur_client.reg.myMask.about)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                cur_client.sendFromBot("\n"+Vars.About.replaceAll(" ","\\ ").replaceAll ("\\x0a","\\\n"));
        }
        else if(recvbuf.toLowerCase ().equals("history"))
        {
                     if(!cur_client.reg.myMask.history)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                     if(!cur_client.reg.myMask.cmdhistory)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
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
                     if(!cur_client.reg.myMask.stats)
                    {
                        cur_client.sendFromBot ("Access denied.");
                        return;
                    }
                Runtime myRun=Runtime.getRuntime();
               
                       //Proppies.getProperty();
                int i=0,j=0;
                ClientNod temp=ClientNod.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.cur_client.userok==1)
                    i++;
                    else j++;
                    temp=temp.NextClient;
                }
                
               
                         long up=System.currentTimeMillis()-Main.curtime; //uptime in millis
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
                  "  Uptime: "+TimeConv.getStrTime(up)+
                   "\n  Bytes red per second: "+Main.Server.IOSM.getTotalByteReadThroughput()+
                   "\n  Bytes written per second: "+Main.Server.IOSM.getTotalByteWrittenThroughput()
                       
                    
                  ;
              
               cur_client.sendFromBot(""+blah);
        }
        else if(recvbuf.equals(""))
            ;
        else
        {
                cur_client.sendFromBot("Unknown Command. Type !help for info.");
        }
          Iterator x=Modulator.myModules.iterator();     
        while(x.hasNext())
            ((DSHubModule)(x.next())).onCommand(cur_client,recvbuf);
           
            }
        
        
        
    
    
}
