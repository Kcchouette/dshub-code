/*
 * BackupCmd.java
 *
 * Created on 28 noiembrie 2007, 15:36
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
public class BackupCmd
{
    
    /** Creates a new instance of BackupCmd */
    public BackupCmd(ClientHandler cur_client,String cmd)
    {
         StringTokenizer curcmd=new StringTokenizer(cmd);
        curcmd.nextToken();
        if(!curcmd.hasMoreTokens())
            {
            String Help="\nThe backup command:\n"+
                    "Usage backup what [where]"+
                    "\n      \"what\" is one of {all,config,bans,regs} and represents what to backup."+
            "\n      \"where\" can be specified only for {config,bans,regs} and is a filename for where to save backup. If no \"where\" specified, files are saved as "+
                    "name.bak ; also on specifier all, the same name covention used."+
                    "\nExample: !backup config conf.saved";
            
            cur_client.sendFromBot(Help);
            return;
            }
    }
    
}
