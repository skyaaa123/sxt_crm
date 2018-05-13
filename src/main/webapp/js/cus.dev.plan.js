function formatDevResult(devResult) {
    if (devResult == 0) {
        return "未开发";
    } else if (devResult == 1) {
        return "开发中";
    } else if (devResult == 2) {
        return "开发成功";
    } else if (devResult == 3) {
        return "开发失败";
    }
}

function formatOp(val, row) {
    var devResult = row.devResult;
    if (devResult == 0 || devResult == 1) {
        /*<a href="javascript:openTab('营销机会管理','saleChance/index/0','icon-yxjhgl')">营销机会管理</a>*/
        return "<a href=\"javascript:openSaleChanceInfoDialog('开发机会数据',"+row.id+")\">开发</a>";
    }else if (devResult == 2 || devResult == 3) {
        return "<a href=\"javascript:openSaleChanceInfoDialog('详情机会数据',"+row.id+")\">查看详情</a>";
    }
}

// 打开新tab
function openSaleChanceInfoDialog(title, sid) {
    window.parent.openTab(title+'_'+sid,ctx+"/cusDev/index/"+sid);
}

// 查询
function querySaleChancesByParams() {
    $('#dg').datagrid('load', {
        customerName: $('#customerName').val(),
        devResult: $('#devResult').combobox('getValue'),
        createDate: $('#time').datebox('getValue')
    });
}