package dshub;
/*
 * Main.java
 *
 * Created on 03 martie 2007, 22:57
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

import dshub.TigerImpl.Base32;
import dshub.gui.TestFrame;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.JOptionPane;




/**
 * DSHub main class, contains main function ( to call when start application )
 * Provides registration functions, calls the GUI.
 * Listens to System.in in tty for commands ( if run via java command line )
 *
 * @author Pietricica
 */


public class Main extends Thread
{
    
    /** Creates a new instance of Main */
   
    
    public static HubServer Server;
    public static Properties Proppies;
    public static String auxhelp;
    public static BanWordsList listaBanate;
      
      public  static String MOTD="";
      public  static long curtime;
    public static TestFrame GUI;
    public static String myPath;
    public static boolean GUIok=true;
    public static boolean GUIshowing=false;
    public static void PopMsg(String bla)   
    {
        System.out.println (bla);
        if(Main.GUIok)
            Main.GUI.insertLog (bla);
        Date d=new Date(Main.curtime);
        if(Vars.savelogs==1)
        {
            File logTest=new File((Main.myPath.equals("")?"":(Main.myPath+"/"))+"log");
            boolean result=true;
            if(!logTest.exists())
                result=logTest.mkdir();
            if(result)
            {
            File logFile=new File((Main.myPath.equals("")?"":(Main.myPath+"/"))+"log/"+d.toString ().replaceAll (" ","_").replaceAll (":","_")+".log");
            FileOutputStream logOutput=null;
            try
            {
            logOutput=new FileOutputStream(logFile,true);
            }
            catch(Exception e)
            {System.out.println (e);}
            DataOutputStream out   = new DataOutputStream(logOutput);
             
            try
            {
                out.writeBytes (bla+"\r\n");
            out.close ();
            }
            catch(Exception e)
            {}
            }
        }
    }
    
    public static void init()
    {
        ClassLoader cl = ClassLoader.getSystemClassLoader (  )  ;
       String bla= System.getProperty ("java.class.path");
       String bla2=System.getProperty ("user.dir");
       ;
       String separator=System.getProperty ("file.separator");
       String pathsep=System.getProperty("path.separator");
         
      bla=bla.replace ('\\',separator.charAt (0));
      bla=bla.replace ('/',separator.charAt (0));
       if(!bla2.endsWith (separator))
           bla2=bla2+separator;
        
        myPath=bla2+bla;
        // System.out.println (myPath);
        
             int x=myPath.lastIndexOf(separator.charAt (0));
         if(x!=-1)
         myPath=myPath.substring (0,x+1);
        
       
        //if("c:\\blaaa".matches("[a-zA-Z]:\\\\.*"))
        //System.out.println("ok");
        if(bla.matches("[a-zA-Z]:\\\\.*"))
        {
           int y= bla.indexOf (';');
           while(y!=-1)
           {
               bla=bla.substring (y+1,bla.length ());
                 y= bla.indexOf (';');
           }
           
           myPath=bla;
           if(myPath.endsWith (".jar")|| myPath.endsWith (".jar"+separator))
           {
               myPath=myPath.substring (0,myPath.lastIndexOf (separator));
           }
           if(!myPath.endsWith (separator))
               myPath=myPath+separator;
           //Main.PopMsg(myPath);
           if(myPath.equals("\\"))
               myPath="";
        }
        if(System.getProperty("os.name").equals("Linux"))
        {
            StringTokenizer st1=new StringTokenizer(myPath,pathsep);
            String aux=st1.nextToken();
            //System.out.println(myPath);
            while(!(aux.toLowerCase().contains("dshub.jar".toLowerCase())) && st1.hasMoreTokens())
                aux=st1.nextToken();
           // if(!st1.hasMoreTokens())
            
              //  Main.PopMsg("FAIL. Java Classpath Error.");
             //   return;
            
            myPath=aux;
            
            
        }
           
    }
       public static void Exit()
       {
            Server.rewriteregs();
            Server.rewriteconfig();
            
            //save Banned Words List
            listaBanate.printFile(Main.myPath+"banwlist.txt");
            
            PopMsg("Closing down hub.");
        try {
            sleep(500);
        } catch (InterruptedException ex) {
           // ex.printStackTrace();
        }
            System.exit(0);
       }
       public static void Restart()
       {
         PopMsg("Restarting.... Wait 5 seconds...");
         Main.Server.rewriteregs();
               Main. Server.rewriteconfig();
               Main.Server.rewritebans ();
             Main.Server.restart=true;
              
             AccountsConfig.First=null;
             BanList.First=null;
             SimpleHandler.Users.clear();
            
               
             Server.shutdown();
             System.gc (); //calling garbage collectors
            Main.Server=new HubServer();
         Main.curtime=System.currentTimeMillis();
         Main.Proppies=System.getProperties();
       }
      public static void Reg(String aux)
      {
           if(aux.length ()==39) //possible CID, lets try
                {
                    try
                    {
                        Base32.decode (aux);
                        if(AccountsConfig.isReg (aux)>0)
                        {
                            
                            System.out.println (AccountsConfig.getnod (aux).getRegInfo ());
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("Already regged.",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        
                         for(ClientNod temp : SimpleHandler.Users)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.ID.equals(aux)))
                               {
                            AccountsConfig.addReg (temp.cur_client.ID,temp.cur_client.NI,"Server");
                            temp.cur_client.reg=AccountsConfig.getnod (temp.cur_client.ID);
                            PopMsg("User "+temp.cur_client.NI+" found with CID "+aux+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            
                            temp.cur_client.sendFromBot(""+"You have been registered by "+"Server"+" . No password set, login does not require pass, however, its recomandable you to set one...");
                            temp.cur_client.putOpchat(true);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="1";}else{temp.cur_client.RG="1";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)+1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)+1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)-1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP1":"RG1")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            
                            temp.cur_client.reg.LastIP=temp.cur_client.RealIP;
                            temp.cur_client.reg.isreg=true;
                            temp.cur_client.LoggedAt=System.currentTimeMillis();
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("User "+temp.cur_client.NI+" found with given CID, added with no password, he should set one.");
                        
                            
                            Main.Server.rewriteregs();
                            return;
                        }
                        }
                        
