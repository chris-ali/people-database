package com.chrisali.peopledatabase.view.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.chrisali.peopledatabase.view.Utilities;

@SuppressWarnings("serial")
public class Toolbar extends JToolBar implements ActionListener {
	private JButton saveButton;
	private JButton refreshButton;
	
	private ToolbarListener toolbarListener;
	
	public Toolbar() {
		
		//------------------ Save Button -----------------------------------
		
		saveButton = new JButton();
		saveButton.setIcon(Utilities.createIcon("/com/chrisali/peopledatabase/images/Save16.gif"));
		saveButton.setToolTipText("Save");
		saveButton.addActionListener(this);
		add(saveButton);
		
		//----------------- Refresh Button ----------------------------------
		
		refreshButton = new JButton();
		refreshButton.setIcon(Utilities.createIcon("/com/chrisali/peopledatabase/images/Refresh16.gif"));
		refreshButton.setToolTipText("Refresh");
		refreshButton.addActionListener(this);
		add(refreshButton);
		
		//======================= Object Settings ================================
		
		setFloatable(false);
	}
	
	public void setToolbarListener(ToolbarListener stringListener) {
		this.toolbarListener = stringListener;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		
		if (clicked == saveButton && toolbarListener != null)
			toolbarListener.saveEventOccurred();
		else if (clicked == refreshButton && toolbarListener != null)
			toolbarListener.refreshEventOccurred();
	}
}
