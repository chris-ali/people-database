package com.chrisali.peopledatabase.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.chrisali.peopledatabase.model.persondatabase.Age;
import com.chrisali.peopledatabase.model.persondatabase.Database;
import com.chrisali.peopledatabase.model.persondatabase.Employment;
import com.chrisali.peopledatabase.model.persondatabase.Gender;
import com.chrisali.peopledatabase.model.persondatabase.Person;
import com.chrisali.peopledatabase.view.formpanel.FormEvent;

public class Controller {
	Database db = new Database();
	
	public void configure(int port, String user, String password) throws Exception {
		db.configure(port, user, password);
	}
	
	public void connect() throws Exception {
		db.connect();
	}
	
	public void disconnect() {
		db.disconnect();	
	}
	
	public void saveToDatabase() throws SQLException {
		db.saveToDatabase();
	}
	
	public void loadFromDatabase() throws SQLException {
		db.loadFromDatabase();
	}
	
	public void removePerson(int index) {
		db.removePerson(index);
	}

	public void addPerson(FormEvent ev) {
		Age age;
		Employment employment;
		Gender gender;

		switch (ev.getAgeCat()) {
		case 0:
			age = Age.UNDER18;
			break;
		case 1:
			age = Age.FROM18TO65;
			break;
		case 2:
			age = Age.OVER65;
			break;
		default:
			age = Age.FROM18TO65;
			break;
		}

		switch (ev.getEmpCat()) {
		case 0:
			employment = Employment.EMPLOYED;
			break;
		case 1:
			employment = Employment.SELFEMPLOYED;
			break;
		case 2:
			employment = Employment.UNEMPLOYED;
			break;
		case 3:
			employment = Employment.STUDENT;
			break;
		case 4:
			employment = Employment.RETIRED;
			break;
		default:
			employment = Employment.UNEMPLOYED;
			break;
		}

		switch (ev.getGenderCat()) {
		case 0:
			gender = Gender.MALE;
			break;
		case 1:
			gender = Gender.FEMALE;
			break;
		default:
			gender = Gender.MALE;
			break;
		}
		
		db.addPerson(new Person(ev.getName(), ev.getOccupation(), age, employment, gender,
					 ev.isUsCitizen(), ev.getTaxId()));
	}
	
	public List<Person> getPeople() {
		return db.getPeople();
	}
	
	public void saveToFile(File file) throws IOException {
		db.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException {
		db.loadFromFile(file);
	}
}
