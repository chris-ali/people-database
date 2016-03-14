package com.chrisali.peopledatabase.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PersonFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {
		if(file.isDirectory())
			return true;
		else if (Utilities.getFileExtension(file.getName()).equals("per"))
			return true;
		else if ((Utilities.getFileExtension(file.getName()) == null))
			return false;
		else
			return false;
	}

	@Override
	public String getDescription() {
		return "Person database files (.per)";
	}
}
