package com.oa.support.file.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class DBQueryProcessorView extends ViewPart {
	public static final String ID = "com.oa.support.file.views.DBQueryProcessorView"; //$NON-NLS-1$

	public DBQueryProcessorView() {
	}

	public void createPartControl(Composite dParent) {

		final ScrolledComposite sc = new ScrolledComposite(dParent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER_SOLID);
		final Composite parent = new Composite(sc, SWT.NONE);
		
		FormLayout layout = new FormLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		parent.setLayout(layout);
		
		Group fileDetails = new Group(parent, SWT.NONE);
		fileDetails.setText("Query Details");
		
		FormLayout fLayout = new FormLayout();
		fLayout.marginWidth = 15;
		fLayout.marginHeight = 25;
		fLayout.marginLeft = 20;
		fileDetails.setLayout(fLayout);

		FormData data = new FormData();
		fileDetails.setLayoutData(data);
		Label blank1 = new Label(fileDetails, SWT.NULL);
		blank1.setText("\t\t\t Database Query Details Widgets and Controls here \t\t\t\t\t\t");
		
		Group fileContents = new Group(parent, SWT.NONE);
		fileContents.setText("Query Results");
		
		fLayout = new FormLayout();
		fLayout.marginWidth = 15;
		fLayout.marginHeight = 25;
		fLayout.marginLeft = 20;
		fileContents.setLayout(fLayout);

		data = new FormData();
		data.top = new FormAttachment(fileDetails, 25);
		fileContents.setLayoutData(data);
		Label blank2 = new Label(fileContents, SWT.NULL);
		blank2.setText("\n\n\n\t\t\t Database Query Results displayed here          \t\t\t\t\t\t\t\n\n\n");
		Label blank3 = new Label(fileContents, SWT.NULL);
		blank3.setText("          ");
		
		sc.setContent(parent);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setShowFocusedControl(true);
	}

	@Override
	public void setFocus() {
	
	}
}
