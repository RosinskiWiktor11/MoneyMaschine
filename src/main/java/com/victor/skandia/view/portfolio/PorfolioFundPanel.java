package com.victor.skandia.view.portfolio;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.victor.entity.InvestmentFund;
import com.victor.skandia.control.SkandiaController;
import com.victor.skandia.model.DateChangeInterface;

/**
 * @author Wiktor Rosinski
 * 	panel przedstawiajacy charakterystyke funduszu, dla ktorego zostal wygenerowany sygnal
 *
 */
public class PorfolioFundPanel extends javax.swing.JPanel implements DateChangeInterface{
	
//	private SkandiaController controller;
	private ReportPortfolioPanel owner;
	private InvestmentFund fund;
	private boolean addedToPorfolio;
	private SkandiaController controller;
	
	private GregorianCalendar startDateForChart;
	private GregorianCalendar endDateForChart;
	


	private javax.swing.JButton buttonAddRemoveAction;
    private javax.swing.JButton buttonShowChart;
    private javax.swing.JLabel labelFundName;
    private javax.swing.JLabel labelRateOfProfit;
    private javax.swing.JFormattedTextField textFieldPercentage;
    private MaskFormatter maskFormat;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;  
    
	
    /**
     * Creates new form SignalFundPanel
     */
    public PorfolioFundPanel( InvestmentFund fund, ReportPortfolioPanel owner, SkandiaController controller, boolean isInPorfolio, Boolean signal) {
		this.fund=fund;
		this.owner=owner;
		this.controller=controller;
		this.addedToPorfolio=isInPorfolio;
        try {
			initComponents();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
        loadData(fund, signal);
    }
    
    
    
//    public NewPorfolioFundPanel( InvestmentFund fund, BigDecimal percentage,  NewPortfolioPanel newPortfolioPanel, boolean isInPorfolio, boolean signal) {
//    	this(fund, newPortfolioPanel, isInPorfolio, signal);
//    	if(isInPorfolio)
//    		textFieldPercentage.setText(Double.toString(percentage.doubleValue()));
//    }
    public void setPercentage(BigDecimal percentage){
    	if(addedToPorfolio)
    		textFieldPercentage.setText(Double.toString(percentage.doubleValue()));
    }
    public BigDecimal getPercentage(){
    	if(addedToPorfolio)
    		return new BigDecimal(textFieldPercentage.getText());
    	else 
    		return null;
    }
    
                        
    private void initComponents() throws ParseException {

        labelFundName = new javax.swing.JLabel();
        separator1 = new javax.swing.JSeparator();
       
        separator2 = new javax.swing.JSeparator();
        buttonShowChart = new javax.swing.JButton();
        buttonAddRemoveAction = new javax.swing.JButton();

        labelFundName.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        labelFundName.setText("Fundusz");

        separator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        if(addedToPorfolio){
        	 maskFormat=new MaskFormatter("##");
             textFieldPercentage = new JFormattedTextField(maskFormat);
             textFieldPercentage.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
             
        }else{
        	labelRateOfProfit = new javax.swing.JLabel();
        	labelRateOfProfit.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        	labelRateOfProfit.setText("Sygna³");
        }

        separator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        buttonShowChart.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonShowChart.setText("Wykres");
        buttonShowChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonShowChartActionPerformed(evt);
            }
        });

        buttonAddRemoveAction.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonAddRemoveAction.setText("+");
        buttonAddRemoveAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAddRemoveActionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFundName, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addedToPorfolio?textFieldPercentage:labelRateOfProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonShowChart)
                    .addComponent(buttonAddRemoveAction))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator1)
            .addComponent(separator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelFundName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addedToPorfolio?textFieldPercentage:labelRateOfProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(buttonShowChart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAddRemoveAction)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }                     

    
    
    private void buttonAddRemoveActionActionPerformed(java.awt.event.ActionEvent evt) {             
    	if(addedToPorfolio){
    		owner.removeFundFromPortfolio(fund);
    	}else{
    		owner.addFundToPortfolio(fund);
    	}
    }                                                     

    private void buttonShowChartActionPerformed(java.awt.event.ActionEvent evt) {  
    	new Thread(){
    		public void run(){
    			openDialog();
    		}
    	}.start();
       	
    }  
    
    private void openDialog(){
    	JDialog dialog=new DateSelectDialog(this);
    	dialog.setLocation(300, 400);
    	dialog.setVisible(true);
    }
    
    private void loadData(InvestmentFund fund, Boolean signal){
    	labelFundName.setText(fund.getShortName());
    	if(addedToPorfolio){
    		textFieldPercentage.setText("");
    		buttonAddRemoveAction.setText("-");
    	}else{
    		labelRateOfProfit.setText(Double.toString(fund.getWeight().getRateOfProfit().doubleValue())+"%");
    		if(signal==null){
    			
    		}else if(signal){
    			buttonAddRemoveAction.setText("+");
    		}else if(!signal){
    			
    			buttonAddRemoveAction.setVisible(false);
    		}
    	}
    	labelFundName.setToolTipText(fund.getCurrency().getName());
    	if(owner==null){
    		buttonAddRemoveAction.setVisible(false);
    	}
    }
    
    
    /* (non-Javadoc) @see com.victor.skandia.model.DateChangeInterface#setStartDate(java.util.GregorianCalendar) */
	@Override
	public void setStartDate(GregorianCalendar date) {
		System.out.println("set start date "+date);
		startDateForChart=date;
	}

	/* (non-Javadoc) @see com.victor.skandia.model.DateChangeInterface#setEndDate(java.util.GregorianCalendar) */
	@Override
	public void setEndDate(GregorianCalendar date) {
		System.out.println("set until date "+date);
		endDateForChart=date;
	}

	/* (non-Javadoc) @see com.victor.skandia.model.DateChangeInterface#run() */
	@Override
	public void run() {
		final JPanel chart=controller.showChartForFundWithSignals(fund, startDateForChart, endDateForChart);
		new Thread(){
			public void run(){
				JFrame chartFrame = new ChartFrame("Historia wartoœci jednostek dla "+fund.getShortName(), true, chart);
			}
		}.start();
		
//		JOptionPane.showOptionDialog(null, chart, , JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, null);
	}
  	
  	//*****************************************************************
  	//*****************   GETTERS & SETTERS   *************************
  	//*****************************************************************

    /**{@link PorfolioFundPanel#fund}*/
  	public InvestmentFund getFund() {
  		return fund;
  	}
    
  	/**{@link PorfolioFundPanel#fund}*/
  	public void setFund(InvestmentFund fund) {
  		this.fund = fund;
  	}


  	/**{@link PorfolioFundPanel#addedToPorfolio}*/
  	public boolean isAddedToPorfolio() {
  		return addedToPorfolio;
  	}


  	/**{@link PorfolioFundPanel#addedToPorfolio}*/
  	public void setAddedToPorfolio(boolean addedToPorfolio) {
  		this.addedToPorfolio = addedToPorfolio;
  	}

	
                   
}
