package edu.rit.honors.drive;

/**
 * A user.
 * @author ry60003333
 */
public interface User
{

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
    
}
