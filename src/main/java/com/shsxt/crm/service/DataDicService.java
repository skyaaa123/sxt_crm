package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.DataDicDao;
import com.shsxt.crm.po.DataDic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/1/15.
 */
@Service
public class DataDicService extends BaseService<DataDic> {
    @Resource
    private DataDicDao dataDicDao;

    public List<Map<String,Object>> queryDataDicsByDicName(String dicnName){
        return dataDicDao.queryDataDicsByDicName(dicnName);
    }
}
