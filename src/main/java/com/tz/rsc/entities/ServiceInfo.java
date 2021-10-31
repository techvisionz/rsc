package com.tz.rsc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "serviceInfo")
@Table(name = "serviceInfo")
public class ServiceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceInfo() {
		this.serviceHosts=new ArrayList<String>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;	
	
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @ElementCollection // 1
    @CollectionTable(name = "hosts", joinColumns = @JoinColumn(name = "id")) // 2
    @Column(name = "serviceHosts") // 3
    private List<String> serviceHosts;
    
	@Column(name = "logPath")
	private String logPath;
	
	@Column(name = "archiveLogPath")
	private String archiveLogPath;
	
	@Column(name = "logFileName")
	private String logFileName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getServiceHosts() {
		return serviceHosts;
	}

	public void setServiceHosts(List<String> serviceHosts) {
		this.serviceHosts = serviceHosts;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}