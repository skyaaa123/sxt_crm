package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerLossDao;
import com.shsxt.crm.po.CustomerLoss;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lp on 2018/1/15.
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss> {
    @Resource
    private CustomerLossDao customerLossDao;

    public Integer confirmLoss(Integer id){
        return customerLossDao.confirmLoss(id);
    }
}
