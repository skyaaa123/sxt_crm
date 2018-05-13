function formateState(val) {
    if(val==0){
        return "未支付";
    }else if(val==1){
        return "已支付";
    }
}
// formateOp
function formateOp(val,row) {
    var state=row.state;
    if(state==0){
        var  href="javascript:openAddReprieveDataTab("+row.id+")";
        return "<a href="+href+">添加暂缓<a>";
    }else{
        return "确认流失";
    }
}

function openAddReprieveDataTab(lossId) {
    window.parent.openTab("添加暂缓措施_"+lossId,ctx+"/customerReprieve/index?lossId="+lossId);
}

//添加暂缓操作
/*function formateOp(val,row){
    var state = row.state;
    if(state==0){
        return "<a href='javascript:openAddReprieveDataTab'('+row.id+')>添加暂缓</a>";
    }else{
        return "确认流失";
    }
}*/


//查询
function queryCustomerLossByParams() {
    $('#dg').datagrid('load',{
        cusName: $('#cusName').val(),
        cusNo: $('#cusNo').val(),
        time: $('#time').datebox('getValue')
    });
}