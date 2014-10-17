package com.oa.support.file.action;

import com.oa.support.file.ActionDetails;
import com.oa.support.file.FileDetails;
import com.oa.support.file.FileHandlerActionResult;
import com.oa.support.file.FileLine;
import com.oa.support.file.views.Messages;

/**
 * This class executes the Show 'n' number of lines from end of a file Action
 */
public class ShowLinesEndAction extends FileHandlerAction {

	@Override
	public FileHandlerActionResult execute(FileDetails fd, ActionDetails ad) throws Exception {
		this.fDetails = fd;
		if (fd.getRemoteFile()) {
			String filename = fd.getFilepath();
			this.remoteCommand = "wc -l " + filename;
			setRemoteInputStream();
		} else {
			setLocalInputStream();
		}
		int nRead;
		TotalLineCountThread lCountThrd = null;

		while ((nRead = rbch.read(bb)) != -1) {
			if (stopAction) {
				return null;
			}

			bb.limit(nRead);
			bb.rewind();
			if (threadList.size() > 0) {
				lCountThrd = (TotalLineCountThread) threadList.getLast();
			}

			if (lCountThrd != null) {
				lCountThrd.join();
				if (lCountThrd.completed == true) {
					break;
				}
				if (lCountThrd.exception == true) {
					throw new Exception(Messages.msg_multithread_exception);
				}
			}

			byte[] bbArray = new byte[nRead];
			bb.get(bbArray);

			TotalLineCountThread thread = new TotalLineCountThread(this, bbArray, fd.getRemoteFile());
			thread.setPriority(threadPriority);
			
			threadList.add(thread);

			thread.start();

			this.lastThrdRemaining = null;

			if (lCountThrd != null) {
				threadList.removeFirst();
			}

			bb.clear();

		}

		if (threadList.size() > 0) {
			lCountThrd = (TotalLineCountThread) threadList.getLast();
		}
		if (lCountThrd != null) {
			lCountThrd.join();
			threadList.removeFirst();
		}
		threadList.clear();
		bb.clear();
		
		int endLine = 0;
		if (fd.getRemoteFile()) {
			FileLine fl = this.foundList.get(0);
			byte [] bArr = fl.getLine();
			String line = new String(bArr);
			String [] strArr = line.split("\\ ");
			endLine = Integer.parseInt(strArr[0].trim());
			this.foundList.clear();
		} else {
			endLine = linecount;
		}
		
		int startLine = (endLine - ad.getEndLine()) + 1;
		
		if (fd.getRemoteFile()) {
			String filename = fd.getFilepath();
			int endLineNo = ad.getEndLine();
			this.remoteCommand = "tail -"+endLineNo+" " +filename;
			setRemoteInputStream();
			linecount = startLine;
		} else {
			setLocalInputStream();
			linecount = 1;
		}
		
		ShowLinesEndThread lastThrd = null;

		while ((nRead = rbch.read(bb)) != -1) {
			if (stopAction) {
				return null;
			}

			bb.limit(nRead);
			bb.rewind();
			if (threadList.size() > 0) {
				lastThrd = (ShowLinesEndThread) threadList.getLast();
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

			ShowLinesEndThread thread = new ShowLinesEndThread(this, bbArray, this.lastThrdRemaining, startLine, endLine);
			thread.setPriority(Thread.NORM_PRIORITY + 1);
			
			threadList.add(thread);

			thread.start();

			this.lastThrdRemaining = null;
			
			if (lastThrd != null) {
				threadList.removeFirst();
			}

			bb.clear();

		}

		if (threadList.size() > 0) {
			lastThrd = (ShowLinesEndThread) threadList.getLast();
		}
		if (lastThrd != null) {
			lastThrd.join();
			if(lastThrd.remainingBytes != null) {
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
