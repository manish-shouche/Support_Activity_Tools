package com.oa.support.file.action;

import java.nio.ByteBuffer;

import com.oa.support.file.FileLine;

/**
 * This class executes the Show Complete file Action in a separate thread
 */
public class ShowCompleteThread extends Thread {
	private FileHandlerAction act;
	public boolean completed = false;
	public boolean exception = false;
	byte[] bArray;
	boolean skip = false;
	byte[] lineArr = null;
	byte[] remainingBytes = null;
	byte[] lastThrdRemainingBytes = null;
	ByteBuffer lBBuffer;
	int buffCapacity = 0;

	public ShowCompleteThread() {

	}

	public ShowCompleteThread(FileHandlerAction action, byte[] arry, byte[] lastThrdRemain) {
		this.act = action;
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

			int lineStartIdx = 0;
			int lineEndIdx = 0;
			int length = 0;

			while (lBBuffer.hasRemaining()) {
				if (FileHandlerAction.newLineByte == lBBuffer.get()) {
					act.linecount++;
					lineStartIdx = lBBuffer.position();
				} else {
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
