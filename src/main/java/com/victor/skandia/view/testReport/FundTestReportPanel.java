package com.victor.skandia.view.testReport;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import com.victor.control.ViewController;
import com.victor.gpw.model.SimulationResult;
import com.victor.skandia.bisnesInterface.ChooseInterface;
import com.victor.skandia.control.SkandiaController;
import com.victor.skandia.model.SkandiaSimulationResult;
import com.victor.skandia.model.TestResult;
import com.victor.utils.ColorUtils;


/**
 *
 * @author Wiktor Rosinski
 */
@SuppressWarnings("serial")
public class FundTestReportPanel extends javax.swing.JPanel implements ChooseInterface {
	
	

    private javax.swing.JLabel labelChoosen;
    private javax.swing.JLabel labelRest;
    private javax.swing.JPanel panelChoosen;
    private javax.swing.JPanel panelRest;
    private javax.swing.JScrollPane scrollPanel;   
    private javax.swing.JPanel restPanelForList;
    
    private ViewController controller;
    private TestResult result;
    private List<AlgorithmReportPanel> algorithmReportPanelList;
    
    /**
     * Creates new form FundTestRaportPanel
     */
    private FundTestReportPanel(String fundName) {
        initComponents(fundName);
        algorithmReportPanelList=new ArrayList<AlgorithmReportPanel>();
    }
    
    public FundTestReportPanel(ViewController controler, TestResult result){
    	this(result.getInvestmentFund().getShortName());
    	this.controller=controler;
    	this.result=result;
    	loadDataResult(result);
    }
                          
    private void initComponents(String fundName) {

        labelChoosen = new javax.swing.JLabel();
        panelChoosen = new javax.swing.JPanel();
        labelRest = new javax.swing.JLabel();
        panelRest = new javax.swing.JPanel();
        scrollPanel = new javax.swing.JScrollPane();
        restPanelForList = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, fundName, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 1, 14))); // NOI18N

        labelChoosen.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        labelChoosen.setText("Wybrany:");

        panelChoosen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelChoosen.setPreferredSize(new java.awt.Dimension(641, 77));

        javax.swing.GroupLayout panelChoosenLayout = new javax.swing.GroupLayout(panelChoosen);
        panelChoosen.setLayout(panelChoosenLayout);
        panelChoosenLayout.setHorizontalGroup(
            panelChoosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelChoosenLayout.setVerticalGroup(
            panelChoosenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 73, Short.MAX_VALUE)
        );

        labelRest.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        labelRest.setText("Pozoste³e:");

        panelRest.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelRest.setPreferredSize(new java.awt.Dimension(641, 144));

        scrollPanel.setBorder(null);

        javax.swing.GroupLayout restPanelForListLayout = new javax.swing.GroupLayout(restPanelForList);
        restPanelForList.setLayout(restPanelForListLayout);
        restPanelForListLayout.setHorizontalGroup(
            restPanelForListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
        );
        restPanelForListLayout.setVerticalGroup(
            restPanelForListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 430, Short.MAX_VALUE)
        );

        scrollPanel.setViewportView(restPanelForList);
        scrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout panelRestLayout = new javax.swing.GroupLayout(panelRest);
        panelRest.setLayout(panelRestLayout);
        panelRestLayout.setHorizontalGroup(
            panelRestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanel)
        );
        panelRestLayout.setVerticalGroup(
            panelRestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRest, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                    .addComponent(panelChoosen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 727, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelChoosen, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelRest, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 629, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelChoosen, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelChoosen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelRest, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRest, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );
    }
    
    private void loadDataResult(TestResult result){
    	panelChoosen.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
    	restPanelForList.setLayout(new java.awt.GridLayout(0, 1, 10, 0));

    	algorithmReportPanelList.add(new AlgorithmReportPanel(this, (SkandiaSimulationResult) result.getChoosenResult(), result.getBeningCapital(), true));
    	for(SimulationResult simulationResult: result.getRestResltList()){
    		algorithmReportPanelList.add(new AlgorithmReportPanel(this, (SkandiaSimulationResult) simulationResult, result.getBeningCapital(), false));
    	}
    	loadPanels();
    }
    
    private void loadPanels(){
    	panelChoosen.removeAll();
    	restPanelForList.removeAll();
    	int i=1;
    	for(AlgorithmReportPanel panel: algorithmReportPanelList){
    		if(panel.isChoosen()){
    			panelChoosen.add(panel);
    		}else {
    			if(i%2==1)
    				panel.setBackground(ColorUtils.getTableColor1());
    			else
    				panel.setBackground(ColorUtils.getTableColor2());
    			restPanelForList.add(panel);
    			i++;
    		}
    	}
    	
    	
    	setVisible(false);
    	setVisible(true);
    }

	/* (non-Javadoc) @see com.victor.skandia.bisnesInterface.ChooseInterface#select(java.lang.Object) */
	@Override
	public void select(Object o) {
		if(o instanceof SkandiaSimulationResult){
			SkandiaSimulationResult selectedResult=(SkandiaSimulationResult) o;
			((SkandiaController) controller).changeChoosenAlgorithm(selectedResult, result.getInvestmentFund());
			AlgorithmReportPanel selectedPanel=(AlgorithmReportPanel) panelChoosen.getComponent(0);
			selectedPanel.setChoise(false);
			
			loadPanels();
		}
		
	}

	/* (non-Javadoc) @see com.victor.skandia.bisnesInterface.ChooseInterface#unselect(java.lang.Object) */
	@Override
	public void unselect(Object o) {
		if(o instanceof SkandiaSimulationResult){
			controller.desactivateAlgorithm(result.getInvestmentFund());
			loadPanels();
		}
	}
	
}