
$(function () {
    var day = [0,0,0,0,0,0,0];
    var point = [0,0,0,0,0,0,0]
    $.ajax({
        url:"lasyWeekPoint",
        contentType:'application/json;charset=UTF-8',
        dataType:"json",
        success:function(result){
            console.info(result.data);
            var data=result.data;
            $.each(data,function (index,data) {
                var days=data.createTimeStr;
                var points=data.point;
                var arr = days.split("-");
                var arrs=(arr[1]+'.'+arr[2])
                day[index]=arrs;
                point[index]=points;

            });




            var lineChart = echarts.init(document.getElementById("echarts-line-chart"));
            var lineoption = {
                title : {
                    text: ''
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['最高能量值']
                },
                grid:{
                    x:40,
                    x2:40,
                    y2:24
                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : [day[6],day[5],day[4],day[3],day[2],day[1],day[0]]
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        axisLabel : {
                            formatter: '{value}'
                        }
                    }
                ],
                series : [
                    {
                        name:'最高能量值',
                        type:'line',
                        data:[point[6], point[5], point[4], point[3], point[2], point[1], point[0]],
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },

                ]
            };

            lineChart.setOption(lineoption);
            $(window).resize(lineChart.resize);













        }});







});
/*
$(function () {
    var names = new Array();
    var daypoint = new Array();
    var allPoint = new Array();
    $.ajax({
        url:"/teamMemberRecord",
        contentType:'application/json;charset=UTF-8',
        dataType:"json",
        success:function(result){
            console.info(result);
            var data=result.data;
            $.each(data,function (index,data) {
                names[index]=data.name;
                daypoint[index]=data.point;
                allPoint[index]=data.allPoint;
            });

            var barChart = echarts.init(document.getElementById("echarts-bar-chart"));
            var baroption = {
                title : {
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['蒸发量','降水量']
                },
                grid:{
                    x:30,
                    x2:40,
                    y2:24
                },
                calculable : true,

                xAxis : [
                    {
                        type : 'category',
                        data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'蒸发量',
                        type:'bar',
                        data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                        markPoint : {
                            data : [
                                {type : 'max', name: '最大值'},
                                {type : 'min', name: '最小值'}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        name:'降水量',
                        type:'bar',
                        data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                        markPoint : {
                            data : [
                                {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                                {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                            ]
                        },
                        markLine : {
                            data : [
                                {type : 'average', name : '平均值'}
                            ]
                        }
                    }
                ]
            };
            barChart.setOption(baroption);
            window.onresize = barChart.resize;







        }});






});*/
