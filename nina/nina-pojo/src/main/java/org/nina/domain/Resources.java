package org.nina.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Resources extends DomainImpl{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "resources")
	private Set<RoleResources> roleResources;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "resources")
	private Set<ResourceServiceUrl> resourceServiceUrl;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "style")
	private String style;
	
	@Column(name = "type")
	private Integer type;

	public Set<RoleResources> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(Set<RoleResources> roleResources) {
		this.roleResources = roleResources;
	}

	public Set<ResourceServiceUrl> getResourceServiceUrl() {
		return resourceServiceUrl;
	}

	public void setResourceServiceUrl(Set<ResourceServiceUrl> resourceServiceUrl) {
		this.resourceServiceUrl = resourceServiceUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
