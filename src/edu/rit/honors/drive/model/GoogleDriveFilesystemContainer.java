/*
 * GoogleDriveFilesystemContainer is an adaptation of com.vaadin.data.util.FilesystemContainer.
 * Modifications were made by Reginald Pierce in 2014.  Use of this file is pursuant
 * to the original Vaadin license.
 */

/*
 * Copyright 2000-2013 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package edu.rit.honors.drive.model;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.drive.model.File;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;

import edu.rit.honors.drive.HelperFactory;

/**
 * A hierarchical container wrapper for Google Drive.
 * 
 * @author Reginald Pierce
 * @author Vaadin Ltd.
 */
@SuppressWarnings("serial")
public class GoogleDriveFilesystemContainer implements Container.Hierarchical {

    /**
     * String identifier of a file's "name" property.
     */
    public static String PROPERTY_NAME = "Name";

    /**
     * String identifier of a file's "size" property.
     */
    public static String PROPERTY_SIZE = "Size";

    /**
     * String identifier of a file's "icon" property.
     */
    public static String PROPERTY_ICON = "Icon";

    /**
     * String identifier of a file's "last modified" property.
     */
    public static String PROPERTY_LASTMODIFIED = "Last Modified";

    /**
     * List of the string identifiers for the available properties.
     */
    public static Collection<String> FILE_PROPERTIES;

    private final static Method FILEITEM_LASTMODIFIED;

    private final static Method FILEITEM_NAME;

    private final static Method FILEITEM_ICON;

    private final static Method FILEITEM_SIZE;

    static {

        FILE_PROPERTIES = new ArrayList<String>();
        FILE_PROPERTIES.add(PROPERTY_NAME);
        FILE_PROPERTIES.add(PROPERTY_ICON);
        FILE_PROPERTIES.add(PROPERTY_SIZE);
        FILE_PROPERTIES.add(PROPERTY_LASTMODIFIED);
        FILE_PROPERTIES = Collections.unmodifiableCollection(FILE_PROPERTIES);
        try {
            FILEITEM_LASTMODIFIED = FileItem.class.getMethod("lastModified",
                    new Class[] {});
            FILEITEM_NAME = FileItem.class.getMethod("getName", new Class[] {});
            FILEITEM_ICON = FileItem.class.getMethod("getIcon", new Class[] {});
            FILEITEM_SIZE = FileItem.class.getMethod("getSize", new Class[] {});
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(
                    "Internal error finding methods in FilesystemContainer");
        }
    }

    private File[] roots = new File[] {};

    private boolean recursive = true;

	public static final Comparator<File> sorter = new FileSorter();

    /**
     * Constructs a new <code>FileSystemContainer</code> with the specified file
     * as the root of the filesystem. The files are included recursively.
     * 
     * @param root
     *            the root file for the new file-system container. Null values
     *            are ignored.
     */
    public GoogleDriveFilesystemContainer(File root) {
        if (root != null) {
            roots = new File[] { root };
        }
    }

    /**
     * Constructs a new <code>FileSystemContainer</code> with the specified file
     * as the root of the filesystem. The files are included recursively
     * only if specified by the recursive parameter.
     * 
     * @param root
     *            the root file for the new file-system container.
     * @param recursive
     *            should the container recursively contain subdirectories.
     */
    public GoogleDriveFilesystemContainer(File root, boolean recursive) {
        this(root);
        setRecursive(recursive);
    }
    
    /**
     * Constructs a new <code>FileSystemContainer</code> with the specified files
     * as the root of the filesystem. The files are included recursively.
     * 
     * @param roots
     *            a Collection of root files for the new file-system container.
     */
    public GoogleDriveFilesystemContainer(Collection<File> roots){
    	this.roots = (File[]) roots.toArray();
    }
    
