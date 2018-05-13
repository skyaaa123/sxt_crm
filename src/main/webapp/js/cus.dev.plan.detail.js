$(function () {
    var saleChanceId = $('#saleChanceId').val();
    $('#dg').edatagrid({
        url: ctx + '/cusDev/queryCusDevplans?saleChanceId=' + saleChanceId,
        saveUrl: ctx + '/cusDev/addOrUpdateCusDevplan?saleChanceId=' + saleChanceId,
        updateUrl: ctx + '/cusDev/addOrUpdateCusDevplan?saleChanceId=' + saleChanceId
        // destroyUrl: ...
    });
    // 页面加载判断是否是 开发成功或者失败
    var devResult = $('#devResult').val();
    if(devResult==2 || devResult==3){
        $('#toolbar').hide();// 隐藏工具栏
        $('#dg').edatagrid('disableEditing');//禁用表格编辑功能
    }
});

// 添加1行
function addRow() {
    $('#dg').edatagrid('addRow');
}

// 保存数据
function saveOrUpdateCusDevPlan() {
    $('#dg').edatagrid('saveRow');
    $('#dg').edatagrid('load');//刷新数据
}

// 批量删除
function delCusDevPlan() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length == 0) {
        $.messager.alert('来自crm', "请选择一条数据进行删除");
        return;
    }

    $.messager.confirm('来自crm', '确认删除选中数据?', function (r) {
        if (r) {
            // 拼接id
            var ids = '';
            for (var i = 0; i < rows.length; i++) {
                ids += "ids=" + rows[i].id + "&"
            }
            ids = ids.slice(0, ids.length - 1);
            // 开始发送ajax
            $.ajax({
                url: ctx + "/cusDev/delCusDevplan",
                data: ids,
                success: function (data) {
                    if (data.code == 200) {
                        $.messager.alert('来自crm', data.msg, 'info', function () {
                            $('#dg').datagrid('load');// 刷新数据
                        });
                    } else {
                        $.messager.alert('来自crm', data.msg);
                    }
                }
            })
        }
    });
}

//更新开发结果
function updateSaleChanceDevResult(devResult) {
    var saleChanceId = $('#saleChanceId').val();
    $.ajax({
        url: ctx +'/saleChance/addOrUpdateSaleChance',
        type:'post',
        data:{
            id: saleChanceId,
            devResult:devResult
        },
        success:function (data) {
            if (data.code == 200) {
                $.messager.alert('来自crm', data.msg, 'info', function () {
                    $('#toolbar').hide();// 隐藏工具栏
                    $('#dg').edatagrid('disableEditing');//禁用表格编辑功能
                });
            } else {
                $.messager.alert('来自crm', data.msg);
            }
        }
    })
}