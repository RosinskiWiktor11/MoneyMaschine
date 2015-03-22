package com.victor.view.calendarDialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.orm.jdo.JdoAccessor;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class CalendarPanel extends JPanel {
	private String backgroundPath = "images/dialog/background.png";
	private BufferedImage image;
	private GregorianCalendar selectedDate;
	private String stringDate;
	private TopPanel topPanel;
	private ButtonsPanel buttonsPanel;
	private ConfirmPanel confirmPanel;
	private JTextField textField;
	private JDialog onwer;

	/**
	 * {@link CalendarPanel}
	 */
	public CalendarPanel(JDialog owner, JTextField textField) {
		super();
		this.onwer=owner;
		this.textField = textField;
		selectedDate = new GregorianCalendar();
		convertToString(selectedDate);
		loadBackground();
		initComponents();
		setVisible(true);
	}

	private void initComponents() {
		topPanel = new TopPanel(this);
		add(topPanel);
		buttonsPanel = new ButtonsPanel(this);
		add(buttonsPanel);
		confirmPanel=new ConfirmPanel(onwer);
		add(confirmPanel);
		
	}

	private void loadBackground() {
		try {
			image = ImageIO.read(new File(backgroundPath));
		} catch (IOException e) {
			setBackground(Color.blue);

		}
	}

	private void convertToString(GregorianCalendar date) {
		StringBuffer buffer = new StringBuffer();
		int i;
		i = date.get(GregorianCalendar.DAY_OF_MONTH);
		if (i < 10) {
			buffer.append("0");
		}
		buffer.append(i).append(".");

		i = date.get(GregorianCalendar.MONTH) + 1;
		if (i < 10) {
			buffer.append("0");
		}
		buffer.append(i).append(".");
		i = date.get(GregorianCalendar.YEAR);
		buffer.append(i);
		stringDate = buffer.toString();
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);

	}

	/** {@link CalendarPanel#stringDate} */
	public String getStringDate() {
		return stringDate;
	}

	/** {@link CalendarPanel#selectedDate} */
	public GregorianCalendar getSelectedDate() {
		return selectedDate;
	}

	/** {@link CalendarPanel#selectedDate} */
	public void setSelectedDate(GregorianCalendar selectedDate) {
		this.selectedDate = selectedDate;
		convertToString(selectedDate);
		topPanel.updateDateLabel(stringDate);
		textField.setText(stringDate);
	}

	public void setMonthOrYear(GregorianCalendar date) {
		this.selectedDate = date;
		convertToString(selectedDate);
		topPanel.updateDateLabel(stringDate);
		buttonsPanel.setVisible(false);
		buttonsPanel = null;
		remove(confirmPanel);
		buttonsPanel = new ButtonsPanel(this);
		add(buttonsPanel);
		add(confirmPanel);
		setVisible(false);
		setVisible(true);
		textField.setText(stringDate);
	}

}
