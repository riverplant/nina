package com.riverplant.payCenter.repository.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
@NoRepositoryBean
public interface RiverRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
