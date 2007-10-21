/*
 * RegConfig.java
 *
 * Created on 28 aprilie 2007, 12:49
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
import java.util.Date;
 class nod implements Serializable,Cloneable
 {
        String CID;
        String Password;
        boolean key;
        //ClientHandler CH;
        nod Next;
        boolean isreg;
        String LastNI;
        String WhoRegged;
        Long CreatedOn;
        Long LastLogin;
        Long TimeOnline;
        String LastIP;
        
        boolean HideShare;
        boolean HideMe;
        
        boolean overrideshare;
        boolean overridespam;
        boolean overridefull;
        boolean kickable,renameable;
        boolean accountflyable;
        
        CommandMask myMask;
        HelpFile myHelp;
        boolean nickprotected;
        
        nod()
        {
            CID=null;
            Password="";
            Next=null;
            key=false;
            
            isreg=false;
            LastNI=null;
            LastIP=null;
            HideShare=false;
            HideMe=false;
            WhoRegged=null;
            overrideshare=overridespam=false;
            overridefull=false;
            accountflyable=false;
            kickable=renameable=true;
            CreatedOn=0L;
            LastLogin=0L;
            TimeOnline=0L;
            myMask=new CommandMask();
            myHelp=new HelpFile(this);
            nickprotected=true;
        }
      protected  nod  clone()
        {
            try 
            {         
                return (nod) super.clone();
            } catch(CloneNotSupportedException e) 
            {
                System.out.println("Cloning not allowed.");
                 return this;
            }
  }
      
      public  String getRegInfo()
     {
         String retString="";
         if(this.key)   
           {
                    retString="\nOperator.";
         }
         else
         {
             retString="\nRegistered.";
         }
         Date d=new Date(this.CreatedOn);
           retString=retString+" Reg Info:\nLast Nick : "+this.LastNI+"\nLast IP: "+this.LastIP+"\nRegged by: "+this.WhoRegged+" on "+d.toString ();
           retString+="\nLast LogIn: "+new Date(this.LastLogin).toString()+"\nTime Online: "+TimeConv.getStrTime(this.TimeOnline)+"\nOverride share restrictions? "+(this.overrideshare ? "yes" : "no")
           +"\nOverride spam settings? "+(this.overridespam? "yes":"no")+"\nCan be renamed? "+(this.renameable?"yes":"no")+
                   "\nPassword set? "+(this.Password.length ()>0?"yes":"no")+"\nIs hidden? "+(this.HideMe?"yes":"no")+
                   "\nShare hidden? "+(this.HideShare?"yes":"no")+"\nFlyable? "+(this.HideShare?"yes":"no")+"\n"+
                   "Last nick protected? "+(this.HideShare?"yes":"no")+"\n"+
                   "---------------------Profile----------------------\n";
           String Help=retString;
           nod curAcc=this;
                   if(this.myMask.about)
            Help+="+about";
                   else
                       Help+="-about";
           
        if(curAcc.myMask.adc)
            Help+="+adc";
        else
            Help+="-adc";
        if(curAcc.myMask.bancid)
            Help+="+bancid";
        else
            Help+="-bancid";
        if(curAcc.myMask.banip)
            Help+="+banip";
        else
            Help+="-banip";
        if(curAcc.myMask.bannick)
            Help+="+bannick";
        else
            Help+="-bannick";
        if(curAcc.myMask.cfg)
            Help+="+cfg";
        else
            Help+="-cfg";
        if(curAcc.myMask.cmdhistory)
            Help+="+cmdhistory";
        else
            Help+="-cmdhistory";
        if(curAcc.myMask.drop)
            Help+="+drop";
        else
            Help+="-drop";
           if(curAcc.myMask.grant)
            Help+="+grant";
        else
            Help+="-grant";
        if(curAcc.myMask.gui)    
            Help+="+gui";
        else
            Help+="-gui";
        if(curAcc.myMask.help)
            Help+="+help";
        else
            Help+="-help";
        if(curAcc.myMask.hideme)
            Help+="+hideme";
        else
            Help+="-hideme";
        if(curAcc.myMask.history)
            Help+="+history";
        else
            Help+="-history";
        if(curAcc.myMask.info)
            Help+="+info";
        else
            Help+="-info";
        if(curAcc.myMask.kick)
            Help+="+kick";
        else
            Help+="-kick";
        if(curAcc.myMask.listban)
            Help+="+listban";
        else
            Help+="-listban";
        if(curAcc.myMask.listreg)
            Help+="+listreg";
        else
            Help+="-listreg";
        if(curAcc.myMask.mass)
            Help+="+mass";
        else
            Help+="-mass";
        if(curAcc.myMask.mynick)
            Help+="+mynick";
        else
            Help+="-mynick";
        if(curAcc.myMask.password)
            Help+="+password";
        else
            Help+="-password";
        if(curAcc.myMask.port)
            Help+="+port";
        else
            Help+="-port";
        if(curAcc.myMask.quit)
            Help+="+quit";
        else
            Help+="-quit";
        if(curAcc.myMask.reg)
            Help+="+reg";
        else
            Help+="-reg";
        if(curAcc.myMask.rename)
            Help+="+rename";
        else
            Help+="-rename";
        if(curAcc.myMask.restart)
            Help+="+restart";
        else
            Help+="-restart";
        if(curAcc.myMask.stats)
            Help+="+stats";
        else
            Help+="-stats";
        if(curAcc.myMask.topic)
            Help+="+topic";
        else
            Help+="-topic";
        if(curAcc.myMask.unban)
            Help+="+unban";
        else
            Help+="-unban";
        if(curAcc.myMask.ureg)
            Help+="+ureg";
        else
            Help+="-ureg";
        if(curAcc.myMask.usercount)
            Help+="+usercount";
        else
            Help+="-usercount";
        ;
        if(curAcc.accountflyable)
            Help+="+flyable";
        else
            Help+="-flyable";
        if(curAcc.key)
            Help+="+key";
        else
            Help+="-key";
        if(curAcc.kickable)
            Help+="+kickable";
        else
            Help+="-kickable";
        if(curAcc.nickprotected)
            Help+="+nickprotected";
        else
            Help+="-nickprotected";
        if(curAcc.overridefull)
            Help+="+overridefull";
        else
            Help+="-overridefull";
        if(curAcc.overrideshare)
            Help+="+overrideshare";
        else
            Help+="-overrideshare";
        if(curAcc.overridespam)
            Help+="+overridespam";
        else
            Help+="-overridespam";
        if(curAcc.renameable)
            Help+="+renameable";
        else
            Help+="-renameable";
                  
           
           return Help;
                    
                            
         
     }
      public boolean setFlyable(boolean x)
      {
          if(x)
          {
              if(this.Password.length ()<1)
                  return false;
              this.accountflyable=true;
          }
          else
              this.accountflyable=false;
          return true;
      }
        
  }
        
    

/**
 *
 * @author Pietricica
 */
