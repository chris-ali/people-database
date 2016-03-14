package com.chrisali.peopledatabase.model.persondatabase;

public enum Gender {
	MALE   ("Male"),
	FEMALE ("Female");
	
	private String text;
	
	private Gender(String text) {this.text = text;}

	@Override
	public String toString() {return text;}
}
