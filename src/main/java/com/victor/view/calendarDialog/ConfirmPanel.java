package com.victor.view.calendarDialog;

import java.util.GregorianCalendar;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author Wiktor Rosinski
 *
 */
public class ConfirmPanel extends JPanel{
	
    private javax.swing.JButton bttnConfirm;
    
    JDialog owner;
    
	public ConfirmPanel(JDialog owner) {
		this.owner=owner;
        initComponents();
    }


                          
    private void initComponents() {

        bttnConfirm = new CalendarButton("images/dialog/button/ok.png");
//    	bttnConfirm=new JButton("test");

        bttnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttnConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(120, Short.MAX_VALUE)
                .addComponent(bttnConfirm)
                .addContainerGap(120, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bttnConfirm)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setVisible(true);
        setOpaque(false);
    }                     

    private void bttnConfirmActionPerformed(java.awt.event.ActionEvent evt) {                                            
    	owner.setVisible(false);
    	owner.dispose();
    }                                           

                  

                


}
