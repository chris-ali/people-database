package com.chrisali.peopledatabase.view.formpanel;

import java.util.EventObject;

public class FormEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	private String name;
	private String occupation;
	private int ageCat;
	private int empCat;
	private boolean usCitizen;
	private String taxId;
	private int genderCat;
	
	public FormEvent(Object arg0) {
		super(arg0);
	}
	
	public FormEvent(Object source, String name, String occupation, AgeCategory ageCat, EmploymentCategory empCat, boolean usCitizen, String taxId, GenderCategory genderCat) {
		super(source);
		this.name = name;
		this.occupation = occupation;
		this.ageCat = ageCat.getId();
		this.empCat = empCat.getId();
		this.genderCat = genderCat.getId();
		this.usCitizen = usCitizen;
		this.taxId = taxId;
	}

	public String getName() {
		return name;
	}

	public String getOccupation() {
		return occupation;
	}

	public int getAgeCat() {
		return ageCat;
	}

	public int getEmpCat() {
		return empCat;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public String getTaxId() {
		return taxId;
	}

	public int getGenderCat() {
		return genderCat;
	}
}
