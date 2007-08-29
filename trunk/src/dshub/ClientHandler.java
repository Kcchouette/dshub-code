package dshub;
/*
 * ClientHandler.java
 *
 * Created on 03 martie 2007, 23:09
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
import java.lang.Integer;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author Pietricica
 */

class ClientFailedException extends Exception
{
    ClientFailedException()
    {
        
    }
    ClientFailedException(String s)
    {
        super(s);
    }
};

public class ClientHandler extends Thread
{
    /** main client socket*/
    Socket ClientSock;
    
    InputStream IS;
    OutputStream OS;
    
    PrintStream PS;
    BufferedReader RS;
    
    ClientHandler NextClient;
    ClientHandler PrevClient;//added in zeta rc6
    static ClientHandler FirstClient;
    
    int logged_in=0;
    int userok=0;
    int ACTIVE=0;
    int quit=0;
    
    ClientQueue Queue;
    nod reg;
    ban myban;
    String RandomData;
    
    long LastChatMsg;
    long LastKeepAlive;
    
/** The CID of the client. Mandatory for C-C connections.*/
String ID;
/**The PID of the client. Hubs must check that the Tiger(PID) == CID and then discard the field before broadcasting it to other clients. Must not be sent in C-C connections.*/
String PD;
/**IPv4 address without port. A zero address (0.0.0.0) means that the server should replace it with the real IP of the client. Hubs must check that a specified address corresponds to what the client is connecting from to avoid DoS attacks, and only allow trusted clients to specify a different address. Clients should use the zero address when connecting, but may opt not to do so at the user's discretion. Any client that supports incoming TCPv4 connections must also add the feature TCP4 to their SU field.*/
String I4;
/**IPv6 address without port. A zero address (::) means that the server should replace it with the IP of the client. Any client that supports incoming TCPv6 connections must also add the feature TCP6 to their SU field.*/
String I6;
/**Client UDP port. Any client that supports incoming UDPv4 packets must also add the feature UDP4 to their SU field.*/
String U4;
/**Same as U4, but for IPv6. Any client that supports incoming UDPv6 packets must also add the feature UDP6 to their SU field.*/
String U6;
/**Share size in bytes, integer.*/
String SS;
/**Number of shared files, integer*/
String SF;
/**Client identification, version (client-specific, a short identifier then a floating-point version number is recommended). Hubs should not discriminate agains clients based on their VE tag but instead rely on SUP when it comes to which clients should be allowed (for example, we only want regex clients).*/	
String VE;
/**Maximum upload speed, bits/sec, integer*/
String US;
/**Maximum download speed, bits/sec, integer*/
String DS;
/**Upload slots open, integer*/
String SL;
/**Automatic slot allocator speed limit, bytes/sec, integer. This is the recommended method of slot allocation, the client keeps opening slots as long as its total upload speed doesn�t exceed this value. SL then serves as a minimum number of slots open.*/
String AS;
/**Maximum number of slots open in automatic slot manager mode, integer.*/
String AM;
/**E-mail address, string.*/
String EM;
/**Nickname, string. The hub must ensure that this is unique in the hub up to case-sensitivity. Valid are all characters in the Unicode character set with code point above 32, although hubs may limit this further as they like with an appropriate error message.
 *When sent for hub, this is the nick that should be displayed before messages from the hub, and may also be used as short name for the hub.*/
String NI;
/**Description, string. Valid are all characters in the Unicode character set with code point equal to or greater than 32.
*When sent by hub, this string should be displayed in the window title of the hub window (if one exists)*/	
String DE;
/**Hubs where user is a normal user and in NORMAL state, integer. While connecting, clients should not count the hub they're connecting to. Hubs should increase one of the three the hub counts by one before passing the client to NORMAL state.*/
String HN;
/**Hubs where user is registered (had to supply password) and in NORMAL state, integer.*/
String HR;
/**Hubs where user is op and in NORMAL state, integer.*/
String HO;
/**Token, as received in RCM/CTM, when establishing a C-C connection.*/
String TO;
/**1=op*/
String OP;
/**1=registered*/
String RG;
/**1=Away
*2=Extended away, not interested in hub chat (hubs may skip sending broadcast type MSG commands to clients with this flag)*/
String AW;
/**1=Bot (in particular, this means that the client does not support file transfers, and thus should never be queried for direct connections)*/
String BO;
/**1=Hidden, should not be shown on the user list.*/
String HI;
/**1=Hub, this INF is about the hub itself*/
String HU;
/**Comma-separated list of feature FOURCC's. This notifies other clients of extended capabilities of the connecting client. Use with discretion.*/
String SU;
	
int search_step=0;
long Lastsearch=0L;
long Lastautomagic=0L;


String InQueueSearch=null;

static int user_count=0;
int kicked=0;
    
