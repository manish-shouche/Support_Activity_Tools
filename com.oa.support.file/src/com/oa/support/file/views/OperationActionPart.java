package com.oa.support.file.views;

/**
 * This class displays the Operation/Action Lists and implements the respective adapters
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class OperationActionPart {
	ViewPart vPart;
	Composite localParent;
	Label actLabel;
	String selectedAction;

	public OperationActionPart(ViewPart fView, Composite lParent) {
		this.vPart = fView;
		this.localParent = lParent;
	}

	public void createOprActionList() {

		Label oprLabel = new Label(localParent, SWT.NONE);
		oprLabel.setText(Messages.lbl_operation);
		final Combo oprLabelcmb = new Combo(localParent, SWT.NONE);
		oprLabelcmb.setItems(new String[] { Messages.opt_default, Messages.opt_opr_open_file, Messages.opt_opr_search_file });
		oprLabelcmb.select(0);

		actLabel = new Label(localParent, SWT.NONE);
		actLabel.setText(Messages.lbl_action);
		final Combo actLabelcmb = new Combo(localParent, SWT.NONE);
		actLabelcmb.setItems(new String[] { Messages.opt_select_operation });
		actLabelcmb.select(0);

		FormData data = new FormData();
		data.top = new FormAttachment(oprLabelcmb, 0, SWT.CENTER);
		oprLabel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(oprLabel, 5);
		data.width = 250;
		oprLabelcmb.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(actLabelcmb, 0, SWT.CENTER);
		actLabel.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(oprLabelcmb, 5);
		data.left = new FormAttachment(oprLabelcmb, 0, SWT.LEFT);
		data.width = 250;
		actLabelcmb.setLayoutData(data);

		oprLabelcmb.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				actLabelcmb.setItems(handleOperationSelection(oprLabelcmb.getItem(oprLabelcmb.getSelectionIndex())));
				actLabelcmb.select(0);
			}
		});

		actLabelcmb.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleActionSelection(actLabelcmb.getItem(actLabelcmb.getSelectionIndex()));
			}
		});

	}

	private String[] handleOperationSelection(String selectedItem) {

		if (selectedItem.equals(Messages.opt_default)) {
			return new String[] { Messages.opt_select_operation };

		} else if (selectedItem.equals(Messages.opt_opr_open_file)) {
			return new String[] { Messages.opt_default, 
					Messages.opt_action_no_of_lines_from_start, 
					Messages.opt_action_no_of_lines_from_end, 
					Messages.opt_action_no_of_lines_between, 
					Messages.opt_action_show_complete_file };

		} else if (selectedItem.equals(Messages.opt_opr_search_file)) {
			return new String[] { Messages.opt_default, 
					Messages.opt_action_find_all, 
					Messages.opt_action_find_first_from_start, 
					Messages.opt_action_find_last_from_start, 
					Messages.opt_action_find_total_lines_count };

		} else {
			return new String[] { Messages.opt_select_operation };
		}

	}

	private void handleActionSelection(String selectedItem) {
		this.selectedAction = selectedItem;
		FileHandlerView fhView = (FileHandlerView) vPart;
		
		if (selectedItem.equals(Messages.opt_action_find_all) || 
			selectedItem.equals(Messages.opt_action_find_first_from_start) ||
			selectedItem.equals(Messages.opt_action_find_last_from_start)) {
			
			fhView.getActionDetailsPart().getLblStartLineNo().setText(Messages.lbl_start_line_no);
			fhView.getActionDetailsPart().getStartLineNo().setText("");
			fhView.getActionDetailsPart().getStartLineNo().setEnabled(false);
			fhView.getActionDetailsPart().getLblEndLineNo().setText(Messages.lbl_end_line_no);
			fhView.getActionDetailsPart().getEndLineNo().setText("");
			fhView.getActionDetailsPart().getEndLineNo().setEnabled(false);
			fhView.getActionDetailsPart().getSearch().setText("");
			fhView.getActionDetailsPart().getSearch().setEnabled(true);

		} else if (selectedItem.equals(Messages.opt_action_no_of_lines_from_start)) {
			
			fhView.getActionDetailsPart().getLblStartLineNo().setText(Messages.lbl_lines_from_start);
			fhView.getActionDetailsPart().getStartLineNo().setText("");
			fhView.getActionDetailsPart().getStartLineNo().setEnabled(true);
			fhView.getActionDetailsPart().getLblEndLineNo().setText(Messages.lbl_lines_from_end);
			fhView.getActionDetailsPart().getEndLineNo().setText("");
			fhView.getActionDetailsPart().getEndLineNo().setEnabled(false);
			fhView.getActionDetailsPart().getSearch().setText("");
			fhView.getActionDetailsPart().getSearch().setEnabled(false);

		} else if (selectedItem.equals(Messages.opt_action_no_of_lines_from_end)) {
			
			fhView.getActionDetailsPart().getLblStartLineNo().setText(Messages.lbl_lines_from_start);
			fhView.getActionDetailsPart().getStartLineNo().setText("");
			fhView.getActionDetailsPart().getStartLineNo().setEnabled(false);
			fhView.getActionDetailsPart().getLblEndLineNo().setText(Messages.lbl_lines_from_end);
			fhView.getActionDetailsPart().getEndLineNo().setText("");
			fhView.getActionDetailsPart().getEndLineNo().setEnabled(true);
			fhView.getActionDetailsPart().getSearch().setText("");
			fhView.getActionDetailsPart().getSearch().setEnabled(false);

		} else if (selectedItem.equals(Messages.opt_action_no_of_lines_between)) {
			
			fhView.getActionDetailsPart().getLblStartLineNo().setText(Messages.lbl_start_line_no);
			fhView.getActionDetailsPart().getStartLineNo().setText("");
			fhView.getActionDetailsPart().getStartLineNo().setEnabled(true);
			fhView.getActionDetailsPart().getLblEndLineNo().setText(Messages.lbl_end_line_no);
			fhView.getActionDetailsPart().getEndLineNo().setText("");
			fhView.getActionDetailsPart().getEndLineNo().setEnabled(true);
			fhView.getActionDetailsPart().getSearch().setText("");
			fhView.getActionDetailsPart().getSearch().setEnabled(false);

		} else {
			fhView.getActionDetailsPart().getLblStartLineNo().setText(Messages.lbl_start_line_no);
			fhView.getActionDetailsPart().getStartLineNo().setText("");
			fhView.getActionDetailsPart().getStartLineNo().setEnabled(false);
			fhView.getActionDetailsPart().getLblEndLineNo().setText(Messages.lbl_end_line_no);
			fhView.getActionDetailsPart().getEndLineNo().setText("");
			fhView.getActionDetailsPart().getEndLineNo().setEnabled(false);
			fhView.getActionDetailsPart().getSearch().setText("");
			fhView.getActionDetailsPart().getSearch().setEnabled(false);
		}
		
	}
	
	public Label getActionLabel() {
		return actLabel;
	}
	
	public String getSelectedAction() {
		return selectedAction;
	}
}
