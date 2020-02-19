package org.nina.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ResourceServiceUrl extends DomainImpl{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resource_id")
	private Resources resources;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="service_url_id")
	private ServiceUrl serviceUrl;

}
