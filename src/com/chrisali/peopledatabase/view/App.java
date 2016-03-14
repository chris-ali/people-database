package com.chrisali.peopledatabase.view;

import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {runApp();}
		});
	}
	
	private static void runApp() {
		new MainFrame();
	}

}
