package com.chrisali.peopledatabase.view.messagelist;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.chrisali.peopledatabase.model.messagedatabase.Message;
import com.chrisali.peopledatabase.view.Utilities;

public class MessageListRenderer implements ListCellRenderer<Message> {

	private JPanel panel;
	private JLabel label;
	private Color selectedColor;
	private Color defaultColor;
	
	public MessageListRenderer() {
		label = new JLabel();
		label.setIcon(Utilities.createIcon("/com/chrisali/peopledatabase/images/Information24.gif"));
		label.setFont(Utilities.createFont("/com/chrisali/peopledatabase/fonts/CrimewaveBB.ttf").deriveFont(Font.PLAIN, 18));
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		
		selectedColor = new Color(210, 210, 255);
		defaultColor = Color.WHITE;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Message> list, Message message, int index, boolean isSelected,
			boolean cellHasFocus) {
		label.setText(message.getTitle());
		panel.setBackground(cellHasFocus ? selectedColor : defaultColor);

		return panel;
	}
}
