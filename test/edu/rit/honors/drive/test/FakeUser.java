package edu.rit.honors.drive.test;

import edu.rit.honors.drive.User;

public class FakeUser implements User {
	
	private String id;
	private String name;
	private String email;

	public FakeUser(String id, String uname, String email){
		this.id = id;
		this.name = uname;
		this.email = email;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public int getRole() {
		return User.OWNER;
	}

}
