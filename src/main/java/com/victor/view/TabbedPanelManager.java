package com.victor.view;

import javax.swing.JTabbedPane;

import com.victor.gpw.view.GpwPanel;
import com.victor.skandia.view.SkandiaPanel;

/**
 * @author Wiktor<br/>
 *         laduje refleksyjnie zakladki do aplikacji ktore dziedzicza po
 *         SingleTabbedPanel
 *
 */
@SuppressWarnings("serial")
public class TabbedPanelManager extends JTabbedPane {

	@SuppressWarnings("unused")
	private Window window;

	public TabbedPanelManager(Window window) {
		initComponents();
		setVisible(true);
	}

	protected void initComponents() {
		SkandiaPanel skandiaPanel;
		GpwPanel gpwPanel;
		
		skandiaPanel = new SkandiaPanel();
		addTab(skandiaPanel.getName(), skandiaPanel);
		
		gpwPanel=new GpwPanel();
		addTab(gpwPanel.getName(), gpwPanel);
	}

}