    /**
     * Constructs a new <code>FileSystemContainer</code> with the specified files
     * as the root of the filesystem. The files are included recursively
     * only if specified by the recursive parameter.
     * 
     * @param root
     *            a Collection of root files for the new file-system container.
     * @param recursive
     *            should the container recursively contain subdirectories.
     */
    public GoogleDriveFilesystemContainer(Collection<File> roots, boolean recursive){
        this(roots);
        setRecursive(recursive);
    }

    /**
     * Adds new root file directory. Adds a file to be included as root file
     * directory in the <code>FilesystemContainer</code>.
     * 
     * @param root
     *            the File to be added as root directory. Null values are
     *            ignored.
     */
    public void addRoot(File root) {
        if (root != null) {
            final File[] newRoots = new File[roots.length + 1];
            for (int i = 0; i < roots.length; i++) {
                newRoots[i] = roots[i];
            }
            newRoots[roots.length] = root;
            roots = newRoots;
        }
    }

    /**
     * Tests if the specified Item in the container may have children. Since a
     * <code>FileSystemContainer</code> contains files and directories, this
     * method returns <code>true</code> for directory Items only.
     * 
     * @param itemId
     *            the id of the item.
     * @return <code>true</code> if the specified Item is a directory,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean areChildrenAllowed(Object itemId) {
        return itemId instanceof File 
        		&& HelperFactory.getFileHelper().isDirectory((File)itemId);
    }

    /*
     * Gets the ID's of all Items who are children of the specified Item. Don't
     * add a JavaDoc comment here, we use the default documentation from
     * implemented interface.
     */
    @Override
    public Collection<File> getChildren(Object itemId) {

        if (!(itemId instanceof File)) {
            return Collections.unmodifiableCollection(new LinkedList<File>());
        }
        final List<File> l = new ArrayList<File>(HelperFactory.getFileHelper().getChildren((File)itemId));
        Collections.sort(l, GoogleDriveFilesystemContainer.sorter );

        return Collections.unmodifiableCollection(l);
    }

    /*
     * Gets the parent item of the specified Item. Don't add a JavaDoc comment
     * here, we use the default documentation from implemented interface.
     */
    @Override
    public Object getParent(Object itemId) {

        if (!(itemId instanceof File)) {
            return null;
        }
        return HelperFactory.getFileHelper().getParent((File)itemId);
    }

    /*
     * Tests if the specified Item has any children. Don't add a JavaDoc comment
     * here, we use the default documentation from implemented interface.
     */
    @Override
    public boolean hasChildren(Object itemId) {

        if (!(itemId instanceof File)) {
            return false;
        }
        return HelperFactory.getFileHelper().hasChildren((File)itemId);
    }

    /*
     * Tests if the specified Item is the root of the filesystem. Don't add a
     * JavaDoc comment here, we use the default documentation from implemented
     * interface.
     */
    @Override
    public boolean isRoot(Object itemId) {

        if (!(itemId instanceof File)) {
            return false;
        }
        for (int i = 0; i < roots.length; i++) {
            if (roots[i].getId().equals(((File)itemId).getId())) {
                return true;
            }
        }
        return false;
    }

    /*
     * Gets the ID's of all root Items in the container. Don't add a JavaDoc
     * comment here, we use the default documentation from implemented
     * interface.
     */
    @Override
    public Collection<File> rootItemIds() {

        File[] f;

        // in single root case we use children
        if (roots.length == 1) {
            return this.getChildren(roots[0]);
        } else {
            f = roots;
        }

        if (f == null) {
            return Collections.unmodifiableCollection(new LinkedList<File>());
        }

        final List<File> l = Arrays.asList(f);
        Collections.sort(l, GoogleDriveFilesystemContainer.sorter );

        return Collections.unmodifiableCollection(l);
    }

    /**
     * Returns <code>false</code> when conversion from files to directories is
     * not supported.
     * 
     * @param itemId
     *            the ID of the item.
     * @param areChildrenAllowed
     *            the boolean value specifying if the Item can have children or
     *            not.
     * @return <code>true</code> if the operaton is successful otherwise
     *         <code>false</code>.
     * @throws UnsupportedOperationException
     *             if the setChildrenAllowed is not supported.
     */
    @Override
    public boolean setChildrenAllowed(Object itemId, boolean areChildrenAllowed)
            throws UnsupportedOperationException {

        throw new UnsupportedOperationException(
                "Conversion file to/from directory is not supported");
    }

