package com.oa.support.file.views;

import org.eclipse.osgi.util.NLS;

/**
 * This class lists all the label/string constants used in plug-in
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.oa.support.file.views.messages"; //$NON-NLS-1$
	public static String lbl_action;
	public static String lbl_group_action_details;
	public static String lbl_search;
	public static String lbl_lines_from_start;
	public static String lbl_lines_from_end;
	public static String lbl_start_line_no;
	public static String lbl_end_line_no;
	public static String lbl_group_file_details;
	public static String lbl_server;
	public static String lbl_user;
	public static String lbl_password;
	public static String lbl_file;
	public static String lbl_browse_btn;
	public static String lbl_execute_btn;
	public static String lbl_cancel_btn;
	public static String lbl_operation;
	public static String lbl_execution_status;
	public static String msg_line_count;
	public static String msg_start_action_execution;
	public static String msg_cancel_action_execution;
	public static String msg_successful_exec_status;
	public static String msg_file_not_found_exception;
	public static String msg_io_exception;
	public static String msg_remote_access_exception;
	public static String msg_multithread_executor_exception;
	public static String msg_multithread_exception;
	public static String msg_eclipse_core_exception;
	public static String msg_number_format_exception;
	public static String msg_exception;
	public static String msg_time_taken;
	public static String opt_opr_open_file;
	public static String opt_opr_search_file;
	public static String opt_select_operation;
	public static String opt_default;
	public static String opt_action_no_of_lines_from_start;
	public static String opt_action_no_of_lines_from_end;
	public static String opt_action_no_of_lines_between;
	public static String opt_action_show_complete_file;
	public static String opt_action_find_all;
	public static String opt_action_find_first_from_start;
	public static String opt_action_find_last_from_start;
	public static String opt_action_find_total_lines_count;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
