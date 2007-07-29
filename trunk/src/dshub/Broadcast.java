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

/**
 *
 * @author Pietricica
 */
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
public class Broadcast extends Thread
{
    int state=0;
    /*state =0 normal, to all
     *state=1 only to active
     *state=2 to all except newest client
     **/
    String STR;
    ClientHandler cur_client=null;
    
    
    static line First=null;
    static line Last=null;
    

   
    static int size=0;
    /** Creates a new instance of Broadcast */
    public Broadcast (String STR, ClientHandler bla)
    {
        this.STR=STR;
        
        cur_client=bla;
        start();
    }
     public Broadcast (String STR, int active)
    {
        this.STR=STR;
        
        state=active; // 1 - active only
        //10 - ops only
        start();
    }
    public Broadcast (String STR)
    {
        
        this.STR=STR;
        start();
    }
     public void   sendToAll(ClientHandler CH)
    {
       
         String NI="";
         
        do
        {
         if(STR.startsWith ("BMSG ") || STR.startsWith("IMSG "))
        {
             NI=Vars.bot_name;
             if(CH.userok==1)
                 
             if(STR.startsWith ("BMSG "))   
             if(CH.SessionID.equals (STR.substring (5,9)))
             {
                NI=CH.NI;
             }
             
       if((STR.startsWith ("BMSG ") && CH.SessionID.equals (STR.substring (5,9))) || STR.startsWith ("IMSG "))
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
         
           
              
            
        if((CH.userok==1 && CH!=cur_client) || (CH.userok==1 && state==1 && CH.ACTIVE==1))
        {
             if(state==10 && !CH.reg.key)
             {
                 CH=CH.NextClient;
                 continue;
             }
           if(STR.startsWith ("IMSG "))
             CH.sendFromBot (STR.substring (5)); 
           else
               CH.Queue.addMsg (STR);
        }
        CH=CH.NextClient;
        }
        while(CH!=null);
            //System.out.printf ("Broadcasting to %s: %s\n",CH.NI,STR);
        
    }
    
    public void run()
    {
        if(ClientHandler.FirstClient!=null)
        if(ClientHandler.FirstClient.NextClient!=null)
       sendToAll(ClientHandler.FirstClient.NextClient);
       
    }
    
}
