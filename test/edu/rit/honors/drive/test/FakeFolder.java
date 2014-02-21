package edu.rit.honors.drive.test;

import java.util.ArrayList;
import java.util.Collection;

import edu.rit.honors.drive.File;
import edu.rit.honors.drive.Folder;
import edu.rit.honors.drive.User;

public class FakeFolder implements edu.rit.honors.drive.Folder {
	
	private String id;
	private User owner;
	private String name;
	private Folder parent;
	private Collection<File> children;

	public FakeFolder(String id, User owner, String name, Folder parent, Collection<File> children){
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.parent = parent;
		this.children = children;
	}
	
	public FakeFolder addChild(File child){
		if(null == this.children){
			this.children = new ArrayList<File>();
		}
		this.children.add(child);
		return this;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public User getOwner() {
		return owner;
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
		return "application/vnd.google-apps.folder";
	}

	@Override
	public Folder getParent() {
		return this.parent;
	}

	@Override
	public boolean hasChildren() {
		return (null != this.children);
	}

	@Override
	public Collection<File> getChildren() {
		return this.children;
	}

	@Override
	public Collection<File> getChildrenRecursive() {
		ArrayList<File> list = new ArrayList<File>(this.children);
		for(File f : this.children){
			if((f instanceof Folder) && ((Folder)f).hasChildren()){
				list.addAll(((Folder)f).getChildrenRecursive());
			}
		}
		return list;
	}

}
