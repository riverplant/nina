package org.nina.service;

import java.util.List;

import org.nina.dto.CategoryInfo;
import org.nina.dto.vo.CategoryVO;
import org.nina.dto.vo.NewItemsVO;

/**
 * 根级分类服务接口
 * @author jli
 *
 */
public interface CategoryService {
   /**
    * 查询根级大分类
    * @return
    */
	List<CategoryInfo> queryAllRootLevel();
	/**
	 * 根据一级分类id查询字分类信息
	 * @param rootId
	 * @return
	 */
	List<CategoryVO> querySubCategory(Long rootId);
	/**
	 * 查询首页所有一级分类下的6条最新商品记录
	 * @param rootId
	 * @return
	 */
	List<NewItemsVO> getSixNewItemsLazy(Long rootId);
	
	
}
