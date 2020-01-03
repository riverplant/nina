package org.nina.service;

import javax.validation.Valid;

import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemsService {

	Page<ItemsInfo> query(ItemsCondition condition, Pageable pageable);

	ItemsInfo update(@Valid ItemsInfo info);
	
	ItemsInfo getInfo(Long id);

	ItemsInfo create(@Valid ItemsInfo info);

	void delete(Long id);
}
