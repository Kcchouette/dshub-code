/*
 * BanWordsList.java
 *
 * Created on 30 octombrie 2007, 15:12
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dshub;

/**
 *
 * @author cc
 */

import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;
import java.util.Vector;
import java.io.FileWriter;

public class BanWordsList {
    Vector bannedWords;
    /** Creates a new instance of BanWordsList */
    public BanWordsList() {
        bannedWords=new Vector();
    }
    
    public int size(){
        return bannedWords.size();
    }
    
    public int searchEl(String s){
        ///searches word by name
        ///returns its index
        for (int i=0;i<bannedWords.size();i++){
            BannedWord cuv=(BannedWord)bannedWords.elementAt(i);
            if (s.equals(cuv.cuvant))
                return i;
        };
        return -1;
    }
    
    
    public void modifyStrAt(int index,String s){
        ///modifies the name of the word at index
        BannedWord cuv=(BannedWord)bannedWords.elementAt(index);
        cuv.setWord(s);
    }
    
    public void modifyStr(String s,String news){
        ///replaces word name with another
        int n=searchEl(s);
        if (n==-1){
            System.out.println("The word you want to modify is not in the list");
            return;
        }
        modifyStrAt(n,news);
    }
    
    public void modifyPrAt(int index,long prop,String repl){
        ///modifies propreties at index
        BannedWord cuv=(BannedWord)bannedWords.elementAt(index);
        cuv.setFlags(prop,repl);
    }
    
    public void modifyPr(String s,long prop,String repl){
        ///modifies propreties by name
        int n=searchEl(s);
        if (n==-1){
            System.out.println("The word you want to modify is not in the list");
            return;
        }
        modifyPrAt(n,prop,repl);
    }
    
    public void modifyMultiClientPrAt(int[] list,long prop){
        ///modifies client propreties for multiple selection
        prop=prop&BannedWord.allclient;
     //   System.out.println("%%%%%");
     //   System.out.println("prop:");
      //  System.out.println(prop);
        long curpr;
        for (int i=0;i<list.length;i++){          
            BannedWord cuv=(BannedWord)bannedWords.elementAt(list[i]);
           // System.out.print(list[i]+" ; ");
            curpr=cuv.getFlags();
            String repl=cuv.getReplacement();
         //   System.out.println("curpr:");
         //   System.out.println(curpr);
            curpr = curpr & BannedWord.allword;
          //  System.out.println(curpr);
            curpr = curpr | prop;
           // System.out.println(curpr);
            cuv.setFlags(curpr,repl);
        }
       // System.out.println("&&&&&");
    }
    
    public void modifyMultiWordPrAt(int[] list,long prop,String repl){
        ///modifies word propreties for multiple selection
        prop=prop&BannedWord.allword;
        long curpr;
        for (int i=0;i<list.length;i++){          
            BannedWord cuv=(BannedWord)bannedWords.elementAt(list[i]);
            curpr=cuv.getFlags();
            curpr = curpr & BannedWord.allclient;
            curpr = curpr | prop;
            cuv.setFlags(curpr,repl);
        }
    }
    
