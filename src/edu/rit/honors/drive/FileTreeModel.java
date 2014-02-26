package edu.rit.honors.drive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;

public class FileTreeModel implements Container.Hierarchical {
	
	public FileTreeModel(File f){
		file = f;
		cache = new LinkedHashMap<String,FileItem>();
		unexplored = new LinkedHashMap<String,Folder>();
		cache.put(f.getID(),new FileItem(f));
		if(f instanceof Folder){
			unexplored.put(f.getID(), (Folder)f);
		}
	}
	
	private edu.rit.honors.drive.File file;
	private LinkedHashMap<String,FileItem> cache;
	private LinkedHashMap<String,Folder> unexplored;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4657996361438210803L;
	private static final int MAX_DEPTH = 10;

	@Override
	public Item getItem(Object itemId) {
		if(file.getID().equals(itemId)){
			return new FileItem(file);
		}else if(cache.containsKey(itemId)){
			return cache.get(itemId);
		}else{
			boolean found = false;
			FileItem item = null, theItem = null;
			if(!unexplored.isEmpty()){
				for(int i = 0; i < MAX_DEPTH; i++){
					for(Folder f : unexplored.values()){
						Collection<File> children = f.getChildren();
						unexplored.remove(f.getID());
						for(File x : children){
							if(x instanceof Folder){
								unexplored.put(x.getID(), (Folder)x);
							}
							item = new FileItem(x);
							if(x.getID().equals(itemId)){
								found = true; theItem = item;
							}
							cache.put(x.getID(), item);
						}
						if(found){
							return theItem;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public Collection<?> getContainerPropertyIds() {
		return null;
	}

	@Override
	public Collection<?> getItemIds() {
		// TODO will this confuse the vaading Tree UI object?
		return cache.keySet();
	}

	@Override
	public Property getContainerProperty(Object itemId, Object propertyId) {
		return null;
	}

	@Override
	public Class<?> getType(Object propertyId) {
		return null;
	}

	@Override
	public int size() {
		// TODO Is there a better way to estimate size?
		return cache.size();
	}

	@Override
	public boolean containsId(Object itemId) {
		// note that searching for an item would be no quicker than getting it
		// since this model is lazy.
		return null != this.getItem(itemId);
	}

	@Override
	public Item addItem(Object itemId) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Items cannot be added to the File Tree");
	}

	@Override
	public Object addItem() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Items cannot be added to the File Tree");
	}

	@Override
	public boolean addContainerProperty(Object propertyId, Class<?> type,
			Object defaultValue) throws UnsupportedOperationException {
		return false;
	}

	@Override
	public boolean removeContainerProperty(Object propertyId)
			throws UnsupportedOperationException {
		return false;
	}

	@Override
	public boolean removeAllItems() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Items cannot be deleted from the File Tree");
	}

	@Override
	public Collection<?> getChildren(Object itemId) {
		FileItem x = (FileItem) this.getItem(itemId);
		if(null == x){
			return null;
		}else{
			//TODO make into items and put in the cache
			Collection<File> children = x.getChildren();
			Collection<FileItem> items = new ArrayList<FileItem>(children.size());
			FileItem i;
			for(File f : children){
				i = new FileItem(f);
				items.add(i);
				cache.put(f.getID(), i);
				if(f instanceof Folder){
					unexplored.put(f.getID(), (Folder)f);
				}
			}
			return items;
		}
	}

	@Override
	public Object getParent(Object itemId) {
		FileItem x = (FileItem) this.getItem(itemId);
		if(null == x){
			return null;
		}else{
			return this.getItem(x.getParent().getID());
		}
	}

	@Override
	public Collection<?> rootItemIds() {
		Collection<String> c = new ArrayList<String>();
		c.add(file.getID());
		return c;
	}

	@Override
	public boolean setParent(Object itemId, Object newParentId)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("The File Tree cannot be modified.");
	}

	@Override
	public boolean areChildrenAllowed(Object itemId) {
		FileItem i = (FileItem) this.getItem(itemId);
		return (i != null && i.isFolder());
	}

	@Override
	public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("The File Tree cannot be modified.");
	}

	@Override
	public boolean isRoot(Object itemId) {
		return file.getID().equals(itemId);
	}

	@Override
	public boolean hasChildren(Object itemId) {
		FileItem i = (FileItem) this.getItem(itemId);
		return i != null && i.hasChildren();
	}

	@Override
	public boolean removeItem(Object itemId)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Items cannot be deleted from the File Tree");
	}

}
