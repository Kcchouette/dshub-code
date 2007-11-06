package dshub;
/*
 * Command.java
 *
 * Created on 06 martie 2007, 16:20
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


import java.util.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
/**
 * Provides a parsing for each ADC command received from client, and makes the states transitions
 * Updates all information and ensures stability.
 *
 *
 * @author Pietricica
 */

class CommandException extends Exception
{
    CommandException()
    {
        super();
    };
    CommandException(String bla)
    {
        super(bla);
    }
};

public class Command
{
    ClientHandler cur_client;
    String Issued_Command;
    String State;

    synchronized void completeLogIn()
    {
        // must check if its op or not and move accordingly
        if(!cur_client.reg.key) //increase HR count and put RG field to 1
        {
             cur_client.HR=String.valueOf(Integer.parseInt(cur_client.HR)+1);
             cur_client.RG="1";
        }
        else //increase HO count and put OP field to 1
        {
             cur_client.HO=String.valueOf(Integer.parseInt(cur_client.HO)+1);
             cur_client.OP="1";
        }
        
        cur_client.reg.LastLogin=System.currentTimeMillis();
        
          //ok now must send to cur_client the inf of all others
               
        
        
                 ClientNod tempy=ClientNod.FirstClient.NextClient;

                 while(tempy!=null)
                 {
                    // System.out.println (tempy.NI);
                     if(tempy.cur_client.userok==1 && !tempy.cur_client.equals (cur_client)) //if the user has some inf ... [ meaning he is ok]
                   cur_client.sendToClient(tempy.cur_client.getINF ()); 
                     tempy=tempy.NextClient;
                 }
                 //
                 cur_client.sendToClient ("BINF ABCD ID"+Main.Server.OpChatCid+" NI"+ADC.retADCStr(Vars.Opchat_name)
                 +" BO1 OP1 DE"+ADC.retNormStr(Vars.Opchat_desc));;
                 cur_client.sendToClient ("BINF DCBA ID"+Main.Server.SecurityCid+" NI"+ADC.retADCStr(Vars.bot_name)
                 +" BO1 OP1 DE"+ADC.retADCStr(Vars.bot_desc));;
                 // cur_client.sendFromBot(""+Main.Server.myPath.replaceAll (" ","\\ "));
                 cur_client.sendToClient(cur_client.getINF ());  //sending inf about itself too
                 
               //ok now must send INF to all clients
                 new Broadcast(cur_client.getINF (),cur_client.myNod);
                 cur_client.userok=1; //user is OK, logged in and cool.
                 cur_client.sendFromBot(ADC.MOTD);
                 //System.out.println("gay");
                 //cur_client.sendFromBot ("gay");
                 cur_client.sendFromBot(cur_client.reg.HideMe ? "You are currently hidden." : "");
                 
                 cur_client.LoggedAt=System.currentTimeMillis();
                 cur_client.State="NORMAL";
                 cur_client.sendFromBot( ADC.MOTD);
                 
                    
               
               
              
    }
    
