package com.oa.support.file;

import java.util.Comparator;

/**
 * This is the Comparator class for FileLine
 */
public class FileLineComparator implements Comparator<FileLine> {
	public int compare(FileLine arg0, FileLine arg1) {
		FileLine fl0 = (FileLine) arg0;
		FileLine fl1 = (FileLine) arg1;
		int lineNo0 = Integer.parseInt(fl0.getLineno().trim());
		int lineNo1 = Integer.parseInt(fl1.getLineno().trim());
		return lineNo0 - lineNo1;
	}

}
