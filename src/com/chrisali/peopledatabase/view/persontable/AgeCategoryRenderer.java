package com.chrisali.peopledatabase.view.persontable;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.chrisali.peopledatabase.model.persondatabase.Age;

public class AgeCategoryRenderer implements TableCellRenderer {

	private JComboBox<Age> comboBox;
	
	public AgeCategoryRenderer() {
		comboBox = new JComboBox<>(Age.values());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		comboBox.setSelectedItem(value);
		
		return comboBox;
	}

}
