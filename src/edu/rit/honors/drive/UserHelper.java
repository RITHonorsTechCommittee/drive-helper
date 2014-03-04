import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.User;
package edu.rit.honors.drive;
public interface UserHelper {
	
	//Returns the User with the given email address
	public User getUser(String email);

	//Return the permissions of the given user
	public Object getPermission(User u);

	//Preconditions: User and file are valid
	//Postcondition: Returns constants
	public int getRole(User u, File f);
}
