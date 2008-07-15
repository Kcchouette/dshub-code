package dshub;
/*
 * Variables.java
 *
 * Created on 06 martie 2007, 16:04
 *
 * DSHub ADC HubSoft
 * Copyright (C) 2007,2008  Eugen Hristev
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

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Basic hub variables to be kept in config file.
 * Serializable and static class.
 *
 * @author Pietricica
 */
public class Variables implements Serializable
{
      int Timeout_Login;
      /** Default port on which to start hubbie */
      //int Default_Port;
    

    
      String HubVersion;
      
      String HubDE;
      
      String HubName;
      

 
      String About;
      
      String Opchat_name;
      String Opchat_desc;
      String bot_name;
      String bot_desc;
      
      String Msg_Banned;
      
      String nick_chars;
      String Msg_Full;

      String Msg_Search_Spam;
      
      String SecurityCid;
      String OpChatCid;
      
      String Hub_Host;
      
      String Proxy_Host;
      int Proxy_Port;
      
      String redirect_url;
      
      String lang;
      
     int max_ni;
     int min_ni;
     int max_de;
     long max_share ; 
     long min_share;
     int max_sl;
     int min_sl;
     int max_em;
     int max_hubs_op;
     int max_hubs_reg;
     int max_hubs_user;
     int min_sch_chars;
     int max_sch_chars;
     int max_chat_msg;

     int command_pm;

     int history_lines;
     int kick_time;

     int reg_only;
     int max_users;


     int chat_interval;

     int savelogs;
     int automagic_search;
     int search_log_base;
     int search_steps;
     int search_spam_reset;
     
     
     String activePlugins;

    // ****** ADC advanced config ************* 
     
     int BMSG;
     int DMSG;
     int EMSG;
     int FMSG;
     int HMSG;
     
     int BSTA;
     int DSTA;
     int ESTA;
     int FSTA;
     int HSTA;
     
      int BCTM;
     int DCTM;
     int ECTM;
     int FCTM;
     int HCTM;
    
     int BRCM;
     int DRCM;
     int ERCM;
     int FRCM;
     int HRCM;
    
     int BINF;
     int DINF;
     int EINF;
     int FINF;
     int HINF;
    
     int BSCH;
     int DSCH;
     int ESCH;
     int FSCH;
     int HSCH;
    
     int BRES;
     int DRES;
     int ERES;
     int FRES;
     int HRES;
    
     int BPAS;
     int DPAS;
     int EPAS;
     int FPAS;
     int HPAS;
    
     int BSUP;
     int DSUP;
     int ESUP;
     int FSUP;
     int HSUP;
     public LinkedList<Port> activePorts;
     
      public Variables()
      {
          Timeout_Login=Vars.Timeout_Login;
         // Default_Port=Vars.Default_Port;

          HubDE=Vars.HubDE;
          HubName=Vars.HubName;

          About=Vars.About;
          HubVersion=Vars.HubVersion;

      max_ni=Vars.max_ni;
      min_ni=Vars.min_ni;
      max_de=Vars.max_de;
      max_share =Vars.max_share; //10 tebibytes
      min_share=Vars.min_share;
      max_sl=Vars.max_sl;
      min_sl=Vars.min_sl;
      max_em=Vars.max_em;
      max_hubs_op=Vars.max_hubs_op;
      max_hubs_reg=Vars.max_hubs_reg;
      max_hubs_user=Vars.max_hubs_user;
      min_sch_chars=Vars.min_sch_chars;
      max_sch_chars=Vars.max_sch_chars;
      max_chat_msg=Vars.max_chat_msg;
      command_pm=Vars.command_pm;

      history_lines=Vars.history_lines;
      Opchat_name=Vars.Opchat_name;
      Opchat_desc=Vars.Opchat_desc;
      kick_time=Vars.kick_time;
      Msg_Banned=Vars.Msg_Banned;
      
      SecurityCid=Vars.SecurityCid;
      OpChatCid=Vars.OpChatCid;
      
      Hub_Host=Vars.Hub_Host;
      
        Proxy_Host=Vars.Proxy_Host;
       Proxy_Port=Vars.Proxy_Port;
       
       redirect_url=Vars.redirect_url;
      

      reg_only=Vars.reg_only;
      nick_chars=Vars.nick_chars;
      max_users=Vars.max_users;
      Msg_Full=Vars.Msg_Full;




      savelogs=Vars.savelogs;
      automagic_search=Vars.automagic_search;
      search_log_base=Vars.search_log_base;
      search_steps=Vars.search_steps;
      search_spam_reset=Vars.search_spam_reset;
      Msg_Search_Spam=Vars.Msg_Search_Spam;
      bot_name=Vars.bot_name;
      bot_desc=Vars.bot_desc;
      
      activePlugins=Vars.activePlugins;
      
      activePorts=Vars.activePorts;
      
      lang=Vars.lang;
      
      BMSG=Vars.BMSG;
      DMSG=Vars.DMSG;
      EMSG=Vars.EMSG;
      FMSG=Vars.FMSG;
      HMSG=Vars.HMSG;
      
      BSTA=Vars.BSTA;
      DSTA=Vars.DSTA;
      ESTA=Vars.ESTA;
      FSTA=Vars.FSTA;
      HSTA=Vars.HSTA;
      
      BCTM=Vars.BCTM;
      DCTM=Vars.DCTM;
      ECTM=Vars.ECTM;
      FCTM=Vars.FCTM;
      HCTM=Vars.HCTM;
      
      BRCM=Vars.BRCM;
      DRCM=Vars.DRCM;
      ERCM=Vars.ERCM;
      FRCM=Vars.FRCM;
      HRCM=Vars.HRCM;
      
      BINF=Vars.BINF;
      DINF=Vars.DINF;
      EINF=Vars.EINF;
      FINF=Vars.FINF;
      HINF=Vars.HINF;
      
      BSCH=Vars.BSCH;
      DSCH=Vars.DSCH;
      ESCH=Vars.ESCH;
      FSCH=Vars.FSCH;
      HSCH=Vars.HSCH;
      
      BRES=Vars.BRES;
      DRES=Vars.DRES;
      ERES=Vars.ERES;
      FRES=Vars.FRES;
      HRES=Vars.HRES;
       
      BPAS=Vars.BPAS;
      DPAS=Vars.DPAS;
      EPAS=Vars.EPAS;
      FPAS=Vars.FPAS;
      HPAS=Vars.HPAS;
      
      BSUP=Vars.BSUP;
      DSUP=Vars.DSUP;
      ESUP=Vars.ESUP;
      FSUP=Vars.FSUP;
      HSUP=Vars.HSUP;
      }
}
 