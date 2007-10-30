/*
 * BannedWord.java
 *
 * Created on 30 octombrie 2007, 15:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package dshub;

import java.util.BitSet;
/**
 *
 * @author cc
 */
public class BannedWord {
    String cuvant;
    long proprietati;
    String replacement;
    /**
     *cuvant        -- banned word
     *proprietati   -- flags for propreties
     *                  0 - drop
     *                  1 - kick
     *                  2 - no action
     *                  3 - hide
     *                  4 - replace with *
     *                  5 - modify
     *replacement   -- the replacement for the banned word (ifcase)
     */
    
    static final long dropped=1;
    static final long kicked=2;
    static final long noAction=4;
    static final long hidden=8;
    static final long replaced=16;
    static final long modified=32;
    static final long allclient=7;
    static final long allword=56;
    /** Creates a new instance of BannedWord */
    
    
    public BannedWord(String ccuvant,long prop,String repl) {
        cuvant =new String(ccuvant);
        proprietati=prop;
        replacement=new String(repl);
    }
    
    
    public void setFlags(long prop,String repl){
        proprietati=prop;        
        replacement = new String(repl);
    }
    
    public long getFlags(){
        return proprietati;
    }
    
    public String getWord(){
        return cuvant;
    }
    
    public String getReplacement(){
        return replacement;
    }
    
    public void setWord(String s){
        cuvant = new String(s);
    }
}
