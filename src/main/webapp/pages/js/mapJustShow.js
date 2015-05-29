$(function(){
    function createArea(){

    	// 百度地图API功能
    	var map = new BMap.Map("allmap");
    	mapNameSpace.map = map;//在外部也可以操作map
    	map.centerAndZoom(new BMap.Point(116.404, 39.915), 15); //初始化地图
    	map.enableScrollWheelZoom();
    	map.addControl(new BMap.NavigationControl()); //平移缩放控件
    	map.addControl(new BMap.ScaleControl()); //比例尺控件

        function showLocations(){
            map.clearOverlays();
            for (var i = 0, len=mapNameSpace.locationArr.length; i < len; i++) {
              if(!$.isNumeric(mapNameSpace.locationArr[i].longitude) || mapNameSpace.locationArr[i].longitude ==0){
                //数字0或不是数字，不在地图上展示
                continue;
              }
              (function(i){
                var pNow = mapNameSpace.locationArr[i];
                
                var point = new BMap.Point(pNow.longitude, pNow.latitude),
                    marker = new BMap.Marker(point);
                map.addOverlay(marker);

                marker.addEventListener("click",function(){
                  var $connectBtn = $('[connectBtn='+i+']');
                  $connectBtn.trigger('click');
                  if($connectBtn.attr('href')){
                    window.open($connectBtn.attr('href'),'_self');
                  }
                });

                marker.addEventListener("mouseover",function(){
                  toggleLabel(pNow.name+'|'+pNow.extraInfo);
                });

                marker.addEventListener("mouseout",function(){
                  toggleLabel(pNow.name);
                });

                var label = null;

                function toggleLabel(text){
                  map.removeOverlay(label);
                  //添加除气球外的文字标注
                  var opts = {
                    position : point,    // 指定文本标注所在的地理位置
                    offset   : new BMap.Size(10, -30)    //设置文本偏移量
                  }

                  label = new BMap.Label(text, opts);  // 创建文本标注对象
                  label.setStyle({
                       color : "red",
                       fontSize : "12px",
                       height : "20px",
                       lineHeight : "20px",
                       fontFamily:"微软雅黑"
                   });
                  map.addOverlay(label);
                }

                toggleLabel(pNow.name);

                if(i==len-1){
                  map.panTo(point);
                }
              })(i);
                
            };
            
        }

        mapNameSpace.showLocations = showLocations;//方法暴露在外部，每次重新下载之后可以重新渲染地图
    }

    createArea();
});