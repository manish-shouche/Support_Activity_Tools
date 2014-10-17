package com.oa.support.file.action;

import com.oa.support.file.ActionDetails;
import com.oa.support.file.FileDetails;
import com.oa.support.file.FileHandlerActionResult;
import com.oa.support.file.FileLine;
import com.oa.support.file.views.Messages;

/**
 * This class executes the Show 'n' number of lines from start of a file Action
 */
public class ShowLinesStartAction extends FileHandlerAction {

	@Override
	public FileHandlerActionResult execute(FileDetails fd, ActionDetails ad) throws Exception {
		this.fDetails = fd;
		if (fd.getRemoteFile()) {
			String filename = fd.getFilepath();
			int startLineNo = ad.getStartLine();
			this.remoteCommand = "head -"+startLineNo+" " +filename;
			setRemoteInputStream();
		} else {
			setLocalInputStream();
		}
		int nRead;
		ShowLinesStartThread lastThrd = null;

		while ((nRead = rbch.read(bb)) != -1) {
			if (stopAction) {
				return null;
			}

			bb.limit(nRead);
			bb.rewind();
			if (threadList.size() > 0) {
				lastThrd = (ShowLinesStartThread) threadList.getLast();
			}

			if (lastThrd != null) {
				lastThrd.join();
				if (lastThrd.completed == true) {
					break;
				}
				if (lastThrd.exception == true) {
					throw new Exception(Messages.msg_multithread_exception);
				}
				this.lastThrdRemaining = lastThrd.remainingBytes;
			}

			byte[] bbArray = new byte[nRead];
			bb.get(bbArray);

			ShowLinesStartThread thread = new ShowLinesStartThread(this, bbArray, ad, this.lastThrdRemaining);
			thread.setPriority(threadPriority);
			
			threadList.add(thread);

			thread.start();

			this.lastThrdRemaining = null;

			if (lastThrd != null) {
				threadList.removeFirst();
			}

			bb.clear();

		}

		if (threadList.size() > 0) {
			lastThrd = (ShowLinesStartThread) threadList.getLast();
		}
		if (lastThrd != null) {
			lastThrd.join();
			if (lastThrd.remainingBytes != null) {
				FileLine fLine = new FileLine();
				fLine.setLineno(String.format("%1$" + FileHandlerAction.leftPadSpaces + "s", linecount));
				fLine.setLine(lastThrd.remainingBytes);
				this.foundList.add(fLine);
			}
			threadList.removeFirst();
		}

		fhaResult = createResult();
		return fhaResult;

	}

}
