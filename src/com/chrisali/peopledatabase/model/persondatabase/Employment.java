package com.chrisali.peopledatabase.model.persondatabase;

public enum Employment {
	EMPLOYED      ("Employed"),
	SELFEMPLOYED  ("Self Employed"),
	UNEMPLOYED    ("Unemployed"),
	STUDENT		  ("Student"),
	RETIRED		  ("Retired");
	
	private String text;
	
	private Employment(String text) {this.text = text;}

	@Override
	public String toString() {return text;}
}
