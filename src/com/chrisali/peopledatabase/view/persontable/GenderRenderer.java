package com.chrisali.peopledatabase.view.persontable;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.chrisali.peopledatabase.model.persondatabase.Gender;

public class GenderRenderer implements TableCellRenderer {

	private JComboBox<Gender> comboBox;
	
	public GenderRenderer() {
		comboBox = new JComboBox<>(Gender.values());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		comboBox.setSelectedItem(value);
		
		return comboBox;
	}

}
