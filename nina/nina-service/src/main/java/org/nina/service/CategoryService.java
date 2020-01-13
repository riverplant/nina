package org.nina.service;

import java.util.List;

import org.nina.domain.Category;

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
	List<Category> queryAllRootLevel();
	
	List<Category> quwerySubCategory(Long rootId);
}
