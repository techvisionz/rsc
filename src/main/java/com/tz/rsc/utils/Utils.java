package com.tz.rsc.utils;

import com.tz.rsc.entities.ShellCommand;

public class Utils {
	
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
	
	public static String prepareCommand(ShellCommand command) {
		String cmd="";
		String fileName = isNullOrEmpty(command.fileName) ? "*" : command.fileName;
		
		if(command.searchCommand.startsWith("grep")) {
			cmd = command.searchCommand + " " + command.searchString
					.replace("\"", "").replace("'", "") + " " + command.filePath
					+ "/" + fileName ;
		} else {
			cmd = command.searchCommand + " 'c-->0;$0~s{if(b)for(c=b+1;c>1;c--)print r[(NR-c+1)%b];print;c=a}b{r[NR%b]=$0}' s='" + command.searchString.replace("\"", "").replace("'", "") + "'"
					+ " a=" + command.linesAfter + " b=" + command.linesBefore
					+ " " + command.filePath
					+ "/" + fileName ;
		}
		
		return cmd;
	}

}
