package com.tz.rsc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tz.rsc.entities.NodeInfo;
import com.tz.rsc.entities.ServiceInfo;
import com.tz.rsc.entities.User;
import com.tz.rsc.repository.NodeInfoRepository;
import com.tz.rsc.repository.ServiceInfoRepository;
import com.tz.rsc.repository.UserRepository;
import com.tz.rsc.utils.Utils;

@RestController
@RequestMapping(value = "/rsc/api/services")
public class ServiceInfoController
{
	private ServiceInfoRepository serviceInfoRepository;
	private NodeInfoRepository nodeInfoRepository;
	private UserRepository userRepository;

	public ServiceInfoController(ServiceInfoRepository serviceInfoRepository, UserRepository userRepository, NodeInfoRepository nodeInfoRepository)
	{
		this.serviceInfoRepository = serviceInfoRepository;
		this.userRepository=userRepository;
		this.nodeInfoRepository = nodeInfoRepository;
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
				serviceInfo.getNodesInfo().size()<=0) {
			return "{\"message\":\"Missing parameters\"}";
		}
		
		for (NodeInfo nodeInfo : serviceInfo.getNodesInfo()) {
			if(!nodeInfoRepository.findById(nodeInfo.getId()).isPresent()) {
				return "{\"message\":\"Node does not exists [" + nodeInfo.getId() + "]\"}";
			}
		}
		
		serviceInfo.setUser(user);
		serviceInfoRepository.save(serviceInfo);
		return "{\"message\":\"success\"}";
	}	
	
	
	@PostMapping("/update")
	public String update(@RequestBody ServiceInfo serviceInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<ServiceInfo> existingServiceOp = this.serviceInfoRepository.findById(serviceInfo.getId());		
		
		if(!existingServiceOp.isPresent()) {
			return "{\"message\":\"Service not found\"}";
		}
		
		ServiceInfo existingService = existingServiceOp.get();
		
		if(user == null || existingService.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this service\"}";
		}
		
		existingService.setName(serviceInfo.getName());
		
		serviceInfoRepository.save(existingService);
		return "{\"message\":\"success\"}";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestBody ServiceInfo serviceInfo)
	{
		Optional<ServiceInfo> existingServiceOp = this.serviceInfoRepository.findById(serviceInfo.getId());		
		
		if(!existingServiceOp.isPresent()) {
			return "{\"message\":\"Service not found\"}";
		}
		
		ServiceInfo existingService = existingServiceOp.get();
		
		
		serviceInfoRepository.delete(existingService);
		return "{\"message\":\"success\"}";
	}
	
	@PostMapping("/{id}/nodeinfo/add")
	public String addNodeInfo(@PathVariable("id") long serviceId, @RequestBody NodeInfo nodeInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<ServiceInfo> existingServiceOp = this.serviceInfoRepository.findById(serviceId);		
		
		if(!existingServiceOp.isPresent()) {
			return "{\"message\":\"Service not found\"}";
		}	
		
		ServiceInfo existingService = existingServiceOp.get();
		
		if(user == null || existingService.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this service\"}";
		}
		
		Optional<NodeInfo> existingNodeOp = this.nodeInfoRepository.findById(nodeInfo.getId());		
		
		if(!existingNodeOp.isPresent()) {
			return "{\"message\":\"Node not found\"}";
		}	
		
		NodeInfo existingNode = existingNodeOp.get();
		
		if(user == null || existingNode.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to use this node\"}";
		}
		
		existingService.getNodesInfo().add(existingNode);
		
		serviceInfoRepository.save(existingService);
		return "{\"message\":\"success\"}";
	}	
	
	@PostMapping("/{id}/nodeinfo/delete")
	public String deleteNodeInfo(@PathVariable("id") long serviceId, @RequestBody NodeInfo nodeInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<ServiceInfo> existingServiceOp = this.serviceInfoRepository.findById(serviceId);		
		
		if(!existingServiceOp.isPresent()) {
			return "{\"message\":\"Service not found\"}";
		}	
		
		ServiceInfo existingService = existingServiceOp.get();
		
		if(user == null || existingService.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this service\"}";
		}		
		
		NodeInfo existingNode = existingService.getNodeInfoById(nodeInfo.getId());
		
		if(existingNode == null) {
			return "{\"message\":\"Node not found\"}";
		}	
		
		existingService.getNodesInfo().remove(existingNode);
		
		serviceInfoRepository.save(existingService);
		return "{\"message\":\"success\"}";
	}

}