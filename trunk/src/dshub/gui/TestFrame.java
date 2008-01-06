/*
 * TestFrame.java
 *
 * Created on 16 iunie 2007, 14:32
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

package dshub.gui;


import dshub.*;
import dshub.Modules.Modulator;
import dshub.Modules.Module;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Proxy;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultListModel;

/**
 * Basic GUI for DSHub ( TestFrame is a test name that was converted into main GUI, didnt change it after
 * I decided to make a GUI )
 * Provides all hub configuration, accounts, and adc adv config panel.
 *
 * @author  Pietricica
 */
public class TestFrame extends javax.swing.JFrame {
    BanWordsList listaBanate;
    ImageIcon myIco;
    /** Creates new form TestFrame */
    public TestFrame() {
        
        initComponents();
        /*java.awt.EventQueue.invokeLater (new Runnable ()
        {
            public void run ()
            {
               // setVisible (true);
         
         
            }
        });*/
        
        
        //init list model for jList1 to DefaultListModel
        DefaultListModel modelLista=new DefaultListModel();
        jList1.setModel(modelLista);
        listaBanate=Main.listaBanate;
        int i,n=listaBanate.size();
        for (i=0;i<n;i++){
            modelLista.addElement(listaBanate.elementAt(i));
        }
        myIco=new ImageIcon(getClass().getResource("/dshub/ds.jpg"));
        //this.setIconImage(new ImageIcon("/dshub/ds.ico").getImage());
        this.setIconImage(myIco.getImage());
          refreshInit();
          
         //Modulator.findModules();
    }
    
   
    public void refreshGUIPlugs()
    {
        //jScrollPane11.removeAll();
         int y=20;
        for(Module myPlug : Modulator.myModules)
        {
            
                myPlug.getName();
           // if(firstClick)
            //    myPlug.loadEnable();
            JPanel myPanel=new JPanel();
            
            //myPanel.setSize(PluginPanel.getWidth()-20,50);
            myPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(myPlug.getName()));
       org.jdesktop.layout.GroupLayout myPanelLayout = new org.jdesktop.layout.GroupLayout(myPanel);
        myPanel.setLayout(myPanelLayout);
       JCheckBox enableCheck=new JCheckBox("Enable");
       JButton guiClick=new JButton("Open Plugin GUI");
        enableCheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        enableCheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        myPanelLayout.setHorizontalGroup(
            myPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(myPanelLayout.createSequentialGroup()
                .add(18, 18, 18)
                .add(enableCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 199, Short.MAX_VALUE)
                .add(guiClick)
                .add(68, 68, 68)
                .addContainerGap(534, Short.MAX_VALUE))
        );
        myPanelLayout.setVerticalGroup(
            myPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(myPanelLayout.createSequentialGroup()
                .add(14, 14, 14)
                .add(myPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(enableCheck)
                    .add(guiClick))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        PluginPanel.add(myPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, y, 520, 80));
        myPlug.setCheckBox(enableCheck);
        myPlug.setButton(guiClick);
        y+=90;
        
       // myPanel.add(enableCheck);
        }
        
    }
    
    public void refreshListaBanate(){
        DefaultListModel modelLista = (DefaultListModel) jList1.getModel();
        modelLista.removeAllElements();
        int i,n=listaBanate.size();
        for (i=0;i<n;i++){
            modelLista.addElement(listaBanate.elementAt(i));
        }
    }
    
    public void selectPr(long prop,String repl){
        jRadioButton1.setSelected(false);
        jRadioButton2.setSelected(false);
        jRadioButton4.setSelected(false);
        jRadioButton5.setSelected(false);
        jRadioButton6.setSelected(false);
        privatecheck.setSelected(false);
        notifycheck.setSelected(false);
        searchcheck.setSelected(false);
        jTextField3.setEditable(false);
        jTextField3.setText("");
        if ( (prop & BannedWord.dropped) != 0 ){
            jRadioButton1.setSelected(true);
        }
        if ( (prop & BannedWord.kicked) != 0 ){
            jRadioButton2.setSelected(true);
        }
        if ( (prop & BannedWord.noAction) != 0 ){
            jRadioButton3.setSelected(true);
        }
        
        if ( (prop & BannedWord.hidden) != 0 ){
            jRadioButton4.setSelected(true);
        }
        if ( (prop & BannedWord.replaced) != 0 ){
            jRadioButton5.setSelected(true);
        }
        if ( (prop & BannedWord.modified) != 0 ){
            jRadioButton6.setSelected(true);
            jTextField3.setEditable(true);
            jTextField3.setText(repl);
        }
        if ( (prop & BannedWord.privatechat) != 0 ){
            privatecheck.setSelected(true);
        }
        if ( (prop & BannedWord.notify) != 0 ){
            notifycheck.setSelected(true);
        }
         if ( (prop & BannedWord.searches) != 0 ){
            searchcheck.setSelected(true);
        }
    }
    
    public long getClientPr(){
        long prop=0;
        
        if (jRadioButton1.isSelected())
            prop = prop | BannedWord.dropped;
        if (jRadioButton2.isSelected())
            prop = prop | BannedWord.kicked;
        if (jRadioButton3.isSelected())
            prop = prop | BannedWord.noAction;
        // System.out.println(jRadioButton1.isSelected()+" "+jRadioButton2.isSelected()+" "+
        //         jRadioButton3.isSelected());
        //System.out.println("sss - "+prop+" - sss");
        return prop;
    }
    
    public long getClientAddPr(){
        long prop=0;
        
        if (jRadioButton7.isSelected())
            prop = prop | BannedWord.dropped;
        if (jRadioButton8.isSelected())
            prop = prop | BannedWord.kicked;
        if (jRadioButton9.isSelected())
            prop = prop | BannedWord.noAction;
        // System.out.println(jRadioButton1.isSelected()+" "+jRadioButton2.isSelected()+" "+
        //         jRadioButton3.isSelected());
        //System.out.println("sss - "+prop+" - sss");
        return prop;
    }
    
    public long getWordPr(){
        long prop=0;
        
        if (jRadioButton4.isSelected())
            prop = prop | BannedWord.hidden;
        if (jRadioButton5.isSelected())
            prop = prop | BannedWord.replaced;
        if (jRadioButton6.isSelected())
            prop = prop | BannedWord.modified;
        // System.out.println(prop);
        return prop;
    }
    public long getWordAddPr(){
        long prop=0;
        
        if (jRadioButton10.isSelected())
            prop = prop | BannedWord.hidden;
        if (jRadioButton11.isSelected())
            prop = prop | BannedWord.replaced;
        if (jRadioButton12.isSelected())
            prop = prop | BannedWord.modified;
        // System.out.println(prop);
        return prop;
    }
    
    public String getRepl(){
        if (jRadioButton6.isSelected()){
            return jTextField3.getText();
        }
        return "";
    }
    
    public String getAddRepl(){
        if (jRadioButton12.isSelected()){
            return jTextField5.getText();
        }
        return "";
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        namefield = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        topicfield = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        regonlycheck = new javax.swing.JCheckBox();
        jPanel21 = new javax.swing.JPanel();
        portfield = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        proxycheck = new javax.swing.JCheckBox();
        proxyhostfield = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        proxyportfield = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        hubhostfield = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        botnamefield = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        botdescfield = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        fieldtimeout = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        maxnifield = new javax.swing.JTextField();
        minnifield = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        maxdefield = new javax.swing.JTextField();
        maxsharefield = new javax.swing.JTextField();
        minsharefield = new javax.swing.JTextField();
        maxslfield = new javax.swing.JTextField();
        minslfield = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        maxemfield = new javax.swing.JTextField();
        maxhubsopfield = new javax.swing.JTextField();
        maxhubsregfield = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        maxhubsuserfield = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        maxusersfield = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        maxschcharsfield = new javax.swing.JTextField();
        minschcharsfield = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        nickcharsfield = new javax.swing.JTextArea();
        jButton9 = new javax.swing.JButton();
        xxx = new javax.swing.JPanel();
        maxchatmsgfield = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        chatintervalfield = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        automagicsearchfield = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        searchlogbasefield = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        searchstepsfield = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        searchspamresetfield = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        msgsearchspamfield = new javax.swing.JTextArea();
        jLabel47 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        miscpanel = new javax.swing.JPanel();
        jButton11 = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        opchatnamefield = new javax.swing.JTextField();
        opchatdescfield = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        redirecturl = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        kicktimefield = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        historylinesfield = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        savelogscheck = new javax.swing.JCheckBox();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        msgfullfield = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        msgbannedfield = new javax.swing.JTextArea();
        jButton31 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AccountTable = new javax.swing.JTable();
        jButton22 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        BanTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton6 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel15 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jTextField3 = new javax.swing.JTextField();
        privatecheck = new javax.swing.JCheckBox();
        notifycheck = new javax.swing.JCheckBox();
        searchcheck = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel64 = new javax.swing.JLabel();
        jButton23 = new javax.swing.JButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jTextField5 = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel9 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        BMSGcheck = new javax.swing.JCheckBox();
        DMSGcheck = new javax.swing.JCheckBox();
        EMSGcheck = new javax.swing.JCheckBox();
        FMSGcheck = new javax.swing.JCheckBox();
        HMSGcheck = new javax.swing.JCheckBox();
        jButton12 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        BSTAcheck = new javax.swing.JCheckBox();
        DSTAcheck = new javax.swing.JCheckBox();
        ESTAcheck = new javax.swing.JCheckBox();
        FSTAcheck = new javax.swing.JCheckBox();
        HSTAcheck = new javax.swing.JCheckBox();
        jPanel27 = new javax.swing.JPanel();
        BCTMcheck = new javax.swing.JCheckBox();
        DCTMcheck = new javax.swing.JCheckBox();
        ECTMcheck = new javax.swing.JCheckBox();
        FCTMcheck = new javax.swing.JCheckBox();
        HCTMcheck = new javax.swing.JCheckBox();
        jPanel28 = new javax.swing.JPanel();
        BRCMcheck = new javax.swing.JCheckBox();
        DRCMcheck = new javax.swing.JCheckBox();
        ERCMcheck = new javax.swing.JCheckBox();
        FRCMcheck = new javax.swing.JCheckBox();
        HRCMcheck = new javax.swing.JCheckBox();
        jPanel29 = new javax.swing.JPanel();
        BINFcheck = new javax.swing.JCheckBox();
        DINFcheck = new javax.swing.JCheckBox();
        EINFcheck = new javax.swing.JCheckBox();
        FINFcheck = new javax.swing.JCheckBox();
        HINFcheck = new javax.swing.JCheckBox();
        jPanel30 = new javax.swing.JPanel();
        BSCHcheck = new javax.swing.JCheckBox();
        DSCHcheck = new javax.swing.JCheckBox();
        ESCHcheck = new javax.swing.JCheckBox();
        FSCHcheck = new javax.swing.JCheckBox();
        HSCHcheck = new javax.swing.JCheckBox();
        jPanel31 = new javax.swing.JPanel();
        BREScheck = new javax.swing.JCheckBox();
        DREScheck = new javax.swing.JCheckBox();
        EREScheck = new javax.swing.JCheckBox();
        FREScheck = new javax.swing.JCheckBox();
        HREScheck = new javax.swing.JCheckBox();
        jPanel32 = new javax.swing.JPanel();
        BPAScheck = new javax.swing.JCheckBox();
        DPAScheck = new javax.swing.JCheckBox();
        EPAScheck = new javax.swing.JCheckBox();
        FPAScheck = new javax.swing.JCheckBox();
        HPAScheck = new javax.swing.JCheckBox();
        jPanel33 = new javax.swing.JPanel();
        BSUPcheck = new javax.swing.JCheckBox();
        DSUPcheck = new javax.swing.JCheckBox();
        ESUPcheck = new javax.swing.JCheckBox();
        FSUPcheck = new javax.swing.JCheckBox();
        HSUPcheck = new javax.swing.JCheckBox();
        PPanel = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        PluginPanel = new javax.swing.JPanel();
        jButton30 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LogText = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        Panelxxx = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        StatusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("DSHub ADC HubSoft created by Pietricica");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener()
        {
            public void windowGainedFocus(java.awt.event.WindowEvent evt)
            {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt)
            {
            }
        });

