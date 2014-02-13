package edu.rit.honors.drive;

import java.util.Collection;

/**
 * Extends the <code>File</code> interface with child operations
 */

public interface Folder extends edu.rit.honors.drive.File {

	/**
	 * Optional.  Implementation will assist compatibility with
	 * com.vaadin.data.util.Container.Hierarchical
	 */
	public boolean hasChildren();

	/**
	 * Return the direct children of this folder.  Note that this
	 * will not traverse into subfolders. If you want to get all
	 * children (including those in subfolders), use 
	 * <code>getChildrenRecursive</code>.
	 *
	 * @return a <code>Collection</code> of children. The order 
	 * in which the children are returned is not guaranteed.
	 */
	public Collection<File> getChildren();

	/**
	 * Return all the children of this folder, including children of
	 * any subfolders.
	 *
	 * @return a <code>Collection</code> of children.  The order
	 * in which the children are returned is not guaranteed.
	 */
	public Collection<File> getChildrenRecursive();

}
