/*
 * CFGConfig.java
 *
 * Created on 07 septembrie 2007, 10:48
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
 * The main hub configuration utility, is called when via command or tty a configuration wants changed.
 *
 * @author Pietricica
 */
public class CFGConfig 
{
    
    /** Creates a new instance of CFGConfig */
    public CFGConfig(ClientHandler cur_client, String recvbuf) 
    {
        if(recvbuf.equals ("cfg"))
                {
                cur_client.sendFromBot("Usage: cfg <varname> <newval>. cfg list to see all.");
                //break;
                }
                else
                {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken ();
                if(aux.toLowerCase().equals ("timeout_login"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.Timeout_Login;
                        Vars.Timeout_Login=Integer.parseInt (aux);
                        cur_client.sendFromBot("Timeout_Login changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("hub_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Hub_name changed from \""+
                                Vars.HubName+"\" to \""+new_name+"\".");
                        
                        Vars.HubName=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("IINF NI"+ADC.retADCStr(Vars.HubName));
                        
                }
                else if(aux.toLowerCase().equals ("max_ni"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_ni;
                        Vars.max_ni=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_NI changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_ni"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.min_ni;
                        Vars.min_ni=Integer.parseInt (aux);
                        cur_client.sendFromBot("Min_NI changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_de"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_de;
                        Vars.max_de=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_DE changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_share"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_share;
                        Vars.max_share=Long.parseLong (aux);
                        cur_client.sendFromBot("Max_share changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_share"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.min_share;
                        Vars.min_share=Long.parseLong (aux);
                        cur_client.sendFromBot("Min_share changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_sl"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_sl;
                        Vars.max_sl=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_SL changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_sl"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_sl;
                        Vars.min_sl=Integer.parseInt (aux);
                        cur_client.sendFromBot("Min_SL changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_em"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_em;
                        Vars.max_em=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_EM changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_op"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_op;
                        Vars.max_hubs_op=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_hubs_op changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_reg"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_reg;
                        Vars.max_hubs_reg=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_hubs_reg changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_hubs_user"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_hubs_user;
                        Vars.max_hubs_user=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_hubs_user changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                       Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_sch_chars"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.max_sch_chars;
                        Vars.max_sch_chars=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_sch_chars changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("min_sch_chars"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        long aucsy=Vars.min_sch_chars;
                        Vars.min_sch_chars=Integer.parseInt (aux);
                        cur_client.sendFromBot("Min_sch_chars changed from \""+Long.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("max_chat_msg"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_chat_msg;
                        Vars.max_chat_msg=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_chat_msg changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                
                else if(aux.toLowerCase().equals ("history_lines"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.history_lines;
                       /* if(Integer.parseInt (aux)<10)
                        {
                          Vars.history_lines=10;
                        }
                        else if(Integer.parseInt (aux)>300)
                            Vars.history_lines=300;
                        else
                        Vars.history_lines=Integer.parseInt (aux);
                        */
                        
                        cur_client.sendFromBot("History lines changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("opchat_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                          // new_name=new_name+" "+ST.nextToken ();
                    if(!Vars.ValidateNick (new_name))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       return;
                   }
                    ClientNod tempy=ClientNod.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if ((tempy.cur_client.userok==1)
									&& ((tempy.cur_client.NI.toLowerCase ().equals(new_name.toLowerCase ()))))
								break;
                            tempy=tempy.NextClient;
                           
                        }
                    if(tempy!=null)
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                        cur_client.sendFromBot("Opchat_name changed from \""+
                                Vars.Opchat_name+"\" to \""+new_name+"\".");
                        
                        Vars.Opchat_name=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF ABCD NI"+ADC.retADCStr(Vars.Opchat_name),10);
                        
                }
                else if(aux.toLowerCase ().equals ("bot_name"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                          // new_name=new_name+" "+ST.nextToken ();
                    if(!Vars.ValidateNick (new_name))
                   {
                       cur_client.sendFromBot("Nick not valid, please choose another.");
                       return;
                   }
                    ClientNod tempy=ClientNod.FirstClient.NextClient;
                
                while(tempy!=null)
                        {
                            if ((tempy.cur_client.userok==1)
									&& ((tempy.cur_client.NI.toLowerCase ().equals(new_name.toLowerCase ()))))
								break;
                            tempy=tempy.NextClient;
                           
                        }
                    if(tempy!=null)
                    {
                       cur_client.sendFromBot("Nick taken, please choose another.");
                       return;
                    }
                        cur_client.sendFromBot("Bot_name changed from \""+
                                Vars.bot_name+"\" to \""+new_name+"\".");
                        
                        Vars.bot_name=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF DCBA NI"+ADC.retADCStr(Vars.bot_name));
                        
                }
                else if(aux.toLowerCase ().equals ("opchat_desc"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Opchat_desc changed from \""+
                                Vars.Opchat_desc+"\" to \""+new_name+"\".");
                        
                        Vars.Opchat_desc=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF ABCD DE"+ADC.retADCStr(Vars.Opchat_desc),10);
                        
                }
                else if(aux.toLowerCase ().equals ("bot_desc"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("bot_desc changed from \""+
                                Vars.bot_desc+"\" to \""+new_name+"\".");
                        
                        Vars.bot_desc=new_name;
                        Main.Server.rewriteconfig();
                        new Broadcast ("BINF DCBA DE"+ADC.retADCStr(Vars.bot_desc));
                        
                }
                else if(aux.toLowerCase().equals ("kick_time"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.kick_time;
                        if(Integer.parseInt (aux)<0)
                        {
                            cur_client.sendFromBot("Invalid kick time number.");
                            return;
                        }
                        Vars.kick_time=Integer.parseInt (aux);
                        
                        cur_client.sendFromBot("Kick time changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("msg_banned"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Msg_Banned changed from \""+
                                Vars.Msg_Banned+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Banned=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                
                else if(aux.toLowerCase().equals ("reg_only"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.reg_only;
                        Vars.reg_only=Integer.parseInt (aux);
                        cur_client.sendFromBot("Reg_only changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("nick_chars"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       //while(ST.hasMoreTokens ())
                       //    new_name=new_name+" "+ST.nextToken ();
                       
                        
                        cur_client.sendFromBot("Nick_chars changed from \""+
                                Vars.nick_chars+"\" to \""+new_name+"\".");
                        
                        Vars.nick_chars=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                
                else if(aux.toLowerCase ().equals ("msg_full"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Msg_Full changed from \""+
                                Vars.Msg_Full+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Full=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
               else if(aux.toLowerCase().equals ("max_users"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.max_users;
                        Vars.max_users=Integer.parseInt (aux);
                        cur_client.sendFromBot("Max_Users changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("chat_interval"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.chat_interval;
                       int blahhh= Integer.parseInt (aux);
                       if(blahhh<20)
                       {
                            cur_client.sendFromBot("A bit small don't you think ?");
                            return;
                       }
                        Vars.chat_interval=blahhh;
                                
                        
                        cur_client.sendFromBot("Chat_Interval changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                
               
                else if(aux.toLowerCase().equals ("save_logs"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.savelogs;
                        Vars.savelogs=Integer.parseInt (aux);
                        cur_client.sendFromBot("Save_logs changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("automagic_search"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.automagic_search;
                        Vars.automagic_search=Integer.parseInt (aux);
                        cur_client.sendFromBot("Automagic_search changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("search_log_base"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_log_base;
                        Vars.search_log_base=Integer.parseInt (aux);
                        cur_client.sendFromBot("Search_log_base changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase().equals ("search_steps"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_steps;
                        Vars.search_steps=Integer.parseInt (aux);
                        cur_client.sendFromBot("Search_steps changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                 else if(aux.toLowerCase().equals ("search_spam_reset"))
                {
                    aux=ST.nextToken ();
                    try
                    {
                        int aucsy=Vars.search_spam_reset;
                        Vars.search_spam_reset=Integer.parseInt (aux);
                        cur_client.sendFromBot("Search_spam_reset changed from \""+Integer.toString (aucsy)+"\" to \""+aux+"\".");
                        Main.Server.rewriteconfig();
                    }
                    
                    catch(NumberFormatException nfe)
                   {
                    cur_client.sendFromBot("Invalid number");
                   } 
                }
                else if(aux.toLowerCase ().equals ("msg_search_spam"))
                {
                     //  String ucsy=Vars.HubName;
                       String new_name=ST.nextToken ();
                       while(ST.hasMoreTokens ())
                           new_name=new_name+" "+ST.nextToken ();
                        
                        cur_client.sendFromBot("Msg_Search_Spam changed from \""+
                                Vars.Msg_Search_Spam+"\" to \""+new_name+"\".");
                        
                        Vars.Msg_Search_Spam=new_name;
                        Main.Server.rewriteconfig();
                        //new Broadcast ("IINF NI"+Vars.HubName);
                        
                }
                else if(aux.toLowerCase ().equals ("list"))
                {
                    String blah="Cfg Variables list: \n"+
                                       "   timeout_login           "  + Integer.toString (Vars.Timeout_Login) +"         -- Number of seconds for hub to wait for connecting users until kick them out.\n"
                            +          "   hub_name                "  + Vars.HubName+ "         -- Hub name to display in main window.\n"
                            +          "   max_ni                  "  +Vars.max_ni+"        -- Maximum nick size, integer.\n"
                            +          "   min_ni                  "  +Vars.min_ni+"         -- Minimum nick size, integer.\n"
                            +          "   max_de                  "  +Vars.max_de+"        -- Maximum description size, integer.\n"
                            +          "   max_share               "  +Vars.max_share+"         -- Maximum share size, long integer.\n"
                            +          "   min_share               "  +Vars.min_share+"         -- Minimum share size, long integer.\n"
                            +          "   max_sl                  "  +Vars.max_sl+"         -- Maximum slot number, integer.\n"
                            +          "   min_sl                  "  +Vars.min_sl+"        -- Minimum slot number, integer.\n"
                            +          "   max_em                  "  +Vars.max_em+"        -- Maximum e-mail string size, integer.\n"
                            +          "   max_hubs_op             "  +Vars.max_hubs_op+"       -- Maximum hubs where user is op, integer.\n"
                            +          "   max_hubs_reg            "  +Vars.max_hubs_reg+"       -- Maximum hubs where user is reg, integer.\n"
                            +          "   max_hubs_user           "  +Vars.max_hubs_user+"         -- Maximum hubs where user is user, integer.\n"
                            +          "   max_sch_chars           "  +Vars.max_sch_chars+"      -- Maximum search chars, integer.\n"
                            +          "   min_sch_chars           "  +Vars.min_sch_chars+"      -- Minimum search chars, integer.\n"
                            +          "   max_chat_msg            "  +Vars.max_chat_msg+"       -- Maximum chat message size, integer.\n"
                            +          "   max_users               "  +Vars.max_users+"         -- Maximum number of online users, integer.\n"
                            +          "   history_lines           "  +Vars.history_lines+"         -- Number of lines to keep in chat history.\n"
                            +          "   opchat_name             "  +Vars.Opchat_name+"       -- The Operator Chat Bot Nick.\n"
                            +          "   opchat_desc             "  +Vars.Opchat_desc+"       -- The Operator Chat Bot Description.\n"
                            +          "   kick_time               "  +Vars.kick_time+"         -- The time to ban a user with a kick, in seconds.\n"
                            +          "   msg_banned              "  +Vars.Msg_Banned+"        -- The aditional message to show to banned users when connecting.\n"
                            +          "   msg_full                "  +Vars.Msg_Full+"      -- Message to be shown to connecting users when hub full.\n"
                            +          "   reg_only                "  +Vars.reg_only+"      -- 1 = registered only hub. 0 = otherwise.\n"
                            +          "   nick_chars              "  +Vars.nick_chars+"         -- Chars that could be used for a nick, String.\n"
                            +          "   chat_interval           "  +Vars.chat_interval+"         -- Interval between chat lines, millis, Integer.\n"
                            +          "   save_logs               "  +Vars.savelogs+"         -- 1 = logs are saved to file, 0 otherwise.\n"
                            +          "   automagic_search        "  +Vars.automagic_search+"         -- Interval between automagic searches for each user, seconds, Integer.\n"
                            +          "   search_log_base         "  +Vars.search_log_base+"         -- Logarithmic base for user searches interval, millis, Integer.\n"
                            +          "   search_steps            "  +Vars.search_steps+"         -- Maximum nr of search steps allowed until reset needed, Integer.\n"
                            +          "   search_spam_reset       "  +Vars.search_spam_reset+"         -- Interval until search_steps is being reset, seconds, Integer.\n"
                            +          "   msg_search_spam         "  +Vars.Msg_Search_Spam+"         -- Message that appears as a result when search is delayed, String.\n"
                            +          "   bot_name                "  +Vars.bot_name+"         -- Hub security bot name, String.\n"
                            +          "   bot_desc                "  +Vars.bot_desc+"         -- Hub security bot description, String."
                            
                            
                            
                            ;
                    cur_client.sendFromBot(""+blah.replaceAll ("\\x0a","\\\n"));
                }
                else
                    cur_client.sendFromBot("Invalid cfg variable. Use \"cfg list\" to see all.");
                }
    }
    
}
