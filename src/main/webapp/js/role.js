
//查询
function queryRolesByParams() {
    $('#dg').datagrid('load',{
        roleName: $('#roleName').val(),
        createDate:$('#time').datebox('getValue')
    });

    // 添加窗口关闭监听事件
    $('#dlg').dialog({
        onClose: function () {
            $('#fm').form('reset');// 重置表单
            $('#id').val('');
        }
    })
}

//添加
function openAddRoleDailog() {
    openAddOrUpdateDlg('dlg', '添加角色')
}

function saveOrUpdateRole() {
    saveOrUpdateData('fm',ctx+'/role/addOrUpdateRole','dlg',queryRolesByParams);
}

//更新
function openModifyRoleDialog() {
    openModifyDialog('dg','fm','dlg','修改角色');
}

// 分配模块权限
function loadRoleModules(id) {

    // 存储角色id
    $('#roleId').val(id);

    $.ajax({
        url: ctx + '/role/queryAllModuleByRoleId?roleId='+id,
        success:function (data) {

            // 初始化zTree
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"} // 勾选和不勾选,同时级联父子模块
                },
                data: {
                    simpleData: {
                        enable: true //采取简单json数据模式
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck // 每次选择记录选中的模块节点
                }
            };
            zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, data);
        }
    });
}

function zTreeOnCheck(event, treeId, treeNode) {
    var nodes = zTreeObj.getCheckedNodes(true);
    $('#moduleIds').val('');
    if(nodes.length>0){
        var ids="moduleIds=";
        for(var i=0;i<nodes.length;i++){
            var node= nodes[i];
            if(i<=nodes.length-2){
                ids=ids+node.id+"&moduleIds=";
            }else{
                ids=ids+node.id;
            }
        }
        $("#moduleIds").val(ids);
    }

}
function openRelationPermissionDialog() {
    //逻辑判断
    var rows = $('#dg').datagrid('getSelections');
    if(rows.length==0){
        $.messager.alert('来自crm',"请选择一条数据进行授权");
        return;
    }
    if(rows.length>1){
        $.messager.alert('来自crm',"只能选择一条数据进行授权");
        return;
    }

    // 加载角色模块权限
    loadRoleModules(rows[0].id);

    openAddOrUpdateDlg('permissionDlg', '权限分配');
}
//授权
function doGrant() {
    $.ajax({
        url: ctx +'/permission/savePermission?roleId='+$('#roleId').val()+'&'+$('#moduleIds').val(),
        success:function (data) {
            if(data.code==200){
                $.messager.alert('来自crm',data.msg,'info',function () {
                    // 关闭窗口, 清除roleId和moduleIds
                    closeDlgData('permissionDlg');
                    $('#roleId').val('');
                    $('#moduleIds').val('');
                });
            }else{
                $.messager.alert('来自crm',data.msg);
            }
        }
    })
}

// 删除角色
function deleteRole() {
    deleteData('dg',ctx+'/role/deleteRoles',queryRolesByParams);
}

















