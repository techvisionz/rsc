package com.tz.rsc.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "nodeInfo")
@Table(name = "nodeInfo")
public class NodeInfo {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "clientUrl")
	private String clientUrl;
	
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private List<LogInfo> logsInfo;
    
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;
    
	public NodeInfo() {
		this.logsInfo=new ArrayList<LogInfo>();
	}	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


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


	public String getClientUrl() {
		return clientUrl;
	}


	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}


	public List<LogInfo> getLogsInfo() {
		return logsInfo;
	}


	public void setLogsInfo(List<LogInfo> logsInfo) {
		this.logsInfo = logsInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public LogInfo getLogInfoById(long id) {
		for (LogInfo logInfo : logsInfo) {
			if(logInfo.getId() == id) {
				return logInfo;
			}
		}
		
		return null;
	}
}
