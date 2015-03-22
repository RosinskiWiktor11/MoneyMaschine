package com.victor.skandia.view.portfolio;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JTextField;

import com.victor.skandia.model.DateChangeInterface;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;
import com.victor.view.calendarDialog.CalendarPickerDialog;

/**
 * @author Wiktor Rosinski
 *
 */
@SuppressWarnings("serial")
public class DateSelectDialog extends JDialog{
	
	private javax.swing.JButton buttonConfirm;
	private javax.swing.JLabel labelFrom;
	private javax.swing.JLabel labelUntil;
	private javax.swing.JTextField textFieldFrom;
	private javax.swing.JTextField textFieldUntil;
	
	private DateChangeInterface parent;

    public DateSelectDialog(DateChangeInterface parent) {
        super();
        initComponents();
        loadData();
        this.parent=parent;
        buttonConfirm.requestFocusInWindow();
    }

                       
    private void initComponents() {

        textFieldUntil = new javax.swing.JTextField();
        labelUntil = new javax.swing.JLabel();
        textFieldFrom = new javax.swing.JTextField();
        labelFrom = new javax.swing.JLabel();
        buttonConfirm = new javax.swing.JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wybierz daty");

        textFieldUntil.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        textFieldUntil.setText("sdfsdfs");
        

        labelUntil.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelUntil.setText("do");

        textFieldFrom.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        textFieldFrom.setText("sdfsdfs");
  

        labelFrom.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelFrom.setText("od");

        buttonConfirm.setText("OK");
        buttonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonConfirmActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelUntil, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldUntil, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonConfirm, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelFrom)
                    .addComponent(textFieldFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelUntil)
                    .addComponent(textFieldUntil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(buttonConfirm)
                .addContainerGap())
        );
        
        textFieldFrom.addFocusListener(new FocusListener() {
			private boolean active = false;

			public void focusLost(FocusEvent e) {
				buttonConfirm.requestFocusInWindow();
			}

			public void focusGained(FocusEvent e) {
				if (active) {
					CalendarPickerDialog.showCalendar(textFieldFrom);
					active = false;
				} else {
					active = true;
				}
			}
		});
       textFieldUntil.addFocusListener(new FocusListener() {
			private boolean active = true;

			public void focusLost(FocusEvent e) {
				buttonConfirm.requestFocusInWindow();
			}

			public void focusGained(FocusEvent e) {
				if (active) {
					CalendarPickerDialog.showCalendar(textFieldUntil);
					active = false;
				} else {
					active = true;
				}
			}
		});
       
        pack();
    }                    

    private void loadData(){
    	GregorianCalendar date=new GregorianCalendar();
    	textFieldUntil.setText(DateUtils.convertToStringFormat(date, DateFormat.DD_MM_YYYY));
    	date.add(GregorianCalendar.MONTH, -3);
    	textFieldFrom.setText(DateUtils.convertToStringFormat(date, DateFormat.DD_MM_YYYY));	
    }
    
    private void addFocusListeners(final JTextField textField){
    	textField.addFocusListener(new FocusListener() {
			private boolean active = true;

			public void focusLost(FocusEvent e) {
				buttonConfirm.requestFocusInWindow();
			}

			public void focusGained(FocusEvent e) {
				if (active) {
					CalendarPickerDialog.showCalendar(textField);
					active = false;
				} else {
					active = true;
				}
			}
		});
    	
    	
    }
    
                                         

    private void buttonConfirmActionPerformed(java.awt.event.ActionEvent evt) {
    	new Thread(){
    		public void run(){
    			parent.setStartDate(DateUtils.convertToDateFormat(textFieldFrom.getText(), DateFormat.DD_MM_YYYY));
    	        parent.setEndDate(DateUtils.convertToDateFormat(textFieldUntil.getText(), DateFormat.DD_MM_YYYY));
    	        parent.run();
    		}
    	}.start();
       
        this.setVisible(false);
        this.dispose();
    }                                             


}
