package org.nina.repository.spec;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.nina.domain.Items;
import org.nina.dto.ItemsCondition;
import org.nina.repository.spec.support.NinaSimpleSpecification;
import org.nina.repository.spec.support.QueryWraper;

public class ItemSpec extends NinaSimpleSpecification<Items, ItemsCondition>{

	private static final long serialVersionUID = 1L;

	public ItemSpec(ItemsCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Items> queryWraper) {
		
		addLikeCondition(queryWraper, "itemName");
	}

	@Override
	protected void addFetch(Root<Items> root) {
		root.fetch("category",JoinType.LEFT);
		super.addFetch(root);
	}
	
	

}
