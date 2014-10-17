package com.oa.support.file.views;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.oa.support.file.ActionDetails;
import com.oa.support.file.Activator;
import com.oa.support.file.FileDetails;
import com.oa.support.file.FileHandlerActionResult;
import com.oa.support.file.action.FileHandlerAction;
import com.oa.support.file.action.FileHandlerActionFactory;

/**
 * This class displays the Buttons and implements its respective adapters
 */
public class ButtonsPart {
	ViewPart vPart;
	Composite localParent;
	Label executionStatus;

	public ButtonsPart(ViewPart fView, Composite lParent) {
		this.vPart = fView;
		this.localParent = lParent;
	}

	public void createButtons() {

		final FileHandlerView fhView = (FileHandlerView) vPart;

		final Button execute = new Button(localParent, SWT.PUSH);
		execute.setText(Messages.lbl_execute_btn);

		FormData data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.top = new FormAttachment(fhView.getFileDetailsPart().getFileDetails(), 10);
		execute.setLayoutData(data);
		execute.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				execute.setEnabled(false);
				FileHandlerAction.stopAction = false;
				Runnable runnable = new Runnable() {
					FileHandlerAction fhAction = null;

					public void run() {
						final long starttime = System.currentTimeMillis();
						try {
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_start_action_execution));
							FileDetails fDetails = createFileDetails(fhView.getFileDetailsPart());
							ActionDetails aDetails = createActionDetails(fhView.getActionDetailsPart());
							FileHandlerActionFactory fhaFactory = new FileHandlerActionFactory();
							fhAction = fhaFactory.getFileHandlerAction(fhView.getOperationActionPart().getSelectedAction());
							FileHandlerActionResult result = fhAction.execute(fDetails, aDetails);
							openEditorForResult(result);
							if(FileHandlerAction.stopAction) {
								Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_cancel_action_execution));
								FileHandlerAction.stopAction = false;
							} else {
								Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_successful_exec_status));
							}

						} catch (FileNotFoundException fnfe) {
							fnfe.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "FileNotFoundException", fnfe);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_file_not_found_exception + " - " + fnfe.getLocalizedMessage()));
						} catch (IOException ioe) {
							ioe.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "IOException", ioe);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_io_exception + " - " + ioe.getLocalizedMessage()));
						} catch (ExecutionException ee) {
							ee.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "ExecutionException", ee);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_multithread_executor_exception + " - " + ee.getLocalizedMessage()));
						} catch (InterruptedException ie) {
							ie.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "InterruptedException", ie);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_multithread_exception + " - " + ie.getLocalizedMessage()));
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "NumberFormatException", nfe);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_number_format_exception));
						} catch (JSchException jsche) {
							jsche.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "JSchException", jsche);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_remote_access_exception + " - " + jsche.getLocalizedMessage()));  
				        } catch (SftpException sftpe) {
				        	sftpe.printStackTrace();
				        	Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "SftpException", sftpe);
							StatusManager.getManager().handle(errStatus);
				        	Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_remote_access_exception + " - " + sftpe.getLocalizedMessage()));
				        }  catch (CoreException ce) {
				        	ce.printStackTrace();
				        	Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "CoreException", ce);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_eclipse_core_exception + " - " + ce.getLocalizedMessage()));
						} catch (Exception e) {
							e.printStackTrace();
							Status errStatus = new Status(Status.ERROR, Activator.PLUGIN_ID, "Exception", e);
							StatusManager.getManager().handle(errStatus);
							Display.getDefault().asyncExec(new UpdateUIStatus(executionStatus, Messages.lbl_execution_status + Messages.msg_exception + " - " + e.getLocalizedMessage()));
						} finally {
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									try {
										execute.setEnabled(true);
										if (fhAction != null && fhAction.getChannel() != null) {
											fhAction.getChannel().close();
										}
										if (fhAction != null && fhAction.getInputStream() != null) {
											fhAction.getInputStream().close();
										}
										if (fhAction != null && fhAction.getRFileSftpChannel() != null) {
											fhAction.getRFileSftpChannel().exit();
										}
										if (fhAction != null && fhAction.getRFileChannel() != null) {
											fhAction.getRFileChannel().disconnect();
										}
										if (fhAction != null && fhAction.getRFileSession() != null) {
											fhAction.getRFileSession().disconnect();
										}
									} catch (Exception e) {
										executionStatus.setText(Messages.lbl_execution_status + Messages.msg_exception + " - " + e.getLocalizedMessage());
									}
									final long endtime = System.currentTimeMillis();
									String statusMsg = executionStatus.getText();
									executionStatus.setText(statusMsg + Messages.msg_time_taken + (endtime - starttime));								}
									
							});
							
						}
					}
					
				};
				new Thread(runnable).start();
			}
		});

		Button cancel = new Button(localParent, SWT.PUSH);
		cancel.setText(Messages.lbl_cancel_btn);

		data = new FormData();
		data.left = new FormAttachment(execute, 10);
		data.top = new FormAttachment(fhView.getFileDetailsPart().getFileDetails(), 10);
		cancel.setLayoutData(data);

		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileHandlerAction.stopAction = true;
				if (!execute.isEnabled()) {
					execute.setEnabled(true);
				}
			}
		});

		executionStatus = new Label(localParent, SWT.WRAP);
		executionStatus.setText(Messages.lbl_execution_status);
		data = new FormData();
		data.top = new FormAttachment(execute, 8);
		data.width = 740;
		executionStatus.setLayoutData(data);
	}

	private FileDetails createFileDetails(final FileDetailsPart fdp) throws Exception {
		final FileDetails fd = new FileDetails();
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				fd.setRemoteFile(fdp.getRemoteFile());
				fd.setServer(fdp.getServerText().getText());
				fd.setUser(fdp.getUserText().getText());
				fd.setPassword(fdp.getPasswordText().getText());
				fd.setFilepath(fdp.getFilenameText().getText());
			}
		});
		return fd;
	}

	private ActionDetails createActionDetails(final ActionDetailsPart adp) {
		final ActionDetails ad = new ActionDetails();
		Display.getDefault().syncExec(new Runnable() {
			public void run() {

				ad.setSearchStr(adp.getSearch().getText());
				if (adp.getStartLineNo() != null && !adp.getStartLineNo().getText().trim().equals("")) {
					ad.setStartLine(Integer.parseInt(adp.getStartLineNo().getText().trim()));
				}
				if (adp.getEndLineNo() != null && !adp.getEndLineNo().getText().trim().equals("")) {
					ad.setEndLine(Integer.parseInt(adp.getEndLineNo().getText().trim()));
				}

			}
		});

		return ad;
	}

	private void openEditorForResult(FileHandlerActionResult fhaResult) throws Exception {

		final FileHandlerView fhView = (FileHandlerView) vPart;

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IProject fileHandlerProject = workspaceRoot.getProject("FileHandler"); //$NON-NLS-1$
		if (!fileHandlerProject.exists()) {
			fileHandlerProject.create(null);
		}
		fileHandlerProject.open(null);
		IFolder tempfiles = fileHandlerProject.getFolder("tempfiles"); //$NON-NLS-1$
		if (!tempfiles.exists()) {
			tempfiles.create(true, true, null);
		}
		if (fhaResult != null) {
			InputStream source = new ByteArrayInputStream(fhaResult.getResultStr().toString().getBytes(FileHandlerAction.charset));
			final IFile file = tempfiles.getFile("TempResultStorageFile.txt"); //$NON-NLS-1$
			if (!file.exists()) {
				file.create(source, true, null);
			} else {
				file.setContents(source, true, false, null);
			}
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					try {
						IDE.openEditor(fhView.getSite().getPage(), file);
					} catch (Exception e) {
						executionStatus.setText(Messages.lbl_execution_status + Messages.msg_eclipse_core_exception);
					}

				}
			});
		}
	}
}

class UpdateUIStatus implements Runnable {
	String statusMsg;
	Label executionStatus;

	UpdateUIStatus(Label lbl, String msg) {
		this.executionStatus = lbl;
		this.statusMsg = msg;
	}

	@Override
	public void run() {
		executionStatus.setText(statusMsg);
	}
}
