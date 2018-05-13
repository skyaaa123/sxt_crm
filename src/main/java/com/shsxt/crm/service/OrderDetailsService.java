package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.OrderDetailsDao;
import com.shsxt.crm.po.OrderDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lp on 2018/1/15.
 */
@Service
public class OrderDetailsService extends BaseService<OrderDetails> {

    @Resource
    private OrderDetailsDao orderDetailsDao;
}
