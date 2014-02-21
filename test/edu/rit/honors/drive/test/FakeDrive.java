package edu.rit.honors.drive.test;

import java.util.ArrayList;

import edu.rit.honors.drive.File;
import edu.rit.honors.drive.FileHelper;
import edu.rit.honors.drive.Folder;
import edu.rit.honors.drive.User;
import edu.rit.honors.drive.UserHelper;

public class FakeDrive implements FileHelper, UserHelper {
	
	public static final User ROOT = new FakeUser("u001","root","root@example.com");
	public static final User WEB = new FakeUser("u002","web","web@example.com");
	public static final User USER = new FakeUser("u100","user","user100@example.com");
	private static FileHelper instance = null;

	@Override
	public User getUser(String email) {
		if(email.startsWith("root")){
			return ROOT;
		}else if(email.startsWith("web")){
			return WEB;
		}else{
			return USER;
		}
	}

	@Override
	public Object getPermission(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FileHelper getInstance() {
		if(null == instance){
			instance = new FakeDrive();
		}
		return instance;
	}

	@Override
	public ArrayList<File> getChildren(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getParent(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<File> getSiblings(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getTree(File file) {
		return getTree();
	}
	
	public File getTree(){
		FakeFolder rootfile = new FakeFolder("a001",FakeDrive.ROOT,"root",null,null);
		File f1 = new FakeFile("bcd1",FakeDrive.USER,"songs.txt",rootfile);
		File f2 = new FakeFile("c5f0",FakeDrive.USER,".vimrc",rootfile);
		rootfile.addChild(f1).addChild(f2);
		FakeFolder folder2 = new FakeFolder("a002",FakeDrive.WEB,"cfg",rootfile,null);
		File cfg1 = new FakeFile("a024",FakeDrive.WEB,"httpd.conf",folder2);
		folder2.addChild(cfg1);
		rootfile.addChild(folder2);
		return rootfile;
	}

	@Override
	public ArrayList<User> getUsers(File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUser(User user, File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(User user, File file) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasUser(User user, File file) {
		// TODO Auto-generated method stub
		return false;
	}

}
