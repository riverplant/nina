package org.nina.service;

import java.util.List;

import org.nina.domain.Carousel;
import org.nina.repository.CarouselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarouselServiceImpl implements CarouselService {
	@Autowired
	private CarouselRepository carouselRepository;

	@Override
	public List<Carousel> querAll(Integer isShow) {
		/**
		 * security
		 */
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication == null) {
//			throw new RuntimeException("");
//		}
		/**
		 * ***************security****************************
		 */
			List<Carousel> result = carouselRepository.querAll(isShow);
		return result;
	}

}
