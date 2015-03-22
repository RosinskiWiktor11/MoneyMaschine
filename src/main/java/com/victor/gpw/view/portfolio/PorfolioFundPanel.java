package com.victor.gpw.view.portfolio;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.victor.entity.GpwCompany;
import com.victor.gpw.control.GpwController;
import com.victor.skandia.model.DateChangeInterface;
import com.victor.skandia.view.portfolio.ChartFrame;
import com.victor.skandia.view.portfolio.DateSelectDialog;

/**
 * @author Wiktor Rosinski
 * 	panel przedstawiajacy charakterystyke funduszu, dla ktorego zostal wygenerowany sygnal
 *
 */
@SuppressWarnings("serial")
public class PorfolioFundPanel extends javax.swing.JPanel implements DateChangeInterface{
	
//	private SkandiaController controller;
	private ReportPortfolioPanel owner;
	private GpwCompany company;
	private boolean addedToPorfolio;
	private boolean boughtCompany=false;
	private GpwController controller;
	
	private GregorianCalendar startDateForChart;
	private GregorianCalendar endDateForChart;
	


	private javax.swing.JButton buttonAddRemoveAction;
    private javax.swing.JButton buttonShowChart;
    private javax.swing.JLabel labelFundName;
    private javax.swing.JLabel labelRateOfProfit;
    private javax.swing.JFormattedTextField textFieldShareAmount;
    private MaskFormatter maskFormat;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;  
    
	
    /**
     * Creates new form SignalFundPanel
     */
    public PorfolioFundPanel( GpwCompany company, ReportPortfolioPanel owner, GpwController controller, boolean isInPorfolio, Boolean signal) {
    	this.company=company;
    	if(owner==null)
    		boughtCompany=true;
		this.owner=owner;
		this.controller=controller;
		this.addedToPorfolio=isInPorfolio;
        try {
			initComponents();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
        loadData(company, signal);
    }
    
    
    
//    public NewPorfolioFundPanel( InvestmentFund fund, BigDecimal percentage,  NewPortfolioPanel newPortfolioPanel, boolean isInPorfolio, boolean signal) {
//    	this(fund, newPortfolioPanel, isInPorfolio, signal);
//    	if(isInPorfolio)
//    		textFieldPercentage.setText(Double.toString(percentage.doubleValue()));
//    }
    public void setPercentage(BigDecimal percentage){
    	if(addedToPorfolio)
    		textFieldShareAmount.setText(Double.toString(percentage.doubleValue()));
    }
    public BigDecimal getShareAmount(){
    	if(addedToPorfolio)
    		return new BigDecimal(textFieldShareAmount.getText());
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
        	 maskFormat=new MaskFormatter("##########");
             textFieldShareAmount = new JFormattedTextField(maskFormat);
             textFieldShareAmount.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
             
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
                .addComponent(addedToPorfolio?textFieldShareAmount:labelRateOfProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(addedToPorfolio?textFieldShareAmount:labelRateOfProfit, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    		owner.removeFundFromPortfolio(company);
    	}else if(boughtCompany){
    		//TODO dorobic dialog zamykajacy transakcje ackji i obsluzyc sprzedaz akcji
    	}else{
    		owner.addFundToPortfolio(company);
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
    
    private void loadData(GpwCompany company, Boolean signal){
    	labelFundName.setText(company.getName());
    	if(addedToPorfolio || (!addedToPorfolio && boughtCompany)){
    		textFieldShareAmount.setText("");
    		buttonAddRemoveAction.setText("-");
    	}else{
    		labelRateOfProfit.setText(Double.toString(company.getWeight().getRateOfProfit().doubleValue())+"%");
    		if(signal==null){
    			
    		}else if(signal){
    			buttonAddRemoveAction.setText("+");
    		}else if(!signal){
    			
    			buttonAddRemoveAction.setVisible(false);
    		}
    	}
    	labelFundName.setToolTipText(company.getCurrency().getName());
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
		final JPanel chart=controller.showChartForFundWithSignals(company, startDateForChart, endDateForChart);
		new Thread(){
			@SuppressWarnings("unused")
			public void run(){
				JFrame chartFrame = new ChartFrame("Historia cen akcji dla "+company.getName(), true, chart);
			}
		}.start();
		
//		JOptionPane.showOptionDialog(null, chart, , JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, null);
	}
  	
  	//*****************************************************************
  	//*****************   GETTERS & SETTERS   *************************
  	//*****************************************************************




  	/**{@link PorfolioFundPanel#addedToPorfolio}*/
  	public boolean isAddedToPorfolio() {
  		return addedToPorfolio;
  	}


  	/**{@link PorfolioFundPanel#company}*/
	public GpwCompany getCompany() {
		return company;
	}



	/**{@link PorfolioFundPanel#company}*/
	public void setCompany(GpwCompany company) {
		this.company = company;
	}



	/**{@link PorfolioFundPanel#addedToPorfolio}*/
  	public void setAddedToPorfolio(boolean addedToPorfolio) {
  		this.addedToPorfolio = addedToPorfolio;
  	}

	
                   
}
