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
public class ServiceUrl extends DomainImpl{

	private static final long serialVersionUID = 1L;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "serviceUrl")
	private Set<ResourceServiceUrl> resourceServiceUrl;
	
	@Column(name = "class_name")
	private String className;
	
	@Column(name = "method_name")
	private String MethodName;
	
	@Column(name = "url")
	private String url;

	public Set<ResourceServiceUrl> getResourceServiceUrl() {
		return resourceServiceUrl;
	}

	public void setResourceServiceUrl(Set<ResourceServiceUrl> resourceServiceUrl) {
		this.resourceServiceUrl = resourceServiceUrl;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return MethodName;
	}

	public void setMethodName(String methodName) {
		MethodName = methodName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
