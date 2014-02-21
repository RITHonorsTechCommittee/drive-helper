package edu.rit.honors.drive;

public interface UserHelper {
	
	//Returns the User with the given email address
	public User getUser(String email);

	//Return the permissions of the given user
	public Object getPermission(User u);
}
