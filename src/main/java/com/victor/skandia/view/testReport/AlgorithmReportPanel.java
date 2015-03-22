package com.victor.skandia.view.testReport;

import java.math.BigDecimal;

import com.victor.skandia.bisnesInterface.ChooseInterface;
import com.victor.skandia.model.SkandiaSimulationResult;


/**
 *
 * @author Wiktor Rosinski
 */
@SuppressWarnings("serial")
public class AlgorithmReportPanel extends javax.swing.JPanel {
    /** interface do ktorego beda przekierowywane decyzje odnosnie wybrania algorytmu */
	private ChooseInterface chooseManager;
	/** obiekt wyniku testu dla algorytmu */
	private SkandiaSimulationResult algorithmResult;
	/** czy algorytm tego panelu jest algorytmem wybranym przez uzytkonika lub system - wtedy nalezy do innego  */
	private boolean choosen;
	
	private javax.swing.JButton buttonAction;
    private javax.swing.JLabel efficiencyLabel;
    private javax.swing.JLabel efficiencyValue;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel nameCloseAlgorithm;
    private javax.swing.JLabel nameOpenAlgorithm;
    private javax.swing.JLabel negativeLabel;
    private javax.swing.JLabel negativeValue;
    private javax.swing.JLabel positiveLabel;
    private javax.swing.JLabel positiveValue;
    private javax.swing.JLabel properiesOpenAlgorithm;
    private javax.swing.JLabel propertiesCloseAlgorithm;
    private javax.swing.JSeparator separator1;
    private javax.swing.JLabel rateOfProfitLabel;
    private javax.swing.JLabel rateOfProfitValue;

	
    /**
     * Creates new form AlgorithmRaportPanel
     */
    private AlgorithmReportPanel() {
        initComponents();
    }
    
    public AlgorithmReportPanel(ChooseInterface chooseManager, SkandiaSimulationResult result, BigDecimal startCapital, boolean choosen){
    	this();
    	this.choosen=choosen;
    	this.chooseManager=chooseManager;
    	this.algorithmResult=result;
    	loadData(result, startCapital);
    }
                         
    private void initComponents() {

        nameOpenAlgorithm = new javax.swing.JLabel();
        nameCloseAlgorithm = new javax.swing.JLabel();
        properiesOpenAlgorithm = new javax.swing.JLabel();
        propertiesCloseAlgorithm = new javax.swing.JLabel();
        separator1 = new javax.swing.JSeparator();
        positiveLabel = new javax.swing.JLabel();
        positiveValue = new javax.swing.JLabel();
        negativeLabel = new javax.swing.JLabel();
        negativeValue = new javax.swing.JLabel();
        efficiencyLabel = new javax.swing.JLabel();
        efficiencyValue = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        rateOfProfitLabel = new javax.swing.JLabel();
        rateOfProfitValue = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        buttonAction = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(780, 77));

        nameOpenAlgorithm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        nameOpenAlgorithm.setText("TwoAverages");

        nameCloseAlgorithm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        nameCloseAlgorithm.setText("TwoAverages");

        properiesOpenAlgorithm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        properiesOpenAlgorithm.setText("99;99");

        propertiesCloseAlgorithm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        propertiesCloseAlgorithm.setText("99;99");

        separator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        positiveLabel.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        positiveLabel.setText("Pozytywne");
        positiveLabel.setMaximumSize(new java.awt.Dimension(60, 14));
        positiveLabel.setMinimumSize(new java.awt.Dimension(60, 14));
        positiveLabel.setPreferredSize(new java.awt.Dimension(60, 14));

        positiveValue.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        positiveValue.setText("100");
        positiveValue.setToolTipText("Liczba transakcji pozytywnych");
        positiveValue.setMaximumSize(new java.awt.Dimension(60, 14));
        positiveValue.setMinimumSize(new java.awt.Dimension(60, 14));
        positiveValue.setPreferredSize(new java.awt.Dimension(60, 14));

        negativeLabel.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        negativeLabel.setText("Negatywne");
        negativeLabel.setMaximumSize(new java.awt.Dimension(60, 14));
        negativeLabel.setMinimumSize(new java.awt.Dimension(60, 14));
        negativeLabel.setPreferredSize(new java.awt.Dimension(60, 14));

