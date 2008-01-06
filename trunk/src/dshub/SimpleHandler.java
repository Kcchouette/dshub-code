/*
 * SimpleHandler.java
 *
 * Created on 06 noiembrie 2007, 14:34
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


import dshub.Exceptions.CommandException;
import dshub.Exceptions.STAException;
import dshub.Modules.Modulator;
import dshub.Modules.Module;
import dshub.TigerImpl.Base32;
import java.util.LinkedList;
import java.util.StringTokenizer;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.TransportType;
import org.apache.mina.transport.socket.nio.SocketSessionConfig;

/**
 *
 * @author Pietricica
 */
public class SimpleHandler extends IoHandlerAdapter
{
    public static LinkedList<ClientNod> Users;
    /** Creates a new instance of SimpleHandler */
    public SimpleHandler()
    {
        //ClientNod.FirstClient=new ClientNod(1);
        Users= new LinkedList();
    }
     public void exceptionCaught(IoSession session, Throwable t) throws Exception 
    {
		//System.out.println(t.getMessage());
         
                if((t.getMessage().contains("IOException")))
                {
                   Main.PopMsg(t.getMessage());
		  session.close();
                return;
                }
                 if((t.getMessage().contains("BufferDataException: Line is too long")))
                 {
                    new STAError((ClientHandler)(session.getAttachment()),000,"Message exceeds buffer."+t.getMessage());
                   // t.printStackTrace();
                 }
                else 
                {
                   // t.printStackTrace();
                    Main.PopMsg(t.getMessage());
		  session.close();
                return;
                };
    }

	public void messageReceived(IoSession session, Object msg) throws Exception 
        {
		String str = msg.toString();
		

		
		//System.out.println("Message received... "+str);
                try
                {
                new Command((ClientHandler)(session.getAttachment()),str);
                }
                catch (STAException stex)
                {
                    if(stex.x<200)
                        return;
                    ClientHandler cur_client=(ClientHandler)(session.getAttachment());
                     if(cur_client.userok==1)
                    {
                         new Broadcast("IQUI "+cur_client.SessionID,cur_client.myNod);
                     }
                    cur_client.myNod.killMe();
                    session.close();
                }
                catch(CommandException cfex)
                {
                    ClientHandler cur_client=(ClientHandler)(session.getAttachment());
                    if(cur_client.userok==1)
                    {
                         new Broadcast("IQUI "+cur_client.SessionID,cur_client.myNod);
                     }
                    cur_client.myNod.killMe();
                    session.close();
                }
                
	}
        public void sessionIdle(IoSession session, IdleStatus status) throws Exception
        {
            //ok, we're in idle
            ClientHandler cur_client=(ClientHandler)(session.getAttachment());
            cur_client.sendToClient("");
            
        }
        public void sessionClosed(IoSession session) throws Exception
        {
            
            ClientHandler cur_client=(ClientHandler)(session.getAttachment());
            if(cur_client.userok==1 && cur_client.kicked!=1)
            {
                new Broadcast("IQUI "+cur_client.SessionID,cur_client.myNod);
            }
            /** calling plugins...*/
                 
                 for(Module myMod : Modulator.myModules)
                 {
                     myMod.onClientQuit(cur_client);
                 }
             cur_client.reg.TimeOnline+=System.currentTimeMillis()-cur_client.LoggedAt;
             Main.PopMsg(cur_client.NI+" with SID " + cur_client.SessionID+" just quited.");
             cur_client.myNod.killMe();
            
        }

	public void sessionCreated(IoSession session) throws Exception 
        {
		//System.out.println("Client Connected...");

		//if( session.getTransportType() == TransportType.SOCKET )
		//	((SocketSessionConfig) session.getConfig() ).setReceiveBufferSize( 2048 );
//((SocketSessionConfig) session.getConfig() ).
             //   session.
                
                ClientHandler cur_client=(ClientHandler)HubServer.AddClient().cur_client;
                
                session.setAttachment(cur_client);
              session.setIdleTime( IdleStatus.READER_IDLE, 120 );
              
              cur_client.mySession=session;
              StringTokenizer ST=new StringTokenizer(cur_client.mySession.getRemoteAddress().toString(),"/:");
              cur_client.RealIP=ST.nextToken();
              //System.out.println(cur_client.RealIP);
               SID cursid=new SID(cur_client);
        cur_client.SessionID=Base32.encode (cursid.cursid).substring (0,4);
        cur_client.sid=cursid.cursid;
                
        
	}
    
}
