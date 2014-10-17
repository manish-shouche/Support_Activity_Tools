package com.oa.support.file.action;

import com.oa.support.file.views.Messages;

/**
 * This is the Abstract Factory implementation for FileHandlerAction
 */
public class FileHandlerActionFactory {
	
	/**
	 * Returns the FileHandlerAction as per selected Action
	 * @param action The selected Action
	 * @return FileHandlerAction
	 */
	public FileHandlerAction getFileHandlerAction(String action) {
		
		FileHandlerAction fhAction = null;
		if(action.equals(Messages.opt_action_find_all)){
			fhAction = new FindAllAction();
		} else if(action.equals(Messages.opt_action_find_total_lines_count)) {
			fhAction = new TotalLineCountAction();
		} else if(action.equals(Messages.opt_action_find_first_from_start)) {
			fhAction = new FindFirstAction();
		} else if(action.equals(Messages.opt_action_find_last_from_start)) {
			fhAction = new FindLastAction();
		} else if(action.equals(Messages.opt_action_no_of_lines_from_start)) {
			fhAction = new ShowLinesStartAction();
		} else if(action.equals(Messages.opt_action_no_of_lines_between)) {
			fhAction = new ShowLinesBetweenAction();
		} else if(action.equals(Messages.opt_action_no_of_lines_from_end)) {
			fhAction = new ShowLinesEndAction();
		} else if(action.equals(Messages.opt_action_show_complete_file)) {
			fhAction = new ShowCompleteAction();
		}
		
		return fhAction;
	}

}
