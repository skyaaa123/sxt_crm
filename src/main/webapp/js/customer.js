
// 查询
function queryCustomersByParams() {
    $('#dg').datagrid('load',{
        name: $('#cusName').val(),
        khno: $('#khno').val(),
        fr: $('#fr').val()
    })
}

// 添加
function openAddCustomerDialog() {
    openAddOrUpdateDlg('dlg',"添加客户信息")
}
function saveOrUpdateCustomer() {
    saveOrUpdateData("fm",ctx+"/customer/addOrUpdateCustomer","dlg",queryCustomersByParams)
}
function openModifyCustomerDialog() {
    openModifyDialog('dg','fm','dlg',"修改客户信息")
}
/*
* 删除
* */
function deleteCustomer() {
    deleteData('dg',ctx+'/customer/deleteCustomer',queryCustomersByParams);
}

/*
 历史订单查看
 打开新tab(历史订单)
* 方法里面可以传参数,这里传的type=3,title就是对应customer_order里的“客户信息”
* 把数据回显到页面上就这样写，后台逻辑也是根据springMVC中的来写
*
*
* */

function openCustomerOtherInfo(title,type){
    //选定判断，只允许选择一条进行更新，不可以多选或者不选就进行更新操作
    var rows = $("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("from crm","请选择一条记录进行更新");
        return;
    }
    if(rows.length>1){
        $.messager.alert("from crm","只能选择一条记录进行更新");
        return;
    }
    /*获取id*/
    var cusId=rows[0].id;
    if(type==3){
        window.parent.openTab(title,ctx+'/customerOrder/index?cid='+cusId);
    }
}