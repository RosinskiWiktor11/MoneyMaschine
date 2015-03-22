package com.victor.gpw.view;


import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import com.victor.entity.GpwCompany;
import com.victor.gpw.control.GpwController;
import com.victor.gpw.view.test.FundTestReportPanel;
import com.victor.gpw.view.test.TestReportPanel;
import com.victor.skandia.model.TestResult;


/**
 * @author Wiktor
 *
 */
public class AlgorithmPanel extends JPanel {
	
	

	/** kontroler dla zakladki skandia */
	private GpwController controller;
	
    private JButton buttonTest;
    private JList<GpwCompany> jFundList;
    private JLabel labelTestFor;
    private JLabel labelTestingFundName;
    private JProgressBar progressBarTest;
    private JScrollPane scrollPaneFundList;
    private JPanel panelConsole;
	
    /**  */
	private static final long serialVersionUID = 1L;
	/**
     * Creates new form TestPanel
     */
    public AlgorithmPanel(GpwController controller, JPanel reportPanel) {
    	super();
    	this.panelConsole=reportPanel;
    	this.controller=controller;
        initComponents();
    }


    @SuppressWarnings("serial")
	private void initComponents() {

        buttonTest = new JButton();
        scrollPaneFundList = new JScrollPane();
        jFundList = new JList<GpwCompany>();
        progressBarTest = new JProgressBar();
        labelTestFor = new JLabel();
        labelTestingFundName = new JLabel();
        
        setPreferredSize(new java.awt.Dimension(500, 164));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Przygotowanie algorytmów", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        buttonTest.setFont(new Font("Times New Roman", 0, 12)); // NOI18N
        buttonTest.setText("start");
        buttonTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTestActionPerformed(evt);
            }
        });

        jFundList.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jFundList.setModel(new javax.swing.AbstractListModel<GpwCompany>() {
            List<GpwCompany> gpwCompanyList =controller.getActiveGpwCompanyList();
            public int getSize() { return gpwCompanyList.size(); }
            public GpwCompany getElementAt(int i) { return gpwCompanyList.get(i); }
        });
        scrollPaneFundList.setViewportView(jFundList);

        progressBarTest.setValue(10);

        labelTestFor.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        labelTestFor.setText("test dla:");

        labelTestingFundName.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labelTestingFundName.setText("ML 4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPaneFundList, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelTestingFundName, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTestFor, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonTest))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(progressBarTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTestFor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labelTestingFundName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonTest))
                    .addComponent(scrollPaneFundList, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(progressBarTest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        setVisible(true);
    }

    private void buttonTestActionPerformed(java.awt.event.ActionEvent evt) {	
    	final TestReportPanel testReportPanel=new TestReportPanel();
    	panelConsole.removeAll();
    	panelConsole.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
		panelConsole.add(testReportPanel);
		panelConsole.setVisible(false);
		panelConsole.setVisible(true);
		
    	new Thread(){
    		public void run(){
    			List<GpwCompany> selectedCompanies=jFundList.getSelectedValuesList();
    			for(GpwCompany company:selectedCompanies){
    				TestResult result=controller.makeAlgorithmTest(company);
    				if(result!=null)
    					prepareReport(testReportPanel, result);
    			}

    		}
    	}.start();
    }
    
    private void prepareReport(TestReportPanel panel, TestResult result){
    	if(result!=null){
	    	FundTestReportPanel testReportPanel=new FundTestReportPanel(controller, result);
	    	panel.getPanelResultList().add(testReportPanel);
	    	panelConsole.setVisible(false);
			panelConsole.setVisible(true);
    	}
    }
}

