package org.nina.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Manager extends DomainImpl{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "real_name", nullable = false)
	private String realName;
	
	@Column(name = "user_name",unique = true, nullable = false)
	private String userName;
	
	@Column(name = "password")
	private String password;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "manager")
	private Set<ManagerRole> managerRole;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_id")
	private Departement departement;
	
	@Column(name = "enable")
	private boolean enable;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<ManagerRole> getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(Set<ManagerRole> managerRole) {
		this.managerRole = managerRole;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Manager() {
		
	}
}
