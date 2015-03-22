package com.victor.skandia.view;

import javax.swing.JPanel;

import com.victor.skandia.control.SkandiaController;
import com.victor.skandia.view.testReport.TestReportPanel;
import com.victor.view.ViewPanel;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class SkandiaPanel extends ViewPanel<SkandiaController> {

	/** pnael z panelami acji zwiazanymi ze Skandia */
	private JPanel panelMenu;
	/** panel do prezentacji raportow */
	private JPanel panelConsole;
	/**
	 * Creates new form SkandiaView
	 */
	public SkandiaPanel() {
		name = "Skandia";
		initComponents();
	}


	private void initComponents() {

		panelMenu = new javax.swing.JPanel();
		panelConsole = new javax.swing.JPanel();
		
		panelMenu.setOpaque(false);
		panelConsole.setOpaque(false);

		javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(
				panelMenu);
		panelMenu.setLayout(panelMenuLayout);
		panelMenuLayout.setHorizontalGroup(panelMenuLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 504,
				Short.MAX_VALUE));
		panelMenuLayout.setVerticalGroup(panelMenuLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0,
				Short.MAX_VALUE));

		javax.swing.GroupLayout panelConsoleLayout = new javax.swing.GroupLayout(
				panelConsole);
		panelConsole.setLayout(panelConsoleLayout);
		panelConsoleLayout.setHorizontalGroup(panelConsoleLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 191, Short.MAX_VALUE));
		panelConsoleLayout.setVerticalGroup(panelConsoleLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 367, Short.MAX_VALUE));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelMenu,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panelConsole,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														panelConsole,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														panelMenu,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));
		loadPaneMenu();
	}

	private void loadPaneMenu() {
		panelMenu.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
		panelConsole.setLayout(new java.awt.GridLayout(0, 1, 10, 0));
		ImportPanel importPanel = new ImportPanel(controller);
		AlgorithmPanel testPanel=new AlgorithmPanel(controller, panelConsole); 
		PortfolioPanel portfolioPanel=new PortfolioPanel(controller, panelConsole);
		panelMenu.add(importPanel);
		panelMenu.add(testPanel);
		panelMenu.add(portfolioPanel);
	}

	// *****************************************************************
	// ***************** GETTERS & SETTERS *****************************
	// *****************************************************************

	/** {@link SkandiaPanel#panelMenu} */
	public JPanel getPanelMenu() {
		return panelMenu;
	}

	/** {@link SkandiaPanel#panelConsole} */
	public JPanel getPanelConsole() {
		return panelConsole;
	}

	/** {@link SkandiaPanel#panelMenu} */
	public void setPanelMenu(JPanel panelMenu) {
		this.panelMenu = panelMenu;
	}

	/** {@link SkandiaPanel#panelConsole} */
	public void setPanelConsole(JPanel panelConsole) {
		this.panelConsole = panelConsole;
	}

}