    synchronized void  handleINF() throws CommandException, STAException
    {
        Issued_Command=Issued_Command.substring(4);
                    StringTokenizer tok=new StringTokenizer(Issued_Command);
                    
                    String cur_inf="BINF " + cur_client.SessionID;
                    
                    String thesid=tok.nextToken();
                    if(!thesid.equals(cur_client.SessionID))
                    {
                      new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                      return ;
                    }
                    
                    
                    if(cur_client.cur_inf!=null)
                    {
                    StringTokenizer inftok=new StringTokenizer(cur_client.cur_inf.substring(5));
                    
                    while(inftok.hasMoreTokens())
                    {
                        String y=inftok.nextToken();
                        if(Issued_Command.contains(y.substring(0,2)))
                        {
                            cur_client.cur_inf=cur_client.cur_inf.substring(0,cur_client.cur_inf.indexOf(y))+cur_client.cur_inf.substring(cur_client.cur_inf.indexOf(y)+y.length());
                            inftok=new StringTokenizer(cur_client.cur_inf);
                        }
                        
                    }
                    Issued_Command+=cur_client.cur_inf.substring(5);
                      tok=new StringTokenizer(Issued_Command);
                    tok.nextToken();
                    }
                    //System.out.println(Issued_Command);
                    while(tok.hasMoreElements())
                    {
                       
                        String aux=tok.nextToken();
                        
                        
                        // System.out.println(aux);
                        if(aux.startsWith("ID"))//meaning we have the ID thingy
                        {
                           
                            if(!State.equals ("PROTOCOL"))
                            {
                                new STAError(cur_client,140,"Can't change CID while connected.");
                                return;
                            }
                             cur_client.ID=aux.substring(2);
                            cur_inf=cur_inf+" ID"+cur_client.ID;
                            //System.out.println (cur_client.ID);
                        }
                        else if(aux.startsWith("NI"))
                        {
                     
                            
                            
                   if(!Vars.ValidateNick (aux.substring (2)))
                   {
                       new STAError(cur_client,222,"Nick not valid, please choose another");
                       return;
                   }
                   cur_client.NI=aux.substring(2);
                   
                            if(!State.equals ("PROTOCOL"))
                                if(cur_client.reg.isreg)
                                     cur_client.reg.LastNI=cur_client.NI;
                        
                            cur_inf=cur_inf+" NI"+cur_client.NI;
                        }
                        else if(aux.startsWith("PD"))//the PiD
                        {
                            
                           
                            if(!State.equals ("PROTOCOL"))
                            {
                                new STAError(cur_client,140,"Can't change PID while connected.");
                                return;
                            }
                           
                           
                            
                            cur_client.PD=aux.substring(2);
                        }
                        else if(aux.startsWith("I4"))
                        {
                            
                            cur_client.I4=aux.substring(2);
              if(aux.substring (2).equals("0.0.0.0")||aux.substring (2).equals ("localhost") )//only if active client
              {
               cur_client.I4=cur_client.RealIP;
              }
              
                  
              else if(!aux.substring (2).equals (cur_client.RealIP) && !aux.substring (2).equals ("") 
              && !cur_client.RealIP.equals ("127.0.0.1"))
              {
                   new STAError(cur_client,243,"Wrong IP address supplied.","I4");
                   return;
              }
                            cur_inf=cur_inf+" I4"+cur_client.I4;
                            
                        }
                        else if(aux.startsWith("I6"))
                        {
                            cur_client.I6=aux.substring(2);
                            cur_inf=cur_inf+" I6"+cur_client.I6;
                        }
                        else if(aux.startsWith("U4"))
                        {
                            cur_client.U4=aux.substring(2);
                            cur_inf=cur_inf+" U4"+cur_client.U4;
                        }
                        else if(aux.startsWith("U6"))
                        {
                            cur_client.U6=aux.substring(2);
                            cur_inf=cur_inf+" U6"+cur_client.U6;
                        }
                        else if(aux.startsWith("SS"))
                        {
                            cur_client.SS=aux.substring(2);
                            cur_inf=cur_inf+" SS"+cur_client.SS;
                        }
                        else if(aux.startsWith("SF"))
                        {
                            cur_client.SF=aux.substring(2);
                            cur_inf=cur_inf+" SF"+cur_client.SF;
                        }
                        else if(aux.startsWith("VE"))
                        {
                            cur_client.VE=aux.substring(2);
                            cur_inf=cur_inf+" VE"+cur_client.VE;
                        }
                        else if(aux.startsWith("US"))
                        {
                            cur_client.US=aux.substring(2);
                            cur_inf=cur_inf+" US"+cur_client.US;
                        }
                        else if(aux.startsWith("DS"))
                        {
                            cur_client.DS=aux.substring(2);
                            cur_inf=cur_inf+" DS"+cur_client.DS;
                        }
                        else if(aux.startsWith("SL"))
                        {
                            cur_client.SL=aux.substring(2);
                            cur_inf=cur_inf+" SL"+cur_client.SL;
                        }
                        else if(aux.startsWith("AS"))
                        {
                            cur_client.AS=aux.substring(2);
                            cur_inf=cur_inf+" AS"+cur_client.AS;
                        }
                        else if(aux.startsWith("AM"))
                        {
                            cur_client.AM=aux.substring(2);
                            cur_inf=cur_inf+" AM"+cur_client.AM;
                        }
                        else if(aux.startsWith("EM"))
                        {
                            cur_client.EM=aux.substring(2);
                            cur_inf=cur_inf+" EM"+cur_client.EM;
                        }
                        
                        else if(aux.startsWith("DE"))
                        {
                            cur_client.DE=aux.substring(2);
                            cur_inf=cur_inf+" DE"+cur_client.DE;
                        }
                        else if(aux.startsWith("HN"))
                        {
                            cur_client.HN=aux.substring(2);
                            
                            if(State.equals ("NORMAL"))
                            cur_inf=cur_inf+" HN"+cur_client.HN;
                        }
                        else if(aux.startsWith("HR"))
                        {
                            cur_client.HR=aux.substring(2);
                            cur_inf=cur_inf+" HR"+cur_client.HR;
                        }
                        else if(aux.startsWith("HO"))
                        {
                            cur_client.HO=aux.substring(2);
                            cur_inf=cur_inf+" HO"+cur_client.HO;
                        }
                        else if(aux.startsWith("TO"))
                        {
                            cur_client.TO=aux.substring(2);
                            cur_inf=cur_inf+" TO"+cur_client.TO;
                        }
                        else if(aux.startsWith("OP"))
                        {
                            //cur_client.OP=aux.substring(2);
                            //cur_inf=cur_inf+" OP"+cur_client.OP;
                            /** not allowed to set op like this*/
                            new STAError(cur_client,210,"Not allowed to have OP field.","ID");
                            return;
                            
                        }
                        else if(aux.startsWith("RG"))
                        {
                           // cur_client.RG=aux.substring(2);
                           // cur_inf=cur_inf+" RG"+cur_client.RG;
                            new STAError(cur_client,210,"Not allowed to have RG field.","ID");
                            return;
                        }
                        else if(aux.startsWith("AW"))
                        {
                            cur_client.AW=aux.substring(2);
                            cur_inf=cur_inf+" AW"+cur_client.AW;
                        }
                        else if(aux.startsWith("BO"))
                        {
                            if(cur_client.reg.key)
                            {
                            cur_client.BO=aux.substring(2);
                            cur_inf=cur_inf+" BO"+cur_client.BO;
                            }else
                            {
                             new STAError(cur_client,210,"Not allowed to have RG field.","ID");
                            return;
                            }
                        }
                        else if(aux.startsWith("HI"))
                        {
                            if(cur_client.reg.key)
                            {
                            cur_client.BO=aux.substring(2);
                            cur_inf=cur_inf+" BO"+cur_client.BO;
                            }
                            else
                            {
                            cur_client.HI=aux.substring(2);
                            cur_inf=cur_inf+" HI"+cur_client.HI;
                            }
                        }

                        else if(aux.startsWith("SU"))
                        {
                            cur_client.SU=aux.substring(2);
                            cur_inf=cur_inf+" SU"+cur_client.SU;
                        }
                        else 
                        {
                            new STAError(cur_client,240,"Protocol Error.");
                            return ;
                        }
                        
                        
                    }
                    if(State.equals ("PROTOCOL"))
                    {
                    if(cur_client.ID==null)
                    {
                        new STAError(cur_client,243,"Missing field","ID");
                        return;
                    }else if(cur_client.ID.equals (""))
                    {
                        new STAError(cur_client,243,"Missing field","ID");
                        return;
                    }
                    if(cur_client.PD==null)
                    {
                        new STAError(cur_client,243,"Missing field","PD");
                        return;
                    }else if(cur_client.PD.equals (""))
                    {
                        new STAError(cur_client,243,"Missing field","PD");
                        return;
                    }
                   
                    if(cur_client.NI==null) 
                    {
                        new STAError(cur_client,243,"Missing field","NI");
                        return;
                    }else if(cur_client.NI.equals (""))
                    {
                        new STAError(cur_client,243,"Missing field","NI");
                        return;
                    }
                    if(cur_client.HN==null)
                    {
                        new STAError(cur_client,243,"Missing field","HN");
                        return;
                    }else if(cur_client.HN.equals (""))
                    {
                        new STAError(cur_client,243,"Missing field","HN");
                        return;
                    }
                   cur_client.reg=reg_config.getnod (cur_client.ID);
                   if(cur_client.reg==null)
                       cur_client.reg=new nod();
                   //cur_client.reg.CH=cur_client;
                    if(!cur_client.reg.isreg)
                            cur_client.HN=String.valueOf(Integer.parseInt(cur_client.HN)+1);
                    }
                    
                    
                    /* check if user is banned first*/
                    cur_client.myban=BanList.getban (3,cur_client.ID);
                    if(cur_client.myban==null)
                    {
                       
                    cur_client.myban=BanList.getban (2,(cur_client.RealIP));
                    //System.out.println(cur_client.mySession.getRemoteAddress().toString());
                    }
                    if(cur_client.myban==null)
                    {
                    cur_client.myban=BanList.getban (1,cur_client.NI.toLowerCase ());
                    
                    }
                    if(cur_client.myban!=null) //banned
                    {
                        if(cur_client.myban.time==-1)
                        {
                            String msg="Hello there. You are permanently banned.\nOp who banned you: "+cur_client.myban.banop+
                                "\nReason: "+cur_client.myban.banreason+"\n"+Vars.Msg_Banned;
                            //System.out.println(msg);
                        new STAError(cur_client,231,msg );
                        
                        return;
                        }
                        long TL=System.currentTimeMillis ()-cur_client.myban.timeofban-cur_client.myban.time;
                        TL=-TL;
                        if(TL>0)
                        {
                            String msg="Hello there. You are temporary banned.\nOp who banned you: "+cur_client.myban.banop+
                                "\nReason: "+cur_client.myban.banreason+"\nThere are still "+
                                    Long.toString (TL/1000)+" seconds remaining.\n"+Vars.Msg_Banned+" TL"+Long.toString (TL/1000);
                            //System.out.println(msg);
                        new STAError(cur_client,232,msg );
                        
                        return;
                        }
                    }
                    //else System.out.println("no nick ban");
                ClientNod temp=ClientNod.FirstClient.NextClient;
                int i=0;
               while(temp!=null)
               {
                   if(temp.cur_client.userok!=0 && !temp.cur_client.equals (cur_client))
                   {
                   if(temp.cur_client.NI.toLowerCase().equals(cur_client.NI.toLowerCase()))
                   {
                       new STAError(cur_client,222,"Nick taken, please choose another");
                       return;
                   }
                   
                   if(temp.cur_client.ID.equals(cur_client.ID))
                   {
                       new STAError(cur_client,221,"CID taken. Please go to Settings and pick new PID.");
                       return;
                   }
                   i++;
                   }
                   temp=temp.NextClient;
                   
               }
                
                if(reg_config.nickReserved(cur_client.NI,cur_client.ID))
                {
                    int x=(State.equals("PROTOCOL"))?240:140;
                    new STAError(cur_client,x,"Nick reserved. Please choose another.");
                    return;
                }
                    // now must check if hub is full...
                    if(State.equals ("PROTOCOL")) //otherwise is already connected, no point in checking this
                    {
                        /** must check the hideme var*/
                        if(cur_client.reg.HideMe)
                        {
                            cur_inf=cur_inf+" HI1";
                            cur_client.HI="1";
                        }
                        
                
                   if(Vars.max_users<=i && !cur_client.reg.overridefull)
                   {
                    new STAError(cur_client,211,"Hello there. Hub is full, there are "+String.valueOf (i)+" users online.\n"+Vars.Msg_Full );
                    return;
                   }
                  
                    
                 }
                    if(cur_client.SS==null && Vars.min_share!=0)
                        new STAError(cur_client,246,"Share too small, "+Vars.min_share+" MiB required.","SS");
                    if(cur_client.SL==null && Vars.min_sl!=0)
                        new STAError(cur_client,246,"Too few slots, open up more.","SL");
                //TODO : add without tag allow ?
                    try
                    {
                //checking all:
                    if(cur_client.NI.length ()>Vars.max_ni)
                    {new STAError(cur_client,221,"Nick too large");return;}
                    if(cur_client.NI.length ()<Vars.min_ni)
                    {new STAError(cur_client,221,"Nick too small");return;}
                    if(cur_client.DE!=null)
                    if(cur_client.DE.length ()>Vars.max_de)
                    {new STAError(cur_client,246,"Description too large","DE");return;}
                    if(cur_client.EM!=null)
                    if(cur_client.EM.length ()>Vars.max_em)
                    {new STAError(cur_client,246,"E-mail too large","EM");return;}
                    if(cur_client.SS!=null)
                    if(Long.parseLong(cur_client.SS)>1024*Vars.max_share*1024)
                    {new STAError(cur_client,246,"Share too large","SS");return;}
                    if(cur_client.SS!=null)
                    if(Long.parseLong(cur_client.SS)<1024*Vars.min_share*1024)
                    {new STAError(cur_client,246,"Share too small "+Vars.min_share+" MiB required.","SS");return;}
                    if(cur_client.SL!=null)
                    if(Integer.parseInt (cur_client.SL)<Vars.min_sl)
                    {new STAError(cur_client,246,"Too few slots, open up more.","SL");return;}
                    if(cur_client.SL!=null)
                    if(Integer.parseInt (cur_client.SL)>Vars.max_sl)
                    {new STAError(cur_client,246,"Too many slots, close some.","SL");return;}
                    
                    if(Integer.parseInt (cur_client.HN)>Vars.max_hubs_user)
                    {new STAError(cur_client,246,"Too many hubs open, close some.","HN");return;}
                    if(cur_client.HO!=null)
                    if(Integer.parseInt (cur_client.HO)>Vars.max_hubs_op)
                    {new STAError(cur_client,246,"You are operator on too many hubs. Sorry.","HO");return;}
                    if(cur_client.HR!=null)
                    if(Integer.parseInt (cur_client.HR)>Vars.max_hubs_reg)
                    {new STAError(cur_client,246,"You are regged on too many hubs. Sorry.","HR");return;}
                    }
                    catch ( NumberFormatException nfe)
                    {
                       new STAError(cur_client,240,"Your client sent weird info, Protocol Error.");
                       return;
                    }
               
               if(cur_client.ID.equals (Main.Server.OpChatCid))
               {
                    new STAError(cur_client,221,"CID taken. Please go to Settings and pick new PID.");
                       return;
               }
                if(cur_client.ID.equals (Main.Server.SecurityCid))
               {
                    new STAError(cur_client,221,"CID taken. Please go to Settings and pick new PID.");
                       return;
               }
               if(cur_client.NI.equalsIgnoreCase (Vars.Opchat_name))
               {
                      new STAError(cur_client,222,"Nick taken, please choose another");
                       return;
               }
                if(cur_client.NI.equalsIgnoreCase (Vars.bot_name))
               {
                      new STAError(cur_client,222,"Nick taken, please choose another");
                       return;
               }
               
              if(State.equals ("PROTOCOL"))
                  
              try
               {
                               Tiger myTiger = new Tiger();
						
		              myTiger.engineReset();
		                myTiger.init();	
                                byte [] bytepid=Base32.decode (cur_client.PD);
                                
				
		         myTiger.engineUpdate(bytepid,0,bytepid.length);
				
		            byte[] finalTiger = myTiger.engineDigest();
		          if(!Base32.encode (finalTiger).equals (cur_client.ID))
                          {
                             new STAError(cur_client,240,"Invalid CID check.");
                             return;
                          }
                          if(cur_client.PD.length ()!=39)
                                    throw new IllegalArgumentException();
                           
                              

                 }
              
             
              catch(IllegalArgumentException iae)
              {
                  new STAError(cur_client,227,"Invalid PID supplied.");
                  return;
              }
                catch (Exception e)
                 {
                                  System.out.println (e);
                                  return;
                 }
              
               
              
               
               
               if(cur_client.SU!=null) if(!(cur_client.SU.equals("")))
               if(cur_client.SU.contains ("TCP4"))
               {
                   cur_client.ACTIVE=1;
               }
               else cur_client.ACTIVE=0;
               /*------------ok now must see if the pid is registered...---------------*/
              
               if(State.equals ("PROTOCOL"))
               {
                   if(cur_client.reg.isreg)
                   {
                       if(cur_client.reg.Password.equals (""))//no pass defined ( yet)
                       {
                           cur_client.sendToClient("ISTA 000 Registered,\\sno\\spassword\\srequired.\\sThough,\\sits\\srecomandable\\sto\\sset\\sone.");
                           cur_client.sendToClient("ISTA 000 Authenticated.");
                        
                         
                         cur_client.reg.LastNI=cur_client.NI;
                         cur_client.reg.LastIP=cur_client.RealIP;
                         completeLogIn();
                         return;
                           
                       }
                       cur_client.sendToClient("ISTA 000 Registered,\\stype\\syour\\spassword.");
                       /* creates some hash for the GPA random data*/
                       Tiger myTiger = new Tiger();
						
                    myTiger.engineReset();
                myTiger.init();	
                byte [] T=Long.toString(System.currentTimeMillis()).getBytes(); //taken from cur time
             myTiger.engineUpdate(T,0,T.length);
				
                 byte[] finalTiger = myTiger.engineDigest();
                 cur_client.RandomData =Base32.encode (finalTiger);
                       cur_client.sendToClient ("IGPA "+cur_client.RandomData);
                       cur_client.State="VERIFY";
                       return;
                   }
                   else
                   {
                       nod k;
                       k=reg_config.isNickRegFl(cur_client.NI);
                       if(k!=null)
                       {
                           cur_client.sendToClient("ISTA 000 Nick\\sRegistered\\s(flyable\\saccount).\\sPlease\\sprovide\\spassword.");
                           
                         /* creates some hash for the GPA random data*/
                       Tiger myTiger = new Tiger();
						
                    myTiger.engineReset();
                myTiger.init();	
                byte [] T=Long.toString(System.currentTimeMillis()).getBytes(); //taken from cur time
             myTiger.engineUpdate(T,0,T.length);
				
                 byte[] finalTiger = myTiger.engineDigest();
                 cur_client.RandomData =Base32.encode (finalTiger);
                       cur_client.sendToClient ("IGPA "+cur_client.RandomData);
                       cur_client.reg=k;
                       cur_client.State="VERIFY";
                       return;
                       }
                       else if(Vars.reg_only==1)
                   {
                       new STAError(cur_client,226,"Registered only hub.");
                       return;
                   }
                   }
                    
               }
               
               
               //ok now must send to cur_client client the inf of all others
               if(State.equals ("PROTOCOL"))
               {
                 ClientNod tempy=ClientNod.FirstClient.NextClient;

                 while(tempy!=null)
                 {
                    // System.out.println (tempy.NI);
                     if(tempy.cur_client.userok==1 && !tempy.cur_client.equals (cur_client)) //if the user has some inf ... [ meaning he is ok]
                   cur_client.sendToClient(tempy.cur_client.getINF ()); 
                     tempy=tempy.NextClient;
                 }
                
                 
                cur_client.sendToClient(cur_client.getINF ());  //sending inf about itself too
               //ok now must send INF to all clients
                 new Broadcast(cur_client.getINF (),cur_client.myNod);
                 cur_client.sendToClient ("BINF DCBA ID"+Main.Server.SecurityCid+" NI"+ADC.retADCStr(Vars.bot_name)+" BO1 OP1 DE"+ADC.retADCStr(Vars.bot_desc));                 
                 cur_client.userok=1; //user is OK, logged in and cool.
               //  cur_client.sendFromBot(""+Main.Server.myPath.replaceAll (" ","\\ "));
                 //ok now that we passed to normal state and user is ok, check if it has UCMD, and if so, send a test command
                 if(cur_client.ucmd==1)
                 {
                     //ok, he is ucmd ok, so
                     cur_client.sendToClient ("ICMD Test CT1 TTTest");
                 }
                 cur_client.State="NORMAL";
                 cur_client.sendFromBot( ADC.MOTD);
                 return;
               }
               
               if(State.equals ("NORMAL"))
               {
                  if(System.currentTimeMillis()-cur_client.LastINF>(1000*120L))
                  {
                    new Broadcast(cur_inf);
                    cur_client.LastINF=System.currentTimeMillis();
                  }
                  else
                      cur_client.cur_inf=cur_inf;
                      
               }
               
    }
    
