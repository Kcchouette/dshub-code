/*
 * PingFrame.java
 *
 * Created on 20 ianuarie 2008, 10:28
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

package dshub.plugin;

import dshub.Vars;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Administrator
 */
public class PingFrame extends javax.swing.JFrame
{
    
    /** Creates new form PingFrame */
    public PingFrame()
    {
        initComponents();
        
        hubliststable.setAutoResizeMode(hubliststable.AUTO_RESIZE_OFF);
        hubliststable.getColumnModel().getColumn(0).setPreferredWidth(hubliststable.getWidth()/3);
        hubliststable.getColumnModel().getColumn(1).setPreferredWidth(hubliststable.getWidth()/3);
        hubliststable.getColumnModel().getColumn(2).setPreferredWidth(hubliststable.getWidth()/3);
        refresh();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jScrollPane1 = new javax.swing.JScrollPane();
        hubliststable = new javax.swing.JTable();
        buttonadd = new javax.swing.JButton();
        buttondel = new javax.swing.JButton();
        buttonstart = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Ping Hub-Hublist Communication");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(600, 400));
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
        addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                formFocusGained(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        hubliststable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {"www.hubtracker.com:3639", "HubTracker", "www.hubtracker.com"}
            },
            new String []
            {
                "Address", "Name", "Website"
            }
        )
        {
            Class[] types = new Class []
            {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean []
            {
                false, false, false
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
        jScrollPane1.setViewportView(hubliststable);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 570, 210));

        buttonadd.setText("Add new");
        buttonadd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttonaddActionPerformed(evt);
            }
        });
        getContentPane().add(buttonadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 130, -1));

        buttondel.setText("Delete Selected");
        buttondel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                buttondelActionPerformed(evt);
            }
        });
        getContentPane().add(buttondel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 130, -1));

        buttonstart.setText("Register hub");
        getContentPane().add(buttonstart, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 320, 130, -1));

        jButton1.setText("Edit Selected");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, 130, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonaddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttonaddActionPerformed
    {//GEN-HEADEREND:event_buttonaddActionPerformed
       String newURL=JOptionPane.showInputDialog(this, "Enter the hublist URL :");
       if(newURL==null)
           return;
       PluginMain.curlist.add(newURL);
       JOptionPane.showMessageDialog(this,"Hublist entry added succesfully.",
                    Vars.HubName,JOptionPane.INFORMATION_MESSAGE);
                
       refresh();
}//GEN-LAST:event_buttonaddActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_formFocusGained
    {//GEN-HEADEREND:event_formFocusGained
        // TODO add your handling code here:
       
            
        
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowGainedFocus
    {//GEN-HEADEREND:event_formWindowGainedFocus
        // TODO add your handling code here:
        refresh();
         
    }//GEN-LAST:event_formWindowGainedFocus
public void refresh()
{
    DefaultTableModel HublistsModel=(DefaultTableModel) hubliststable.getModel();
        HublistsModel.setRowCount(0) ;
        
        for(Hublist hList : PluginMain.curlist.hList)
                
                HublistsModel.addRow(new Object[]{hList.URL,hList.Website,hList.status});
}
    
    private void buttondelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_buttondelActionPerformed
    {//GEN-HEADEREND:event_buttondelActionPerformed
        int row=hubliststable.getSelectedRow();
         String curURL=(String)hubliststable.getModel().getValueAt(row,0);
         if(PluginMain.curlist.rem(curURL))
             JOptionPane.showMessageDialog(this,"Hublist entry deleted succesfully.",
                    Vars.HubName,JOptionPane.INFORMATION_MESSAGE);
         else
             JOptionPane.showMessageDialog(this,"Unable to delete. Error.",
                    Vars.HubName,JOptionPane.ERROR_MESSAGE);
         
         refresh();
    }//GEN-LAST:event_buttondelActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed
        
        
        int row=hubliststable.getSelectedRow();
         String curURL=(String)hubliststable.getModel().getValueAt(row,0);
         
        String newURL=JOptionPane.showInputDialog(this, "ReEnter the hublist URL :",curURL);
       if(newURL==null)
           return;
       if(PluginMain.curlist.mod(curURL,newURL))
          JOptionPane.showMessageDialog(this,"Hublist entry modified succesfully.",
                    Vars.HubName,JOptionPane.INFORMATION_MESSAGE);
       else
         JOptionPane.showMessageDialog(this,"Unable to modify. Error.",
                    Vars.HubName,JOptionPane.ERROR_MESSAGE);
         refresh();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonadd;
    private javax.swing.JButton buttondel;
    private javax.swing.JButton buttonstart;
    private javax.swing.JTable hubliststable;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
