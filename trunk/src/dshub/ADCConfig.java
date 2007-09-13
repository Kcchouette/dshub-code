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
 * @author Pietricica
 */
public class ADCConfig 
{
    ClientHandler cur_client;
    //String msg;
    /** Creates a new instance of ADCConfig */
    public ADCConfig(ClientHandler CH,String msg) 
    {
        cur_client=CH;
        //this.msg=msg;
        msg=ADC.retNormStr(msg.substring(1));
        StringTokenizer TK=new StringTokenizer(msg);
        
        TK.nextToken();
        if(!TK.hasMoreTokens())
        {
            cur_client.sendFromBot("\nADC Advanced Configuration Settings.\n"+
                    "---------------------------------------------------------------------------\n"+
                    "To modify a value use \" adc _context__name_ on/off \"\n"+
                    "Example : \"adc bmsg off\", where \"b\" is the context,\n"+
                    "\"msg\" is the name and off is the specifier of what to do.\n"+
                    "Current Settings : \n"+
            " MSG :     STA:     CTM:      RCM:     INF:       SCH:       RES:      PAS:      SUP:\n"+
                    "B "+ (Vars.BMSG==1 ? "on":"off")+  "        B "+ (Vars.BSTA==1 ? "on":"off")+ 
                    "       B "+ (Vars.BCTM==1 ? "on":"off")+"       B "+ (Vars.BRCM==1 ? "on":"off")+
                    "       B "+ (Vars.BINF==1 ? "on":"off")+"       B "+ (Vars.BSCH==1 ? "on":"off")+
                    "       B "+ (Vars.BRES==1 ? "on":"off")+"       B "+ (Vars.BPAS==1 ? "on":"off")+
                    "       B "+ (Vars.BSUP==1 ? "on":"off")+
                    
                    "\n"+
                    
                    "D "+ (Vars.DMSG==1 ? "on":"off")+  "        D "+ (Vars.DSTA==1 ? "on":"off")+ 
                    "      D "+ (Vars.DCTM==1 ? "on":"off")+"       D "+ (Vars.DRCM==1 ? "on":"off")+
                    "       D "+ (Vars.DINF==1 ? "on":"off")+"      D "+ (Vars.DSCH==1 ? "on":"off")+
                    "       D "+ (Vars.DRES==1 ? "on":"off")+"      D "+ (Vars.DPAS==1 ? "on":"off")+
                    "       D "+ (Vars.DSUP==1 ? "on":"off")+
                    "\n"+
                    
                    "E "+ (Vars.EMSG==1 ? "on":"off")+  "        E "+ (Vars.ESTA==1 ? "on":"off")+
                    
                    "\n"+
                    "F "+ (Vars.FMSG==1 ? "on":"off")+  "        F "+ (Vars.FSTA==1 ? "on":"off")+
                    
                    "\n"+
                    "H "+ (Vars.HMSG==1 ? "on":"off") + "        H "+ (Vars.HSTA==1 ? "on":"off") +
                    ""
                    
                    
                    );
            return;
        }
        String nameValue=TK.nextToken();
        if(!TK.hasMoreTokens())
        {
            String tempStr="\nADC Advanced Configuration Settings.\n"+
                    "---------------------------------------------------------------------------\n  "+
                    nameValue.toUpperCase()+" is currently ";
            if(nameValue.equalsIgnoreCase("BMSG"))
                tempStr+=(Vars.BMSG==1 ? "on." : "off.");
            if(nameValue.equalsIgnoreCase("DMSG"))
                tempStr+=Vars.DMSG==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("EMSG"))
                tempStr+=Vars.EMSG==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("FMSG"))
                tempStr+=Vars.FMSG==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("HMSG"))
                tempStr+=Vars.HMSG==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("BSTA"))
                tempStr+=(Vars.BSTA==1 ? "on." : "off.");
            if(nameValue.equalsIgnoreCase("DSTA"))
                tempStr+=Vars.DSTA==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("ESTA"))
                tempStr+=Vars.ESTA==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("FSTA"))
                tempStr+=Vars.FSTA==1 ? "on." : "off.";
            if(nameValue.equalsIgnoreCase("HSTA"))
                tempStr+=Vars.HSTA==1 ? "on." : "off.";
            cur_client.sendFromBot(tempStr);
            return;
        }
        String Specifier=TK.nextToken();
        String tempStr="\nADC Advanced Configuration Settings.\n"+
                    "---------------------------------------------------------------------------\n  "+
                    "Setting "+nameValue.toUpperCase();
            if(nameValue.equalsIgnoreCase("BMSG"))
            {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.BMSG=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.BMSG=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
            }
        else if(nameValue.equalsIgnoreCase("DMSG"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.DMSG=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.DMSG=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("EMSG"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.EMSG=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.EMSG=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("FMSG"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.FMSG=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.FMSG=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("HMSG"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.HMSG=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.HMSG=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("BSTA"))
            {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.BSTA=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.BSTA=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
            }
        else if(nameValue.equalsIgnoreCase("DSTA"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.DSTA=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.DSTA=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("ESTA"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.ESTA=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.ESTA=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("FSTA"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.FSTA=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.FSTA=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
        else if(nameValue.equalsIgnoreCase("HSTA"))
        {
            if (Specifier.equalsIgnoreCase("on"))
            {
                Vars.HSTA=1;
                tempStr+=" on.";
            }
            else if(Specifier.equalsIgnoreCase("off"))
            {
                Vars.HSTA=0;
                tempStr+=" off.";
            }
            else
            {
                cur_client.sendFromBot("Invalid Specifier.");
                return;
            }
        }
                
            
            
            cur_client.sendFromBot(tempStr);
        
    }
    
}
