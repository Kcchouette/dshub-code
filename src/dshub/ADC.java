package dshub;
/*
 * ADC.java
 *
 * Created on 04 martie 2007, 13:20
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

import java.lang.String;

/**
 *
 * @author Pietricica
 */
abstract public class ADC 
{
    
    
    /**First string to send to connecting client ;)*/
    static String Init="ISUP ADBASE ADUCM0"; //adding basic ucmds
    
    /**  ISID = session id string for connecting client*/
    static String ISID="ISID";
    
    /** The default motd.*/
   static String MOTD="\n       CONGRATULATIONS you succesfully run DSHub and you are now connected to it.\n"+
           "Some reminders :\n" +
           "DSHub is ADC software so you need an ADC compatibile client.\n"+
"At the moment of this release ( October 2007 ), the following ADC clients were available:\n"+
"dc++ 0.69*, icedc 1.01a, zion++ 2.04  apexdc 0.3.0, strongdc  2.01 , zk++  0.7, BCDC 0.69*, FMDC, Elise or ANY later version of those will be ADC compatible.\n"+
"So after you start the Hub, try connecting to adc://127.0.0.1:411\n"+
"Some ADC reminders:\n"+
"-- You need to connect to address adc://\n"+
"-- There is no default port, every time one must be specified ( like 411 on NMDC)\n"+
"-- Accounts are on CID not nick ( you can use what nick you want )\n"+
"-- Clients that are not ADC compat or dont use the address correctly will just hang up and you will see them at Connecting Users in stats command.\n"+
"Oh and another thing, NMDC hublists dont work with ADC, so i got 2 fine lists that support ADC for you:\n"+
"  www.hubtracker.com\n"+
"  www.adchublist.com\n"+
"Thanks for using DSHub and I hope you will have as much fun using it as I had creating it ;)\n"+

"For latest version, updates, any suggestions, information, or just anything visit www.death-squad.ro/dshub\n"+
           "Or the SourceForge page located at http://www.sf.net/projects/dshub\n"+
           "Have a nice hubbing !"
           
          /*           * fixed cfg spaces in setting topic hub name etc.
                       * fixed small gui stuff in opchat desc & name;
           * added advanced adc config panel;
           * added the MSG context parameters;
           * added variables in config file, added the modifiers in GUI;
           * the msg context modders work now;
           * changed from simple linked list to double one;
           * fixed history and cmdhistory space problem;
           * added new file for adc adv config ;
           * moved adc functions from cmd parser to adc class;
           * fixed adc adv config bug forgot the break in switch;
           * created primary showup for setting the adc via commands;
           * fixed adc adv panel avail via pm with bot;
           * added adc _context__name shows if on/off;
           * fixed mass message thingy;
           * also minor fixes in extended kick n drop;
           * diffed back to good version of clienthandler, forgot to do that last time;
           * fixed opchat & bot desc not showing properly;
           * fixed reginfo badly shown;
           * tried to reduce mem usage by disposing the GUI when not shown ;
           * fixed not showing restart/quit message when using command;
           * finished msg context changing via command;
           * added STA checkboxes in adc adv panel;
           * added info box on contexts;
           * added another 2 info boxes on MSG and STA commands;
           * created new file CFGConfig and moved all cfg in there;
           * modified cfg commands with [config:] instead of [command:];
           * created 3 new files, Ext mass, drop and kick, moved code there;
           * added  new config variables , for STA contexts;
           * validated boxes in gui, and added in panel and modifiers via command;
           * fixed broadcasting same stuff to sender too;
           * fixed broadcasting feature searches to all;
           * created new file SCH for sch command;
           * created new file STA for sta command, also made possible D/E sta;
           * tried to fix clientassasin on bug when only quit was sent but client still conn;
           * moved info command to separate file, corrected bug when showing \s in tag;
           * added boxes for CTM command;
           * created variables for all the rest of commands, added to config file;
           * added boxes for other commands in the GUI;
           * after hours of trying to make checkboxes( stupid java editor), finally done them;
           * made initial checks on checkboxes;
           * started work for modifying via command;
           * finished the adc cmd panel;
           * created a CTM file for ctm command;
           * created new RCM file moved rcm command there;
           * created also new file for RES;
           * enabled support for ERES ECTM and ERCM;
           * modified MSG cmd to new file;
           * moved SUP to new file;
           * added modders for PAS as well;
           * fixed linked list banning bug ( thanks Spader for spotting );
           * added button for account editing;
           * modified first page;
           * created AccountEditer.java for a new jframe to edit account proppies;
           * added first fields into account editing window;
           * clicking X on main window now does nothing, need to click Exit to close;
           * added more fields to the edit account window;
           * created 2 more files, one for the command masking of each account;
           * modified regconfig to include them;
           * the second file generates a help file according to mask;
           * removed the necesity of "help" file;
           * the help in the console is generated with a full account mask;
           * moved all buttons in account edit in a nice tabbed pane;
           * continued work on accounts panel, buttons work but do nothing atm;
           * saved command maskes, now need to modify access via CommandParser;
           * command allocation from gui works now, and access is set via Command parser;
           * profiles are almost done, need to create a command that sets access via client too;
           * fixed showing key for all regs no matter if they had or not;
           * all regs still enter opchat, need to fix that;
           * key setting on gui works now, still need to make it instantly not on reconnect;
           * improved partially the registration info shown on reg and info commands;
           * key allocating and removing works instantly now;
           * fixed bug when thread crashed on !info if user had null description ( tnx Toast for spotting);
           * fixed bug on Key allocating when wrong hub count was made;
           * fixed issue on ClientAssassin that checked dead clients @ 5secs instead of 2 mins;
           * checks for override spam and share are functional, need to change what they do
           * creating premise for getting rid of ops_override_spam and share;
           * fixed hub counts on key and registered on reg and ureg;
           * almost finished boxes in account edit;
           * fixed bug when adding key and button did not work ( asking CID on not userok);
           * removed variable ops_override_spam, every reg has its own;
           * done with override hub full command, some checkboxes still do nothing;
           * improved reg information on !reg and !info;
           * removed variable ops_override_full from testframe and cfg;
           * all accounts have their own override;
           * added a grant command to GUI and reg configuration;
           * added a grant command file to handle grants command;
           **/
            
            ;
    public static String retNormStr(String blah)
    {
        return blah.replaceAll ("\\\\s"," ").replaceAll ("\\\\n","\n").replaceAll ("\\\\\\\\","\\\\");
    }
    public static String retADCStr(String blah)
    {
         return blah.replaceAll ("\\\\","\\\\\\\\").replaceAll (" ","\\\\s").replaceAll ("\n","\\\\n");
    }
    
    public static boolean isIP(String blah)
    {
        return blah.matches("\\b(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b");
        
       
    }
    public static boolean isCID(String blah)
    {
        if(blah.length ()!=39)
            return false;
        try
        {
            Base32.decode (blah);
        }
        catch(IllegalArgumentException iae)
        {
            return false;
        }
        return true;
       
    }
    
    
}