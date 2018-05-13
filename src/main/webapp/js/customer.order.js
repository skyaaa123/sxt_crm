function formatState(val) {
    if(val==0){
        return "未支付";
    }else if(val==1){
        return "已支付";
    }
}

// 显示操作
function formatOpt(val, row) {
    console.log(row);
    return "<a href='javascript:openOrderDetail("+row.id+");'>查看详情</a>"
}

function openOrderDetail(orderId) {
    // 弹出窗
    openAddOrUpdateDlg('dlg','订单详情显示');
    $.ajax({
        url:ctx+'/customerOrder/queryCustomerOrderById',
        data:{
            id: orderId
        },
        success:function (data) {
            if(data.code==200){
                $('#fm').form('load',data.result);//重新加载
            }else{
                $.messager.alert("来自crm",data.msg,'error');
            }
        }
    })
    /*回显订单列表*/
    $('#dg2').datagrid('load',{
        orderId: orderId
    })
}