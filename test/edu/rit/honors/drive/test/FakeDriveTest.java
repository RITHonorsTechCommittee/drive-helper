package edu.rit.honors.drive.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.rit.honors.drive.File;

public class FakeDriveTest {
	
	private File tree;

	@Before
	public void setUp() throws Exception {
		tree = FakeDrive.getInstance().getTree(null);
	}

	@Test
	public void testTree() {
		Assert.assertNotNull(tree);
	}
	
	@Test
	public void testRoot() {
		Assert.assertEquals("a001", tree.getID());
	}

}
