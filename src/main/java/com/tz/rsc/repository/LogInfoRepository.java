package com.tz.rsc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tz.rsc.entities.LogInfo;

@Repository
public interface LogInfoRepository extends CrudRepository<LogInfo, Long> {
	
}