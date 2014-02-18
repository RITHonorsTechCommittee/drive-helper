package edu.rit.honors.drive;

/**
 * A user.
 * @author ry60003333
 */
public class User
{
    /**
     * The ID of the user.
     */
    private final int id;
    
    /**
     * The name of the user.
     */
    private final String name;
    
    /**
     * The email address of the user.
     */
    private final String email;
    
    /**
     * Creates a new User.
     * @param id The ID of the user.
     * @param name The name of the user.
     * @param email The email address of the user.
     */
    public User(
            int id,
            String name, 
            String email
    )
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Get the ID of the user.
     * @return The ID.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Get the name of the user.
     * @return The name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the email address of the user.
     * @return The email address.
     */
    public String getEmail()
    {
        return email;
    }
}
