package edu.rit.honors.drive.test;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.GAEVaadinServlet;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import edu.rit.honors.drive.FileTreeModel;

@Title("Hello Window")
public class TreeUITest extends UI {
    /**
	 * Generated by Eclipse
	 */
	private static final long serialVersionUID = -3513103186685785409L;
	
    //@WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(
            productionMode = false,
            ui = TreeUITest.class)
    public static class Servlet extends GAEVaadinServlet {

		/**
		 * Generated by Eclipse
		 */
		private static final long serialVersionUID = 3177684570944007538L;
    }

	@Override
    protected void init(VaadinRequest request) {
        // Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Display the greeting
        content.addComponent(new Label("Hello World!"));
        Tree tree = new Tree();
        tree.setContainerDataSource(new FileTreeModel(FakeDrive.getInstance().getTree(null)));
        content.addComponent(tree);
    }
}
