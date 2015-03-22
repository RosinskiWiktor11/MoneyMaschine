package com.victor.skandia.view.testReport;


/**
 *
 * @author Wiktor Rosinski<br/>
 * 	panel zawierajacy liste wynikow dla poszczegolnych funduszy
 */
@SuppressWarnings("serial")
public class TestReportPanel extends javax.swing.JPanel {

    private javax.swing.JLabel header;
    private javax.swing.JPanel panelResultList;
    private javax.swing.JScrollPane scrollPaneResultPanel;
    
    
    /**
     * Creates new form TestRaportPanel
     */
    public TestReportPanel() {
        initComponents();
    }
                        
    private void initComponents() {

        header = new javax.swing.JLabel();
        scrollPaneResultPanel = new javax.swing.JScrollPane();
        panelResultList = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        header.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        header.setText("Wyniki:");

        javax.swing.GroupLayout panelResultListLayout = new javax.swing.GroupLayout(panelResultList);
        panelResultList.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
        panelResultListLayout.setHorizontalGroup(
            panelResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 594, Short.MAX_VALUE)
        );
        panelResultListLayout.setVerticalGroup(
            panelResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
        );

        scrollPaneResultPanel.setViewportView(panelResultList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(scrollPaneResultPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPaneResultPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
        );
    }

	/**{@link TestReportPanel#panelResultList}*/
	public javax.swing.JPanel getPanelResultList() {
		return panelResultList;
	}                   

}
