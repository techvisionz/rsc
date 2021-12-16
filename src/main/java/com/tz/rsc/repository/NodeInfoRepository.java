package com.tz.rsc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tz.rsc.entities.NodeInfo;
import com.tz.rsc.entities.User;

@Repository
public interface NodeInfoRepository extends CrudRepository<NodeInfo, Long> {
	
	public NodeInfo findByNameAndUser(String name, User user);
	public List<NodeInfo> findAllByUser(User user);
}