/*
 * ban.java
 *
 * Created on 11 mai 2007, 18:53
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
import java.io.Serializable;

/**
 *
 * @author Pietricica
 */
public class ban implements Serializable
{
    /** 0 -- no ban
     * 1 -- nick ban
     * 2 -- ip ban
     * 3 -- cid ban
     */
    int bantype;
    String banreason;
    String banop;
    /** millis till ban over
     * -1 == permban
     */
    long time;
    
    String nick;
    String ip;
    String cid;
    
    long timeofban;
    
    ban Next;
    
    /** Creates a new instance of ban */
    public ban (int bantype,String whatever,long time,String banop,String banreason)
    {
        this.bantype=bantype;
        nick=cid=ip=null;
        if(bantype==1)
            nick=whatever.toLowerCase ();
        else if(bantype==2)
            ip=whatever;
        else if(bantype==3)
            cid=whatever;
        this.time=time;
        this.banop=banop;
        this.banreason=banreason;
        Next=null;
        timeofban=System.currentTimeMillis ();
    }
    
}
