package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerServeDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.po.CustomerServe;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 2018/5/9.
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe>{
    @Resource
    private CustomerServeDao customerServeDao;
    @Resource
    private UserDao userDao;

    public void saveCustomerServe(CustomerServe customerServe,Integer userId){
        //参数非空验证
        checkCustomerServeParams(customerServe.getServeType(),customerServe.getCustomer(),
                customerServe.getOverview(),customerServe.getServiceRequest());

        //根据数据库补全参数
        customerServe.setState("1");
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        customerServe.setIsValid(1);
        User user = userDao.queryById(userId);
        customerServe.setCreatePeople(user.getTrueName());

        //验证是否添加成功
        AssertUtil.isTrue(customerServeDao.save(customerServe)<1,"添加失败");
    }

    private void checkCustomerServeParams(String serveType, String customer, String overview, String serviceRequest) {
        AssertUtil.isTrue(StringUtils.isBlank(serveType),"服务类型不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(customer),"顾客不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(overview),"概要不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(serviceRequest),"服务请求不能为空");

    }


    public void updateCustomerServe(CustomerServe customerServe, Integer userId) {
        // 补全参数
        // 根据state 判断需要添加那些字段

        CustomerServe customerServe1 = customerServeDao.queryById(customerServe.getId());
        String state = customerServe1.getState();
        customerServe.setUpdateDate(new Date());// 更新时间
        if(state.equals("1")){
            customerServe.setAssignTime(new Date());
            customerServe.setState("2");
        }else if(state.equals("2")){
            User user = userDao.queryById(userId);
            customerServe.setServiceProcePeople(user.getTrueName());
            customerServe.setServiceProceTime(new Date());
            customerServe.setState("3");
        }else if(state.equals("3")){
            customerServe.setState("5");
        }
        AssertUtil.isTrue(customerServeDao.update(customerServe)<1,CrmConstant.OPS_FAILED_MSG);
    }


    public List<Map> queryServiceData() {
        return customerServeDao.queryServiceData();
    }
}
