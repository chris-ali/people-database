package com.chrisali.peopledatabase.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Utilities {
	public static String getFileExtension(String fileName) {
		int periodLocation = fileName.lastIndexOf(".");
		
		if (periodLocation == -1)
			return "";
		else if (periodLocation == fileName.length()-1)
			return "";
		else 
			return fileName.substring(periodLocation+1, fileName.length());
	}
	
	public static ImageIcon createIcon(String path) {
		URL url = System.class.getResource(path);
		
		if (url == null) {
			System.err.println("Unable to load image: " + path);
			return null;
		}

		return new ImageIcon(url);
	}
	
	public static Font createFont(String path) {
		URL url = System.class.getResource(path);
		
		if (url == null) {
			System.err.println("Unable to load font: " + path);
			return null;
		}
		
		Font font = null;
		try {font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());} 
		catch (FontFormatException e) {System.err.println("Incorrect font type for: " + path);} 
		catch (IOException e) {System.err.println("Unable to load font: " + path);}

		return font;
	}
}
