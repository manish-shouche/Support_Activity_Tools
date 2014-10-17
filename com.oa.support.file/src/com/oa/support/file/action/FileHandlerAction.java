package com.oa.support.file.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.LinkedList;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import com.oa.support.file.ActionDetails;
import com.oa.support.file.FileDetails;
import com.oa.support.file.FileHandlerActionResult;
import com.oa.support.file.FileLine;

/**
 * This is the Abstract class for any of the Action to be executed. The
 * subclasses need to implement the execute() method as per their Action
 */
public abstract class FileHandlerAction {
	public final static int remoteDefaultbArrSize = 1024 * 512;
	public final static int remoteDefaultPort = 22;
	public final static String remoteDefaultProtocol = "sftp";
	public final static int leftPadSpaces = 9;
	public final static byte newLineByte = '\n';
	public final static String charset = "UTF-8";
	public final int threadPriority = Thread.NORM_PRIORITY;
	public static boolean stopAction;
	public static boolean processResults;
	public String remoteCommand;

	ArrayList<FileLine> foundList = new ArrayList<FileLine>();
	InputStream is;
	ReadableByteChannel rbch;
	ByteBuffer bb;
	FileHandlerActionResult fhaResult;
	FileDetails fDetails;

	JSch jsch;
	Session session;
	ChannelExec channel;
	ChannelSftp sftpChannel;

	int linecount = 1;
	byte[] lastThrdRemaining = null;
	LinkedList <Thread> threadList = new LinkedList<Thread>();

	public abstract FileHandlerActionResult execute(FileDetails fd, ActionDetails ad) throws Exception;

	/**
	 * Initializes the InptStream and its channel for a local file
	 * 
	 * @throws Exception
	 *             Exception while creating InputStream and their channels
	 * 
	 */
	public void setLocalInputStream() throws Exception {
		FileInputStream f;
		FileChannel ch;
		f = new FileInputStream(fDetails.getFilepath().trim());
		ch = f.getChannel();
		int arrSize = getSize(ch.size());
		bb = ByteBuffer.allocateDirect(arrSize);
		is = f;
		rbch = Channels.newChannel(is);
	}

	/**
	 * Initializes the InptStream and its channel for a remote file Uses JSch
	 * for connecting to remote machine
	 * 
	 * @throws Exception
	 *             Exception while creating InputStream and their channels
	 * 
	 */
	public void setRemoteInputStream() throws Exception {
		jsch = new JSch();
		session = jsch.getSession(fDetails.getUser().trim(), fDetails.getServer().trim(), remoteDefaultPort);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setPassword(fDetails.getPassword());
		session.connect();
		channel = (ChannelExec) session.openChannel("exec");
		is = channel.getInputStream();
		//Status infoStatus = new Status(Status.INFO, Activator.PLUGIN_ID, "Remote Command set is '"+remoteCommand+"'");
		//StatusManager.getManager().handle(infoStatus);
		channel.setCommand(remoteCommand);
		channel.connect(); 
		rbch = Channels.newChannel(is);
		bb = ByteBuffer.allocateDirect(remoteDefaultbArrSize);

	}

	/**
	 * Returns the size required for byte array according to file channel length
	 * of local file
	 * 
	 * @param fileLength
	 *            Local file length
	 * @return Integer size to be used as byte array size
	 * 
	 */
	public int getSize(long fileLength) {
		int retSize = 0;
		long mb512 = 1024 * 1024 * 512;
		long mb = 1024 * 1024;
		long kb = 1024;
		if (fileLength < kb) {
			retSize = 256;
		} else if (fileLength > kb && fileLength < mb) {
			retSize = 1024 * 256;
		} else if (fileLength > mb && fileLength < mb512) {
			retSize = 1024 * 1024 * 8;
		} else if (fileLength > mb512) {
			retSize = 1024 * 1024 * 16;
		}
		return retSize;
	}

	/**
	 * Creates a String representation of matched items according to Action
	 * details specified It then sets this String in the FileHandlerActionResult
	 * 
	 * @return FileHandlerActionResult
	 * 
	 */
	public FileHandlerActionResult createResult() {
		StringBuffer sb = new StringBuffer();
		StringBuffer lresult = new StringBuffer(fDetails.getFilepath());
		final String colon = ":";
		FileHandlerActionResult lfhaResult = new FileHandlerActionResult();
		for (int i = 0; i < foundList.size(); i++) {
			FileLine fLine = foundList.get(i);
			String bArrStr = "";
			if (fLine.getLine() != null) {
				bArrStr = new String(fLine.getLine());
			}

			sb.delete(0, sb.length());
			sb.append("\n" + fLine.getLineno() + colon + bArrStr);
			lresult.append(sb);
		}
		lfhaResult.setResultStr(lresult);
		return lfhaResult;
	}

	public ReadableByteChannel getChannel() {
		return this.rbch;
	}

	public InputStream getInputStream() {
		return this.is;
	}

	public Channel getRFileChannel() {
		return this.channel;
	}

	public ChannelSftp getRFileSftpChannel() {
		return this.sftpChannel;
	}

	public Session getRFileSession() {
		return this.session;
	}

	public void addInFoundList(FileLine fl) {
		try {
			System.out.println("Inside adding to list");
			this.foundList.add(fl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
