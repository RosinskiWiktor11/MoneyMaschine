package com.victor.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.victor.control.ViewController;

/**
 * @author Wiktor<br/>
 *         klasa bazowa dla wszystkich paneli
 * 
 *
 */
@SuppressWarnings("serial")
public abstract class ViewPanel<T extends ViewController<? extends ViewPanel<T>>>
		extends JPanel {

	protected T controller;
	protected String name = "main";
	protected String backgroundPath = "images/main_backgrond.jpg";
	protected BufferedImage background;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ViewPanel() {
		openImage();
		Type type = getClass().getGenericSuperclass();
		ParameterizedType paramType = (ParameterizedType) type;
		Class controllerType = (Class<T>) paramType.getActualTypeArguments()[0];
		try {
			controller = (T) controllerType.getConstructor(this.getClass())
					.newInstance(this);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
	}

	private void openImage() {
		try {
			background = ImageIO.read(new File(backgroundPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc) @see java.awt.Component#getName() */
	@Override
	public String getName() {
		return name;
	}
}