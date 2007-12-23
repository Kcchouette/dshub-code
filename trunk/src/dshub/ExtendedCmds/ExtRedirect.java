/*
 *  ExtRedirect.java
 * 
 * Created on 22 decembrie 2007, 10:19
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

package dshub.ExtendedCmds;

import dshub.*;
import java.util.StringTokenizer;

/**
 *
 * @author Pietricica
 */
public class ExtRedirect 
{

    public ExtRedirect(ClientHandler cur_client, String recvbuf)
    {
        if(recvbuf.equalsIgnoreCase ("redirect"))
                    {
                        
                                String Text=
                                "\nRedirect Command:\nRedirecting in DSHub is very simple now.\nClassic redirect:\n"+
                                "     redirect nick/CID [URL] - redirects user given by nick or CID to given URL\n" +
                                        "           If URL is not specified, the redirect_url variable is used.\n"+
                                "Extended redirect has way more advantages and can be used very efficiently with a large hub.\n"+
                                "Extended redirect features:\n"+
                                "   Redirecting users that match a certain regular expression:\n"+
                                "      Example: !redirect \\[RO\\].* [URL]-- this command redirects all users that have their nick starting with [RO]\n" +
                                "      Example: !redirect .. [URL] -- this command redirects all users with 2 letter nicks\n"+
                                "      This type of redirect accepts just any regular expression.\n"+
                                "   Redirecting users that have their fields checked:\n"+
                                "      Example: !redirect share<1024 [URL] --this command just redirects all users with share less then 1 gigabyte.\n"+
                                "      Example: !redirect sl=1 [URL] -- this command redirects all users with exactly one open slot.\n"+
                                        "      Example: !kick su!tcp4 [URL] -- this command redirects all passive users.\n"
                               +"Extended redirect has the operators >, < , =, !\n"+
                                "   And a list of possible fields : share, sl (slots), ni (nick length),su(supports, accepts only = or !, example: !redirect su=tcp4 [URL]),hn(normal hubs count),hr(registered hub count),ho(op hub count),aw(away, 1 means normal away, 2 means extended away),rg (1- registered, 0 otherwise, registered means not op),op ( 1 -op, 0 - otherwise , op means it has key).";
                               cur_client.sendFromBot(Text );
                        return;
                    }
        
        StringTokenizer ST=new StringTokenizer(recvbuf);
        ST.nextToken();
        String what=ST.nextToken();
        String URL="adc://89.33.231.41:1111";//TODO change with variable
        if(ST.hasMoreTokens())
            URL=ST.nextToken();
        
        if(ADC.isCID(what))
        {
            ClientNod aux= ClientNod.FirstClient.NextClient;
            while(aux!=null)
            {
                if(aux.cur_client.userok==1)
                if(aux.cur_client.ID.equalsIgnoreCase(what))
                    break;
                aux=aux.NextClient;
            }
            if(aux==null)
            {
                cur_client.sendFromBot("No user found with given CID.");
                cur_client.sendFromBot("Done.");
            }
            else
            {
                aux.redirectMe(cur_client, URL);
            }
            return;
        }
        else //is a nick
        {
            ClientNod aux= ClientNod.FirstClient.NextClient;
            while(aux!=null)
            {
                if(aux.cur_client.userok==1)
                if(aux.cur_client.NI.equalsIgnoreCase(what))
                    break;
                aux=aux.NextClient;
            }
            if(aux!=null)
            {
                aux.redirectMe(cur_client, URL);
                return;
            }
            
        }
        
            ClientNod aux= ClientNod.FirstClient.NextClient;
            while(aux!=null)
            {
                if(aux.cur_client.userok==1)
                if(aux.cur_client.NI.matches(what))
                {
                    ClientNod test=aux.NextClient;
                    aux.redirectMe(cur_client, URL);
                    aux=test;
                    continue;
                }
                    
                aux=aux.NextClient;
            }
            cur_client.sendFromBot("Done with matching users...");
        
        //done with normal redirect, parsing to field check
        
        
    }
}
