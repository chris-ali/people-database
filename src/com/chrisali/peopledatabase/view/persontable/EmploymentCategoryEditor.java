package com.chrisali.peopledatabase.view.persontable;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.chrisali.peopledatabase.model.persondatabase.Employment;

public class EmploymentCategoryEditor extends AbstractCellEditor implements TableCellEditor {

	private static final long serialVersionUID = -542084431664030111L;

	private JComboBox<Employment> comboBox;

	public EmploymentCategoryEditor() {
		comboBox = new JComboBox<>(Employment.values());
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
