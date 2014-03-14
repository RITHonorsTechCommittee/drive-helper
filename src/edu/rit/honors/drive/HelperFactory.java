package edu.rit.honors.drive;

public class HelperFactory {

	public static FileHelper getFileHelper(){
		return edu.rit.honors.drive.test.FakeDrive.getInstance();
	}
	
	public static UserHelper getUserHelper(){
		return null;
	}
	
}
