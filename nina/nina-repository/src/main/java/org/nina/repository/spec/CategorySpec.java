package org.nina.repository.spec;

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
		// TODO Auto-generated method stub
		
	}
}
