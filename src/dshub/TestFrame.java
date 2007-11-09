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

package dshub;


import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListModel;
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
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        portfield = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        topicfield = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        namefield = new javax.swing.JTextField();
        regonlycheck = new javax.swing.JCheckBox();
        savelogscheck = new javax.swing.JCheckBox();
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
        kickops = new javax.swing.JPanel();
        opchatnamefield = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        opchatdescfield = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        historylinesfield = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        kicktimefield = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        msgbannedfield = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        msgfullfield = new javax.swing.JTextArea();
        jLabel42 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
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
        jLabel50 = new javax.swing.JLabel();
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
        jCheckBox1 = new javax.swing.JCheckBox();
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
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        BMSGcheck = new javax.swing.JCheckBox();
        DMSGcheck = new javax.swing.JCheckBox();
        EMSGcheck = new javax.swing.JCheckBox();
        FMSGcheck = new javax.swing.JCheckBox();
        HMSGcheck = new javax.swing.JCheckBox();
        jLabel55 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        BSTAcheck = new javax.swing.JCheckBox();
        DSTAcheck = new javax.swing.JCheckBox();
        ESTAcheck = new javax.swing.JCheckBox();
        FSTAcheck = new javax.swing.JCheckBox();
        HSTAcheck = new javax.swing.JCheckBox();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel56 = new javax.swing.JLabel();
        BCTMcheck = new javax.swing.JCheckBox();
        DCTMcheck = new javax.swing.JCheckBox();
        ECTMcheck = new javax.swing.JCheckBox();
        FCTMcheck = new javax.swing.JCheckBox();
        HCTMcheck = new javax.swing.JCheckBox();
        jButton15 = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        BRCMcheck = new javax.swing.JCheckBox();
        DRCMcheck = new javax.swing.JCheckBox();
        ERCMcheck = new javax.swing.JCheckBox();
        FRCMcheck = new javax.swing.JCheckBox();
        HRCMcheck = new javax.swing.JCheckBox();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        BINFcheck = new javax.swing.JCheckBox();
        DINFcheck = new javax.swing.JCheckBox();
        EINFcheck = new javax.swing.JCheckBox();
        FINFcheck = new javax.swing.JCheckBox();
        HINFcheck = new javax.swing.JCheckBox();
        jLabel59 = new javax.swing.JLabel();
        BSCHcheck = new javax.swing.JCheckBox();
        DSCHcheck = new javax.swing.JCheckBox();
        ESCHcheck = new javax.swing.JCheckBox();
        FSCHcheck = new javax.swing.JCheckBox();
        HSCHcheck = new javax.swing.JCheckBox();
        jLabel60 = new javax.swing.JLabel();
        BREScheck = new javax.swing.JCheckBox();
        DREScheck = new javax.swing.JCheckBox();
        EREScheck = new javax.swing.JCheckBox();
        FREScheck = new javax.swing.JCheckBox();
        HREScheck = new javax.swing.JCheckBox();
        jLabel61 = new javax.swing.JLabel();
        BPAScheck = new javax.swing.JCheckBox();
        DPAScheck = new javax.swing.JCheckBox();
        EPAScheck = new javax.swing.JCheckBox();
        FPAScheck = new javax.swing.JCheckBox();
        HPAScheck = new javax.swing.JCheckBox();
        jLabel62 = new javax.swing.JLabel();
        BSUPcheck = new javax.swing.JCheckBox();
        DSUPcheck = new javax.swing.JCheckBox();
        ESUPcheck = new javax.swing.JCheckBox();
        FSUPcheck = new javax.swing.JCheckBox();
        HSUPcheck = new javax.swing.JCheckBox();
        jButton21 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        LogText = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
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
        jPanel1.setToolTipText("About DSHub...");
        jLabel1.setText("This program is distributed in the hope that it will be useful, ");

        jLabel2.setText("but WITHOUT ANY WARRANTY; without even the implied warranty of ");

        jLabel3.setText("MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the ");

        jLabel4.setText("GNU General Public License for more details. ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel5.setText("Welcome to DSHub an ADC hubsoft for Direct Connect.");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14));
        jLabel6.setText("Death Squad Hub. The Credits. ");

        jLabel7.setText("Developer: Pietry (Pietricica)");

        jLabel8.setText("Special Thanks to MAGY for GUI ideas and to Spader and Toast for nice testing :)");

        jLabel9.setText("Also lots of thanks to all who helped me and all beta testers and contributors.");

        jLabel14.setText("Version: DSHub Theta RC1");

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dshub/ds.jpg")));

        jLabel65.setText("Co-Developer: Naccio");

        jLabel66.setText("This program uses the MINA library http://mina.apache.org");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2)
                            .add(jLabel3)
                            .add(jLabel4)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(267, 267, 267)
                        .add(jLabel6))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(163, 163, 163)
                        .add(jLabel5)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 97, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jLabel14)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jLabel44)
                        .add(25, 25, 25))))
            .add(jPanel1Layout.createSequentialGroup()
                .add(278, 278, 278)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jLabel65))
                    .add(jLabel7))
                .addContainerGap(326, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .add(163, 163, 163)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(8, 8, 8)
                        .add(jLabel9))
                    .add(jLabel8))
                .addContainerGap(190, Short.MAX_VALUE))
            .add(jPanel1Layout.createSequentialGroup()
                .add(214, 214, 214)
                .add(jLabel66)
                .addContainerGap(243, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel14)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jLabel5)
                        .add(24, 24, 24)
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel4)
                        .add(40, 40, 40)
                        .add(jLabel6)
                        .add(28, 28, 28)
                        .add(jLabel7))
                    .add(jLabel44))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel65)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel9)
                .add(30, 30, 30)
                .add(jLabel66)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("About", null, jPanel1, "About DSHub...");

        jPanel2.setToolTipText("Hub Settings");
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jPanel7.setToolTipText("Primary Settings");
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel17.setText("Default port hub should run on. Requires restart to apply.");

        portfield.setText("411");
        portfield.setPreferredSize(new java.awt.Dimension(130, 20));
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

        jButton7.setText("Save Settings");
        jButton7.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel18.setText("Current hub topic, to be shown in title bar.");

        topicfield.setPreferredSize(new java.awt.Dimension(130, 20));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel19.setText("Hub name to display in main window.");

        namefield.setPreferredSize(new java.awt.Dimension(130, 20));

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

        botnamefield.setText("DSHub");
        botnamefield.setPreferredSize(new java.awt.Dimension(130, 20));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel48.setText("Hub security bot name.");

        botdescfield.setText("www.death-squad.ro/dshub");
        botdescfield.setPreferredSize(new java.awt.Dimension(130, 20));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel49.setText("Hub security bot description.");

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(27, 27, 27)
                        .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(namefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(topicfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(portfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(regonlycheck)
                            .add(savelogscheck)
                            .add(botdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(botnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel49)
                            .add(jLabel48)
                            .add(jLabel19)
                            .add(jLabel18)
                            .add(jLabel17)))
                    .add(jPanel7Layout.createSequentialGroup()
                        .add(272, 272, 272)
                        .add(jButton7)))
                .addContainerGap(207, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(portfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel17))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(topicfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel18))
                .add(8, 8, 8)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(namefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel19))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(regonlycheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(savelogscheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel48))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(botdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel49))
                .add(98, 98, 98)
                .add(jButton7)
                .add(68, 68, 68))
        );
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

        kickops.setToolTipText("Misc Settings");
        opchatnamefield.setText("OpChat");
        opchatnamefield.setMinimumSize(new java.awt.Dimension(130, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel33.setText("The Operator Chat Bot Nick.");

        opchatdescfield.setText("BoT");
        opchatdescfield.setPreferredSize(new java.awt.Dimension(130, 20));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel34.setText("The Operator Chat Bot Description.");

        historylinesfield.setText("50");

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel39.setText("Number of lines to keep in chat history.");

        kicktimefield.setText("300");

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel40.setText("The time to ban a user with a kick, in seconds.");

        msgbannedfield.setColumns(20);
        msgbannedfield.setRows(5);
        jScrollPane5.setViewportView(msgbannedfield);

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel41.setText("The aditional message to show to banned users when connecting.");

        msgfullfield.setColumns(20);
        msgfullfield.setRows(5);
        jScrollPane6.setViewportView(msgfullfield);

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel42.setText("The additional message to be shown to connecting users when hub full.");

        jButton11.setText("Save Settings");
        jButton11.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton11ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout kickopsLayout = new org.jdesktop.layout.GroupLayout(kickops);
        kickops.setLayout(kickopsLayout);
        kickopsLayout.setHorizontalGroup(
            kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(kickopsLayout.createSequentialGroup()
                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(kickopsLayout.createSequentialGroup()
                        .add(28, 28, 28)
                        .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(kickopsLayout.createSequentialGroup()
                                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, opchatdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, opchatnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 129, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                .add(20, 20, 20)
                                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel33)
                                    .add(jLabel34)))
                            .add(kickopsLayout.createSequentialGroup()
                                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, kicktimefield)
                                    .add(org.jdesktop.layout.GroupLayout.LEADING, historylinesfield, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                                .add(24, 24, 24)
                                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel40)
                                    .add(jLabel39)))
                            .add(jScrollPane5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                            .add(jScrollPane6)))
                    .add(kickopsLayout.createSequentialGroup()
                        .add(96, 96, 96)
                        .add(jLabel41))
                    .add(kickopsLayout.createSequentialGroup()
                        .add(91, 91, 91)
                        .add(jLabel42))
                    .add(kickopsLayout.createSequentialGroup()
                        .add(246, 246, 246)
                        .add(jButton11)))
                .addContainerGap(160, Short.MAX_VALUE))
        );
        kickopsLayout.setVerticalGroup(
            kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(kickopsLayout.createSequentialGroup()
                .addContainerGap()
                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(opchatnamefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel33))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(opchatdescfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel34))
                .add(54, 54, 54)
                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(historylinesfield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel39))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(kickopsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(kicktimefield, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel40))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 54, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel41)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel42)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton11)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jTabbedPane2.addTab("Misc Settings", kickops);

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
        jLabel50.setText("Bans are still in work ( for GUI ). Banning works fine as OP in the hub.");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(204, 204, 204)
                .add(jLabel50)
                .addContainerGap(205, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(186, 186, 186)
                .add(jLabel50)
                .addContainerGap(194, Short.MAX_VALUE))
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

        jButton6.setText("Update Stats");
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
                .add(jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(78, 78, 78)
                        .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 544, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel6Layout.createSequentialGroup()
                        .add(285, 285, 285)
                        .add(jButton6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 133, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel6Layout.createSequentialGroup()
                .add(55, 55, 55)
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 226, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17)
                .add(jButton6)
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Stats", null, jPanel6, "Some Hub Statistics...");

        jPanel10.setToolTipText("Forbidden words");
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Selected Options"));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Client Action"));
        buttonGroup2.add(jRadioButton1);
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
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Word Action"));
        buttonGroup1.add(jRadioButton4);
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
        jRadioButton6.setText("Modify");
        jRadioButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jRadioButton6.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                jRadioButton6StateChanged(evt);
            }
        });
        jRadioButton6.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                jRadioButton6MouseClicked(evt);
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
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
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
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jCheckBox1.setText(" Private Chat Control ?");
        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel11Layout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(jCheckBox1)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jPanel11Layout.createSequentialGroup()
                .add(jPanel14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jCheckBox1)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "FileList"));

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
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 29, Short.MAX_VALUE)
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

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Add / Edit Word"));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                jTextField2KeyPressed(evt);
            }
        });
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                jTextField2MousePressed(evt);
            }
        });

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
        jRadioButton7.setSelected(true);
        jRadioButton7.setText("Drop");
        jRadioButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton7.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup3.add(jRadioButton8);
        jRadioButton8.setText("Kick");
        jRadioButton8.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton8.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup3.add(jRadioButton9);
        jRadioButton9.setText("No Action");
        jRadioButton9.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton9.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup4.add(jRadioButton10);
        jRadioButton10.setSelected(true);
        jRadioButton10.setText("Hide Line");
        jRadioButton10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton10.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup4.add(jRadioButton11);
        jRadioButton11.setText("Replace With *");
        jRadioButton11.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioButton11.setMargin(new java.awt.Insets(0, 0, 0, 0));

        buttonGroup4.add(jRadioButton12);
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
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 20, Short.MAX_VALUE)
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
                                .add(jRadioButton12)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 33, Short.MAX_VALUE)
                                .add(jTextField5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(jPanel17Layout.createSequentialGroup()
                                .add(jRadioButton9)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 194, Short.MAX_VALUE)))))
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
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jPanel17Layout.linkSize(new java.awt.Component[] {jButton23, jLabel64, jTextField2}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Forbidden Words List"));
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
                .add(jScrollPane9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
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
                    .add(jPanel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel18, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel10Layout.createSequentialGroup()
                        .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel17, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jTabbedPane1.addTab("Chat Control", jPanel10);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setToolTipText("ADC advanced configuration panel");
        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel51.setText("The ADC advanced configuration Panel.");
        jPanel9.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel52.setText("Here you can configure the ADC commands separately.");
        jPanel9.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel53.setText("MSG");
        jPanel9.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 10));
        jLabel54.setText("Allowed contexts:");
        jPanel9.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, -1, -1));

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

        jPanel9.add(BMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, -1));

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

        jPanel9.add(DMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

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

        jPanel9.add(EMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

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

        jPanel9.add(FMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

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

        jPanel9.add(HMSGcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel55.setText("STA");
        jPanel9.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, -1, -1));

        jButton12.setText("[?]");
        jButton12.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton12ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

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

        jPanel9.add(BSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

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

        jPanel9.add(DSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, -1, -1));

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

        jPanel9.add(ESTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

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

        jPanel9.add(FSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, -1, -1));

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

        jPanel9.add(HSTAcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, -1, -1));

        jButton13.setText("[?]");
        jButton13.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton13ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, -1, -1));

        jButton14.setText("[?]");
        jButton14.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton14ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, -1, -1));

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel56.setText("CTM");
        jPanel9.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, -1, -1));

        BCTMcheck.setText("B");
        BCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, -1, -1));

        DCTMcheck.setText("D");
        DCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, -1, -1));

        ECTMcheck.setText("E");
        ECTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ECTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(ECTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        FCTMcheck.setText("F");
        FCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 170, -1, -1));

        HCTMcheck.setText("H");
        HCTMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HCTMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HCTMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));

        jButton15.setText("[?]");
        jButton15.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton15ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, -1, -1));

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel57.setText("RCM");
        jPanel9.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        BRCMcheck.setText("B");
        BRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, -1, -1));

        DRCMcheck.setText("D");
        DRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 130, -1, -1));

        ERCMcheck.setText("E");
        ERCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ERCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(ERCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, -1, -1));

        FRCMcheck.setText("F");
        FRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        HRCMcheck.setText("H");
        HRCMcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HRCMcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HRCMcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, -1, -1));

        jButton16.setText("[?]");
        jButton16.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton16ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, -1, -1));

        jButton17.setText("[?]");
        jButton17.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton17ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 220, -1, -1));

        jButton18.setText("[?]");
        jButton18.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton18ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 220, -1, -1));

        jButton19.setText("[?]");
        jButton19.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton19ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 220, -1, -1));

        jButton20.setText("[?]");
        jButton20.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton20ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 220, -1, -1));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel58.setText("INF");
        jPanel9.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));

        BINFcheck.setText("B");
        BINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, -1, -1));

        DINFcheck.setText("D");
        DINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 130, -1, -1));

        EINFcheck.setText("E");
        EINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(EINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, -1, -1));

        FINFcheck.setText("F");
        FINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));

        HINFcheck.setText("H");
        HINFcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HINFcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HINFcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 190, -1, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel59.setText("SCH");
        jPanel9.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 90, -1, -1));

        BSCHcheck.setText("B");
        BSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, -1, -1));

        DSCHcheck.setText("D");
        DSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, -1, -1));

        ESCHcheck.setText("E");
        ESCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ESCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(ESCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 150, -1, -1));

        FSCHcheck.setText("F");
        FSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 170, -1, -1));

        HSCHcheck.setText("H");
        HSCHcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HSCHcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HSCHcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 190, -1, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel60.setText("RES");
        jPanel9.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        BREScheck.setText("B");
        BREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, -1, -1));

        DREScheck.setText("D");
        DREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, -1, -1));

        EREScheck.setText("E");
        EREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(EREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 150, -1, -1));

        FREScheck.setText("F");
        FREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 170, -1, -1));

        HREScheck.setText("H");
        HREScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HREScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HREScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, -1, -1));

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel61.setText("PAS");
        jPanel9.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

        BPAScheck.setText("B");
        BPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 110, -1, -1));

        DPAScheck.setText("D");
        DPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 130, -1, -1));

        EPAScheck.setText("E");
        EPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        EPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(EPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, -1, -1));

        FPAScheck.setText("F");
        FPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, -1, -1));

        HPAScheck.setText("H");
        HPAScheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HPAScheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HPAScheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 190, -1, -1));

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel62.setText("SUP");
        jPanel9.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 90, -1, -1));

        BSUPcheck.setText("B");
        BSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        BSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(BSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, -1));

        DSUPcheck.setText("D");
        DSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        DSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(DSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 130, -1, -1));

        ESUPcheck.setText("E");
        ESUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        ESUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(ESUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 150, -1, -1));

        FSUPcheck.setText("F");
        FSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        FSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(FSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, -1));

        HSUPcheck.setText("H");
        HSUPcheck.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        HSUPcheck.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel9.add(HSUPcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, -1, -1));

        jButton21.setText("[?]");
        jButton21.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton21ActionPerformed(evt);
            }
        });

        jPanel9.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 220, -1, -1));

        jTabbedPane1.addTab("Advanced", jPanel9);

        jPanel12.setToolTipText("Hub Log");
        LogText.setColumns(20);
        LogText.setRows(5);
        jScrollPane4.setViewportView(LogText);

        org.jdesktop.layout.GroupLayout jPanel12Layout = new org.jdesktop.layout.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .add(70, 70, 70)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 606, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel12Layout.createSequentialGroup()
                .add(29, 29, 29)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 272, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );
        jTabbedPane1.addTab("Log", jPanel12);

        jPanel4.setToolTipText("Some Help ...");
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(127, 127, 127)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 498, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .add(37, 37, 37)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 318, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
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
        listaBanate.printFile(path);   
         this.SetStatus("List saved.");
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
    }//GEN-LAST:event_jTextField3KeyReleased
            
    private void jRadioButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton6MouseClicked
