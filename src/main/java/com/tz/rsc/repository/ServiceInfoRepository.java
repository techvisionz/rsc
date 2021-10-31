package com.tz.rsc.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tz.rsc.entities.ServiceInfo;
import com.tz.rsc.entities.User;

@Repository
public interface ServiceInfoRepository extends CrudRepository<ServiceInfo, Long> {
	
	public ServiceInfo findByNameAndUser(String name, User user);
	public List<ServiceInfo> findAllByUser(User user);
}