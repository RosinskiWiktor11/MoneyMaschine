package com.victor.skandia.view.portfolio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.victor.entity.InvestmentFund;
import com.victor.entity.PortfolioFund;
import com.victor.skandia.control.SkandiaController;
import com.victor.skandia.model.CustomerGroupType;
import com.victor.utils.ColorUtils;

/**
 * @author Wiktor Rosinski<br/>
 * 	panel dla nowego portfela
 *
 */
@SuppressWarnings("serial")
public class ReportPortfolioPanel extends javax.swing.JPanel {

	private SkandiaController controller;
	private List<InvestmentFund> fundListWithOpenSignal;
	private List<InvestmentFund> porfolioFundList;
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
    public ReportPortfolioPanel(SkandiaController controller, List<InvestmentFund> fundsWithOpenSignals) {
    	if(fundsWithOpenSignals!=null){
    		this.fundListWithOpenSignal=fundsWithOpenSignals;
    		this.porfolioFundList=new ArrayList<InvestmentFund>();
    	}
    	this.controller=controller;
        initComponents();
        loadDataToPanels();
    }
    
    public ReportPortfolioPanel(SkandiaController controller, List<InvestmentFund> fundsWithOpenSignals, List<PorfolioFundPanel> porfolioFundPanelList) {
    	if(fundsWithOpenSignals!=null){
    		this.fundListWithOpenSignal=fundsWithOpenSignals;
    		this.porfolioFundList=new ArrayList<InvestmentFund>();
    	}
    	this.controller=controller;
        initComponents();
        loadDataToPanels(porfolioFundPanelList);
    }
    
    
    
    public void addFundToPortfolio(InvestmentFund fund){
    	if(porfolioFundList.size()>=10){
    		JOptionPane.showMessageDialog(this, "Zbyt du¿a liczba funduszy. Maksymalnie mo¿e byæ tylko 10.", "Liczba funduszy powy¿ej 10", JOptionPane.WARNING_MESSAGE);
    	}else{
	    	porfolioFundList.add(fund);
	    	fundListWithOpenSignal.remove(fund);
	    	sortFundList(porfolioFundList);
	    	sortFundList(fundListWithOpenSignal);
	    	loadDataToPanels();
    	}
    }
    public void removeFundFromPortfolio(InvestmentFund fund){
    	porfolioFundList.remove(fund);
    	fundListWithOpenSignal.add(fund);
    	sortFundList(porfolioFundList);
    	sortFundList(fundListWithOpenSignal);
    	loadDataToPanels();
    }
    
    public JPanel showChartForFundWithSignals(InvestmentFund fund, GregorianCalendar dateFrom, GregorianCalendar dateUntil){
    	return controller.showChartForFundWithSignals(fund, dateFrom, dateUntil);
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
        buttonRecalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRecalculateActionPerformed(evt);
            }
        });

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

    /** przycisk przelicz % */
    private void buttonRecalculateActionPerformed(java.awt.event.ActionEvent evt) {  
    	int percentage=100/porfolioFundList.size();
    	int rest=100%porfolioFundList.size();
    	portfolioFundListPanel.removeAll();
    	PorfolioFundPanel temp;
    	boolean first=true;
    	int i=1;
    	
    	panelList=new ArrayList<PorfolioFundPanel>();
    	if(porfolioFundList!=null)
    	for(InvestmentFund fund: porfolioFundList){
    		temp=new PorfolioFundPanel(fund, this, controller, true, false);
    		
    		if(i==1)
    			temp.setPercentage(new BigDecimal(percentage+rest));
    		else
    			temp.setPercentage(new BigDecimal(percentage));
    				
    		if(i++%2==1)
    			temp.setBackground(ColorUtils.getTableColor1());
    		else
    			temp.setBackground(ColorUtils.getTableColor2());
    		
    		
    		portfolioFundListPanel.add(temp);
    		panelList.add(temp);
    	}
    	
    	setVisible(false);
		setVisible(true);
    }                                                 

    /** zapisz nowy portfel */
    private void buttonSaveNewPortfolioActionPerformed(java.awt.event.ActionEvent evt) {  
    	boolean setForYourCustomers;
    	new Thread(){
    		public void run(){
    			savePortfolioProcess();
    		}
    	}.start();
    }   
    

    
    /** sprawdza czy suma udzialow wszystkich funduszy w nowym portfelu wynosi 100 */
    private boolean checkPercentage(){
    	int percentage=0;
    	for(PorfolioFundPanel panel: panelList){
    		percentage+=panel.getPercentage().intValue();
    	}
    	if(percentage==100)
    		return true;
    	else
    		return false;
    }
    
    /** wywoluje metode kontrollera generujaca sygnaly otwarcia */
    private void generateOpenSignals(){
    	this.fundListWithOpenSignal=controller.generateOpenTransactionSingals();
    	this.porfolioFundList=new ArrayList<InvestmentFund>();
    }
    
    private void loadDataToPanels(){
    	openingSignalFundListPanel.removeAll();
        portfolioFundListPanel.removeAll();
        openingSignalFundListPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        portfolioFundListPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        int i=1;
        PorfolioFundPanel temp;
        if(fundListWithOpenSignal!=null)
	    	for(InvestmentFund fund: fundListWithOpenSignal){
	    		temp=new PorfolioFundPanel(fund, this, controller, false, true);
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
    	for(InvestmentFund fund: porfolioFundList){
    		temp=new PorfolioFundPanel(fund, this, controller, true, false);
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
        if(fundListWithOpenSignal!=null)
	    	for(InvestmentFund fund: fundListWithOpenSignal){
	    		temp=new PorfolioFundPanel(fund, this, controller, false, true);
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
    private void sortFundList(List<InvestmentFund> fundList){
    	boolean sorted=false;
    	while(!sorted){
    		sorted=true;
    		for(int i=1;i<fundList.size();i++){
    			if(fundList.get(i-1).getWeight().getRateOfProfit().compareTo(fundList.get(i).getWeight().getRateOfProfit())<0){
    				InvestmentFund temp=fundList.get(i);
    				fundList.set(i, fundList.get(i-1));
    				fundList.set(i-1, temp);
    				i--;
    				sorted=false;
    			}
    		}
    	}
    }
    
    /** przeprowadza proces zapisywania portfela */
    private void savePortfolioProcess(){
    	int i=JOptionPane.showOptionDialog(null, "Komu przypisaæ portfel?", "Zapis portfela", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, CustomerGroupType.values(), null);
    	CustomerGroupType customerType=CustomerGroupType.getEnum(i);
    	if(checkPercentage()){
    		List<PortfolioFund> portfolioFundList=new ArrayList<PortfolioFund>();
    		PortfolioFund temp;
    		for(PorfolioFundPanel panel:panelList){
    			temp=new PortfolioFund();
    			temp.setInvestmentFund(panel.getFund());
    			temp.setPercentage(panel.getPercentage());
    			portfolioFundList.add(temp);
    		}
    		controller.createNewPortfolio(portfolioFundList, customerType);
    		JOptionPane.showMessageDialog(this, "Portfel zosta³ zapisany", "Zapis zakoñczony powodzeniem", JOptionPane.INFORMATION_MESSAGE);
    	}else{
    		JOptionPane.showMessageDialog(this, "Suma udzia³ów poszczególnych funduszy w nowym portfelu nie jest równa 100%", "Z³y rozk³ad udzia³ów funduszy", JOptionPane.WARNING_MESSAGE);
    	}
    }


}
