package edu.rit.honors.drive;

/**
 * A user.
 * @author ry60003333
 */
public interface User
{
	/**
	 * The constants for the User roles.
	 */
	public int OWNER = 0, WRITER = 1, READER = 2;

    /**
     * Get the ID of the user.
     * @return The ID.
     */
    public String getId();

    /**
     * Get the name of the user.
     * @return The name.
     */
    public String getName();

    /**
     * Get the email address of the user.
     * @return The email address.
     */
    public String getEmail();

    /**
     * Get the role of the user.
     * @return The role, defined as an integer.
     */
    public int getRole();
    
}
