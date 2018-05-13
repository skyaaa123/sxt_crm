package com.shsxt.crm.service;


import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/4/24.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance> {

    @Resource
    private SaleChanceDao saleChanceDao;

    @Resource
    private UserDao userDao;

    public void addOrUpdateSaleChance(SaleChance saleChance, Integer userId) {
        // 设置缺失参数
        saleChance.setCreateDate(new Date());
        /***
         * 分配人为空: 代表未分配, 不为空已分配
         * */
        if (StringUtils.isBlank(saleChance.getAssignMan())) {
            saleChance.setState(0);// 未分配
        } else {
            saleChance.setState(1);// 已分配
            saleChance.setAssignTime(new Date());// 分配时间
        }
        /**
         * 1. 如果SaleChance有id, 代表更新
         * 2. 如果SaleChance没有id, 代表添加
         * */
        if (null==saleChance.getId()) {
            // 添加
            User user = userDao.queryById(userId);
            saleChance.setCreateMan(user.getTrueName());// 真实创建人
            saleChance.setIsValid(1);//有效数据
            saleChance.setDevResult(0);//未开发
            AssertUtil.isTrue(saleChanceDao.save(saleChance) < 1, "营销机会添加失败");
        }else{
            // 修改
            AssertUtil.isTrue(saleChanceDao.update(saleChance)<1, "营销机会更新失败");
        }
    }


    // 查询所有的客户经理
    public List<Map> queryCustomerManagers() {
        return userDao.queryCustomerManagers();
    }


    public void deleteSaleChance(Integer[] ids) throws DataAccessException {
        AssertUtil.isTrue(null==ids, "没有传入用户ID");
        AssertUtil.isTrue(saleChanceDao.deleteBatch(ids)<ids.length, CrmConstant.OPS_FAILED_MSG);
    }
}
