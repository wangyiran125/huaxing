Highcharts.setOptions({
	lang: {
		months: ['一月', '二月', '三月', '四月', '五月', '六月',  '七月', '八月', '九月', '十月', '十一月', '十二月'],
		weekdays: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
		contextButtonTitle: '报表导出',
		downloadJPEG: '保存为JPEG图片',
		downloadPDF: '保存为PDF文件',
		downloadPNG: '保存为PNG图片',
		downloadSVG: '保存为SVG文件',
		printChart: '打印',
		resetZoom: '原始大小',
		resetZoomTitle: '返回原始大小',
		noData: '暂无数据'
	}
});
var options = {
	chart: {
	    renderTo:'',
	    type: 'column'
	},
	credits:{ //版权信息，不显示
		enabled: false
	},
	title: {
	    text: ''
	},
	tooltip: {
	  pointFormat: '{series.name}: <b>{point.y}</b>'
	},
	plotOptions:{
		column : {
			cursor:'pointer',
			events:{
				click:function(e){
					var url="";
					if(e.point.name=="抢单数"){
						url="chart_goods_goodsIntentNum.html?btntype="+btntype+"";
						location.href=encodeURI(url);
						location.href.target="right";
					}
					if (e.point.name=="成单数") {
						url="chart_order_ordernum.html?btntype="+btntype+"";
						location.href=encodeURI(url);
						location.href.target="right";
					}
					if(e.point.name=="发货数"){
						url="chart_goods_goodsnum.html?btntype="+btntype+"";
						location.href=encodeURI(url);
						location.href.target="right";
					}
				}
			}
		},
		series:{
			dataLabels: {
				enabled: true
			}
		}
	},
	series: [],
	xAxis: {
        categories: []
    },
	yAxis: {
       	allowDecimals: false,
       	floor: 0,
       	title: {
       		text: ''
       	}
   }
};
var chart;
var series;
var pieSeries;
var xCategories;
var pieXCategories;
var btntype="all";
function getChart(randerTo, title, url, yTitle){
	options.chart.renderTo = randerTo;
	options.title.text = title;
	options.yAxis.title.text = yTitle;
	getExportData(url);
}

function getExportData(url){
	$.ajax({
        type:'get',
        url:url,
        contentType:'application/json',
        success:function(json){
           if(json.code == 0){
	           	if(getUrlParam(url,'type')!=""&&getUrlParam(url,'type')!=null){
	           		btntype=getUrlParam(url,'type');
	           	}
        	   var exportData = json.payload;
        	   xCategories = exportData.xCategories;
        	   series = exportData.series;
        	   makePieSeries(series, true);
//        	   if(options.chart.type == 'pie'){
        		   
        		   options.series = pieSeries;
        		   options.xAxis.categories = pieXCategories;
//        	   }else{
//        		   options.series = series;
//        		   options.xAxis.categories = xCategories;
//        		   
//        	   }
				
        	   chart = new Highcharts.Chart(options);
           }
        },
        error: function(){
            alert("服务器异常，请稍后尝试！")
        }
    })
}

function makePieSeries(series, chang){
	pieSeries = series;
	pieXCategories = xCategories;
	if(chang && series.length > 1){
		pieXCategories = new Array();
		var pieDatas = new Array();
		for(var i = 0; i < series.length; i++){
			var s = series[i];
			pieXCategories[i] = s.name;
			var datas = s.data;
			var count = 0;
			for(var j = 0; j < datas.length; j++){
				count += datas[j].y;
			}
			pieDatas[i] = {'name': s.name, 'y': count};
		}
		pieSeries = [{name: options.title.text, data: pieDatas}];
	}
}

$(document).ready(function(){
	$("button.change").click(function(){
		var type = $(this).attr("name");
		
//		if(type == "pie") {
//			makePieSeries(series, true);
 		   	options.series = pieSeries;
 		   	options.xAxis.categories = pieXCategories;
			options.tooltip.pointFormat = '{series.name}: <b>{point.y}</b>';
//		}else {
//			options.series = series;
// 		   	options.xAxis.categories = xCategories;
//			options.tooltip.pointFormat = '{series.name}: <b>{point.y}</b>';
//		}
		options.chart.type = type;
		chart = new Highcharts.Chart(options);
	});
	$(".btn, .btn-primary").click(function(){
		$(this).removeClass("btn");
		$(this).addClass("btn-primary");
		$(this).siblings(".btn-primary").each(function(){
			$(this).removeClass("btn-primary");
			$(this).addClass("btn");
		});
	});
});

