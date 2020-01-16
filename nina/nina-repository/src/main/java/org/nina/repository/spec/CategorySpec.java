package org.nina.repository.spec;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.nina.domain.Category;
import org.nina.dto.CategoryCondition;
import org.nina.repository.spec.support.NinaSimpleSpecification;
import org.nina.repository.spec.support.QueryWraper;

public class CategorySpec extends NinaSimpleSpecification<Category, CategoryCondition>{

	private static final long serialVersionUID = 1L;

	public CategorySpec(CategoryCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Category> queryWraper) {
		addEqualsCondition(queryWraper, "type");
		
	}
	
	@Override
	protected void addFetch(Root<Category> root) {
		root.fetch("Items",JoinType.LEFT);
		root.fetch("Items_img",JoinType.LEFT);
		super.addFetch(root);
	}
	
}