    /**
     * Returns <code>false</code> when moving files around in the filesystem is
     * not supported.
     * 
     * @param itemId
     *            the ID of the item.
     * @param newParentId
     *            the ID of the Item that's to be the new parent of the Item
     *            identified with itemId.
     * @return <code>true</code> if the operation is successful otherwise
     *         <code>false</code>.
     * @throws UnsupportedOperationException
     *             if the setParent is not supported.
     */
    @Override
    public boolean setParent(Object itemId, Object newParentId)
            throws UnsupportedOperationException {

        throw new UnsupportedOperationException("File moving is not supported");
    }

    /*
     * Tests if the filesystem contains the specified Item. Don't add a JavaDoc
     * comment here, we use the default documentation from implemented
     * interface.
     */
    @Override
    public boolean containsId(Object itemId) {

        if (!(itemId instanceof File)) {
            return false;
        }
        String id = ((File)itemId).getId();
        
        return containsId(Arrays.asList(roots),id);
    }
    
    /**
     * Internal recursive method to match ids
     * 
     * @param c Collection of Files to match against
     * @param id the String id of interest
     * @return true only if one of the files in Collection c or one of its
     * 			children is the file identified by id
     */
    private boolean containsId(Collection<File> c,String id){
    	boolean val = false;
    	for(File x : c){
    		val |= id.equals(x.getId()) || containsId(HelperFactory.getFileHelper().getChildren(x),id);
        	if(val)break;
    	}
    	return val;
    }

    /*
     * Gets the specified Item from the filesystem. Don't add a JavaDoc comment
     * here, we use the default documentation from implemented interface.
     */
    @Override
    public Item getItem(Object itemId) {

        if (!(itemId instanceof File)) {
            return null;
        }
        return new FileItem((File) itemId);
    }

    /**
     * Internal recursive method to add the files under the specified directory
     * to the collection.
     * 
     * @param col
     *            the collection where the found items are added
     * @param f
     *            the root file where to start adding files
     */
    private void addItemIds(Collection<File> col, File f) {
        final List<File> l = new ArrayList<File>(HelperFactory.getFileHelper().getChildren((File)f));
        Collections.sort(l, GoogleDriveFilesystemContainer.sorter ); 

        for (final Iterator<File> i = l.iterator(); i.hasNext();) {
            final File lf = i.next();
            col.add(lf);
            if (HelperFactory.getFileHelper().isDirectory(lf)) {
                addItemIds(col, lf);
            }
        }
    }

    /*
     * Gets the IDs of Items in the filesystem. Don't add a JavaDoc comment
     * here, we use the default documentation from implemented interface.
     */
    @Override
    public Collection<File> getItemIds() {

        if (recursive) {
            final Collection<File> col = new ArrayList<File>();
            for (int i = 0; i < roots.length; i++) {
                addItemIds(col, roots[i]);
            }
            return Collections.unmodifiableCollection(col);
        } else {
            File[] f;
            if (roots.length == 1) {
            	return HelperFactory.getFileHelper().getChildren(roots[0]);
            } else {
                f = roots;
            }

            if (f == null) {
                return Collections
                        .unmodifiableCollection(new LinkedList<File>());
            }

            final List<File> l = Arrays.asList(f);
            Collections.sort(l,GoogleDriveFilesystemContainer.sorter);
            
            return Collections.unmodifiableCollection(l);
        }

    }

