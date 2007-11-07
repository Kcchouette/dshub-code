/*
 * MSG.java
 *
 * Created on 27 septembrie 2007, 12:14
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

import java.util.StringTokenizer;

/**
 * Implementation of the MSG command in ADC protocol.
 *
 * @author Pietricica
 */
public class MSG
{
    
    /** Creates a new instance of MSG */
    public MSG (ClientHandler cur_client, String State, String Issued_Command) throws STAException
    {
        if(State.equals ("IDENTIFY") || State.equals ("VERIFY") || State.equals ("PROTOCOL"))
        new STAError(cur_client,240,"MSG Invalid State.");
      
     switch(Issued_Command.charAt(0))
     {
         case 'B':
             if(Vars.BMSG!=1)
             { new STAError(cur_client,140,"MSG Invalid Context B");
                       return;}break;
         case 'E':
              if(Vars.EMSG!=1)
              {  new STAError(cur_client,140,"MSG Invalid Context E");
                       return;}break;
         case 'D':
          if(Vars.DMSG!=1)
          {   new STAError(cur_client,140,"MSG Invalid Context D");
                       return;
         }break;
         case 'F':
          if(Vars.FMSG!=1)
           {   new STAError(cur_client,140,"MSG Invalid Context F");
                       return;}break;
         case 'H':
              if(Vars.HMSG!=1)
              {   new STAError(cur_client,140,"MSG Invalid Context H");
                       return;}
                 
     }
                   //System.out.println (Issued_Command) ;
                    StringTokenizer tok=new StringTokenizer(Issued_Command);
                    String aux=tok.nextToken ();
                    if(Issued_Command.charAt (0)=='H') //for hub only, special check
                   {
                   if(aux.equals ("Test"))
                       cur_client.sendFromBot("Test OK.");
                   return;
                   }
               
                    if(!tok.nextToken().equals(cur_client.SessionID))
                    {
                      new STAError(cur_client,240,"Protocol Error. Wrong SID supplied.");
                      return ;
                    }
                    String pmsid=null;
                   if(Issued_Command.charAt (0)=='D' || Issued_Command.charAt (0)=='E')
                       pmsid=tok.nextToken ();
                   String message=tok.nextToken ();
                   
                    if(message.length ()>Vars.max_chat_msg )
                   {
                       if(
                        !(cur_client.reg.overridespam))
                       {
                       new STAError(cur_client,140,"Message exceeds maximum lenght.");
                       return;
                       }
                   }
                   int index=Main.listaBanate.isOK(message);
                   System.out.println(index);
                   if(index!=-1)//not ok
                   {
                       long what=Main.listaBanate.getPrAt(index);
                       /* 
                         static final long dropped=1;
    static final long kicked=2;
    static final long noAction=4;
    static final long hidden=8;
    static final long replaced=16;
    static final long modified=32;
    static final long allclient=7;
    static final long allword=56;*/
                     //  long what=56;
                       System.out.println(what);
                       if(what % 2 ==1)
                       {
                           new STAError(cur_client,201,"You typed forbidden word.");
                          // System.out.println("flag contine 1");
                       }
                       what /=2;
                        if(what % 2 ==1)
                        {
                           //System.out.println("flag contine 2");
                           cur_client.myNod.kickMeByBot("You typed forbidden word.",3);
                        }
                       what/=2;
                        if(what % 2 ==1)
                        {
                           //System.out.println("flag contine 4");
                           ;
                        }   
                       what/=2;
                        if(what % 2 ==1)
                        {
                           //System.out.println("flag contine 8");
                           ;
                           return;
                        } 
                       what/=2;
                        if(what % 2 ==1)
                        {
                           //System.out.println("flag contine 16");
                           Issued_Command=Issued_Command.replace(message,"****");
                           
                        } 
                       what/=2;
                        if(what % 2 ==1)
                        {
                           //System.out.println("flag contine 32");
                           Issued_Command=Issued_Command.replace(message,Main.listaBanate.getReplAt(index));
                           
                        } 
                   }
                   String thissid=null;
                   int me=0;
                    while(tok.hasMoreElements())
                    {
                       aux=tok.nextToken();   
                       if(aux.startsWith ("PM"))
                           thissid=aux.substring (2);
                       if(aux.startsWith ("ME"))
                           if(aux.substring (2)=="1")
                             me=1;
                           else
                           {
                               new STAError(cur_client,140,"MSG Invalid Flag.");
                               return;
                           }
                       
                               
                    }
                   long now=System.currentTimeMillis ();
                   if(cur_client.LastChatMsg!=0)
                   {
                       if(now-cur_client.LastChatMsg<Vars.chat_interval)
                       {
                           if(!cur_client.reg.overridespam)
                               
                               {
                       new STAError(cur_client,000,"Chatting Too Fast. Minimum chat interval "+String.valueOf (Vars.chat_interval)+" .You made "+String.valueOf (now-cur_client.LastChatMsg)+".");
                           return;
                               }
                           
                       }
                       
                       else
                     cur_client.LastChatMsg=now;
                   }
                   else  cur_client.LastChatMsg=now;
                   
                  
                   if(Issued_Command.charAt (0)=='B') //broadcast
                   {
                       if(pmsid!=null)
                       {
                           new STAError(cur_client,140,"MSG Can't PM to Mainchat.");
                           return;
                       }
                       // ok now lets check that the chat is a command....
                       if(cur_client.reg.isreg && message.charAt (0)=='!' || cur_client.reg.isreg && message.charAt (0)=='+') //ok.. command mode.
                       {
                           
                           if(message.toLowerCase().startsWith("!adc") || message.toLowerCase().startsWith("+adc"))//adc adv config panel
                           {
                               cur_client.sendFromBot("[adc:] "+ADC.retNormStr (message));
                               new ADCConfig(cur_client,message);
                               
                           }
                           else if(message.toLowerCase().startsWith("!cfg") || message.toLowerCase().startsWith("+cfg"))//config settings
                           {
                               cur_client.sendFromBot("[config:] "+ADC.retNormStr (message));
                               new CommandParser(cur_client,message);
                               
                           }
                           else
                           {
                               cur_client.sendFromBot("[command:] "+ADC.retNormStr (message));
                                new CommandParser(cur_client,message);
                                
                           }
                       }
                       else
                       
                       
                       new Broadcast(Issued_Command);
                       //System.out.println (Issued_Command);
                   }
                   else if(Issued_Command.charAt (0)=='E') //echo direct msg
                   {
                       if(pmsid==null)
                       {
                           new STAError(cur_client,140,"MSG Can't PM to Nobody.");
                           return;
                       }
                       if(!thissid.equals (cur_client.SessionID))
                       {
                           new STAError(cur_client,140,"MSG PM not returning to self.");
                           return;
                       }
                       if(pmsid.equals ("DCBA"))
                       {
                           //talking to bot security
                           cur_client.sendToClient(Issued_Command);
                           // ok now lets check that the chat is a command....
                       if(cur_client.reg.isreg && message.charAt (0)=='!' || cur_client.reg.isreg && message.charAt (0)=='+') //ok.. command mode.
                       {
                           if(message.toLowerCase().startsWith("!adc") || message.toLowerCase().startsWith("+adc"))//adc adv config panel
                           {
                               cur_client.sendFromBot("[adc:] "+ADC.retNormStr (message));
                               new ADCConfig(cur_client,message);
                               
                           }
                           else if(message.toLowerCase().startsWith("!cfg") || message.toLowerCase().startsWith("+cfg"))//config settings
                           {
                               cur_client.sendFromBot("[config:] "+ADC.retNormStr (message));
                               new CommandParser(cur_client,message);
                               
                           }
                           else
                           {
                               cur_client.sendFromBot("[command:] "+ADC.retNormStr (message));
                                new CommandParser(cur_client,message);
                                
                           }
                       }
                           //cur_client.sendToClient ("EMSG DCBA "+cur_client.SessionID+" Hello ! PMDCBA");
                           return;
                       }
                       else
                       if(!pmsid.equals ("ABCD"))
                       {
                       ClientNod temp=ClientNod.FirstClient.NextClient;
                       while(temp!=null)
                       {
                       if(temp.cur_client.SessionID.equals(pmsid))
                           break;
                       temp=temp.NextClient;
                      }
                      if(temp==null)//talking to inexisting client
                      {
                            new STAError(cur_client,140,"MSG User not found."); //not kick, maybe the other client just left after he sent the msg;
                            return;
                       }
                       
                      temp.cur_client.sendToClient(Issued_Command);
                      
                      
                      }
                       else
                       {
                           //talking to bot
                           //must send to all ops...
                           
                           //cant broadcast coz must send each;s SID
                           ClientNod temp=ClientNod.FirstClient.NextClient;
                           while(temp!=null)
                           {
                               if(temp.cur_client.userok==1)
                                   if(temp.cur_client.reg.isreg && !temp.cur_client.equals (cur_client))
                               temp.cur_client.sendToClient("EMSG "+cur_client.SessionID+" "+temp.cur_client.SessionID+ " "+message + " PMABCD");
                               temp=temp.NextClient;
                           }
                           
                           
                       }
                      cur_client.sendToClient(Issued_Command);
                       
                       
                   }
                   else if(Issued_Command.charAt (0)=='D') //direct direct msg
                   {
                       if(pmsid==null)
                       {
                           new STAError(cur_client,140,"MSG Can't PM to Nobody.");
                           return;
                       }
                       if(!thissid.equals (cur_client.SessionID))
                       {
                           new STAError(cur_client,140,"MSG PM not returning to self.");
                           return;
                       }
                       if(pmsid.equals ("DCBA"))
                       {
                           //talking to bot security
                          // cur_client.sendToClient ("DMSG DCBA "+cur_client.SessionID+" Hello ! PMDCBA");
                           // ok now lets check that the chat is a command....
                       if(cur_client.reg.isreg && message.charAt (0)=='!' || cur_client.reg.isreg && message.charAt (0)=='+') //ok.. command mode.
                       {
                           cur_client.sendFromBot("[command:] "+ADC.retNormStr(message));
                           new CommandParser(cur_client,message);
                       }
                       }
                       else  
                       if(!pmsid.equals ("ABCD"))
                       {
                       ClientNod temp=ClientNod.FirstClient.NextClient;
                       while(temp!=null)
                       {
                       if(temp.cur_client.SessionID.equals(pmsid))
                           break;
                       temp=temp.NextClient;
                        }
                      if(temp==null)//talking to inexisting client
                      {
                            new STAError(cur_client,140,"MSG User not found."); //not kick, maybe the other client just left after he sent the msg;
                            return;
                       }
                      temp.cur_client.sendToClient(Issued_Command);
                       }
                       else
                       {
                           //talking to bot
                           //must send to all ops...
                           
                           ClientNod temp=ClientNod.FirstClient.NextClient;
                           while(temp!=null)
                           {
                               if(temp.cur_client.reg.isreg && !temp.cur_client.equals (cur_client))
                               temp.cur_client.sendToClient("DMSG "+cur_client.SessionID+" "+temp.cur_client.SessionID+ " "+message + " PMABCD");
                               temp=temp.NextClient;
                           }
                           
                           
                       }
                   }
                   
                   else
                   {
                       new STAError(cur_client,140,"MSG Invalid Context");
                       return;
                   }
    }
    
}
