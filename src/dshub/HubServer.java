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
import java.nio.charset.Charset;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.*;
import java.util.Calendar;
import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.IoServiceConfig;
import org.apache.mina.common.PooledByteBufferAllocator;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.common.ThreadModel;
import org.apache.mina.filter.LoggingFilter;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.integration.jmx.IoServiceManager;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

/**
 * Basic hub server listener and socket receiver, sends users to each's thread after connecting.
 * Handles the hub databases kept in files ( regs, config and bans).
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
   
   static boolean restart;
   static IoServiceManager IOSM;
   static ServiceManager SM;
   
   IoAcceptor acceptor;
   ExecutorService x,y;
   InetSocketAddress address;
   Calendar MyCalendar;
    /** Creates a new instance of HubServer */
    public HubServer() 
    {
        
        //myPath="";
        //System.out.println (myPath);
        start();    
        setPriority(NORM_PRIORITY+1);

    } 
    
    public void run()
    {
        
        
      
       
        try 
        {
            File MotdFile=new File(Main.myPath+"motd");
            FileReader mr = new FileReader(MotdFile); 
            BufferedReader mb = new BufferedReader(mr); 
            Main.MOTD="";
            while ( (Main.auxhelp=mb.readLine()) != null ) 
            { 
            Main.MOTD=Main.MOTD+Main.auxhelp+"\n";
            } 
            mb.close ();
            Main.MOTD=Main.MOTD.substring (0,Main.MOTD.length ()-1);
            ADC.MOTD=Main.MOTD;
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
       
      
          try
          {
        this.sleep (500);
          }
          catch ( Exception e)
          {}
      
       //   new ClientNod();
        ByteBuffer.setUseDirectBuffers(false);
        ByteBuffer.setAllocator(new PooledByteBufferAllocator());
        
        
        
        x=Executors.newCachedThreadPool();
        y=Executors.newCachedThreadPool();
        acceptor = new SocketAcceptor(5, x);
        
        
        
        
        SocketAcceptorConfig cfg = new SocketAcceptorConfig();
      // cfg.setThreadModel(ThreadModel.MANUAL);
        
         cfg.getSessionConfig().setReceiveBufferSize(102400);
         cfg.getSessionConfig().setSendBufferSize(102400);
         
        cfg.getFilterChain().addLast( "logger", new LoggingFilter() );
        cfg.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory( Charset.forName( "UTF-8" ))));
        MyCalendar=Calendar.getInstance();
       DefaultIoFilterChainBuilder filterChainBuilder = cfg.getFilterChain();
          filterChainBuilder.addLast("threadPool", new ExecutorFilter(y));
        cfg.getSessionConfig().setKeepAlive(true);
        
       
        //cfg.getSessionConfig().
        //System.out.println(cfg.getSessionConfig().getReceiveBufferSize());
        IOSM=new IoServiceManager(acceptor);
        SM=new ServiceManager(acceptor);
        IOSM.startCollectingStats(10000);
        address=new InetSocketAddress(port);
        try
        {

            acceptor.bind( address, new SimpleHandler(), cfg);
            if(Main.GUIok)
           {
               Main.GUI.SetStatus ("Server created. Listening on port "+port+".\n");
               
           }
            Main.PopMsg("Server created. Listening on port "+port+".");
            Date d=new Date(Main.curtime);
        Main.PopMsg("Start Time:"+d.toString ());
        System.out.print("\n>");
        
         myAssasin=new ClientAssasin();//temporary removed
       //  ClientExecutor myExecutor=new ClientExecutor();
        } 
        catch( java.net.BindException jbe)
        {
            Main.PopMsg("Network problem. Unable to listen on port "+port+"."+jbe);
            if(Main.GUIok)
           {
               Main.GUI.SetStatus ("Network problem. Unable to listen on port "+port+"."+jbe);
               
           }
            return;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            
        }
        
           
        
        
        
        
      /* try 
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
        {}*/
    }
    public void shutdown()
    {
        acceptor.unbind(address);
        x.shutdown();
    }
    public static ClientNod AddClient()
    {
     //ClientHandler ret;
        if(restart)
            return null;
       
        ClientNod newclient=new ClientNod();
       newclient.NextClient=ClientNod.FirstClient.NextClient;
       ClientNod.FirstClient.NextClient=newclient;
       newclient.PrevClient=ClientNod.FirstClient;
       if(newclient.NextClient!=null)
       newclient.NextClient.PrevClient=newclient;
       return newclient;

        
    }
    
    public static void  rewriteconfig()
    {
        File MainConfigFile;
        MainConfigFile=new File(Main.myPath+"config");
      
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
                Main.PopMsg(e.toString());
            }
    }
    public static void  rewritebans()
    {
        File MainBanFile;
        MainBanFile=new File(Main.myPath+"banlist");
      
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
                Main.PopMsg(e.toString());
            }
    }
    public static void  rewriteregs()
    {
        File MainRegFile;

        MainRegFile=new File(Main.myPath+"regs");
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
                Main.PopMsg(e.toString());
            }
    }
     public  void reloadconfig() 
    {
        File MainConfigFile;
        MainConfigFile=new File(Main.myPath+"config");
       
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

      Vars.Opchat_name=vars.Opchat_name;
      Vars.Opchat_desc=vars.Opchat_desc;
      Vars.kick_time=vars.kick_time;
      Vars.Msg_Banned=vars.Msg_Banned;

      Vars.reg_only=vars.reg_only;
      Vars.nick_chars=vars.nick_chars;
      Vars.max_users=vars.max_users;
      Vars.Msg_Full=vars.Msg_Full;

     Vars.OpChatCid=vars.OpChatCid;
     Vars.SecurityCid=vars.SecurityCid;
      
      Vars.chat_interval=vars.chat_interval;

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
      
      Vars.BSTA=vars.BSTA;
      Vars.ESTA=vars.ESTA;
      Vars.DSTA=vars.DSTA;
      Vars.FSTA=vars.FSTA;
      Vars.HSTA=vars.HSTA;
      
      Vars.BCTM=vars.BCTM;
      Vars.DCTM=vars.DCTM;
      Vars.ECTM=vars.ECTM;
      Vars.FCTM=vars.FCTM;
      Vars.HCTM=vars.HCTM;
      
      Vars.BRCM=vars.BRCM;
      Vars.DRCM=vars.DRCM;
      Vars.ERCM=vars.ERCM;
      Vars.FRCM=vars.FRCM;
      Vars.HRCM=vars.HRCM;
      
      Vars.BINF=vars.BINF;
      Vars.DINF=vars.DINF;
      Vars.EINF=vars.EINF;
      Vars.FINF=vars.FINF;
      Vars.HINF=vars.HINF;
      
      Vars.BSCH=vars.BSCH;
      Vars.DSCH=vars.DSCH;
      Vars.ESCH=vars.ESCH;
      Vars.FSCH=vars.FSCH;
      Vars.HSCH=vars.HSCH;
      
      Vars.BRES=vars.BRES;
      Vars.DRES=vars.DRES;
      Vars.ERES=vars.ERES;
      Vars.FRES=vars.FRES;
      Vars.HRES=vars.HRES;
       
      Vars.BPAS=vars.BPAS;
      Vars.DPAS=vars.DPAS;
      Vars.EPAS=vars.EPAS;
      Vars.FPAS=vars.FPAS;
      Vars.HPAS=vars.HPAS;
      
      Vars.BSUP=vars.BSUP;
      Vars.DSUP=vars.DSUP;
      Vars.ESUP=vars.ESUP;
      Vars.FSUP=vars.FSUP;
      Vars.HSUP=vars.HSUP;
        
        in.close();

        }
        catch ( FileNotFoundException fnfe)
        {
            //file not found so were gonna make it
             Main.PopMsg("Generated new PID/CID for OpChat and Hub Security.");
           rewriteconfig();

            
            
        }
        catch (IOException e)
        {
            Main.PopMsg("Error accesing config files.Attempting overwrite with default values.");
            Main.PopMsg("Generated new PID/CID for OpChat and Hub Security.");
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
        
        MainRegFile=new File(Main.myPath+"regs");
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
        
        MainBanFile=new File(Main.myPath+"banlist");
        try
        {
      
        
        FileInputStream MainBanFileReader=new FileInputStream (MainBanFile);
        GZIPInputStream gzisreg = new GZIPInputStream(MainBanFileReader);
        ObjectInputStream inreg=new ObjectInputStream(gzisreg);
        
        bcfg=(bans)inreg.readObject ();

        for(int i=1;i<bcfg.i;i++)
        {
            bcfg.bans[i].Next=null;
            BanList.addban (bcfg.bans[i]);
        }

        

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
