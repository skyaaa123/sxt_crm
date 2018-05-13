package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lp on 2018/5/4.
 */
@Repository
public interface CustomerDao extends BaseDao<Customer>{
    public List<Customer> queryLossCustomers();

    public Integer updateCustomerBatch(List<Integer> ids);
}
