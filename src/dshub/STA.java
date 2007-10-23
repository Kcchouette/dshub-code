/*
 * STA.java
 *
 * Created on 08 septembrie 2007, 12:09
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
 * Basic implementation of ADC STA command, in client to hub context.
 *
 * @author Pietricica
 */
public class STA
{
    
    /** Creates a new instance of STA */
    public STA (ClientHandler cur_client,String recvbuf, String State) throws STAException
    {
        if(recvbuf.charAt (0)=='B')
        {
            if(Vars.BSTA==0)
            {
                cur_client.sendFromBot ("STA invalid context B");
                return;
            }
        }
        else if(recvbuf.charAt (0)=='D')
        {
            if(Vars.DSTA==0)
            {
                cur_client.sendFromBot ("STA invalid context D");
                return;
            }
            StringTokenizer TK=new StringTokenizer(recvbuf);
            TK.nextToken ();
            String cursid=TK.nextToken ();
            if(!cursid.equals (cur_client.SessionID))
            {
                 new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                 return ;
            }
            String dsid=TK.nextToken ();
            ClientHandler target=ClientHandler.FirstClient.NextClient
                    ;
            while(target!=null)
            {
                if(target.SessionID.equals (dsid))
                    break;
            }
            if(target==null)
            {
                new STAError(cur_client,140,"Invalid Target Sid.");
                return ;
            }
                 
            target.sendToClient (recvbuf);
            
            
        }
        else if(recvbuf.charAt (0)=='E')
        {
            if(Vars.ESTA==0)
            {
                cur_client.sendFromBot ("STA invalid context E");
                return;
            }
            StringTokenizer TK=new StringTokenizer(recvbuf);
            TK.nextToken ();
            String cursid=TK.nextToken ();
            if(!cursid.equals (cur_client.SessionID))
            {
                 new STAError(cur_client,240,"Protocol Error.Wrong SID supplied.");
                 return ;
            }
            String esid=TK.nextToken ();
            ClientHandler target=ClientHandler.FirstClient.NextClient
                    ;
            while(target!=null)
            {
                if(target.SessionID.equals (esid))
                    break;
            }
            if(target==null)
            {
                new STAError(cur_client,140,"Invalid Target Sid.");
                return ;
            }
                 
            target.sendToClient (recvbuf);
            cur_client.sendToClient (recvbuf);
        }
        else if(recvbuf.charAt (0)=='F')
        {
            if(Vars.FSTA==0)
            {
                cur_client.sendFromBot ("STA invalid context F");
                return;
            }
        }
        else if(recvbuf.charAt (0)=='H')
        {
            // ok, client has an error. what can i do about it? :))
            if(Vars.HSTA==0)
            {
                cur_client.sendFromBot ("STA invalid context H");
                return;
            }
        }
    }
    
}
