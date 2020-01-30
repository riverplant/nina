package org.nina.service;

import java.util.List;

import javax.validation.Valid;

import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.dto.ItemsParamInfo;
import org.nina.dto.ItemsSpecInfo;
import org.nina.dto.Items_imgInfo;
import org.nina.dto.vo.CommentLevelCountsVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface ItemsService {

	Page<ItemsInfo> query(ItemsCondition condition, Pageable pageable);

	ItemsInfo update(@Valid ItemsInfo info);
	
	ItemsInfo getInfo(Long id);

	ItemsInfo create(@Valid ItemsInfo info);

	void delete(Long id);
	
	/**
	 * 用作定时任务的方法必须是无返回值，无参数
	 */
	//void task();
	
	ItemsInfo queryById(Long itemId);
	/**
	 * 根据商品ID查询商品图片
	 * @param itemId
	 * @return
	 */
	List<Items_imgInfo> queryItemImgList(Long itemId);
	
	/**
	 * 根据商品ID查询商品规格
	 * @param itemId
	 * @return
	 */
	List<ItemsSpecInfo> queryItemSpecList(Long itemId);
	
	/**
	 * 根据商品ID查询商品参数
	 * @param itemId
	 * @return
	 * @PreAuthorize("hasAuthority('admin')")
	 * 通过注解为该方法添加springSecurity的授权,
	 * 所有调取该方法的用户必须具备admin权限
	 * 该方法不适用于分布式dabbo环境
	 */
     @PreAuthorize("hasAuthority('admin')")
	 ItemsParamInfo queryItemParam(Long itemId);
	 /**
	  * 根据商品ID查询商品所有评价等级和数量
	  * @param itemId
	  */
	 CommentLevelCountsVO queryCommentLevelsAndCounts(Long itemId);
}
