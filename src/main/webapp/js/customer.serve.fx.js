$(function () {

    $.ajax({
        url: ctx +"/report/queryServiceData",
        success:function (data) {
            console.log(data);
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));


            myChart.title = '客户服务分析';
            // 指定图表的配置项和数据
            var
                option = {
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b}: {c} ({d}%)"
                    },
                    legend: {
                        orient: 'vertical',
                        x: 'left',
                        data:['咨询','建议','投诉']
                    },
                    series: [
                        {
                            name:'服务类型',
                            type:'pie',
                            radius: ['50%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                normal: {
                                    show: false,
                                    position: 'center'
                                },
                                emphasis: {
                                    show: true,
                                    textStyle: {
                                        fontSize: '30',
                                        fontWeight: 'bold'
                                    }
                                }
                            },
                            labelLine: {
                                normal: {
                                    show: false
                                }
                            },
                            data:data
                        }
                    ]
                };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });

});