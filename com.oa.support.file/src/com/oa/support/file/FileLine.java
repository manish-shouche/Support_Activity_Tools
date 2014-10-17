package com.oa.support.file;

/**
 * This is the holder class for matched items according to Action details specified 
 */
public class FileLine {
	private String lineno;
	private byte[] line;

	public String getLineno() {
		return lineno;
	}

	public void setLineno(String lineno) {
		this.lineno = lineno;
	}

	public byte[] getLine() {
		return line;
	}

	public void setLine(byte[] line) {
		this.line = line;
	}

}
