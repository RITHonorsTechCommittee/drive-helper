package edu.rit.honors.drive.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.rit.honors.drive.FileTreeModel;

public class FileTreeModelTest {

	private FileTreeModel model;

	@Before
	public void setUp() throws Exception {
		this.model = new FileTreeModel(FakeDrive.getInstance().getTree(null));
	}

	@Test
	public void testRoot() {
		String[] expected = {"a001"};
		Assert.assertArrayEquals(expected, model.rootItemIds().toArray());
	}
	
	@Test
	public void testExists() {
		Assert.assertTrue(model.containsId("a001"));
	}
	
	@Test
	public void testExistsFirstLevel() {
		Assert.assertTrue(model.containsId("bcd1"));
	}
	
	@Test
	public void testExistsSecondLevel() {
		Assert.assertTrue(model.containsId("a024"));
	}

}
