package edu.rit.honors.drive;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;

public class FileItem implements File, Item, Folder {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6778975016426834906L;
	private File underlying;
	
	public FileItem(File f){
		underlying = f;
	}
	
	public FileItem(com.google.api.services.drive.model.File f){
		this(new GoogleDriveFile(f));
	}
	
	public FileItem(String fileId){
		this(new GoogleDriveFile(fileId));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Property getItemProperty(Object id) {
		Object prop = underlying.getGoogleFile().get(id);
		return new ObjectProperty(prop,prop.getClass(),true);
	}

	@Override
	public Collection<?> getItemPropertyIds() {
		return underlying.getGoogleFile().keySet();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean addItemProperty(Object id, Property property)
			throws UnsupportedOperationException {
		underlying.getGoogleFile().set((String)id, property.getValue());
		return true;
	}

	@Override
	public boolean removeItemProperty(Object id)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getID() {
		return underlying.getID();
	}

	@Override
	public User getOwner() {
		return underlying.getOwner();
	}

	@Override
	public Collection<User> getEditors() {
		return underlying.getEditors();
	}

	@Override
	public boolean changeOwner(User newUser) {
		return underlying.changeOwner(newUser);
	}

	@Override
	public com.google.api.services.drive.model.File getGoogleFile() {
		return underlying.getGoogleFile();
	}

	@Override
	public String downloadUrl(String mimeType) {
		return underlying.downloadUrl(mimeType);
	}

	@Override
	public String getName() {
		return underlying.getName();
	}

	@Override
	public String getMIME() {
		return underlying.getMIME();
	}

	@Override
	public Folder getParent() {
		return underlying.getParent();
	}

	@Override
	public boolean hasChildren() {
		if(underlying instanceof Folder){
			return ((Folder) underlying).hasChildren();
		}else{
			return false;
		}
	}

	@Override
	public Collection<File> getChildren() {
		if(underlying instanceof Folder){
			return ((Folder) underlying).getChildren();
		}else{
			return new ArrayList<File>(0);
		}
	}

	@Override
	public Collection<File> getChildrenRecursive() {
		if(underlying instanceof Folder){
			return ((Folder) underlying).getChildrenRecursive();
		}else{
			return new ArrayList<File>(0);
		}
	}

	public boolean isFolder() {
		//TODO this may not be the best choice because (in theory) the underlying
		//  could be another FileItem or something else which implements folder
		//  but isn't actually a folder.
		return underlying instanceof Folder;
	}

}
