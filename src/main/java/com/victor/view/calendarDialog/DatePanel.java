package com.victor.view.calendarDialog;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author Wiktor
 *
 */
@SuppressWarnings("serial")
public class DatePanel extends JPanel {
	BufferedImage image;

	public DatePanel(String date) {
		super();
		try {
			prepareDateImage(date);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		setVisible(true);
	}

	private void prepareDateImage(String date) throws IOException {
		BufferedImage icon = new BufferedImage(260, 80,
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage tempImage;
		StringBuffer buffer;
		for (int i = 0; i < 10; i++) {
			buffer = new StringBuffer();
			buffer.append("images/dialog/week/");
			if (date.substring(i, i + 1).equals("."))
				buffer.append("dash.png");
			else
				buffer.append(date.substring(i, i + 1)).append(".png");

			tempImage = ImageIO.read(new File(buffer.toString()));
			Graphics g = icon.getGraphics();
			g.drawImage(tempImage, i * 20, 0, null);
		}
		image = icon;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);

	}
}
