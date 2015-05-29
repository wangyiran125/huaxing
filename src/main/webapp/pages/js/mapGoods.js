$(function(){
    //把初次打开地图和出发地做了弱关联，建议在html页面给出发地加ID（没有也可以） 【省份的select：#departureprovince，市的select：#departurecity 具体地址的input框：#departure】
    mapNameSpace.mapClicked = false;//判断地图是不是从未点击过，用户有可能不点击地图，直接点确定
	mapNameSpace.longitude = '';
	mapNameSpace.latitude = '';
	mapNameSpace.address = '';
    function createArea(){

    	// 百度地图API功能
    	var map = new BMap.Map("allmap");
    	mapNameSpace.map = map;//在外部也可以操作map
    	map.centerAndZoom(new BMap.Point(116.404, 39.915), 15); //初始化地图
    	map.enableScrollWheelZoom();
    	map.addControl(new BMap.NavigationControl()); //平移缩放控件
    	map.addControl(new BMap.ScaleControl()); //比例尺控件
    	
    	var local = new BMap.LocalSearch(map, {
    		renderOptions:{map: map}
    	});

        local.setSearchCompleteCallback(function(ev){//百度地图搜索完成后的回调函数
            var mapCenter = null;
            if(ev && ev.Dq[0]){//有搜索内容
                mapCenter = ev.Dq[0].point;//搜索之后，地图的中心
                
            }else{//没搜索内容，默认背景
                mapCenter = map.getCenter();
            }

            if(!mapNameSpace.mapClicked){//如果从未点击过，经纬度默认存成地图中心
                mapNameSpace.longitude = mapCenter.lng;
                mapNameSpace.latitude = mapCenter.lat;
            }
        });

    	var geoc = new BMap.Geocoder();
    	var marker;
    	map.addEventListener("click", function(e){
            mapNameSpace.mapClicked = true;
    		mapNameSpace.longitude = e.point.lng;//保存经纬度及名称到全局变量mapNameSpace，提交的时候可以在logistics.js中使用
    		mapNameSpace.latitude = e.point.lat;//保存经纬度及名称到全局变量mapNameSpace，提交的时候可以在logistics.js中使用

    		map.panTo(e.point);
    		if(marker){
    			map.removeOverlay(marker);
    		}
    		marker = new BMap.Marker(e.point);
    		map.addOverlay(marker);
			geoc.getLocation(e.point, function(rs){
				$("#signarea_positionDescription").val(rs.address);
				mapNameSpace.address = rs.address;//保存经纬度及名称到全局变量mapNameSpace，提交的时候可以在logistics.js中使用
			}); 
    	});
    	$("#mapSearch").click(function(){
            var searchTxt = '';
            if(!mapNameSpace.mapClicked){//如果没确认过坐标，则取出发地的值进行搜索
                if($('#departureprovince').length && $('#departureprovince').val() != -1){
                    searchTxt+=$('#departureprovince').find("option:selected").text();
                }

                if($('#departurecity').length && $('#departurecity').val() != -1){
                    searchTxt+=$('#departurecity').find("option:selected").text();
                }

                if($('#departure').length){
                    searchTxt+=$('#departure').val();
                }

                $("#signarea_positionDescription").val(searchTxt);
            }else{
                searchTxt = $("#signarea_positionDescription").val();
            }

    		$('#map-wrap').show();
    		$('#confirmMap-wrap').show();
            
    		local.search(searchTxt);
            
    		return false;
    	});
    	$('#confirmMap-wrap').click(function(){
            mapNameSpace.mapClicked = true;
    		$('#map-wrap').hide();
    		$('#confirmMap-wrap').hide();
            if($("#signarea_positionDescription").val()!="") {
                $("#signarea_positionDescription").removeClass('promt');
            }
    	});

        //根据当前用户ip定位
        function myFun(result){
            var cityName = result.name;
            map.setCenter(cityName);
        }
        var myCity = new BMap.LocalCity();
        myCity.get(myFun);
    }

    createArea();
});