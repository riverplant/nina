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
public class Role extends DomainImpl{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "role")
	private Set<ManagerRole> managerRole;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "role")
	private Set<RoleResources> roleResources;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "memo")
	private String memo;

	public Set<ManagerRole> getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(Set<ManagerRole> managerRole) {
		this.managerRole = managerRole;
	}

	public Set<RoleResources> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(Set<RoleResources> roleResources) {
		this.roleResources = roleResources;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