                            AccountsConfig.addReg (aux,null,"Server");
                            Nod x=AccountsConfig.getnod(aux);
                             x.isreg=true;
                            PopMsg("CID added. No password set, login does not require pass, however, its recomandable to set one...");
                             if(Main.GUIok)
                                Main.GUI.SetStatus ("CID added with no password, he should set one.");
                             
                             
                       
                        
                        
                    }
                    catch (IllegalArgumentException iae)
                    {
                        //cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                        for(ClientNod temp : SimpleHandler.Users)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                               {
                         if(AccountsConfig.isReg (temp.cur_client.ID)>0)
                        {
                            System.out.println (AccountsConfig.getnod (temp.cur_client.ID).getRegInfo ());
                             if(Main.GUIok)
                                Main.GUI.SetStatus ("Already regged.",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                            AccountsConfig.addReg (temp.cur_client.ID,temp.cur_client.NI,"Server");
                            temp.cur_client.reg=AccountsConfig.getnod (temp.cur_client.ID);
                            
                            temp.cur_client.sendFromBot(""+"You have been registered by "+"Server"+" . No password set, login does not require pass, however, its recomandable you to set one...");
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
                            PopMsg("Not a CID, trying to add the "+aux+" nick.\nUser "+temp.cur_client.NI+" found with CID "+temp.cur_client.ID+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("Found user online, added. No password set, he should set one.");
                            Main.Server.rewriteregs();
                            return;
                        }
                        }
                        
                            PopMsg("Not a CID, trying to add the "+aux+" nick.\nNo such client online.");
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("Not a CID nor such user online.",JOptionPane.WARNING_MESSAGE);
                       
                        
                            
                    }
                    catch (Exception e)
                    {
                      return ;
                    }
                }
                else 
                {
                    //cur_client.sendFromBot("Not a CID, trying to add the "+aux+" nick.");
                         for(ClientNod temp : SimpleHandler.Users)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                            {
                                  if(AccountsConfig.isReg (temp.cur_client.ID)>0)
                        {
                           System.out.println (AccountsConfig.getnod (temp.cur_client.ID).getRegInfo ());
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("Already regged.",JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                            AccountsConfig.addReg (temp.cur_client.ID,temp.cur_client.NI,"Server");
                            temp.cur_client.reg=AccountsConfig.getnod (temp.cur_client.ID);
                           
                            temp.cur_client.sendFromBot(""+"You have been registered by "+"Server"+" . No password set, login does not require pass, however, its recomandable you to set one...");
                            temp.cur_client.putOpchat(true);
                            if(temp.cur_client.reg.key){temp.cur_client.OP="1";}else{temp.cur_client.RG="1";};
                            if(temp.cur_client.reg.key)
                            temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)+1);
                            else
                              temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)+1);  
                            temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)-1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP1":"RG1")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                            
                            temp.cur_client.LoggedAt=System.currentTimeMillis();
                            temp.cur_client.reg.isreg=true;
                            temp.cur_client.reg.LastIP=temp.cur_client.RealIP;
                            PopMsg("Not a CID, trying to add the "+aux+" nick.\nUser "+temp.cur_client.NI+" found with CID "+temp.cur_client.ID+", added. No password set, login does not require pass, however, its recomandable to set one...");
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("Found user online, added. No password set, he should set one.");
                            Main.Server.rewriteregs();
                            return;
                        }
                        }
                        
                            PopMsg("Not a CID, trying to add the "+aux+" nick.\nNo such client online.");
                        
                        
                            if(Main.GUIok)
                                Main.GUI.SetStatus ("Not a CID nor such user online.",JOptionPane.WARNING_MESSAGE);
                        
                        
                         
                }
                
                Main.Server.rewriteregs();
                //sendFromBot("Your password is now "+aux+".");
      }
    /**
     * @param args the command line arguments
     */
    public static void main(String [] args) 
    {   
        //
        System.out.println ("Initializing DSHub Theta ...");
        curtime=System.currentTimeMillis();
        init();
        
        
       
        
        
        
        
        
        
            //init banned words list
        Main.listaBanate=new BanWordsList();
        Main.listaBanate.loadFile(Main.myPath+"banwlist.txt");
        
        //System.out.println(Modulator.findModule("dshub.Main.class"));    
        
                      /*     Tiger myTiger = new Tiger();
						
		              myTiger.engineReset();
		                myTiger.init();	
                               String result1="aabbcc";
                               byte [] b1=result1.getBytes();
                                
		         myTiger.engineUpdate(b1,0,b1.length);
				
		            byte[] final1 = myTiger.engineDigest();
		        System.out.println("all in one "+Base32.encode (final1));
                        
                        Tiger myTiger2=new Tiger();
                        myTiger2.engineReset();
                        myTiger2.init();
                        String resultx="aa";
                        String resulty="bbcc";
                        byte [] bx=resultx.getBytes();
                        myTiger.engineUpdate(bx,0,bx.length);
                        
                        byte [] blaa=myTiger.engineDigest();
                       
                        //myTiger.engineUpdate(blaa,0,blaa.length);
                        byte [] by=resulty.getBytes();
                        myTiger.engineUpdate(by,0,by.length);
                        byte[] final2 = myTiger.engineDigest();
		        System.out.println("separately "+Base32.encode (final2));
                        */
       /* try{
            long x;
        System.out.println(x=TimeConv.getLongTime("#5h"));
        System.out.println(TimeConv.getStrTime(x));
        
        ;}catch(Exception e){System.out.println(e);}
        */
    try
    {
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
          GUI=new TestFrame();
          GUIok=true;
          GUIshowing=true;
           
    }
    catch (Exception e)
    {
        System.out.println ("GUI not viewable. "+e);
        GUIok=false;
    }
        
    
        
         Server=new HubServer();

         
         PopMsg("This program is distributed in the hope that it will be useful,\r\nbut WITHOUT ANY WARRANTY; without even the implied warranty of\r\nMERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\nGNU General Public License for more details.");
         
         Proppies=System.getProperties();
       //  GUI.setExtendedState(javax.swing.JFrame.ICONIFIED);
         if(GUIok)
        if(GUI.isDisplayable ()&& !GUI.isShowing ())
             {
                GUI.setVisible (true);
                System.out.println ("GUI launched...");
                
             }
             else {
             System.out.println ("GUI not viewable.");
             GUIok=false;
             }
         PopMsg("Done.");
             System.out.println("Parsing to Command Mode.Help for info,quit to quit.");
             
       
        InputStreamReader b=new InputStreamReader(System.in);
        BufferedReader bla=new BufferedReader(b);
        
        
        try{
            
            while(true)
            {
                
            String recvbuf=bla.readLine();
            //System.out.println(recvbuf);
        //System.out.println(bla.readLine());
       // System.out.println(recvbuf);
            if(recvbuf==null)
            {
                    try
                    {
                        sleep(500);
                    } catch (InterruptedException ex)
                    {
                        ;
                    }
                continue;
            }
        if(recvbuf.toLowerCase ().equals("quit"))
        {
              Exit();
        }
            
        if(recvbuf.toLowerCase ().equals("help"))
        {
                Nod ServerNode=new Nod();
                ServerNode.myMask=new CommandMask(1);
                
                System.out.printf(
                        
                         ServerNode.myHelp.getHelp ()+"\n",
                        Proppies.getProperty("os.name"),
                        Proppies.getProperty("os.version"),
                        Proppies.getProperty("os.arch"));
               
        }
         else if(recvbuf.toLowerCase ().equals("gui"))
        {
             if(!Main.GUI.isDisplayable())
                    {
                        try
    {
          Main.GUI=new TestFrame();
          Main.GUIok=true;
          Main.GUIshowing=true;
          Main.GUI.SetStatus("GUI restored...");
           
    }
    catch (Exception e)
    {
        System.out.println("GUI not viewable.");
        Main.GUIok=false;
    }   
             }
            if(GUIok)
             if(GUI.isDisplayable ()&& !GUI.isShowing ())
             {
                GUI.setVisible (true);
                System.out.println ("GUI launched...");
                //GUIok=true;
             }
             else System.out.println ("GUI not viewable.");
        }
       else if(recvbuf.toLowerCase ().equals ("restart"))
            {
              Restart();
          }
         else if(recvbuf.toLowerCase ().startsWith ("unban "))
        {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken (); //the thing to unban;
                //al right,now must check if that is a nick, cid or ip
                //first if its a cid...
                try
                {
                    Base32.decode(aux);
                    //ok if we got here it really is a CID so:
                    if(aux.length ()!=39)
                        throw new IllegalArgumentException();
                    if(BanList.delban(3,aux))
                    {
                        System.out.println ("Searching...\nFound CID "+aux+", unbanned.");
                        
                        
                    }
                    else
                        System.out.println ("Searching...\n Found CID "+aux+", not banned nothing to do.");
                }
                catch(IllegalArgumentException iae)
                {
                    //ok its not a cid, lets check if its some IP address...
                    System.out.println ("Not a CID, Searching...");
                    if(ADC.isIP(aux))
                    {
                        System.out.println ("Is IP ...checking if banned...");
                        if(BanList.delban (2,aux))
                            System.out.println ("Found IP address "+aux+", unbanned.");
                        else
                            System.out.println ("Found IP address "+aux+", but is not banned, nothing to do.");
                    }
                    else 
                    {
                        System.out.println ("Is not IP...Checking for nick...");
                        if(BanList.delban (1,aux))
                            System.out.println ("Found nick "+aux+", unbanned.");
                        else
                            System.out.println ("Nick "+aux+" is not banned, nothing to do.");
                    }
                }
                System.out.println ("Done.");
                    Main.Server.rewritebans ();
                    
                
        }
        else if(recvbuf.toLowerCase ().equals("listreg"))
        {
            String blah00="Reg List :\n";
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
            blah00=blah00.substring (0,blah00.length ()-2);
            System.out.println (blah00);
        }
        else if(recvbuf.toLowerCase ().startsWith("ureg "))
        {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                 if(!ST.hasMoreTokens ())
                    continue;
                String aux=ST.nextToken ();
                try
                {
                if(aux.length ()!=39)
                    throw new IllegalArgumentException();
                Base32.decode (aux);
                if(AccountsConfig.unreg (aux))
                {
                    for(ClientNod temp : SimpleHandler.Users)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.ID.equals(aux)))
                             {
                            temp.cur_client.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.");
                            temp.cur_client.putOpchat(false);
                            temp.cur_client.OP="";
                            temp.cur_client.HO=Integer.toString (Integer.parseInt(temp.cur_client.HO)-1);
                            temp.cur_client.HN=Integer.toString (Integer.parseInt(temp.cur_client.HN)+1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP":"RG")+""+" HO"+temp.cur_client.HO+" HN"+temp.cur_client.HN);
                            temp.cur_client.reg=new Nod();
                            System.out.println ("User "+temp.cur_client.NI+" with CID "+aux+" found, deleted.");
                            
                            Main.Server.rewriteregs ();
                            return;
                            }
                        }
                     
                         System.out.println ("Reg deleted.");
                }
                else
                    System.out.println ("Reg not found.");
                }
                catch (IllegalArgumentException iae)
                {
                    System.out.println ("Not a valid CID, checking for possible users...");
                     for(ClientNod temp : SimpleHandler.Users)
                        {
                            if(temp.cur_client.userok==1) if( (temp.cur_client.NI.toLowerCase ().equals(aux.toLowerCase ())))
                             {
                            AccountsConfig.unreg(temp.cur_client.ID);
                           System.out.println ("User "+temp.cur_client.NI+" deleted.");
                            temp.cur_client.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.");
                            temp.cur_client.putOpchat(false);
                            temp.cur_client.OP="";
                            temp.cur_client.HO=Integer.toString (Integer.parseInt(temp.cur_client.HO)-1);
                            temp.cur_client.HN=Integer.toString (Integer.parseInt(temp.cur_client.HN)+1);
                            new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP":"RG")+""+" HO"+temp.cur_client.HO+" HN"+temp.cur_client.HN);
                            temp.cur_client.reg=new Nod();
                            }
                            Main.Server.rewriteregs ();
                            return;
                        }
                       
                            System.out.println ("No such client online.");

                        
                }
                Main.Server.rewriteregs ();
                
        }
        else if(recvbuf.toLowerCase ().startsWith("reg "))
        {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                if(!ST.hasMoreTokens ())
                    return;
                String aux=ST.nextToken ();
                Reg(aux);
               
               
        } 
        else if(recvbuf.toLowerCase ().startsWith("grant"))
        {
                new GrantCmd(recvbuf);
               
               
        } 
        else if (recvbuf.toLowerCase ().startsWith("cfg"))
        {
                if(recvbuf.equals ("cfg"))
                {
                System.out.println ("Usage: cfg <varname> <newval>. cfg list to see all.");
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
                        System.out.printf("Timeout_Login changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("hub_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        System.out.printf("Hub_name changed from \""+
                                Vars.HubName+"\" to \""+new_name+"\".\n");
                        
                        Vars.HubName=new_name;
                        Server.rewriteconfig();
                        new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                
                else if(aux.toLowerCase ().equals ("hub_host"))
                {
                     
                       String new_name=ST.nextToken ();
                      
                        
                       
                       InetSocketAddress myHost=new InetSocketAddress(new_name,Vars.Default_Port);
                       Vector localAddies=new Vector();
                       try{
Enumeration<NetworkInterface> eNI =
NetworkInterface.getNetworkInterfaces();

NetworkInterface cNI;
Enumeration<InetAddress> eIA;
InetAddress cIA;

for(;eNI.hasMoreElements();){
cNI = eNI.nextElement();
eIA = cNI.getInetAddresses();

for(;eIA.hasMoreElements();){
cIA = eIA.nextElement();
localAddies.add(cIA.getHostAddress());
//System.out.println("IP Local = " + cIA.getHostAddress());
}
}
}
catch(SocketException eS)
{
System.out.println("Hub_host cannot be resolved or machine hostname badly set.\n");
continue;
} 
                       if(myHost.isUnresolved())
                       {
                        System.out.printf("Hub_host cannot be resolved or DNS server failure.\n");
                       continue;
                       }
                       try
                       {
                       
                        InetAddress addresses[] = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
                        boolean ok=false;
                        Iterator myIT=localAddies.iterator();
                        while(myIT.hasNext())
                        
                              if(myHost.getAddress().getHostAddress().equals(myIT.next()))
                                     ok=true;
                        //ok=false;
                       if(!ok)
                       {
                           System.out.println("Scanning hub_host please wait...");
                        if(!(HostTester.hostOK(new_name)))
                        {
                            System.out.printf("The hub_host you provided does not point to one of your eth interfaces. "+
                                    "Reasons: DNS not correctly set; you dont have a external real IP (if you are creating"+"" +
                                    " LAN hub, use your LAN local IP as a hub_host); not even package routing to your system work.\n");
                       continue;
                        }
                       }
                        System.out.printf("Hub_host changed from \""+
                                Vars.Hub_Host+"\" to \""+new_name+"\".\n");
                        
                        Vars.Hub_Host=new_name;
                        Server.rewriteconfig();
                       
                       }
                       catch (UnknownHostException uhe)
                       {
                           System.out.println("Hub_host cannot be resolved or DNS server failure.\n");
                       }
                        
                }
                else if(aux.toLowerCase ().equals ("proxy_host"))
                {
                     
                    if(Vars.Proxy_Port==0)
                    {
                        System.out.printf("Set the proxy_port first.\n");
                        continue;
                    }
                       String new_name=ST.nextToken ();
                      try
                      {
                       Proxy x=new Proxy(Proxy.Type.HTTP,new InetSocketAddress(new_name,Vars.Proxy_Port));
                      }
                      catch(Exception e)
                      {
                          System.out.println("Invalid proxy. Possible reasons: incorrect input, domain resolution failure, invalid proxy.");
                      }
                        System.out.printf("Proxy_Host changed from \""+
                                Vars.Proxy_Host+"\" to \""+new_name+"\".\n");
                        
                        Vars.Proxy_Host=new_name;
                        Server.rewriteconfig();
                       
                        
                }
                else if(aux.toLowerCase().equals ("proxy_port"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.Proxy_Port;
                        int x=Integer.parseInt(aux);
                        if(x<1 || x> 65355)
                            throw new NumberFormatException();
                        Vars.Proxy_Port=x;
                        System.out.printf("Proxy_port changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid port number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("redirect_url"))
                {
                     
                    
                       String new_name=ST.nextToken ();
                      
                        System.out.printf("Redirect_url changed from \""+
                                Vars.redirect_url+"\" to \""+new_name+"\".\n");
                        
                        Vars.redirect_url=new_name;
                        Server.rewriteconfig();
                       
                        
                }
                else if(aux.toLowerCase().equals ("max_ni"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_ni;
                        Vars.max_ni=Integer.parseInt (aux);
                        System.out.printf("Max_NI changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_ni"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.min_ni;
                        Vars.min_ni=Integer.parseInt (aux);
                        System.out.printf("Min_NI changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_de"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_de;
                        Vars.max_de=Integer.parseInt (aux);
                        System.out.printf("Max_DE changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_share"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_share;
                        Vars.max_share=Long.parseLong (aux);
                        System.out.printf("Max_share changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_share"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.min_share;
                        Vars.min_share=Long.parseLong (aux);
                        System.out.printf("Min_share changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_sl"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_sl;
                        Vars.max_sl=Integer.parseInt (aux);
                        System.out.printf("Max_SL changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_sl"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_sl;
                        Vars.min_sl=Integer.parseInt (aux);
                        System.out.printf("Min_SL changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_em"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_em;
                        Vars.max_em=Integer.parseInt (aux);
                        System.out.printf("Max_EM changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_op"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_op;
                        Vars.max_hubs_op=Integer.parseInt (aux);
                        System.out.printf("Max_hubs_op changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_reg"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_reg;
                        Vars.max_hubs_reg=Integer.parseInt (aux);
                        System.out.printf("Max_hubs_reg changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_user"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_user;
                        Vars.max_hubs_user=Integer.parseInt (aux);
                        System.out.printf("Max_hubs_user changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_sch_chars"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_sch_chars;
                        Vars.max_sch_chars=Integer.parseInt (aux);
                        System.out.printf("Max_sch_chars changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_sch_chars"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.min_sch_chars;
                        Vars.min_sch_chars=Integer.parseInt (aux);
                        System.out.printf("Min_sch_chars changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_chat_msg"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_chat_msg;
                        Vars.max_chat_msg=Integer.parseInt (aux);
                        System.out.printf("Max_chat_msg changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                
                else if(aux.toLowerCase().equals ("history_lines"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.history_lines;
                        if(Integer.parseInt (aux)<10)
                        {
                          Vars.history_lines=10;
                        }
                        else if(Integer.parseInt (aux)>300)
                            Vars.history_lines=300;
                        else
                        Vars.history_lines=Integer.parseInt (aux);
                        
                       System.out.printf("History_lines changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("opchat_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       
                       if(!Vars.ValidateNick (new_name))
                   {
                       System.out.println("Nick not valid, please choose another.");
                       return;
                   }
                    
                         for( ClientNod tempy : SimpleHandler.Users)
                           if(tempy.cur_client.userok==1) if( tempy.cur_client.NI.equalsIgnoreCase(new_name))
                          {
                       System.out.println("Nick taken, please choose another.");
                       return;
                         }
                
                    if(new_name.equalsIgnoreCase(Vars.bot_name))
                      {
                       System.out.println("Nick taken, please choose another.");
                       return;
                      }
                           
                       
                   
                    
                        System.out.println ("Opchat_name changed from \""+
                                Vars.Opchat_name+"\" to \""+new_name+"\".");
                        
                        Vars.Opchat_name=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF ABCD NI"+Vars.Opchat_name,Broadcast.STATE_ALL_KEY);
                        
                }
                else if(aux.toLowerCase ().equals ("bot_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       
                       if(!Vars.ValidateNick (new_name))
                   {
                       System.out.println("Nick not valid, please choose another.");
                       return;
                   }
                    for( ClientNod tempy : SimpleHandler.Users)
                           if(tempy.cur_client.userok==1) if( tempy.cur_client.NI.equalsIgnoreCase(new_name))
                          {
                       System.out.println("Nick taken, please choose another.");
                       return;
                         }
                
                    if(new_name.equalsIgnoreCase(Vars.Opchat_name))
                      {
                       System.out.println("Nick taken, please choose another.");
                       return;
                      }
                        
                        System.out.println ("Bot_name changed from \""+
                                Vars.bot_name+"\" to \""+new_name+"\".");
                        
                        Vars.bot_name=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF DCBA NI"+Vars.bot_name);
                        
                }
                else if(aux.toLowerCase ().equals ("opchat_desc"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        System.out.println ("Opchat_desc changed from \""+
                                Vars.Opchat_desc+"\" to \""+new_name+"\".");
                        
                        Vars.Opchat_desc=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF ABCD DE"+Vars.Opchat_desc,Broadcast.STATE_ALL_KEY);
                        
                }
                else if(aux.toLowerCase ().equals ("bot_desc"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        System.out.println ("bot_desc changed from \""+
                                Vars.bot_desc+"\" to \""+new_name+"\".");
                        
                        Vars.bot_desc=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF DCBA DE"+Vars.bot_desc);
                        
                }
                else if(aux.toLowerCase().equals ("kick_time"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.kick_time;
                        if(Integer.parseInt (aux)<0)
                        {
                            System.out.println("Invalid kick time number.");
                            continue;
                        }
                        Vars.kick_time=Integer.parseInt (aux);
                        
                        System.out.printf("Kick_time changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".\n");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println ("Invalid number");
                   } 
                }
                 else if(aux.toLowerCase ().equals ("msg_banned"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        System.out.println("Msg_Banned changed from \""+
                                Vars.Msg_Banned.replaceAll("\\ "," ")+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Banned=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                
               
                else if(aux.toLowerCase().equals ("reg_only"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.reg_only;
                        Vars.reg_only=Integer.parseInt (aux);
                        System.out.println("Reg_only changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println( "Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("nick_chars"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                       //    new_name=new_name+" "+ST.nextToken ();
                       if(new_name.indexOf ("\\")!=-1  )
                       {
                           System.out.println("Nick_chars may not contain \"\\\"");
                           continue;
                       }
                       if(new_name.indexOf ("\"")!=-1  )
                       {
                           System.out.println("Nick_chars may not contain \"");
                           continue;
                       }
                       if(new_name.length ()<2  )
                       {
                           System.out.println("Nick_chars too short don't you think ?");
                           continue;
                       }
                        
                        System.out.println("Nick_chars changed from \""+
                                Vars.nick_chars+"\" to \""+new_name+"\".");
                        
                        Vars.nick_chars=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                
                else if(aux.toLowerCase ().equals ("msg_full"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        System.out.println("Msg_Full changed from \""+
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
                        System.out.println("Max_Users changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
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
                            System.out.println("A bit small don't you think ?");
                            continue;
                       }
                        Vars.chat_interval=blahhh;
                                
                        
                        System.out.println("Chat_Interval changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println("Invalid number");
                   } 
                }
                
                 else if(aux.toLowerCase().equals ("save_logs"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.savelogs;
                        Vars.savelogs=Integer.parseInt (aux);
                        System.out.println("Save_logs changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                   System.out.println("Invalid number");
                   } 
                }
                
                 else if(aux.toLowerCase().equals ("automagic_search"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.automagic_search;
                        int x=Integer.parseInt (aux);
                        
                        Vars.automagic_search=x;
                        System.out.println ("Automagic_search changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println ("Invalid number");
                   } 
                }
                 else if(aux.toLowerCase().equals ("search_log_base"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_log_base;
                        int x=Integer.parseInt (aux);
                       
                        Vars.search_log_base=x;
                        System.out.println ("Search_log_base changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println ("Invalid number");
                   } 
                }
                 else if(aux.toLowerCase().equals ("search_steps"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_steps;
                        int x=Integer.parseInt (aux);
                       
                        Vars.search_steps=x;
                        System.out.println ("Search_steps changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println ("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("search_spam_reset"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_spam_reset;
                        int x=Integer.parseInt (aux);
                       
                        Vars.search_spam_reset=x;
                        System.out.println ("Search_spam_reset changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    System.out.println ("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("msg_search_spam"))
                {
                     
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        System.out.println("Msg_search_spam changed from \""+
                                Vars.Msg_Search_Spam+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Search_Spam=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                else if(aux.toLowerCase ().equals ("list"))
                {
                    System.out.println("Cfg Variables list: \n"+
                                       "   timeout_login           "  + Integer.toString (Vars.Timeout_Login) +"         -- Number of seconds for hub to wait for connecting users until kick them out.\n"
                            +          "   hub_name                "  + Vars.HubName+ "         -- Hub name to display in main window.\n"
                            +          "   hub_host                "  + Vars.Hub_Host+ "         -- Hub host (address) (enter your DNS here).\n"
                            +          "   proxy_host                "  + Vars.Proxy_Host+ "         -- Proxy host ( for http integration modules ).\n"
                            +          "   proxy_port                "  + Vars.Proxy_Port+ "         -- Proxy port ( for http integration modules ).\n"
                            +          "   redirect_url              "  + Vars.redirect_url+ "         -- The main redirect URL to send faulty users ( or default redirects ).\n"
                            +          "   max_ni                  "  +Vars.max_ni+" -- Maximum nick size, integer.\n"
                            +          "   min_ni                  "  +Vars.min_ni+" -- Minimum nick size, integer.\n"
                            +          "   max_de                  "  +Vars.max_de+" -- Maximum description size, integer.\n"
                            +          "   max_share               "  +Vars.max_share+" -- Maximum share size, long integer.\n"
                            +          "   min_share               "  +Vars.min_share+" -- Minimum share size, long integer.\n"
                            +          "   max_sl                  "  +Vars.max_sl+" -- Maximum slot number, integer.\n"
                            +          "   min_sl                  "  +Vars.min_sl+" -- Minimum slot number, integer.\n"
                            +          "   max_em                  "  +Vars.max_em+" -- Maximum e-mail string size, integer.\n"
                            +          "   max_hubs_op             "  +Vars.max_hubs_op+" -- Maximum hubs where user is op, integer.\n"
                            +          "   max_hubs_reg            "  +Vars.max_hubs_reg+" -- Maximum hubs where user is reg, integer.\n"
                            +          "   max_hubs_user           "  +Vars.max_hubs_user+" -- Maximum hubs where user is user, integer.\n"
                            +          "   max_sch_chars           "  +Vars.max_sch_chars+" -- Maximum search chars, integer.\n"
                            +          "   min_sch_chars           "  +Vars.min_sch_chars+" -- Minimum search chars, integer.\n"
                            +          "   max_chat_msg            "  +Vars.max_chat_msg+" -- Maximum chat message size, integer.\n"
                            +          "   max_users               "  +Vars.max_users+" -- Maximum number of online users, integer.\n"
                            +          "   history_lines           "  +Vars.history_lines+" -- Number of lines to keep in chat history.\n"
                            +          "   opchat_name             "  +Vars.Opchat_name+" -- The Operator Chat Bot Nick.\n"
                            +          "   opchat_desc             "  +Vars.Opchat_desc+" -- The Operator Chat Bot Description.\n"
                            +          "   kick_time               "  +Vars.kick_time+" -- The time to ban a user with a kick, in seconds.\n"
                            +          "   msg_banned              "  +Vars.Msg_Banned+" -- The aditional message to show to banned users when connecting.\n"
                            +          "   msg_full                "  +Vars.Msg_Full+" -- Message to be shown to connecting users when hub full.\n"
                            +          "   reg_only                "  +Vars.reg_only+" -- 1 = registered only hub. 0 = otherwise.\n"
                            +          "   nick_chars              "  +Vars.nick_chars+" -- Chars that could be used for a nick, String."
                            +          "   chat_interval           "  +Vars.chat_interval+"         -- Interval between chat lines, millis, Integer.\n"
                            +          "   save_logs               "  +Vars.savelogs+"         -- 1 = logs are saved to file, 0 otherwise.\n"
                            +          "   automagic_search        "  +Vars.automagic_search+"         -- Interval between automagic searches for each user, seconds, Integer.\n"
                            +          "   search_log_base         "  +Vars.search_log_base+"         -- Logarithmic base for user searches interval,millis, Integer.\n"
                            +          "   search_steps            "  +Vars.search_steps+"         -- Maximum nr of search steps allowed until reset needed, Integer.\n"
                            +          "   search_spam_reset       "  +Vars.search_spam_reset+"         -- Interval until search_steps is being reset, seconds, Integer.\n"
                            +          "   msg_search_spam         "  +Vars.Msg_Search_Spam+"         -- Message that appears as a result when search is delayed, String.\n"
                            +          "   bot_name                "  +Vars.bot_name+"         -- Hub security bot name, String.\n"
                            +          "   bot_desc                "  +Vars.bot_desc+"         -- Hub security bot description, String."
                            );
                }
                else
                    System.out.println ("Invalid cfg variable. Use \"cfg list\" to see all.");
                }
        }
        else if(recvbuf.toLowerCase ().startsWith("topic"))
        {
               if(recvbuf.toLowerCase ().equals("topic"))
               {
                   
               new Broadcast("IINF DE");
               if(!Vars.HubDE.equals(""))
               {
               System.out.println("Topic \""+Vars.HubDE+"\" deleted.");
               new Broadcast("IMSG Topic was deleted by Server.");
               }
               else
               System.out.println("There wasn't any topic anyway.");
               Vars.HubDE="";
              

               
               }
               else
               {
                   String auxbuf=recvbuf.substring(6);
                   
                   
                  Vars.HubDE=Vars.HubDE.replaceAll("\\ "," ");
                   System.out.println("Topic changed from \""+Vars.HubDE+"\" "+"to \""+auxbuf+"\".");
                   auxbuf=auxbuf;
                   Vars.HubDE=auxbuf;
                  
                   new Broadcast ("IINF DE"+auxbuf);
                   new Broadcast("IMSG Topic was changed by Server to \""+Vars.HubDE+"\"");
                   
               }
        }
        else if(recvbuf.toLowerCase ().startsWith("port "))
        {
                try
                {
            int x=Integer.parseInt(recvbuf.substring(5));
           if(x<1 || x>65000)
           {
               System.out.printf("What kinda port is that?\n");
            return;
           }
            System.out.printf("New default port change from %s to %s. Restart for settings to take effect.\n",Vars.Default_Port,recvbuf.substring(5));
             Vars.Default_Port=x;
             
             Server.rewriteconfig();
                }
                catch(NumberFormatException nfe)
                {
                    System.out.println("Invalid port number");
                }
        }
        else if(recvbuf.toLowerCase ().equals("usercount"))
        {
                int i=0,j=0;
                for( ClientNod temp : SimpleHandler.Users)
                
                {
                    if(temp.cur_client.userok==1)
                    i++;
                    else j++;
                    
                }
                System.out.printf("Current user count: %d. In progress users: %d.\n",i,j);
        }
       else if(recvbuf.toLowerCase ().equals("sessions"))
        {
              
        }
        else if(recvbuf.toLowerCase ().equals("about"))
        {
                System.out.println(Vars.About);
        }
        else if(recvbuf.toLowerCase ().equals("stats"))
        {
                Runtime myRun=Runtime.getRuntime();
               
                       //Proppies.getProperty();
                int i=0,j=0;
                for( ClientNod temp : SimpleHandler.Users)
                
                {
                    if(temp.cur_client.userok==1)
                    i++;
                    else j++;
                    
                }
                
                long up=System.currentTimeMillis()-curtime; //uptime in millis
                
                        
               System.out.printf(
                  "Death Squad Hub. Version "+Vars.HubVersion+".\n"+
                  "  Running on %s Version %s on Architecture %s\n"+
                  "  Java Runtime Environment %s from %s\n"+
                  "  Java Virtual Machine %s\n"+
                  "  Available CPU's to JVM %d\n"+
                  "  Available Memory to JVM: %s Bytes, where free: %s Bytes\n"+
                  "Hub Statistics:\n"+
                  "  Online users: %d\n"+
                  "  Connecting users: %d\n"+
                  "  Uptime: %s\n"+
                       "\n  Bytes red per second: "+Main.Server.IOSM.getTotalByteReadThroughput()+
                "\n  Bytes written per second: "+Main.Server.IOSM.getTotalByteWrittenThroughput()
                       
                    
                   ,Proppies.getProperty("os.name") ,  Proppies.getProperty("os.version"),
                    Proppies.getProperty("os.arch"),  Proppies.getProperty("java.version") ,
                    Proppies.getProperty("java.vendor"),Proppies.getProperty("java.vm.specification.version"),
                    myRun.availableProcessors(), Long.toString(myRun.maxMemory()) ,Long.toString(myRun.freeMemory())
                    ,i,j,TimeConv.getStrTime(up)
                       );
        }
        else if(recvbuf.equals(""))
            ;
        else
        {
                System.out.println("Unknown Command. Type help for info, quit for quit");
        }
            System.out.print(">");
            }
        }
        catch(IOException bl)
        {
        
        }
    }
    
   
}




