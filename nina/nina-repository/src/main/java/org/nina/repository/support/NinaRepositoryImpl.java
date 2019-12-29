package org.nina.repository.support;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

/**
 * 自定义repository
 * 
 * @author riverplant
 *
 */
public class NinaRepositoryImpl<T> extends SimpleJpaRepository<T, Long> {

	private static Logger log = LoggerFactory.getLogger(NinaRepositoryImpl.class);

	public NinaRepositoryImpl(JpaEntityInformation<T, Long> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <S extends T> S save(S entity) {
		log.info("保存了:"+entity.getClass().getSimpleName());
		return super.save(entity);
	}

}
