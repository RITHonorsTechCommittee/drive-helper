package edu.rit.honors.drive;

import java.util.Collection;

public class GoogleDriveFile implements File {
	
	private String id;
	private com.google.api.services.drive.model.File underlying;
	
	public GoogleDriveFile(com.google.api.services.drive.model.File f){
		underlying = f;
		id = f.getId();
	}
	
	public GoogleDriveFile(String fileId){
		//TODO: FileHelper should be able to get this for me...
		underlying = null;
		id = fileId;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public User getOwner() {
		// TODO Auto-generated method stub
		return new GoogleDriveUser(underlying.getOwners().get(0));
	}

	@Override
	public Collection<User> getEditors() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changeOwner(User newUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public com.google.api.services.drive.model.File getGoogleFile() {
		return underlying;
	}

	@Override
	public String downloadUrl(String mimeType) {
		return underlying.getDownloadUrl();
	}

	@Override
	public String getName() {
		return underlying.getTitle();
	}

	@Override
	public String getMIME() {
		return underlying.getMimeType();
	}

	@Override
	public Folder getParent() {
		// TODO Auto-generated method stub
		return null;
	}

}
