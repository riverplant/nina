package org.nina.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.nina.commons.aop.ServiceLog;
import org.nina.commons.enums.CommentLevel;
import org.nina.domain.Items;
import org.nina.domain.ItemsComments;
import org.nina.domain.ItemsParam;
import org.nina.domain.ItemsSpec;
import org.nina.domain.Items_img;
import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.dto.ItemsParamInfo;
import org.nina.dto.ItemsSpecInfo;
import org.nina.dto.Items_imgInfo;
import org.nina.dto.vo.CommentLevelCountsVO;
import org.nina.dto.vo.ShopcartVO;
import org.nina.repository.ItemsCommentRepository;
import org.nina.repository.ItemsImgRepository;
import org.nina.repository.ItemsParamRepository;
import org.nina.repository.ItemsRepository;
import org.nina.repository.ItemsSpecRepository;
import org.nina.repository.spec.ItemSpec;
import org.nina.repository.support.AbstractDomain2InfoConverter;
import org.nina.repository.support.QueryResultConverter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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
	@Autowired
	private ItemsImgRepository itemsImgRepository;
	@Autowired
	private ItemsSpecRepository itemsSpecRepository;
	@Autowired
	private ItemsParamRepository itemsParamRepository;
	@Autowired
	private ItemsCommentRepository itemsCommentRepository;
	// 通过编程控制事务
	@Autowired
	private PlatformTransactionManager transactionManager;
	// 编程式控制缓存
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private RedissonClient redisson;
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
	@Cacheable(cacheNames = "items", key = "#condition.itemName") // condition = "#pageable.size > 0")
	@Transactional(propagation = Propagation.SUPPORTS)
	public Page<ItemsInfo> query(ItemsCondition condition, Pageable pageable) {
		/**
		 * security
		 */
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		// if (authentication != null) {
		// // authentication.getPrincipal():拿到当前认证的用户信息
		// System.out.println(authentication.getPrincipal());
		// }
		/**
		 * ***************security****************************
		 */
		Page<Items> result = itemsRepository.findAll(new ItemSpec(condition), pageable);
		if (result != null && result.hasContent()) {
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
		return null;
	}

	/**
	 * 学习通过编程控制事务
	 * 
	 * @param condition
	 * @param pageable
	 * @return
	 */
	@ServiceLog
	@Transactional(propagation = Propagation.SUPPORTS)
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
			Page<Items> result = itemsRepository.findAll(new ItemSpec(condition), pageable);
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
	 * 如果已经存在事务就使用现有的, 如果当前没有,就创建一个
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
	// @Override
	// @Scheduled(cron = "0/3*****")
	// public void task() {
	// System.out.println("task开始运行");
	// Map<String,JobParameter> paran = new HashMap<>();
	// paran.put("startTime", new JobParameter(new Date()));
	// try {
	// jobLauncher.run(job, new JobParameters(paran));
	// } catch (JobExecutionAlreadyRunningException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JobRestartException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JobInstanceAlreadyCompleteException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (JobParametersInvalidException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ItemsInfo queryById(Long itemId) {
		Items item = itemsRepository.findById(itemId).orElse(null);
		if (item != null) {
			ItemsInfo itemInfo = new ItemsInfo();
			BeanUtils.copyProperties(item, itemInfo);
			return itemInfo;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Items_imgInfo> queryItemImgList(Long itemId) {
		Items_img items_img = new Items_img();
		Items item = itemsRepository.findById(itemId).orElse(null);
		if (item != null) {
			items_img.setItems(item);
			Example<Items_img> example = Example.of(items_img);
			List<Items_img> itemsImgs = itemsImgRepository.findAll(example);
			List<Items_imgInfo> result2 = QueryResultConverter.convert(itemsImgs, Items_imgInfo.class);
			return result2;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ItemsSpecInfo> queryItemSpecList(Long itemId) {
		ItemsSpec itemsSpec = new ItemsSpec();
		Items item = itemsRepository.findById(itemId).orElse(null);
		if (item != null) {
			itemsSpec.setItems(item);
			Example<ItemsSpec> example = Example.of(itemsSpec);
			List<ItemsSpec> itemsSpecs = itemsSpecRepository.findAll(example);
			List<ItemsSpecInfo> result2 = QueryResultConverter.convert(itemsSpecs, ItemsSpecInfo.class);
			return result2;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ItemsParamInfo queryItemParam(Long itemId) {
		ItemsParam itemsParam = new ItemsParam();
		Items item = itemsRepository.findById(itemId).orElse(null);
		if (item != null) {
			itemsParam.setItems(item);
			Example<ItemsParam> example = Example.of(itemsParam);
			ItemsParam itemsParams = itemsParamRepository.findOne(example).orElse(null);
			if (itemsParams != null) {
				ItemsParamInfo result2 = new ItemsParamInfo();
				BeanUtils.copyProperties(itemsParams, result2);
				return result2;
			}
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public CommentLevelCountsVO queryCommentLevelsAndCounts(Long itemId) {
		Integer goodCount = getCommentCounts(itemId, CommentLevel.GOOD.type);
		Integer normalCount = getCommentCounts(itemId, CommentLevel.NORMAL.type);
		Integer badCount = getCommentCounts(itemId, CommentLevel.BAD.type);
		// Integer totalCounts = goodCount + normalCount + badCount;
		CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
		commentLevelCountsVO.setBadCount(badCount);
		commentLevelCountsVO.setGoodCount(goodCount);
		commentLevelCountsVO.setNormalCount(normalCount);
		commentLevelCountsVO.setTotalCount();
		return commentLevelCountsVO;
	}

	/**
	 * 
	 * @param itemId
	 * @param level
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	Integer getCommentCounts(Long itemId, Integer level) {
		ItemsComments itemsComments = new ItemsComments();
		itemsComments.setId(itemId);
		if (level != null && level != 0) {
			itemsComments.setCommentLevel(CommentLevel.stateOf(level));
		}
		Example<ItemsComments> example = Example.of(itemsComments);
		Long result = itemsCommentRepository.count(example);
		return result.intValue();
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
		String[] sids = specIds.split(",");
		List<Long> specIdsList = Arrays.stream(sids).map(Long::parseLong).collect(Collectors.toList());
		itemsSpecRepository.queryItemsBySpecIds(specIdsList);
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public ItemsSpec queryItemSpecById(Long id) {
		return itemsSpecRepository.getOne(id);
	}
    /**
     * 扣减库存
     */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void decreaseItemSpecStocke(Long specId, Integer buyCounts) {
		/**
		 * 1.集群下不推荐使用synchronized 2.不推荐锁数据库 3.应该使用分布式锁: zookeeper, redis
		 */
		// ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		// ReadLock readLock = lock.readLock();
		// WriteLock writeLock = lock.writeLock();
		// readLock.lock();//--加锁
		// readLock.unlock();//--解锁
		RLock rLock = redisson.getLock("item_lock_" + specId);
		// 设置锁的过期时间，当过期没有释放锁，会自动释放
		rLock.lock(5, TimeUnit.SECONDS);
		try {
			// 1.查询库存
			// 2.判断库存，是否能减少到0以下
			int result = itemsRepository.decreaseItemSpecStock(specId, buyCounts);
			if (result != 1) {
				throw new RuntimeException("订单创建失败，库存不足");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			rLock.unlock();
		}
	}

}
