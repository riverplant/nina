package org.nina.repository;

import java.util.List;

import org.nina.domain.Carousel;
import org.nina.repository.support.NinaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends NinaRepository<Carousel> {
	@Query("from Carousel carousel where carousel.isShow = ?1 order by carousel.sort desc ")
	List<Carousel> querAll(boolean isShow);
}
