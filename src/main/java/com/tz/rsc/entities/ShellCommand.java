package com.tz.rsc.entities;

public class ShellCommand {

	public String searchCommand;
	public String searchString;
	public String logPath;
	public String archiveLogPath;
	public String logFileName;
	public String logFileNameExt;
	public String logFileNamePattern;
	public String fileDatePattern;
	public String fileDate;
	public boolean searchInArchive;
	public boolean includePattern;
	public int timeoutSeconds=10;
	public int linesAfter=1;
	public int linesBefore=1;
	public int rollingType=1;
}
