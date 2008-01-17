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
import org.apache.mina.common.IoSession;


/**
 * Main client class, keeps all info regarding a client, also provides forward and backlinks to other clients.
 * Also implements disconnecting methods.
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

public class ClientHandler 
{
    /** main client socket*/
    //Socket ClientSock;
    
   
    
    public ClientNod myNod;
    
    public int logged_in=0;
    public int userok=0;
    public int ACTIVE=0;
    public int quit=0;
    
    public long LoggedAt=0l;
    
    public Nod reg;
    public Ban myban;
    public String RandomData;
    
    public long LastChatMsg;
   public  long LastKeepAlive;
    
    public long LastCTM;
    public long LastINF;
    
   public  String State="PROTOCOL";
    
    public String RealIP;
    
   public ClientQueue Queue;
    
/** The CID of the client. Mandatory for C-C connections.*/
public String ID="";
/**The PID of the client. Hubs must check that the Tiger(PID) == CID and then discard the field before broadcasting it to other clients. Must not be sent in C-C connections.*/
public String PD;
/**IPv4 address without port. A zero address (0.0.0.0) means that the server should replace it with the real IP of the client. Hubs must check that a specified address corresponds to what the client is connecting from to avoid DoS attacks, and only allow trusted clients to specify a different address. Clients should use the zero address when connecting, but may opt not to do so at the user's discretion. Any client that supports incoming TCPv4 connections must also add the feature TCP4 to their SU field.*/
public String I4;
/**IPv6 address without port. A zero address (::) means that the server should replace it with the IP of the client. Any client that supports incoming TCPv6 connections must also add the feature TCP6 to their SU field.*/
public String I6;
/**Client UDP port. Any client that supports incoming UDPv4 packets must also add the feature UDP4 to their SU field.*/
public String U4;
/**Same as U4, but for IPv6. Any client that supports incoming UDPv6 packets must also add the feature UDP6 to their SU field.*/
public String U6;
/**Share size in bytes, integer.*/
public String SS;
/**Number of shared files, integer*/
public String SF;
/**Client identification, version (client-specific, a short identifier then a floating-point version number is recommended). Hubs should not discriminate agains clients based on their VE tag but instead rely on SUP when it comes to which clients should be allowed (for example, we only want regex clients).*/	
public String VE;
/**Maximum upload speed, bits/sec, integer*/
public String US;
/**Maximum download speed, bits/sec, integer*/
public String DS;
/**Upload slots open, integer*/
public String SL;
/**Automatic slot allocator speed limit, bytes/sec, integer. This is the recommended method of slot allocation, the client keeps opening slots as long as its total upload speed doesn�t exceed this value. SL then serves as a minimum number of slots open.*/
public String AS;
/**Maximum number of slots open in automatic slot manager mode, integer.*/
public String AM;
/**E-mail address, string.*/
public String EM;
/**Nickname, string. The hub must ensure that this is unique in the hub up to case-sensitivity. Valid are all characters in the Unicode character set with code point above 32, although hubs may limit this further as they like with an appropriate error message.
 *When sent for hub, this is the nick that should be displayed before messages from the hub, and may also be used as short name for the hub.*/
public String NI="";
/**Description, string. Valid are all characters in the Unicode character set with code point equal to or greater than 32.
*When sent by hub, this string should be displayed in the window title of the hub window (if one exists)*/	
public String DE;
/**Hubs where user is a normal user and in NORMAL state, integer. While connecting, clients should not count the hub they're connecting to. Hubs should increase one of the three the hub counts by one before passing the client to NORMAL state.*/
public String HN;
/**Hubs where user is registered (had to supply password) and in NORMAL state, integer.*/
public String HR;
/**Hubs where user is op and in NORMAL state, integer.*/
public String HO;
/**Token, as received in RCM/CTM, when establishing a C-C connection.*/
public String TO;
/**1=op*/
public String OP;
/**1=registered*/
public String RG;
/**1=Away
*2=Extended away, not interested in hub chat (hubs may skip sending broadcast type MSG commands to clients with this flag)*/
public String AW;
/**1=Bot (in particular, this means that the client does not support file transfers, and thus should never be queried for direct connections)*/
public String BO;
/**1=Hidden, should not be shown on the user list.*/
public String HI;
/**1=Hub, this INF is about the hub itself*/
public String HU;
/**Comma-separated list of feature FOURCC's. This notifies other clients of extended capabilities of the connecting client. Use with discretion.*/
public String SU;
	
public int search_step=0;
public long Lastsearch=0L;
public long Lastautomagic=0L;


public String InQueueSearch=null;

public static int user_count=0;
public int kicked=0;
    
    public String SessionID;
    byte [] sid;
    /** indicates if client is a pinger a.k.a. PING extension */
    public boolean ping;
    
    
    /** indicates if client supports UCMD messages*/
    public int ucmd;
    /**indicates if client supports BASE messages*/
    public int base; 
    /** Client Connect time in millis as Syste.gettimemillis() ; ;)*/
   public  long ConnectTimeMillis;
   public  String cur_inf;
    
    public IoSession mySession;
    
    /** Creates a new instance of ClientHandler */
    
    public ClientHandler()
    {
        ClientHandler.user_count++;
       // myNod=my;
        base=0;
        ucmd=0;
       // ClientSock=s;
       // Queue=new ClientQueue(this);
        
        
      sid=null;
      myban=null;
      LastChatMsg=0;
      LastCTM=0L;
      LastINF=0L;
      cur_inf=null;
      reg=new Nod();
      
        ConnectTimeMillis=System.currentTimeMillis();
    }
   
    
   
    /** sends the bla String in RAW to client.
     *adds the \n ending char ;)
     */
    
    
    
     public void sendToClient(String bla)
    {
        
         //this.Queue.addMsg (bla);
        //System.out.println("[sent]: "+bla);
         mySession.write(bla);
       
    
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
     
     
    
     public void sendFromBot(String text)
     {
         if(text.equals(""))
             return;
                   if(this.userok==1)
                   {
                        this.sendToClient ("EMSG DCBA "+this.SessionID+" "+ADC.retADCStr (text));
                   }
     }
     public void sendFromBotPM(String text)
     {
                   if(this.userok==1)
                   {
                        this.sendToClient ("EMSG DCBA "+this.SessionID+" "+ADC.retADCStr (text)+" PMDCBA");
                   }
     }
   
     
     public void putOpchat(boolean x)
     {
         if(x==true)
         {
             if(this.reg.isreg && this.reg.opchataccess==true)
                 this.sendToClient("BINF ABCD ID"+Vars.OpChatCid+" NI"+ADC.retADCStr(Vars.Opchat_name)+" BO1 OP1 DE"+ADC.retADCStr(Vars.Opchat_desc));
         }
         else
         {
             if(this.reg.isreg && this.reg.opchataccess==true)
                  this.sendToClient("IQUI ABCD");
         }
     }
     
     
    

    
}
