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
            /*   var devResult = row.devResult;
             if (devResult == 0){
             return 'background-color:blue;';
             }else if (devResult == 1){
             return 'background-color:yellow;';
             }else if (devResult == 2){
             return 'background-color:green;';
             }else if (devResult == 3){
             return 'background-color:red;';
             }*/

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
    $('#dlg').dialog('open');
}
// 保存或者更新(添加或者更新时提交的按钮)
function saveOrUpdateSaleChance() {
    $('#fm').form('submit', {
        url: ctx + '/saleChance/addOrUpdateSaleChance',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            data = JSON.parse(data);// 解析json数据
            console.log(data);
            if(data.code==200){
                $.messager.alert('来自crm',data.msg,'info',function () {
                    /*
                    * 1. 关闭窗口
                    * 2. 重置表单
                    * 3. 刷新数据
                    * */
                    $('#dlg').dialog('close');// 关闭窗口
                    // $('#fm').form('reset');// 重置表单
                    $('#dg').datagrid('load');// 刷新数据
                })
            }
        }
    });
}
// 更新
function openModifySaleChanceDialog() {
    /***
     * 判断用户是否选中和选择多条
     * */
    var rows = $('#dg').datagrid('getSelections');
    console.log(rows);
    if(rows.length==0){
        $.messager.alert('来自crm',"请选择一条数据进行更新");
        return;
    }
    if(rows.length>1){
        $.messager.alert('来自crm',"只能选择一条数据进行更新");
        return;
    }
    // 打开弹出窗, 回显数据
    $('#dlg').dialog('open');
    $("#fm").form('load',rows[0]);
}
//删除
function deleteSaleChance() {
    var rows = $('#dg').datagrid('getSelections');
    if(rows.length==0){
        $.messager.alert('来自crm',"请选择一条数据进行更新");
        return;
    }

    $.messager.confirm('来自crm', '确认删除选中数据?',function (r) {
        if(r){
            // 拼接id
            var ids = '';
            for(var i=0; i<rows.length; i++){
                ids += "ids="+rows[i].id+"&"
            }
            ids = ids.slice(0,ids.length-1);
            console.log(ids)

            // 开始发送ajax
            $.ajax({
                url: ctx+"/saleChance/deleteSaleChance",
                data: ids,
                success:function (data) {
                    if(data.code==200){
                        $.messager.alert('来自crm',data.msg,'info',function () {
                            $('#dg').datagrid('load');// 刷新数据
                        });
                    }else{
                        $.messager.alert('来自crm',data.msg);
                    }
                }
            })
        }
    });

}
















