package com.chrisali.peopledatabase.model.persondatabase;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -8405504630959228358L;

	private static int count = 1;

	private int id;
	private String name;
	private String occupation;
	private Age age;
	private Employment emp;
	private Gender gender;
	private boolean usCitizen;
	private String taxId;

	public Person(String name, String occupation, Age age, Employment emp, Gender gender, boolean usCitizen,
			String taxId) {
		this.id = count;
		this.name = name;
		this.occupation = occupation;
		this.age = age;
		this.emp = emp;
		this.gender = gender;
		this.usCitizen = usCitizen;
		this.taxId = taxId;

		count++;
	}
	
	public Person (int id, String name, String occupation, Age age, Employment emp, Gender gender, boolean usCitizen,
			String taxId) {
		this(name, occupation, age, emp, gender, usCitizen, taxId);
		
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Age getAge() {
		return age;
	}

	public void setAge(Age age) {
		this.age = age;
	}

	public Employment getEmp() {
		return emp;
	}

	public void setEmp(Employment emp) {
		this.emp = emp;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(boolean usCitizen) {
		this.usCitizen = usCitizen;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	@Override
	public String toString() {
		return id + ": " + name;
	}
}
