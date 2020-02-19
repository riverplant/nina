package org.nina.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RoleResources extends DomainImpl{

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="resource_id")
	private Resources resources;
}
