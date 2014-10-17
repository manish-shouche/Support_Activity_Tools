package com.oa.support.file.action;

import java.nio.ByteBuffer;

import com.oa.support.file.ActionDetails;
import com.oa.support.file.FileLine;

/**
 * This class executes the Find Last in a file Action in a separate thread
 */
public class FindLastThread extends Thread {
	private FileHandlerAction act;
	private ActionDetails actDetails;
	public boolean completed = false;
	public boolean exception = false;
	byte[] bArray;
	boolean skip = false;
	byte[] lineArr = null;
	byte[] remainingBytes = null;
	byte[] lastThrdRemainingBytes = null;
	ByteBuffer lBBuffer;
	int buffCapacity = 0;

	public FindLastThread() {

	}

	public FindLastThread(FileHandlerAction action, byte[] arry, ActionDetails ad, byte[] lastThrdRemain) {
		this.act = action;
		this.actDetails = ad;
		this.bArray = arry;
		this.lastThrdRemainingBytes = lastThrdRemain;
	}

	@Override
	public void run() {
		try {

			if (lastThrdRemainingBytes != null && lastThrdRemainingBytes.length > 0) {
				buffCapacity = lastThrdRemainingBytes.length + bArray.length;
				this.lBBuffer = ByteBuffer.allocate(buffCapacity);
				lBBuffer.put(lastThrdRemainingBytes, 0, lastThrdRemainingBytes.length);
				lBBuffer.put(bArray, 0, bArray.length);
			} else {
				this.lBBuffer = ByteBuffer.wrap(bArray);
			}
			lBBuffer.rewind();
			
			byte[] searchStrArr = actDetails.getSearchStr().getBytes();
			final int srcArLen = searchStrArr.length;
			int matchIdx = 0;
			int lineStartIdx = 0;
			int lineEndIdx = 0;
			int length = 0;
			byte ch1;

			while (lBBuffer.hasRemaining()) {
				ch1 = lBBuffer.get();
				if (FileHandlerAction.newLineByte == ch1) {
					act.linecount++;
					lineStartIdx = lBBuffer.position();
				} else if (searchStrArr[matchIdx] == ch1) {
					matchIdx++;
					if (matchIdx == srcArLen) {
						while (true) {
							if (!lBBuffer.hasRemaining()) {
								lineEndIdx = lBBuffer.position();
								length = lineEndIdx - lineStartIdx;
								remainingBytes = new byte[length];
								lBBuffer.position(lineStartIdx);
								lBBuffer.get(remainingBytes, 0, length);
								skip = true;
								break;
							} else if (FileHandlerAction.newLineByte == lBBuffer.get()) {
								lineEndIdx = lBBuffer.position() - 1;
								break;
							}
						}
						if (skip) {
							break;
						}

						length = lineEndIdx - lineStartIdx;
						lineArr = new byte[length];
						lBBuffer.position(lineStartIdx);
						lBBuffer.get(lineArr, 0, length);

						FileLine fLine = new FileLine();
						fLine.setLineno(String.format("%1$" + FileHandlerAction.leftPadSpaces + "s", act.linecount));
						fLine.setLine(lineArr);
						act.foundList.add(fLine);
						matchIdx = 0;
					}
				} else {
					matchIdx = 0;
				}

				if (!lBBuffer.hasRemaining()) {
					lineEndIdx = lBBuffer.position();
					length = lineEndIdx - lineStartIdx;
					remainingBytes = new byte[length];
					lBBuffer.position(lineStartIdx);
					lBBuffer.get(remainingBytes, 0, length);
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