    /**
     * Gets the specified property of the specified file Item. The available
     * file properties are "Name", "Size" and "Last Modified". If propertyId is
     * not one of those, <code>null</code> is returned.
     * 
     * @param itemId
     *            the ID of the file whose property is requested.
     * @param propertyId
     *            the property's ID.
     * @return the requested property's value, or <code>null</code>
     */
    @Override
    public Property<? extends Object> getContainerProperty(Object itemId, Object propertyId) {

        if (!(itemId instanceof File)) {
            return null;
        }

        if (propertyId.equals(PROPERTY_NAME)) {
            return new MethodProperty<Object>(getType(propertyId),
                    new FileItem((File) itemId), FILEITEM_NAME, null);
        }

        if (propertyId.equals(PROPERTY_ICON)) {
            return new MethodProperty<Object>(getType(propertyId),
                    new FileItem((File) itemId), FILEITEM_ICON, null);
        }

        if (propertyId.equals(PROPERTY_SIZE)) {
            return new MethodProperty<Object>(getType(propertyId),
                    new FileItem((File) itemId), FILEITEM_SIZE, null);
        }

        if (propertyId.equals(PROPERTY_LASTMODIFIED)) {
            return new MethodProperty<Object>(getType(propertyId),
                    new FileItem((File) itemId), FILEITEM_LASTMODIFIED, null);
        }

        return null;
    }

    /**
     * Gets the collection of available file properties.
     * 
     * @return Unmodifiable collection containing all available file properties.
     */
    @Override
    public Collection<String> getContainerPropertyIds() {
        return FILE_PROPERTIES;
    }

    /**
     * Gets the specified property's data type. "Name" is a <code>String</code>,
     * "Size" is a <code>Long</code>, "Last Modified" is a <code>Date</code>. If
     * propertyId is not one of those, <code>null</code> is returned.
     * 
     * @param propertyId
     *            the ID of the property whose type is requested.
     * @return data type of the requested property, or <code>null</code>
     */
    @Override
    public Class<?> getType(Object propertyId) {

        if (propertyId.equals(PROPERTY_NAME)) {
            return String.class;
        }
        if (propertyId.equals(PROPERTY_ICON)) {
            return Resource.class;
        }
        if (propertyId.equals(PROPERTY_SIZE)) {
            return Long.class;
        }
        if (propertyId.equals(PROPERTY_LASTMODIFIED)) {
            return Date.class;
        }
        return null;
    }

    /**
     * Internal method to recursively calculate the number of files under a root
     * directory.
     * 
     * @param f
     *            the root to start counting from.
     */
    private int getFileCounts(File f) {
        Collection<File> l = HelperFactory.getFileHelper().getChildren(f);

        if (l == null) {
            return 0;
        }
        int ret = l.size();
        for (File x : l) {
            if (HelperFactory.getFileHelper().isDirectory(x)) {
                ret += getFileCounts(x);
            }
        }
        return ret;
    }

    /**
     * Gets the number of Items in the container. In effect, this is the
     * combined amount of files and directories.
     * 
     * @return Number of Items in the container.
     */
    @Override
    public int size() {

        if (recursive) {
            int counts = 0;
            for (int i = 0; i < roots.length; i++) {
                counts += getFileCounts(roots[i]);
            }
            return counts;
        } else {
            Collection<File> f;
            if (roots.length == 1) {
                f = HelperFactory.getFileHelper().getChildren(roots[0]);
                if (f == null) {
                	return 0;
                }
                return f.size();
            } else {
                return roots.length;
            }
        }
    }

    /**
     * A Item wrapper for files in a filesystem.
     * 
     * @author Reginald Pierce
     * @author Vaadin Ltd.
     */
    public class FileItem implements Item {

        /**
         * The wrapped file.
         */
        private final File file;

        /**
         * Constructs a FileItem from a existing file.
         */
        private FileItem(File file) {
            this.file = file;
        }

        /*
         * Gets the specified property of this file. Don't add a JavaDoc comment
         * here, we use the default documentation from implemented interface.
         */
        @Override
        public Property<? extends Object> getItemProperty(Object id) {
            return getContainerProperty(file, id);
        }

        /*
         * Gets the IDs of all properties available for this item Don't add a
         * JavaDoc comment here, we use the default documentation from
         * implemented interface.
         */
        @Override
        public Collection<String> getItemPropertyIds() {
            return getContainerPropertyIds();
        }

