package org.nina.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity(name = "departement")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Departement extends DomainImpl {

	private static final long serialVersionUID = 1L;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "departement")
	private Set<Manager> manager;

	@Column(name = "name")
	private String name;

}
