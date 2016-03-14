package com.chrisali.peopledatabase.model.persondatabase;

public enum Age {
	UNDER18     ("Under 18"),
	FROM18TO65  ("18 to 65"),
	OVER65		("Over 65");
	
	private String text;
	
	private Age(String text) {this.text = text;}

	@Override
	public String toString() {return text;}
}
