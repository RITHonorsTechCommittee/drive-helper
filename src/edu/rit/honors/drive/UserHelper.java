package edu.rit.honors.drive;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.User;
import com.google.api.services.drive.model.Permission;


public interface UserHelper {

	public int OWNER = 0, WRITER = 1, READER = 2;	

	/**
	 * Get the User with the given email address
	 * @return the User
	 */
	public User getUser(String email);

	/**
	 * Get the permissions of the given user for the given file
	 * @return permission
	 */
	public Permission getPermission(User u, File f);

	/**
	 * @return the role of the user
	 */
	public int getRole(User u, File f);
}
