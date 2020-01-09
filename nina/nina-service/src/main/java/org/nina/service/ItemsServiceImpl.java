package org.nina.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.nina.commons.aop.ServiceLog;
import org.nina.domain.Items;
import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.repository.ItemsRepository;
import org.nina.repository.spec.ItemsSpec;
import org.nina.repository.support.AbstractDomain2InfoConverter;
import org.nina.repository.support.QueryResultConverter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 
 * @author riverplant
 *
 */
@Service("itemsService")
@Transactional(readOnly = true)
public class ItemsServiceImpl implements ItemsService {
	@Autowired
	private ItemsRepository itemsRepository;
	// 通过编程控制事务
	@Autowired
	private PlatformTransactionManager transactionManager;
	// 编程式控制缓存
	@Autowired
	private CacheManager cacheManager;
	/**
	 * Job的运行器
	 */
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;

	/**
	 * 使用condition.itemName作为缓存的key,只有itemName变化才存入缓存 缓存名称为items condition =
	 * "#pageable.size > 0":当条件为真才开启缓存
	 */
	@Override
	@ServiceLog
	@Cacheable(cacheNames = "items", key = "#condition.itemName", condition = "#pageable.size > 0")
	@Transactional(propagation = Propagation.SUPPORTS)
	public Page<ItemsInfo> query(ItemsCondition condition, Pageable pageable) {
		Page<Items> result = itemsRepository.findAll(new ItemsSpec(condition), pageable);
		Page<ItemsInfo> result2 = QueryResultConverter.convert(result, pageable,
				new AbstractDomain2InfoConverter<Items, ItemsInfo>() {
					/**
					 * 调用该方法之前已经将相同的字段都拷贝了
					 */
					@Override
					protected void doConvert(Items domain, ItemsInfo info) throws Exception {
						info.setItemName(domain.getItemName());
					}

				});
		// return QueryResultConverter.convert(result, ItemsInfo.class, pageable);
		return result2;
	}

	/**
	 * 学习通过编程控制事务
	 * 
	 * @param condition
	 * @param pageable
	 * @return
	 */
	@ServiceLog
	@Transactional
	public Page<ItemsInfo> query2(ItemsCondition condition, Pageable pageable) {
		/**
		 * 开启一个事务
		 */
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		/**
		 * 因为方法上使用了@Transactional，所以当使用默认的事务传播级别PROPAGATION_REQUIRED，
		 * 同一个事务中的数据库操作将一起成功提交或者一起失败回滚 如果使用PROPAGATION_NEW，将会谁成功就提交谁
		 */
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(definition);

		try {
			Page<Items> result = itemsRepository.findAll(new ItemsSpec(condition), pageable);
			transactionManager.commit(status);// 提交事务
			Page<ItemsInfo> result2 = QueryResultConverter.convert(result, pageable,
					new AbstractDomain2InfoConverter<Items, ItemsInfo>() {
						/**
						 * 调用该方法之前已经将相同的字段都拷贝了
						 */
						@Override
						protected void doConvert(Items domain, ItemsInfo info) throws Exception {
							info.setItemName(domain.getItemName());
						}

					});
			return result2;
		} catch (Exception e) {
			transactionManager.rollback(status);// 事务回滚
		}
		return null;
	}

	/**
	 * 了解分布式业务中事务的管理 各个服务来自不同的远程调用
	 * 
	 * @param condition
	 * @param pageable
	 */
	@ServiceLog
	@Transactional
	public void fenbushiyewu(ItemsCondition condition, Pageable pageable) {
		/**
		 * 
		 */
		createOrder();
		/**
		 * updateStock服务应该监听一个消息队列，当收到订单异常， 就查询数据库，如果之前已经修改过库存就将库存修改回来
		 */
		updateStock();
		/**
		 * updateUserBalance如果失败，抛出异常，并且往消息队列里 写入订单异常信息，让updateStock服务捕获并且回滚
		 */
		updateUserBalance();

	}

	private void updateUserBalance() {
		// TODO Auto-generated method stub

	}

	private void updateStock() {
		// TODO Auto-generated method stub

	}

	private void createOrder() {
		// TODO Auto-generated method stub

	}

	/**
	 * 必须要外部类调用事务才会起作用!!!!! 本类的内部类调用事务无效
	 */
	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@CachePut(cacheNames = "items", key = "#info.id")
	public ItemsInfo update(@Valid ItemsInfo info) {
		if (info.getId() == null) {
			throw new RuntimeException("info's id is null...");
		}
		Items items = itemsRepository.getOne(info.getId());
		items.setItemName(info.getItemName());
		itemsRepository.save(items);
		return info;
	}
   /**
    * 如果已经存在事务就使用现有的,
    * 如果当前没有,就创建一个
    */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ItemsInfo create(@Valid ItemsInfo info) {
		Items items = new Items();
		items.setItemName(info.getItemName());
		itemsRepository.save(items);
		info.setId(items.getId());
		return info;
	}

	/**
	 * allEntries = true:删除调所有的缓存 beforeInvocation:是否在执行完方法后再请缓存
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	@CacheEvict(cacheNames = "items", allEntries = true, beforeInvocation = false)
	public void delete(Long id) {
		if (id == null) {
			throw new RuntimeException(" id is null...");
		}

		itemsRepository.deleteById(id);
	}

	/**
	 * 使用redis做缓存
	 */
	@Override
	@Cacheable(cacheNames = "items", key = "#id")
	public ItemsInfo getInfo(Long id) {
		Items items = itemsRepository.getOne(id);
		ItemsInfo info = new ItemsInfo();
		BeanUtils.copyProperties(items, info);
		return info;
	}

	/**
	 * 通过编程使用redis做缓存
	 */
	public ItemsInfo getInfo2(Long id) {
		// 先判断缓存中是否已经存在,如果已经存在可以直接返回
		ValueWrapper value = cacheManager.getCache("items").get(id);
		if (value == null) {
			Items items = itemsRepository.getOne(id);
			ItemsInfo info = new ItemsInfo();
			BeanUtils.copyProperties(items, info);
			// 通过编程创建缓存
			cacheManager.getCache("items").put(id, info);
			return info;
		} else {
			return (ItemsInfo) value.get();
		}
	}
    /**
     * 每隔三秒执行一次
     */
	@Override
	@Scheduled(cron = "0/3*****")
	public void task() {
		System.out.println("task开始运行");
		Map<String,JobParameter> paran = new HashMap<>();
		paran.put("startTime", new JobParameter(new Date()));
		try {
			jobLauncher.run(job, new JobParameters(paran));
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
