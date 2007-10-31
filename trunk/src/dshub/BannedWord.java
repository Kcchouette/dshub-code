/*
 * BannedWord.java
 *
 * Created on 30 octombrie 2007, 15:13
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

import java.util.BitSet;
/**
 *
 * @author naccio
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
