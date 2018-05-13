/* 登录 */
function login() {
    /***
     * 1. 获取参数
     * 2. 验证参数是否为空
     * 3. 发送ajax请求
     * 4. 做回调处理: 存放cookie, 跳转到主页
     * */
    var userName = $("#username").val();// 获取用户名
    var userPwd = $("#password").val();// 获取密码

    if(isEmpty(userName)){
        alert('用户名为空!');
        return;
    }
    if(isEmpty(userPwd)){
        alert('密码为空!');
        return;
    }

    $.ajax({
        url:ctx+"/user/login",// 请求登录地址
        type:"post",// post请求
        data:{userName:userName, userPwd:userPwd},//请求参数
        success:function (data) {
            console.log(data);
            if(data.code == 200){
                var result = data.result;
                // 把返回信息放入cookie
                $.cookie("userName",result.userName);
                $.cookie("trueName",result.trueName);
                $.cookie("userIdStr",result.userIdStr);
                alert(data.msg);
                // 跳转
                window.location.href = ctx + "/main";
            }else{
                alert(data.msg);
            }
        }
    });
}