/* 格式化数据显示 */
function formatState(state) {
    if (state == 0) {
        return "未分配";
    } else if (state == 1) {
        return "已分配";
    }
}
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

// 页面加载完成之后执行
$(function () {
    $('#dg').datagrid({
        rowStyler: function (index, row) {
            var devResult = row.devResult;
            if (devResult == 0) {
                return "background-color:#5bc0de;"; // 蓝色
            } else if (devResult == 1) {
                return "background-color:#f0ad4e;"; // 黄色
            } else if (devResult == 2) {
                return "background-color:#5cb85c;"; // 绿色
            } else if (devResult == 3) {
                return "background-color:#d9534f;"; // 红色
            }
        }
    });
    // 添加窗口关闭监听事件
    $('#dlg').dialog({
        onClose: function () {
            $('#fm').form('reset');// 重置表单
            $('#id').val('');
        }
    })
});

// 查询
function querySaleChancesByParams() {
    $('#dg').datagrid('load', {
        customerName: $('#customerName').val(),
        state: $('#state').combobox('getValue'),
        devResult: $('#devResult').combobox('getValue'),
        createDate: $('#time').datebox('getValue')
    });
}
// 添加
function openAddSaleChacneDialog() {
    openAddOrUpdateDlg('dlg',"添加营销机会");
}
// 保存或者更新
function saveOrUpdateSaleChance() {
    saveOrUpdateData('fm', ctx + '/saleChance/addOrUpdateSaleChance','dlg',querySaleChancesByParams);
}
// 更新
function openModifySaleChanceDialog() {
    openModifyDialog('dg','fm','dlg','更新营销机会');
}
//删除
function deleteSaleChance() {
    deleteData('dg',ctx+"/saleChance/deleteSaleChance",querySaleChancesByParams);
}
















