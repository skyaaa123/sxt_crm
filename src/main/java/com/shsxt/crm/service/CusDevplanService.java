package com.shsxt.crm.service;


import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CusDevplanDao;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.po.CusDevplan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
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
public class CusDevplanService extends BaseService<CusDevplan> {
    @Resource
    private CusDevplanDao cusDevplanDao;

    @Resource
    private SaleChanceDao saleChanceDao;

    /***
     * 添加和修改.通过id区分
     * */
    public void addOrUpdateCusDevplan(CusDevplan cusDevplan){
        // 参数检查
        checkCusDevplanParams(cusDevplan.getPlanDate(),
                cusDevplan.getPlanItem(), cusDevplan.getExeAffect(), cusDevplan.getSaleChanceId());
        // 检查营销机会是否真实存在
        SaleChance saleChance = saleChanceDao.queryById(cusDevplan.getSaleChanceId());
        AssertUtil.isTrue(null==saleChance, "更新失败,记录不存在或删除");
        //设置剩余参数
        cusDevplan.setUpdateDate(new Date());// 更新时间

        if(cusDevplan.getId()==null){
            //新增
            cusDevplan.setCreateDate(new Date());
            cusDevplan.setIsValid(1);
            AssertUtil.isTrue(cusDevplanDao.save(cusDevplan)<1, "添加营销计划失败");
            // 如果新增开发计划,判断devResult 是否为0,如果为0更改为1
            if(saleChance.getDevResult()==0){
                saleChance.setDevResult(1);
                AssertUtil.isTrue(saleChanceDao.update(saleChance)<1, "更新营销机会失败");
            }
        }else{
            // 更新
            AssertUtil.isTrue(cusDevplanDao.update(cusDevplan)<1, "更新营销计划失败");
        }
    }

    private void checkCusDevplanParams(Date planDate, String planItem, String exeAffect, Integer saleChanceId) {
        AssertUtil.isTrue(null==saleChanceId, "更新失败,记录不存在");
        AssertUtil.isTrue(null==planDate, "计划时间为空");
        AssertUtil.isTrue(StringUtils.isBlank(planItem), "计划内容为空");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect), "执行结果为空");
    }
}
