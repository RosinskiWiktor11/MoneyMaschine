package com.victor.gpw.view.portfolio;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.victor.entity.CustomerGpwTransaction;
import com.victor.entity.GpwCompany;
import com.victor.gpw.control.GpwController;
import com.victor.utils.ColorUtils;

/**
 * @author Wiktor Rosinski<br/>
 * 	panel dla nowego portfela
 *
 */
@SuppressWarnings("serial")
public class ReportPortfolioPanel extends javax.swing.JPanel {

	private GpwController controller;
	private List<GpwCompany> companiesWithOpenSignals;
	private List<GpwCompany> porfolioFundList;
	private List<PorfolioFundPanel> panelList;
	
	private javax.swing.JButton buttonGenerateOpenSignals;
    private javax.swing.JButton buttonRecalculate;
    private javax.swing.JButton buttonSaveNewPortfolio;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel openingSignalFundListPanel;
    private javax.swing.JPanel portfolioFundListPanel;
	
    

    
	
    /**
     * Creates new form NewPortfolioPanel
     */
    public ReportPortfolioPanel(GpwController controller, List<GpwCompany> companiesWithOpenSignals) {
    	if(companiesWithOpenSignals!=null){
    		this.companiesWithOpenSignals=companiesWithOpenSignals;
    		this.porfolioFundList=new ArrayList<GpwCompany>();
    	}
    	this.controller=controller;
        initComponents();
        loadDataToPanels();
    }
    
    public ReportPortfolioPanel(GpwController controller, List<GpwCompany> fundsWithOpenSignals, List<PorfolioFundPanel> porfolioFundPanelList) {
    	if(fundsWithOpenSignals!=null){
    		this.companiesWithOpenSignals=fundsWithOpenSignals;
    		this.porfolioFundList=new ArrayList<GpwCompany>();
    	}
    	this.controller=controller;
        initComponents();
        loadDataToPanels(porfolioFundPanelList);
    }
    
    
    
    public void addFundToPortfolio(GpwCompany company){
	   	porfolioFundList.add(company);
	   	companiesWithOpenSignals.remove(company);
	   	sortFundList(porfolioFundList);
	    sortFundList(companiesWithOpenSignals);
	    loadDataToPanels();
    }
    public void removeFundFromPortfolio(GpwCompany company){
    	porfolioFundList.remove(company);
    	companiesWithOpenSignals.add(company);
    	sortFundList(porfolioFundList);
    	sortFundList(companiesWithOpenSignals);
    	loadDataToPanels();
    }
    
    public JPanel showChartForFundWithSignals(GpwCompany company, GregorianCalendar dateFrom, GregorianCalendar dateUntil){
    	return controller.showChartForFundWithSignals(company, dateFrom, dateUntil);
    }
               