    /** Main command handling function, ADC specific.*/
    void HandleIssuedCommand() throws CommandException,STAException
    {
        
        
        
                /*******************************INF COMMAND *****************************************/
       
                if(Issued_Command.charAt(1)=='I' && Issued_Command.charAt (2)=='N' && Issued_Command.charAt (3)=='F')
                {
                     if(State.equals ("IDENTIFY") || State.equals ("VERIFY"))
                     {
                         new STAError(cur_client,240,"INF Invalid State.");
                         return;
                     }
                    if(Issued_Command.charAt (0)!='B')
                    {
                        new STAError(cur_client,140,"INF Invalid Context.");
                        return;
                    }
                         switch(Issued_Command.charAt(0))
     {
         case 'B':
             if(Vars.BINF!=1)
             { new STAError(cur_client,140,"INF Invalid Context B");
                       return;}break;
         case 'E':
              if(Vars.EINF!=1)
              {  new STAError(cur_client,140,"INF Invalid Context E");
                       return;}break;
         case 'D':
          if(Vars.DINF!=1)
          {   new STAError(cur_client,140,"INF Invalid Context D");
                       return;
         }break;
         case 'F':
          if(Vars.FINF!=1)
           {   new STAError(cur_client,140,"INF Invalid Context F");
                       return;}break;
         case 'H':
              if(Vars.HINF!=1)
              {   new STAError(cur_client,140,"INF Invalid Context H");
                       return;}
                 
     }
                         
                         
               handleINF();
               
              }
            
                /************************PAS COMMAND****************************/
        if(Issued_Command.charAt (1)=='P'&& Issued_Command.charAt (2)=='A' && Issued_Command.charAt (3)=='S')
        {
                    
                    switch(Issued_Command.charAt(0))
     {
         case 'B':
             if(Vars.BPAS!=1)
             { new STAError(cur_client,140,"PAS Invalid Context B");
                       return;}break;
         case 'E':
              if(Vars.EPAS!=1)
              {  new STAError(cur_client,140,"PAS Invalid Context E");
                       return;}break;
         case 'D':
          if(Vars.DPAS!=1)
          {   new STAError(cur_client,140,"PAS Invalid Context D");
                       return;
         }break;
         case 'F':
          if(Vars.FPAS!=1)
           {   new STAError(cur_client,140,"PAS Invalid Context F");
                       return;}break;
         case 'H':
              if(Vars.HPAS!=1)
              {   new STAError(cur_client,140,"PAS Invalid Context H");
                       return;}
                 
     }
                    nod k;
                    
                    if((k=reg_config.isNickRegFl(cur_client.NI))!=null)
                        cur_client.reg=k;
                    if(!cur_client.reg.isreg )
                    {
                        new STAError(cur_client,140,"Not registered.");
                        return;
                    }
                   if(Issued_Command.charAt (0)!='H')
                    {
                        if(State.equals ("NORMAL"))
                         throw new CommandException("FAIL state:PROTOCOL reason:NOT BASE CLIENT");
                        else 
                        {
                            new STAError(cur_client,140,"PAS Invalid Context.");
                            return;
                        }
                    }     
                    
                    String realpas="";
                try
               {
                               Tiger myTiger = new Tiger();
						
		              myTiger.engineReset();
		                myTiger.init();	
                                byte [] bytecid=Base32.decode (cur_client.ID);
                                byte [] pas=cur_client.reg.Password.getBytes ();
                                byte []random=Base32.decode (cur_client.RandomData);
                                
                                byte [] result =new byte[bytecid.length+pas.length+random.length];
				for(int i=0;i<bytecid.length;i++)
                                    result[i]=bytecid[i];
                                for(int i=bytecid.length;i<pas.length+bytecid.length;i++)
                                    result[i]=pas[i-bytecid.length];
                                for(int i=pas.length+bytecid.length;i<random.length+pas.length+bytecid.length;i++)
                                    result[i]=random[i-pas.length-bytecid.length];
                                
		         myTiger.engineUpdate(result,0,result.length);
				
		            byte[] finalTiger = myTiger.engineDigest();
		          realpas=Base32.encode (finalTiger);

                 }
              
             
              catch(IllegalArgumentException iae)
              {
                  System.out.println (iae);
              }
                catch (Exception e)
                 {
                                  System.out.println (e);
                 }
              if(realpas.equals(Issued_Command.substring (5)))
              {
                        cur_client.sendToClient("IMSG Authenticated.");
                        
                         cur_client.sendFromBot(ADC.MOTD);
                         
                         //System.out.println ("pwla");
                         cur_client.reg.LastNI=cur_client.NI;
                        // cur_client.reg.LastNI=cur_client.NI;
                         cur_client.reg.LastIP=cur_client.RealIP;
                         
                         if(!cur_client.ID.equals(cur_client.reg.CID))
                        cur_client.sendToClient("IMSG Account\\sCID\\supdated\\sto\\s"+cur_client.ID);
                         cur_client.reg.CID=cur_client.ID;
              }
              else
              {
                new STAError(cur_client,223,"Invalid Password."); 
                return;
              }

                   //System.out.println (Issued_Command);
               completeLogIn();
        }
        
        /**********************SUP COMMAND******************************/
         if(Issued_Command.charAt(1)=='S' && Issued_Command.charAt (2)=='U' && Issued_Command.charAt (3)=='P')
         {
                   new SUP(cur_client, State, Issued_Command);
                    
         }
                    
     
    /********************************MSG COMMAND************************************/
if(Issued_Command.charAt(1)=='M' && Issued_Command.charAt (2)=='S' && Issued_Command.charAt (3)=='G')
 {
     new MSG(cur_client, State, Issued_Command);
}
         
                
if(Issued_Command.charAt(1)=='S' && Issued_Command.charAt (2)=='C' && Issued_Command.charAt (3)=='H')
{
    new SCH(cur_client,Issued_Command,State);
}
if(Issued_Command.charAt(1)=='S' && Issued_Command.charAt (2)=='T' && Issued_Command.charAt (3)=='A')
{
    new STA(cur_client,Issued_Command,State);
}
if(Issued_Command.substring(1).startsWith("RES ")) //direct search result, only active to passive must send this
{
    new RES(cur_client,State,Issued_Command);
}
else if(Issued_Command.substring(1).startsWith("CTM ")) //direct connect to me
{
    new CTM(cur_client,State, Issued_Command);
}
else if(Issued_Command.substring(1).startsWith("RCM ")) //reverse connect to me
{
    new RCM(cur_client, State, Issued_Command);
}
        
            
        
    
    }
    
