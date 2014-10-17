package com.oa.support.file.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * This class displays the Action Details group and its component widgets
 */
public class ActionDetailsPart {
	private ViewPart vPart;
	private Composite localParent;
	private Group actDetails;
	private Label lblSearch;
	private Text search;
	private Label lblStartLineNo;
	private Text startLineNo;
	private Label lblEndLineNo;
	private Text endLineNo;

	public ActionDetailsPart(ViewPart fView, Composite lParent) {
		this.vPart = fView;
		this.localParent = lParent;
	}

	public void createActionDetailsGroup() {

		FileHandlerView fhView = (FileHandlerView) vPart;
		actDetails = new Group(localParent, SWT.NONE);
		actDetails.setText(Messages.lbl_group_action_details);
		FormLayout actLayout = new FormLayout();
		actLayout.marginWidth = 15;
		actLayout.marginHeight = 5;
		actDetails.setLayout(actLayout);

		FormData data = new FormData();
		data.top = new FormAttachment(fhView.getOperationActionPart().getActionLabel(), 15);
		data.left = new FormAttachment(fhView.getOperationActionPart().getActionLabel(), 0, SWT.LEFT);
		data.right = new FormAttachment(100, 0);
		actDetails.setLayoutData(data);

		lblSearch = new Label(actDetails, SWT.NULL);
		lblSearch.setText(Messages.lbl_search);
		search = new Text(actDetails, SWT.SINGLE | SWT.BORDER);

		data = new FormData();
		data.top = new FormAttachment(actDetails, 0, 5);
		lblSearch.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(lblSearch, 5);
		data.width = 250;
		search.setLayoutData(data);

		lblStartLineNo = new Label(actDetails, SWT.NULL);
		lblStartLineNo.setText(Messages.lbl_start_line_no);
		lblEndLineNo = new Label(actDetails, SWT.NULL);
		lblEndLineNo.setText(Messages.lbl_end_line_no);
		startLineNo = new Text(actDetails, SWT.SINGLE | SWT.BORDER);
		endLineNo = new Text(actDetails, SWT.SINGLE | SWT.BORDER);

		data = new FormData();
		data.left = new FormAttachment(search, 15);
		lblStartLineNo.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(lblStartLineNo, 5);
		data.width = 50;
		startLineNo.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(startLineNo, 15);
		lblEndLineNo.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(lblEndLineNo, 5);
		data.width = 50;
		endLineNo.setLayoutData(data);

	}

	public Group getActionDetails() {
		return actDetails;
	}

	public Composite getLocalParent() {
		return localParent;
	}

	public Label getLblSearch() {
		return lblSearch;
	}

	public Text getSearch() {
		return search;
	}

	public Label getLblStartLineNo() {
		return lblStartLineNo;
	}

	public Text getStartLineNo() {
		return startLineNo;
	}

	public Label getLblEndLineNo() {
		return lblEndLineNo;
	}

	public Text getEndLineNo() {
		return endLineNo;
	}

}
