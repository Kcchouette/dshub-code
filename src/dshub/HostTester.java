/*
 * 
 * PluginCmd.java
 *
 * Created on 16 decembrie 2007, 20:10
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Pietricica
 */
public class HostTester 
{
    
    
    public static boolean hostOK(String Host)
    {
        try
        {
            Socket testS = new Socket();
            testS.connect(new InetSocketAddress(Host, Vars.Default_Port),2*1000);
           
            BufferedReader in = new BufferedReader(new InputStreamReader(testS.getInputStream()));
            PrintStream out=new PrintStream(testS.getOutputStream());
            out.println("HSUP ADBASE");
            String SUP=in.readLine();
            if(!SUP.equals(ADC.Init))
                return false;
            in.readLine();
            String INF=in.readLine();
            if(!INF.equals("IINF HU1 HI1 VE"+ADC.retADCStr(Vars.HubVersion)+" NI"+ADC.retADCStr(Vars.HubName)))
               return false;
            
            in.close();
            out.close();
            testS.close();
            

            
        } catch (UnknownHostException ex)
        {
            //Logger.getLogger(HostTester.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            return false;
        } catch (IOException ex)
        {
           // Logger.getLogger(HostTester.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
          return false;
        }
        
        catch ( Exception e)
        {
            e.printStackTrace();
           return false;
        }
        return true;
    }
}
