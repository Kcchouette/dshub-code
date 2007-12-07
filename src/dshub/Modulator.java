/*
 * Modulator.java
 *
 * Created on 06 decembrie 2007, 19:27
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



import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.NoClassDefFoundError;
import dshub.Modules.DSHubModule;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Pietricica
 */
public class Modulator
{
    
    public static boolean hubtrackerModule;
    
    /** Creates a new instance of Modulator */
    public Modulator()
    {
       
        
    }
    
    static LinkedList<DSHubModule> myModules;
    static
    {
        myModules=new LinkedList();
    }
    
    public static void findModules()
    { 
        File curPath=new File(Main.myPath+"/modules");
    
            File [] Modules=curPath.listFiles();
        
 
        
            if(Modules!=null)
            for(File myJar: Modules)
            {
        try
        {
           
            // Set up a URLClassLoader with the new Jar file in it.
           
         URLClassLoader loader = null;
         try {
             ClassLoader parentLoader = Modulator.class.getClassLoader();
             loader = new URLClassLoader(
                 new URL[] { 
                 myJar.toURI().toURL() 
             }, parentLoader);
         } catch (MalformedURLException murle) {
             murle.printStackTrace();
         }
 
         // Grab the resource out of the jar.
         
        
            Class x= loader.loadClass("dshub.plugin.PluginMain");
         DSHubModule y=(DSHubModule) x.newInstance();
         boolean moduleOK=y.startup();
 
         if(moduleOK)
         {
             myModules.add(y);
         }
        
       
        
           
            
        }
        catch(NoClassDefFoundError e)
        {
            //System.out.println("hubtracker not loaded");
            hubtrackerModule=false;
            
            
        }
        catch(ClassNotFoundException cnfe)
        {
            hubtrackerModule=false;
           
        }
        catch(Exception e)
        {
            hubtrackerModule=false;
            
        }
        }
    }
    
}