        negativeValue.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        negativeValue.setText("100");
        negativeValue.setToolTipText("Liczba transakcji negatywnych");
        negativeValue.setMaximumSize(new java.awt.Dimension(60, 14));
        negativeValue.setMinimumSize(new java.awt.Dimension(60, 14));
        negativeValue.setPreferredSize(new java.awt.Dimension(60, 14));

        efficiencyLabel.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        efficiencyLabel.setText("Skutecznoœæ");
        efficiencyLabel.setMaximumSize(new java.awt.Dimension(69, 14));
        efficiencyLabel.setMinimumSize(new java.awt.Dimension(69, 14));
        efficiencyLabel.setPreferredSize(new java.awt.Dimension(69, 14));

        efficiencyValue.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        efficiencyValue.setText("100");
        efficiencyValue.setToolTipText("Skutecznoœæ - stosunek transakcji pozytywnych do sumy wszystkich transakcji");
        efficiencyValue.setMaximumSize(new java.awt.Dimension(69, 14));
        efficiencyValue.setMinimumSize(new java.awt.Dimension(69, 14));
        efficiencyValue.setPreferredSize(new java.awt.Dimension(69, 14));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        rateOfProfitLabel.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        rateOfProfitLabel.setText("Stopa zwrotu");
        rateOfProfitLabel.setMaximumSize(new java.awt.Dimension(60, 14));
        rateOfProfitLabel.setMinimumSize(new java.awt.Dimension(60, 14));
        rateOfProfitLabel.setPreferredSize(new java.awt.Dimension(60, 14));

        rateOfProfitValue.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        rateOfProfitValue.setText("24 000,00");
        rateOfProfitValue.setToolTipText("Stopa zwrotu za ca³y okres testu np. 5 lat");
        rateOfProfitValue.setMaximumSize(new java.awt.Dimension(60, 14));
        rateOfProfitValue.setMinimumSize(new java.awt.Dimension(60, 14));
        rateOfProfitValue.setPreferredSize(new java.awt.Dimension(60, 14));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        buttonAction.setText("Wybierz");
        buttonAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nameCloseAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(nameOpenAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(properiesOpenAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(propertiesCloseAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(positiveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(negativeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(efficiencyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(positiveValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(negativeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(efficiencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(rateOfProfitValue, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                    .addComponent(rateOfProfitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonAction)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameOpenAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(properiesOpenAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(positiveLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(negativeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(efficiencyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameCloseAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(propertiesCloseAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(positiveValue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(negativeValue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(efficiencyValue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(rateOfProfitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(rateOfProfitValue, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(buttonAction)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }
    
    private void loadData(SkandiaSimulationResult result, BigDecimal startCapital){
    	nameOpenAlgorithm.setText(result.getOpenAlgorithm().getName());
    	nameCloseAlgorithm.setText(result.getCloseAlgorithm().getName());
    	properiesOpenAlgorithm.setText(result.getOpenAlgorithm().getParameter());
    	propertiesCloseAlgorithm.setText(result.getCloseAlgorithm().getParameter());
    	
    	positiveValue.setText(Integer.toString(result.getPositiveTransactionAmount()));
    	negativeValue.setText(Integer.toString(result.getNegaticeTransactionAmount()));
    	efficiencyValue.setText(Double.toString(result.getEfficiencyInPercentage().doubleValue())+"%");
    	
    	rateOfProfitValue.setText(Double.toString(result.getRateOfProfit(startCapital).doubleValue())+"%");
    	
    	if(choosen){
    		buttonAction.setText("Dezaktywuj");
    	}else {
    		buttonAction.setText("Wybierz");
    	}
    }
    private void buttonActionActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if(choosen){
        	buttonAction.setText("Wybierz");
        	this.choosen=false;
        	chooseManager.unselect(this.algorithmResult);
        } else {
        	buttonAction.setText("Dezaktywuj");
        	this.choosen=true;
        	chooseManager.select(this.algorithmResult);
        	
        }
    }

	/**{@link AlgorithmReportPanel#choosen}*/
	public boolean isChoosen() {
		return choosen;
	}   
	
	public void setChoise(boolean selected){
		this.choosen=selected;
		if(selected){
			buttonAction.setText("Dezaktywuj");
		}else{
			buttonAction.setText("Wybierz");
		}
	}

    
}
