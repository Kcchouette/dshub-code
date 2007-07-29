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
 *
 * @author Administrator
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
        
        
        
          //ok now must send to cur_client the inf of all others
               
        
        
                 ClientHandler tempy=cur_client.FirstClient.NextClient;

                 while(tempy!=null)
                 {
                    // System.out.println (tempy.NI);
                     if(tempy.userok==1 && !tempy.equals (cur_client)) //if the user has some inf ... [ meaning he is ok]
                   cur_client.sendToClient(tempy.getINF ()); 
                     tempy=tempy.NextClient;
                 }
                 //
                 cur_client.sendToClient ("BINF ABCD ID"+Main.Server.OpChatCid+" NI"+Vars.Opchat_name+" BO1 OP1 DE"+Vars.Opchat_desc);;
                 cur_client.sendToClient ("BINF DCBA ID"+Main.Server.SecurityCid+" NI"+Vars.bot_name+" BO1 OP1 DE"+Vars.bot_desc);;
                 // cur_client.sendFromBot(""+Main.Server.myPath.replaceAll (" ","\\ "));
                 cur_client.sendToClient(cur_client.getINF ());  //sending inf about itself too
                 
               //ok now must send INF to all clients
                 new Broadcast(cur_client.getINF (),cur_client);
                 cur_client.userok=1; //user is OK, logged in and cool.
                 cur_client.sendFromBot(ADC.MOTD);
                 //System.out.println("gay");
                 //cur_client.sendFromBot ("gay");
                 cur_client.sendFromBot(cur_client.reg.HideMe ? "You are currently hidden." : "");
                 
                    
               
               
              
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
                     
                            
                            if(!State.equals ("PROTOCOL"))
                                if(cur_client.reg.isreg)
                            cur_client.reg.LastNI=cur_client.NI;
                   if(!Vars.ValidateNick (aux.substring (2)))
                   {
                       new STAError(cur_client,222,"Nick not valid, please choose another");
                       return;
                   }
                             cur_client.NI=aux.substring(2);
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
                            
                            
              if(aux.substring (2).equals("0.0.0.0")||aux.substring (2).equals ("localhost") )//only if active client
              {
               cur_client.I4=cur_client.ClientSock.getInetAddress().getHostAddress();
              }
              
                  
              else if(!aux.substring (2).equals (cur_client.ClientSock.getInetAddress().getHostAddress()) && !aux.substring (2).equals ("") 
              && !cur_client.ClientSock.getInetAddress().getHostAddress().equals ("127.0.0.1"))
              {
                   new STAError(cur_client,243,"Wrong IP address supplied.","I4");
                   return;
              }
                            cur_inf=cur_inf+" I4"+cur_client.I4;
                            cur_client.I4=aux.substring(2);
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
                       
                    cur_client.myban=BanList.getban (2,cur_client.ClientSock.getInetAddress().getHostAddress());
                    
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
                    // now must check if hub is full...
                    if(State.equals ("PROTOCOL")) //otherwise is already connected, no point in checking this
                    {
                        /** must check the hideme var*/
                        if(cur_client.reg.HideMe)
                        {
                            cur_inf=cur_inf+" HI1";
                            cur_client.HI="1";
                        }
                        
                 int i=0;
                ClientHandler temp0=ClientHandler.FirstClient;
                while(temp0!=null)
                {
                    if(temp0.userok==1)
                    i++;
                    temp0=temp0.NextClient;
                }
                   if(Vars.max_users<=i && Vars.ops_override_full!=1)
                   {
                    new STAError(cur_client,211,"Hello there. Hub is full, there are "+String.valueOf (i)+" users online.\n"+Vars.Msg_Full );
                    return;
                   }
                   if(Vars.max_users<=i && Vars.ops_override_full==1 && !cur_client.reg.key)
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
               ClientHandler temp=cur_client.FirstClient.NextClient;
               while(temp!=null)
               {
                   if(temp.userok!=0 && !temp.equals (cur_client))
                   {
                   if(temp.NI.toLowerCase().equals(cur_client.NI.toLowerCase()))
                   {
                       new STAError(cur_client,222,"Nick taken, please choose another");
                       return;
                   }
                   
                   if(temp.ID.equals(cur_client.ID))
                   {
                       new STAError(cur_client,221,"CID taken. Please go to Settings and pick new PID.");
                       return;
                   }
                   }
                   temp=temp.NextClient;
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
                           cur_client.sendFromBot("Registered, no password required. Though, its recomandable to set one.");
                           cur_client.sendFromBot("Authenticated.");
                        
                         
                         cur_client.reg.LastNI=cur_client.NI;
                         cur_client.reg.LastIP=cur_client.ClientSock.getInetAddress ().getHostAddress ();
                         completeLogIn();
                         return;
                           
                       }
                       cur_client.sendFromBot("Registered, type your password.");
                       /* creates some hash for the GPA random data*/
                       Tiger myTiger = new Tiger();
						
                    myTiger.engineReset();
                myTiger.init();	
                byte [] T=Long.toString(System.currentTimeMillis()).getBytes(); //taken from cur time
             myTiger.engineUpdate(T,0,T.length);
				
                 byte[] finalTiger = myTiger.engineDigest();
                 cur_client.RandomData =Base32.encode (finalTiger);
                       cur_client.sendToClient ("IGPA "+cur_client.RandomData);
                       return;
                   }
                   else if(Vars.reg_only==1)
                   {
                       new STAError(cur_client,226,"Registered only hub.");
                       return;
                   }
               }
               
               
               //ok now must send to cur_client client the inf of all others
               if(State.equals ("PROTOCOL"))
               {
                 ClientHandler tempy=cur_client.FirstClient.NextClient;

                 while(tempy!=null)
                 {
                    // System.out.println (tempy.NI);
                     if(tempy.userok==1 && !tempy.equals (cur_client)) //if the user has some inf ... [ meaning he is ok]
                   cur_client.sendToClient(tempy.getINF ()); 
                     tempy=tempy.NextClient;
                 }
                
                 
                cur_client.sendToClient(cur_client.getINF ());  //sending inf about itself too
               //ok now must send INF to all clients
                 new Broadcast(cur_client.getINF (),cur_client);
                 cur_client.sendToClient ("BINF DCBA ID"+Main.Server.SecurityCid+" NI"+Vars.bot_name+" BO1 OP1 DE"+Vars.bot_desc);
                 cur_client.userok=1; //user is OK, logged in and cool.
               //  cur_client.sendFromBot(""+Main.Server.myPath.replaceAll (" ","\\ "));
                 //ok now that we passed to normal state and user is ok, check if it has UCMD, and if so, send a test command
                 if(cur_client.ucmd==1)
                 {
                     //ok, he is ucmd ok, so
                     cur_client.sendToClient ("ICMD Test CT1 TTTest");
                 }
               }
               
               if(State.equals ("NORMAL"))
               {
                  
                   new Broadcast(cur_inf);
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
                    
               handleINF();
               
              }
            
                /************************PAS COMMAND****************************/
        if(Issued_Command.charAt (1)=='P'&& Issued_Command.charAt (2)=='A' && Issued_Command.charAt (3)=='S')
        {
                    if(reg_config.isReg (cur_client.ID)==0)
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
                        cur_client.sendFromBot("Authenticated.");
                        
                         cur_client.sendFromBot(ADC.MOTD);
                         if(cur_client.reg.HideMe)
                            cur_client.sendFromBot("You are currently hidden.");
                         //System.out.println ("pwla");
                         cur_client.reg.LastNI=cur_client.NI;
                        
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
                    if(Issued_Command.charAt (0)!='H')
                    {
                        if(State.equals ("PROTOCOL"))
                         throw new CommandException("FAIL state:PROTOCOL reason:NOT BASE CLIENT");
                        else 
                        {
                            new STAError(cur_client,140,"SUP Invalid Context.");
                            return;
                        }
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
                            if(aux.startsWith ("UCM") && aux.length()==4)
                                cur_client.ucmd=1;
                        }
                        else if(aux.startsWith ("RM"))
                        {
                            aux=aux.substring (2);
                            if(aux.startsWith ("UCM") && aux.length()==4)
                                cur_client.ucmd=0;
                        }
                    }
                    if(cur_client.base==0)
                      if(State.equals("PROTOCOL"))
                           throw new CommandException("FAIL state:PROTOCOL reason:NOT BASE CLIENT");  
                      else if (State.equals ("NORMAL"))
                      {
                          new STAError(cur_client,240,"You removed BASE features therefore you can't stay\\on hub anymore.");
                          return;
                      }
                    
         }
                    
     
    /********************************MSG COMMAND************************************/
