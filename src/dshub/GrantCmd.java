/*
 * GrantCmd.java
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
 * @author Pietricica
 */
public class GrantCmd
{
    
    /** Creates a new instance of GrantCmd */
    public GrantCmd(ClientHandler cur_client,String cmd)
    {
        
        
        StringTokenizer curcmd=new StringTokenizer(cmd);
        curcmd.nextToken();
        if(!curcmd.hasMoreTokens())
            {
            String Help="\nThe grant command:\n"+
                    "Usage grant user/CID [[+-]attribute1]*"+
                    "\n      +attribute adds the atribute to the registered user, - removes it.";
            cur_client.sendFromBot(Help);
            return;
            }
        String who=curcmd.nextToken();
        nod modnod=null;
        if(ADC.isCID(who))
        {
             modnod=reg_config.getnod(who);
        }
        else
        {
            ClientHandler temp=ClientHandler.FirstClient.NextClient;
            while(temp!=null)
            {
                if(temp.userok==1)
                    if(temp.NI.equalsIgnoreCase(who))
                        modnod=reg_config.getnod(temp.ID);
                temp=temp.NextClient;
                        
            }
            if(temp==null)
            {
                cur_client.sendFromBot("Invalid argument supplied. Use with no arguments to see usage tip.");
                return;
            }
        }
        if(!curcmd.hasMoreTokens())
        {
            cur_client.sendFromBot(modnod.getRegInfo());
            return;
        }
    }
    
}
