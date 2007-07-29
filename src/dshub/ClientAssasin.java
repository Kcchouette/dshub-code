/*
 * ClientAssasin.java
 *
 * Created on 26 mai 2007, 19:18
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

/**
 *
 * @author Pietricica
 */
public class ClientAssasin extends Thread
{
    
    /** Creates a new instance of ClientAssasin */
   
    
   
    public ClientAssasin ()
    {
        
        start();
    }
    
    public void run()
    {
        
        while(!Main.Server.restart)
        {
            ClientHandler temp=ClientHandler.FirstClient;
            
            while(temp.NextClient!=null )
            {
                if(temp.NextClient.userok==1)
                if((System.currentTimeMillis ()-temp.NextClient.LastKeepAlive)>Long.parseLong ("240000"))
                {
              
                    try
                    {
                        temp.NextClient.OS.write (0x0a);
                    }
                    catch(Exception e)
                    {
                    
                    
                    
         ClientHandler x=temp.NextClient;
         new Broadcast("IQUI "+temp.NextClient.SessionID);
          // x.sendToClient ("IQUI "+temp.NextClient.SessionID);
             Main.PopMsg(x.NI+" was dropped due to timeout."+(System.currentTimeMillis ()-temp.NextClient.LastKeepAlive)/1000);
               temp.NextClient=x.NextClient;
             
                x.kicked=1;    
               try
              {
              x.sleep (200);
               x.ClientSock.close();
              }
             catch (Exception ef)
               {
               }
                    }
               }
                if(temp.NextClient.InQueueSearch!=null)
                if(temp.NextClient.userok==1)
                {
                  
                    double x=1;
                        for(int i=0;i<temp.NextClient.search_step;i++)
                            x*=((double)Vars.search_log_base)/1000;
                        x*=1000;
                        long xx=(long)x;
                        if(temp.NextClient.search_step>=Vars.search_steps)
                            xx=Vars.search_spam_reset*1000;
                  // System.out.println(xx);
                    if((System.currentTimeMillis ()-temp.NextClient.Lastsearch)>xx)
                    {
                    
                        if(temp.NextClient.InQueueSearch.startsWith("B"))
                            new Broadcast(temp.NextClient.InQueueSearch);
                        else
                            new Broadcast(temp.NextClient.InQueueSearch,1);
                        temp.NextClient.InQueueSearch=null;
                        temp.NextClient.Lastsearch=System.currentTimeMillis ();
                    }
                    
                }
                 temp=temp.NextClient;
                 if(temp==null)
                     break;
            }
            //temp.PS.printf
       // new Broadcast("");
            //System.out.println("gay.");
            //if(temp!=null)
           // new Broadcast("IMSG Debug Message Please Ignore, Check "+temp.NI);
        try
        {
        this.sleep(5000);
        }
        catch(Exception e)
        {
        }
            
        }
    }
    
}
