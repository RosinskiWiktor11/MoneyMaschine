package com.victor.skandia.view;

import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import com.victor.entity.InvestmentFund;
import com.victor.skandia.control.SkandiaController;
import com.victor.skandia.model.TestResult;
import com.victor.skandia.view.testReport.FundTestReportPanel;
import com.victor.skandia.view.testReport.TestReportPanel;

/**
 * @author Wiktor
 *
 */
public class AlgorithmPanel extends JPanel {
	
	

	/** kontroler dla zakladki skandia */
	private SkandiaController controller;
	
    private JButton buttonTest;
    private JList<InvestmentFund> jFundList;
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
    public AlgorithmPanel(SkandiaController controller, JPanel reportPanel) {
    	super();
    	this.panelConsole=reportPanel;
    	this.controller=controller;
        initComponents();
    }


    @SuppressWarnings("serial")
	private void initComponents() {

        buttonTest = new JButton();
        scrollPaneFundList = new JScrollPane();
        jFundList = new JList<InvestmentFund>();
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
        jFundList.setModel(new javax.swing.AbstractListModel<InvestmentFund>() {
            List<InvestmentFund> investmentFundTable =controller.getActiveInvestmentFundList();
            public int getSize() { return investmentFundTable.size(); }
            public InvestmentFund getElementAt(int i) { return investmentFundTable.get(i); }
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
    			List<InvestmentFund> selectedFunds=jFundList.getSelectedValuesList();
    			for(InvestmentFund fund:selectedFunds){
    				TestResult result=controller.makeAlgorithmTest(fund);
    				prepareReport(testReportPanel, result);
    			}

    		}
    	}.start();
    }
    
    private void prepareReport(TestReportPanel panel, TestResult result){
    	FundTestReportPanel testReportPanel=new FundTestReportPanel(controller, result);
    	panel.getPanelResultList().add(testReportPanel);
    	panelConsole.setVisible(false);
		panelConsole.setVisible(true);
    }
}

