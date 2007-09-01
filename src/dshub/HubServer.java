package dshub;
/*
 * HubServer.java
 *
 * Created on 03 martie 2007, 23:00
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

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.zip.*;
import java.util.Calendar;

/**
 *
 * @author Pietricica
 */
public class HubServer extends Thread
{
    
    /** Server main socket */
    ServerSocket MainSocket;
    InputStream IS;
    OutputStream OS;    
    int port;
   
   ClientAssasin myAssasin;
    
    //ClientHandler firstclient;
    
   static Variables vars;
   static  RegConfig rcfg;
   static bans bcfg;
   static String OpChatCid;
   static String SecurityCid;
   static boolean restart;
   
   static String myPath;
   Calendar MyCalendar;
    /** Creates a new instance of HubServer */
    public HubServer() 
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
        
        setPriority(NORM_PRIORITY+1);
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
          // System.out.println (myPath);
        }
        if(System.getProperty("os.name").equals("Linux"))
        {
            StringTokenizer st1=new StringTokenizer(myPath,pathsep);
            String aux=st1.nextToken();
            //System.out.println(myPath);
            while(!(aux.toLowerCase().contains("dshub.jar".toLowerCase())) && st1.hasMoreTokens())
                aux=st1.nextToken();
           // if(!st1.hasMoreTokens())
            {
              //  Main.PopMsg("FAIL. Java Classpath Error.");
             //   return;
            }
            myPath=aux;
            
            
        }
        //System.out.println (myPath);
        start();    
       

    } 
    
    public void run()
    {
        
        ADC.MOTD=ADC.MOTD;
      
        try 
        {
            File HelpFile=new File(myPath+"help");
            FileReader fr = new FileReader(HelpFile); 
            BufferedReader br = new BufferedReader(fr); 
            Main.HelpText="";Main.MOTD="";
            while ( (Main.auxhelp=br.readLine()) != null ) 
            { 
            Main.HelpText=Main.HelpText+Main.auxhelp+"\n";
            } 
            br.close ();
        }
        catch (IOException e)
        {
            Main.HelpText="No Help File found.";
        }
        
        try 
        {
            File MotdFile=new File(myPath+"motd");
            FileReader mr = new FileReader(MotdFile); 
            BufferedReader mb = new BufferedReader(mr); 
            
            while ( (Main.auxhelp=mb.readLine()) != null ) 
            { 
            Main.MOTD=Main.MOTD+Main.auxhelp+"\n";
            } 
            mb.close ();
            Main.MOTD=Main.MOTD.substring (0,Main.MOTD.length ()-1);
            ADC.MOTD=Main.MOTD;//.replaceAll ("\\x0a","\\\n").replaceAll (" ","\\ ");
        }
        catch (IOException e)
        {
            Main.MOTD=ADC.MOTD;
            
        }
        restart=false;
        vars=new Variables();
       reloadregs();
       reloadconfig();
       reloadbans();
       
       port=vars.Default_Port;
       Tiger myTiger = new Tiger();
						
	myTiger.engineReset();
	myTiger.init();	
       byte [] T=Long.toString(System.currentTimeMillis()).getBytes();
        myTiger.engineUpdate(T,0,T.length);
				
	 byte[] finalTiger = myTiger.engineDigest();
	  OpChatCid=Base32.encode (finalTiger);
          Tiger myTiger2 = new Tiger();
						
	myTiger2.engineReset();
	myTiger2.init();	
        T=Long.toString(System.currentTimeMillis()+2).getBytes();
        myTiger2.engineUpdate(T,0,T.length);
				
	 finalTiger = myTiger2.engineDigest();
	  SecurityCid=Base32.encode (finalTiger);
          try
          {
        this.sleep (500);
          }
          catch ( Exception e)
          {}
       try
       {
           MainSocket=new ServerSocket(port);
       }
      catch (IOException e)
       {
           Main.PopMsg("Network problem in attempt to listen to localhost:"+Vars.Default_Port+"  "+e.getMessage());
           if(Main.GUIok)
           {
               Main.GUI.SetStatus (e.getMessage ());
               
           }
           return;
       }
           
           Main.PopMsg("Server created. Listening on port "+port+".");
        new ClientHandler();
        MyCalendar=Calendar.getInstance();
        if(Main.GUIok)
           {
               Main.GUI.SetStatus ("Server created. Listening on port "+port+".\n");
               
           }
        Date d=new Date(Main.curtime);
        Main.PopMsg("Start Time:"+d.toString ());
        System.out.print("\n>");
        
         myAssasin=new ClientAssasin();//temporary removed
       try 
       {
           while(!restart)
               AddClient(MainSocket.accept());
       }
      catch(IOException e)
       {
           //System.out.println("Client I/O Error.");
       }
        try
        {MainSocket.close ();}
        catch( Exception e)
        {}
    }
    
    public void AddClient(Socket s)
    {
     
        if(restart)
            return;
        try
        {
             this.sleep (30);
        }
             catch(InterruptedException ie)
             {
             
             }
        ClientHandler newclient=new ClientHandler(s);
       newclient.NextClient=ClientHandler.FirstClient.NextClient;
       ClientHandler.FirstClient.NextClient=newclient;
       newclient.PrevClient=ClientHandler.FirstClient;
       if(newclient.NextClient!=null)
       newclient.NextClient.PrevClient=newclient;

        
    }
    
    public static void  rewriteconfig()
    {
        File MainConfigFile;
        MainConfigFile=new File(myPath+"config");
      
         try 
            {
            FileOutputStream MainConfigFileOutput=new FileOutputStream(MainConfigFile);
            GZIPOutputStream gzos = new GZIPOutputStream(MainConfigFileOutput);  // Compress.
            ObjectOutputStream out = new ObjectOutputStream(gzos);  // Save objects
            out.writeObject(new Variables());      
            out.flush();                 // Always flush the output.
            out.close();                 // And close the stream.
           
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
    }
    public static void  rewritebans()
    {
        File MainBanFile;
        MainBanFile=new File(myPath+"banlist");
      
         try 
            {
            FileOutputStream MainBanFileOutput=new FileOutputStream(MainBanFile);
            GZIPOutputStream gzos = new GZIPOutputStream(MainBanFileOutput);  // Compress.
            ObjectOutputStream out = new ObjectOutputStream(gzos);  // Save objects
            out.writeObject(new bans());      
            out.flush();                 // Always flush the output.
            out.close();                 // And close the stream.
           
            }
            catch (IOException e)
            {
                System.out.println(e);
            }
    }
    public static void  rewriteregs()
    {
        File MainRegFile;

        MainRegFile=new File(myPath+"regs");
         try 
            {
           
            FileOutputStream MainRegFileOutput=new FileOutputStream(MainRegFile);
            GZIPOutputStream gzosreg = new GZIPOutputStream(MainRegFileOutput);  // Compress.
            ObjectOutputStream outreg = new ObjectOutputStream(gzosreg);  // Save objects
            outreg.writeObject(new RegConfig());      
            outreg.flush();                 // Always flush the output.
            outreg.close();                 // And close the stream.

            }
            catch (IOException e)
            {
                System.out.println(e);
            }
    }
     public  void reloadconfig() 
    {
        File MainConfigFile;
        MainConfigFile=new File(myPath+"config");
       
        try
        {
        FileInputStream MainConfigFileReader=new FileInputStream (MainConfigFile);
        GZIPInputStream gzis = new GZIPInputStream(MainConfigFileReader);
        ObjectInputStream in=new ObjectInputStream(gzis);
        
        
        
        vars=(Variables)in.readObject();
        Vars.Timeout_Login=vars.Timeout_Login;
        Vars.Default_Port=vars.Default_Port;
      
        Vars.HubVersion=vars.HubVersion;
        Vars.HubDE=vars.HubDE;
        Vars.HubName=vars.HubName;
 

        
      Vars.min_ni=vars.min_ni;
      Vars.max_de=vars.max_de;
      Vars.max_share=vars.max_share; //10 tebibytes
      Vars.min_share=vars.min_share;
      Vars.max_sl=vars.max_sl;
      Vars.min_sl=vars.min_sl;
      Vars.max_em=vars.max_em;
      Vars.max_hubs_op=vars.max_hubs_op;
      Vars.max_hubs_reg=vars.max_hubs_reg;
      Vars.max_hubs_user=vars.max_hubs_user;
      Vars.min_sch_chars=vars.min_sch_chars;
      Vars.max_sch_chars=vars.max_sch_chars;
      Vars.max_chat_msg=vars.max_chat_msg;
      Vars.history_lines=vars.history_lines;
      Vars.kick_ops=vars.kick_ops;
      Vars.Opchat_name=vars.Opchat_name;
      Vars.Opchat_desc=vars.Opchat_desc;
      Vars.kick_time=vars.kick_time;
      Vars.Msg_Banned=vars.Msg_Banned;
      Vars.rename_ops=vars.rename_ops;
      Vars.reg_only=vars.reg_only;
      Vars.nick_chars=vars.nick_chars;
      Vars.max_users=vars.max_users;
      Vars.Msg_Full=vars.Msg_Full;
      Vars.ops_override_full=vars.ops_override_full;
      Vars.ops_override_spam=vars.ops_override_spam;
      Vars.chat_interval=vars.chat_interval;
      Vars.keep_alive_interval=vars.keep_alive_interval;
      Vars.savelogs=vars.savelogs;
      Vars.automagic_search=vars.automagic_search;
      Vars.search_log_base=vars.search_log_base;
      Vars.search_steps=vars.search_steps;
      Vars.search_spam_reset=vars.search_spam_reset;
      Vars.bot_name=vars.bot_name;
      Vars.bot_desc=vars.bot_desc;
      
      Vars.BMSG=vars.BMSG;
      Vars.EMSG=vars.EMSG;
      Vars.DMSG=vars.DMSG;
      Vars.HMSG=vars.HMSG;
      Vars.FMSG=vars.FMSG;
        
        in.close();

        }
        catch ( FileNotFoundException fnfe)
        {
            //file not found so were gonna make it
           rewriteconfig();

            
            
        }
        catch (IOException e)
        {
            Main.PopMsg("Error accesing config files.Attempting overwrite with default values.");
            rewriteconfig();
        }
         catch(ClassNotFoundException e)
        {
            Main.PopMsg("Internal Error Config Corrupted Files. FAIL.");
        }
      
    }
     public  void reloadregs() 
    {
        File MainRegFile;
        
        MainRegFile=new File(myPath+"regs");
        try
        {
      
        
        FileInputStream MainRegFileReader=new FileInputStream (MainRegFile);
        GZIPInputStream gzisreg = new GZIPInputStream(MainRegFileReader);
        ObjectInputStream inreg=new ObjectInputStream(gzisreg);
        
        rcfg=(RegConfig)inreg.readObject ();

        for(int i=1;i<rcfg.reg_count;i++)
            reg_config.addReg (rcfg.nods[i]);

        

        inreg.close ();
        }
        catch ( FileNotFoundException fnfe)
        {
            //file not found so were gonna make it
           rewriteregs();
           
            
            
        }
        catch (IOException e)
        {
            Main.PopMsg("Error accesing regs files.Attempting overwrite with default values.");
            rewriteregs();
        }
         catch(ClassNotFoundException e)
        {
            Main.PopMsg("Internal Error Corrupted Regs File. FAIL.");
        }
      
    }
      public  void reloadbans() 
    {
        File MainBanFile;
        
        MainBanFile=new File(myPath+"banlist");
        try
        {
      
        
        FileInputStream MainBanFileReader=new FileInputStream (MainBanFile);
        GZIPInputStream gzisreg = new GZIPInputStream(MainBanFileReader);
        ObjectInputStream inreg=new ObjectInputStream(gzisreg);
        
        bcfg=(bans)inreg.readObject ();

        for(int i=1;i<bcfg.i;i++)
            BanList.addban (bcfg.bans[i]);

        

        inreg.close ();
        }
        catch ( FileNotFoundException fnfe)
        {
            //file not found so were gonna make it
           rewriteregs();

            
            
        }
        catch (IOException e)
        {
            Main.PopMsg("Error accesing bans files.Attempting overwrite with default values.");
            rewriteregs();
        }
         catch(ClassNotFoundException e)
        {
            Main.PopMsg("Internal Error Corrupted Bans File. FAIL.");
        }
      
    }
}