// TODO add your handling code here:
        long prop=getWordPr();
        listaBanate.modifyMultiWordPrAt(jList1.getSelectedIndices(),prop,
                jTextField3.getText());
    }//GEN-LAST:event_jRadioButton6MouseClicked
    
    private void jRadioButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton5MouseClicked
// TODO add your handling code here:
        long prop=getWordPr();
        jTextField3.setText("");
        listaBanate.modifyMultiWordPrAt(jList1.getSelectedIndices(),prop,"");
    }//GEN-LAST:event_jRadioButton5MouseClicked
    
    private void jRadioButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton4MouseClicked
// TODO add your handling code here:
        long prop=getWordPr();
        jTextField3.setText("");
        listaBanate.modifyMultiWordPrAt(jList1.getSelectedIndices(),prop,"");
    }//GEN-LAST:event_jRadioButton4MouseClicked
    
    private void jRadioButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton3MouseClicked
// TODO add your handling code here:
        long prop=getClientPr();
        // System.out.println(prop);
        listaBanate.modifyMultiClientPrAt(jList1.getSelectedIndices(),prop);
    }//GEN-LAST:event_jRadioButton3MouseClicked
    
    private void jRadioButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton2MouseClicked
// TODO add your handling code here:
        long prop=getClientPr();
        //System.out.println(prop);
        listaBanate.modifyMultiClientPrAt(jList1.getSelectedIndices(),prop);
    }//GEN-LAST:event_jRadioButton2MouseClicked
    
    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