public class RegConfig implements Serializable
{
    
    int reg_count;
    
    nod [] nods;
    
    public RegConfig ()
    {
        nods= new nod[100];
        reg_count=reg_config.reg_count;
       
        if(reg_config.First==null)
            return ;
        nod temp=reg_config.First;
        int i=1;
        while(temp!=null)
        {
            //nods[reg_count]=new nod();
            nods[i]=temp;
            i++;
            temp=temp.Next;
        }
        
        
    }
    
    
}

class reg_config 
{
    static int reg_count=1;
    
    static nod First=null;
    /** Creates a new instance of RegConfig */
      public reg_config ()
    {
        reg_count=1;
        First=null;
    }
    
   static public void addReg(String CID,String LastNI,String WhoRegged)
    {
         nod newreg;
         newreg=new nod();
        newreg.CID=CID;
        newreg.Password="";
        newreg.key=false;
        newreg.isreg=true;
        newreg.LastNI=LastNI;
        newreg.WhoRegged=WhoRegged;
        newreg.CreatedOn=System.currentTimeMillis ();
        if(First==null)
            First=newreg;
        else
        {
            newreg.Next=First;
            First=newreg;
        }
        reg_count++;
        
    }
   
   static public void addReg(nod n)
    {
         nod newreg;
         //newreg=new nod();
        newreg=n;
        if(n==null)
            return;
        
        if(First==null)
        {
            First=newreg;
            newreg.Next=null;
        }
        else
        {
            newreg.Next=First;
            First=newreg;
        }
        reg_count++;
        
    }
    static public int isReg(String CID)
    {
        if(First==null)
            return 0;
        nod temp=First;
        while(temp!=null)
        {
            if(temp.CID.equals (CID))
                if(temp.key)
                return 2;
                else return 1;
            temp=temp.Next;
        }
        return 0;
    }
    static public nod getnod(String CID)
    {
         if(First==null)
            return null;
        nod temp=First;
        while(temp!=null)
        {
            if(temp.CID.equals (CID))
                return temp;
            temp=temp.Next;
        }
        return null;
    }
    static public boolean unreg(String CID)
    {
        if(First==null)
            return false;
        nod temp=First;
        if(First.CID.equals (CID))
        {
            
            First=First.Next;
            temp.Next=null;
            return true;
        }
       
        while(temp.Next!=null && !temp.Next.CID.equals (CID)  )
        {
            temp=temp.Next;
        }
        if(temp.Next==null)
            return false;
        temp.Next=temp.Next.Next;
        reg_count--;
        return true;
    }
    
    static public boolean nickReserved(String nick,String CID)
    {
     nod x= reg_config.First;
        while(x!=null)
        {
         if(!x.CID.equals(CID))
            if(x.nickprotected && x.LastNI.equalsIgnoreCase(nick))
                return true;
            x=x.Next;
        }
     return false;
    }
}