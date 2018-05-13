function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/* 退出 */
function logout() {
    /*
    * 1. 提示用户,确认退出
    * 2. 清除cookie
    * 3. 跳转登录页
    * */
    $.messager.confirm("来自crm","是否要退出?",function (r) {
        if(r){
            // 清除cookie
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.removeCookie("userIdStr");
            // 提示用户系统退出
            $.messager.alert("来自crm", "系统将会在3s之后退出...");
            // 实现3s退出
            setTimeout(function () {
                window.location.href = ctx + "/index";// 回登录页
            },3000);
        }
    })
}

// 打开修改密码弹窗
function openPasswordModifyDialog() {
    $("#dlg").dialog('open');
}
// 更改密码
function modifyPassword() {
    var oldPassword = $("#oldPassword").val();
    var newPassword = $("#newPassword").val();
    var confirmPassword = $("#newPassword2").val();

    if(isEmpty(oldPassword)){
        $.messager.alert("来自crm", "旧密码不能为空");
        return;
    }
    if(isEmpty(newPassword)){
        $.messager.alert("来自crm", "新密码不能为空");
        return;
    }
    if(newPassword!=confirmPassword){
        $.messager.alert("来自crm", "两次密码不一致");
        return;
    }
    // 发送请求
    $.ajax({
        url: ctx+"/user/updateUserPwd",
        type:'post',
        data:{
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmPassword: confirmPassword
        },
        success:function (data) {
            /***
             * 1. 修改成功后, 清除cookie,重新登录
             * */
            console.log(data);
            if(data.code==200){
                // 清除cookie
                $.removeCookie("userName");
                $.removeCookie("trueName");
                $.removeCookie("userIdStr");
                $.messager.alert("来自crm", "用户密码修改成功,请重新登录!",'info',function () {
                    window.location.href = ctx + "/index";// 回登录页
                });
            }else{
                $.messager.alert("来自crm", data.msg);
            }
        }
    });

}

