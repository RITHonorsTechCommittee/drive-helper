package edu.rit.honors.drive;

import java.util.ArrayList;
import java.util.Collection;

import com.google.api.services.drive.model.File;

/**
 * An interface for finding files in the RIT Honors Google Drive.
 * 
 * @author Veronica Wharton
 */
public interface FileHelper {

	/**
	 * Find the immediate children of a given File.
	 * @param file The File whose children should be found
	 * @return a list of the immediate children of file. if file has no
	 * no children, return null
	 */
	public Collection<File> getChildren(File file);

	/**
	 * Find the parent of a given File.
	 * @param file The File whose parent should be found
	 * @return a reference to the File's parent. if file has no parent, 
	 * return null
	 */
	public File getParent(File file); 

	/**
	 * Find the siblings of a given File.
	 * @param file The File whose siblings should be found
	 * @return a list of the immediate children of file. if file has no
	 * no children, return null
	 */
	public ArrayList<File> getSiblings(File file);

	/**
	 * Find the tree of files beneath this file
	 * @param file The File whose tree should be found
	 * @return a tree structure of all the files with 'file' as their ancestor
	 */
	public File getTree(File file); 

	/**
	 * Find all users who have read-write permission for a given file
	 * @param file The file from which all the users should be retrieved
	 * @return a list of all of 'file's' users
	 */
	public ArrayList<User> getUsers(File file);

	/**
	 * Give a user read-write permission to a given File
	 * @param @param user The User to add
	 * @param file The File from which 'user' should be added
	 * @return true if the user was successfully added to the file; 
	 * else false
	 */
	public boolean addUser(User user, File file);

	/**
	 * Remove a user's read-write permission to a given File
	 * @param user The User to remove
	 * @param file The File from which 'user' should be removed
	 * @return true if the user was successfully removed from the file; 
	 * else false
	 */
	public boolean removeUser(User user, File file);

	/**
	 * Determine whether a user has read-write permission for a given File
	 * @param user The User to check
	 * @param file The File to check against
	 * @return true if the user was successfully added to the file; else false
	 */
	public boolean hasUser(User user, File file);
	
	/**
	 * Checks if a "File" is actually a file (as opposed to a folder)
	 * 
	 * @param f The file to check
	 * @return True, if it is a file.  False, if it is a folder
	 */
	public boolean isFile(File f);
	
	/**
	 * Checks if a "File" is actually a folder
	 * 
	 * @param f The file to check
	 * @return True, if it is a folder. False, if it is a file
	 */
	public boolean isDirectory(File f);
	
	
	/**
	 * Check if a directory has any children.
	 * 
	 * @param f The file to check
	 * @return True, if the file is a directory that has children.
	 * 			False, if the file is not a directory, or has no children
	 */
	public boolean hasChildren(File f);
	
}