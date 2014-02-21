package edu.rit.honors.drive;

public class GoogleDriveUser implements User {
	
	private com.google.api.services.drive.model.User underlying;
	
	public GoogleDriveUser(com.google.api.services.drive.model.User u){
		underlying = u;
	}

	@Override
	public String getId() {
		return underlying.getPermissionId();
	}

	@Override
	public String getName() {
		return underlying.getDisplayName();
	}

	@Override
	public String getEmail() {
		//TODO This has to be possible right...
		return "";
	}

	@Override
	public int getRole() {
		// TODO Auto-generated method stub.  
		return 0;
	}

}
