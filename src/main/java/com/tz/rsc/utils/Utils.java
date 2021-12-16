package com.tz.rsc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tz.rsc.entities.ShellCommand;

public class Utils {
	
	public static final String HEADER_STRING="Authorization";
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String JWT_KEY="SecretKeyToGenJWTs";
	
	public static boolean isNullOrEmpty(String val) {
		if (val == null || val.isEmpty() || val.trim().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public static boolean commandIsSupported(String val) {
		if (val.startsWith("grep") || 
				val.startsWith("awk") || 
				val.startsWith("nawk")) {
			return true;
		}
		
		return false;
	}
	
	private static String getDateString(String date_s, String format) throws ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date;
		if(isNullOrEmpty(date_s)) {
			date = new Date(); 
		} else {
			date = dt.parse(date_s); 
		}
		SimpleDateFormat dt1 = new SimpleDateFormat(format);
		return dt1.format(date);
	}
	
	public static String prepareCommand(ShellCommand command, boolean isArchive) throws ParseException {
		String cmd="";
		String fileName = "";
		
		if(command.includePattern) {
			fileName = command.logFileNamePattern.replace("[DATE]", getDateString(command.fileDate, command.fileDatePattern))
					.replace("[FILENAME]", command.logFileName).replace("[EXT]", command.logFileNameExt);
			
		} else {
			fileName = isNullOrEmpty(command.logFileName) ? "*." + command.logFileNameExt : command.logFileName + "." + command.logFileNameExt;
		}
		
		if(!command.logFileName.contains(".gz") && command.searchCommand.startsWith("grep")) {
			cmd = command.searchCommand + " " + command.searchString
					.replace("\"", "").replace("'", "") + " " + (isArchive ? command.archiveLogPath : command.logPath)
					+ "/" + fileName ;
		} else if(command.logFileName.contains(".gz") && command.searchCommand.startsWith("grep")) { 
			cmd = "zgrep " + command.searchString
					.replace("\"", "").replace("'", "") + " " + (isArchive ? command.archiveLogPath : command.logPath)
					+ "/" + fileName ;
		} else if(command.logFileName.contains(".gz") && (command.searchCommand.startsWith("awk") || command.searchCommand.startsWith("nawk"))) { 
			cmd = "for f in " + (isArchive ? command.archiveLogPath : command.logPath)
					+ "/" + fileName + "; do zcat $f | " + command.searchCommand + " -v fname=${f} 'c-->0;$0~s{if(b)for(c=b+1;c>1;c--){print fname}print r[(NR-c+1)%b];print;c=a}b{r[NR%b]=$0}' s='" + command.searchString.replace("\"", "").replace("'", "") + "'"
					+ " a=" + command.linesAfter + " b=" + command.linesBefore + "; done";
		} else {
//			cmd = command.searchCommand + " -v fname=${f} 'c-->0;$0~s{if(b)for(c=b+1;c>1;c--){print fname}print r[(NR-c+1)%b];print;c=a}b{r[NR%b]=$0}' s='" + command.searchString.replace("\"", "").replace("'", "") + "'"
//					+ " a=" + command.linesAfter + " b=" + command.linesBefore
//					+ " " + (isArchive ? command.archiveLogPath : command.logPath)
//					+ "/" + fileName ;	
			
			cmd = "for f in " + (isArchive ? command.archiveLogPath : command.logPath)
					+ "/" + fileName + "; do " + command.searchCommand + " -v fname=${f} 'c-->0;$0~s{if(b)for(c=b+1;c>1;c--){print fname}print r[(NR-c+1)%b];print;c=a}b{r[NR%b]=$0}' s='" + command.searchString.replace("\"", "").replace("'", "") + "'"
					+ " a=" + command.linesAfter + " b=" + command.linesBefore + " $f ; done";			
		}
		
		//System.out.println(cmd);
		
		return cmd;
	}

}
