package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.CustomerLoss;
import org.springframework.stereotype.Repository;

/**
 * Created by lp on 2018/5/4.
 */
@Repository
public interface CustomerLossDao extends BaseDao<CustomerLoss> {
    public Integer confirmLoss(Integer id);
}
