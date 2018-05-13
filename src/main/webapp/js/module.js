//formateGrade

function formateGrade(val) {
    if (val == 0) {
        return "根级菜单";
    }
    if (val == 1) {
        return "一级菜单";
    }
    if (val == 2) {
        return "二级菜单";
    }
}

//查询
function queryModulesByParams() {
    $('#dg').datagrid('load', {
        moduleName: $('#moduleName').val(),
        parentId: $('#pid').val(),
        grade: $('#grade').combobox('getValue'),
        optValue: $('#optValue').val()
    })
}

//添加
function openAddModuleDailog() {
    openAddOrUpdateDlg('dlg', '添加模块');
}

function saveOrUpdateModule() {
    saveOrUpdateData('fm', ctx + '/module/addOrUpdateModule', 'dlg', queryModulesByParams);
}
// 根据grade加载模块

function loadParentMeau(grade) {
    $('#parentId02').combobox({
        url: ctx + '/module/queryModulesByGrade?grade=' + grade,
        valueField: 'id',
        textField: 'module_name'
    });
}
$(function () {
    //默认不显示 父级下拉
    $('#parentMenu').hide();

    // 对菜单层级 添加点击事件
    $('#grade02').combobox({
        onSelect: function (record) {
            var grade = record.value;
            if (grade == 0) {
                $('#parentMenu').hide();
                $('#parentId02').combobox('clear');// 清除值
            } else {
                $('#parentMenu').show();
                loadParentMeau(grade - 1);
            }
        }
    });
    //重置表单
// 添加窗口关闭监听事件
    $('#dlg').dialog({
        onClose: function () {
            $('#fm').form('reset');// 重置表单
            $('#id').val('');
        }
    })
});

// 更新
function openModifyModuleDialog() {
    openModifyDialog('dg','fm','dlg','更新模块')
}

//删除
function deleteModule() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请至少选中一条记录进行删除!");
        return;
    }
    $.messager.confirm("来自crm","确定删除选中的"+rows.length+"条记录?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+'/module/deleteModule',
                data:{
                    moduleId: rows[0].id
                },
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        // 刷新datagrid
                        queryModulesByParams();
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}