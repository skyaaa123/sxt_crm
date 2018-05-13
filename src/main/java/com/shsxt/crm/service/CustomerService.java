package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CustomerDao;
import com.shsxt.crm.dao.CustomerLossDao;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.MathUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lp on 2018/5/4.
 */
@Service
public class CustomerService extends BaseService<Customer> {

    @Resource
    private CustomerDao customerDao;

    @Resource
    private CustomerLossDao customerLossDao;


    public void addOrUpdateCustomer(Customer customer) {
        // 参数检测
        AssertUtil.isTrue(StringUtils.isBlank(customer.getName()), "客户名称为空");
        // 补充参数
        customer.setUpdateDate(new Date());

        // 判断是添加还是更新
        if (customer.getId() == null) {
            // 添加
            customer.setCreateDate(new Date());
            customer.setIsValid(1);//有效
            customer.setState(0);//未流失
            customer.setKhno(MathUtil.genereateKhCode());// 生成客户编号
            AssertUtil.isTrue(customerDao.save(customer) < 1, CrmConstant.OPS_FAILED_MSG);
        } else {
            // 更新
            Customer customer1 = customerDao.queryById(customer.getId());
            AssertUtil.isTrue(null == customer1, CrmConstant.OPS_FAILED_MSG);
            AssertUtil.isTrue(customerDao.update(customer) < 1, CrmConstant.OPS_FAILED_MSG);
        }
    }

// 读取流失客户, 添加到客户流失表中

    public void addLossCustomers() {
        /****
         * 1. 查询出流失客户
         * 2. 批量添加到客户流失表
         * */
        List<Customer> customers = customerDao.queryLossCustomers();
        // 存储流失客户信息
        List<CustomerLoss> customerLosses = new ArrayList<>();
        // 存储客户id的集合
        List<Integer> cusIds = new ArrayList<>();

        if (!CollectionUtils.isEmpty(customers)) {
            // 遍历流失客户信息
            for (Customer customer : customers) {
                // 储存客户id
                cusIds.add(customer.getId());//

                // 有流失客户才做操作
                CustomerLoss customerLoss = new CustomerLoss();
                customerLoss.setUpdateDate(new Date());
                customerLoss.setCreateDate(new Date());
                customerLoss.setState(0);// 待流失
                //customerLoss.setLastOrderTime();
                customerLoss.setIsValid(1);
                customerLoss.setCusNo(customer.getKhno());// 客户编号
                customerLoss.setCusManager(customer.getCusManager());
                customerLoss.setCusName(customer.getName());// 客户名称
                customerLoss.setLossReason("满足流失客户预警");
                customerLosses.add(customerLoss);
            }

            // 批量添加 客户流失表
            AssertUtil.isTrue(customerLossDao.saveBatch(customerLosses) < customerLosses.size(), CrmConstant.OPS_FAILED_MSG);

            // 批量修改 客户表的状态为1 流失
            AssertUtil.isTrue(customerDao.updateCustomerBatch(cusIds) < cusIds.size(), CrmConstant.OPS_FAILED_MSG);

        }
    }
}