    String SessionID;
    byte [] sid;
    /** indicates if client supports UCMD messages*/
    int ucmd;
    /**indicates if client supports BASE messages*/
    int base; 
    /** Client Connect time in millis as Syste.gettimemillis() ; ;)*/
    long ConnectTimeMillis;
    public ClientHandler()
    {
        
        NextClient=null;
        PrevClient=null;
        FirstClient=this;
    }
    /** Creates a new instance of ClientHandler */
    public ClientHandler(Socket s) 
    {
        /*do not asume that client have BASE features*/
        user_count++;
        base=0;
        ucmd=0;
        ClientSock=s;
        Queue=new ClientQueue(this);
        
        
      sid=null;
      myban=null;
      LastChatMsg=0;
      
        ConnectTimeMillis=System.currentTimeMillis();
       
        NextClient=null;
        //FirstClient=first;
        
        setPriority(NORM_PRIORITY);
        start();
    }
    
    public void run ()
    {
        
        SID cursid=new SID(this);
        SessionID=Base32.encode (cursid.cursid).substring (0,4);
        sid=cursid.cursid;

        LastKeepAlive=0L;
        try
        {
        IS=ClientSock.getInputStream();
        OS=ClientSock.getOutputStream();
        
        }
        catch(IOException e)
        {
            System.out.println("FAIL.");
            return;
        }
       // System.out.println("Client connected.");
        try
        {
        RS=new BufferedReader(new InputStreamReader(IS,"UTF8"));
        PS=new PrintStream(OS,true,"UTF8");
        
        }
        catch (UnsupportedEncodingException e) 
        {
            System.out.println("System does not support UTF-8. FAIL.");
            return;
        }
        
       
        String recvbuf;
        
        try
        {
      
        ClientSock.setSoTimeout (1000*Vars.Timeout_Login);
        ClientSock.setKeepAlive (true);
         LastKeepAlive=System.currentTimeMillis ();
        recvbuf=RS.readLine();
        
        Command c1=new Command(this,recvbuf,"PROTOCOL");  //HSUP ADBASE
       
        
        
        sendToClient(ADC.Init);
        
       
        sendToClient(ADC.ISID+" "+SessionID);
        
        if(Vars.HubDE.equals (""))
            sendToClient("IINF HU1 HI1 VE"+CommandParser.retADCStr (Vars.HubVersion)+" NI"+CommandParser.retADCStr(Vars.HubName));
        else
            sendToClient("IINF HU1 HI1 VE"+CommandParser.retADCStr (Vars.HubVersion)+" NI"+CommandParser.retADCStr(Vars.HubName)+ " DE"+CommandParser.retADCStr(Vars.HubDE));
        sendToClient("IMSG "+
            "Running\\sEta\\sVersion\\sof\\sDSHub.\nIMSG Hub\\sis\\sup\\ssince\\s"+ Main.Server.MyCalendar.getTime ().toString ().replaceAll (" ","\\\\s"));
       
       
        recvbuf=RS.readLine();
        logged_in=1;
        Command c2=new Command(this,recvbuf,"PROTOCOL");  //BINF
       
       if(!reg.isreg) 
       {
        
        sendFromBot( ADC.MOTD);
       }
       
      
      ClientSock.setSoTimeout (0);
   
      
      
        while(ClientSock.isConnected () && !ClientSock.isClosed ())
     
       {
          
          
            recvbuf=RS.readLine();
          
          
            //String auxbuf;
            char aux1='\\';char aux2='n';
            while(RS.ready() && aux1=='\\' && aux2=='n')
            {
                RS.mark(2);
                 aux1=(char)RS.read();
                 aux2=(char)RS.read();
                RS.reset();
                if(aux1=='\\' && aux2=='n')
                    recvbuf=recvbuf+RS.readLine();
                    
            }
                
         this.LastKeepAlive=System.currentTimeMillis ();
        // System.out.println ("OK, the value is : "+LastKeepAlive/1000);
         StringTokenizer TK;
         if(recvbuf!=null)
         {
             TK=new StringTokenizer(recvbuf,"\n");
             while(TK.hasMoreTokens ())
                 new Command(this,TK.nextToken (),"NORMAL");
         }
         else
         
         new Command (this,null,"NORMAL");
        
       }
        
        }
       catch (ClientFailedException ce)
        {
          
        }
        catch(Exception sta)
        {
            if(kicked==1)
                return;
            //System.out.println (sta);
           ClientHandler tempy=FirstClient;
            //ClientHandler tempyprev=FirstClient;
            
            while (!tempy.NextClient.equals(this) && tempy.NextClient!=null)
            {
                
                tempy=tempy.NextClient;
                if(tempy.NextClient==null)
                    break;
            }
          if(this.userok==1) //if he ever logged in... else is no point in sending QUI
          {
                 new Broadcast("IQUI "+SessionID);
                    // System.out.printf ("[disconnected:] %s\n",this.NI);  
          }
           
            tempy.NextClient=tempy.NextClient.NextClient;
            try
            {
                this.sleep (100);
                ClientSock.close();
            }
            catch (Exception e)
            {
            }
            
            }
            
    
        
        
        //System.out.println ("ok, some guy left "+this.NI);
       //user_count--; 
    }
    /** sends the bla String in RAW to client.
     *adds the \n ending char ;)
     */
     public void sendToClient(String bla)
    {
        
         this.Queue.addMsg (bla);
        //System.out.println("[sent]: "+bla);
    
    }
     
