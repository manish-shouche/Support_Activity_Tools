Support_Activity_Tools
========================

A) File Handler Eclipse Plugin
===============================
Introduction
-------------
1. Eclipse is not able to open large files (>50 MB). All the editors, internal or external,  has to open the file first for any search. Most of the times we do not need to know all the contents of a text or log file for failure or any other analysis. We only need to know some part in between.  Hence the requirement to limit the file size even before it is opened.
2. We cannot connect to a text or log file on linux machine from Eclipse installed on Windows machine and vice versa. One has to connect through PUTTY/SFTP tools to linux and then analyse the file. Also analysis of these files require knowledge of vi editor / linux commands. This has considerable amount of switching time for  developer  as he has to move from his Eclipse workspace, perform the file analysis and then get back to his development activity.
3. In support activity, developer needs to monitor log files on continuous basis to check for errors / exceptions.

Features
---------
1. After installing the plugin, invoke the plugin by selecting – Window -> Show View -> Other… -> Support Activity Tools -> File Handler
2. The plugin has a View and the Editor which works as below - 

The View has below mentioned widgets for selecting the action - 

a)	Operation: 
i>	Open a File – To view complete or a part of a file
ii>	Search in a File – For a case sensitive search in a file

b)	 Action: 
Actions for “Open a File” operation
i>	Show number of lines from start of file
ii>	Show number of lines from end of file
iii>	Show number of lines in between of file
iv>	Show complete file (Do not select for large file)

Actions for “Search in a File” operation
i>	Find all in file
ii>	Find first occurrence in file
iii>	Find last occurrence in file
iv>	Count total number of lines

c)	Action Details: 
The below fields are to be entered as per Operation / Action selected
i>	Search – The case sensitive search string
ii>	Start Line Number – The start  line number from where the file is to be shown
iii>	End Line Number – The end  line number till where the file is to be shown

d)	File Details:
The below fields are to be entered 
i>	Local – To be selected for local file operation
ii>	Remote – To be selected for remote file operation
iii>	Server – To be entered for remote file, the hostname of remote server
iv>	User – The user name for SFTP connection to the server
v>	Password – The password for user name entered
vi>	File – The absolute file path, e.g., d:\logs\x.log or /opt/logs/x.log
vii>	Browse – Is enabled only for selecting local file

e)	Execute / Cancel:
i>	Execute – Executes the action selected in multi-threaded environment
ii>	Cancel – Terminates the currently executing action

f)	Execution Status: 
Shows the current status of the action that is executing. It also shows the time taken by the action for its execution

The Editor is invoked to display the results of action selected in the View and has below components -

i>	Package Explorer View – A ‘TempResultStorageFile.txt’ file is created / overwritten for every action executed. The file is located in the workspace project of ‘FileHandler/tempFiles’. This new workspace project gets created if it is not existing

ii>	Text Editor – The ‘TempResultStorageFile.txt’ created is programmatically opened in the default Eclipse Editor. The first line displays the file name alongwith the complete path. Following the filename, are the result lines as per the action executed. The first 9 characters are the line numbers followed by a colon(:) and then the line contents. The delimiter used while selecting line is ‘\n’. All the Eclipse Editor features of Copy, Paste, Save As, Block Select Mode, etc can be used on this file for further analysis of the file.

Benefits
---------
1. Working with local and remote files for analysis in the same Eclipse window while simultaneously working with the development work.  A 750 MB local file can be parsed and processed for searching in 18 secs and remote file depends on connection speed but it is faster as getting the lines from remote file is done using unix commands, if remote server is a Unix/Linix machine.
2. Working with large log and data files in Eclipse only. All the search and other actions are performaed in background while the user can continue working on his other tasks in Eclipse.
3. Plugin supports i18n.
4. Built-in features of Eclipse editors like Block Select mode can be used to quickly insert the data in Excel file.
5. Organizations can achieve a better productivity by asking development resources to carry out monitoring and vice versa.
6. Organizations do not need Unix / Linux resources or resources to know the vi, linux commands for monitoring large files

Limitations
------------
1. Not as fast as Vi, grep.
2. Cannot search multiple files and folders.
3. Supports only files delimited with '\n'. Need to change the configuration code if the file is delimited with some other character
4. Supported connection for remote files is sftp.
5. Left padding of line nos till 9 digits only.
6. Case sensitive search only.

B) File Monitor Eclipse Plugin
================================
Work is in progress for creating the same. The last changed file contents will be fetched and displayed in the View. This process will continue running in the background thread.

C) DB Query Processor Eclipse Plugin
======================================
Need to check if this needs to be implemented as we already have Eclipse plugin for the same which works like TOAD, SQL Developer.


