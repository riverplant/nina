package org.nina.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author riverplant
 * 实现Items和Carousel的多对多关系
 */
@Entity
public class ItemsCarouselAssociation extends DomainImpl {
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="items_id")
	private Items items;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="carousel_id")
	private Carousel carousel;

	public Items getItems() {
		return items;
	}

	public void setItems(Items items) {
		this.items = items;
	}

	public Carousel getCarousel() {
		return carousel;
	}

	public void setCarousel(Carousel carousel) {
		this.carousel = carousel;
	}
   
	
}
