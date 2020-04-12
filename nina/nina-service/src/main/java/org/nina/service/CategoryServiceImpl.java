package org.nina.service;

import java.util.List;

import org.nina.domain.Category;
import org.nina.dto.CategoryInfo;
import org.nina.dto.vo.CategoryVO;
import org.nina.dto.vo.NewItemsVO;
import org.nina.repository.CategoryRepository;
import org.nina.repository.support.QueryResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author jli
 *
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CategoryInfo> queryAllRootLevel() {
		//authenticationUserPermission();
		Category category = new Category();
		category.setType(1);
		Example<Category> example = Example.of(category);
		List<Category> categorys = categoryRepository.findAll(example);
		return QueryResultConverter.convert(categorys, CategoryInfo.class);
	}

	private void authenticationUserPermission() {
		/**
		 * security
		 */
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication == null) {
//			throw new RuntimeException("");
//		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Object> querySubCategory(Long rootId) {
		//authenticationUserPermission();
		return categoryRepository.querySubCatalog(rootId);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<NewItemsVO> getSixNewItemsLazy(Long rootId) {
		authenticationUserPermission();
		return categoryRepository.getSixNewItemsLazy(rootId);
	}
}