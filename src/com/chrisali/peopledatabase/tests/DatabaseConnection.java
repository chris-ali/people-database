package com.chrisali.peopledatabase.tests;

import java.sql.SQLException;

import com.chrisali.peopledatabase.model.persondatabase.Age;
import com.chrisali.peopledatabase.model.persondatabase.Database;
import com.chrisali.peopledatabase.model.persondatabase.Employment;
import com.chrisali.peopledatabase.model.persondatabase.Gender;
import com.chrisali.peopledatabase.model.persondatabase.Person;

public class DatabaseConnection {

	public static void main(String[] args) {
		Database db = new Database();
		try {
			db.connect();
			System.out.println("Connected successfully to database");
		} catch (Exception e) {e.printStackTrace();}
		
		db.addPerson(new Person("Joe", "builder", Age.FROM18TO65, Employment.EMPLOYED, Gender.MALE, true, "777"));
		db.addPerson(new Person("Sue", "artist", Age.OVER65, Employment.RETIRED, Gender.FEMALE, false, ""));
		db.addPerson(new Person("John", "software", Age.FROM18TO65, Employment.SELFEMPLOYED, Gender.MALE, false, null));
		//db.addPerson(new Person("Chris", "engineer", Age.FROM18TO65, Employment.UNEMPLOYED, Gender.MALE, true, "326"));
		//db.addPerson(new Person("Matt", "", Age.FROM18TO65, Employment.STUDENT, Gender.MALE, true, "325"));
				
		try {db.saveToDatabase();
		} catch (SQLException e) {e.printStackTrace();}
		
		try {db.loadFromDatabase();
		} catch (SQLException e) {e.printStackTrace();}
		
		db.disconnect();
		System.out.println("Disconnected from database");
	}

}
