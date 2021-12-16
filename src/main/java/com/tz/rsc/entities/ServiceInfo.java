package com.tz.rsc.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
		this.nodesInfo=new ArrayList<NodeInfo>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;	
	
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(
            cascade = CascadeType.MERGE
        )
    private List<NodeInfo> nodesInfo;    

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<NodeInfo> getNodesInfo() {
		return nodesInfo;
	}

	public void setNodesInfo(List<NodeInfo> nodesInfo) {
		this.nodesInfo = nodesInfo;
	}
	
	public NodeInfo getNodeInfoById(long id) {
		for (NodeInfo nodeInfo : nodesInfo) {
			System.out.println(nodeInfo.getId());
			if(nodeInfo.getId() == id) {
				return nodeInfo;
			}
		}
		
		return null;
	}
}