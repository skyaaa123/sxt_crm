package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.service.DataDicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lp on 2018/1/15.
 */
@Controller
@RequestMapping("dataDic")
public class DataDicController extends BaseController {
    @Resource
    private DataDicService dataDicService;

    @RequestMapping("queryDataDicsByDicName")
    @ResponseBody
    public List<Map<String,Object>> queryDataDicsByDicName(String dicName){
        return dataDicService.queryDataDicsByDicName(dicName);
    }
}
