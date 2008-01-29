/*
 * Translation.java
 *
 * Created on 27 ianuarie 2008, 17:24
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

import java.text.MessageFormat;
import java.util.*;

/**
 *
 * @author Pietricica
 */
public class Translation 
{
static String cc;
static String lc;

public static ResourceBundle Strings=null;
static Locale curLocale;

static
{
        curLocale=Locale.getDefault()    ;
         
         try
         {
              Strings = ResourceBundle.getBundle("Translation",
                                           curLocale);
         }
         catch (java.util.MissingResourceException mre)
         {
             System.out.println("Fatal Error : Unable to locate Translation.properties file or any other translation. FAIL.");
             System.exit(1);
         }
         
        
         System.out.println("Current found country : " + (cc=curLocale.getCountry()));
         lc=curLocale.getLanguage();
         System.out.println(getString("init"));
}

public static String getString(String what)
{
    try
    {
    if(what.equals("startup"))
    {
        Object[] Args = {
    Vars.HubVersion
       };
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(curLocale);
        
        formatter.applyPattern(Strings.getString("startup"));
        return formatter.format(Args);
        
        
    }
    if(what.equals("init"))
    {
        return Strings.getString("init");
    }
    
    return Strings.getString(what);
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }
    
    return null;
}

public static String getUserRegged(String nick, String cid)
{
    Object[] Args = {
    nick, cid
       };
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(curLocale);
        
        formatter.applyPattern(Strings.getString("user_regged"));
        return formatter.format(Args);
    
}
public static String getFoundCid(String nick)
{
    Object[] Args = {
    nick
       };
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(curLocale);
        
        formatter.applyPattern(Strings.getString("regged_found_cid"));
        return formatter.format(Args);
    
}
public static String getNotCid(String aux)
{
    Object[] Args = {
    aux
       };
        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(curLocale);
        
        formatter.applyPattern(Strings.getString("not_cid"));
        return formatter.format(Args);
   
}
}
