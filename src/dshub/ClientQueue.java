/*
 * ClientQueue.java
 *
 * Created on 24 aprilie 2007, 20:49
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
import java.io.IOException;

/**
 *
 * @author Pietricica
 */
public class ClientQueue extends Thread
{
    
    ClientHandler cur_client;
    
    public class msgnod
    {
        String MSG;
        msgnod Next;
        public msgnod()
        {
            Next=null;
        }
    };
    
    msgnod First,Last;
    /** Creates a new instance of ClientQueue */
    public ClientQueue (ClientHandler CH)
    {
        cur_client=CH;

        First=null;
        Last=null;
        start();
    }
    
    public void addMsg(String newmsg)
    {
        msgnod Bla=new msgnod();
        if(First==null)
            First=Bla;
        if(Last==null)
            Last=Bla;
        Last.Next=Bla;
        Last=Bla;
        Bla.MSG=newmsg;
        
       
    }
    
    public void run()
    {
        try
        {
            while(true)
            {
                if(!cur_client.ClientSock.isConnected () && cur_client.userok==1)
                    throw new Exception();
                if(cur_client.ClientSock.isClosed ())
                    break;
                while(First!=null)
                {
                cur_client.PS.printf ("%s\n",First.MSG);
                
                First=First.Next;
                }
                this.sleep (50);
            }
        }
        catch (Exception e)
        {
            //ClientHandler tempy=ClientHandler.FirstClient;
            
            
           /* while (!tempy.NextClient.equals(cur_client) && tempy.NextClient!=null)
            {
                
                tempy=tempy.NextClient;
                if(tempy.NextClient==null)
                    break;
            }*/
          if(cur_client.userok==1) //if he ever logged in... else is no point in sending QUI
          {
                 new Broadcast("IQUI "+cur_client.SessionID);
                    // System.out.printf ("[disconnected:] %s\n",this.NI);  
          }
           cur_client.PrevClient.NextClient=cur_client.NextClient;
           if(cur_client.NextClient!=null)
           cur_client.NextClient.PrevClient=cur_client.PrevClient;
            //tempy.NextClient=tempy.NextClient.NextClient;
            try
            {
            cur_client.ClientSock.close();
            }
            catch (IOException ee)
            {
            }
            
        }
    }
    
}