        jButton1.setText("Exit");
        jButton1.setToolTipText("Exits the program.");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Hideme");
        jButton2.setToolTipText("Hides the Window, but hub keeps running.");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                jTabbedPane1MousePressed(evt);
            }
        });

        jPanel1.setToolTipText("About DSHub...");
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel5.setText("Welcome to DSHub an ADC hubsoft for Direct Connect.");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 360, -1, -1));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dshub/ds.jpg"))); // NOI18N
        jPanel1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, -1));

        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder("Death Squad Hub. The Credits"));

        jLabel14.setText("Version: DSHub Theta RC3");

        jLabel7.setText("Copyright 2007  by Pietry");

        jLabel8.setText("Special Thanks goes to : MAGY, Spader, Toast, Naccio");

        jLabel6.setText("Also thanks go to everybody who helped me and were ");

        jLabel69.setText("there for me, also to everybody using my software and");

        jLabel70.setText("all testers and contributors with ideas.");

        org.jdesktop.layout.GroupLayout jPanel23Layout = new org.jdesktop.layout.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23Layout.createSequentialGroup()
                .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel23Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel69))
                    .add(jPanel23Layout.createSequentialGroup()
                        .add(41, 41, 41)
                        .add(jLabel70))
                    .add(jPanel23Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel8)
                            .add(jLabel6)))
                    .add(jPanel23Layout.createSequentialGroup()
                        .add(73, 73, 73)
                        .add(jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel14)
                            .add(jLabel7))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel23Layout.createSequentialGroup()
                .add(12, 12, 12)
                .add(jLabel14)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel7)
                .add(14, 14, 14)
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel69)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel70)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 330, 180));

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("License"));

        jLabel1.setText("This program is distributed in the hope that it will be useful, ");

        jLabel2.setText("but WITHOUT ANY WARRANTY; without even the implied warranty of ");

        jLabel3.setText("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See");

        jLabel4.setText("the GNU General Public License for more details. ");

        jLabel66.setText("This program uses the MINA library http://mina.apache.org licensed");

        jLabel9.setText("under the Apache Public License.");

        org.jdesktop.layout.GroupLayout jPanel24Layout = new org.jdesktop.layout.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel24Layout.createSequentialGroup()
                .add(jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel1)
                    .add(jLabel2)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                    .add(jLabel4)
                    .add(jLabel66)
                    .add(jLabel9))
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel66)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel9)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 390, 180));

        jTabbedPane1.addTab("About", null, jPanel1, "About DSHub...");

        jPanel2.setToolTipText("Hub Settings");

        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel7.setToolTipText("Primary Settings");
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton7.setText("Save Settings");
        jButton7.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, -1, -1));

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Hub Settings"));

        namefield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel19.setText("Hub name to display in main window.");

        topicfield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel18.setText("Current hub topic, to be shown in title bar.");

        regonlycheck.setFont(new java.awt.Font("Tahoma", 0, 10));
        regonlycheck.setText("Registered users only hub.");
        regonlycheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        regonlycheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        regonlycheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                regonlycheckActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel20Layout = new org.jdesktop.layout.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel20Layout.createSequentialGroup()
                .add(jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel20Layout.createSequentialGroup()
                        .add(jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(namefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(topicfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(14, 14, 14)
                        .add(jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel18)
                            .add(jLabel19)))
                    .add(jPanel20Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(regonlycheck)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel20Layout.createSequentialGroup()
                .add(jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(namefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel19))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel20Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(topicfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel18))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(regonlycheck)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 470, 90));

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection Settings"));

        portfield.setText("411");
        portfield.setPreferredSize(new java.awt.Dimension(180, 20));
        portfield.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                portfieldActionPerformed(evt);
            }
        });
        portfield.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                portfieldFocusLost(evt);
            }
        });
        portfield.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                portfieldKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel17.setText("Default port hub should run on. Requires restart to apply.");

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Proxy Settings"));

        proxycheck.setFont(new java.awt.Font("Tahoma", 0, 10));
        proxycheck.setText("Use Proxy for http access required by some modules");
        proxycheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        proxycheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        proxycheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                proxycheckActionPerformed(evt);
            }
        });

        proxyhostfield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel67.setText("Proxy host");

        proxyportfield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel68.setText("Proxy port");

        org.jdesktop.layout.GroupLayout jPanel19Layout = new org.jdesktop.layout.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(proxycheck)
                    .add(jPanel19Layout.createSequentialGroup()
                        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(proxyhostfield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(proxyportfield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(14, 14, 14)
                        .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel68)
                            .add(jLabel67))))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel19Layout.createSequentialGroup()
                .add(proxycheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(proxyhostfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel67))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel19Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(proxyportfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel68))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        hubhostfield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel50.setText("Hub host ( address ) ( enter your DNS here )");

        org.jdesktop.layout.GroupLayout jPanel21Layout = new org.jdesktop.layout.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel21Layout.createSequentialGroup()
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(portfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(hubhostfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(17, 17, 17)
                        .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel50)
                            .add(jLabel17)))
                    .add(jPanel21Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel21Layout.createSequentialGroup()
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(portfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel17))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel21Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(hubhostfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel50))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 8, Short.MAX_VALUE)
                .add(jPanel19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel7.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 470, 180));

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("Bots Settings"));

        botnamefield.setText("DSHub");
        botnamefield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel48.setText("Hub security bot name.");

        botdescfield.setText("www.death-squad.ro/dshub");
        botdescfield.setPreferredSize(new java.awt.Dimension(180, 20));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel49.setText("Hub security bot description.");

        org.jdesktop.layout.GroupLayout jPanel22Layout = new org.jdesktop.layout.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(botdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(botnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(14, 14, 14)
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel48)
                    .add(jLabel49))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel22Layout.createSequentialGroup()
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel48))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel22Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel49))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 470, 90));

        jTabbedPane2.addTab("Main Settings", jPanel7);

        jPanel8.setToolTipText("Hub Restrictions for Users");

        fieldtimeout.setText("20");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel20.setText("Number of seconds for hub to wait for connecting users until kick them out, Integer");

        maxnifield.setText("64");

        minnifield.setText("1");

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel21.setText("Maximum nick size, integer. ");

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel22.setText("Minimum nick size, integer.");

        maxdefield.setText("120");

        maxsharefield.setText("1125899906842624");

        minsharefield.setText("0");

        maxslfield.setText("1000");

        minslfield.setText("0");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel23.setText("Maximum description size, integer.");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel24.setText("Maximum share size, MiB, long integer. ");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel25.setText("Minimum share size, MiB, long integer. ");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel26.setText("Maximum slot number, integer. ");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel27.setText("Minimum slot number, integer. ");

        maxemfield.setText("128");

        maxhubsopfield.setText("40");

        maxhubsregfield.setText("30");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel28.setText("Maximum e-mail string size, integer. ");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel29.setText("Maximum hubs where user is op, integer.");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel30.setText("Maximum hubs where user is reg, integer.");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel31.setText("Maximum hubs where user is user, integer.");

        maxhubsuserfield.setText("200");

        jButton8.setText("Save Settings");
        jButton8.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton8ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(27, 27, 27)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(maxhubsuserfield)
                            .add(maxhubsregfield)
                            .add(maxhubsopfield)
                            .add(maxemfield)
                            .add(minslfield)
                            .add(maxslfield)
                            .add(minsharefield)
                            .add(maxsharefield)
                            .add(maxdefield)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, minnifield)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, maxnifield)
                            .add(fieldtimeout, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                        .add(34, 34, 34)
                        .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel20)
                            .add(jLabel21)
                            .add(jLabel22)
                            .add(jLabel23)
                            .add(jLabel24)
                            .add(jLabel25)
                            .add(jLabel26)
                            .add(jLabel27)
                            .add(jLabel28)
                            .add(jLabel29)
                            .add(jLabel30)
                            .add(jLabel31)))
                    .add(jPanel8Layout.createSequentialGroup()
                        .add(264, 264, 264)
                        .add(jButton8)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel20)
                    .add(fieldtimeout, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxnifield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel21))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(minnifield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel22))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxdefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel23))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxsharefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel24))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(minsharefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel25))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxslfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel26))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(minslfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel27))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxemfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel28))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxhubsopfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel29))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxhubsregfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel30))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxhubsuserfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel31))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 16, Short.MAX_VALUE)
                .add(jButton8)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Restrictions1", jPanel8);

        jPanel13.setToolTipText("Hub Restrictions");

        maxusersfield.setText("1000");

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel32.setText("Maximum number of online users, integer.");

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel35.setText("Chars that could be used for a nick, String. ");

        maxschcharsfield.setText("256");
        maxschcharsfield.setPreferredSize(new java.awt.Dimension(130, 19));

        minschcharsfield.setText("3");
        minschcharsfield.setPreferredSize(new java.awt.Dimension(130, 19));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel36.setText("Maximum search chars, integer.");

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel37.setText("Minimum search chars, integer.");

        nickcharsfield.setColumns(20);
        nickcharsfield.setRows(5);
        jScrollPane7.setViewportView(nickcharsfield);

        jButton9.setText("Save Settings");
        jButton9.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton9ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel13Layout = new org.jdesktop.layout.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel13Layout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel13Layout.createSequentialGroup()
                                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, minschcharsfield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, maxschcharsfield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .add(21, 21, 21)
                                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel36)
                                    .add(jLabel37)))
                            .add(jPanel13Layout.createSequentialGroup()
                                .add(maxusersfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(21, 21, 21)
                                .add(jLabel32))
                            .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 463, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(jPanel13Layout.createSequentialGroup()
                        .add(149, 149, 149)
                        .add(jLabel35))
                    .add(jPanel13Layout.createSequentialGroup()
                        .add(256, 256, 256)
                        .add(jButton9)))
                .addContainerGap(157, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxusersfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel32))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel35)
                .add(17, 17, 17)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxschcharsfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel36))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(minschcharsfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel37))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 110, Short.MAX_VALUE)
                .add(jButton9)
                .add(69, 69, 69))
        );

        jTabbedPane2.addTab("Restrictions2", jPanel13);

        xxx.setToolTipText("Spam settings");

        maxchatmsgfield.setText("512");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel38.setText("Maximum chat message size, integer.");

        chatintervalfield.setText("500");

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel43.setText("Interval between chat lines, millis, Integer.");

        jButton10.setText("Save Settings");
        jButton10.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton10ActionPerformed(evt);
            }
        });

        automagicsearchfield.setText("36");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel15.setText("Interval between automagic searches for each user, seconds, Integer.");

        searchlogbasefield.setText("2000");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel16.setText("Logarithmic base for user searches interval,millis, Integer.");

        searchstepsfield.setText("6");

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel45.setText("Maximum nr of search steps allowed until reset needed, Integer.");

        searchspamresetfield.setText("300");

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel46.setText("Interval until search_steps is being reset, seconds, Integer.");

        msgsearchspamfield.setColumns(20);
        msgsearchspamfield.setRows(5);
        jScrollPane8.setViewportView(msgsearchspamfield);

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel47.setText("Message that appears as a result when search is delayed, String.");

        jButton5.setText("[?]");
        jButton5.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout xxxLayout = new org.jdesktop.layout.GroupLayout(xxx);
        xxx.setLayout(xxxLayout);
        xxxLayout.setHorizontalGroup(
            xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(xxxLayout.createSequentialGroup()
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(xxxLayout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 468, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(xxxLayout.createSequentialGroup()
                                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                    .add(searchstepsfield)
                                    .add(searchlogbasefield)
                                    .add(automagicsearchfield)
                                    .add(chatintervalfield)
                                    .add(maxchatmsgfield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                                    .add(searchspamresetfield))
                                .add(25, 25, 25)
                                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel16)
                                    .add(jLabel15)
                                    .add(jLabel38)
                                    .add(jLabel43)
                                    .add(xxxLayout.createSequentialGroup()
                                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                        .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(jLabel46)
                                            .add(xxxLayout.createSequentialGroup()
                                                .add(jLabel45)
                                                .add(53, 53, 53)
                                                .add(jButton5))))))))
                    .add(xxxLayout.createSequentialGroup()
                        .add(95, 95, 95)
                        .add(jLabel47))
                    .add(xxxLayout.createSequentialGroup()
                        .add(255, 255, 255)
                        .add(jButton10)))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        xxxLayout.setVerticalGroup(
            xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(xxxLayout.createSequentialGroup()
                .addContainerGap()
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(maxchatmsgfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel38))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(chatintervalfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel43))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(automagicsearchfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel15))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(searchlogbasefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel16))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel45)
                    .add(searchstepsfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton5))
                .add(9, 9, 9)
                .add(xxxLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(searchspamresetfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel46))
                .add(21, 21, 21)
                .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel47)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 67, Short.MAX_VALUE)
                .add(jButton10)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Spam Settings", xxx);

        miscpanel.setToolTipText("Misc Settings");
        miscpanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton11.setText("Save Settings");
        jButton11.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton11ActionPerformed(evt);
            }
        });
        miscpanel.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 160, -1, -1));

        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder("Bots settings"));

        opchatnamefield.setText("OpChat");
        opchatnamefield.setMinimumSize(new java.awt.Dimension(130, 20));
        opchatnamefield.setPreferredSize(new java.awt.Dimension(140, 20));

        opchatdescfield.setText("BoT");
        opchatdescfield.setPreferredSize(new java.awt.Dimension(140, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel33.setText("The Operator Chat Bot Nick.");

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel34.setText("The Operator Chat Bot Description.");

        org.jdesktop.layout.GroupLayout jPanel35Layout = new org.jdesktop.layout.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel35Layout.createSequentialGroup()
                        .add(opchatnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel33))
                    .add(jPanel35Layout.createSequentialGroup()
                        .add(opchatdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabel34)))
                .addContainerGap(141, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel35Layout.createSequentialGroup()
                .add(jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(opchatnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel33))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel35Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(opchatdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel34))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        miscpanel.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 480, 80));

        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder("Redirects"));

        redirecturl.setMinimumSize(new java.awt.Dimension(130, 20));
        redirecturl.setPreferredSize(new java.awt.Dimension(140, 20));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel53.setText("The main redirect URL to send faulty users ( or default redirects )");

        org.jdesktop.layout.GroupLayout jPanel36Layout = new org.jdesktop.layout.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel36Layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(redirecturl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel53)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel36Layout.createSequentialGroup()
                .add(jPanel36Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(redirecturl, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel53))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        miscpanel.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 480, 50));

        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder("Kick settings"));

        kicktimefield.setText("300");
        kicktimefield.setPreferredSize(new java.awt.Dimension(140, 20));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel40.setText("The time to ban a user with a kick, in seconds.");

        org.jdesktop.layout.GroupLayout jPanel38Layout = new org.jdesktop.layout.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .add(kicktimefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel40)
                .addContainerGap(92, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel38Layout.createSequentialGroup()
                .add(jPanel38Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(kicktimefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel40))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        miscpanel.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 480, 60));

        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder("History settings"));

        historylinesfield.setText("50");
        historylinesfield.setPreferredSize(new java.awt.Dimension(140, 20));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel39.setText("Number of lines to keep in chat and command history.");

        org.jdesktop.layout.GroupLayout jPanel39Layout = new org.jdesktop.layout.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .add(historylinesfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel39)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel39Layout.createSequentialGroup()
                .add(jPanel39Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(historylinesfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel39))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        miscpanel.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 480, 60));

        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder("Logging settings"));

        savelogscheck.setFont(new java.awt.Font("Tahoma", 0, 10));
        savelogscheck.setText("Save logs to file.");
        savelogscheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        savelogscheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        savelogscheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                savelogscheckActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel56.setText("Logs have their file name the date when hub was started.");

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel57.setText("They are saved to /logs directory.");

        org.jdesktop.layout.GroupLayout jPanel40Layout = new org.jdesktop.layout.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .add(savelogscheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel57)
                    .add(jLabel56))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel40Layout.createSequentialGroup()
                .add(jPanel40Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(savelogscheck)
                    .add(jLabel56))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel57)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        miscpanel.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 480, 60));

        jTabbedPane2.addTab("Misc Settings1", miscpanel);

        jPanel37.setToolTipText("Miscellaneous Settings");
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder("Messages"));

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel42.setText("The additional message to be shown to connecting users when hub full.");

        msgfullfield.setColumns(20);
        msgfullfield.setRows(5);
        jScrollPane6.setViewportView(msgfullfield);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel41.setText("The aditional message to show to banned users when connecting.");

        msgbannedfield.setColumns(20);
        msgbannedfield.setRows(5);
        jScrollPane5.setViewportView(msgbannedfield);

        org.jdesktop.layout.GroupLayout jPanel34Layout = new org.jdesktop.layout.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                    .add(jLabel42)
                    .add(jLabel41)
                    .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel34Layout.createSequentialGroup()
                .add(jLabel42)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jLabel41)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel37.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 600, 250));

        jButton31.setText("Save Settings");
        jButton31.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton31ActionPerformed(evt);
            }
        });
        jPanel37.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 290, -1, -1));

        jTabbedPane2.addTab("Misc Settings2", jPanel37);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 735, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Settings", null, jPanel2, "Hub Settings...");

        jPanel3.setToolTipText("The Hub Accounts");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel10.setText("Here you can modify the hub accounts");

        jLabel11.setText("Reg new user by inputing either CID or the nick (if logged in):");

        jTextField1.setToolTipText("Enter the CID/Nick(his CID will be added) you want to add");

        jButton4.setText("Reg !");
        jButton4.setToolTipText("Adds the CID/nick to database");
        jButton4.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel12.setText("Note: Registrations are CID only, if you enter a nick, his CID will be added. Press delete on an account to ureg it.");

        jScrollPane2.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                jScrollPane2KeyPressed(evt);
            }
        });

        AccountTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {
                "CID", "Last Nick:", "Last IP:", "Regged By:", "Regged At:"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        AccountTable.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                AccountTableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                AccountTableKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(AccountTable);

        jButton22.setText("Edit Account");
        jButton22.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton22ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(230, 230, 230)
                        .add(jLabel10))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(78, 78, 78)
                        .add(jLabel12))
                    .add(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jLabel11)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 299, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton4))
                            .add(jPanel3Layout.createSequentialGroup()
                                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 624, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton22)))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jLabel10)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 302, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(116, 116, 116)
                        .add(jButton22, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 97, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 15, Short.MAX_VALUE)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel11)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel12)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Accounts", null, jPanel3, "The Hub Accounts");

        jPanel5.setToolTipText("Ban Handler");

        BanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {new Integer(1), "bla bla bla", "[RO][B]adjsaiodjsa", "21.10 10:56", "[RO][B][CABLE]dusau", "122.234.121.120", "JDISJADIJSAOIDJSAIOJDOSAJ", new Long(3600000)}
            },
            new String []
            {
                "Type", "Reason", "Who banned", "Time Issued", "Nick", "IP", "CID", "Remaining time"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Long.class
            };

            public Class getColumnClass(int columnIndex)
            {
                return types [columnIndex];
            }
        });
        jScrollPane10.setViewportView(BanTable);

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
                .add(40, 40, 40))
        );

        jTabbedPane1.addTab("Bans", null, jPanel5, "Ban Handler...");

        jPanel6.setToolTipText("Some Hub Statistics...");
        jPanel6.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
        {
            public void mouseMoved(java.awt.event.MouseEvent evt)
            {
                jPanel6MouseMoved(evt);
            }
        });
        jPanel6.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                jPanel6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt)
            {
                jPanel6FocusLost(evt);
            }
        });
        jPanel6.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jPanel6MouseClicked(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane3.setViewportView(jTextArea2);

        jButton6.setText("Update Statistics");
        jButton6.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel6Layout = new org.jdesktop.layout.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE))
            .add(jPanel6Layout.createSequentialGroup()
                .add(297, 297, 297)
                .add(jButton6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(311, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 226, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton6)
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Stats", null, jPanel6, "Some Hub Statistics...");

        jPanel10.setToolTipText("Forbidden words");

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Options"));
        jPanel11.setFont(new java.awt.Font("Tahoma", 0, 10));

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Client Action"));

        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton1.setSelected(true);
        jRadioButton1.setText("Drop");
        jRadioButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton1MouseClicked(evt);
            }
        });

        buttonGroup2.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton2.setText("Kick");
        jRadioButton2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton2MouseClicked(evt);
            }
        });

        buttonGroup2.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton3.setText("No Action");
        jRadioButton3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton3.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton3MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel14Layout = new org.jdesktop.layout.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jRadioButton1)
                    .add(jRadioButton2)
                    .add(jRadioButton3))
                .addContainerGap(145, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel14Layout.createSequentialGroup()
                .add(jRadioButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton3)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Word Action"));
        jPanel15.setFont(new java.awt.Font("Tahoma", 0, 10));

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton4.setSelected(true);
        jRadioButton4.setText("Hide Line");
        jRadioButton4.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton4.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton4MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton5.setText("Replace with *");
        jRadioButton5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton5.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton5MouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton6.setText("Modify");
        jRadioButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton6.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton6MouseClicked(evt);
            }
        });
        jRadioButton6.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jRadioButton6StateChanged(evt);
            }
        });

        jTextField3.setEditable(false);
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextField3KeyReleased(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel15Layout = new org.jdesktop.layout.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                    .add(jRadioButton4)
                    .add(jRadioButton5)
                    .add(jRadioButton6))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15Layout.createSequentialGroup()
                .add(jRadioButton4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jRadioButton6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        privatecheck.setFont(new java.awt.Font("Tahoma", 0, 10));
        privatecheck.setText("Control also Private Chat");
        privatecheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        privatecheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        privatecheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                privatecheckActionPerformed(evt);
            }
        });

        notifycheck.setFont(new java.awt.Font("Tahoma", 0, 10));
        notifycheck.setText("Notify operator chat");
        notifycheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        notifycheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        notifycheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                notifycheckActionPerformed(evt);
            }
        });

        searchcheck.setFont(new java.awt.Font("Tahoma", 0, 10));
        searchcheck.setText("Control also Searches");
        searchcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                searchcheckActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(privatecheck)
                        .add(45, 45, 45)
                        .add(searchcheck)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 45, Short.MAX_VALUE)
                        .add(notifycheck, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 119, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(privatecheck)
                    .add(notifycheck)
                    .add(searchcheck))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("File List"));

        jLabel63.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel63.setText("File:");

        jButton26.setText("Load");
        jButton26.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton26MouseClicked(evt);
            }
        });

        jButton27.setText("Append");
        jButton27.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton27MouseClicked(evt);
            }
        });

        jButton28.setText("Save");
        jButton28.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton28MouseClicked(evt);
            }
        });

        jButton29.setText("Browse");
        jButton29.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton29MouseClicked(evt);
            }
        });
        jButton29.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton29ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel16Layout = new org.jdesktop.layout.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel16Layout.createSequentialGroup()
                        .add(jButton26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton27)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton28))
                    .add(jPanel16Layout.createSequentialGroup()
                        .add(jLabel63)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 26, Short.MAX_VALUE)
                        .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 341, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton29)))
                .addContainerGap())
        );

        jPanel16Layout.linkSize(new java.awt.Component[] {jButton26, jButton27, jButton28}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel16Layout.createSequentialGroup()
                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel63)
                    .add(jButton29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton28)
                    .add(jButton27)
                    .add(jButton26))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(new java.awt.Component[] {jButton29, jLabel63, jTextField4}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel16Layout.linkSize(new java.awt.Component[] {jButton26, jButton27, jButton28}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Add word ( regular expression )"));

        jTextField2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                jTextField2MousePressed(evt);
            }
        });
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                jTextField2KeyPressed(evt);
            }
        });

        jLabel64.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel64.setText("Word:");

        jButton23.setText("Add");
        jButton23.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton23MouseClicked(evt);
            }
        });

        buttonGroup3.add(jRadioButton7);
        jRadioButton7.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton7.setSelected(true);
        jRadioButton7.setText("Drop");
        jRadioButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton7.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup3.add(jRadioButton8);
        jRadioButton8.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton8.setText("Kick");
        jRadioButton8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton8.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup3.add(jRadioButton9);
        jRadioButton9.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton9.setText("No Action");
        jRadioButton9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton9.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup4.add(jRadioButton10);
        jRadioButton10.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton10.setSelected(true);
        jRadioButton10.setText("Hide Line");
        jRadioButton10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton10.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup4.add(jRadioButton11);
        jRadioButton11.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton11.setText("Replace With *");
        jRadioButton11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton11.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup4.add(jRadioButton12);
        jRadioButton12.setFont(new java.awt.Font("Tahoma", 0, 10));
        jRadioButton12.setText("Modify");
        jRadioButton12.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton12.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton12.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jRadioButton12StateChanged(evt);
            }
        });

        jTextField5.setEditable(false);

        org.jdesktop.layout.GroupLayout jPanel17Layout = new org.jdesktop.layout.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jLabel64)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 26, Short.MAX_VALUE)
                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 342, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(8, 8, 8)
                        .add(jButton23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 65, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel17Layout.createSequentialGroup()
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jRadioButton7)
                            .add(jRadioButton10))
                        .add(45, 45, 45)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jRadioButton8)
                            .add(jRadioButton11))
                        .add(13, 13, 13)
                        .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jRadioButton9)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 206, Short.MAX_VALUE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jRadioButton12)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 35, Short.MAX_VALUE)
                                .add(jTextField5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(10, 10, 10)))))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel17Layout.createSequentialGroup()
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel64)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jRadioButton7)
                    .add(jRadioButton8)
                    .add(jRadioButton9))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel17Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jRadioButton11)
                    .add(jRadioButton10)
                    .add(jRadioButton12)
                    .add(jTextField5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(new java.awt.Component[] {jButton23, jLabel64, jTextField2}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Forbidden Words List"));

        jButton25.setText("Delete");
        jButton25.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton25MouseClicked(evt);
            }
        });

        jButton24.setText("Clear List");
        jButton24.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jButton24MouseClicked(evt);
            }
        });

        jList1.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jList1KeyReleased(evt);
            }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(jList1);

        org.jdesktop.layout.GroupLayout jPanel18Layout = new org.jdesktop.layout.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .add(jPanel18Layout.createSequentialGroup()
                        .add(jButton24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 14, Short.MAX_VALUE)
                        .add(jButton25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 88, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel18Layout.linkSize(new java.awt.Component[] {jButton24, jButton25}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel18Layout.createSequentialGroup()
                .add(jScrollPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel18Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton24)
                    .add(jButton25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 23, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel18Layout.linkSize(new java.awt.Component[] {jButton24, jButton25}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel10Layout.createSequentialGroup()
                        .add(jPanel18, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jPanel10Layout.createSequentialGroup()
                        .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(11, 11, 11))))
        );

        jTabbedPane1.addTab("Chat Control", jPanel10);

        jPanel9.setToolTipText("ADC advanced configuration panel");
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel51.setText("The ADC advanced configuration Panel.");
        jPanel9.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel52.setText("Here you can configure the ADC commands separately.");
        jPanel9.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel54.setText("Allowed contexts:");
        jPanel9.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 60, -1, -1));

        jButton13.setText("[?]");
        jButton13.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, -1, -1));

        jButton14.setText("[?]");
        jButton14.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        jButton15.setText("[?]");
        jButton15.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, -1, -1));

        jButton16.setText("[?]");
        jButton16.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, -1, -1));

        jButton17.setText("[?]");
        jButton17.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, -1, -1));

        jButton18.setText("[?]");
        jButton18.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton18ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 230, -1, -1));

        jButton19.setText("[?]");
        jButton19.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, -1, -1));

        jButton20.setText("[?]");
        jButton20.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, -1, -1));

        jButton21.setText("[?]");
        jButton21.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 230, -1, -1));

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MSG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BMSGcheck.setText("B");
        BMSGcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BMSGcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BMSGcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                BMSGcheckActionPerformed(evt);
            }
        });
        jPanel25.add(BMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DMSGcheck.setText("D");
        DMSGcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DMSGcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        DMSGcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                DMSGcheckActionPerformed(evt);
            }
        });
        jPanel25.add(DMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        EMSGcheck.setText("E");
        EMSGcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EMSGcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        EMSGcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                EMSGcheckActionPerformed(evt);
            }
        });
        jPanel25.add(EMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FMSGcheck.setText("F");
        FMSGcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FMSGcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        FMSGcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                FMSGcheckActionPerformed(evt);
            }
        });
        jPanel25.add(FMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HMSGcheck.setText("H");
        HMSGcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HMSGcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        HMSGcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                HMSGcheckActionPerformed(evt);
            }
        });
        jPanel25.add(HMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 50, 130));

        jButton12.setText("[?]");
        jButton12.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, -1));

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "STA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BSTAcheck.setText("B");
        BSTAcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BSTAcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        BSTAcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                BSTAcheckActionPerformed(evt);
            }
        });
        jPanel26.add(BSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DSTAcheck.setText("D");
        DSTAcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DSTAcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        DSTAcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                DSTAcheckActionPerformed(evt);
            }
        });
        jPanel26.add(DSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        ESTAcheck.setText("E");
        ESTAcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ESTAcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ESTAcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                ESTAcheckActionPerformed(evt);
            }
        });
        jPanel26.add(ESTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FSTAcheck.setText("F");
        FSTAcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FSTAcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        FSTAcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                FSTAcheckActionPerformed(evt);
            }
        });
        jPanel26.add(FSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HSTAcheck.setText("H");
        HSTAcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HSTAcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        HSTAcheck.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                HSTAcheckActionPerformed(evt);
            }
        });
        jPanel26.add(HSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 50, 130));

        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CTM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BCTMcheck.setText("B");
        BCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel27.add(BCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DCTMcheck.setText("D");
        DCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel27.add(DCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        ECTMcheck.setText("E");
        ECTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ECTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel27.add(ECTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FCTMcheck.setText("F");
        FCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel27.add(FCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HCTMcheck.setText("H");
        HCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel27.add(HCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 50, 130));

        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RCM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BRCMcheck.setText("B");
        BRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel28.add(BRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DRCMcheck.setText("D");
        DRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel28.add(DRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        ERCMcheck.setText("E");
        ERCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ERCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel28.add(ERCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FRCMcheck.setText("F");
        FRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel28.add(FRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HRCMcheck.setText("H");
        HRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel28.add(HRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 50, 130));

        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INF", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BINFcheck.setText("B");
        BINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel29.add(BINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DINFcheck.setText("D");
        DINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel29.add(DINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        EINFcheck.setText("E");
        EINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel29.add(EINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FINFcheck.setText("F");
        FINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel29.add(FINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HINFcheck.setText("H");
        HINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel29.add(HINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 90, 50, 130));

        jPanel30.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SCH", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BSCHcheck.setText("B");
        BSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel30.add(BSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DSCHcheck.setText("D");
        DSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel30.add(DSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        ESCHcheck.setText("E");
        ESCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ESCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel30.add(ESCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FSCHcheck.setText("F");
        FSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel30.add(FSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HSCHcheck.setText("H");
        HSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel30.add(HSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 50, 130));

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BREScheck.setText("B");
        BREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel31.add(BREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DREScheck.setText("D");
        DREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel31.add(DREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        EREScheck.setText("E");
        EREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel31.add(EREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FREScheck.setText("F");
        FREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel31.add(FREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HREScheck.setText("H");
        HREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel31.add(HREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 90, 50, 130));

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PAS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BPAScheck.setText("B");
        BPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel32.add(BPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DPAScheck.setText("D");
        DPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel32.add(DPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        EPAScheck.setText("E");
        EPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel32.add(EPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FPAScheck.setText("F");
        FPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel32.add(FPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HPAScheck.setText("H");
        HPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel32.add(HPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 50, 130));

        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SUP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11)));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BSUPcheck.setText("B");
        BSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel33.add(BSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        DSUPcheck.setText("D");
        DSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel33.add(DSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        ESUPcheck.setText("E");
        ESUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ESUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel33.add(ESUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        FSUPcheck.setText("F");
        FSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel33.add(FSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        HSUPcheck.setText("H");
        HSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel33.add(HSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        jPanel9.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, 50, 130));

        jTabbedPane1.addTab("Advanced", jPanel9);

        PPanel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                PPanelMouseClicked(evt);
            }
        });
        PPanel.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                PPanelFocusGained(evt);
            }
        });
        PPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane11.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        PluginPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jScrollPane11.setViewportView(PluginPanel);

        PPanel.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 720, 350));

        jButton30.setText("Rescan for modules");
        jButton30.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton30ActionPerformed(evt);
            }
        });
        PPanel.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 160, -1));

        jLabel55.setText("Modules are the way DSHub can be extended. Place them in the /modules subdirectory.");
        PPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jTabbedPane1.addTab("Additional Modules", PPanel);

        jPanel12.setToolTipText("Hub Log");

        LogText.setColumns(20);
        LogText.setRows(5);
        jScrollPane4.setViewportView(LogText);

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Log", jPanel12);

        jPanel4.setToolTipText("Some Help ...");

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        Panelxxx.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(Panelxxx, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(Panelxxx, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Help", null, jPanel4, "Some Help...");

        jButton3.setText("RESTART HUB");
        jButton3.setToolTipText("Restarts hub with current settings.");
        jButton3.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel13.setText("STATUS:");
        jLabel13.setToolTipText("The Hub current Status.");

        StatusLabel.setFont(new java.awt.Font("Tahoma", 0, 10));
        StatusLabel.setText("Initialising ...");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(37, 37, 37)
                .add(jLabel13)
                .add(25, 25, 25)
                .add(StatusLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 441, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton3)
                .add(23, 23, 23)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 85, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 423, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(30, 30, 30)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel13)
                            .add(StatusLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jButton1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton2))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jButton3)
                                .add(21, 21, 21)))
                        .add(20, 20, 20))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_jTabbedPane1MousePressed
    {//GEN-HEADEREND:event_jTabbedPane1MousePressed
// TODO add your handling code here:
        JPanel x=(JPanel)jTabbedPane1.getSelectedComponent();
        if(x!=null)
        {
            if(x==PPanel)
            {
                
         
        refreshGUIPlugs();
            }
        }
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void PPanelFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_PPanelFocusGained
    {//GEN-HEADEREND:event_PPanelFocusGained
// TODO add your handling code here:
       
    }//GEN-LAST:event_PPanelFocusGained

    private void PPanelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_PPanelMouseClicked
    {//GEN-HEADEREND:event_PPanelMouseClicked
// TODO add your handling code here:
         
    }//GEN-LAST:event_PPanelMouseClicked

    private void proxycheckActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_proxycheckActionPerformed
    {//GEN-HEADEREND:event_proxycheckActionPerformed
if(proxycheck.isSelected())
{
    proxyhostfield.setEditable(true);
    proxyportfield.setEditable(true);
}
else
{
    
    proxyhostfield.setEditable(false);
    proxyportfield.setEditable(false);

}
    }//GEN-LAST:event_proxycheckActionPerformed

    private void notifycheckActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_notifycheckActionPerformed
    {//GEN-HEADEREND:event_notifycheckActionPerformed
       if(notifycheck.isSelected())
        {
            long prop= getClientPr() + getWordPr();
            if(privatecheck.isSelected())
                prop+=BannedWord.privatechat;
            if(searchcheck.isSelected())
                prop+=BannedWord.searches;
            prop+=BannedWord.notify;
            listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop);
        }
        else
        {
            long prop= getClientPr() + getWordPr();
            if(privatecheck.isSelected())
                prop+=BannedWord.privatechat;
            if(searchcheck.isSelected())
                prop+=BannedWord.searches;
            
            listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop);
        }
    }//GEN-LAST:event_notifycheckActionPerformed

    private void privatecheckActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_privatecheckActionPerformed
    {//GEN-HEADEREND:event_privatecheckActionPerformed
        if(privatecheck.isSelected())
        {
            long prop= getClientPr() + getWordPr() +BannedWord.privatechat;
            if(notifycheck.isSelected())
                prop+=BannedWord.notify;
            if(searchcheck.isSelected())
                prop+=BannedWord.searches;
            
            listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop);
        }
        else
        {
            
            long prop= getClientPr() + getWordPr();
            if(notifycheck.isSelected())
                prop+=BannedWord.notify;
            if(searchcheck.isSelected())
                prop+=BannedWord.searches;
            listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop);
        }
    }//GEN-LAST:event_privatecheckActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton29ActionPerformed
    {//GEN-HEADEREND:event_jButton29ActionPerformed
// TODO add your handling code here:
            String path=jTextField4.getText();
            JFileChooser fc;
            if(!(path.equals("")))
                fc=new JFileChooser(new File(path));
            else
                fc=new JFileChooser();
        
        fc.setMultiSelectionEnabled(false);
        int sel=0;
        sel=fc.showDialog(this,"Select File:");
        if (sel==JFileChooser.APPROVE_OPTION)
            jTextField4.setText(fc.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton29MouseClicked
// TODO add your handling code here:
    
    }//GEN-LAST:event_jButton29MouseClicked

    private void jButton28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton28MouseClicked
// TODO add your handling code here:
        //cc
        String path=jTextField4.getText();
        if(listaBanate.printFile(path)==true) 
         this.SetStatus("List saved.");
        else
         this.SetStatus("File access error.",JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_jButton28MouseClicked

    private void jButton27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton27MouseClicked
// TODO add your handling code here:
        String path=jTextField4.getText();
        File f=new File(path);
        if (f.isFile()){
           // listaBanate.clean();
            listaBanate.loadFile(path);
            this.refreshListaBanate();
            this.SetStatus("List saved.");
        }
    }//GEN-LAST:event_jButton27MouseClicked

    private void jButton26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton26MouseClicked
// TODO add your handling code here:
        String path=jTextField4.getText();
        File f=new File(path);
        if (f.isFile()){
            listaBanate.clean();
            listaBanate.loadFile(path);
            this.refreshListaBanate();
            this.SetStatus("List loaded.");
        }
    }//GEN-LAST:event_jButton26MouseClicked

    private void jTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyPressed
// TODO add your handling code here:
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            String banWord=jTextField2.getText();
            int l=banWord.length();
            
            if (!listaBanate.ver_regex(banWord)){
                JOptionPane.showMessageDialog(null,banWord+"is an invalid regex",
                        "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            if (l!=0){
                if (listaBanate.searchEl(banWord)==-1){
                    DefaultListModel modelLista=(DefaultListModel) jList1.getModel();
                    modelLista.add(0,banWord);
                }
                long prop=0;
                String repl="";
                prop=prop | this.getClientAddPr() | this.getWordAddPr();
                repl+= this.getAddRepl();
                listaBanate.add(banWord,prop,repl);
            }
        }
    }//GEN-LAST:event_jTextField2KeyPressed

    private void jTextField2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField2MousePressed
// TODO add your handling code here:
    }//GEN-LAST:event_jTextField2MousePressed

    private void jList1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList1KeyReleased
// TODO add your handling code here:
        
        if (evt.getKeyCode() == KeyEvent.VK_DELETE){
            int[] indici=jList1.getSelectedIndices();
            listaBanate.removeElementsAt(indici);
            DefaultListModel modelLista=(DefaultListModel)jList1.getModel();
            
            Arrays.sort(indici);
            for (int i=indici.length-1;i>=0;i--){
                modelLista.removeElementAt(indici[i]);
            }
            
            return;
        }
        
        int[] indici=jList1.getSelectedIndices();
        
        int i,n;
        n=indici.length;
        if (n==0){
            selectPr(BannedWord.dropped | BannedWord.hidden,"");
            return;
        }
        long prop=listaBanate.getPrAt(indici[0]);
        String repl=listaBanate.getReplAt(indici[0]);
        selectPr(prop,repl);
    }//GEN-LAST:event_jList1KeyReleased
                
    private void jButton25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton25MouseClicked
