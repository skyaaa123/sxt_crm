package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.DataDic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository

public interface DataDicDao extends BaseDao<DataDic> {

    public List<Map<String,Object>> queryDataDicsByDicName(@Param("dicName") String dicnName);

}