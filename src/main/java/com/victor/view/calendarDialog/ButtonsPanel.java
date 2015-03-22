package com.victor.view.calendarDialog;

import java.awt.Dimension;
import java.awt.GridLayout;
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
public class ButtonsPanel extends JPanel {

	private CalendarPanel owner;
	private CalendarButton selectedButton;

	/**
	 * {@link ButtonsPanel}
	 */
	public ButtonsPanel(CalendarPanel panel) {
		super();
		this.owner = panel;
		initComponents();
	}

	public void updateButtonsPanel() {
		setVisible(false);
		for (int i = 0; i < getComponentCount(); i++)
			remove(getComponent(i));
		preparedButtons();
		setVisible(true);
	}

	private void initComponents() {
		setLayout(new GridLayout(6, 7, 10, 10));
		setOpaque(false);
		setPreferredSize(new Dimension(490, 270));
		preparedButtons();
		setVisible(true);
	}

	private void preparedButtons() {
		GregorianCalendar temp = owner.getSelectedDate();
		int selectedDay = temp.get(GregorianCalendar.DAY_OF_MONTH);
		GregorianCalendar date = new GregorianCalendar(
				temp.get(GregorianCalendar.YEAR),
				temp.get(GregorianCalendar.MONTH), 1);
		int dayOfWeek = getDayOfWeek(date);
		if (dayOfWeek == 0)
			dayOfWeek = 7;
		int daysTo42 = 43 - dayOfWeek;
		// niewidzialne przyciski do ustawienia wlasciwych od odpowiedniego dnia
		for (int i = 1; i < dayOfWeek; i++) {
			JButton button = new CalendarButton(date, null);
			button.setVisible(false);
			add(button);
		}
		int daysInMonth = date.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		dayOfWeek--;
		// widoczne przyciski reprezentujace dni miesiaca
		for (int i = 0; i < daysInMonth; i++) {
			GregorianCalendar buttonDate = new GregorianCalendar(
					date.get(GregorianCalendar.YEAR),
					date.get(GregorianCalendar.MONTH),
					date.get(GregorianCalendar.DAY_OF_MONTH));
			final CalendarButton button = new CalendarButton(buttonDate,
					prepareNumberUrl(i + 1, dayOfWeek++));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectDay(button);
				}
			});
			if (i + 1 == selectedDay) {
				button.doClick();
			}
			button.setVisible(true);
			add(button);
			date.add(GregorianCalendar.DAY_OF_MONTH, 1);
			if (dayOfWeek > 6)
				dayOfWeek = 0;
		}
		daysTo42 -= daysInMonth;
		// niewidoczne przyciski uzupelniaja do konca GridLayout
		for (int i = 0; i < daysTo42; i++) {
			JButton button = new JButton();
			button.setVisible(false);
			add(button);
		}
	}

	private int getDayOfWeek(GregorianCalendar date) {
		int day = date.get(GregorianCalendar.DAY_OF_WEEK);
		day--;
		if (day < 0)
			day = 6;
		return day;
	}

	private String prepareNumberUrl(int dayNumber, int dayOfWeek) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("images/dialog/");
		int weekDay = dayOfWeek % 7;
		if (weekDay < 5) {
			buffer.append("week/");
		} else if (weekDay == 5) {
			buffer.append("saturday/");
		} else {
			buffer.append("sunday/");
		}
		buffer.append(dayNumber).append(".png");
		return buffer.toString();
	}

	private void selectDay(CalendarButton button) {
		if (selectedButton != null) {
			selectedButton.desactivate();
		}
		selectedButton = button;
		selectedButton.activate();
		owner.setSelectedDate(button.getDate());
	}
}
