package org.nina.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.nina.domain.Items;
import org.nina.dto.ItemsCondition;
import org.nina.dto.ItemsInfo;
import org.nina.repository.ItemsRepository;
import org.nina.repository.spec.ItemsSpec;
import org.nina.repository.support.AbstractDomain2InfoConverter;
import org.nina.repository.support.QueryResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

/**
 * 
 * @author riverplant
 *
 */
@Service("itemsService")
@Transactional
public class ItemsServiceImpl implements ItemsService {
	@Autowired
	ItemsRepository itemsRepository;

	@Override
	public Page<ItemsInfo> query(ItemsCondition condition, Pageable pageable) {
		Page<Items> result = itemsRepository.findAll(new ItemsSpec(condition), pageable);
		Page<ItemsInfo> result2 = QueryResultConverter.convert(result, pageable,new AbstractDomain2InfoConverter<Items,ItemsInfo>(){
            /**
             * 调用该方法之前已经将相同的字段都拷贝了
             */
			@Override
			protected void doConvert(Items domain, ItemsInfo info) throws Exception {
				info.setItemName(domain.getItemName());
			}
			
		});
		//return QueryResultConverter.convert(result, ItemsInfo.class, pageable);
		return result2;
	}

	@Override
	@Modifying
	public ItemsInfo update(@Valid ItemsInfo info) {
		if (info.getId() == null) {
			throw new RuntimeException("info's id is null...");
		}
		Items items = itemsRepository.getOne(info.getId());
		items.setItemName(info.getItemName());
		itemsRepository.save(items);
		return info;
	}

	@Override
	public ItemsInfo create(@Valid ItemsInfo info) {
		Items items = new Items();
		items.setItemName(info.getItemName());
		itemsRepository.save(items);
		info.setId(items.getId());
		return info;
	}

	@Override
	public void delete(Long id) {
		if (id == null) {
			throw new RuntimeException(" id is null...");
		}

		itemsRepository.deleteById(id);
	}

}
