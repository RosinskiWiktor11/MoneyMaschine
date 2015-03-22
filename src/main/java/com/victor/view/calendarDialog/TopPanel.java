package com.victor.view.calendarDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class TopPanel extends JPanel {
	CalendarPanel owner;
	JPanel selectedDataPanel;

	public TopPanel(CalendarPanel panel) {
		this.owner = panel;
		initComponents();
	}

	private void initComponents() {
		setOpaque(false);
		setLayout(new BorderLayout());
		preparePreviousButtonsPanel();
		prepareSelectedDatePanel(owner.getStringDate());
		prepareNextButtonsPanel();
		setBounds(0, 0, 560, 93);
		setPreferredSize(new Dimension(503, 93));

	}

	private void preparePreviousButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton button = new CalendarButton("images/dialog/button/lewo1.png",
				"images/dialog/button/lewo2.png");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousYear();
			}
		});
		panel.add(button);
		button = new CalendarButton("images/dialog/button/lewo1.png",
				"images/dialog/button/lewo2.png");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousMonth();
			}
		});
		panel.add(button);
		this.add(panel, "West");
	}

	private void prepareSelectedDatePanel(String date) {
		selectedDataPanel = new DatePanel(date);
		selectedDataPanel.setSize(260, 80);
		this.add(selectedDataPanel, "Center");
	}

	private void prepareNextButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton button = new CalendarButton("images/dialog/button/prawo1.png",
				"images/dialog/button/prawo2.png");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextMonth();
			}
		});
		panel.add(button);
		button = new CalendarButton("images/dialog/button/prawo1.png",
				"images/dialog/button/prawo2.png");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextYear();
			}
		});
		panel.add(button);
		this.add(panel, "East");
	}

	public void updateDateLabel(String date) {
		selectedDataPanel.setVisible(false);
		selectedDataPanel = null;
		selectedDataPanel = new DatePanel(date);
		selectedDataPanel.setSize(260, 80);
		this.add(selectedDataPanel, "Center");
		this.setVisible(true);
	}

	private void previousMonth() {
		GregorianCalendar date = owner.getSelectedDate();
		date.add(GregorianCalendar.MONTH, -1);
		owner.setMonthOrYear(date);
	}

	private void nextMonth() {
		GregorianCalendar date = owner.getSelectedDate();
		date.add(GregorianCalendar.MONTH, 1);
		owner.setMonthOrYear(date);
	}

	private void previousYear() {
		GregorianCalendar date = owner.getSelectedDate();
		date.add(GregorianCalendar.YEAR, -1);
		owner.setMonthOrYear(date);
	}

	private void nextYear() {
		GregorianCalendar date = owner.getSelectedDate();
		date.add(GregorianCalendar.YEAR, 1);
		owner.setMonthOrYear(date);
	}

}
