function updateSaleChanceDevResult(id) {
    $.ajax({
        url:ctx+'/customerReprieve/updateCustomerLoss',
        data:{
            id: id
        },
        success:function (data) {
            if(data.code==200){
                $.messager.alert('来自crm',data.msg,'info')
            }else{
                $.messager.alert('来自crm',data.msg,'error')
            }
        }
    })
}