package com.chrisali.peopledatabase.view.persontable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.chrisali.peopledatabase.model.persondatabase.Age;
import com.chrisali.peopledatabase.model.persondatabase.Employment;
import com.chrisali.peopledatabase.model.persondatabase.Gender;
import com.chrisali.peopledatabase.model.persondatabase.Person;

public class TablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private PersonTableModel tableModel;
	private JPopupMenu popup;
	private PersonTableListener personTableListener;
	
	public TablePanel() {
		tableModel = new PersonTableModel();
		table = new JTable(tableModel);
		table.setDefaultRenderer(Employment.class, new EmploymentCategoryRenderer());
		table.setDefaultRenderer(Age.class, new AgeCategoryRenderer());
		table.setDefaultRenderer(Gender.class, new GenderRenderer());
		table.setDefaultEditor(Employment.class, new EmploymentCategoryEditor());
		table.setDefaultEditor(Age.class, new AgeCategoryEditor());
		table.setDefaultEditor(Gender.class, new GenderEditor());
		table.setRowHeight(25);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.getSelectionModel().setSelectionInterval(row, row);
				
				if (e.getButton() == MouseEvent.BUTTON3)
					popup.show(table, e.getX(), e.getY());
			}
			
		});
		
		JMenuItem removeItem = new JMenuItem("Delete Row");
		popup = new JPopupMenu();
		popup.add(removeItem);
		removeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				
				if (personTableListener != null) {
					personTableListener.rowDeleted(row);
					tableModel.fireTableRowsDeleted(row, row);
				}
			}
		});

		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void setData(List<Person> db) {
		tableModel.setData(db);
	}
	
	public void refresh() {
		tableModel.fireTableDataChanged();
	}
	
	public void setPersonTableListener(PersonTableListener listener) {
		this.personTableListener = listener;
	}
}
