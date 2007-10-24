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
 * Permanent thread that keeps clients connected ( meaning killing the ones who
 * are disconnected). Also sends delayed searches and will be used for cronlike jobs.
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
                long curtime=System.currentTimeMillis();
                ClientHandler x=temp.NextClient;
                if(temp.NextClient.userok==1)
                {
                    if(temp.NextClient.cur_inf!=null)
                        if(curtime-temp.NextClient.LastINF>(1000*120L))
                    {
                        new Broadcast(temp.NextClient.cur_inf);
                        temp.NextClient.LastINF=curtime;
                        temp.NextClient.cur_inf=null;
                        
                        }
                }
                if(temp.NextClient.userok==1)
                if((curtime-temp.NextClient.LastKeepAlive)>Long.parseLong ("240000"))
                {
                  
                    try
                    {
                        temp.NextClient.OS.write (0x0a);
                        temp.NextClient.LastKeepAlive=curtime;
                    }
                    catch(Exception e)
                    {
                    
                   ClientHandler cur_client=temp.NextClient; 
            cur_client.PrevClient.NextClient=cur_client.NextClient;
            if(cur_client.NextClient!=null)
           cur_client.NextClient.PrevClient=cur_client.PrevClient;
         
         new Broadcast("IQUI "+temp.NextClient.SessionID);
          // x.sendToClient ("IQUI "+temp.NextClient.SessionID);
             Main.PopMsg(x.NI+" was dropped due to timeout."+(curtime-temp.NextClient.LastKeepAlive)/1000);
             x.reg.TimeOnline+=curtime-x.LoggedAt;
               //temp.NextClient=x.NextClient;
             
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
                if(x.kicked!=1)
                if(x.InQueueSearch!=null)
                if(x.userok==1)
                {
                  
                    double xy=1;
                        for(int i=0;i<x.search_step;i++)
                            xy*=((double)Vars.search_log_base)/1000;
                        xy*=1000;
                        long xx=(long)xy;
                        if(x.search_step>=Vars.search_steps)
                            xx=Vars.search_spam_reset*1000;
                  // System.out.println(xx);
                    if((curtime-x.Lastsearch)>xx)
                    {
                    
                        if(x.InQueueSearch.startsWith("B"))
                            new Broadcast(x.InQueueSearch);
                        else
                            new Broadcast(x.InQueueSearch,1);
                        x.InQueueSearch=null;
                       x.Lastsearch=curtime;
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
