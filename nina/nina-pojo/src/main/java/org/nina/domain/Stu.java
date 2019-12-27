package org.nina.domain;

import javax.persistence.Entity;

/**
 * 
 * @author riverplant
 *
 */
@Entity
public class Stu extends DomainImpl {
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
