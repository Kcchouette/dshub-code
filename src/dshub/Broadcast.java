/*
 * Broadcast.java
 *
 * Created on 23 aprilie 2007, 19:05
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

import org.apache.mina.common.IoSession;


 class line
    {
        public String curline;
        
        line Next;
        public line(String str)
        {
            
            Next=null;
            curline=str;
        }
    }
 /**
 * Provides broadcasts and feature broadcasts constructors to all connected clients.
 *
 * @author Pietricica
 */
public class Broadcast
{
    
    public static final int STATE_ALL=0;
    public static final int STATE_ACTIVE=1;
   
    public static final int STATE_ALL_KEY=10;
    
    int state=0;
    
    String STR;
    ClientNod cur_client=null;
    
    
    static line First=null;
    static line Last=null;
    

   
    static int size=0;
    /** Creates a new instance of Broadcast , sends to all except the ClientNod received as param.*/
    public Broadcast (String STR, ClientNod bla)
    {
        this.STR=STR;
        
        cur_client=bla;
        run();
    }
    /**state =STATE_ALL normal, to all
     *state=STATE_ACTIVE only to active
     * state= STATE_ALL_KEY to all ops;
     **/
     public Broadcast (String STR, int state)
    {
        this.STR=STR;
        
        this.state=state; // STATE_ACTIVE - active only
        //STATE_ALL_KEY - ops only
        run();
    }
    public Broadcast (String STR)
    {
        
        this.STR=STR;
        run();
    }
     public void   sendToAll()
    {
       
         String NI="";
         
         IoSession [] x= Main.Server.SM.getSessions().toArray(new IoSession[0]);
             for(int i=0;i<x.length;i++)
        {
          //  x[i]= ((IoSession) (x[i]));
        ClientNod CH=((ClientHandler)(x[i].getAttachment())).myNod;
         if(STR.startsWith ("BMSG ") || STR.startsWith("IMSG "))
        {
             NI=Vars.bot_name;
             if(CH.cur_client.userok==1)
                 
             if(STR.startsWith ("BMSG "))   
             if(CH.cur_client.SessionID.equals (STR.substring (5,9)))
             {
                NI=CH.cur_client.NI;
             }
             
       if((STR.startsWith ("BMSG ") && CH.cur_client.SessionID.equals (STR.substring (5,9))) || STR.startsWith ("IMSG "))
        if(First==null)
        {
                 line bla;
               if(STR.startsWith ("BMSG "))   
             bla=new line("<"+
                    NI+"> "+
                    STR.substring (10)+"\n");
               else
                   bla=new line("<"+
                    NI+"> "+
                    STR.substring (5)+"\n");
            Last=bla;
            First=Last;
            size++;
        }
        
        else
        {
           line bla;
                //BMSG AAAA message
                 if(STR.startsWith ("BMSG "))   
             bla=new line("<"+NI+"> "+STR.substring (10)+"\n");
                 else
                   bla=new line("<"+
                    NI+"> "+
                    STR.substring (5)+"\n");
           
           if(!(Last.curline.equals (bla.curline) && STR.startsWith ("IMSG ")))
           {    
            Last.Next=bla;
            
            size++;
            
            
            Last=bla;
            
            while(size>=Vars.history_lines)
            {
                First=First.Next;  
                size--;
            }
           }
        }
        
        }
         
           
             
            
        if((CH.cur_client.userok==1 && CH!=cur_client) || (CH!=cur_client && CH.cur_client.userok==1 && state==1 && CH.cur_client.ACTIVE==1))
        {
             if(state==this.STATE_ALL_KEY && !CH.cur_client.reg.key)
             {
                 
                 continue;
             }
             if(CH.cur_client.ACTIVE!=1 && state==this.STATE_ACTIVE)
             {
                 
                 continue;
             }
             if(!STR.startsWith ("E") && CH==cur_client)
             {
                
                 continue;
             }
           if(STR.startsWith ("IMSG "))
             CH.cur_client.sendFromBot (STR.substring (5)); 
           else
               CH.cur_client.sendToClient(STR);
        }
        
        }
        
            //System.out.printf ("Broadcasting to %s: %s\n",CH.NI,STR);
        
    }
    
    public void run()
    {
       
       sendToAll();
       
    }
    
}