    public void add(String s,long proprietati,String replacement){
        ///adds an element at the begining of the list
        int x=searchEl(s);
        if (x==-1){
            BannedWord altCuvant=new BannedWord(s,proprietati,replacement);
            try{
                bannedWords.insertElementAt(altCuvant,0);
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }else{
            BannedWord cuv=(BannedWord)bannedWords.elementAt(x);
            cuv.setFlags(proprietati,replacement);
        }
    }
    
    public void append(String s,long proprietati,String replacement){
        ///adds an element at the end of the list
        int x=searchEl(s);
        if (x==-1){
            BannedWord altCuvant=new BannedWord(s,proprietati,replacement);
            try{
                bannedWords.add(altCuvant);
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }else{
            BannedWord cuv=(BannedWord)bannedWords.elementAt(x);
            cuv.setFlags(proprietati,replacement);
        }
    }
    
    public void clean(){
        ///cleans the list
        bannedWords.removeAllElements();
    }
    
    public void printFile(String path){
        /// prints to file
        FileWriter fo;
        try {
            fo=new FileWriter(path);
        }catch(Exception e){
            System.out.println(e.toString());
            return;
        }
        try{
            fo.write(toString());
            fo.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public void loadFile(String path){
        ///loads list from file
        FileReader fi;
        File f;
        try{
            fi=new FileReader(path);
            f=new File(path);
        }catch(Exception e){
            System.out.println("Invalid File");
            System.out.println(e.toString());
            return;
        }
        if (!f.isFile()){
            System.out.println("The path must be a file");
            return;
        }
        
        char[] buff = new char[(int)f.length()+1];
        System.out.println(f.length());
        int n;
        try{
            n=fi.read(buff);
        }catch(Exception e){
            System.out.println("File not readable");
            System.out.println(e.toString());
            return;
        }
        String buffer=new String(buff);
        
        StringTokenizer st=new StringTokenizer(buffer,";\n\r");
        try{
            while (st.countTokens()>=3){
                //System.out.println("am intrat");
                String cuv;
                String pr;
                long prop;
                String repl;
                cuv=st.nextToken();
                cuv=cuv.substring(1);
                //System.out.println(cuv);
                pr=st.nextToken();
                pr=pr.substring(1);
                prop=Long.parseLong(pr);
                //System.out.println(prop);
                repl=st.nextToken();
                repl=repl.substring(1);
                //System.out.println(repl);
                append(cuv,prop,repl);
            }
        }catch(Exception e){
            System.out.println("Invalid File Format");
            System.out.println(e.toString());
        }
    }
    
    public void removeElAt(int index){
        ///removes word at index
        try{
            bannedWords.removeElementAt(index);
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public void removeElement(String s){
        ///removes word by name
        int i;
        BannedWord cuv;
        try{
            for (i=0;i<bannedWords.size();i++){
                cuv=(BannedWord) bannedWords.elementAt(i);
                if ( s.equals(cuv.getWord()) ){
                    bannedWords.removeElementAt(i);
                    i--;
                }            
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    
    public void removeElements(String[] list){
        ///removes multiple words given by names
        int i;
        for (i=0;i<list.length;i++){
            removeElement(list[i]);
        }
    }
    
    public void removeElementsAt(int[] list){
        ///removes multiple words given by indexes
        int i;
        java.util.Arrays.sort(list);
        for (i=list.length-1;i>=0;i--){
            removeElAt(list[i]);
        }
    }
    
    public String elementAt(int index){
        ///returns the name of the word at index
        BannedWord cuv;
        cuv=(BannedWord)bannedWords.elementAt(index);
        return cuv.getWord();
    }
    
    public long getPrAt(int index){
        ///returns flags at index
        BannedWord cuv;
        cuv=(BannedWord)bannedWords.elementAt(index);
        return cuv.getFlags();
    }
    
    public long getPr(String s){
        int n=searchEl(s);
        if (n==-1){
            System.out.println("The word is not in the list");
            return 0;
        }
        return getPrAt(n);
    }
    
    public String getReplAt(int index){
        ///returns replacement at index
        BannedWord cuv;
        cuv=(BannedWord)bannedWords.elementAt(index);
        return cuv.getReplacement();
    }
    
    public String getRepl(String s){
        ///returns replacement of the word
        int n=searchEl(s);
        if (n==-1){
            System.out.println("The word is not in the list");
            return "";
        }
        return getReplAt(n);
    }
    
    public String toString(){
        ///generates a String version of the vector
        String v="";
        int i;
        for (i=0;i<bannedWords.size();i++){
            try{
                v+= ":"+((BannedWord) bannedWords.elementAt(i)).getWord()+";:"+
                        ((BannedWord) bannedWords.elementAt(i)).getFlags()+";:"+
                        ((BannedWord) bannedWords.elementAt(i)).getReplacement()+"\n";
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        return v;
    }
    
}
