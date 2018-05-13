package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.CustomerServe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 2018/5/9.
 */
@Repository
public interface CustomerServeDao extends BaseDao<CustomerServe> {
    List<Map> queryServiceData();
}
