package com.victor.skandia.view;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.victor.gpw.control.GpwController;
import com.victor.skandia.control.SkandiaController;
import com.victor.utils.DateFormat;
import com.victor.utils.DateUtils;
import com.victor.view.calendarDialog.CalendarPickerDialog;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class ImportPanel extends JPanel {

	SkandiaController controller;

	/** tytul panelu wyswietlony w border */
	private final String title = "Import";
	/** etykieta daty importu "od" */
	private JLabel label1;
	/** etykieta daty importu "do" */
	private JLabel label2;
	/** etykieta dla progressImportDate */
	private JLabel label3;
	/** progressBar wskazujacy postep importowanych danych */
	private JProgressBar progressImportBar;
	/** textField data od ktorej rozpocznie sie import cen funduszy */
	private JTextField textFieldStartImportDate;
	/** textField data do ktorej ceny funduszy beda importowane */
	private JTextField textFieldEndImportDate;
	/** przycisk rozpoczynajacy import cen funduszy z zadanym zakresie */
	private JButton buttonImport;

	private boolean isChanged;

	/**
	 * {@link ImportPanel}
	 */
	public ImportPanel(SkandiaController controller2) {
		super();
		isChanged = false;
		this.controller = controller2;
		initComponents();
	}

	private void initComponents() {

		// **************** inicjalizaja kontrolek *************************
		label1 = new JLabel();
		label2 = new JLabel();
		label3 = new JLabel();
		progressImportBar = new JProgressBar();
		textFieldStartImportDate = new JTextField();
		textFieldEndImportDate = new JTextField();
		buttonImport = new JButton();

		// ************ ustawienie parametrow kontolek *********************
		setBorder(javax.swing.BorderFactory.createTitledBorder(null, title,
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Times New Roman", 0, 14))); // NOI18N
//		setMaximumSize(new java.awt.Dimension(506, 100));
		setPreferredSize(new java.awt.Dimension(500, 100));

		label1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		label1.setText("od");

		label2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		label2.setText("do");

		label3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
		label3.setText("wykonano:");
		label3.setVisible(false);

		progressImportBar.setVisible(false);
		
		textFieldStartImportDate.setFont(new java.awt.Font("Times New Roman",
				0, 14)); // NOI18N
		textFieldStartImportDate.setText(DateUtils.convertToStringFormat(new GregorianCalendar(), DateFormat.DD_MM_YYYY));
		textFieldStartImportDate
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						textFieldStartImportDateActionPerformed(evt);
					}
				});
		textFieldStartImportDate.addFocusListener(new FocusListener() {
			private boolean active = true;

			public void focusLost(FocusEvent e) {
				buttonImport.requestFocusInWindow();
			}

			public void focusGained(FocusEvent e) {
				if (active) {
					CalendarPickerDialog.showCalendar(textFieldStartImportDate);
					active = false;
				} else {
					active = true;
				}
				isChanged = true;
			}
		});

		textFieldEndImportDate.setFont(new java.awt.Font("Times New Roman", 0,
				14)); // NOI18N
		textFieldEndImportDate.setText(DateUtils.convertToStringFormat(new GregorianCalendar(), DateFormat.DD_MM_YYYY));
		textFieldEndImportDate
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						textFieldEndImportDateActionPerformed(evt);
					}
				});
		textFieldEndImportDate.addFocusListener(new FocusListener() {
			private boolean active = true;

			public void focusLost(FocusEvent e) {
				buttonImport.requestFocusInWindow();
			}

			public void focusGained(FocusEvent e) {
				if (active) {
					CalendarPickerDialog.showCalendar(textFieldEndImportDate);
					active = false;
				} else {
					active = true;
				}
				isChanged = true;
			}
		});

		buttonImport.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
		buttonImport.setText("importuj");
		buttonImport.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				buttonImportActionPerformed(evt);
			}
		});

		// ************** ustwienie layaout dla panelu *********************
		javax.swing.GroupLayout importPanelLayout = new javax.swing.GroupLayout(
				this);
		setLayout(importPanelLayout);
		importPanelLayout
				.setHorizontalGroup(importPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								importPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												importPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																progressImportBar,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																importPanelLayout
																		.createSequentialGroup()
																		.addGroup(
																				importPanelLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label3,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								105,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								importPanelLayout
																										.createSequentialGroup()
																										.addComponent(
																												label1,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												25,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												textFieldStartImportDate,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												107,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												label2,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												25,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												textFieldEndImportDate,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												107,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addGap(18,
																												18,
																												18)
																										.addComponent(
																												buttonImport)))
																		.addGap(0,
																				89,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		importPanelLayout
				.setVerticalGroup(importPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								importPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												importPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(label1)
														.addComponent(
																textFieldStartImportDate,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textFieldEndImportDate,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(label2)
														.addComponent(
																buttonImport))
										.addGap(18, 18, 18)
										.addComponent(label3)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												progressImportBar,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(27, 27, 27)));
	}
	
//	private String convertToString(GregorianCalendar date){
//    	StringBuffer buffer=new StringBuffer();
//    	int i;
//    	i=date.get(GregorianCalendar.DAY_OF_MONTH);
//    	if(i<10)
//    		buffer.append("0");
//    	buffer.append(i).append(".");
//    	i=date.get(GregorianCalendar.MONTH)+1;
//    	if(i<10)
//    		buffer.append("0");
//    	buffer.append(i).append(".");
//    	i=date.get(GregorianCalendar.YEAR);
//    	buffer.append(i);
//    	return buffer.toString();
//    }

	/** metoda wywolana przez textField textFieldStartImportDate */
	private void textFieldStartImportDateActionPerformed(ActionEvent event) {
		buttonImportActionPerformed(event);
	}

	/** metoda wywolana przez textField textFieldEndImportDate */
	private void textFieldEndImportDateActionPerformed(ActionEvent event) {
		buttonImportActionPerformed(event);
	}

	/** metoda wywolana przez przycisk importButton */
	private void buttonImportActionPerformed(ActionEvent event) {
		System.out.println("button action");
		new Thread() {
			public void run() {
				controller.importData(textFieldStartImportDate.getText(),
						textFieldEndImportDate.getText(), isChanged);
			}
		}.start();
	}

}
