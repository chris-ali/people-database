package com.chrisali.peopledatabase.view.persontable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.chrisali.peopledatabase.model.persondatabase.Age;

public class AgeCategoryEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = -542084431664030111L;

	private JComboBox<Age> comboBox;

	public AgeCategoryEditor() {
		comboBox = new JComboBox<>(Age.values());
	}

	@Override
	public Object getCellEditorValue() {
		return comboBox.getSelectedItem();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		comboBox.setSelectedItem(value);
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {fireEditingStopped();}
		});
		
		return comboBox;
	}

}
