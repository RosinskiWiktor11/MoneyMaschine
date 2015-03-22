package com.victor.view.calendarDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class CalendarPickerDialog extends JDialog {

	/** referencja pola tekstowego, do ktorego zostanie wstawiona data */
	private JTextField textField;

	/** Container zawierajacy zawierajacy wszystkie kontrolki */
	private JPanel panel;

	@SuppressWarnings("unused")
	public static void showCalendar(JTextField textField) {
		CalendarPickerDialog calendarDialog = new CalendarPickerDialog(
				textField);
	}

	/**
	 * {@link CalendarPickerDialog}
	 */
	private CalendarPickerDialog(JTextField textField) {
		super();
		this.textField = textField;
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setBounds(200, 200, 510, 470);
		setResizable(false);
		panel = new CalendarPanel(this, textField);
		getContentPane().add(panel);

	}

	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

		Action action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		};
		InputMap inputMap = rootPane
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		return rootPane;
	}

	
}
