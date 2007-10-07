/**
 * BanList.java
 *
 * Created on 11 mai 2007, 18:59
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
 * 
 */

package dshub;
import java.io.Serializable;

/**
 *
 * @author Pietricica
 */

class bans implements Serializable
{
      
    
    ban [] bans;
    int i;
    public bans ()
    {
        bans= new ban[1000];
        
       
        if(BanList.First==null)
            return ;
        ban temp=BanList.First;
        i=1;
        while(temp!=null)
        {
            if(System.currentTimeMillis()-temp.timeofban-temp.time<0 || temp.time==-1)
            {
            bans[i]=temp;
            
            i++;
            }
            temp=temp.Next;
        }
        
        
    }
}
public class  BanList
{
    
    
    static ban First;
    /** Creates a new instance of BanList */
    public BanList ()
    {
        First=null;
    }
    /**
     *@param bantype ==1 nick ban
     *bantype==2 ip ban
     *bantype==3 cid ban
     */
    static public void addban(int bantype,String whatever,long time,String banop,String reason)
    {
        
        ban test=getban(bantype,whatever);
        if(test!=null)
        {
            test.banop=banop;
            test.time=time;
            test.timeofban=System.currentTimeMillis ();
            test.banreason=reason;
        }
        else
        {
        ban BAN=new ban( bantype, whatever, time, banop,reason);
        if(First==null)
            First=BAN;
        else
        {
            BAN.Next=First.Next;
            First.Next=BAN;
        }
        }
    }
    static public void addban(ban BAN)
    {
        
        if(First==null)
            First=BAN;
        else
        {
            
            BAN.Next=First.Next;
            First.Next=BAN;
        }
    }
    static public boolean delban(int bantype,String whatever)
    {
        
        if(First==null)
            return false;
        ban tempy=First;
        ban tempyprev=null;
        while(tempy!=null)
        {
            if(tempy.bantype==bantype)
            
                if(bantype==1 && whatever.equals (tempy.nick))
                {
                  if(tempy==First)
                      First=tempy.Next;//deleted
                  else
                      tempyprev.Next=tempy.Next;//deleted
                  return true;
                }
                    
                else if(bantype==2 && whatever.equals (tempy.ip))
                {
                if(tempy==First)
                      First=tempy.Next;//deleted
                  else
                      tempyprev.Next=tempy.Next;//deleted
                    return true;
                }
                else if(bantype==3 && whatever.equals (tempy.cid))
                {
                if(tempy==First)
                      First=tempy.Next;//deleted
                  else
                      tempyprev.Next=tempy.Next;//deleted
                return true;
                }
                    
            tempyprev=tempy;
            tempy=tempy.Next;
            
        }
        return false;
    }
    static public ban getban(int bantype,String whatever)
    {
        ban tempy=First;
        while(tempy!=null)
        {
            if(tempy.bantype==bantype)
            
                if(bantype==1 && whatever.toLowerCase ().equals (tempy.nick))
                    return tempy;
                else if(bantype==2 && whatever.equals (tempy.ip))
                    return tempy;
                else if(bantype==3 && whatever.equals (tempy.cid))
                    return tempy;
            tempy=tempy.Next;
        }
        return null;
    }
    
}