     public String getINF()
    {
            String auxstr="";
               auxstr=auxstr+"BINF " + SessionID+" ID"+ID+" NI"+NI;
               //these were mandatory fields.. now adding the extra...
               if(I4!=null)if(!I4.equals (""))
                   auxstr=auxstr+" I4"+I4;
               if(AM!=null)if(!AM.equals (""))
                   auxstr=auxstr+" AM"+AM;
               if(AS!=null)if(!AS.equals (""))
                   auxstr=auxstr+" AS"+AS;
               if(AW!=null)if(!AW.equals (""))
                   auxstr=auxstr+" AW"+AW;
               if(DE!=null)if(!DE.equals (""))
                   auxstr=auxstr+" DE"+DE;
               if(DS!=null)if(!DS.equals (""))
                   auxstr=auxstr+" DS"+DS;
               if(EM!=null)if(!EM.equals (""))
                   auxstr=auxstr+" EM"+EM;
               if(HI!=null)if(!HI.equals ("")) //should change.. only for ops :)
                   auxstr=auxstr+" HI"+HI;
               if(HN!=null)if(!HN.equals (""))
                   auxstr=auxstr+" HN"+HN;
               if(HO!=null)if(!HO.equals (""))
                   auxstr=auxstr+" HO"+HO;
               if(HR!=null)if(!HR.equals (""))
                   auxstr=auxstr+" HR"+HR;
               if(HU!=null)if(!HU.equals (""))
                   auxstr=auxstr+" HU"+HU;
               if(OP!=null)if(!OP.equals (""))//should change.. only for OPS ;)
                   auxstr=auxstr+" OP"+OP;
               if(RG!=null)if(!RG.equals (""))//should change.. only for regs
                   auxstr=auxstr+" RG"+RG;
               if(SF!=null)if(!SF.equals (""))
                   auxstr=auxstr+" SF"+SF;
               if(SS!=null)if(!SS.equals (""))
                   auxstr=auxstr+" SS"+SS;
               if(SL!=null)if(!SL.equals (""))
                   auxstr=auxstr+" SL"+SL;
               if(SU!=null)if(!SU.equals (""))
                   auxstr=auxstr+" SU"+SU;
               if(TO!=null)if(!TO.equals (""))
                   auxstr=auxstr+" TO"+TO;
               if(U4!=null)if(!U4.equals (""))
                   auxstr=auxstr+" U4"+U4;
               if(U6!=null)if(!U6.equals (""))
                   auxstr=auxstr+" U6"+U6;
               if(VE!=null)if(!VE.equals (""))
                   auxstr=auxstr+" VE"+VE;
               if(US!=null)if(!US.equals (""))
                   auxstr=auxstr+" US"+US;
               
               return auxstr;
    }
     
     
     public void kickMeOut(ClientHandler whokicked,String kickmsg,int bantype,Long kicktime)
     {
         kickmsg=CommandParser.retNormStr (kickmsg);
         if(this.reg.key && Vars.kick_ops==0)
         {
             whokicked.sendFromBot(""+this.NI+" is op, can't kick him.");
             return;
         }
         ClientHandler tempy=FirstClient;
         while(tempy.NextClient!=this)
             tempy=tempy.NextClient;
         ClientHandler temp=tempy.NextClient;
         if(kicktime!=-1)
         {
             if(bantype==3)
         BanList.addban (bantype,temp.ID,1000*kicktime,whokicked.NI,kickmsg);
             else if(bantype==2)
                     BanList.addban (bantype,temp.ClientSock.getInetAddress().getHostAddress(),1000*kicktime,whokicked.NI,kickmsg);
              else if(bantype==1)
                     BanList.addban (bantype,temp.NI,1000*kicktime,whokicked.NI,kickmsg);
        }
         else
          {
         
          if(bantype==3)
        BanList.addban (bantype,temp.ID,kicktime,whokicked.NI,kickmsg);
             else if(bantype==2)
                     BanList.addban (bantype,temp.ClientSock.getInetAddress().getHostAddress(),kicktime,whokicked.NI,kickmsg);
              else if(bantype==1)
                     BanList.addban (bantype,temp.NI,kicktime,whokicked.NI,kickmsg);
                   
          }
         String brcast="IQUI "+temp.SessionID+" ID"+whokicked.SessionID+" TL"+Long.toString (kicktime);
           if(!kickmsg.equals(""))
               brcast=brcast+" MS"+CommandParser.retADCStr (kickmsg);
         new Broadcast(brcast);
           this.sendToClient (brcast);
             
               tempy.NextClient=this.NextClient;
             this. kicked=1;
                    
               try
              {
              this.sleep (200);
               temp.ClientSock.close();
              }
             catch (Exception e)
               {
               }
             whokicked.sendFromBot("Kicked user "+this.NI+" with CID "+this.ID+" out in flames.");
             Main.PopMsg (whokicked.NI+" kicked user "+this.NI+" with CID " + temp.ID+" out in flames.");
                    Main.Server.rewritebans ();
     }
     public void kickMeOut(ClientHandler whokicked,String kickmsg,int bantype)
     {
     kickMeOut( whokicked, kickmsg,bantype,Long.parseLong (Integer.toString (Vars.kick_time)));
     }
     public void sendFromBot(String text)
     {
                   if(this.userok==1)
                   {
                        this.sendToClient ("EMSG DCBA "+this.SessionID+" "+CommandParser.retADCStr (text));
                   }
     }
     public void sendFromBotPM(String text)
     {
                   if(this.userok==1)
                   {
                        this.sendToClient ("EMSG DCBA "+this.SessionID+" "+CommandParser.retADCStr (text)+" PMDCBA");
                   }
     }
     public void dropMe(ClientHandler whokicked)
     {
         if(this.reg.key && Vars.kick_ops==0)
         {
             whokicked.sendFromBot(""+this.NI+" is op, can't drop him.");
             return;
         }
         ClientHandler tempy=FirstClient;
         while(tempy.NextClient!=this)
             tempy=tempy.NextClient;
         ClientHandler temp=tempy.NextClient;
         
         
         new Broadcast("IQUI "+temp.SessionID);
           this.sendToClient ("IQUI "+temp.SessionID+" ID"+whokicked.SessionID);
             
               tempy.NextClient=this.NextClient;
             this. kicked=1;
                    
               try
              {
              this.sleep (200);
               temp.ClientSock.close();
              }
             catch (Exception e)
               {
               }
             whokicked.sendFromBot("Dropped user "+this.NI+" with CID "+this.ID+" down from the sky.");
                  //  Main.Server.rewritebans ();
     }
     
     
    

    
}
