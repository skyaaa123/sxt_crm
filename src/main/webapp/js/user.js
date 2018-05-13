
$(function () {
    // 添加窗口关闭监听事件
    $('#dlg').dialog({
        onClose: function () {
            $('#fm').form('reset');// 重置表单
            $('#id').val('');
        }
    })
})

/* 查询 */
function queryUsersByParams() {
    $('#dg').datagrid('load',{
        userName: $('#userName').val(),
        email: $('#email').val(),
        phone: $('#phone').val()
    })
}

/* 添加 */
function openAddUserDailog() {
    openAddOrUpdateDlg('dlg','添加用户');
}
function saveOrUpdateUser() {
    saveOrUpdateData('fm',ctx+'/user/addOrUpdateUser','dlg',queryUsersByParams);
}
/* 更新 */
function openModifyUserDialog() {
    openModifyDialog('dg','fm','dlg',"更新用户");
}

/* 删除 */
function deleteUser() {
    deleteData('dg',ctx+'/user/delUser',queryUsersByParams);
}