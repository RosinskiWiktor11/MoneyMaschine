package com.victor.skandia.view.portfolio;

import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Wiktor Rosinski
 *
 */
public class ChartFrame extends JFrame{

	public ChartFrame(String title, boolean maximalize, JPanel chart){
		super(title);
		initFrame();
		if(maximalize)
			maximalizeWindow();
		else
			setBounds(300, 400, 800, 600);
		add(chart);
		
	}
	
	private void initFrame(){
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
//		addWindowListener(new WindowAdapter() {
//			  @Override
//			  public void windowClosing(WindowEvent we){ 
//				  setVisible(false);
//				  dispose();
//			  }
//		});
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void maximalizeWindow() {
		Rectangle ekran = this.getGraphicsConfiguration().getBounds();
		Rectangle okno = new Rectangle(ekran);

		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(
				this.getGraphicsConfiguration());
		okno.height = ekran.height - (insets.bottom + insets.top);
		okno.width = ekran.width - (insets.left + insets.right);
		setBounds(okno);

	}
}
