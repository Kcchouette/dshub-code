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
                    {
                        modnod=reg_config.getnod(temp.ID);
                        break;
                    }
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
        String what=curcmd.nextToken();
        String aux=what;
        cur_client.sendFromBot("Editing Account: "+modnod.CID+"\n");
    while(aux!=null)    
    {
        what=aux;
        if(!what.startsWith("+") && !what.startsWith("-"))
        {
            cur_client.sendFromBot("Invalid argument supplied. Use with no arguments to see usage tipx.");
                return;
            
        }
        boolean attribute=false;
        if(what.startsWith("+"))
            attribute=true;
            
        what=what.substring(1);
        int x=what.indexOf('+');
        int y=what.indexOf('-');
        int z=0;
        if(x<0 && y<0)
            z=what.length();//over
        else 
          if(x>0 && y>0)
          {
            if(x>y)
                z=y;
            else z=x;
          }
          else if(x>0)
              z=x;
          else
              z=y;
        
        
            what=what.substring(0,z);
            aux=aux.substring(z+1);
            
        if(what.equalsIgnoreCase("adc"))
        {
              cur_client.reg.myMask.adc=attribute;
              cur_client.sendFromBot(" adc modified to "+attribute);
        }
            
        else  if(what.equals("about"))
             {
                if(cur_client.reg.myMask.about==false)
                {
                    cur_client.sendFromBot("about - can't grant a feature you don't possess.");
                    return;
                }
              modnod.myMask.about=attribute;
              cur_client.sendFromBot(" about modified to "+attribute);
             }
            else  if(what.equals("bancid"))
             {
                if(cur_client.reg.myMask.bancid==false)
                {
                    cur_client.sendFromBot("bancid - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.bancid=attribute;
              cur_client.sendFromBot(" bancid modified to "+attribute);
             }
            else  if(what.equals("banip"))
             {
                if(cur_client.reg.myMask.banip==false)
                {
                    cur_client.sendFromBot("banip - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.banip=attribute;
              cur_client.sendFromBot(" banip modified to "+attribute);
             }
             else  if(what.equals("bannick"))
             {
                if(cur_client.reg.myMask.bannick==false)
                {
                    cur_client.sendFromBot("bannick - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.bannick=attribute;
              cur_client.sendFromBot(" bannick modified to "+attribute);
             }
             else  if(what.equals("cfg"))
             {
                if(cur_client.reg.myMask.cfg==false)
                {
                    cur_client.sendFromBot("cfg - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.cfg=attribute;
              cur_client.sendFromBot(" cfg modified to "+attribute);
             }
             else  if(what.equals("cmdhistory"))
             {
                if(cur_client.reg.myMask.cmdhistory==false)
                {
                    cur_client.sendFromBot("cmdhistory - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.cmdhistory=attribute;
              cur_client.sendFromBot(" cmdhistory modified to "+attribute);
             }
             else  if(what.equals("drop"))
             {
                if(cur_client.reg.myMask.drop==false)
                {
                    cur_client.sendFromBot("drop - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.drop=attribute;
              cur_client.sendFromBot(" drop modified to "+attribute);
             }
            else  if(what.equals("grant"))
             {
                if(cur_client.reg.myMask.grant==false)
                {
                    cur_client.sendFromBot("grant - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.drop=attribute;
              cur_client.sendFromBot(" grant modified to "+attribute);
             }
             else  if(what.equals("gui"))
             {
                if(cur_client.reg.myMask.gui==false)
                {
                    cur_client.sendFromBot("gui - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.gui=attribute;
              cur_client.sendFromBot(" gui modified to "+attribute);
             }
             else  if(what.equals("help"))
             {
                if(cur_client.reg.myMask.help==false)
                {
                    cur_client.sendFromBot("help - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.help=attribute;
              cur_client.sendFromBot(" help modified to "+attribute);
             }
            else  if(what.equals("hideme"))
             {
                if(cur_client.reg.myMask.hideme==false)
                {
                    cur_client.sendFromBot("hideme - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.hideme=attribute;
              cur_client.sendFromBot(" hideme modified to "+attribute);
             }
            else  if(what.equals("history"))
             {
                if(cur_client.reg.myMask.history==false)
                {
                    cur_client.sendFromBot("history - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.history=attribute;
              cur_client.sendFromBot(" history modified to "+attribute);
             }
            else  if(what.equals("info"))
             {
                if(cur_client.reg.myMask.info==false)
                {
                    cur_client.sendFromBot("info - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.info=attribute;
              cur_client.sendFromBot(" info modified to "+attribute);
             }
            else  if(what.equals("kick"))
             {
                if(cur_client.reg.myMask.kick==false)
                {
                    cur_client.sendFromBot("kick - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.kick=attribute;
              cur_client.sendFromBot(" kick modified to "+attribute);
             }
            else  if(what.equals("help"))
             {
                if(cur_client.reg.myMask.help==false)
                {
                    cur_client.sendFromBot("help - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.help=attribute;
              cur_client.sendFromBot(" help modified to "+attribute);
             }
            else  if(what.equals("listban"))
             {
                if(cur_client.reg.myMask.listban==false)
                {
                    cur_client.sendFromBot("listban - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.listban=attribute;
              cur_client.sendFromBot(" listban modified to "+attribute);
             }
            else  if(what.equals("listreg"))
             {
                if(cur_client.reg.myMask.listreg==false)
                {
                    cur_client.sendFromBot("listreg - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.listreg=attribute;
              cur_client.sendFromBot(" listreg modified to "+attribute);
             }
            else  if(what.equals("mass"))
             {
                if(cur_client.reg.myMask.mass==false)
                {
                    cur_client.sendFromBot("mass - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.mass=attribute;
              cur_client.sendFromBot(" mynick modified to "+attribute);
             }
            else  if(what.equals("mynick"))
             {
                if(cur_client.reg.myMask.mynick==false)
                {
                    cur_client.sendFromBot("mynick - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.mynick=attribute;
              cur_client.sendFromBot(" mynick modified to "+attribute);
             }
           
            else  if(what.equals("password"))
             {
                if(cur_client.reg.myMask.password==false)
                {
                    cur_client.sendFromBot("password - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.password=attribute;
              cur_client.sendFromBot(" password modified to "+attribute);
             }
            else  if(what.equals("port"))
             {
                if(cur_client.reg.myMask.port==false)
                {
                    cur_client.sendFromBot("port - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.port=attribute;
              cur_client.sendFromBot(" port modified to "+attribute);
             }
            else  if(what.equals("quit"))
             {
                if(cur_client.reg.myMask.quit==false)
                {
                    cur_client.sendFromBot("quit - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.quit=attribute;
              cur_client.sendFromBot(" quit modified to "+attribute);
             }
            else  if(what.equals("reg"))
             {
                if(cur_client.reg.myMask.reg==false)
                {
                    cur_client.sendFromBot("reg - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.reg=attribute;
              cur_client.sendFromBot(" reg modified to "+attribute);
             }
            else  if(what.equals("rename"))
             {
                if(cur_client.reg.myMask.rename==false)
                {
                    cur_client.sendFromBot("rename - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.rename=attribute;
              cur_client.sendFromBot(" rename modified to "+attribute);
             }
            else  if(what.equals("restart"))
             {
                if(cur_client.reg.myMask.restart==false)
                {
                    cur_client.sendFromBot("restart - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.restart=attribute;
              cur_client.sendFromBot(" restart modified to "+attribute);
             }
            else  if(what.equals("stats"))
             {
                if(cur_client.reg.myMask.stats==false)
                {
                    cur_client.sendFromBot("stats - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.stats=attribute;
              cur_client.sendFromBot(" stats modified to "+attribute);
             }
            else  if(what.equals("help"))
             {
                if(cur_client.reg.myMask.help==false)
                {
                    cur_client.sendFromBot("help - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.help=attribute;
              cur_client.sendFromBot(" help modified to "+attribute);
             }
            else  if(what.equals("topic"))
             {
                if(cur_client.reg.myMask.topic==false)
                {
                    cur_client.sendFromBot("topic - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.topic=attribute;
              cur_client.sendFromBot(" topic modified to "+attribute);
             }
            else  if(what.equals("unban"))
             {
                if(cur_client.reg.myMask.unban==false)
                {
                    cur_client.sendFromBot("unban - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.unban=attribute;
              cur_client.sendFromBot(" unban modified to "+attribute);
             }
            else  if(what.equals("ureg"))
             {
                if(cur_client.reg.myMask.ureg==false)
                {
                    cur_client.sendFromBot("ureg - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.ureg=attribute;
              cur_client.sendFromBot(" ureg modified to "+attribute);
             }
            else  if(what.equals("usercount"))
             {
                if(cur_client.reg.myMask.usercount==false)
                {
                    cur_client.sendFromBot("usercount - can't grant a feature you don't possess.");
                    return;
                }
               modnod.myMask.usercount=attribute;
              cur_client.sendFromBot(" usercount modified to "+attribute);
             }
            
        if(x<0 && y<0)
            break;//over
    }
            
        
    }
    
}
