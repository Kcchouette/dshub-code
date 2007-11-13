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
 *This class is the main storage place for ADC command that hub has to send.
 *Also contains functions to modify strings from normal to ADC type and viceversa.
 *
 * @author Pietricica
 */
abstract public class ADC 
{
    
    
    /**First string to send to connecting client ;)*/
    static final String Init="ISUP ADBASE ADUCM0"; //adding basic ucmds
    
    /**  ISID = session id string for connecting client*/
    static final String ISID="ISID";
    
    /** The default motd.*/
   static String MOTD="\n       CONGRATULATIONS you succesfully run DSHub and you are now connected to it.\n"+
           "Some reminders :\n" +
           "DSHub is ADC software so you need an ADC compatibile client.\n"+
"At the moment of this release ( November 2007 ), the following ADC clients were available:\n"+
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