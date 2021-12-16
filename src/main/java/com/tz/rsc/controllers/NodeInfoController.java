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

import com.tz.rsc.entities.LogInfo;
import com.tz.rsc.entities.NodeInfo;
import com.tz.rsc.entities.User;
import com.tz.rsc.repository.LogInfoRepository;
import com.tz.rsc.repository.NodeInfoRepository;
import com.tz.rsc.repository.UserRepository;
import com.tz.rsc.utils.Utils;

@RestController
@RequestMapping(value = "/rsc/api/nodes")
public class NodeInfoController
{
	private NodeInfoRepository nodeInfoRepository;
	private UserRepository userRepository;
	private LogInfoRepository logInfoRepository;

	public NodeInfoController(NodeInfoRepository nodeInfoRepository, UserRepository userRepository, LogInfoRepository logInfoRepository)
	{
		this.nodeInfoRepository = nodeInfoRepository;
		this.userRepository=userRepository;
		this.logInfoRepository= logInfoRepository;
	}
	
	@GetMapping("/all")
	public List<NodeInfo> all()
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		return (List<NodeInfo>) nodeInfoRepository.findAllByUser(user);
	}
	
	public NodeInfo nodeByName(NodeInfo nodeInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		return nodeInfoRepository.findByNameAndUser(nodeInfo.getName(), user);
	}
	
	@GetMapping("/{id}")
	public NodeInfo nodeById(@PathVariable("id") long nodeId)
	{
		Optional<NodeInfo> nodeOp = nodeInfoRepository.findById(nodeId);
		
		if(nodeOp.isPresent()) {
			return null;
		}
		
		return nodeOp.get();
	}
	
	@PostMapping("/create")
	public String create(@RequestBody NodeInfo nodeInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		if(nodeByName(nodeInfo) != null) {
			return "{\"message\":\"Node already exists\"}";
		}
		
		if(Utils.isNullOrEmpty(nodeInfo.getName()) ||
				Utils.isNullOrEmpty(nodeInfo.getClientUrl())) {
			return "{\"message\":\"Missing parameters\"}";
		}		
		
		nodeInfo.setUser(user);
		nodeInfoRepository.save(nodeInfo);
		return "{\"message\":\"success\"}";
	}	
	
	
	@PostMapping("/update")
	public String update(@RequestBody NodeInfo nodeInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<NodeInfo> existingNodeOp = this.nodeInfoRepository.findById(nodeInfo.getId());		
		
		if(!existingNodeOp.isPresent()) {
			return "{\"message\":\"Node not found\"}";
		}
		
		NodeInfo existingNode = existingNodeOp.get();
		
		if(user == null || existingNode.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this node\"}";
		}
		
		if(Utils.isNullOrEmpty(nodeInfo.getName()) ||
				Utils.isNullOrEmpty(nodeInfo.getClientUrl())) {
			return "{\"message\":\"Missing parameters\"}";
		}
		
		existingNode.setName(nodeInfo.getName());
		existingNode.setClientUrl(nodeInfo.getClientUrl());		
		
		nodeInfoRepository.save(existingNode);
		return "{\"message\":\"success\"}";
	}		
	
	@PostMapping("/delete")
	public String delete(@RequestBody NodeInfo nodeInfo)
	{
		System.out.println(nodeInfo.getId());
		Optional<NodeInfo> existingNode = this.nodeInfoRepository.findById(nodeInfo.getId());		
		
		if(!existingNode.isPresent()) {
			return "{\"message\":\"Node not found\"}";
		}	
		
		nodeInfoRepository.delete(existingNode.get());
		return "{\"message\":\"success\"}";
	}
	
	@PostMapping("/{id}/loginfo/add")
	public String addLogInfo(@PathVariable("id") long nodeId, @RequestBody LogInfo logInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<NodeInfo> existingNodeOp = this.nodeInfoRepository.findById(nodeId);		
		
		if(!existingNodeOp.isPresent()) {
			return "{\"message\":\"Node not found\"}";
		}	
		
		NodeInfo existingNode = existingNodeOp.get();
		
		if(user == null || existingNode.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this node\"}";
		}
		
		if(Utils.isNullOrEmpty(logInfo.getLogPath()) ||
				Utils.isNullOrEmpty(logInfo.getLogFileName())) {
			return "{\"message\":\"Missing parameters\"}";
		}
		
		existingNode.getLogsInfo().add(logInfo);
		
		nodeInfoRepository.save(existingNode);
		return "{\"message\":\"success\"}";
	}
	
	@PostMapping("/{id}/loginfo/update")
	public String updateLogInfo(@PathVariable("id") long nodeId, @RequestBody LogInfo logInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<NodeInfo> existingNodeOp = this.nodeInfoRepository.findById(nodeId);		
		
		if(!existingNodeOp.isPresent()) {
			return "{\"message\":\"Node not found\"}";
		}	
		
		NodeInfo existingNode = existingNodeOp.get();
		
		if(user == null || existingNode.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this node\"}";
		}
		
		LogInfo existingLogInfo = existingNode.getLogInfoById(logInfo.getId());
		
		if(existingLogInfo == null) {
			return "{\"message\":\"Log info not found\"}";
		}
		
		if(Utils.isNullOrEmpty(logInfo.getLogPath()) ||
				Utils.isNullOrEmpty(logInfo.getLogFileName())) {
			return "{\"message\":\"Missing parameters\"}";
		}
		
		existingLogInfo.setArchiveLogPath(Utils.isNullOrEmpty(logInfo.getArchiveLogPath()) ? existingLogInfo.getArchiveLogPath(): logInfo.getArchiveLogPath());
		existingLogInfo.setFileDatePattern(Utils.isNullOrEmpty(logInfo.getFileDatePattern()) ? existingLogInfo.getFileDatePattern(): logInfo.getFileDatePattern());
		existingLogInfo.setLogFileName(Utils.isNullOrEmpty(logInfo.getLogFileName()) ? existingLogInfo.getLogFileName(): logInfo.getLogFileName());
		existingLogInfo.setLogPath(Utils.isNullOrEmpty(logInfo.getLogPath()) ? existingLogInfo.getLogPath(): logInfo.getLogPath());
		
		logInfoRepository.save(existingLogInfo);
		return "{\"message\":\"success\"}";
	}
	
	@PostMapping("/{id}/loginfo/delete")
	public String deleteLogInfo(@PathVariable("id") long nodeId, @RequestBody LogInfo logInfo)
	{
		User user = this.userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal().toString());
		
		Optional<NodeInfo> existingNodeOp = this.nodeInfoRepository.findById(nodeId);		
		
		if(!existingNodeOp.isPresent()) {
			return "{\"message\":\"Node not found\"}";
		}	
		
		NodeInfo existingNode = existingNodeOp.get();
		
		if(user == null || existingNode.getUser().getId() != user.getId()) {
			return "{\"message\":\"You are not authorized to update this node\"}";
		}
		
		LogInfo existingLogInfo = existingNode.getLogInfoById(logInfo.getId());
		
		if(existingLogInfo == null) {
			return "{\"message\":\"Log info not found\"}";
		}	
		
		existingNode.getLogsInfo().remove(existingLogInfo);
		
		nodeInfoRepository.save(existingNode);
		logInfoRepository.delete(existingLogInfo);
		
		return "{\"message\":\"success\"}";
	}

}