    /** Creates a new instance of Command with following params
     *CH of type ClientHandler identifies tha client to handle
     *Issued_command of String type actually identifies the given command
     *state also of type String Identifies tha State in which tha connection is,
     *meaning [ accordingly to arne's draft]:
     *PROTOCOL (feature support discovery), IDENTIFY (user identification, static checks),
     * VERIFY (password check), NORMAL (normal operation) and DATA (for binary transfers). 
     *Calling function should send one of this params, that is calling function 
     *request... Command class does not check params.
     *Function throws CommandException if smth is wrong.
     */
    public Command(ClientHandler CH, String Issued_command) throws ClientFailedException, STAException,CommandException
    {
        cur_client=CH;
     // System.out.printf("["+cur_client.NI+"]:%s\n",Issued_command);
        
       if(Issued_command==null)
       { 
             //this means client is disconnected
           
           // ClientHandler tempy=cur_client.FirstClient.NextClient;
           // ClientHandler tempyprev=cur_client.FirstClient;
           
            if(ClientNod.FirstClient.NextClient!=null && cur_client.userok==1)
            {
            new Broadcast("IQUI "+cur_client.SessionID);
            cur_client.reg.TimeOnline+=System.currentTimeMillis()-cur_client.LoggedAt;
                  
            }
           cur_client.myNod.killMe();
            
            //cur_client.ClientSock.close();
            cur_client=null;
            
            
           
             throw new ClientFailedException("Client Disconnected.\n");
          
       }
            
       //System.out.printf("[Received]:%s\n",Issued_command);
       else if(Issued_command.equals(""))
       {
            //System.out.println("("+cur_client.NI+")"+System.currentTimeMillis ()/1000);
            return;
       }
            
       
    
        Issued_Command=Issued_command;
        State=cur_client.State;
        HandleIssuedCommand();
    }
    
}
