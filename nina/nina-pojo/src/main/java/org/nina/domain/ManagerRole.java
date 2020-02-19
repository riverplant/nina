package org.nina.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ManagerRole extends DomainImpl{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="manager_id")
	private Manager manager;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id")
	private Role role;
}
