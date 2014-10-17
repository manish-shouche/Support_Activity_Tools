package com.oa.support.file.action;

import java.nio.ByteBuffer;

import com.oa.support.file.FileLine;

/**
 * This class executes the Count total number of lines in a file Action in a
 * separate thread
 */
public class TotalLineCountThread extends Thread {

	private FileHandlerAction act;
	public boolean completed = false;
	public boolean exception = false;
	public boolean isRemoteFile = false;
	byte[] bArray;
	ByteBuffer lBBuffer;

	public TotalLineCountThread() {

	}

	public TotalLineCountThread(FileHandlerAction action, byte[] arry, boolean isRemoteFile) {
		this.act = action;
		this.bArray = arry;
		this.isRemoteFile = isRemoteFile;
	}

	@Override
	public void run() {
		try {

			this.lBBuffer = ByteBuffer.wrap(this.bArray);

			if(isRemoteFile) {
				int length = lBBuffer.remaining();
				byte [] lineArr = new byte[length];
				lBBuffer.get(lineArr, 0, length);
				
				FileLine fLine = new FileLine();
				fLine.setLineno(String.format("%1$" + FileHandlerAction.leftPadSpaces + "s", 1));
				fLine.setLine(lineArr);
				act.foundList.add(fLine);
			} else {
				while (lBBuffer.hasRemaining()) {
					if (FileHandlerAction.newLineByte == lBBuffer.get()) {
						act.linecount++;
					}
				}
			}
			

		} catch (Exception e) {
			this.exception = true;

		} finally {
			lBBuffer.clear();
			this.bArray = null;
		}
	}

}
