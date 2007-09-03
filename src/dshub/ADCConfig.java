/*
 * ADCConfig.java
 *
 * Created on 03 septembrie 2007, 18:17
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
 *
 * @author Administrator
 */
public class ADCConfig 
{
    ClientHandler cur_client;
    String msg;
    /** Creates a new instance of ADCConfig */
    public ADCConfig(ClientHandler CH,String msg) 
    {
        cur_client=CH;
        this.msg=msg;
        StringTokenizer TK=new StringTokenizer(msg);
        
    }
    
}
