/*
 * PluginMain.java
 *
 * Created on 06 decembrie 2007, 20:30
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

package dshub.plugin;

import dshub.Modules.*;
import dshub.*;
import javax.swing.JFrame;

/**
 *
 * @author Pietricica
 */
public class PluginMain implements DSHubModule
{
    static HubtrackerFrame curFrame;
   public static String result="";
    
    public boolean onCommand(ClientHandler cur_client,String Issued_Command)
    {
       // System.out.println("hubtracker");
        if(Issued_Command.toLowerCase().startsWith("hubtracker") || 
                Issued_Command.toLowerCase().startsWith("hubtracker"))
        {
            new HubtrackerCmd(cur_client,Issued_Command);
            return true;
        }
        return false;
    }
    
    public void onConnect(ClientHandler cur_client)
    {
        ; //hubtracker integration module has nothing to do here
    }
    
    public void onRawCommand(ClientHandler cur_client,String Raw_Command)
    {
        ; //hubtracker integration module has nothing to do here
    }
    
    public void onClientQuit(ClientHandler cur_client)
    {
        ; //hubtracker integration module has nothing to do here
    }
     public boolean startup()
     {
         ;//nothing to do yet, the module is OK (!)
        curFrame=null;
        result="";
         return true;
     }
     
     public void close()
     {
         
     }
     
    public void onGUIClick(JFrame parent)
    {
         curFrame=new HubtrackerFrame();
        curFrame.setVisible(true);
    }
    
    public String getName()
    {
        return "Hubtracker Integration";
    }
    
}