// TODO add your handling code here:
        int[] indici=jList1.getSelectedIndices();
        listaBanate.removeElementsAt(indici);
        DefaultListModel modelLista=(DefaultListModel)jList1.getModel();
           
        Arrays.sort(indici);
        for (int i=indici.length-1;i>=0;i--){
            modelLista.removeElementAt(indici[i]);
        }
        
    }//GEN-LAST:event_jButton25MouseClicked
    
    private void jTextField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyReleased
// TODO add your handling code here:
        
        long prop=getWordPr();
        listaBanate.modifyMultiWordPrAt(jList1.getSelectedIndices(),prop,
                jTextField3.getText());
        prop+=getClientPr();
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jTextField3KeyReleased
            
    private void jRadioButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton6MouseClicked
// TODO add your handling code here:
        long prop=getWordPr();
        listaBanate.modifyMultiWordPrAt(jList1.getSelectedIndices(),prop,
                jTextField3.getText());
        prop+=getClientPr();
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jRadioButton6MouseClicked
    
    private void jRadioButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton5MouseClicked
// TODO add your handling code here:
        long prop=getWordPr()+getClientPr();
        jTextField3.setText("");
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jRadioButton5MouseClicked
    
    private void jRadioButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton4MouseClicked
// TODO add your handling code here:
        long prop=getWordPr()+getClientPr();
        jTextField3.setText("");
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jRadioButton4MouseClicked
    
    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
// TODO add your handling code here:
        long prop=getClientPr()+getWordPr();
        // System.out.println(prop);
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jRadioButton3MouseClicked
    
    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
// TODO add your handling code here:
        long prop=getClientPr();
        //System.out.println(prop);
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jRadioButton2MouseClicked
    
    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
// TODO add your handling code here:
        long prop=getClientPr()+getWordPr();
        // System.out.println(prop);
        listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop+(notifycheck.isSelected()?BannedWord.notify:0)+(privatecheck.isSelected()?BannedWord.privatechat:0));
    }//GEN-LAST:event_jRadioButton1MouseClicked
    
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
// TODO add your handling code here:
        int[] indici=jList1.getSelectedIndices();
        
        int i,n;
        n=indici.length;
        if (n==0){
            selectPr(BannedWord.dropped | BannedWord.hidden,"");
            return;
        }
        long prop=listaBanate.getPrAt(indici[0]);
        String repl=listaBanate.getReplAt(indici[0]);
        /*
        for (i=1;i<n;i++){
            prop = prop & listaBanate.getPrAt(indici[i]);
            if (!repl.equals(listaBanate.getReplAt(indici[i]))){
                repl="";
            };
        }
         */
        // System.out.println(prop);
        selectPr(prop,repl);
    }//GEN-LAST:event_jList1MouseClicked
    
    private void jButton23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton23MouseClicked
// TODO add your handling code here:
        
        String banWord=jTextField2.getText();
        int l=banWord.length();
        if (!listaBanate.ver_regex(banWord)){
            JOptionPane.showMessageDialog(null,banWord+" is an invalid Regular Expression.",
                    "Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (l!=0){
            if (listaBanate.searchEl(banWord)==-1){
                DefaultListModel modelLista=(DefaultListModel) jList1.getModel();
                modelLista.add(0,banWord);
            }
            long prop=0;
            String repl="";
            prop=prop | this.getClientAddPr() | this.getWordAddPr();
            repl+= this.getAddRepl();
            listaBanate.add(banWord,prop+BannedWord.notify+BannedWord.privatechat,repl);
        }
    }//GEN-LAST:event_jButton23MouseClicked
    
    private void jRadioButton12StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton12StateChanged
// TODO add your handling code here:
        jTextField5.setEditable(jRadioButton12.isSelected());
    }//GEN-LAST:event_jRadioButton12StateChanged
    
    private void jButton24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton24MouseClicked
// TODO add your handling code here:
        listaBanate.clean();
        DefaultListModel modelLista=(DefaultListModel)jList1.getModel();
            
        modelLista.removeAllElements();      
    }//GEN-LAST:event_jButton24MouseClicked
    
    private void jRadioButton6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton6StateChanged
