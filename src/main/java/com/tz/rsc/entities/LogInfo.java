package com.tz.rsc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "logInfo")
@Table(name = "logInfo")
public class LogInfo {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "logPath")
	private String logPath;
	
	@Column(name = "archiveLogPath")
	private String archiveLogPath;
	
	@Column(name = "logFileName")
	private String logFileName;
	
	@Column(name = "logFileNameExt")
	private String logFileNameExt;	
	
	@Column(name = "logFileNamePattern")
	private String logFileNamePattern;	
	
	@Column(name = "fileDatePattern")
	private String fileDatePattern;
	
	@Column(name = "rollingType")
	private int rollingType;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}

	public String getArchiveLogPath() {
		return archiveLogPath;
	}

	public void setArchiveLogPath(String archiveLogPath) {
		this.archiveLogPath = archiveLogPath;
	}

	public String getLogFileName() {
		return logFileName;
	}

	public void setLogFileName(String logFileName) {
		this.logFileName = logFileName;
	}

	public String getFileDatePattern() {
		return fileDatePattern;
	}

	public void setFileDatePattern(String fileDatePattern) {
		this.fileDatePattern = fileDatePattern;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRollingType() {
		return rollingType;
	}

	public void setRollingType(int rollingType) {
		this.rollingType = rollingType;
	}

	public String getLogFileNamePattern() {
		return logFileNamePattern;
	}

	public void setLogFileNamePattern(String logFileNamePattern) {
		this.logFileNamePattern = logFileNamePattern;
	}

	public String getLogFileNameExt() {
		return logFileNameExt;
	}

	public void setLogFileNameExt(String logFileNameExt) {
		this.logFileNameExt = logFileNameExt;
	}

}
