package com.riverplant.payCenter.repository;

import org.springframework.stereotype.Repository;

import com.riverplant.payCenter.domain.PayOrders;
import com.riverplant.payCenter.repository.support.RiverRepository;

@Repository
public interface PayOrderRepository extends RiverRepository<PayOrders> {

}
