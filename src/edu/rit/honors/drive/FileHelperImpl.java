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

/**
 * @author Greg
 * 
 * Implementation of the File Helper Singleton
 * 
 */
public class FileHelperImpl implements FileHelper
{

	private static final String FOLDER_MIME = "application/vnd.google-apps.folder";
	
	private Drive service;
	
	private static FileHelperImpl instance;
	
	/**
	 * This doesn't implement singleton correctly (constructor should be private)
	 * TODO:  One we figure out how this will fit into the lifecycle of the servlet we can refactor
	 * 
	 * @param service A reference to the drive API that has been authenticated
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.rit.honors.drive.FileHelper#getChildren(edu.rit.honors.drive.File)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.rit.honors.drive.FileHelper#getParent(edu.rit.honors.drive.File)
	 */
	@Override
	public File getParent(File file)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.rit.honors.drive.FileHelper#getSiblings(edu.rit.honors.drive.File)
	 */
	@Override
	public ArrayList<File> getSiblings(File file)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.rit.honors.drive.FileHelper#getTree(edu.rit.honors.drive.File)
	 */
	@Override
	public File getTree(File file)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.rit.honors.drive.FileHelper#getUsers(edu.rit.honors.drive.File)
	 */
	@Override
	public ArrayList<User> getUsers(File file)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.rit.honors.drive.FileHelper#addUser(edu.rit.honors.drive.User,
	 * edu.rit.honors.drive.File)
	 */
	@Override
	public boolean addUser(User user, File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.rit.honors.drive.FileHelper#removeUser(edu.rit.honors.drive.User,
	 * edu.rit.honors.drive.File)
	 */
	@Override
	public boolean removeUser(User user, File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.rit.honors.drive.FileHelper#hasUser(edu.rit.honors.drive.User,
	 * edu.rit.honors.drive.File)
	 */
	@Override
	public boolean hasUser(User user, File file)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFile(File f)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDirectory(File f)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasChildren(File f)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
