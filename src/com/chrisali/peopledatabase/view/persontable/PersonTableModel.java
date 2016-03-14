package com.chrisali.peopledatabase.view.persontable;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.chrisali.peopledatabase.model.persondatabase.Age;
import com.chrisali.peopledatabase.model.persondatabase.Employment;
import com.chrisali.peopledatabase.model.persondatabase.Gender;
import com.chrisali.peopledatabase.model.persondatabase.Person;

public class PersonTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Person> db;
	private String[] columnNames = {"ID", "Name", "Gender", "Age", "Employment Status", "Occupation", "US Citizen", "Tax ID"};
	
	public void setData(List<Person> db) {
		this.db = db;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 1: 
			return true;
		case 2: 
			return true;
		case 3: 
			return true;
		case 4: 
			return true;
		case 5: 
			return true;
		case 6:
			return true;
		case 7: 
			return true;
		default: 
			return false;
		}
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
		Person person = db.get(rowIndex);
		
		switch (columnIndex) {
		case 1:
			person.setName((String)aValue);
			break;
		case 2:
			person.setGender((Gender)aValue);
			break;
		case 3:
			person.setAge((Age)aValue);
			break;
		case 4:
			person.setEmp((Employment)aValue);
			break;
		case 5:
			person.setOccupation((String)aValue);
			break;
		case 6:
			person.setUsCitizen((Boolean)aValue);
			break;
		case 7:
			person.setTaxId((String)aValue);
			break;
		default: 
			return;
		}
	}
	
	

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return Integer.class;
		case 1:
			return String.class;
		case 2:
			return Gender.class;
		case 3:
			return Age.class;
		case 4:
			return Employment.class;
		case 5:
			return String.class;
		case 6:
			return Boolean.class;
		case 7:
			return String.class;
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Person selectedRow = db.get(row);
		
		switch (col) {
		case 0:
			return selectedRow.getId();
		case 1:
			return selectedRow.getName();
		case 2:
			return selectedRow.getGender();
		case 3:
			return selectedRow.getAge();
		case 4:
			return selectedRow.getEmp();
		case 5:
			return selectedRow.getOccupation();
		case 6:
			return selectedRow.isUsCitizen();
		case 7:
			return selectedRow.getTaxId();
		}
		return null;
	}
}