// TODO add your handling code here:
        jTextField3.setEditable(jRadioButton6.isSelected());
        
    }//GEN-LAST:event_jRadioButton6StateChanged
    
    private void jButton22ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton22ActionPerformed
    {//GEN-HEADEREND:event_jButton22ActionPerformed
        int row=AccountTable.getSelectedRow();
        if(row==-1)
            return;
        String CID=(String)AccountTable.getModel().getValueAt(row,0);
        this.setVisible(false);
        AccountEditer Acc1=new AccountEditer(CID);
        Acc1.setVisible(true);
    }//GEN-LAST:event_jButton22ActionPerformed
    
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("SUP Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("SUP is the ADC feature negotiating,\ncommand, hub-client.\n( B ) No defined purpose.\n"+
                "( D ) No defined purpose.\n( E ) No defined purpose.\n( F ) No defined purpose.\n"+
                "( H ) Send to hub."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton21ActionPerformed
    
    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("RES Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("RES is the search result command,\nis used to reply to searches.\n( B ) No defined purpose.\n"+
                "( D ) Reply to a single user.\n( E ) same as D.\n( F ) No defined purpose.\n"+
                "( H ) No defined purpose."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton20ActionPerformed
    
    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("PAS Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("PAS is the command that supplies the\npassword to the hub.\n( B ) No defined purpose.\n"+
                "( D ) No defined purpose.\n( E ) No defined purpose.\n( F ) No defined purpose.\n"+
                "( H ) Send to hub."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton19ActionPerformed
    
    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("INF Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("INF is the information specifier command,\nis used to tell other clients about\none's ADC client.\n( B ) Send info to all other clients.\n"+
                "( D ) No defined purpose.\n( E ) No defined purpose.\n( F ) No defined purpose.\n"+
                "( H ) No defined purpose."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton18ActionPerformed
    
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("SCH Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("SCH is the search command,\nis used to search for files.\n( B ) Send search request to all other clients.\n"+
                "( D ) Search on a single user.\n( E ) same as D.\n( F ) Search featured clients.\n"+
                "( H ) No defined purpose."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton17ActionPerformed
    
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("RCM Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("RCM is the reverse connect to me command,\nis used for requesting a direct\nconnection from another client,\nby a passive TCP user.\n( B ) No defined purpose.\n"+
                "( D ) Requesting from other client.\n( E ) same as D.\n( F ) No defined purpose.\n"+
                "( H ) No defined purpose."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton16ActionPerformed
    
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("CTM Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("CTM is the connect to me command,\nis used for requesting a direct\nconnection from another client.\n( B ) No defined purpose.\n"+
                "( D ) Connecting to other client.\n( E ) same as D.\n( F ) No defined purpose.\n"+
                "( H ) No defined purpose."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed
    
    private void HSTAcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HSTAcheckActionPerformed
        if(HSTAcheck.isSelected())
            
        {
            Main.PopMsg("HSTA changed from \"0\" to \"1\".");
            Vars.HSTA=1;
        } else {
            Main.PopMsg("HSTA changed from \"1\" to \"0\".");
            Vars.HSTA=0;
        }
    }//GEN-LAST:event_HSTAcheckActionPerformed
    
    private void FSTAcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FSTAcheckActionPerformed
        if(FSTAcheck.isSelected())
            
        {
            Main.PopMsg("FSTA changed from \"0\" to \"1\".");
            Vars.FSTA=1;
        } else {
            Main.PopMsg("FSTA changed from \"1\" to \"0\".");
            Vars.FSTA=0;
        }
    }//GEN-LAST:event_FSTAcheckActionPerformed
    
    private void ESTAcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ESTAcheckActionPerformed
        if(ESTAcheck.isSelected())
            
        {
            Main.PopMsg("ESTA changed from \"0\" to \"1\".");
            Vars.ESTA=1;
        } else {
            Main.PopMsg("ESTA changed from \"1\" to \"0\".");
            Vars.ESTA=0;
        }
    }//GEN-LAST:event_ESTAcheckActionPerformed
    
    private void DSTAcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DSTAcheckActionPerformed
        if(DSTAcheck.isSelected())
            
        {
            Main.PopMsg("DSTA changed from \"0\" to \"1\".");
            Vars.DSTA=1;
        } else {
            Main.PopMsg("DSTA changed from \"1\" to \"0\".");
            Vars.DSTA=0;
        }
    }//GEN-LAST:event_DSTAcheckActionPerformed
    
    private void BSTAcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSTAcheckActionPerformed
        if(BSTAcheck.isSelected())
            
        {
            Main.PopMsg("BSTA changed from \"0\" to \"1\".");
            Vars.BSTA=1;
        } else {
            Main.PopMsg("BSTA changed from \"1\" to \"0\".");
            Vars.BSTA=0;
        }
    }//GEN-LAST:event_BSTAcheckActionPerformed
    
    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("STA Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("STA is the status command, can be used.\neither for confirming commands\nor signaling some error.\n( B ) no defined purpose for STA.\n"+
                "( D ) Can be sent to a specified client.\n( E ) same as D.\n( F ) no defined purpose.\n"+
                "( H ) To be sent to hub."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton14ActionPerformed
    
    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("MSG Command");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("MSG is the chat command.\n( B ) is broadcast MSG ( main chat ).\n"+
                "( D ) is direct msg, used for private message.\n( E ) is used for private message too.\n( F ) can be used by ADC clients.\n"+
                "( H ) can be used in some messages from clients."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton13ActionPerformed
    
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("What are this contexts about ?");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("ADC uses a context for each command.\nThat is necessary because every command can be\n"+
                "treated differently according to it's context.\nThe context for each command that you can change:\nBroadcast ( B ) is the context in which.\n"+
                "the command is being sent to all clients connected.\nDirect Message ( D ) is intended for a single user\nand coming from a single user\n"+
                "This is tipically used for requesting direct\nconnection, or perhaps private message.\nDirect Echo Message ( E ) is much alike D,\n"+
                "except that the message is sent to first\nuser too ( echoing it ).\nFeature Broadcast ( F ) is much alike ( B ),\n"+
                "except that the broadcast is not sent to all,\nbut to users that have some feature,\nlike passive searching (sending to active only).\n"+
                "To Hub only ( H ) is a message from a single client\nintended for hub only, like \nnegotiating protocol features."
                
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
    }//GEN-LAST:event_jButton12ActionPerformed
    
    private void HMSGcheckActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_HMSGcheckActionPerformed
    {//GEN-HEADEREND:event_HMSGcheckActionPerformed
        if(HMSGcheck.isSelected())
            
        {
            Main.PopMsg("HMSG changed from \"0\" to \"1\".");
            Vars.HMSG=1;
        } else {
            Main.PopMsg("HMSG changed from \"1\" to \"0\".");
            Vars.HMSG=0;
        }
    }//GEN-LAST:event_HMSGcheckActionPerformed
    
    private void FMSGcheckActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_FMSGcheckActionPerformed
    {//GEN-HEADEREND:event_FMSGcheckActionPerformed
        if(FMSGcheck.isSelected())
            
        {
            Main.PopMsg("FMSG changed from \"0\" to \"1\".");
            Vars.FMSG=1;
        } else {
            Main.PopMsg("FMSG changed from \"1\" to \"0\".");
            Vars.FMSG=0;
        }
    }//GEN-LAST:event_FMSGcheckActionPerformed
    
    private void EMSGcheckActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_EMSGcheckActionPerformed
    {//GEN-HEADEREND:event_EMSGcheckActionPerformed
        if(EMSGcheck.isSelected())
            
        {
            Main.PopMsg("EMSG changed from \"0\" to \"1\".");
            Vars.EMSG=1;
        } else {
            Main.PopMsg("EMSG changed from \"1\" to \"0\".");
            Vars.EMSG=0;
        }
    }//GEN-LAST:event_EMSGcheckActionPerformed
    
    private void DMSGcheckActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_DMSGcheckActionPerformed
    {//GEN-HEADEREND:event_DMSGcheckActionPerformed
        if(DMSGcheck.isSelected())
            
        {
            Main.PopMsg("DMSG changed from \"0\" to \"1\".");
            Vars.DMSG=1;
        } else {
            Main.PopMsg("DMSG changed from \"1\" to \"0\".");
            Vars.DMSG=0;
        }
    }//GEN-LAST:event_DMSGcheckActionPerformed
    
    private void BMSGcheckActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_BMSGcheckActionPerformed
    {//GEN-HEADEREND:event_BMSGcheckActionPerformed
        
        if(BMSGcheck.isSelected())
            
        {
            Main.PopMsg("BMSG changed from \"0\" to \"1\".");
            Vars.BMSG=1;
        } else {
            Main.PopMsg("BMSG changed from \"1\" to \"0\".");
            Vars.BMSG=0;
        }
    }//GEN-LAST:event_BMSGcheckActionPerformed
    
    
    
    private void AccountTableKeyPressed (java.awt.event.KeyEvent evt)//GEN-FIRST:event_AccountTableKeyPressed
    {//GEN-HEADEREND:event_AccountTableKeyPressed
// TODO add your handling code here:
        //SetStatus("lesbo");
        
        if(evt.getKeyCode()==evt.VK_DELETE)
            // SetStatus("gay");
        {
            //need to ureg that reg
            //  int row=AccountTable.getEditingRow ();
            int row=AccountTable.getSelectedRow();
            String CID=(String)AccountTable.getModel().getValueAt(row,0);
            // Main.PopMsg(CID);
            if(AccountsConfig.unreg(CID)) {
                DefaultTableModel AccountModel=(DefaultTableModel) AccountTable.getModel();
                Nod n=AccountsConfig.First;
                int regcount=0;
                while(n!=null) {
                    regcount++;
                    n=n.Next;
                }
                
                if(regcount!=AccountModel.getRowCount()) {
                    AccountModel.setRowCount(0) ;
                    n=AccountsConfig.First;
                    while(n!=null) {
                        String blah00="";
                        Date d=new Date(n.CreatedOn);
                        if(n.LastNI!=null)
                            blah00=n.LastNI;
                        else
                            blah00="Never seen online.";
                        
                        AccountModel.addRow(new Object[]{n.CID,blah00,n.LastIP,n.WhoRegged,d.toString()});
                        n=n.Next;
                    }
                }
                ClientNod temp=ClientNod.FirstClient.NextClient;
                while(temp!=null) {
                    if(temp.cur_client.userok==1) if( (temp.cur_client.ID.equals(CID)))
                        break;
                    temp=temp.NextClient;
                }
                if(temp!=null) {
                    temp.cur_client.sendFromBot(""+"Your account has been deleted. From now on you are a simple user.");
                    temp.cur_client.putOpchat(false);
                    if(temp.cur_client.reg.key){temp.cur_client.OP="";}else{temp.cur_client.RG="";};
                    if(temp.cur_client.reg.key)
                        temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)-1);
                    else
                        temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)-1);
                    temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)+1);
                    new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP":"RG")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                    temp.cur_client.reg=new Nod();
                    Main.PopMsg("User "+temp.cur_client.NI+" with CID "+CID+" found, deleted.");
                } else
                    Main.PopMsg("Reg "+CID+" deleted.");
            }
            
            SetStatus("Reg Deleted");
        }
    }//GEN-LAST:event_AccountTableKeyPressed
    
    private void AccountTableKeyTyped (java.awt.event.KeyEvent evt)//GEN-FIRST:event_AccountTableKeyTyped
    {//GEN-HEADEREND:event_AccountTableKeyTyped
// TODO add your handling code here:
        
    }//GEN-LAST:event_AccountTableKeyTyped
    
    private void jScrollPane2KeyPressed (java.awt.event.KeyEvent evt)//GEN-FIRST:event_jScrollPane2KeyPressed
    {//GEN-HEADEREND:event_jScrollPane2KeyPressed
// TODO add your handling code here:
        
    }//GEN-LAST:event_jScrollPane2KeyPressed
    
    private void jButton5ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
    {//GEN-HEADEREND:event_jButton5ActionPerformed
// TODO add your handling code here:
        JDialog bla =new JDialog(this,true);
        JPanel jp=new JPanel();
        
        bla.setSize(300,400);
        bla.setTitle("What this means ?");
        
        bla.getContentPane().add(jp);
        JTextArea jl=new JTextArea("DSHub has now powerful searching features.\nFirst, we need to make a distinction between the\nautomagic "+
                "and the user searches.\n First type is made by client at a regular interval\nand DSHub keeps a liniar spam setting.\n"+
                " Second type are user searches ( manual searches )\nthat the user takes.\nFor this type (because of the human factor)\n"+
                "DSHub keeps a logarithmic spam setting.\nThis way, the 2nd search is at search_log_base\ninterval, but third, is at search_log_base^2\n"+
                "and so on, until the power gets to max_steps.\n After this point, the user needs to wait\nsearch_spam_reset seconds to get his burst back.\n"+
                "The searches are being kept in queue ( not ignored !)\nand are processed once the timeout is completed\nso user doesnt need to search again\n"+
                "but just wait for his search to be completed.\nThe messages appears as a fictive result\nin his search box, which will be filled\n"+
                "once the search string is being sent to others.\n"
                );
        // jl.setSize (100,30);
        jp.add(jl);
        // jp.add(new JLabel("test"));
        bla.setVisible(true);
        // jc.add (new JLabel("Blabla"));
    }//GEN-LAST:event_jButton5ActionPerformed
    
    private void savelogscheckActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_savelogscheckActionPerformed
    {//GEN-HEADEREND:event_savelogscheckActionPerformed
// TODO add your handling code here:
        if(savelogscheck.isSelected())
            //Main.PopMsg("clicked");
        {
            Main.PopMsg("Save_logs changed from \"0\" to \"1\".");
            Vars.savelogs=1;
        } else {
            Main.PopMsg("Save_logs changed from \"1\" to \"0\".");
            Vars.savelogs=0;
        }
    }//GEN-LAST:event_savelogscheckActionPerformed
    
    private void jButton11ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton11ActionPerformed
    {//GEN-HEADEREND:event_jButton11ActionPerformed
// TODO add your handling code here:
        String Thing=opchatnamefield.getText();
        try {
            String aucsy=Vars.Opchat_name;
            if(!(aucsy.equals(Thing))) {
                if(!Vars.ValidateNick(Thing)) {
                    throw new Exception();
                }
                ClientNod tempy=null;
                if(ClientNod.FirstClient!=null)
                    tempy=ClientNod.FirstClient.NextClient;
                
                while(tempy!=null) {
                    if(tempy.cur_client.userok==1) if( (tempy.cur_client.NI.toLowerCase().equals(Thing.toLowerCase())))
                        break;
                    tempy=tempy.NextClient;
                    
                }
                if(tempy!=null) {
                    // System.out.println("Nick taken, please choose another.");
                    throw new Exception();
                }
                Vars.Opchat_name=Thing;
                Main.PopMsg("Opchat_name changed from \""+aucsy+"\" to \""+Thing+"\".");
                new Broadcast("BINF ABCD NI"+Vars.Opchat_name,10);
                
            }
        } catch (Exception e) {
            opchatnamefield.setText(Vars.Opchat_name);
        }
        Thing=opchatdescfield.getText();
        
        String aucsy=Vars.Opchat_desc;
        if(!(aucsy.equals(Thing))) {
            
            Vars.Opchat_desc=Thing;
            Main.PopMsg("Opchat_desc changed from \""+aucsy+"\" to \""+Thing+"\".");
            new Broadcast("BINF ABCD DE"+ADC.retADCStr(Vars.Opchat_desc),10);
            
        }
        Thing=historylinesfield.getText();
        try {
            int aucs=Vars.history_lines;
            if(aucs!=Integer.parseInt(Thing)) {
                Vars.history_lines=Integer.parseInt(Thing.toString());
                Main.PopMsg("History_lines changed from \""+Integer.toString(aucs)+"\" to \""+Vars.history_lines+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            historylinesfield.setText(Integer.toString(Vars.history_lines));
        }
        Thing=kicktimefield.getText();
        try {
            int aucs=Vars.kick_time;
            if(aucs!=Integer.parseInt(Thing)) {
                Vars.kick_time=Integer.parseInt(Thing.toString());
                Main.PopMsg("Kick_time changed from \""+Integer.toString(aucs)+"\" to \""+Vars.kick_time+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            kicktimefield.setText(Integer.toString(Vars.kick_time));
        }
        
        
        Thing=redirecturl.getText();
        
        aucsy=Vars.redirect_url;
        if(!(aucsy.equals(Thing))) {
            
            Vars.redirect_url=Thing;
            Main.PopMsg("Redirect_url changed from \""+aucsy+"\" to \""+Thing+"\".");
            
        }
        
        SetStatus("Miscellaneous settings saved.");
    }//GEN-LAST:event_jButton11ActionPerformed
    
    private void jButton10ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton10ActionPerformed
    {//GEN-HEADEREND:event_jButton10ActionPerformed
// TODO add your handling code here:
        String Thing=maxchatmsgfield.getText();
        try {
            int aucsy=Vars.max_chat_msg;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_chat_msg=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_chat_msg changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_chat_msg+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxchatmsgfield.setText(Integer.toString(Vars.max_chat_msg));
        }
        Thing=chatintervalfield.getText();
        try {
            int aucsy=Vars.chat_interval;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.chat_interval=Integer.parseInt(Thing.toString());
                Main.PopMsg("Chat_interval changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.chat_interval+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            chatintervalfield.setText(Integer.toString(Vars.chat_interval));
        }
        
        Thing=automagicsearchfield.getText();
        try {
            int aucsy=Vars.automagic_search;
            if(aucsy!=Integer.parseInt(Thing)) {
                
                Vars.automagic_search=Integer.parseInt(Thing.toString());
                Main.PopMsg("Automagic_search changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.automagic_search+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            automagicsearchfield.setText(Integer.toString(Vars.automagic_search));
        }
        Thing=searchlogbasefield.getText();
        try {
            int aucsy=Vars.search_log_base;
            if(aucsy!=Integer.parseInt(Thing)) {
                
                Vars.search_log_base=Integer.parseInt(Thing.toString());
                Main.PopMsg("Search_log_base changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.search_log_base+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            searchlogbasefield.setText(Integer.toString(Vars.search_log_base));
        }
        Thing=searchstepsfield.getText();
        try {
            int aucsy=Vars.search_steps;
            if(aucsy!=Integer.parseInt(Thing)) {
                
                Vars.search_steps=Integer.parseInt(Thing.toString());
                Main.PopMsg("Search_steps changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.search_steps+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            searchstepsfield.setText(Integer.toString(Vars.search_steps));
        }
        Thing=searchspamresetfield.getText();
        try {
            int aucsy=Vars.search_spam_reset;
            if(aucsy!=Integer.parseInt(Thing)) {
                
                Vars.search_spam_reset=Integer.parseInt(Thing.toString());
                Main.PopMsg("Search_spam_reset changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.search_spam_reset+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            searchspamresetfield.setText(Integer.toString(Vars.search_spam_reset));
        }
        
        Thing=msgsearchspamfield.getText();
        
        String aucsy=Vars.Msg_Search_Spam;
        if(!(aucsy.equals(Thing))) {
            
            Vars.Msg_Search_Spam=Thing;
            Main.PopMsg("Msg_Search_Spam changed from \""+aucsy+"\" to \""+Thing+"\".");
            
        }
        
        SetStatus("Spam settings saved.");
    }//GEN-LAST:event_jButton10ActionPerformed
    
    private void jButton9ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton9ActionPerformed
    {//GEN-HEADEREND:event_jButton9ActionPerformed
// TODO add your handling code here:
        String Thing=maxusersfield.getText();
        try {
            int aucsy=Vars.max_users;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_users=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_users changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_users+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxusersfield.setText(Integer.toString(Vars.max_users));
        }
        Thing=nickcharsfield.getText();
        
        String aucsy=Vars.nick_chars;
        if(!(aucsy.equals(Thing))) {
            Vars.nick_chars=Thing;
            Main.PopMsg("Nick_chars changed from \""+aucsy+"\" to \""+Thing+"\".");
        }
        nickcharsfield.setText( aucsy);
        Thing=maxschcharsfield.getText();
        try {
            int aucs=Vars.max_sch_chars;
            if(aucs!=Integer.parseInt(Thing)) {
                Vars.max_sch_chars=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_sch_chars changed from \""+Integer.toString(aucs)+"\" to \""+Vars.max_sch_chars+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxschcharsfield.setText(Integer.toString(Vars.max_sch_chars));
        }
        
        SetStatus("Restrictions settings saved.");
        
    }//GEN-LAST:event_jButton9ActionPerformed
    
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton8ActionPerformed
    {//GEN-HEADEREND:event_jButton8ActionPerformed
// TODO add your handling code here:
        /** timeout_login*/
        String Thing=fieldtimeout.getText();
        try {
            int aucsy=Vars.Timeout_Login;
            int aux=Integer.parseInt(Thing);
            if(aucsy!=aux) {
                Vars.Timeout_Login=aux;
                Main.PopMsg("Timeout_Login changed from \""+Integer.toString(aucsy)+"\" to \""+Thing+"\".");
            }
        } catch(NumberFormatException nfe) {
            // System.out.println("Invalid number");
            fieldtimeout.setText(Integer.toString(Vars.Timeout_Login));
        }
        
        Thing=maxnifield.getText();
        try {
            int aucsy=Vars.max_ni;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_ni=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_ni changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_ni+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxnifield.setText(Integer.toString(Vars.max_ni));
        }
        Thing=minnifield.getText();
        try {
            int aucsy=Vars.min_ni;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.min_ni=Integer.parseInt(Thing.toString());
                Main.PopMsg("Min_ni changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.min_ni+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            minnifield.setText(Integer.toString(Vars.min_ni));
        }
        Thing=maxdefield.getText();
        try {
            int aucsy=Vars.max_de;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_de=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_de changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_de+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxdefield.setText(Integer.toString(Vars.max_de));
        }
        Thing=maxsharefield.getText();
        try {
            Long aucsy=Vars.max_share;
            if(aucsy!=Long.parseLong(Thing)) {
                Vars.max_share=Long.parseLong(Thing.toString());
                Main.PopMsg("Max_share changed from \""+Long.toString(aucsy)+"\" to \""+Vars.max_share+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxsharefield.setText(Long.toString(Vars.min_share));
        }
        Thing=minsharefield.getText();
        try {
            Long aucsy=Vars.min_share;
            if(aucsy!=Long.parseLong(Thing)) {
                Vars.min_share=Long.parseLong(Thing.toString());
                Main.PopMsg("Min_share changed from \""+Long.toString(aucsy)+"\" to \""+Vars.min_share+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            minsharefield.setText(Long.toString(Vars.min_share));
        }
        Thing=maxslfield.getText();
        try {
            int aucsy=Vars.max_sl;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_sl=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_sl changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_sl+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxslfield.setText(Integer.toString(Vars.max_sl));
        }
        Thing=minslfield.getText();
        try {
            int aucsy=Vars.min_sl;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.min_sl=Integer.parseInt(Thing.toString());
                Main.PopMsg("Min_sl changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.min_sl+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            minslfield.setText(Integer.toString(Vars.min_sl));
        }
        Thing=maxemfield.getText();
        try {
            int aucsy=Vars.max_em;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_em=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_em changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_em+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxemfield.setText(Integer.toString(Vars.max_em));
        }
        Thing=maxhubsopfield.getText();
        try {
            int aucsy=Vars.max_hubs_op;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_hubs_op=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_hubs_op changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_hubs_op+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxhubsopfield.setText(Integer.toString(Vars.max_hubs_op));
        }
        Thing=maxhubsregfield.getText();
        try {
            int aucsy=Vars.max_hubs_reg;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_hubs_reg=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_hubs_reg changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_hubs_reg+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxhubsregfield.setText(Integer.toString(Vars.max_hubs_reg));
        }
        Thing=maxhubsuserfield.getText();
        try {
            int aucsy=Vars.max_hubs_user;
            if(aucsy!=Integer.parseInt(Thing)) {
                Vars.max_hubs_user=Integer.parseInt(Thing.toString());
                Main.PopMsg("Max_hubs_user changed from \""+Integer.toString(aucsy)+"\" to \""+Vars.max_hubs_user+"\".");
            }
            
        } catch(NumberFormatException nfe) {
            maxhubsuserfield.setText(Integer.toString(Vars.max_hubs_user));
        }
        
        Main.Server.rewriteconfig();
        SetStatus("Restrictions settings saved.");
    }//GEN-LAST:event_jButton8ActionPerformed
    
    private void regonlycheckActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_regonlycheckActionPerformed
    {//GEN-HEADEREND:event_regonlycheckActionPerformed
// TODO add your handling code here:
        if(regonlycheck.isSelected())
            //Main.PopMsg("clicked");
        {
            Main.PopMsg("Reg_only changed from \"0\" to \"1\".");
            Vars.reg_only=1;
        } else {
            Main.PopMsg("Reg_only changed from \"1\" to \"0\".");
            Vars.reg_only=0;
        }
    }//GEN-LAST:event_regonlycheckActionPerformed
    
    public void insertLog(String bla) {
        LogText.append(bla+"\n");
    }
    
    private void jButton7ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton7ActionPerformed
    {//GEN-HEADEREND:event_jButton7ActionPerformed
// TODO add your handling code here:
        String thenewport= portfield.getText();
        //System.out.println (newport);
        try {
            int x=Integer.parseInt(thenewport);
            if(x!=Vars.Default_Port)
                if(x>3 && x<65000) {
                int y=Vars.Default_Port;
                Vars.Default_Port=x;
                //Main.Server.vars.Default_Port=x;
                Main.PopMsg("New Default Port change from "+y+" to "+x+".");
                }
        } catch (Exception e) {
        }
        String newtopic= topicfield.getText();
        if(!(newtopic.equals(Vars.HubDE)))
            if(newtopic.equals("")) {
            
            new Broadcast("IINF DE");
            if(!Vars.HubDE.equals("")) {
                //System.out.println("Topic \""+Vars.HubDE+"\" deleted.");
                new Broadcast("IMSG Topic was deleted by Server.");
                Main.PopMsg("Topic was deleted by Server.");
            } else
                //System.out.println("There wasn't any topic anyway.");
                Vars.HubDE="";
            
            
            
            } else {
            String auxbuf=newtopic;
            
            
            Vars.HubDE=Vars.HubDE;
            // System.out.println("Topic changed from \""+Vars.HubDE+"\" "+"to \""+auxbuf+"\".");
            auxbuf=auxbuf;
            Vars.HubDE=auxbuf;
            
            new Broadcast("IINF DE"+ADC.retADCStr(auxbuf));
            new Broadcast("IMSG Topic was changed by Server to \""+Vars.HubDE+"\".");
            Main.PopMsg("Topic was changed by Server to \""+Vars.HubDE+"\".");
            
            }
        /**hub name*/
        String NowName=namefield.getText();
        
        if(!(NowName.equals(Vars.HubName))) {
            
            Main.PopMsg("Hub_Name changed from \""+Vars.HubName+"\" to \""+NowName+"\".");
            Vars.HubName=NowName.toString();
            
            new Broadcast("IINF NI"+ADC.retADCStr(Vars.HubName));
        }
        
        
        /**bot_name*/
        String Thing=botnamefield.getText();
        try {
            String aucsy=Vars.bot_name;
            if(!(aucsy.equals(Thing))) {
                if(!Vars.ValidateNick(Thing)) {
                    throw new Exception();
                }
                ClientNod tempy=null;
                if(ClientNod.FirstClient!=null)
                    tempy=ClientNod.FirstClient.NextClient;
                
                while(tempy!=null) {
                    if(tempy.cur_client.userok==1) if( (tempy.cur_client.NI.toLowerCase().equals(Thing.toLowerCase())))
                        break;
                    tempy=tempy.NextClient;
                    
                }
                if(tempy!=null) {
                    // System.out.println("Nick taken, please choose another.");
                    throw new Exception();
                }
                Vars.bot_name=Thing;
                Main.PopMsg("bot_name changed from \""+aucsy+"\" to \""+Thing+"\".");
                new Broadcast("BINF DCBA NI"+ADC.retADCStr(Vars.bot_name));
                
            }
        } catch (Exception e) {
            botnamefield.setText(Vars.bot_name);
        }
        /**bot desc*/
        Thing=botdescfield.getText();
        
        String aucsy=Vars.bot_desc;
        if(!(aucsy.equals(Thing))) {
            
            Vars.bot_desc=Thing;
            Main.PopMsg("Bot_desc changed from \""+aucsy+"\" to \""+Thing+"\".");
            new Broadcast("BINF DCBA DE"+ADC.retADCStr(Vars.bot_desc));
            
        }
        
        
        /** hub host */
      setHost();
        
        /** proxy settings */
        SetProxy();
        
        Main.Server.rewriteconfig();
        refreshAll();
        
        
        SetStatus("Main settings saved.");
        
    }//GEN-LAST:event_jButton7ActionPerformed
    private void setHost()
    {
          String new_name=hubhostfield.getText();
        if(new_name==null)
        {
              JOptionPane.showMessageDialog(null,"Hub_host cannot be null",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
        else if("".equals(new_name))
        {
              JOptionPane.showMessageDialog(null,"Hub_host cannot be empty.",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
         if(!(new_name.equals(Vars.Hub_Host))) 
         {
          
                      
                        
                       
                       InetSocketAddress myHost=new InetSocketAddress(new_name,Vars.Default_Port);
                       Vector localAddies=new Vector();
                       try{
Enumeration<NetworkInterface> eNI =
NetworkInterface.getNetworkInterfaces();

NetworkInterface cNI;
Enumeration<InetAddress> eIA;
InetAddress cIA;

for(;eNI.hasMoreElements();){
cNI = eNI.nextElement();
eIA = cNI.getInetAddresses();

for(;eIA.hasMoreElements();){
cIA = eIA.nextElement();
localAddies.add(cIA.getHostAddress());
//System.out.println("IP Local = " + cIA.getHostAddress());
}
}
}
catch(SocketException eS)
{
    JOptionPane.showMessageDialog(null,new_name+" cannot be resolved or machine hostname badly set.",
                    "Error",JOptionPane.ERROR_MESSAGE);

return;
} 
                       if(myHost.isUnresolved())
                       {
                           JOptionPane.showMessageDialog(null,new_name+" cannot be resolved or DNS server failure.",
                    "Error",JOptionPane.ERROR_MESSAGE);
              
                       return;
                       }
                       try
                       {
                       
                        InetAddress addresses[] = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
                        boolean ok=false;
                        Iterator myIT=localAddies.iterator();
                        while(myIT.hasNext())
                        
                              if(myHost.getAddress().getHostAddress().equals(myIT.next()))
                                     ok=true;
                       //ok=false;
                         if(!ok)
                       {
                          int result=JOptionPane.showConfirmDialog(this, "Press ok to scan hub_host ( may take a while) \nso please be patient",
                                  Vars.HubName,JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE);
                          if(result==JOptionPane.NO_OPTION)
                              return;
                        if(!(HostTester.hostOK(new_name)))
                        {
                            JOptionPane.showMessageDialog(null,new_name+" does not point to one of your eth interfaces. "+
                                    "\nReasons: DNS not correctly set;  you dont have a external real IP \n(if you are creating"+"" +
                                    " LAN hub, use your LAN local IP as a hub_host);\nnot even package routing to your system work.",
                    "Error",JOptionPane.ERROR_MESSAGE);
                            
                       return;
                        }
                         }
                        Main.PopMsg("Hub_host changed from \""+
                                Vars.Hub_Host+"\" to \""+new_name+"\".");
                        
                        Vars.Hub_Host=new_name;
                        Main.Server.rewriteconfig();
                         
                       }
                       catch (UnknownHostException uhe)
                       {
                          JOptionPane.showMessageDialog(null,new_name+" cannot be resolved or DNS server failure.",
                    "Error",JOptionPane.ERROR_MESSAGE);
                       }
                       
                       
         }
    }
    private void SetProxy()
    {
        if(!proxycheck.isSelected())
        {
              if(!("".equals(Vars.Proxy_Host) ))   
         Main.PopMsg("Proxy_Host changed from \""+Vars.Proxy_Host+"\" to \""+(Vars.Proxy_Host="")+"\".");
    if(0!=Vars.Proxy_Port)    
         Main.PopMsg("Proxy_Port changed from \""+Vars.Proxy_Port+"\" to \""+(Vars.Proxy_Port=0)+"\".");
    proxyhostfield.setEditable(false);
    proxyportfield.setEditable(false);
    proxyhostfield.setText("");
    proxyportfield.setText("");
    
    refreshAll();
    return;
        }
        if(proxyhostfield.getText().equals("")&& proxycheck.isSelected())
        {
            refreshAll();
       return;
        }
        try{
        if((!(Vars.Proxy_Host.equals(proxyhostfield.getText())) || Vars.Proxy_Port!=Integer.parseInt(proxyportfield.getText()))
        ||(!Vars.Proxy_Host.equals("") && !proxycheck.isSelected()))
        {

    proxyhostfield.setEditable(true);
    proxyportfield.setEditable(true);
   
    
     int x=Integer.parseInt(proxyportfield.getText());
    
    if((x<1 || x> 65355))
    {
       this.SetStatus("Invalid port number.",JOptionPane.ERROR_MESSAGE);
       refreshAll();
       return;
        
    }
    else
     if(x!=Vars.Proxy_Port)    
         Main.PopMsg("Proxy_Port changed from \""+Vars.Proxy_Port+"\" to \""+(Vars.Proxy_Port=x)+"\".");
  
    
    if(Vars.Proxy_Port==0)
                    {
                        this.SetStatus("Setup proxy port first.",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                       String neww=proxyhostfield.getText();
                      try
                      {
                       new Proxy(Proxy.Type.HTTP,new InetSocketAddress(neww,Vars.Proxy_Port));
                      }
                      catch(Exception e)
                      {
                          this.SetStatus("Invalid proxy. Possible reasons: incorrect input, domain resolution failure, invalid proxy.",JOptionPane.ERROR_MESSAGE);
                          return;
                      }
    String y=proxyhostfield.getText();
    if(!(y.equals(Vars.Proxy_Port) ))   
         Main.PopMsg("Proxy_Host changed from \""+Vars.Proxy_Host+"\" to \""+(Vars.Proxy_Host=y)+"\".");
    

        }
        }catch(NumberFormatException nfe)
        {
            this.SetStatus("Invalid port number.",JOptionPane.ERROR_MESSAGE);
       refreshAll();
       return;
        }
    }
    
    private void portfieldFocusLost (java.awt.event.FocusEvent evt)//GEN-FIRST:event_portfieldFocusLost
    {//GEN-HEADEREND:event_portfieldFocusLost
// TODO add your handling code here:
        
    }//GEN-LAST:event_portfieldFocusLost
    
    private void portfieldKeyTyped (java.awt.event.KeyEvent evt)//GEN-FIRST:event_portfieldKeyTyped
    {//GEN-HEADEREND:event_portfieldKeyTyped
// TODO add your handling code here:
        
    }//GEN-LAST:event_portfieldKeyTyped
    
    private void portfieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_portfieldActionPerformed
    {//GEN-HEADEREND:event_portfieldActionPerformed
// TODO add your handling code here:
        
    }//GEN-LAST:event_portfieldActionPerformed
    
    private void jButton6ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
    {//GEN-HEADEREND:event_jButton6ActionPerformed
// TODO add your handling code here:
        Runtime myRun=Runtime.getRuntime();
        
        
        int i=0,j=0;
        if(ClientNod.FirstClient!=null) {
            ClientNod temp=ClientNod.FirstClient.NextClient;
            while(temp!=null) {
                if(temp.cur_client.userok==1)
                    i++;
                else j++;
                temp=temp.NextClient;
            }
        }
        
        long up=System.currentTimeMillis()-Main.curtime; //uptime in millis
        
        Date b=new Date(Main.curtime       );
        jTextArea2.setText(
                "Death Squad Hub. Version "+Vars.HubVersion+".\n"+
                "  Running on "+Main.Proppies.getProperty("os.name")+" Version "+Main.Proppies.getProperty("os.version")+" on Architecture "+Main.Proppies.getProperty("os.arch")+"\n"+
                "  Java Runtime Environment "+Main.Proppies.getProperty("java.version")+" from "+Main.Proppies.getProperty("java.vendor")+"\n"+
                "  Java Virtual Machine "+Main.Proppies.getProperty("java.vm.specification.version")+"\n"+
                "  Available CPU's to JVM "+Integer.toString(myRun.availableProcessors())+"\n"+
                "  Available Memory to JVM: "+Long.toString(myRun.maxMemory())+" Bytes, where free: "+Long.toString(myRun.freeMemory())+" Bytes\n"+
                "Hub Statistics:\n"+
                "  Online users: "+Integer.toString(i)+"\n"+
                "  Connecting users: "+Integer.toString(j)+"\n"+
                "  Uptime: "+TimeConv.getStrTime(up)+"\n"+
                "  Start Time: "+b.toString()+
                "\n  Bytes red per second: "+Main.Server.IOSM.getTotalByteReadThroughput()+
                "\n  Bytes written per second: "+Main.Server.IOSM.getTotalByteWrittenThroughput()
                
                );
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void jPanel6MouseMoved (java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel6MouseMoved
    {//GEN-HEADEREND:event_jPanel6MouseMoved
// TODO add your handling code here:
        
       // refreshAll();
    }//GEN-LAST:event_jPanel6MouseMoved
    
    public void refreshInit()
    {
        resizeBanTable(2,7,6,5,6,7,10,4);
        insertBans();
        /**setting window name*/
        //System.out.println("gay");
        
        this.setTitle(Vars.HubName+" running on "+Vars.HubVersion+" A New Generation 2007");
        //jLabel51.setIcon (new Icon("ds.bmp"));
        //addImage(jPanel1, "ds.bmp");
        /**showing accounts*/
        
        
        AccountTable.setAutoResizeMode(AccountTable.AUTO_RESIZE_OFF);
        AccountTable.getColumnModel().getColumn(0).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(1).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(2).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(3).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(4).setPreferredWidth(AccountTable.getWidth()/5);
    }
    public void refreshAll()
    {
        refreshInit();
        
        
        DefaultTableModel AccountModel=(DefaultTableModel) AccountTable.getModel();
        Nod n=AccountsConfig.First;
        int regcount=0;
        while(n!=null) {
            regcount++;
            n=n.Next;
        }
        
        if(regcount!=AccountModel.getRowCount()) {
            AccountModel.setRowCount(0) ;
            n=AccountsConfig.First;
            while(n!=null) {
                String blah00="";
                Date d=new Date(n.CreatedOn);
                if(n.LastNI!=null)
                    blah00=n.LastNI;
                else
                    blah00="Never seen online.";
                
                AccountModel.addRow(new Object[]{n.CID,blah00,n.LastIP,n.WhoRegged,d.toString()});
                n=n.Next;
            }
        }
        //  blah00=blah00.substring (0,blah00.length ()-2);
        // System.out.println (blah00);
        
        /**setting stuff*/
        jTextArea1.setText("DSHub is ADC software so you need an ADC compatibile client.\n"+
                "At the moment of this release ( January 2008 ), the following ADC clients were available:\n"+
                "dc++ 0.691, icedc 1.01a, zion++ 2.04  apexdc 0.3.0, strongdc  2.01 , zk++  0.7, BCDC 0.69, FMDC, Elise or ANY later version of those will be ADC compatible.\n"+
                "So after you start the Hub, try connecting to adc://127.0.0.1:411\n"+
                "Some ADC reminders:\n"+
                "-- You need to connect to address adc://\n"+
                "-- There is no default port, every time one must be specified ( like 411 on NMDC)\n"+
                "-- Accounts are on CID not nick ( you can use what nick you want )\n"+
                "-- Clients that are not ADC compat or dont use the address correctly will just hang up and you will see them at Connecting Users in stats command.\n"+
                "Oh and another thing, NMDC hublists dont work with ADC, so i got 2 fine lists that support ADC for you:\n"+
                "  www.hubtracker.com\n"+
                "  www.adchublist.com\n"+
                "Thanks for using DSHub and I hope you will have as much fun using it as I had creating it ;)\n\n"+
                "For latest version, updates, any suggestions, information, or just anything visit www.death-squad.ro/dshub");
        
        Runtime myRun=Runtime.getRuntime();
        
        
        int i=0,j=0;
        if(ClientNod.FirstClient!=null) {
            ClientNod temp=ClientNod.FirstClient.NextClient;
            while(temp!=null) {
                if(temp.cur_client.userok==1)
                    i++;
                else j++;
                temp=temp.NextClient;
            }
        }
        
        long up=System.currentTimeMillis()-Main.curtime; //uptime in millis
        
        Date b=new Date(Main.curtime   );
        jTextArea2.setText(
                "Death Squad Hub. Version "+Vars.HubVersion+".\n"+
                "  Running on "+Main.Proppies.getProperty("os.name")+" Version "+Main.Proppies.getProperty("os.version")+" on Architecture "+Main.Proppies.getProperty("os.arch")+"\n"+
                "  Java Runtime Environment "+Main.Proppies.getProperty("java.version")+" from "+Main.Proppies.getProperty("java.vendor")+"\n"+
                "  Java Virtual Machine "+Main.Proppies.getProperty("java.vm.specification.version")+"\n"+
                "  Available CPU's to JVM "+Integer.toString(myRun.availableProcessors())+"\n"+
                "  Available Memory to JVM: "+Long.toString(myRun.maxMemory())+" Bytes, where free: "+Long.toString(myRun.freeMemory())+" Bytes\n"+
                "Hub Statistics:\n"+
                "  Online users: "+Integer.toString(i)+"\n"+
                "  Connecting users: "+Integer.toString(j)+"\n"+
                "  Uptime: "+TimeConv.getStrTime(up)+"\n"+
                "  Start Time: "+b.toString()+
                "\n  Bytes red per second: "+(Main.Server.IOSM==null?"0.0":Main.Server.IOSM.getTotalByteReadThroughput())+
                "\n  Bytes written per second: "+(Main.Server.IOSM==null?"0.0":Main.Server.IOSM.getTotalByteWrittenThroughput())
                
                );
        
        
/*
 * max_em                  128        -- Maximum e-mail string size, integer.
   max_hubs_op             100       -- Maximum hubs where user is op, integer.
   max_hubs_reg            30       -- Maximum hubs where user is reg, integer.
   max_hubs_user           200         -- Maximum hubs where user is user, integer.
   max_sch_chars           256      -- Maximum search chars, integer.
   min_sch_chars           3      -- Minimum search chars, integer.
   max_chat_msg            512       -- Maximum chat message size, integer.
   max_users               1000         -- Maximum number of online users, integer.
   history_lines           50         -- Number of lines to keep in chat history.
   opchat_name             OpChat       -- The Operator Chat Bot Nick.
   opchat_desc             BoT       -- The Operator Chat Bot Description.
   kick_time               300         -- The time to ban a user with a kick, in seconds.
   msg_banned              Have a nice day and don't forget to smile !        -- The aditional message to show to banned users when connecting.
   msg_full                Have a nice day and don't forget to smile !      -- Message to be shown to connecting users when hub full.
   reg_only                0      -- 1 = registered only hub. 0 = otherwise.
   nick_chars              ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890[]()-.,;'`~*&^%$#@!+=_|{}<>:         -- Chars that could be used for a nick, String.
   chat_interval           500         -- Interval between chat lines, millis, Integer.
 */
        portfield.setText(Integer.toString(Vars.Default_Port));
        
        hubhostfield.setText(Vars.Hub_Host);
        
        topicfield.setText(Vars.HubDE);
        
        fieldtimeout.setText(Integer.toString(Vars.Timeout_Login));
        
        namefield.setText(Vars.HubName);
        
        maxnifield.setText(Integer.toString(Vars.max_ni));
        
        minnifield.setText(Integer.toString(Vars.min_ni));
        
        maxdefield.setText(Integer.toString(Vars.max_de));
        
        maxsharefield.setText(Long.toString(Vars.max_share));
        
        minsharefield.setText(Long.toString(Vars.min_share));
        
        maxslfield.setText(Integer.toString(Vars.max_sl));
        
        minslfield.setText(Integer.toString(Vars.min_sl));
        
        maxemfield.setText(Integer.toString(Vars.max_em));
        
        maxhubsopfield.setText(Integer.toString(Vars.max_hubs_op));
        
        maxhubsuserfield.setText(Integer.toString(Vars.max_hubs_reg));
        
        maxhubsuserfield.setText(Integer.toString(Vars.max_hubs_user));
        
        maxschcharsfield.setText(Integer.toString(Vars.max_sch_chars));
        
        minschcharsfield.setText(Integer.toString(Vars.min_sch_chars));
        
        maxchatmsgfield.setText(Integer.toString(Vars.max_chat_msg));
        
        maxusersfield.setText(Integer.toString(Vars.max_users));
        
        
        
        
        
        
        historylinesfield.setText(Integer.toString(Vars.history_lines));
        
        opchatnamefield.setText(Vars.Opchat_name);
        
        opchatdescfield.setText(Vars.Opchat_desc);
        
        kicktimefield.setText(Integer.toString(Vars.kick_time));
        
        msgbannedfield.setText(Vars.Msg_Banned);
        
        msgfullfield.setText(Vars.Msg_Full);
        
        if(Vars.reg_only==1)
            regonlycheck.setSelected(true);
        else
            regonlycheck.setSelected(false);
        if(Vars.savelogs==1)
            savelogscheck.setSelected(true);
        else
            savelogscheck.setSelected(false);
        
        nickcharsfield.setText(Vars.nick_chars);
        
        chatintervalfield.setText(Integer.toString(Vars.chat_interval));
        
        
        automagicsearchfield.setText(Integer.toString(Vars.automagic_search));
        searchlogbasefield.setText(Integer.toString(Vars.search_log_base));
        searchstepsfield.setText(Integer.toString(Vars.search_steps));
        msgsearchspamfield.setText(Vars.Msg_Search_Spam);
        searchspamresetfield.setText(Integer.toString(Vars.search_spam_reset));
        botnamefield.setText(Vars.bot_name);
        
        botdescfield.setText(Vars.bot_desc);
        
       redirecturl.setText(Vars.redirect_url);
        
        
        if(!Vars.Proxy_Host.equals(""))
        {
            proxycheck.setSelected(true);
            proxyhostfield.setText(Vars.Proxy_Host);
            proxyportfield.setText(Integer.toString(Vars.Proxy_Port));
            proxyportfield.setEditable(true);
            proxyhostfield.setEditable(true);
        }
        else
        {
            proxycheck.setSelected(false);
            proxyhostfield.setText(null);
            proxyportfield.setText(null);
            proxyportfield.setEditable(false);
            proxyhostfield.setEditable(false);
        }
        
        
        
        if(Vars.BMSG==1)
            BMSGcheck.setSelected(true);
        else
            BMSGcheck.setSelected(false);
        if(Vars.EMSG==1)
            EMSGcheck.setSelected(true);
        else
            EMSGcheck.setSelected(false);
        if(Vars.DMSG==1)
            DMSGcheck.setSelected(true);
        else
            DMSGcheck.setSelected(false);
        if(Vars.HMSG==1)
            HMSGcheck.setSelected(true);
        else
            HMSGcheck.setSelected(false);
        if(Vars.FMSG==1)
            FMSGcheck.setSelected(true);
        else
            FMSGcheck.setSelected(false);
        if(Vars.BSTA==1)
            BSTAcheck.setSelected(true);
        else
            BSTAcheck.setSelected(false);
        if(Vars.ESTA==1)
            ESTAcheck.setSelected(true);
        else
            ESTAcheck.setSelected(false);
        if(Vars.DSTA==1)
            DSTAcheck.setSelected(true);
        else
            DSTAcheck.setSelected(false);
        if(Vars.HSTA==1)
            HSTAcheck.setSelected(true);
        else
            HSTAcheck.setSelected(false);
        if(Vars.FSTA==1)
            FSTAcheck.setSelected(true);
        else
            FSTAcheck.setSelected(false);
        
        if(Vars.BCTM==1)
            BCTMcheck.setSelected(true);
        else
            BCTMcheck.setSelected(false);
        if(Vars.ECTM==1)
            ECTMcheck.setSelected(true);
        else
            ECTMcheck.setSelected(false);
        if(Vars.DCTM==1)
            DCTMcheck.setSelected(true);
        else
            DCTMcheck.setSelected(false);
        if(Vars.HCTM==1)
            HCTMcheck.setSelected(true);
        else
            HCTMcheck.setSelected(false);
        if(Vars.FCTM==1)
            FCTMcheck.setSelected(true);
        else
            FCTMcheck.setSelected(false);
        
        if(Vars.BRCM==1)
            BRCMcheck.setSelected(true);
        else
            BRCMcheck.setSelected(false);
        if(Vars.ERCM==1)
            ERCMcheck.setSelected(true);
        else
            ERCMcheck.setSelected(false);
        if(Vars.DRCM==1)
            DRCMcheck.setSelected(true);
        else
            DRCMcheck.setSelected(false);
        if(Vars.HRCM==1)
            HRCMcheck.setSelected(true);
        else
            HRCMcheck.setSelected(false);
        if(Vars.FRCM==1)
            FRCMcheck.setSelected(true);
        else
            FRCMcheck.setSelected(false);
        
        if(Vars.BINF==1)
            BINFcheck.setSelected(true);
        else
            BINFcheck.setSelected(false);
        if(Vars.EINF==1)
            EINFcheck.setSelected(true);
        else
            EINFcheck.setSelected(false);
        if(Vars.DINF==1)
            DINFcheck.setSelected(true);
        else
            DINFcheck.setSelected(false);
        if(Vars.HINF==1)
            HINFcheck.setSelected(true);
        else
            HINFcheck.setSelected(false);
        if(Vars.FINF==1)
            FINFcheck.setSelected(true);
        else
            FINFcheck.setSelected(false);
        
        if(Vars.BSCH==1)
            BSCHcheck.setSelected(true);
        else
            BSCHcheck.setSelected(false);
        if(Vars.ESCH==1)
            ESCHcheck.setSelected(true);
        else
            ESCHcheck.setSelected(false);
        if(Vars.DSCH==1)
            DSCHcheck.setSelected(true);
        else
            DSCHcheck.setSelected(false);
        if(Vars.HSCH==1)
            HSCHcheck.setSelected(true);
        else
            HSCHcheck.setSelected(false);
        if(Vars.FSCH==1)
            FSCHcheck.setSelected(true);
        else
            FSCHcheck.setSelected(false);
        
        if(Vars.BRES==1)
            BREScheck.setSelected(true);
        else
            BREScheck.setSelected(false);
        if(Vars.ERES==1)
            EREScheck.setSelected(true);
        else
            EREScheck.setSelected(false);
        if(Vars.DRES==1)
            DREScheck.setSelected(true);
        else
            DREScheck.setSelected(false);
        if(Vars.HRES==1)
            HREScheck.setSelected(true);
        else
            HREScheck.setSelected(false);
        if(Vars.FRES==1)
            FREScheck.setSelected(true);
        else
            FREScheck.setSelected(false);
        
        if(Vars.BPAS==1)
            BPAScheck.setSelected(true);
        else
            BPAScheck.setSelected(false);
        if(Vars.EPAS==1)
            EPAScheck.setSelected(true);
        else
            EPAScheck.setSelected(false);
        if(Vars.DPAS==1)
            DPAScheck.setSelected(true);
        else
            DPAScheck.setSelected(false);
        if(Vars.HPAS==1)
            HPAScheck.setSelected(true);
        else
            HPAScheck.setSelected(false);
        if(Vars.FPAS==1)
            FPAScheck.setSelected(true);
        else
            FPAScheck.setSelected(false);
        
        if(Vars.BSUP==1)
            BSUPcheck.setSelected(true);
        else
            BSUPcheck.setSelected(false);
        if(Vars.ESUP==1)
            ESUPcheck.setSelected(true);
        else
            ESUPcheck.setSelected(false);
        if(Vars.DSUP==1)
            DSUPcheck.setSelected(true);
        else
            DSUPcheck.setSelected(false);
        if(Vars.HSUP==1)
            HSUPcheck.setSelected(true);
        else
            HSUPcheck.setSelected(false);
        if(Vars.FSUP==1)
            FSUPcheck.setSelected(true);
        else
            FSUPcheck.setSelected(false);
        
        
        
        
    }
    private void jPanel6MouseClicked (java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel6MouseClicked
    {//GEN-HEADEREND:event_jPanel6MouseClicked
// TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel6MouseClicked
    
    private void jPanel6FocusLost (java.awt.event.FocusEvent evt)//GEN-FIRST:event_jPanel6FocusLost
    {//GEN-HEADEREND:event_jPanel6FocusLost
// TODO add your handling code here:
    }//GEN-LAST:event_jPanel6FocusLost
    
    private void jPanel6FocusGained (java.awt.event.FocusEvent evt)//GEN-FIRST:event_jPanel6FocusGained
    {//GEN-HEADEREND:event_jPanel6FocusGained
        
    }//GEN-LAST:event_jPanel6FocusGained
    
    public void resizeBanTable(int ty,int r,int o,int t,int n, int i,int c, int re){
        // how many parts represents each column
        // preffered call : 
        int suma=ty+r+o+t+n+i+c+re;
        DefaultTableModel BanModel=(DefaultTableModel) BanTable.getModel();
        BanTable.setAutoResizeMode(BanTable.AUTO_RESIZE_OFF);
        BanTable.getColumnModel().getColumn(0).setPreferredWidth(BanTable.getWidth()*ty/suma);
        BanTable.getColumnModel().getColumn(1).setPreferredWidth(BanTable.getWidth()*r/suma);
        BanTable.getColumnModel().getColumn(2).setPreferredWidth(BanTable.getWidth()*o/suma);
        BanTable.getColumnModel().getColumn(3).setPreferredWidth(BanTable.getWidth()*t/suma);
        BanTable.getColumnModel().getColumn(4).setPreferredWidth(BanTable.getWidth()*n/suma);
        BanTable.getColumnModel().getColumn(5).setPreferredWidth(BanTable.getWidth()*i/suma);
        BanTable.getColumnModel().getColumn(6).setPreferredWidth(BanTable.getWidth()*c/suma);
        BanTable.getColumnModel().getColumn(7).setPreferredWidth(BanTable.getWidth()*re/suma);
        
        
        
    }
    
    public void insertBans()
    {
        Ban n=BanList.First;
        int bancount=0;
        while(n!=null) {
            bancount++;
            n=n.Next;
        }
        /** 0 -- no ban
     * 1 -- nick ban
     * 2 -- ip ban
     * 3 -- cid ban
     */
        DefaultTableModel BanModel=(DefaultTableModel) BanTable.getModel();
        if(bancount!=BanModel.getRowCount())
        {
            BanModel.setRowCount(0) ;
            n=BanList.First;
            while(n!=null) {
                
                Date d=new Date(n.timeofban);
               
               String type;
                switch(n.bantype)
                {
                    case 1:
                        type="Nick";
                        break;
                    case 2:
                        type="IP";
                        break;
                    default:
                        type="CID";
                }
                
                
                BanModel.addRow(new Object[]
                {
                    type,ADC.retNormStr(n.banreason),n.banop,d.toString(),n.nick,n.ip,n.cid,n.getTimeLeft()
                });
                n=n.Next;
            }
        }
    }
    
    private void formWindowGainedFocus (java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowGainedFocus
    {//GEN-HEADEREND:event_formWindowGainedFocus
refreshAll();        
       
        
    }//GEN-LAST:event_formWindowGainedFocus
    
    private void jButton4ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
//clicked reg...
        Main.Reg(jTextField1.getText());
        ((DefaultTableModel) AccountTable.getModel()).setRowCount(0) ;
        Nod n=AccountsConfig.First;
        while(n!=null) {
            String blah00="";
            Date d=new Date(n.CreatedOn);
            if(n.LastNI!=null)
                blah00=n.LastNI;
            else
                blah00="Never seen online.";
            
            ((DefaultTableModel) AccountTable.getModel()).addRow(new Object[]{n.CID,blah00,n.LastIP,n.WhoRegged,d.toString()});
            n=n.Next;
        }
        Main.Server.rewriteregs();
    }//GEN-LAST:event_jButton4ActionPerformed
    
    private void jButton3ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
    {//GEN-HEADEREND:event_jButton3ActionPerformed
        SetStatus("Restarting... Press OK and wait a few seconds....");
        Main.Restart();
        
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jButton2ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        this.setVisible(false);
        this.dispose();
        System.gc();
        Main.GUIshowing=false;
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jButton1ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        Main.Exit();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton30ActionPerformed
    {//GEN-HEADEREND:event_jButton30ActionPerformed
       
        Modulator.findModules();
        this.refreshGUIPlugs();
        String foundPlugs="";
        for(Module myPlug: Modulator.myModules)
        {
            foundPlugs+="\n"+myPlug.getName();
        }
        if(foundPlugs.equals(""))
            foundPlugs="No plugin found.";
        else
            foundPlugs="Found following plugins:"+foundPlugs;
        JOptionPane.showMessageDialog(this,foundPlugs,
                    Vars.HubName,JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_jButton30ActionPerformed

    private void searchcheckActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchcheckActionPerformed
    {//GEN-HEADEREND:event_searchcheckActionPerformed
     if(searchcheck.isSelected())
        {
            long prop= getClientPr() + getWordPr();
            if(privatecheck.isSelected())
                prop+=BannedWord.privatechat;
            if(notifycheck.isSelected())
                prop+=BannedWord.notify;
            prop+=BannedWord.searches;
            listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop);
        }
        else
        {
            long prop= getClientPr() + getWordPr();
            if(privatecheck.isSelected())
                prop+=BannedWord.privatechat;
            if(notifycheck.isSelected())
                prop+=BannedWord.notify;
            
            listaBanate.modifyMultiPrAt(jList1.getSelectedIndices(),prop);
        }
    }//GEN-LAST:event_searchcheckActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton31ActionPerformed
    {//GEN-HEADEREND:event_jButton31ActionPerformed
     String Thing,aucsy;
        
        Thing=msgbannedfield.getText();
        
        aucsy=Vars.Msg_Banned;
        if(!(aucsy.equals(Thing))) {
            
            Vars.Msg_Banned=Thing;
            Main.PopMsg("Msg_Banned changed from \""+aucsy+"\" to \""+Thing+"\".");
            
        }
        Thing=msgfullfield.getText();
        
        aucsy=Vars.Msg_Full;
        if(!(aucsy.equals(Thing))) {
            
            Vars.Msg_Full=Thing;
            Main.PopMsg("Msg_Full changed from \""+aucsy+"\" to \""+Thing+"\".");
            
        }
        
        SetStatus("Miscellaneous settings saved.");
    }//GEN-LAST:event_jButton31ActionPerformed
    
    public void SetStatus(String newstring,int msgType)
    {
        StatusLabel.setText(newstring);
        if(Main.GUIshowing)
        JOptionPane.showMessageDialog(this,newstring,
                    Vars.HubName,msgType);
    }
    
    public void SetStatus(String newstring) 
    {
        StatusLabel.setText(newstring);
        if(Main.GUIshowing)
        JOptionPane.showMessageDialog(this,newstring,
                    Vars.HubName,JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AccountTable;
    private javax.swing.JCheckBox BCTMcheck;
    private javax.swing.JCheckBox BINFcheck;
    private javax.swing.JCheckBox BMSGcheck;
    private javax.swing.JCheckBox BPAScheck;
    private javax.swing.JCheckBox BRCMcheck;
    private javax.swing.JCheckBox BREScheck;
    private javax.swing.JCheckBox BSCHcheck;
    private javax.swing.JCheckBox BSTAcheck;
    private javax.swing.JCheckBox BSUPcheck;
    private javax.swing.JTable BanTable;
    private javax.swing.JCheckBox DCTMcheck;
    private javax.swing.JCheckBox DINFcheck;
    private javax.swing.JCheckBox DMSGcheck;
    private javax.swing.JCheckBox DPAScheck;
    private javax.swing.JCheckBox DRCMcheck;
    private javax.swing.JCheckBox DREScheck;
    private javax.swing.JCheckBox DSCHcheck;
    private javax.swing.JCheckBox DSTAcheck;
    private javax.swing.JCheckBox DSUPcheck;
    private javax.swing.JCheckBox ECTMcheck;
    private javax.swing.JCheckBox EINFcheck;
    private javax.swing.JCheckBox EMSGcheck;
    private javax.swing.JCheckBox EPAScheck;
    private javax.swing.JCheckBox ERCMcheck;
    private javax.swing.JCheckBox EREScheck;
    private javax.swing.JCheckBox ESCHcheck;
    private javax.swing.JCheckBox ESTAcheck;
    private javax.swing.JCheckBox ESUPcheck;
    private javax.swing.JCheckBox FCTMcheck;
    private javax.swing.JCheckBox FINFcheck;
    private javax.swing.JCheckBox FMSGcheck;
    private javax.swing.JCheckBox FPAScheck;
    private javax.swing.JCheckBox FRCMcheck;
    private javax.swing.JCheckBox FREScheck;
    private javax.swing.JCheckBox FSCHcheck;
    private javax.swing.JCheckBox FSTAcheck;
    private javax.swing.JCheckBox FSUPcheck;
    private javax.swing.JCheckBox HCTMcheck;
    private javax.swing.JCheckBox HINFcheck;
    private javax.swing.JCheckBox HMSGcheck;
    private javax.swing.JCheckBox HPAScheck;
    private javax.swing.JCheckBox HRCMcheck;
    private javax.swing.JCheckBox HREScheck;
    private javax.swing.JCheckBox HSCHcheck;
    private javax.swing.JCheckBox HSTAcheck;
    private javax.swing.JCheckBox HSUPcheck;
    private javax.swing.JTextArea LogText;
    private javax.swing.JPanel PPanel;
    private javax.swing.JScrollPane Panelxxx;
    private javax.swing.JPanel PluginPanel;
    private javax.swing.JLabel StatusLabel;
    private javax.swing.JTextField automagicsearchfield;
    private javax.swing.JTextField botdescfield;
    private javax.swing.JTextField botnamefield;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JTextField chatintervalfield;
    private javax.swing.JTextField fieldtimeout;
    private javax.swing.JTextField historylinesfield;
    private javax.swing.JTextField hubhostfield;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField kicktimefield;
    private javax.swing.JTextField maxchatmsgfield;
    private javax.swing.JTextField maxdefield;
    private javax.swing.JTextField maxemfield;
    private javax.swing.JTextField maxhubsopfield;
    private javax.swing.JTextField maxhubsregfield;
    private javax.swing.JTextField maxhubsuserfield;
    private javax.swing.JTextField maxnifield;
    private javax.swing.JTextField maxschcharsfield;
    private javax.swing.JTextField maxsharefield;
    private javax.swing.JTextField maxslfield;
    private javax.swing.JTextField maxusersfield;
    private javax.swing.JTextField minnifield;
    private javax.swing.JTextField minschcharsfield;
    private javax.swing.JTextField minsharefield;
    private javax.swing.JTextField minslfield;
    private javax.swing.JPanel miscpanel;
    private javax.swing.JTextArea msgbannedfield;
    private javax.swing.JTextArea msgfullfield;
    private javax.swing.JTextArea msgsearchspamfield;
    private javax.swing.JTextField namefield;
    private javax.swing.JTextArea nickcharsfield;
    private javax.swing.JCheckBox notifycheck;
    private javax.swing.JTextField opchatdescfield;
    private javax.swing.JTextField opchatnamefield;
    private javax.swing.JTextField portfield;
    private javax.swing.JCheckBox privatecheck;
    private javax.swing.JCheckBox proxycheck;
    private javax.swing.JTextField proxyhostfield;
    private javax.swing.JTextField proxyportfield;
    private javax.swing.JTextField redirecturl;
    private javax.swing.JCheckBox regonlycheck;
    private javax.swing.JCheckBox savelogscheck;
    private javax.swing.JCheckBox searchcheck;
    private javax.swing.JTextField searchlogbasefield;
    private javax.swing.JTextField searchspamresetfield;
    private javax.swing.JTextField searchstepsfield;
    private javax.swing.JTextField topicfield;
    private javax.swing.JPanel xxx;
    // End of variables declaration//GEN-END:variables
    
    // private JPanel jPanel1;
    
}
