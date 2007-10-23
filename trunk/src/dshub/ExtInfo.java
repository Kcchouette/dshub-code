/*
 * ExtInfo.java
 *
 * Created on 10 septembrie 2007, 22:41
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
 * The client Info command, also with extended call.
 *
 * @author Pietricica
 */
public class ExtInfo
{
    
    /** Creates a new instance of ExtInfo */
    public ExtInfo (ClientHandler cur_client,String recvbuf)
    {
                StringTokenizer ST=new StringTokenizer(recvbuf);
                ST.nextToken ();
                String aux=ST.nextToken (); //the thing to check;
                while(ST.hasMoreTokens ())
                    aux+=ST.nextToken ();
                aux=ADC.retADCStr(aux);
                ClientHandler temp=ClientHandler.FirstClient.NextClient;
              
                if(ADC.isIP(aux))//we have an IP address
                {
                    //ClientHandler temp=ClientHandler.FirstClient.NextClient;
              String Nicklist="";
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ClientSock.getInetAddress().getHostAddress().equals(aux.toLowerCase ())))
                                Nicklist=Nicklist+temp.NI+"\n";
                            temp=temp.NextClient;
                            
                        }
               if(!Nicklist.equals (""))
                  cur_client.sendFromBot("Users with IP "+aux+" :\n"+Nicklist.substring(0,Nicklist.length()-1));
               else 
                  cur_client.sendFromBot("No user with IP "+aux);
               
                }
                else
                {
                
                    //ok now lets see if its a valid CID
                    if(aux.length ()==39)
                    {
                        try
                        {
                            Base32.decode (aux);
                            //ok if we are here, its a CID
                             temp=ClientHandler.FirstClient.NextClient;
             
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.ID.equals(aux)))
                                break;
                            temp=temp.NextClient;
                            
                        }
               if(temp!=null)
                  cur_client.sendFromBot("CID "+aux+" is used by:\n"+temp.NI);
               else 
                  cur_client.sendFromBot("Nobody is using "+aux);
                            return;
                        }
                        catch (IllegalArgumentException e)
                        {
                        //its a nick though...
                        }
                    }
                    
                    
                    
                temp=ClientHandler.FirstClient.NextClient;
                while(temp!=null)
                        {
                            if(temp.userok==1) if( (temp.NI.toLowerCase ().equals(aux.toLowerCase ())))
                                break;
                            temp=temp.NextClient;
                            
                        }
               if(temp==null)
                cur_client.sendFromBot("No such user online.");
               else
               {
                    
                    String blah11="User Info\nNick "+ADC.retNormStr(temp.NI)+"\nCID "+temp.ID+"\nShare Size "+temp.SS+ " Bytes\n"+
                            "Description "+(temp.DE!=null?ADC.retNormStr(temp.DE):"")+"\nTag ";
                    
                    String Tag="<"+ADC.retNormStr (temp.VE)+",M:";
                    if(temp.ACTIVE==1)
                        Tag=Tag+"A";
                    else Tag=Tag+"P";
                    Tag=Tag+",H:"+temp.HN+"/";
                    if(temp.HR!=null)
                                Tag=Tag+temp.HR+"/";
                    else Tag=Tag+"?";
                    if(temp.HO!=null)
                                Tag=Tag+temp.HO;
                    else Tag=Tag+"?";
                            
                            Tag=Tag+",S:";
                    if(temp.SL!=null)
                            
                            Tag=Tag+temp.SL+">";
                    else
                        Tag=Tag+"?>";
                            blah11=blah11+Tag+"\nSupports "
                                    + ((temp.SU!=null) ? temp.SU : "nothing special")+"\nIp address "+temp.ClientSock.getInetAddress().getHostAddress();
                     if(temp.reg.isreg)   
                     {
                                
                            blah11=blah11+temp.reg.getRegInfo();
                     }
                    
                     else
                         blah11=blah11+"\nNormal user.";
                      cur_client.sendFromBot(""+blah11);
               }
                }
    }
    
}
