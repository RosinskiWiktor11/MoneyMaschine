package com.victor.view;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.stereotype.Controller;

/**
 * @author Wiktor<br/>
 *         glowne okno dla aplikacji. Zawiera panel zakladek
 * @see {@link TabbedPanelManager}
 */
@SuppressWarnings("serial")
@Controller
public class Window extends JFrame {
	private TabbedPanelManager tabbedPanel;
	@SuppressWarnings("unused")
	private boolean zalogowany;

	/**
	 * {@link Window}
	 */
	public Window() {
		super("Przewodnik po skarbcu");
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
		initComponents();
	}

	private void initComponents() {

		zmaksymalizujOkno();
		
		addWindowListener(new WindowAdapter() {

			  @Override
			  public void windowClosing(WindowEvent we)
			  { 
			    String ObjButtons[] = {"Tak","Nie"};
			    int PromptResult = JOptionPane.showOptionDialog(null, 
			        "Czy na pewno chcesz opuœciæ skarbiec?", "Zamykanie", 
			        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
			        ObjButtons,ObjButtons[1]);
			    if(PromptResult==0)
			    {
			      System.exit(0);          
			    }else{
			    	return;
			    }
			  }
			});
		
		// TODO odkomentowac i zaimplementowac to po ludzku
		tabbedPanel = new TabbedPanelManager(this);
		add(tabbedPanel);
		
		setVisible(true);
	}

	private void zmaksymalizujOkno() {
		Rectangle ekran = this.getGraphicsConfiguration().getBounds();
		Rectangle okno = new Rectangle(ekran);

		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(
				this.getGraphicsConfiguration());
		okno.height = ekran.height - (insets.bottom + insets.top);
		okno.width = ekran.width - (insets.left + insets.right);
		setBounds(okno);

	}

}