        /**
         * Calculates a integer hash-code for the Property that's unique inside
         * the Item containing the Property. Two different Properties inside the
         * same Item contained in the same list always have different
         * hash-codes, though Properties in different Items may have identical
         * hash-codes.
         * 
         * @return A locally unique hash-code as integer
         */
        @Override
        public int hashCode() {
            return file.hashCode() ^ GoogleDriveFilesystemContainer.this.hashCode();
        }

        /**
         * Tests if the given object is the same as the this object. Two
         * Properties got from an Item with the same ID are equal.
         * 
         * @param obj
         *            an object to compare with this object.
         * @return <code>true</code> if the given object is the same as this
         *         object, <code>false</code> if not
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof FileItem)) {
                return false;
            }
            final FileItem fi = (FileItem) obj;
            return fi.getHost() == getHost() && fi.file.equals(file);
        }

        /**
         * Gets the host of this file.
         */
        private GoogleDriveFilesystemContainer getHost() {
            return GoogleDriveFilesystemContainer.this;
        }

        /**
         * Gets the last modified date of this file.
         * 
         * @return Date
         */
        public DateTime lastModified() {
            return file.getModifiedDate();
        }

        /**
         * Gets the name of this file.
         * 
         * @return file name of this file.
         */
        public String getName() {
            return file.getTitle();
        }

        /**
         * Gets the icon of this file.
         * 
         * @return the icon of this file.
         */
        public Resource getIcon() {
            return new ExternalResource(file.getIconLink());
        }

        /**
         * Gets the size of this file.
         * 
         * @return size
         */
        public long getSize() {
            if (HelperFactory.getFileHelper().isDirectory(file)) {
                return 0;
            }
            return file.getFileSize();
        }

        /**
         * Generally, the toString() method will return the same value as getName().
         * 
         * @see getName()
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            if ("".equals(file.getTitle())) {
                return file.getId();
            }
            return file.getTitle();
        }

        /**
         * Filesystem container does not support adding new properties.
         * 
         * @see com.vaadin.data.Item#addItemProperty(Object, Property)
         */
        @SuppressWarnings("rawtypes")
		@Override
        public boolean addItemProperty(Object id, Property property)
                throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Filesystem container "
                    + "does not support adding new properties");
        }

        /**
         * Filesystem container does not support removing properties.
         * 
         * @see com.vaadin.data.Item#removeItemProperty(Object)
         */
        @Override
        public boolean removeItemProperty(Object id)
                throws UnsupportedOperationException {
            throw new UnsupportedOperationException(
                    "Filesystem container does not support property removal");
        }

    }

    /**
     * Is this container recursive filesystem.
     * 
     * @return <code>true</code> if container is recursive, <code>false</code>
     *         otherwise.
     */
    public boolean isRecursive() {
        return recursive;
    }

    /**
     * Sets the container recursive property. Set this to false to limit the
     * files directly under the root file.
     * <p>
     * Note : This is meaningful only if the root really is a directory.
     * </p>
     * 
     * @param recursive
     *            the New value for recursive property.
     */
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Container#addContainerProperty(java.lang.Object,
     * java.lang.Class, java.lang.Object)
     */
    @Override
    public boolean addContainerProperty(Object propertyId, Class<?> type,
            Object defaultValue) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "File system container does not support this operation");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Container#addItem()
     */
    @Override
    public Object addItem() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "File system container does not support this operation");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Container#addItem(java.lang.Object)
     */
    @Override
    public Item addItem(Object itemId) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "File system container does not support this operation");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Container#removeAllItems()
     */
    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "File system container does not support this operation");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Container#removeItem(java.lang.Object)
     */
    @Override
    public boolean removeItem(Object itemId)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "File system container does not support this operation");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vaadin.data.Container#removeContainerProperty(java.lang.Object )
     */
    @Override
    public boolean removeContainerProperty(Object propertyId)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "File system container does not support this operation");
    }
}