if(Issued_Command.charAt(1)=='M' && Issued_Command.charAt (2)=='S' && Issued_Command.charAt (3)=='G')
 {
     if(State.equals ("IDENTIFY") || State.equals ("VERIFY") || State.equals ("PROTOCOL"))
        new STAError(cur_client,240,"MSG Invalid State.");
      
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
                       if(!(message.charAt (0)=='!' && cur_client.reg.key) && !(message.charAt (0)=='+' && cur_client.reg.key)
                       && !(cur_client.reg.key && Vars.ops_override_spam==1))
                       {
                       new STAError(cur_client,140,"Message exceeds maximum lenght.");
                       return;
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
                           if(cur_client.reg.key)
                               if(Vars.ops_override_spam!=1)
                               {
                       new STAError(cur_client,000,"Chatting Too Fast. Minimum chat interval "+String.valueOf (Vars.chat_interval)+" .You made "+String.valueOf (now-cur_client.LastChatMsg)+".");
                           return;
                               }
                           if(!cur_client.reg.key)
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
                           cur_client.sendFromBot("[command:] "+CommandParser.retNormStr (message));
                           new CommandParser(cur_client,message);
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
                           cur_client.sendFromBot("[command:] "+CommandParser.retNormStr(message));
                           new CommandParser(cur_client,message);
                       }
                           //cur_client.sendToClient ("EMSG DCBA "+cur_client.SessionID+" Hello ! PMDCBA");
                           return;
                       }
                       else
                       if(!pmsid.equals ("ABCD"))
                       {
                       ClientHandler temp=cur_client.FirstClient.NextClient;
                       while(temp!=null)
                       {
                       if(temp.SessionID.equals(pmsid))
                           break;
                       temp=temp.NextClient;
                      }
                      if(temp==null)//talking to inexisting client
                      {
                            new STAError(cur_client,140,"MSG User not found."); //not kick, maybe the other client just left after he sent the msg;
                            return;
                       }
                       
                      temp.sendToClient(Issued_Command);
                      
                      
                      }
                       else
                       {
                           //talking to bot
                           //must send to all ops...
                           
                           //cant broadcast coz must send each;s SID
                           ClientHandler temp=cur_client.FirstClient.NextClient;
                           while(temp!=null)
                           {
                               if(temp.userok==1)
                                   if(temp.reg.isreg && !temp.equals (cur_client))
                               temp.sendToClient("EMSG "+cur_client.SessionID+" "+temp.SessionID+ " "+message + " PMABCD");
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
                           cur_client.sendFromBot("[command:] "+CommandParser.retNormStr(message));
                           new CommandParser(cur_client,message);
                       }
                       }
                       else  
                       if(!pmsid.equals ("ABCD"))
                       {
                       ClientHandler temp=cur_client.FirstClient.NextClient;
                       while(temp!=null)
                       {
                       if(temp.SessionID.equals(pmsid))
                           break;
                       temp=temp.NextClient;
                        }
                      if(temp==null)//talking to inexisting client
                      {
                            new STAError(cur_client,140,"MSG User not found."); //not kick, maybe the other client just left after he sent the msg;
                            return;
                       }
                      temp.sendToClient(Issued_Command);
                       }
                       else
                       {
                           //talking to bot
                           //must send to all ops...
                           
                           ClientHandler temp=cur_client.FirstClient.NextClient;
                           while(temp!=null)
                           {
                               if(temp.reg.isreg && !temp.equals (cur_client))
                               temp.sendToClient("DMSG "+cur_client.SessionID+" "+temp.SessionID+ " "+message + " PMABCD");
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
       /******************************SCH COMMAND****************************/   
                
if(Issued_Command.charAt(1)=='S' && Issued_Command.charAt (2)=='C' && Issued_Command.charAt (3)=='H')
 {
     if(State.equals ("IDENTIFY") || State.equals ("VERIFY") || State.equals ("PROTOCOL"))
     {
        new STAError(cur_client,240,"SCH Invalid State.");
      return ;
     }
               
     
     /** This is my new idea, the logarythmic search spam abuse. 
      * This way, the hub is being kept safe, and users are not frustrated no more of bad and useless regular searches.
      * First, there are 2 types of searches, the automagic searches that client auto sends
      * and 2nd, the search by name that the user tries.
      * By logarithmic, im trying to keep a log base that would increase with every search,
      * and if no searches, is being reset.
      */
      boolean automagic = true;
      String Key="";
                    StringTokenizer tok=new StringTokenizer(Issued_Command);
                    String aux=tok.nextToken ();
                    String TOken="";
                    int activeonly=0;
                    int len=0;
                    if(!tok.nextToken().equals(cur_client.SessionID))
                    {
                      new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                      return ;
                    }
                    
                    while(tok.hasMoreTokens ())
                    {
                        aux=tok.nextToken ();
                        if(aux.startsWith ("+") || aux.startsWith("-"))
                        
                            if(Issued_Command.charAt (0)!='F')
                            {
                                new STAError(cur_client,140,"SCH Must Be Feature Broadcast To send to\\Featured clients.");
                                 return;
                            }
                            else if(aux.equals ("+TCP4"))
                              activeonly=1;
                            else
                            {
                                new STAError(cur_client,140,"SCH Feature Not Supported.");
                                return;
                            } 
                        else if(aux.startsWith ("AN") || aux.startsWith ("EX") || aux.startsWith ("NO"))
                        {
                            len+=aux.length ()-2;
                            automagic=false; //its not automagic search
                            Key+=aux.substring (2);
                        }
                        else if(aux.startsWith ("TO"))
                        {
                            TOken=aux.substring (2);
                        }
                    }
                    if(len>Vars.max_sch_chars)
                    {
                        new STAError(cur_client,140,"Search exceeds maximum length.");
                        return;
                    }
                    if(len<Vars.min_sch_chars && len!=0)
                    {
                        new STAError(cur_client,140,"Search too short.");
                        return;
                    }
                    if(automagic==false)
                    {
                    if(System.currentTimeMillis ()-cur_client.Lastsearch>Vars.search_spam_reset*1000)
                        cur_client.search_step=0;
                    else if(cur_client.search_step<Vars.search_steps)
                    {
                        double x=1;
                        for(int i=0;i<cur_client.search_step;i++)
                            x*=((double)Vars.search_log_base)/1000;
                        x*=1000;
                        long xx=(long)x;
                        
                        //System.out.println(xx+ "ok");
                        if(System.currentTimeMillis ()-cur_client.Lastsearch<xx)
                        {
                            //cur_client.sendToClient (Issued_Command);
                            String [] Messages=Vars.Msg_Search_Spam.split ("\\\n");
                            for(int j=0;j<Messages.length;j++)
                              cur_client.sendToClient ("DRES DCBA "+cur_client.SessionID+" SI1 SL1 FN/Searching: "+Key+" -- "+Messages[j]+" TRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA TO"+TOken);
                            if(cur_client.InQueueSearch==null)
                            {
                                cur_client.search_step++;
                                // cur_client.Lastsearch=System.currentTimeMillis ();
                            }
                           
                            cur_client.InQueueSearch=Issued_Command;
                            return;
                        }
                        
                    }
                    else
                        {
                        
                        long xx=Vars.search_spam_reset*1000;
                        
                        //System.out.println(xx);
                        if(System.currentTimeMillis ()-cur_client.Lastsearch<xx)
                        {
                            //cur_client.sendToClient (Issued_Command);
                            String [] Messages=Vars.Msg_Search_Spam.split ("\\\n");
                            for(int j=0;j<Messages.length;j++)
                              cur_client.sendToClient ("DRES DCBA "+cur_client.SessionID+" SI1 SL1 FN/Searching: "+Key+" -- "+Messages[j]+" TRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA TO"+TOken);
                             if(cur_client.InQueueSearch==null)
                             {  cur_client.search_step++;
                            //cur_client.Lastsearch=System.currentTimeMillis ();
                             }
                            cur_client.InQueueSearch=Issued_Command;
                            return;
                        }
                        
                    }
                    
                    cur_client.search_step++;
                    cur_client.Lastsearch=System.currentTimeMillis ();
                    }
                    else 
                    {
                        if(System.currentTimeMillis ()-cur_client.Lastautomagic<Vars.automagic_search*1000)
                        {
                            
                            return;
                        }
                        cur_client.Lastautomagic=System.currentTimeMillis ();
                            
                    }
                    if(Issued_Command.charAt (0)=='B')
                        new Broadcast(Issued_Command);
                    else if(Issued_Command.charAt (0)=='F')
                        new Broadcast(Issued_Command,1);
                    else
                    {
                        new STAError(cur_client,140,"SCH Invalid Context.");
                        return;
                    }
}
                    
       
        
             if(Issued_Command.charAt(0)=='D') //direct command, must send to target_sid only
        {
            
             if(Issued_Command.startsWith("DRES ")) //direct search result, only active to passive must send this
            {
               //  System.out.println (Issued_Command);
                StringTokenizer tok=new StringTokenizer(Issued_Command);
                String aux=tok.nextToken();
                aux=tok.nextToken();
                if(!aux.equals(cur_client.SessionID))
                {
                      new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                      return ;
                }
                aux=tok.nextToken();
                //now must look for the aux SID...
                ClientHandler temp=cur_client.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.SessionID.equals(aux))
                        break;
                    temp=temp.NextClient;
                }
                if(temp==null)//talking to inexisting client
                    return; //not kick, maybe the other client just left after he sent the msg;
                aux=tok.nextToken(); // this is the effective result
               
                temp.sendToClient(Issued_Command);
            }
            else if(Issued_Command.startsWith("DCTM ")) //direct connect to me
            {
                if(cur_client.ACTIVE==0)
                    return;
                StringTokenizer tok=new StringTokenizer(Issued_Command);
                String aux=tok.nextToken();
                aux=tok.nextToken();
                if(!aux.equals(cur_client.SessionID))
                {
                      new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                      return ;
                }
                aux=tok.nextToken();
                //now must look for the aux SID...
                ClientHandler temp=cur_client.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.SessionID.equals(aux))
                        break;
                    temp=temp.NextClient;
                }
                if(temp==null)//talking to inexisting client
                    return; //not kick, maybe the other client just left after he sent the msg;
                aux=tok.nextToken(); // this is the string representing protocol, next token is port, next token is TO
               
                temp.sendToClient(Issued_Command);
            }
            else if(Issued_Command.startsWith("DRCM ")) //reverse connect to me
            {
                if(cur_client.ACTIVE==1)
                    return;
                StringTokenizer tok=new StringTokenizer(Issued_Command);
                String aux=tok.nextToken();
                aux=tok.nextToken();
                if(!aux.equals(cur_client.SessionID))
                {
                      new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                      return ;
                }
                aux=tok.nextToken();
                //now must look for the aux SID...
                ClientHandler temp=cur_client.FirstClient.NextClient;
                while(temp!=null)
                {
                    if(temp.SessionID.equals(aux))
                        break;
                    temp=temp.NextClient;
                }
                if(temp==null)//talking to inexisting client
                    return; //not kick, maybe the other client just left after he sent the msg;
                aux=tok.nextToken(); // this is the string representig protocol, next token is port, next token is TO
               
                temp.sendToClient(Issued_Command);
            }
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
    public Command(ClientHandler CH, String Issued_command, String state) throws ClientFailedException, STAException,CommandException
    {
        cur_client=CH;
     // System.out.printf("["+cur_client.NI+"]:%s\n",Issued_command);
        
       if(Issued_command==null)
       { 
             //this means client is disconnected
           
            ClientHandler tempy=cur_client.FirstClient.NextClient;
            ClientHandler tempyprev=cur_client.FirstClient;
            while (!tempy.equals(cur_client))
            {
                tempyprev=tempyprev.NextClient;
                tempy=tempy.NextClient;
            }
            tempyprev.NextClient=tempy.NextClient;
            if(cur_client.FirstClient.NextClient!=null && cur_client.userok==1)
            {
            new Broadcast("IQUI "+cur_client.SessionID);
                  
            }
        
            try{
            cur_client.ClientSock.close();
            }
            catch (IOException e)
            {
            }
            
           
             throw new ClientFailedException("Client Disconnected.\n");
          
       }
            
       //System.out.printf("[Received]:%s\n",Issued_command);
       else if(Issued_command.equals(""))
       {
            //System.out.println("("+cur_client.NI+")"+System.currentTimeMillis ()/1000);
            return;
       }
            
       
    
        Issued_Command=Issued_command;
        State=state;
        HandleIssuedCommand();
    }
    
}
