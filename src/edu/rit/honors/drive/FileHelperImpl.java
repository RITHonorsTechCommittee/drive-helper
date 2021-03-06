/**
 * 
 */
package edu.rit.honors.drive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

/**
 * @author Greg
 * 
 *         Implementation of the File Helper Singleton
 * 
 */
public class FileHelperImpl implements FileHelper
{

	private static final String FOLDER_MIME = "application/vnd.google-apps.folder";

	private Drive service;

	private static FileHelperImpl instance;

	/**
	 * This doesn't implement singleton correctly (constructor should be
	 * private) TODO: One we figure out how this will fit into the lifecycle of
	 * the servlet we can refactor
	 * 
	 * @param service
	 *            A reference to the drive API that has been authenticated
	 */
	public FileHelperImpl(Drive service)
	{
		this.service = service;
		instance = this;
	}

	/**
	 * Gets the instance of the FileHelper
	 * 
	 * @return The instance
	 */
	public FileHelper getIntance()
	{
		return instance;
	}

	@Override
	public Collection<File> getChildren(File file)
	{
		List<File> result = new ArrayList<File>();
		Files.List request;
		try
		{
			request = service.files().list();

			request.setQ(String.format("'%s' in parents", file.getId()));
			request.setFields("items(id,mimeType,ownerNames,owners(displayName,kind,permissionId),parents(id,isRoot,kind),title),kind,nextPageToken");

			do
			{
				try
				{
					FileList files = request.execute();

					// Add every file / folder in the hierarchy
					for (File f : files.getItems())
					{
						if (f.getMimeType().equals(FOLDER_MIME))
						{
							result.addAll(getChildren(f));
						}
						else
						{
							result.add(f);
						}
					}

					request.setPageToken(files.getNextPageToken());

				}
				catch (IOException e)
				{
					request.setPageToken(null);
					throw e;
				}
			} while (request.getPageToken() != null && request.getPageToken().length() > 0);

			return result;
		}
		catch (IOException e1)
		{
			return new ArrayList<com.google.api.services.drive.model.File>();
		}
	}

	@Override
	public File getParent(File file)
	{
		List<ParentReference> parents = file.getParents();
		if (parents != null && parents.size() > 0)
		{
			return getFileById(parents.get(0).getId());
		}

		return null;
	}

	@Override
	public Collection<File> getSiblings(File file)
	{
		File parent = getParent(file);
		return getChildren(parent);
	}

	@Override
	public Collection<User> getUsers(File file)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(User user, File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(User user, File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasUser(User user, File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFile(File f)
	{
		return f != null && !f.getMimeType().equals(FOLDER_MIME);
	}

	@Override
	public boolean isDirectory(File f)
	{
		return f != null && f.getMimeType().equals(FOLDER_MIME);
	}

	@Override
	public boolean hasChildren(File f)
	{
		// Should find a better way than just calculating the children.
		return isDirectory(f) && getChildren(f).size() > 0;
	}

	@Override
	public File getFileById(String id)
	{
		try
		{
			File file = service.files().get(id).execute();
			return file;
		}
		catch (IOException e)
		{
			return null;
		}
	}

	@Override
	public boolean replaceFile(File oldfile) {
		File newfile = new File();
		newfile.setTitle(oldfile.getTitle());
		newfile.setParents(oldfile.getParents());
		try {
			newfile = service.files().copy(oldfile.getId(), newfile).execute();
			try{
				service.files().delete(oldfile.getId());
				return true;
			} catch (IOException e) {
				service.files().delete(newfile.getId());
			}
		} catch (IOException e) {
		}
		return false;
	}

}
