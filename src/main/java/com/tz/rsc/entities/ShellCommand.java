package com.tz.rsc.entities;

public class ShellCommand {

	public String searchCommand;
	public String searchString;
	public String filePath;
	public String archivePath;
	public String fileName;
	public boolean searchInArchive;
	public int timeoutSeconds=10;
	public int linesAfter=1;
	public int linesBefore=1;
}
