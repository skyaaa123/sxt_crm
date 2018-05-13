<#include "common.ftl" >
  <script type="text/javascript">
    alert('${errorMsg}');// 提示信息
    //跳转到登录页
    //判断是/main 还是其他
    if('${uri}'.indexOf('main')>-1){
        window.location.href=ctx+"/index";
    }else{
        window.parent.location.href=ctx+"/index";
    }
  </script>
