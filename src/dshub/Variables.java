package dshub;
/*
 * Variables.java
 *
 * Created on 06 martie 2007, 16:04
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

import java.io.Serializable;

/**
 *
 * @author Pietricica
 */
public class Variables implements Serializable
{
      int Timeout_Login;
      /** Default port on which to start hubbie */
      int Default_Port;
    

    
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
      
     int max_ni;
     int min_ni;
     int max_de;
     long max_share ; //10 tebibytes
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
     int kick_ops;
     int history_lines;
     int kick_time;
     int rename_ops;
     int reg_only;
     int max_users;


     int chat_interval;
     int keep_alive_interval;
     int savelogs;
     int automagic_search;
     int search_log_base;
     int search_steps;
     int search_spam_reset;
     

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
     
      public Variables()
      {
          Timeout_Login=Vars.Timeout_Login;
          Default_Port=Vars.Default_Port;

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
      kick_ops=Vars.kick_ops;
      history_lines=Vars.history_lines;
      Opchat_name=Vars.Opchat_name;
      Opchat_desc=Vars.Opchat_desc;
      kick_time=Vars.kick_time;
      Msg_Banned=Vars.Msg_Banned;
      rename_ops=Vars.rename_ops;
      reg_only=Vars.reg_only;
      nick_chars=Vars.nick_chars;
      max_users=Vars.max_users;
      Msg_Full=Vars.Msg_Full;



      keep_alive_interval=Vars.keep_alive_interval;
      savelogs=Vars.savelogs;
      automagic_search=Vars.automagic_search;
      search_log_base=Vars.search_log_base;
      search_steps=Vars.search_steps;
      search_spam_reset=Vars.search_spam_reset;
      Msg_Search_Spam=Vars.Msg_Search_Spam;
      bot_name=Vars.bot_name;
      bot_desc=Vars.bot_desc;
      
      
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
 class Vars
{
    static boolean ValidateNick(String nick)
    {
        for(int i=0;i<nick.length ();i++)
            if(nick_chars.indexOf (nick.charAt (i))==-1)
                return false;
        
       if(ADC.isIP (nick)) 
           return false;
        return true;
    }
     
    static int  Timeout_Login=20;
    static int  Default_Port=411;
   
    static String HubVersion="DSHub Eta";
    static String HubDE="";
    static String HubName="hub of "+System.getProperty ("user.name");
   
            
    static String About=
                   "               Death Squad Hub\n"+
                   "                 The credits\n"
              +    "          Project Coordinator: Pietry\n"
              +    "         Development Team: Death Squad\n"

              +    "Thanks to all Testers and Contributors and all Team Members.";
    
    static String Opchat_name="OpChat";
    static String Opchat_desc="BoT";
    static String bot_name="DSHub";
    static String bot_desc="www.death-squad.ro/dshub";
    
    static int max_ni=64;
    static int min_ni=1;
    static int max_de=128;
    static long max_share =10485760L; //10 tebibytes //but are now in mibibytes
    static long min_share=0;
    static int max_sl=1000;
    static int min_sl=0;
    static int max_em=128;
    static int max_hubs_op=70;
    static int max_hubs_reg=30;
    static int max_hubs_user=200;
    static int min_sch_chars=3;
    static int max_sch_chars=256;
    static int max_chat_msg=512;
    static int kick_ops=0;
    static int history_lines=50;
    static int kick_time=300;
    static int rename_ops=0;
    static int reg_only=0;
    static int max_users=1000;


    static int chat_interval=500;//millis
    static int keep_alive_interval=120;//seconds
    static int savelogs=1;
    static int automagic_search=36;
    static int search_log_base=2000;
    static int search_steps=6;
    static int search_spam_reset=300;
    
    static String Msg_Banned="Have a nice day and don't forget to smile !";
    static String Msg_Full="Have a nice day and don't forget to smile !";
    static String nick_chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890[]()-.,;'`~*&^%$#@!+=_|{}<>:";
    static String Msg_Search_Spam="Search ticket Reserved.\nPlease be patient while search\nis being processed.\nDo NOT close this window or start other search\nor you will lose this search !".replaceAll (" ","\\ ");
    
    // ****** ADC advanced config ************* 
    
    static int BMSG=1;
    static int DMSG=1;
    static int EMSG=1;
    static int FMSG=1;
    static int HMSG=1;
    
    static int BSTA=0;
    static int DSTA=1;
    static int ESTA=1;
    static int FSTA=0;
    static int HSTA=1;
    
    static int BCTM=0;
    static int DCTM=1;
    static int ECTM=1;
    static int FCTM=0;
    static int HCTM=0;
    
    static int BRCM=0;
    static int DRCM=1;
    static int ERCM=1;
    static int FRCM=0;
    static int HRCM=0;
    
    static int BINF=1;
    static int DINF=0;
    static int EINF=0;
    static int FINF=0;
    static int HINF=0;
    
    static int BSCH=1;
    static int DSCH=1;
    static int ESCH=1;
    static int FSCH=1;
    static int HSCH=0;
    
    static int BRES=0;
    static int DRES=1;
    static int ERES=1;
    static int FRES=0;
    static int HRES=0;
    
    static int BPAS=0;
    static int DPAS=0;
    static int EPAS=0;
    static int FPAS=0;
    static int HPAS=1;
    
    static int BSUP=0;
    static int DSUP=0;
    static int ESUP=0;
    static int FSUP=0;
    static int HSUP=1;
}