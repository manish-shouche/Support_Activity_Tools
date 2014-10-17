package com.oa.support.file.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * This class acts as a plug-in view into a new workbench view. The view
 * shows data obtained from various other View Parts.
 */

public class FileHandlerView extends ViewPart {

	public static final String ID = "com.oa.support.file.views.FileHandlerView"; //$NON-NLS-1$

	OperationActionPart oaPart;
	ActionDetailsPart adPart;
	FileDetailsPart fdPart;
	ButtonsPart btnPart;
	
	public FileHandlerView() {
	}

	public void createPartControl(Composite dParent) {

		final ScrolledComposite sc = new ScrolledComposite(dParent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER_SOLID);
		final Composite parent = new Composite(sc, SWT.NONE);

		FormLayout layout = new FormLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		parent.setLayout(layout);

		oaPart = new OperationActionPart(this, parent);
		oaPart.createOprActionList();
		adPart = new ActionDetailsPart(this, parent);
		adPart.createActionDetailsGroup();
		fdPart = new FileDetailsPart(this, parent);
		fdPart.createFileDetailsGroup();
		btnPart = new ButtonsPart(this, parent);
		btnPart.createButtons();

		sc.setContent(parent);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(parent.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		sc.setShowFocusedControl(true);
	}

	public OperationActionPart getOperationActionPart() {
		return oaPart;
	}

	public ActionDetailsPart getActionDetailsPart() {
		return adPart;
	}

	public FileDetailsPart getFileDetailsPart() {
		return fdPart;
	}
	
	public ButtonsPart getButtonsPart() {
		return btnPart;
	}
	
	public void setFocus() {

	}
}