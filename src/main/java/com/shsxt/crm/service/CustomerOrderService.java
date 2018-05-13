package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerOrderDao;
import com.shsxt.crm.po.CustomerOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by lp on 2018/1/15.
 */
@Service
public class CustomerOrderService  extends BaseService<CustomerOrder> {

    @Resource
    private CustomerOrderDao customerOrderDao;


}
