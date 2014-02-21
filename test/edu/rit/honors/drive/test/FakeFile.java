package edu.rit.honors.drive.test;

import java.util.Collection;

import edu.rit.honors.drive.File;
import edu.rit.honors.drive.Folder;
import edu.rit.honors.drive.User;

public class FakeFile implements File {
	
	private String id;
	private User owner;
	private String name;
	private Folder parent;
	
	public FakeFile(String id, User owner, String name, Folder parent){
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.parent = parent;
	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public User getOwner() {
		return this.owner;
	}

	@Override
	public Collection<User> getEditors() {
		return null;
	}

	@Override
	public boolean changeOwner(User newUser) {
		this.owner = newUser;
		return true;
	}

	@Override
	public com.google.api.services.drive.model.File getGoogleFile() {
		return null;
	}

	@Override
	public String downloadUrl(String mimeType) {
		return "https://example.com/"+id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getMIME() {
		return "text/plain";
	}

	@Override
	public Folder getParent() {
		return this.parent;
	}

}
