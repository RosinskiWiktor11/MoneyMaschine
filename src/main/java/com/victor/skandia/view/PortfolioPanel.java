package com.victor.skandia.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JPanel;

import com.victor.entity.InvestmentFund;
import com.victor.skandia.control.SkandiaController;
import com.victor.skandia.view.portfolio.PorfolioFundPanel;
import com.victor.skandia.view.portfolio.ReportPortfolioPanel;
import com.victor.utils.ColorUtils;

/**
 * @author Wiktor Rosinski<br/>
 * 	panel operacji na portfelu
 *
 */
@SuppressWarnings("serial")
public class PortfolioPanel extends javax.swing.JPanel {
	
	private SkandiaController controller; 
	private JPanel reportPanel;
	private ReportPortfolioPanel newPortfolioPanel=null;

    private javax.swing.JButton buttonCheckPortfolio;
    private javax.swing.JButton buttonNewPortfolio;
    private javax.swing.JButton buttonSchowPortfolio;
    private javax.swing.JButton buttonImportPortfolioData;
    @SuppressWarnings("rawtypes")
	private javax.swing.JList jListPortfolioFund;
    private javax.swing.JScrollPane jScrollPane;
    
    private List<InvestmentFund> fundsWithOpenSignals;
    
    
    /**
     * Creates new form PortfolioPanel
     */
    public PortfolioPanel(SkandiaController controller, JPanel reportPanel) {
    	this.controller=controller;
    	this.reportPanel=reportPanel;
        initComponents();
        loadDataToJList();
    }

                    
   @SuppressWarnings("rawtypes")
   private void initComponents() {

        buttonSchowPortfolio = new javax.swing.JButton();
        buttonCheckPortfolio = new javax.swing.JButton();
        buttonNewPortfolio = new javax.swing.JButton();
        jScrollPane = new javax.swing.JScrollPane();
        jListPortfolioFund = new javax.swing.JList();
        buttonImportPortfolioData = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Portfel", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 14))); // NOI18N

        buttonSchowPortfolio.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonSchowPortfolio.setText("Poka¿ portfel");
        buttonSchowPortfolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSchowPortfolioActionPerformed(evt);
            }
        });

        buttonCheckPortfolio.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonCheckPortfolio.setText("SprawdŸ portfel");
        buttonCheckPortfolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCheckPortfolioActionPerformed(evt);
            }
        });

        buttonNewPortfolio.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        buttonNewPortfolio.setText("Nowy portfel");
        buttonNewPortfolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNewPortfolioActionPerformed(evt);
            }
        });

        jScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jListPortfolioFund.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fundusze w obecnym porfelu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 0, 11))); // NOI18N
        jListPortfolioFund.setFont(new java.awt.Font("Times New Roman", 0, 11)); 
        jScrollPane.setViewportView(jListPortfolioFund);

        buttonImportPortfolioData.setFont(new java.awt.Font("Times New Roman", 0, 12)); 
        buttonImportPortfolioData.setText("Importuj dane portfela");
        buttonImportPortfolioData.setToolTipText("Pobiera dane ze strony o iloœci kupionych jednostek oraz przy okazji wartoœæ kapita³u portfela");
        buttonImportPortfolioData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonImportPortfolioDataActionPerformed(e);
			}
		});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonCheckPortfolio, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                    .addComponent(buttonNewPortfolio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSchowPortfolio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonImportPortfolioData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonSchowPortfolio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonCheckPortfolio)
                .addGap(18, 18, 18)
                .addComponent(buttonNewPortfolio)
                .addGap(18, 18, 18)
                .addComponent(buttonImportPortfolioData)
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane)
                .addContainerGap())
        );
    }
   
   
   @SuppressWarnings("unchecked")
   private void loadDataToJList(){
	  
	   jListPortfolioFund.setModel(new AbstractListModel<String>() {
		   
		   List<InvestmentFund> portfolioFundList=controller.getActiveInvestmentFundList();
		   
			@Override
			public String getElementAt(int index) {
				return portfolioFundList.get(index).getShortName();
			}

			@Override
			public int getSize() {
				return portfolioFundList.size();
			}
       	
		});   
   }
   
   /** listener obslugujacy przycisk sprawdzajacy portfel - sygnaly kupna, sprzedarzy */
   private void buttonCheckPortfolioActionPerformed(java.awt.event.ActionEvent evt) {                                                      
        new Thread(){
        	public void run(){
        		generateOpenSignals();
        		List<PorfolioFundPanel> portfolioFundPanelList= preparePortfolioFundPanels();
        		if(portfolioFundPanelList==null)
        			return;
        		newPortfolioPanel = new ReportPortfolioPanel(controller,fundsWithOpenSignals,  portfolioFundPanelList);
				reportPanel.removeAll();
				reportPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
				reportPanel.add(newPortfolioPanel);
				reportPanel.setVisible(false);
				reportPanel.setVisible(true);
        	}
        }.start();
    }                                                     

   /** listener obslugujacy przycisk pokazujacy szczegoly dotyczace portfela */
    private void buttonSchowPortfolioActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    /** listener obslugujacy przycisk tworzenia nowego portfela */
    private void buttonNewPortfolioActionPerformed(java.awt.event.ActionEvent evt) {  
		new Thread() {
			public void run() {
				if(fundsWithOpenSignals==null || fundsWithOpenSignals.size()==0)
					newPortfolioPanel = new ReportPortfolioPanel(controller,null );
				else
					newPortfolioPanel = new ReportPortfolioPanel(controller,fundsWithOpenSignals );
				reportPanel.removeAll();
				reportPanel.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
				reportPanel.add(newPortfolioPanel);
				reportPanel.setVisible(false);
				reportPanel.setVisible(true);
			}
		}.start();
       
    }        
    
    /** obsluga przyciksu importu danych o portfelu */
    public void buttonImportPortfolioDataActionPerformed(ActionEvent e){
    	new Thread(){
    		public void run(){
    			controller.importPortfolioData();
    		}
    	}.start();
    }
                 

    private void generateOpenSignals(){
    	List<InvestmentFund> openSignalFundList=controller.generateOpenTransactionSingals();
    	fundsWithOpenSignals=openSignalFundList;
    }
    
    private List<PorfolioFundPanel> preparePortfolioFundPanels(){
    	List<InvestmentFund>[] fundsTable=controller.checkInvestmentFundFromPortfolio();
    	if(fundsTable==null)
    		return null;
    	List<PorfolioFundPanel> fundPanelList=new ArrayList<PorfolioFundPanel>();
    	PorfolioFundPanel panel;
    	boolean odd=true;
    	
    	for(InvestmentFund fund: fundsTable[0]){
    		panel=new PorfolioFundPanel(fund,null, controller, false, true);
    		if(odd)
    			panel.setBackground(ColorUtils.getGreen1());
    		else
    			panel.setBackground(ColorUtils.getGreen2());
    		fundPanelList.add(panel);
    		odd=!odd;
    	}
    	
    	odd=true;
    	for(InvestmentFund fund: fundsTable[1]){
    		panel=new PorfolioFundPanel(fund,null, controller, false, null);
    		if(odd)
    			panel.setBackground(ColorUtils.getYellow1());
    		else
    			panel.setBackground(ColorUtils.getYellow2());
    		
    		fundPanelList.add(panel);
    		odd=!odd;
    	}
    	
    	odd=true;
    	for(InvestmentFund fund: fundsTable[2]){
    		panel=new PorfolioFundPanel(fund, null, controller, false, false);
    		if(odd)
    			panel.setBackground(ColorUtils.getRed1());
    		else
    			panel.setBackground(ColorUtils.getRed2());
    		
    		fundPanelList.add(panel);
    		odd=!odd;
    	}
    	
    	return fundPanelList;
    }
}
