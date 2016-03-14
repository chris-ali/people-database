package com.chrisali.peopledatabase.view.formpanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class FormPanel extends JPanel {
	private JLabel nameLabel;
	private JTextField nameField;
	
	private JLabel occupationLabel;
	private JTextField occupationField;
	
	private JList<AgeCategory> ageList;
	
	private JComboBox<EmploymentCategory> empCombo;
	
	private JCheckBox citizenCheck;
	private JLabel citizenLabel;
	
	private JLabel taxLabel;
	private JTextField taxField;
	
	private JRadioButton maleButton;
	private JRadioButton femaleButton;
	private ButtonGroup genderGroup;
	
	private JButton okButton;
	
	private FormListener formListener;
	
	public FormPanel() {

		setLayout(new GridBagLayout());
		
		GridBagConstraints gc = new GridBagConstraints();
		
		//---------------------- First Row ----------------------------------
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		nameLabel = new JLabel("Name: ");
		add(nameLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		nameField = new JTextField(10);
		add(nameField, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		occupationLabel = new JLabel("Occupation: ");
		add(occupationLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		occupationField = new JTextField(10);
		add(occupationField, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		gc.weighty = 0.1;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		occupationLabel = new JLabel("Age: ");
		add(occupationLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
		ageModel.addElement(new AgeCategory(0, "Under 18"));
		ageModel.addElement(new AgeCategory(1, "18 to 65"));
		ageModel.addElement(new AgeCategory(2, "Over 65"));
		ageList = new JList<AgeCategory>();
		ageList.setModel(ageModel);
		ageList.setSelectedIndex(1);
		ageList.setBorder(BorderFactory.createEtchedBorder());
		ageList.setPreferredSize(new Dimension(110, 70));
		add(ageList, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		
		occupationLabel = new JLabel("Employment: ");
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(occupationLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		DefaultComboBoxModel<EmploymentCategory> empModel = new DefaultComboBoxModel<>();
		empModel.addElement(new EmploymentCategory(0, "Employed"));
		empModel.addElement(new EmploymentCategory(1, "Self-Employed"));
		empModel.addElement(new EmploymentCategory(2, "Unemployed"));
		empModel.addElement(new EmploymentCategory(3, "Student"));
		empModel.addElement(new EmploymentCategory(4, "Retired"));
		empCombo = new JComboBox<>();
		empCombo.setModel(empModel);
		empCombo.setSelectedIndex(0);	
		add(empCombo, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		citizenLabel = new JLabel("US Citizen: ");
		add(citizenLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		citizenCheck = new JCheckBox();
		citizenCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				taxLabel.setEnabled(citizenCheck.isSelected());
				taxField.setEnabled(citizenCheck.isSelected());
				taxField.setText("");
			}
		});
		add(citizenCheck, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		taxLabel = new JLabel("Tax ID: ");
		taxLabel.setEnabled(false);
		add(taxLabel, gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		taxField = new JTextField(10);
		taxField.setEnabled(false);
		add(taxField, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		gc.weighty = 0.05;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Gender:"), gc);
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		maleButton = new JRadioButton("Male");
		maleButton.setSelected(true);
		maleButton.setActionCommand("Male");
		add(maleButton, gc);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		gc.weighty = 0.2;
		
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		femaleButton = new JRadioButton("Female");
		femaleButton.setActionCommand("Female");
		add(femaleButton, gc);
		
		//====================== Button Group ================================
		
		genderGroup = new ButtonGroup();
		genderGroup.add(maleButton);
		genderGroup.add(femaleButton);
		
		//----------------------- Next Row ----------------------------------
		gc.gridy++;
		gc.weighty = 2;
		
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (formListener != null) {
					formListener.formEventOccured(new FormEvent(this, 
																nameField.getText(), 
																occupationField.getText(),
																ageList.getSelectedValue(),
																(EmploymentCategory)empCombo.getSelectedItem(),
																citizenCheck.isSelected(),
																taxField.getText(),
																new GenderCategory(genderGroup.getSelection().getActionCommand()) ));
					nameField.setText("");
					occupationField.setText("");
					taxField.setText("");
				}
			}
		});
		add(okButton, gc);
		
		//========================== Borders ===============================
		
		int margins = 5;
		Border innerBorder = BorderFactory.createTitledBorder("Add People");
		Border outerBorder = BorderFactory.createEmptyBorder(margins,margins,margins,margins);
		
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		//===================== Window Settings =============================
		
		Dimension dim = getPreferredSize();
		dim.width = 250;
		setPreferredSize(dim);
		setMinimumSize(dim);
	}
	
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}
}

class GenderCategory {
	private int id;
	private String text;
	
	public GenderCategory(String text) {
		this.text = text;
		if (text.toLowerCase().contentEquals("male"))
			this.id = 0;
		else
			this.id = 1;
	}
	
	public int getId() {return id;}

	@Override
	public String toString() {return text;}
}

class EmploymentCategory {
	private int id;
	private String text;
	
	public EmploymentCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}
	
	public int getId() {return id;}

	@Override
	public String toString() {return text;}
}

class AgeCategory {
	private int id;
	private String text;
	
	public AgeCategory(int id, String text) {
		this.id = id;
		this.text = text;
	}
	public int getId() {return id;}

	@Override
	public String toString() {return text;}
}
