package org.nina.repository.support;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

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
    /**因为SimpleJpaRepository标注了@Transactional(readOnly = true)
     * 所以如果动库的操作不申明一个@Transactional
     * 不在一个事务里，保存方法将不会执行 
     */
	@Override
	@Transactional
	public <S extends T> S save(S entity) {
		log.info("保存了:"+entity.getClass().getSimpleName());
		return super.save(entity);
	}

}
