package dshub;
/*
 * STAError.java
 *
 * Created on 17 martie 2007, 11:14
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

/**
 *
 * @author Pietricica
 */

class STAException extends Exception
{
    STAException()
    {
        super();
    };
    STAException(String bla)
    {
        super(bla);
    }
};

public class STAError
{
    ClientHandler cur_client;
    int error_code;
    String error_desc;
    /** Creates a new instance of STAError */
    public STAError(ClientHandler CH,int ec,String error_d) throws STAException
    {
        cur_client=CH;
        error_code=ec;
        
        error_desc=CommandParser.retADCStr (error_d).replaceAll ("\\\\sTL"," TL");
        String Error_string;
        if(ec==0)
             Error_string="ISTA 000 "+error_desc;
        else
         Error_string="ISTA "+Integer.toString(error_code)+" "+error_desc;
       
        cur_client.sendToClient(Error_string);
        if(ec>200)
        throw new STAException(Error_string);
    }
    public STAError(ClientHandler CH,int ec,String error_d, String FL) throws STAException
    {
        cur_client=CH;
        error_code=ec;
        
        error_desc=CommandParser.retADCStr (error_d);
        
       
        String Error_string="ISTA "+Integer.toString(error_code)+" "+error_desc+" FL" + FL;
         
        cur_client.sendToClient(Error_string);
        if(ec>200)
        throw new STAException(Error_string);
    }
    
}
