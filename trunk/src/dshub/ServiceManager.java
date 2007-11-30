/*
 * ServiceManager.java
 *
 * Created on 12 noiembrie 2007, 12:37
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

import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Set;
import org.apache.mina.common.IoService;
import org.apache.mina.common.IoSession;
import org.apache.mina.integration.jmx.IoServiceManager;
import org.apache.mina.management.StatCollector;

/**
 * A class that manages statistics on all open sessions
 *
 * @author Pietricica
 */
public class ServiceManager 
{
    private IoService service;

    

    /** Creates a new instance of ServiceManager */
    public ServiceManager(IoService service)
    {
        
        this.service=service;
    }
    
    public Set<IoSession> getSessions() 
    {
     Set<IoSession> mySessions=null;
        
        for (Iterator iter = service.getManagedServiceAddresses().iterator(); iter
                .hasNext();) 
        {
            SocketAddress element = (SocketAddress) iter.next();
        if(mySessions==null)
                  mySessions=service.getManagedSessions(element);
        else
            mySessions.addAll(service.getManagedSessions(element));
        }
        return mySessions;
    }

    
}
