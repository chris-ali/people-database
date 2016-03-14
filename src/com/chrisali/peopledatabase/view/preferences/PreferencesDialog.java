package com.chrisali.peopledatabase.view.preferences;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class PreferencesDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private JButton okButton;
	private JButton cancelButton;
	private JSpinner portSpinner;
	private SpinnerNumberModel spinnerModel;
	private JTextField userField;
	private JPasswordField passField;
	
	private PrefsListener prefsListener;
	
	public PreferencesDialog(JFrame parent) {
		super(parent, "Preferences", false);
		
		//----------------- OK Button ----------------------------
		
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(prefsListener != null) {
					prefsListener.preferencesSet(userField.getText(), new String(passField.getPassword()), (int)portSpinner.getValue());
				}
				
				setVisible(false);
			}
		});
		
		//------------------- Cancel Button ------------------------
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		//----------------- User Name Field ------------------------
		
		userField = new JTextField(10);
		
		//----------------- Password Field ------------------------
		
		passField = new JPasswordField(10);
		
		//-------------------- Port Spinner ------------------------
		
		spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
		portSpinner = new JSpinner(spinnerModel);
		
		//================== Window Settings ======================
		
		setPreferencesLayout();
		setLocationRelativeTo(parent);
		setSize(300, 220);
	}
	
	public void setPreferencesLayout() {
		
		//-------------------- Panels ---------------------------
		
		JPanel controlsPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		controlsPanel.setLayout(new GridBagLayout());
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		setLayout(new BorderLayout());
		add(controlsPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
		//------------------ Borders and Insets -----------------
		
		int margins = 5;
		Border emptyBorder = BorderFactory.createEmptyBorder(margins ,margins, margins, margins);
		Border titleBorder = BorderFactory.createTitledBorder("Database Connection");
		
		controlsPanel.setBorder(BorderFactory.createCompoundBorder(emptyBorder, titleBorder));
		
		Insets rightInsets = new Insets(0, 0, 0, 5);
		Insets noInsets = new Insets(0, 0, 0, 0);
		
		// ------------- GridBag Items -------------------------- 
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridy = 0;
		
		//------------ Next Row ------------------
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightInsets;
		controlsPanel.add(new JLabel("User Name: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noInsets;
		controlsPanel.add(userField, gc);
		
		//------------ Next Row ------------------
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightInsets;
		controlsPanel.add(new JLabel("Password: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noInsets;
		controlsPanel.add(passField, gc);
		
		//------------ Next Row ------------------
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.EAST;
		gc.insets = rightInsets;
		controlsPanel.add(new JLabel("Port: "), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = noInsets;
		controlsPanel.add(portSpinner, gc);
		
		//------------ Buttons ------------------
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		okButton.setPreferredSize(cancelButton.getPreferredSize());
	}
	
	public void setDefaults(String user, String password, int port) {
		userField.setText(user);
		passField.setText(password);
		portSpinner.setValue(port);
	}

	public void setPrefsListener(PrefsListener prefsListener) {
		this.prefsListener = prefsListener;
	}

}
