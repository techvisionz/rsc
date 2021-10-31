package com.tz.rsc.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.rsc.entities.ServiceInfo;
import com.tz.rsc.entities.User;
import com.tz.rsc.repository.ServiceInfoRepository;
import com.tz.rsc.repository.UserRepository;
import com.tz.rsc.utils.Utils;

@RestController

@RequestMapping(value = "/services")

public class ServiceInfoController
{
	private ServiceInfoRepository serviceInfoRepository;
	private UserRepository userRepository;

	public ServiceInfoController(ServiceInfoRepository serviceInfoRepository, UserRepository userRepository)
	{
		this.serviceInfoRepository = serviceInfoRepository;
		this.userRepository=userRepository;
	}
	
	@GetMapping("/all")
	public List<ServiceInfo> all()
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		return (List<ServiceInfo>) serviceInfoRepository.findAllByUser(user);
	}
	
	@GetMapping("/serviceByName")
	public ServiceInfo serviceByName(ServiceInfo serviceInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		return serviceInfoRepository.findByNameAndUser(serviceInfo.getName(), user);
	}
	
	@PostMapping("/create")
	public String create(@RequestBody ServiceInfo serviceInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		if(serviceByName(serviceInfo) != null) {
			return "{\"message\":\"Service already exists\"}";
		}
		
		if(Utils.isNullOrEmpty(serviceInfo.getName()) ||
				serviceInfo.getServiceHosts().size()<=0 ||
				Utils.isNullOrEmpty(serviceInfo.getLogPath())) {
			return "{\"message\":\"Missing parameters\"}";
		}		
		
		serviceInfo.setUser(user);
		serviceInfoRepository.save(serviceInfo);
		return "{\"message\":\"success\"}";
	}	
	
	
	@PostMapping("/update")
	public String update(@RequestBody ServiceInfo serviceInfo)
	{		
		ServiceInfo existingService = this.serviceInfoRepository.findById(serviceInfo.getId()).get();		
		
		if(existingService == null) {
			return "{\"message\":\"Service not found\"}";
		}		
		
		if(!Utils.isNullOrEmpty(serviceInfo.getArchiveLogPath())) {
			existingService.setArchiveLogPath(serviceInfo.getArchiveLogPath());
		}
		
		if(!Utils.isNullOrEmpty(serviceInfo.getLogFileName())) {
			existingService.setLogFileName(serviceInfo.getLogFileName());
		}
		
		if(!Utils.isNullOrEmpty(serviceInfo.getLogPath())) {
			existingService.setLogPath(serviceInfo.getLogPath());
		}
		
		if(!Utils.isNullOrEmpty(serviceInfo.getLogPath())) {
			existingService.setLogPath(serviceInfo.getLogPath());
		}
		
		if(serviceInfo.getServiceHosts().size() >0) {
			existingService.setServiceHosts(serviceInfo.getServiceHosts());
		}
		
		serviceInfoRepository.save(existingService);
		return "{\"message\":\"success\"}";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestBody ServiceInfo serviceInfo)
	{
		ServiceInfo existingService = this.serviceInfoRepository.findById(serviceInfo.getId()).get();
		
		
		serviceInfoRepository.delete(existingService);
		return "{\"message\":\"success\"}";
	}

}