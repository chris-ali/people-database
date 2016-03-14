package com.chrisali.peopledatabase.model.persondatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Database {
	private ArrayList<Person> people;
	
	private static boolean ssl = false;
	private int port;
	private String hostName = "localhost";
	private String databaseName = "swingtest";
	private String user;
	private String password;
	private Connection connection;
	
	public Database() {
		this.people = new ArrayList<>(); 
	}
	
	public void configure(int port, String user, String password) throws Exception {
		this.port = port;
		this.user = user;
		this.password = password;
				
		if (connection != null) {
			disconnect();
			connect();
		}
			
	}
	
	public void connect() throws Exception {
		if (connection != null)
			return;
		
		try {Class.forName("com.mysql.jdbc.Driver");} 
		catch (ClassNotFoundException e) {throw new Exception("Driver not found");}
		
		try { 
			String url = String.format("jdbc:mysql://%s:%d/%s?autoReconnect=true&useSSL=%b", hostName, port, databaseName, ssl);
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {System.err.println("Could not connect to SQL database!");}
	}
	
	public void disconnect() {
		if (connection != null) {
			try {connection.close();} 
			catch (SQLException e) {System.err.println("Could not close SQL connection!");}
		}
	}
	
	public void saveToDatabase() throws SQLException {
		String checkSql = "select count(*) as count from people where id=?";
		PreparedStatement checkStatement = connection.prepareStatement(checkSql);
		
		String insertSql = "insert into people (id, name, age, gender, employment_status, occupation, us_citizen, tax_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = connection.prepareStatement(insertSql);
		
		String updateSql = "update people set name=?, age=?, gender=?, employment_status=?, occupation=?, us_citizen=?, tax_id=? where id =?";
		PreparedStatement updateStatement = connection.prepareStatement(updateSql);
		
		for (Person person : people) {
			checkStatement.setInt(1, person.getId());
			
			ResultSet checkResult = checkStatement.executeQuery();
			checkResult.next();
			
			if (checkResult.getInt(1) == 0) {
				int col = 1;
				
				insertStatement.setInt(col++, person.getId());
				insertStatement.setString(col++, person.getName());
				insertStatement.setString(col++, person.getAge().name());
				insertStatement.setString(col++, person.getGender().name());
				insertStatement.setString(col++, person.getEmp().name());
				insertStatement.setString(col++, person.getOccupation());
				insertStatement.setBoolean(col++, person.isUsCitizen());
				insertStatement.setString(col++, person.getTaxId());
				
				insertStatement.executeUpdate();
				
			} else {
				int col = 1;
				
				updateStatement.setString(col++, person.getName());
				updateStatement.setString(col++, person.getAge().name());
				updateStatement.setString(col++, person.getGender().name());
				updateStatement.setString(col++, person.getEmp().name());
				updateStatement.setString(col++, person.getOccupation());
				updateStatement.setBoolean(col++, person.isUsCitizen());
				updateStatement.setString(col++, person.getTaxId());
				updateStatement.setInt(col++, person.getId());
				
				updateStatement.executeUpdate();
			}
		}
		
		checkStatement.close();
		insertStatement.close();
		updateStatement.close();
	}
	
	public void loadFromDatabase() throws SQLException {
		people.clear();
		
		String selectSql = "select id, name, age, gender, employment_status, occupation, us_citizen, tax_id from people order by name";
		Statement selectStatement = connection.createStatement();
		
		ResultSet results = selectStatement.executeQuery(selectSql);
		
		while (results.next()) {
			int id = results.getInt("id");
			String name = results.getString("name");
			
			Age age = Age.FROM18TO65;
			try {age = Age.valueOf(results.getString("age"));
			} catch (IllegalArgumentException e) {
				System.err.println("Error parsing age from id " + id + " in the database!");
				age = Age.FROM18TO65;
			}
			
			Gender gender = Gender.MALE;
			try {gender = Gender.valueOf(results.getString("gender"));
			} catch (IllegalArgumentException e) {
				System.err.println("Error parsing gender from id " + id + " in the database!");
				gender = Gender.MALE;
			}
			
			Employment emp = Employment.EMPLOYED;
			try {emp = Employment.valueOf(results.getString("employment_status"));
			} catch (IllegalArgumentException e) {
				System.err.println("Error parsing employment status from id " + id + " in the database!");
				emp = Employment.EMPLOYED;
			}
			
			String occupation = results.getString("occupation");
			boolean usCitizen = results.getBoolean("us_citizen");
			String taxId = results.getString("tax_id");
			
			people.add(new Person(id, name, occupation, age, emp, gender, usCitizen, taxId));
		}
		
		selectStatement.close();
		results.close();
	}
	
	public void addPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(int index) {
		people.remove(index);
	}
	
	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}
	
	public void saveToFile(File file) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		
		Person[] personArray = people.toArray(new Person[people.size()]);
		
		oos.writeObject(personArray);
		
		oos.close();
	}
	
	public void loadFromFile(File file) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		
		Person[] personArray = null;
		try {personArray = (Person[])ois.readObject();
		} catch (ClassNotFoundException e) {e.printStackTrace();}
		
		people.clear();
		people.addAll(Arrays.asList(personArray));
		
		ois.close();
	}
}