    private void initComponents() {

        buttonGenerateOpenSignals = new javax.swing.JButton();
        buttonRecalculate = new javax.swing.JButton();
        buttonSaveNewPortfolio = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        portfolioFundListPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        openingSignalFundListPanel = new javax.swing.JPanel();

        buttonGenerateOpenSignals.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonGenerateOpenSignals.setText("ZnajdŸ fundusze");
        buttonGenerateOpenSignals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGenerateOpenSignalsActionPerformed(evt);
            }
        });

        buttonRecalculate.setText("Przelicz %");
        buttonRecalculate.setVisible(false);

        buttonSaveNewPortfolio.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonSaveNewPortfolio.setText("Zapisz");
        buttonSaveNewPortfolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveNewPortfolioActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);

        portfolioFundListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Portfel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        javax.swing.GroupLayout portfolioFundListPanelLayout = new javax.swing.GroupLayout(portfolioFundListPanel);
        portfolioFundListPanel.setLayout(portfolioFundListPanelLayout);
        portfolioFundListPanelLayout.setHorizontalGroup(
            portfolioFundListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        portfolioFundListPanelLayout.setVerticalGroup(
            portfolioFundListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 434, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(portfolioFundListPanel);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );

        jScrollPane1.setBorder(null);

        openingSignalFundListPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Znalezione fundusze", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 12))); // NOI18N

        javax.swing.GroupLayout openingSignalFundListPanelLayout = new javax.swing.GroupLayout(openingSignalFundListPanel);
        openingSignalFundListPanel.setLayout(openingSignalFundListPanelLayout);
        openingSignalFundListPanelLayout.setHorizontalGroup(
            openingSignalFundListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        openingSignalFundListPanelLayout.setVerticalGroup(
            openingSignalFundListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 432, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(openingSignalFundListPanel);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonGenerateOpenSignals)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRecalculate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
                .addComponent(buttonSaveNewPortfolio)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonGenerateOpenSignals)
                    .addComponent(buttonRecalculate)
                    .addComponent(buttonSaveNewPortfolio))
                .addContainerGap())
        );
    }
    
    /** przycisk Znajdz fundusze */
    private void buttonGenerateOpenSignalsActionPerformed(java.awt.event.ActionEvent evt) {
    	new Thread(){
    		public void run(){
    			generateOpenSignals();
    			loadDataToPanels();  
    		}
    	}.start();
       
    }                                                         

                                            

    /** zapisz nowy portfel */
    private void buttonSaveNewPortfolioActionPerformed(java.awt.event.ActionEvent evt) {  
//    	boolean setForYourCustomers;
    	new Thread(){
    		public void run(){
    			savePortfolioProcess();
    		}
    	}.start();
    }   
    

    

    
    /** wywoluje metode kontrollera generujaca sygnaly otwarcia */
    private void generateOpenSignals(){
    	this.companiesWithOpenSignals=controller.generateOpenTransactionSingals();
    	this.porfolioFundList=new ArrayList<GpwCompany>();
    }
    
    private void loadDataToPanels(){
    	openingSignalFundListPanel.removeAll();
        portfolioFundListPanel.removeAll();
        openingSignalFundListPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        portfolioFundListPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        int i=1;
        PorfolioFundPanel temp;
        if(companiesWithOpenSignals!=null)
	    	for(GpwCompany company: companiesWithOpenSignals){
	    		temp=new PorfolioFundPanel(company, this, controller, false, true);
	    		if(i++%2==1){
	    			temp.setBackground(ColorUtils.getGreen1());
	    		}else{
	    			temp.setBackground(ColorUtils.getGreen2());
	    		}
	    		openingSignalFundListPanel.add(temp);
	    	}
    	i=1;
    	panelList=new ArrayList<PorfolioFundPanel>();
    	if(porfolioFundList!=null)
    	for(GpwCompany company: porfolioFundList){
    		temp=new PorfolioFundPanel(company, this, controller, true, false);
    		if(i++%2==1){
    			temp.setBackground(ColorUtils.getTableColor1());
    		}else{
    			temp.setBackground(ColorUtils.getTableColor2());
    		}
    		portfolioFundListPanel.add(temp);
    		panelList.add(temp);
    	}
    	
    	setVisible(false);
    	setVisible(true);
    }
    
    private void loadDataToPanels(List<PorfolioFundPanel> portfolioPanelList){
    	openingSignalFundListPanel.removeAll();
        portfolioFundListPanel.removeAll();
        openingSignalFundListPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        portfolioFundListPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        int i=1;
        PorfolioFundPanel temp;
        if(companiesWithOpenSignals!=null)
	    	for(GpwCompany company: companiesWithOpenSignals){
	    		temp=new PorfolioFundPanel(company, this, controller, false, true);
	    		if(i++%2==1){
	    			temp.setBackground(ColorUtils.getGreen1());
	    		}else{
	    			temp.setBackground(ColorUtils.getGreen2());
	    		}
	    		openingSignalFundListPanel.add(temp);
	    	}
        
        for(PorfolioFundPanel panel: portfolioPanelList){
        	portfolioFundListPanel.add(panel);
        }
        
        setVisible(false);
    	setVisible(true);
    }
    
    
    /** sortuje fundusze wg stopy zwrotu od najwyzszej */
    private void sortFundList(List<GpwCompany> companyList){
    	boolean sorted=false;
    	while(!sorted){
    		sorted=true;
    		for(int i=1;i<companyList.size();i++){
    			if(companyList.get(i-1).getWeight().getRateOfProfit().compareTo(companyList.get(i).getWeight().getRateOfProfit())<0){
    				GpwCompany company=companyList.get(i);
    				companyList.set(i, companyList.get(i-1));
    				companyList.set(i-1, company);
    				i--;
    				sorted=false;
    			}
    		}
    	}
    }
    
    /** przeprowadza proces zapisywania portfela */
    private void savePortfolioProcess(){
    	List<CustomerGpwTransaction> customerTransactionList=new ArrayList<CustomerGpwTransaction>();
    	CustomerGpwTransaction customerTransaction;
    	for(PorfolioFundPanel panel:panelList){
    		customerTransaction=new CustomerGpwTransaction();
    		customerTransaction.setAmount(panel.getShareAmount());
    		customerTransaction.setOpenDate(new GregorianCalendar());
    		customerTransaction.setCompany(panel.getCompany());
    		customerTransactionList.add(customerTransaction);
    	}
    	controller.createNewPortfolio(customerTransactionList);
    	
    	JOptionPane.showMessageDialog(this, "Spó³ki zosta³y zapisane", "Zapis zakoñczony powodzeniem", JOptionPane.INFORMATION_MESSAGE);
    }


}
