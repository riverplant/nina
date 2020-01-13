package org.nina.service;

import java.util.List;

import org.nina.domain.Carousel;

/**
 * 轮播图服务接口
 * @author jli
 *
 */
public interface CarouselService {
    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
	List<Carousel> querAll(boolean isShow);
}
