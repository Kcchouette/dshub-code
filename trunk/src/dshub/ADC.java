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
    
    /** IINF hub info parsing to connecting client*/
   static String MOTD="\n       CONGRATULATIONS you succesfully run DSHub and you are now connected to it.\n"+
           "Some reminders :\n" +
           "DSHub is ADC software so you need an ADC compatibile client.\n"+
"At the moment of this release ( August 2007 ), the following ADC clients were available:\n"+
"dc++ 0.69*, icedc 1.01a, zion++ 2.04  apexdc 0.4.0, strongdc  2.01 , zk++  0.7, BCDC 0.69, FMDC, Elise or ANY later version of those will be ADC compatible.\n"+
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
           
          /* "           * fixed cfg spaces in setting topic hub name etc.\n" +
            "           * fixed small gui stuff in opchat desc & name;\n"+
            "           * //met MAGY he helped me with a GUI for dshub, just testing some GUI now;\n"+
            "           * tested GUI on windows and linux with no X server, in last case, message GUI not viewable shows up;\n"+
            "           * still need to test on X server;\n"+
            "           * added a gui command that brings up GUI;\n"+
            "           * gui still has minimal functionality, lot of work still to do;\n" +
            "           * fixed bug with running via dbl click with class path;\n"+
            "           * fixed bug not showing correct port after change;\n"+
            "           * a lot of new changes in GUI, added some tabs, config settings, and account listing;\n"+
            "           * fixed minor bugs with config variables;\n"+
            "           * hope the new GUI is nice, waiting for feedback :D\n"+
            "           * modified GUi added log tab with log;\n"+
            "           * created popmsg fcuntion that pops a message console & log;\n"+
            "           * moved settings in 4 tabs according to type;\n"+
            "           * fixed minor problems in other files;\n"+
            "           * still TODO : sta, keepalive;\n"+
            "           * completeley redesigned settings tab;\n"+
            "           * now the string variables like nick chars, may contain space, newline and slash;\n"+
            "           * fixed the variables check from gui, modifying into cmd parser too;\n"+
            "           * changed with default hub name in form title;\n"+
            "           * corrected rename, topic bugs from rc4, sorry about them;\n"+
            "           * changed the keep alive completely, added some other checker, will remove var keep_alive too probably;\n"+
            "           * started code optimizing: 1) optimized thread priorities, some useless params on threads;\n"+
            "           * corrected message reading from client deleted crappy code that possibly dropped laggy and multiple messaging;\n"+
            "           * added another variable save_logs and to main settings, that checks if logs are saved in format starttime&date.log;\n"+
            "           * refixed minor bugs that some previous changes made, im SORRY;\n"+
            "           * added some commands to log registry;\n"+
            "           * fixed client assassin again, sorry for it;\n"+
            "           * fixed creating ugly log file with wrong path & name with startup message;\n"+
            "           * modified share to MiB, added extra message to disconnect specifying min share;\n"+
            "           * TODO : messages for all disconnects( better ones);\n"+
                               " * DSHub has now powerful searching features..\n"+
                                "First, we need to make a distinction between the.\n"+
                                "automagic and the user searches.\n"+
                                "  First type is made by client at a regular interval\n"+
                                " and DSHub keeps a liniar spam setting.\n" +
                                "  Second type are user searches ( manual searches )\n"+
                                " that the user takes.\n"+
                                "For this type (because of the human factor)\n"+
                                " DSHub keeps a logarithmic spam setting.\n"+
                                "This way, the 2nd search is at search_log_base\n"+
                                "interval, but third, is at search_log_base^2\n"
                               +"and so on, until the power gets to max_steps.\n"+
                                " After this point, the user needs to wait\n"+
            "search_spam_reset seconds to get his burst back.\n"+
            "The searches are being kept in queue ( not ignored !)\n"+
            "and are processed once the timeout is completed\n"+
            "so user doesnt need to search again\n"+
            "but just wait for his search to be completed.\n"+
            "The messages appears as a fictive result\n"+
            "in his search box, which will be filled\n"+
            "once the search string is being sent to others.\n"+
            "           * Lots of new stuff, started working on the spam settings on searches;\n"+
            "           * above is the main info for the variables i added;\n"+
            "           * automagic_search is the automagic interval;\n"+
            "           * search_log_base the log base for search spam algorithm;\n"+
            "           * search_steps the max steps allowed in log scale;\n"+
            "           * search_spam_reset the time needed to reset burst ( also needed if search_steps is touched);\n"+
            "           * msg_search_spam the message that appears as a fake result in searches until the SCH is being sent;\n"+
            "           * added a hub_security bot that receives cmds in private ( but are shown as IMSGs still );\n"+
            "           * also added 2 vars: bot_name and bot_desc to set the NI and DE of the bot;\n"+
            "           * added extra fields to clienthandler so it can properly remember chat settings;\n"+
            "           * fixed some banning bugs...\n"+
            "           * added posibility to delete accounts from GUI via delete key...\n"+
            "           * fixed bugs when renaming and not check the bot cid & nick\n"+
            "           * added extended possibilites to mass comand, renamed from massall to mass\n"+
            "           * hub security now sends all messages except login;\n"+
            "           * finally found bug that killed me over last month, when passive user couldnt connect irregulary (found out why);\n"+
            "           * completely changed string parsing, now everything is going thru 2 functions;\n"+
            "Zeta Ultra Version"*/
            ;
    
    
    
}