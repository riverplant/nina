package org.nina.repository.spec;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.nina.domain.Carousel;
import org.nina.dto.CarouselCondition;
import org.nina.repository.spec.support.NinaSimpleSpecification;
import org.nina.repository.spec.support.QueryWraper;

public class CarouselSpec extends NinaSimpleSpecification<Carousel, CarouselCondition>{

	private static final long serialVersionUID = 1L;

	public CarouselSpec(CarouselCondition condition) {
		super(condition);
	}

	@Override
	protected void addCondition(QueryWraper<Carousel> queryWraper) {
		addEqualsCondition(queryWraper,"isShow");
		
	}

	@Override
	protected void addFetch(Root<Carousel> root) {
		root.fetch("category",JoinType.LEFT);
		super.addFetch(root);
	}
}
