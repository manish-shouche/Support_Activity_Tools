package com.oa.support.file;

/**
 * This is the holder class for FileDetailsPart
 */
public class FileDetails {
	private boolean remoteFile;
	private String server;
	private String user;
	private String password;
	private String filepath;
	
	public boolean getRemoteFile() {
		return remoteFile;
	}
	public void setRemoteFile(boolean remoteFile) {
		this.remoteFile = remoteFile;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