// TODO add your handling code here:
        long prop=getClientPr();
        // System.out.println(prop);
        listaBanate.modifyMultiClientPrAt(jList1.getSelectedIndices(),prop);
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
            listaBanate.add(banWord,prop,repl);
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
        AccountEditer Acc1=new AccountEditer(CID);
        Acc1.setVisible(true);
    }//GEN-LAST:event_jButton22ActionPerformed
    
    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
        JDialog bla =new JDialog();
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
            if(reg_config.unreg(CID)) {
                DefaultTableModel AccountModel=(DefaultTableModel) AccountTable.getModel();
                nod n=reg_config.First;
                int regcount=0;
                while(n!=null) {
                    regcount++;
                    n=n.Next;
                }
                
                if(regcount!=AccountModel.getRowCount()) {
                    AccountModel.setRowCount(0) ;
                    n=reg_config.First;
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
                    temp.cur_client.sendToClient("IQUI ABCD");
                    if(temp.cur_client.reg.key){temp.cur_client.OP="";}else{temp.cur_client.RG="";};
                    if(temp.cur_client.reg.key)
                        temp.cur_client.HO=String.valueOf(Integer.parseInt(temp.cur_client.HO)-1);
                    else
                        temp.cur_client.HR=String.valueOf(Integer.parseInt(temp.cur_client.HR)-1);
                    temp.cur_client.HN=String.valueOf(Integer.parseInt(temp.cur_client.HN)+1);
                    new Broadcast("BINF "+temp.cur_client.SessionID+" "+(temp.cur_client.reg.key?"OP":"RG")+(temp.cur_client.reg.key?" HO":" HR")+(temp.cur_client.reg.key?temp.cur_client.HO:temp.cur_client.HR)+" HN"+temp.cur_client.HN);
                    temp.cur_client.reg=new nod();
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
        JDialog bla =new JDialog();
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
    
    void insertLog(String bla) {
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
            Main.Server.vars.HubDE="";
            
            
            } else {
            String auxbuf=newtopic;
            
            
            Vars.HubDE=Vars.HubDE;
            // System.out.println("Topic changed from \""+Vars.HubDE+"\" "+"to \""+auxbuf+"\".");
            auxbuf=auxbuf;
            Vars.HubDE=auxbuf;
            Main.Server.vars.HubDE=auxbuf;
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
        
        
        
        Main.Server.rewriteconfig();
        
        
        
        SetStatus("Main settings saved.");
        
    }//GEN-LAST:event_jButton7ActionPerformed
    
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
        // up=345345343;
        long days=up/(3600000*24);
        long hours =up/3600000-24*days;
        long minutes=up/60000-60*hours-24*60*days;
        long seconds=up/1000-60*minutes-60*24*60*days-60*60*hours;
        long millis=up-1000*seconds-60*1000*24*60*days-1000*60*60*hours-1000*60*minutes;
        
        String uptime="";
        if(days!=0)
            uptime=Long.toString(days)+" Days ";
        if(hours!=0 || (hours==0 && days!=0))
            uptime=uptime+Long.toString(hours)+" Hours ";
        if(minutes!=0 || (minutes==0 && (days!=0 || hours!=0)))
            uptime=uptime+Long.toString(minutes)+" Minutes ";
        if(seconds!=0 || (seconds==0 && (days!=0 || hours!=0 || minutes!=0)))
            uptime=uptime+Long.toString(seconds)+" Seconds ";
        uptime=uptime+Long.toString(millis)+ " Millis";
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
                "  Uptime: "+uptime+".\n"+
                "  Start Time: "+b.toString());
    }//GEN-LAST:event_jButton6ActionPerformed
    
    private void jPanel6MouseMoved (java.awt.event.MouseEvent evt)//GEN-FIRST:event_jPanel6MouseMoved
    {//GEN-HEADEREND:event_jPanel6MouseMoved
// TODO add your handling code here:
        
        
    }//GEN-LAST:event_jPanel6MouseMoved
    
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
    
    private void formWindowGainedFocus (java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowGainedFocus
    {//GEN-HEADEREND:event_formWindowGainedFocus
        
        
        
        /**setting window name*/
        //System.out.println("gay");
        
        this.setTitle(Vars.HubName+" running on "+Vars.HubVersion+" A New Generation 2007");
        //jLabel51.setIcon (new Icon("ds.bmp"));
        //addImage(jPanel1, "ds.bmp");
        /**showing accounts*/
        
        DefaultTableModel AccountModel=(DefaultTableModel) AccountTable.getModel();
        AccountTable.setAutoResizeMode(AccountTable.AUTO_RESIZE_OFF);
        AccountTable.getColumnModel().getColumn(0).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(1).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(2).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(3).setPreferredWidth(AccountTable.getWidth()/5);
        AccountTable.getColumnModel().getColumn(4).setPreferredWidth(AccountTable.getWidth()/5);
        nod n=reg_config.First;
        int regcount=0;
        while(n!=null) {
            regcount++;
            n=n.Next;
        }
        
        if(regcount!=AccountModel.getRowCount()) {
            AccountModel.setRowCount(0) ;
            n=reg_config.First;
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
                "At the moment of this release ( October 2007 ), the following ADC clients were available:\n"+
                "dc++ 0.69*, icedc 1.01a, zion++ 2.04  apexdc 0.3.0, strongdc  2.01 , zk++  0.7, BCDC 0.69, FMDC, Elise or ANY later version of those will be ADC compatible.\n"+
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
        // up=345345343;
        long days=up/(3600000*24);
        long hours =up/3600000-24*days;
        long minutes=up/60000-60*hours-24*60*days;
        long seconds=up/1000-60*minutes-60*24*60*days-60*60*hours;
        long millis=up-1000*seconds-60*1000*24*60*days-1000*60*60*hours-1000*60*minutes;
        
        String uptime="";
        if(days!=0)
            uptime=Long.toString(days)+" Days ";
        if(hours!=0 || (hours==0 && days!=0))
            uptime=uptime+Long.toString(hours)+" Hours ";
        if(minutes!=0 || (minutes==0 && (days!=0 || hours!=0)))
            uptime=uptime+Long.toString(minutes)+" Minutes ";
        if(seconds!=0 || (seconds==0 && (days!=0 || hours!=0 || minutes!=0)))
            uptime=uptime+Long.toString(seconds)+" Seconds ";
        uptime=uptime+Long.toString(millis)+ " Millis";
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
                "  Uptime: "+uptime+".\n"+
                "  Start Time: "+b.toString());
        
        
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
        
        
        
        
        
    }//GEN-LAST:event_formWindowGainedFocus
    
    private void jButton4ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
    {//GEN-HEADEREND:event_jButton4ActionPerformed
//clicked reg...
        Main.Reg(jTextField1.getText());
        ((DefaultTableModel) AccountTable.getModel()).setRowCount(0) ;
        nod n=reg_config.First;
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
        SetStatus("Restarting... Wait 5 seconds....");
        Main.Restart();
        
    }//GEN-LAST:event_jButton3ActionPerformed
    
    private void jButton2ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
    {//GEN-HEADEREND:event_jButton2ActionPerformed
        this.setVisible(false);
        this.dispose();
        System.gc();
        //Main.GUIok=false;
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jButton1ActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        Main.Exit();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    
    public void SetStatus(String newstring) {
        StatusLabel.setText(newstring);
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
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JPanel kickops;
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
    private javax.swing.JTextArea msgbannedfield;
    private javax.swing.JTextArea msgfullfield;
    private javax.swing.JTextArea msgsearchspamfield;
    private javax.swing.JTextField namefield;
    private javax.swing.JTextArea nickcharsfield;
    private javax.swing.JTextField opchatdescfield;
    private javax.swing.JTextField opchatnamefield;
    private javax.swing.JTextField portfield;
    private javax.swing.JCheckBox regonlycheck;
    private javax.swing.JCheckBox savelogscheck;
    private javax.swing.JTextField searchlogbasefield;
    private javax.swing.JTextField searchspamresetfield;
    private javax.swing.JTextField searchstepsfield;
    private javax.swing.JTextField topicfield;
    private javax.swing.JPanel xxx;
    // End of variables declaration//GEN-END:variables
    
    // private JPanel jPanel1;
    
}
