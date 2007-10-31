/*
 * ClientNod.java
 *
 * Created on 29 octombrie 2007, 11:35
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

import java.net.Socket;

/**
 * A class that keeps a ClientHandler thread instance and connections to other nods
 * @author Administrator
 */
public class ClientNod
{
    
    ClientHandler cur_client;
    ClientNod NextClient=null;
    ClientNod PrevClient=null;
    
    static ClientNod FirstClient=null;
    
    /** Creates a new instance of ClientNod */
   
    public ClientNod()
    {
       
       
       
      
        //FirstClient=this;
       cur_client=new ClientHandler(this);
        NextClient=null;
        PrevClient=null;
           
        
    }
    public ClientNod(int i)
    {
       
       FirstClient=this;
       cur_client=new ClientHandler();
           
        
    }
    
   
     public void kickMeOut(ClientHandler whokicked,String kickmsg,int bantype,Long kicktime)
     {
         kickmsg=ADC.retNormStr (kickmsg);
         if(!cur_client.reg.kickable)
         {
             whokicked.sendFromBot(""+cur_client.NI+" is unkickable.");
             return;
         }
         //ClientHandler tempy=FirstClient;
        // while(tempy.NextClient!=this)
          //   tempy=tempy.NextClient;
        // ClientHandler temp=tempy.NextClient;
         if(kicktime!=-1)
         {
             if(bantype==3)
         BanList.addban (bantype,cur_client.ID,1000*kicktime,whokicked.NI,kickmsg);
             else if(bantype==2)
                     BanList.addban (bantype,cur_client.ClientSock.getInetAddress().getHostAddress(),1000*kicktime,whokicked.NI,kickmsg);
              else if(bantype==1)
                     BanList.addban (bantype,cur_client.NI,1000*kicktime,whokicked.NI,kickmsg);
        }
         else
          {
         
          if(bantype==3)
        BanList.addban (bantype,cur_client.ID,kicktime,whokicked.NI,kickmsg);
             else if(bantype==2)
                     BanList.addban (bantype,cur_client.ClientSock.getInetAddress().getHostAddress(),kicktime,whokicked.NI,kickmsg);
              else if(bantype==1)
                     BanList.addban (bantype,cur_client.NI,kicktime,whokicked.NI,kickmsg);
                   
          }
         String brcast="IQUI "+cur_client.SessionID+" ID"+whokicked.SessionID+" TL"+Long.toString (kicktime);
         cur_client.reg.TimeOnline+=System.currentTimeMillis()-cur_client.LoggedAt;
           if(!kickmsg.equals(""))
               brcast=brcast+" MS"+ADC.retADCStr (kickmsg);
         new Broadcast(brcast);
           cur_client.sendToClient (brcast);
             
           this.PrevClient.NextClient=this.NextClient;
           if(this.NextClient!=null)
           this.NextClient.PrevClient=this.PrevClient;
             cur_client. kicked=1;
                    
               try
              {
             // cur_client.sleep (200);
               cur_client.ClientSock.close();
              }
             catch (Exception e)
               {
               }
             whokicked.sendFromBot("Kicked user "+cur_client.NI+" with CID "+cur_client.ID+" out in flames.");
             Main.PopMsg (whokicked.NI+" kicked user "+cur_client.NI+" with CID " + cur_client.ID+" out in flames.");
                    Main.Server.rewritebans ();
     }
     public void kickMeOut(ClientHandler whokicked,String kickmsg,int bantype)
     {
     kickMeOut( whokicked, kickmsg,bantype,Long.parseLong (Integer.toString (Vars.kick_time)));
     }
       public void dropMe(ClientHandler whokicked)
     {
         if(!cur_client.reg.kickable)
         {
             whokicked.sendFromBot(""+cur_client.NI+" is undroppable.");
             return;
         }
         //ClientHandler tempy=FirstClient;
        // while(tempy.NextClient!=this)
         //    tempy=tempy.NextClient;
        // ClientHandler temp=tempy.NextClient;
         
         
         new Broadcast("IQUI "+cur_client.SessionID);
           cur_client.sendToClient ("IQUI "+cur_client.SessionID+" ID"+whokicked.SessionID);
             cur_client.reg.TimeOnline+=System.currentTimeMillis()-cur_client.LoggedAt;
               //tempy.NextClient=this.NextClient;
           this.PrevClient.NextClient=this.NextClient;
           if(this.NextClient!=null)
           this.NextClient.PrevClient=this.PrevClient;
             cur_client. kicked=1;
                    
               try
              {
              //cur_client.sleep (200);
               cur_client.ClientSock.close();
              }
             catch (Exception e)
               {
               }
             whokicked.sendFromBot("Dropped user "+cur_client.NI+" with CID "+cur_client.ID+" down from the sky.");
                  //  Main.Server.rewritebans ();
     }
       
       public void killMe()
       {
           this.PrevClient.NextClient=this.NextClient;
           if(this.NextClient!=null)
              this.NextClient.PrevClient=this.PrevClient;
           
       }
    
}
