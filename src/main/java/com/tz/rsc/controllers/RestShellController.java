package com.tz.rsc.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tz.rsc.entities.ShellCommand;
import com.tz.rsc.entities.ShellCommandResult;
import com.tz.rsc.utils.Utils;

@RestController
public class RestShellController {

	@PostMapping("/search")
	ShellCommandResult search(@RequestBody ShellCommand command) {

		ShellCommandResult result = new ShellCommandResult();
		StringBuffer data = new StringBuffer();
		String cmd="";	
	
		
		Runtime run = Runtime.getRuntime();
		Process pr;
		try {			
			
			if(!Utils.commandIsSupported(command.searchCommand)) {
				result.result = "Currently only grep/awk/nawk commands are supported";
				return result;
			}			
			
			cmd = Utils.prepareCommand(command);		
			
			System.out.println("Executing .... [" + cmd + "]");
			pr = run.exec(new String[] { "sh", "-c", cmd });
			pr.waitFor(command.timeoutSeconds, TimeUnit.SECONDS);
			BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line = "";
			while ((line = buf.readLine()) != null) {
				data.append(line);
				data.append(System.getProperty("line.separator"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			
			result.result = "Error While processing the command [" + cmd + "]";
			return result;			
		}
		
		result.result = data.toString();

		return result;
	}

}
