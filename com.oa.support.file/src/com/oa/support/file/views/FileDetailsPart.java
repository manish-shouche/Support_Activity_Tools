package com.oa.support.file.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * This class displays the File Details group and its component widgets
 */
public class FileDetailsPart {
	private ViewPart vPart;
	private Composite localParent;
	private Group fDetails;
	private boolean remoteFile;
	private Text serverText;
	private Text userText;
	private Text passwordText;
	private Text filenameText;
	
	public FileDetailsPart(ViewPart fView, Composite lParent) {
		this.vPart = fView;
		this.localParent = lParent;
	}
	
	public void createFileDetailsGroup() {

		FileHandlerView fhView = (FileHandlerView) vPart;
		
		fDetails = new Group(localParent, SWT.NONE);
		fDetails.setText(Messages.lbl_group_file_details);
		FormLayout fLayout = new FormLayout();
		fLayout.marginWidth = 15;
		fLayout.marginHeight = 5;
		fDetails.setLayout(fLayout);

		FormData data = new FormData();
		data.top = new FormAttachment(fhView.getActionDetailsPart().getActionDetails(), 10);
		data.left = new FormAttachment(fhView.getActionDetailsPart().getActionDetails(), 0, SWT.LEFT);
		data.right = new FormAttachment(100, 0);
		fDetails.setLayoutData(data);

		Button[] radioButton = new Button[2];
		radioButton[0] = new Button(fDetails, SWT.RADIO);
		radioButton[0].setSelection(true);
		radioButton[0].setText("Local");
	 
		radioButton[1] = new Button(fDetails, SWT.RADIO);
		radioButton[1].setText("Remote");
		
		Label server = new Label(fDetails, SWT.NULL);
		server.setText(Messages.lbl_server);
		
		Label user = new Label(fDetails, SWT.NULL);
		user.setText(Messages.lbl_user);
		
		Label password = new Label(fDetails, SWT.NULL);
		password.setText(Messages.lbl_password);
		
		Label filename = new Label(fDetails, SWT.NULL);
		filename.setText(Messages.lbl_file);
		
		final Text serverText = new Text(fDetails, SWT.SINGLE | SWT.BORDER);
		final Text userText = new Text(fDetails, SWT.SINGLE | SWT.BORDER);
		final Text passwordText = new Text(fDetails, SWT.SINGLE | SWT.BORDER);
		passwordText.setEchoChar('*');
		final Text filenameText = new Text(fDetails, SWT.SINGLE | SWT.BORDER);
		
		
		this.serverText = serverText;
		this.userText = userText;
		this.passwordText = passwordText;
		this.filenameText = filenameText;
		
		final Button browse = new Button(fDetails, SWT.PUSH);
		browse.setText(Messages.lbl_browse_btn);

		
		data = new FormData();
		data.top = new FormAttachment(fDetails, 0, 5);
		radioButton[0].setLayoutData(data);
		radioButton[0].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				remoteFile = false;
				serverText.setText("");
				serverText.setEnabled(false);
				userText.setText("");
				userText.setEnabled(false);
				passwordText.setText("");
				passwordText.setEnabled(false);
				filenameText.setText("");
				browse.setEnabled(true);
			}
		});
		
		data = new FormData();
		data.top = new FormAttachment(fDetails, 0, 5);
		data.left = new FormAttachment(radioButton[0], 75);
		radioButton[1].setLayoutData(data);
		radioButton[1].addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				remoteFile = true;
				serverText.setText("");
				serverText.setEnabled(true);
				userText.setText("");
				userText.setEnabled(true);
				passwordText.setText("");
				passwordText.setEnabled(true);
				filenameText.setText("");
				browse.setEnabled(false);
			}
		});
		
		data = new FormData();
		data.top = new FormAttachment(radioButton[0],7);
		server.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(radioButton[0],7);
		data.left = new FormAttachment(server, 5);
		data.width = 120;
		serverText.setLayoutData(data);
		serverText.setEnabled(false);

		data = new FormData();
		data.top = new FormAttachment(radioButton[0],7);
		data.left = new FormAttachment(serverText, 15);
		user.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(radioButton[0],7);
		data.left = new FormAttachment(user, 5);
		data.width = 120;
		userText.setLayoutData(data);
		userText.setEnabled(false);
		
		data = new FormData();
		data.top = new FormAttachment(radioButton[0],7);
		data.left = new FormAttachment(userText, 15);
		password.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(radioButton[0],7);
		data.left = new FormAttachment(password, 5);
		data.width = 120;
		passwordText.setLayoutData(data);
		passwordText.setEnabled(false);

		data = new FormData();
		data.top = new FormAttachment(server, 15);
		filename.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(server, 15);
		data.left = new FormAttachment(serverText, 0, SWT.LEFT);
		data.width = 120;
		filenameText.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(server, 15);
		data.left = new FormAttachment(filenameText, 15);
		browse.setLayoutData(data);
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog fileName = new FileDialog(localParent.getShell());
				filenameText.setText(fileName.open());
			}
		});
	}

	public Group getFileDetails() {
		return fDetails;
	}

	public Text getServerText() {
		return serverText;
	}

	public Text getUserText() {
		return userText;
	}

	public Text getPasswordText() {
		return passwordText;
	}

	public Text getFilenameText() {
		return filenameText;
	}
	
	public boolean getRemoteFile() {
		return remoteFile;
	}
}
