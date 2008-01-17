/*
 * SUP.java
 *
 * Created on 29 septembrie 2007, 14:56
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

package dshub.ProtoCmds;

import dshub.*;
import dshub.Exceptions.CommandException;
import dshub.Exceptions.STAException;
import java.util.StringTokenizer;

/**
 * Implementation of the ADC SUP command, feature checker of clients.
 *
 * @author Pietricica
 */
public class SUP
{
    
    /** Creates a new instance of SUP */
    public SUP (ClientHandler cur_client, String State, String Issued_Command) throws STAException, CommandException
    {
        if(!cur_client.reg.overridespam)
        switch(Issued_Command.charAt(0))
     {
         case 'B':
             if(Vars.BSUP!=1)
             { new STAError(cur_client,140,"SUP Invalid Context B");
                       return;}break;
         case 'E':
              if(Vars.ESUP!=1)
              {  new STAError(cur_client,140,"SUP Invalid Context E");
                       return;}break;
         case 'D':
          if(Vars.DSUP!=1)
          {   new STAError(cur_client,140,"SUP Invalid Context D");
                       return;
         }break;
         case 'F':
          if(Vars.FSUP!=1)
           {   new STAError(cur_client,140,"SUP Invalid Context F");
                       return;}break;
         case 'H':
              if(Vars.HSUP!=1)
              {   new STAError(cur_client,140,"SUP Invalid Context H");
                       return;}
                 
     }
        
         if(Issued_Command.charAt (0)!='H')
                    {
                        if(State.equals ("PROTOCOL"))
                         throw new CommandException("FAIL state:PROTOCOL reason:NOT BASE CLIENT");
                        
                    } 
                    if(State.equals ("VERIFY")||State.equals ("IDENTIFY"))
                        {
                            new STAError(cur_client,240,"SUP Invalid State.");
                            return;
                        }
                    Issued_Command=Issued_Command.substring(4);
                    StringTokenizer tok=new StringTokenizer(Issued_Command);
                    while(tok.hasMoreTokens ())
                    {
                        String aux=tok.nextToken ();
                        if(aux.startsWith ("AD"))
                        {
                            aux=aux.substring (2);
                            if(aux.startsWith ("BAS") && aux.length()==4)
                                cur_client.base=1;
                            if(aux.startsWith ("PIN") && aux.length()==4)
                                cur_client.ping=true;
                            if(aux.startsWith ("UCM") && aux.length()==4)
                                cur_client.ucmd=1;
                        }
                        else if(aux.startsWith ("RM"))
                        {
                            aux=aux.substring (2);
                            if(aux.startsWith ("UCM") && aux.length()==4)
                                cur_client.ucmd=0;
                            if(aux.startsWith ("BAS") && aux.length()==4)
                                cur_client.base=0;
                            if(aux.startsWith ("PIN") && aux.length()==4)
                                cur_client.ping=false;
                        }
                    }
                    if(cur_client.base==0)
                      if(State.equals("PROTOCOL"))
                           throw new CommandException("FAIL state:PROTOCOL reason:NOT BASE CLIENT");  
                      else if (State.equals ("NORMAL"))
                      {
                          new STAError(cur_client,240,"You removed BASE features therefore you can't stay on hub anymore.");
                          return;
                      }
                    if(State.equals("PROTOCOL"))
                    {
                   cur_client. sendToClient(ADC.Init);
         
       
        cur_client.sendToClient(ADC.ISID+" "+cur_client.SessionID);
         if(!cur_client.ping)
        if(Vars.HubDE.equals (""))
            cur_client.sendToClient("IINF HU1 HI1 VE"+ADC.retADCStr (Vars.HubVersion)+" NI"+ADC.retADCStr(Vars.HubName));
        else
           cur_client. sendToClient("IINF HU1 HI1 VE"+ADC.retADCStr (Vars.HubVersion)+" NI"+ADC.retADCStr(Vars.HubName)+ " DE"+ADC.retADCStr(Vars.HubDE));
       
         else
             //its a PINGer
         if(Vars.HubDE.equals (""))
            cur_client.sendToClient("IINF HU1 HI1 VE"+ADC.retADCStr (Vars.HubVersion)+" NI"+ADC.retADCStr(Vars.HubName)
                     +" HH"+Vars.Hub_Host+" UC"+SimpleHandler.getUserCount()+" SS"+
                     SimpleHandler.getTotalShare()+" SF"+SimpleHandler.getTotalFileCount()+
                     " MS"+1024*1024*Vars.min_share+" XS"+1024*1024*Vars.max_share+
                     " ML"+Vars.min_sl+ " XL"+Vars.max_sl+" XU"+Vars.max_hubs_user+
                     " XR"+Vars.max_hubs_reg+" XO"+Vars.max_hubs_op+
                     " MC"+Vars.max_users
                    
                    
                    );
        else
           cur_client. sendToClient("IINF HU1 HI1 VE"+ADC.retADCStr (Vars.HubVersion)+" NI"+ADC.retADCStr(Vars.HubName)+ " DE"+ADC.retADCStr(Vars.HubDE));
       cur_client. sendToClient("ISTA 000 "+
            "Running\\sTheta\\sVersion\\sof\\sDSHub.\nISTA 000 Hub\\sis\\sup\\ssince\\s"+ Main.Server.MyCalendar.getTime ().toString ().replaceAll (" ","\\\\s")
             +" HH"+Vars.Hub_Host+" UC"+SimpleHandler.getUserCount()+" SS"+
                     SimpleHandler.getTotalShare()+" SF"+SimpleHandler.getTotalFileCount()+
                     " MS"+1024*1024*Vars.min_share+" XS"+1024*1024*Vars.max_share+
                     " ML"+Vars.min_sl+ " XL"+Vars.max_sl+" XU"+Vars.max_hubs_user+
                     " XR"+Vars.max_hubs_reg+" XO"+Vars.max_hubs_op+
                     " MC"+Vars.max_users
            
            );
       
       cur_client. sendToClient("ISTA 000 "+
            "Running\\sTheta\\sVersion\\sof\\sDSHub.\nISTA 000 Hub\\sis\\sup\\ssince\\s"+ Main.Server.MyCalendar.getTime ().toString ().replaceAll (" ","\\\\s"));
             
                    }
    }
    
}
