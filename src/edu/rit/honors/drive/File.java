package edu.rit.honors.drive;

import com.google.api.services.drive.model.File;

/**
 * An (as of now incomplete) Implementation of the file interface for use in 
 * the Google Drive Helper program.
 * 
 * TODO:  DO WE NEED THIS?
 * https://developers.google.com/resources/api-libraries/documentation/drive/v2/java/latest/com/google/api/services/drive/model/File.html
 * 
 * Also, https://developers.google.com/resources/api-libraries/documentation/drive/v2/java/latest/com/google/api/services/drive/model/User.html
 * We will need to look at the entire Model package and see how much we want to wrap ourselves.
 */
public interface File {
	
	/**
	 * Gets the primary owner of a the given file object.
	 */
	public User getOwner();
	
	/**
	 * Gets a list of all the users who have full edit permissions
	 */
	public Collection<User> getEditors();
	
	/**
	 * Attempts to change the ownership of a file in Google Drive.  If the 
	 * operation is successful, the method returns true.  If the operation 
	 * fails, it will return false.
	 * 
	 * Note that ownership can only be transferred to other users from the 
	 * within the same organization (which is to say @rit.edu)
	 * 
	 * @param newUser The desired new owner.
	 * @return True, if the operation was successful
	 */
	public boolean changeOwner(User newUser);
	
	/**
	 * Gets the underlying Google-Provided file representation.
	 * 
	 * TODO: Do we actually want to provide this, or should we completely wrap 
	 * it and only expose what we deem "necessary" at the cost or redundency? 
	 * 
	 * This class also exposes a copy class which would be VERY helpful for 
	 * hostile takeovers.  All we need to do is copy, delete the original, and 
	 * rename as appropriate
	 */
	public com.google.api.services.drive.model.File getGoogleFile();
	
	
	/**
	 * Fetches the download URL for a given file resource.  This can be used 
	 * later for the hostile takeover, or for archival purposes
	 * 
	 * @see https://developers.google.com/drive/web/manage-downloads
	 */
	public String downloadUrl(String mimeType);
	
	/**
	 * Gets the name of the file
	 */
	public String getName();
	
	/**
	 * Gets the MIME Type of a file
	 */
	public String getMIME();
	
	/**
	 * Gets the parent of a file
	 */
	public Folder getParent();
	
	
	

}
