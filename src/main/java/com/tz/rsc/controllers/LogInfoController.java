package com.tz.rsc.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.rsc.entities.LogInfo;
import com.tz.rsc.repository.LogInfoRepository;

@RestController
@RequestMapping(value = "/rsc/api/loginfo")
public class LogInfoController
{
	private LogInfoRepository logInfoRepository;

	public LogInfoController(LogInfoRepository logInfoRepository)
	{
		this.logInfoRepository = logInfoRepository;
	}
	
	
	@PostMapping("/update")
	public String update(@RequestBody LogInfo logInfo)
	{		
		Optional<LogInfo> logInfoOp = this.logInfoRepository.findById(logInfo.getId());		
		
		if(!logInfoOp.isPresent()) {
			return "{\"message\":\"Log Info not found\"}";
		}
		
		LogInfo existingLogInfo = logInfoOp.get();

		existingLogInfo.setArchiveLogPath(logInfo.getArchiveLogPath());
		existingLogInfo.setFileDatePattern(logInfo.getFileDatePattern());
		existingLogInfo.setLogFileName(logInfo.getLogFileName());
		existingLogInfo.setLogFileNameExt(logInfo.getLogFileNameExt());
		existingLogInfo.setLogFileNamePattern(logInfo.getLogFileNamePattern());
		existingLogInfo.setLogPath(logInfo.getLogPath());
		existingLogInfo.setName(logInfo.getName());
		
		logInfoRepository.save(existingLogInfo);
		return "{\"message\":\"success\"}";
	}	

}