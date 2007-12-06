/*
 * HubtrackerModule.java
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

package dshub.hubtracker;

import dshub.Modules.*;
import dshub.*;

/**
 *
 * @author Pietricica
 */
public class HubtrackerModule implements DSHubModule
{
    
    /** Creates a new instance of HubtrackerModule */
    public HubtrackerModule()
    {
        ;//nothing to do yet, the module is OK (!)
    }
    
    public void onCommand(ClientHandler cur_client,String Issued_Command)
    {
        
    }
    
    public void onConnect(ClientHandler cur_client)
    {
        
    }
    
    public void onRawCommand(ClientHandler cur_client,String Raw_Command)
    {
        
    }
    
    public void onQuit(ClientHandler cur_client)
    {
        
    }
    
}
