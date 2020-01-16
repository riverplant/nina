package org.nina.service;

import java.util.List;

import org.nina.domain.Category;
import org.nina.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	public List<Category> queryAllRootLevel() {
		authenticationUserPermission();
		Category category = new Category();
		category.setType(1);
		Example<Category> example = Example.of(category);
		return categoryRepository.findAll(example);

	}

	private void authenticationUserPermission() {
		/**
		 * security
		 */
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			throw new RuntimeException("");
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Category> quwerySubCategory(Long rootId) {
		authenticationUserPermission();
//		Category category = new Category();
//		category.setFatherId(rootId);
//		Example<Category> example = Example.of(category);
		//return categoryRepository.findAll(example);
		return categoryRepository.querySubCatalog(rootId);
	}
}