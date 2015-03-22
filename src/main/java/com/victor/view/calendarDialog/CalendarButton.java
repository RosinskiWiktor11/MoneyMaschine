package com.victor.view.calendarDialog;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class CalendarButton extends JButton {
	/** sciezka do obrazka dla przycisku wlaczonego */
	private String iconUrlActive = "images/dialog/button/button1.png";
	/** sciezka do obrazka dla przycisku wylaczonego */
	private String iconUrlDesactive = "images/dialog/button/button2.png";
	/** sciezka do obrazka dla liczby reprezentujacej przycisk */
	private String numberUrl;

	/** tlo dla przycisku wylaczonego */
	private ImageIcon releasedIcon;
	/** tlo dla przycisku wlaczonego */
	private ImageIcon pressedIcon;
	/** tlo dla przycisku, gdy myszka nad przyciskiem */
	@SuppressWarnings("unused")
	private ImageIcon mouseOverIcon;
	/** czy przycisk jest wcisniety */
	boolean pressed;

	private GregorianCalendar date;

	/**
	 * {@link CalendarButton}
	 */
	public CalendarButton(GregorianCalendar date, String urlNumber) {
		this.numberUrl = urlNumber;
		this.date = date;
		initComponent();
		setIcon(releasedIcon);
	}
	
	public CalendarButton(String urlText){
		super();
		System.out.println("tylko icon");
		this.numberUrl = urlText;
		System.out.println(numberUrl);
		initComponent2();
		setIcon(releasedIcon);
	}

	public CalendarButton(String urlDesactiveIcon, String urlActiveIcon) {
		super();
		this.iconUrlActive = urlActiveIcon;
		this.iconUrlDesactive = urlDesactiveIcon;
		initComponent2();
		setIcon(releasedIcon);
		setVisible(true);
	}

	private void initComponent() {
		BufferedImage image;
		BufferedImage numberImage = null;
		pressed = false;
		@SuppressWarnings("unused")
		Toolkit zestaw = Toolkit.getDefaultToolkit();
		try {

			image = ImageIO.read(new File(iconUrlDesactive));
			if (numberUrl != null) {
				numberImage = ImageIO.read(new File(numberUrl));
				releasedIcon = new ImageIcon(joinImages(image, numberImage));
			}

			image = ImageIO.read(new File(iconUrlActive));
			if (numberUrl != null) {
				pressedIcon = new ImageIcon(joinImages(image, numberImage));
			}
			setIcon(releasedIcon);
			Dimension size = new Dimension(64, 47);
			setMaximumSize(size);
			setMinimumSize(size);
			setPreferredSize(size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentAreaFilled(false);
		setBorder(null);
	}

	@SuppressWarnings("unused")
	private void initComponent2() {
		BufferedImage image;
		pressed = false;
		Toolkit zestaw = Toolkit.getDefaultToolkit();
		
		try {
			System.out.println(numberUrl);
			if(numberUrl!=null){
				BufferedImage numberImage = ImageIO.read(new File(numberUrl));
				image = ImageIO.read(new File(iconUrlDesactive));
				releasedIcon = new ImageIcon(joinImages(image, numberImage));

				image = ImageIO.read(new File(iconUrlActive));
				pressedIcon = new ImageIcon(joinImages(image, numberImage));
			}else{
				image = ImageIO.read(new File(iconUrlDesactive));
				releasedIcon = new ImageIcon(image);

				image = ImageIO.read(new File(iconUrlActive));
				pressedIcon = new ImageIcon(image);
			}
			
			

			setIcon(releasedIcon);
			Dimension size = new Dimension(image.getWidth(), image.getHeight());
			setMaximumSize(size);
			setMinimumSize(size);
			setPreferredSize(size);

		} catch (IOException e) {
			e.printStackTrace();
		}
		addMouseListener(new ButtonListener());
		setContentAreaFilled(false);
		setBorder(null);
	}

	private BufferedImage joinImages(BufferedImage buttonImage,
			BufferedImage numberImage) {
		BufferedImage combined = new BufferedImage(buttonImage.getWidth(),
				buttonImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = combined.getGraphics();
		g.drawImage(buttonImage, 0, 0, null);
		g.drawImage(numberImage, 0, 0, null);
		return combined;
	}

	@SuppressWarnings("unused")
	private String getDateAsString(GregorianCalendar date) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(date.get(GregorianCalendar.DAY_OF_MONTH)).append(".");
		buffer.append(date.get(GregorianCalendar.MONTH) + 1).append(".");
		buffer.append(date.get(GregorianCalendar.YEAR));
		return buffer.toString();
	}

	public void activate() {
		setIcon(pressedIcon);
	}

	public void desactivate() {
		setIcon(releasedIcon);
	}

	class ButtonListener implements MouseListener {

		public void mouseEntered(MouseEvent e) {
			if (pressed)
				setIcon(pressedIcon);
			else {
				setIcon(releasedIcon);
				setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			}
		}

		public void mousePressed(MouseEvent e) {
			pressed = true;
			setIcon(pressedIcon);
		}

		public void mouseReleased(MouseEvent e) {
			pressed = false;
			if (getIcon() == pressedIcon)
				setIcon(releasedIcon);
		}

		public void mouseExited(MouseEvent e) {
			setIcon(releasedIcon);
			setBorder(null);
		}

		public void mouseClicked(MouseEvent e) {
			new Thread() {
				public void run() {

				}
			}.start();
		}
	}

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	/** {@link CalendarButton#date} */
	public GregorianCalendar getDate() {
		return date;
	}

	public void addMouseListener() {
		addMouseListener(new ButtonListener());
	}

}
