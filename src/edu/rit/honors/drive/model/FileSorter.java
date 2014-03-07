package edu.rit.honors.drive.model;

import java.util.Comparator;

import com.google.api.services.drive.model.File;

public class FileSorter implements Comparator<File> {

	@Override
	public int compare(File arg0, File arg1) {
		try{
			return arg0.getTitle().compareTo(arg1.getTitle());
		}catch(NullPointerException e){
			return arg0.getId().compareTo(arg1.getId());
		}
	}

}
