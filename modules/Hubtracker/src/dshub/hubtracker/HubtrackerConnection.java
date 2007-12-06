/*
 * HubtrackerConnection.java
 *
 * Created on 29 noiembrie 2007, 17:33
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy;

/**
 *
 * @author Pietricica
 */
public class HubtrackerConnection extends Thread
{
    String user,e_mail;
    /** Creates a new instance of HubtrackerConnection */
    public HubtrackerConnection(String user, String e_mail)
    {
        this.user=user;
        this.e_mail=e_mail;
        start();
    }
    public void run ()
    {
        BufferedReader inp = null;
        try {
          String urlString = "http://www.hubtracker.com/query.php?action=add&username="+user+"&email="+e_mail+"&address=testy.bla.ro:1411";
          URL url = new URL(urlString);
          
          URLConnection conn = url.openConnection(new Proxy(Proxy.Type.HTTP,new InetSocketAddress("89.33.224.1",3128)));
          conn.setDoInput(true); // or 
          conn.setDoOutput(true);
         
          
          /* really open connection */
          conn.connect(); // establish connection
          
          inp = new BufferedReader(
                  new InputStreamReader(conn.getInputStream()));
          String result ;
          while((result= inp.readLine())!=null)
          System.out.println(result);
          inp.close(); 
          inp = null;
        }
        catch (MalformedURLException ue) 
        {
            System.out.println(ue);
        }
        catch (Exception e)
        {
        System.out.println(e);
        }
        
    }
    
}
