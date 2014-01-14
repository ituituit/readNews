package com.cheesemobile.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BitmapUtil {
	public static void drawText() {
		JFrame frame = new JFrame("test");
		JLabel text = new JLabel("Complete");
		text.setBackground(Color.white);
		text.setForeground(Color.black);
		text.setFont(new java.awt.Font("Dialog", 0, 12)); 
		text.getFont();
		frame.add(text); 
		frame.pack();
		frame.getContentPane().setBackground(new Color(255, 255, 255));
		frame.setVisible(true);
		BufferedImage img = getImage(text);
		try {
			ImageIO.write(img, "bmp", new File("C:/temp/img.bmp"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private static BufferedImage getImage(JComponent c) {
		Rectangle region = c.getBounds();
		BufferedImage image = new BufferedImage(region.width, region.height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		g2d.translate(-region.x, -region.y);
		g2d.setColor(c.getBackground());
		g2d.fillRect(region.x, region.y, region.width, region.height);
		c.paint(g2d);
		g2d.dispose();
		return image;
	}

}
