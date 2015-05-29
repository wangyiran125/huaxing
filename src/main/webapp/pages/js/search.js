//var 查询条件
var searchCondition = {};

//搜车条件
var truck_condition = ["type","truckCondition","truckLength","truckLoad","isBackTruck"];

//搜仓库条件
var warehouse_condition = ["type","area","volume"];

//搜货源条件
var goods_condition = ["weight","volume","shippingType","goodsType"];
//搜索--样式切换
function updWarehouseTypeCss(obj){
	$("#warehouseType li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Warehouse();
}
function updWarehouseAreaCss(obj){
	$("#warehouseArea li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Warehouse();
}
function updWarehouseVolumeCss(obj){
	$("#warehouseVolume li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Warehouse();
}
function updTruckTypeCss(obj){
	$("#truckType li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Truck();
}
function updTruckConditionCss(obj){
	$("#truckCondition li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Truck();
}
function updTruckLengthCss(obj){
	$("#truckLength li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Truck();
}

function updTruckLoadCss(obj){
	$("#truckLoad li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Truck();
}
function updTruckPlan(obj){
	$("#truckPlan li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Truck();
}
function updShippingTypeCss(obj){
	$("#shippingType li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Goods();
}
function updGoodsTypeCss(obj){
	$("#goodsType li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Goods();
}
function updGoodsWeightCss(obj){
	$("#goodsWeight li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Goods();
}
function updGoodsVolumeCss(obj){
	$("#goodsVolume li").removeClass("choose_active");
	$(obj).addClass("choose_active");
	addSearchCondition4Goods();
}
//搜车---添加查询条件
function addSearchCondition4Truck(){
	searchCondition = {};
	if($("#departureprovince").val() != -1){
		searchCondition['departureProvinceCode'] = $("#departureprovince").val();
		searchCondition['departureCityCode'] = $("#departurecity").val();
	}
	if($("#destinationprovince").val() != -1){
		searchCondition['destinationProvinceCode'] = $("#destinationprovince").val();
		searchCondition['destinationCityCode'] = $("#destinationcity").val();
	}
	var temp = $(".choose_active");
	for(var i=0;i<temp.length;i++){
		if($(temp[i]).val() != 0){
			searchCondition[truck_condition[i]] = $(temp[i]).val();
		}
	};
	//重新加载数据
	loadTruckPlans(0,PAGE_SIZE);
}

//搜仓库---添加查询条件
function addSearchCondition4Warehouse(){
	searchCondition = {};
	var temp = $(".choose_active");
	for(var i=0;i<temp.length;i++){
		if($(temp[i]).val() != 0){
			searchCondition[warehouse_condition[i]] = $(temp[i]).val();
		}
	};
	loadWarehouses(0,PAGE_SIZE);
}

//搜货源---添加查询条件
function addSearchCondition4Goods(){
	searchCondition = {};
	if($("#departureprovince").val() != -1){
		searchCondition['departureProvinceCode'] = $("#departureprovince").val();
		searchCondition['departureCityCode'] = $("#departurecity").val();
	}
	if($("#destinationprovince").val() != -1){
		searchCondition['destinationProvinceCode'] = $("#destinationprovince").val();
		searchCondition['destinationCityCode'] = $("#destinationcity").val();
	}
	var temp = $(".choose_active");
	for(var i=0;i<temp.length;i++){
		if($(temp[i]).val() != 0){
			searchCondition[goods_condition[i]] = $(temp[i]).val();
		}
	};
	loadGoods(0,PAGE_SIZE);
}
//搜仓库页加载仓库
function loadWarehouses(from,max){
	mapNameSpace.isWarehouse = true;//如果是仓库的话，仓库不会动，地图不需要刷新
	mapNameSpace.locationArr = [];//存储仓库的位置名称,清空
	mapNameSpace.showLocations();//mapJustShow.js中的函数，每次重新下载之后重新渲染地图
	$('#warehouse-list-box').empty();
	$('#page_container').empty();
	$.ajax({
		type:'post',
		url:'../api/warehouses/search?from='+from+'&max='+max+'&t='+new Date().getTime(),
		data:JSON.stringify(searchCondition),
		contentType:'application/json',
		success:function(json){
			if(json.code == 0){
				var list = json.payload.list;
				if(! list.length > 0){
					$('#warehouse-list-box').html('<center style="padding:30px;color:#969696;">未找到符合条件的仓库！</center>');
				}else{
					if(json.payload.total > PAGE_SIZE){
						showPages(json.payload.total, from, max, "loadWarehouses", "page_container");
					}
					for (var i = 0; i < list.length; i++) {
						var wid = newGuid();
						var picture = list[i].picture;
						if(picture != null){
							picture = picture.split(',')[0];
						}else{
							picture = 'images/invalid_pic1.png';
						}
			        	sessionStorage.setItem(wid, JSON.stringify(list[i]));
			        	mapNameSpace.locationArr.push({
			        		'latitude': list[i].latitude,
			        		'longitude': list[i].longitude,
			        		'name': list[i].companyName,
			        		'extraInfo': list[i].contactName+'-电话'+list[i].contactMobile
			        	});
			        	
			        	 $('\
			        			<li>\
			                     <div class="content_list_left"><a href="warehouse_detailed.html?wid='+wid+'"><img width="125px" src="'+picture+'" /></a></div>\
			                     <div class="content_list_right">\
			                       <h2><a href="warehouse_detailed.html?wid='+wid+'">'+list[i].address+'</a></h2>\
			                       <div class="cont_right_list">\
			                         <ol>\
			                       	   <li style="padding-left:0px;">'+parseToWarehouseType(list[i].type)+'</li>\
			                           <li>面积：'+list[i].area+'平方米</li>\
			                           <li>体积：'+list[i].volume+'立方米</li>\
			                           <li>'+(list[i].isCoolStore == 0?'':(list[i].isCoolStore == 1? '有冷库':'无冷库'))+'</li>\
			                         </ol>\
			                       </div>\
			                       <p> <span  style="padding-left:0px;">发布时间：'+formatterDate(list[i].createTime)+'&nbsp;&nbsp;</span>&nbsp;&nbsp;<span>发布者：'+list[i].companyName+'</span> &nbsp;<span>联系人：'+list[i].contactName+'&nbsp;&nbsp;电话：'+list[i].contactMobile+'</span></p>\
			                     </div>\
			                     <div class="content_list_money"><a connectBtn="'+i+'" href="warehouse_detailed.html?wid='+wid+'">'+(list[i].rent == 0?"运价面议":((list[i].rent/100)+"元/平"))+'</a></div>\
			                   </li>\
			                      ').appendTo('#warehouse-list-box');
					}

					mapNameSpace.showLocations();//mapJustShow.js中的函数，每次重新下载之后重新渲染地图
				}
			}
		}
	})
}

//搜车页加载车源
function loadTruckPlans(from,max){
	mapNameSpace.isTruckPlan = true;//车
	mapNameSpace.locationArr = [];//存储车辆的位置名称,清空
	mapNameSpace.showLocations();//mapJustShow.js中的函数，每次重新下载之后重新渲染地图
	$('#truck-plan-box').empty();
	$('#page_container').empty();
	$.ajax({
		type:'post',
		url: '../api/truckplans/search?from='+from+'&max='+max+'&t='+new Date().getTime(),
		data:JSON.stringify(searchCondition),
		contentType:'application/json',
		success:function(json){
			 if(json.code == 0){
		          var list = json.payload.list;
		          if(! list.length > 0){
		        	  $('#truck-plan-box').html('<center style="padding:30px;color:#969696;">未找到符合条件的车源！</center>');
		          }else{
		        	  if(json.payload.total > PAGE_SIZE){
							showPages(json.payload.total, from, max, "loadTruckPlans", "page_container");
					  }
		        	  for (var i = 0; i < list.length; i++) {
			        	  var tid = newGuid();
			        	  sessionStorage.setItem(tid, JSON.stringify(list[i]));
			        	  mapNameSpace.locationArr.push({
			        	  	'latitude': list[i].truck.lat,
			        	  	'longitude': list[i].truck.lan,
			        	  	'name': list[i].truckLabel,
			        	  	'extraInfo':parseToDistrictName(list[i].truckPlan.departureProvinceCode)+'→'+parseToDistrictName(list[i].truckPlan.destinationProvinceCode)
			        	  });
			        	  var truckpicture = list[i].truck.truckPicture;
			    		  truckpicture = truckpicture == null ? 'images/invalid_pic1.png' : decodeURIComponent(truckpicture).split(',')[0];
			    		  var isLocalFreeTruck = list[i].truckPlan.isLocalFreeTruck;
			              var btnStr = '';
			              switch(isLocalFreeTruck){
			              		case 1:
			              			btnStr = parseToDistrictName(list[i].truckPlan.departureProvinceCode)+parseToDistrictName(list[i].truckPlan.departureCityCode)+"本地空闲车";
			              			break;
			              		case 2:
			              			btnStr = parseToDistrictName(list[i].truckPlan.departureProvinceCode)+'-'+parseToDistrictName(list[i].truckPlan.departureCityCode)+' &nbsp; <span>→</span> &nbsp;<img src="images/pic_3.png" />&nbsp;'+parseToDistrictName(list[i].truckPlan.destinationProvinceCode)+'-'+parseToDistrictName(list[i].truckPlan.destinationCityCode);
			              			break;
			              }
			        	  $('\
			                      <li>\
			                         <div class="content_list_left"><a href="goods_truck_detailed.html?tid='+tid+'"><img width="125px" src="'+truckpicture+'" /></a></div>\
			                         <div class="content_list_right">\
			                           <h2>\
			                           <a href="goods_truck_detailed.html?tid='+tid+'"><img src="images/pic_3.png" /> &nbsp;'+btnStr+'</a>\
			                                '+ (list[i].vipLevel == 1 ? '<img src="images/pic_11.png"/><span style="color: #FED042; font-size: 14px;">金卡用户</span>' : 
			                                 (list[i].vipLevel == 2 ? '<img src="images/pic_12.png"/><span style="color: #B3B6BF; font-size: 14px;">银卡用户</span>' : '')) +'\
			                            </h2>\
			                           <div class="cont_right_list">\
			                             <ol>\
			                               <li style="padding-left:0px;">'+decodeURI(list[i].truck.licensePlateNumber)+'</li>\
			                               <li>'+parseToTruckType(list[i].truck.type)+'</li>\
			                               <li>'+parseToTruckCondition(list[i].truck.truckCondition)+'</li>\
			                               <li>长'+list[i].truck.truckLength+'米</li>\
			                               <li>载重'+list[i].truck.truckLoad+'吨</li>\
			                               <li>'+(list[i].truckPlan.isBackTruck == 1 ? '回程车':'本地车')+'</li>\
			                             </ol>\
			                           </div>\
			                           <p> 常住地：'+(list[i].truck.truckStation == (null || "") ? "不详" : decodeURI(list[i].truck.truckStation))+' </span>&nbsp;&nbsp;<span>发布时间：'+formatterDate(list[i].truckPlan.publishTime)+'</span></p>\
			                           <p>说明：'+((list[i].truckPlan.remark == null || list[i].truckPlan.remark == "") ? "暂无详细说明" : decodeURI(list[i].truckPlan.remark).replace(/\+/g, ''))+'</p>\
			                         </div>\
					                  <div class="content_list_money"><a connectBtn="'+i+'" title="点击查看详情" href="goods_truck_detailed.html?tid='+tid+'">'+(list[i].truckPlan.shippingPrice == 0 ? "运价面议" : ((list[i].truckPlan.shippingPrice/100)+"元/吨"))+'</div>\
			                      </li>\
			                      ').appendTo('#truck-plan-box');
			          };
			          mapNameSpace.showLocations();//mapJustShow.js中的函数，每次重新下载之后重新渲染地图
		          }
		     }else{
		          logisticsError(json.description);
		     }
		},
        error: function(jqXHR, textStatus, errorThrown){
            ajaxError(jqXHR, textStatus, errorThrown);
        }
	})
}
//搜货物页加载货源
function loadGoods(from, max){
	mapNameSpace.isGoods = true;//货物
	mapNameSpace.locationArr = [];//存储货物的位置名称,清空
	mapNameSpace.showLocations();//mapJustShow.js中的函数，每次重新下载之后重新渲染地图
	$('#goods-list-box').empty();
	$('#page_container').empty();
    $.ajax({
      type:'post',
      url: '../api/goods/search?from='+from+'&max='+max+'&t='+new Date().getTime(),
      data:JSON.stringify(searchCondition),
	  contentType:'application/json',
      success: function(json){
        if(json.code == 0){
          var list = json.payload.list;
          if(! list.length > 0){
        	  $('#goods-list-box').html('<center style="padding:30px;color:#969696;">未找到符合条件的货源！</center>');
          }else{
        	  if(json.payload.total > PAGE_SIZE){
					showPages(json.payload.total, from, max, "loadGoods", "page_container");
			  }
              for (var i = 0; i < list.length; i++) {//TODO 删除车辆按钮的样式需要在main.css里面修改
            	  var picture = list[i].picture;
	        	  picture = (picture == null ? 'images/invalid_pic1.png' :picture.split(',')[0]);
	        	  var gid = newGuid();
	        	  sessionStorage.setItem(gid, JSON.stringify(list[i]));
	        	  mapNameSpace.locationArr.push({
	        	  	'latitude': list[i].latitude,
	        	  	'longitude': list[i].longitude,
	        	  	'name': parseToGoodsName(list[i].goodsName),
	        	  	'extraInfo':parseToDistrictName(list[i].departureProvinceCode)+'→'+parseToDistrictName(list[i].destinationProvinceCode)
	        	  });
                  $('\
                    <li>\
                      <div class="content_list_left"><a href="truck_catch_goods.html?gid='+gid+'"><img width="125px" src="'+picture+'" /></a></div>\
                      <div class="content_list_right">\
                        <h2><a href="truck_catch_goods.html?gid='+gid+'"><img src="images/pic_3.png" />&nbsp;'+parseToDistrictName(list[i].departureProvinceCode)+' ' +parseToDistrictName(list[i].departureCityCode)+' &nbsp;→  &nbsp;  <img src="images/pic_3.png" />&nbsp;'+parseToDistrictName(list[i].destinationProvinceCode)+' ' + parseToDistrictName(list[i].destinationCityCode)+'</a></h2>\
                        <div class="cont_right_list">\
                          <ol>\
                            <li style="padding-left:0px;">'+parseToGoodsName(list[i].goodsName)+' </li>\
                            <li>'+parseToGoodsType(list[i].goodsType)+' </li>\
                            <li>'+list[i].volume+'立方米 </li>\
                            <li>'+list[i].quantity+'件</li>\
                            <li>'+list[i].weight+'吨</li>\
                            <li>'+parseToShippingType(list[i].shippingType)+'</li>\
                          </ol>\
                        </div>\
                        <p> <span style="padding-left:0px;">联系人：'+list[i].contactName+'</span>&nbsp;&nbsp;&nbsp;<span>信息有效期：'+(!list[i].validity ? "不详" : (list[i].validity == -1 ? "长期货源" : list[i].validity))+'&nbsp;&nbsp;<span>发布时间：'+formatterDate(list[i].publishTime)+'</span></p>\
                        <p>'+list[i].remark+'</p>\
                      </div>\
                      <div class="content_list_money"><a connectBtn="'+i+'" href="truck_catch_goods.html?gid='+gid+'">'+(list[i].shippingPrice == (0 || -1) ? "运价面议" : ((list[i].shippingPrice/100)+"元/吨"))+'</a></div>\
                    </li>\
                    ').appendTo('#goods-list-box');
                  // list[i]
                };
                mapNameSpace.showLocations();//mapJustShow.js中的函数，每次重新下载之后重新渲染地图
          }
   
          // $('#truck-list-box')
        }else{
            logisticsError(json.description);
        }
      },
        error: function(jqXHR, textStatus, errorThrown){
            ajaxError(jqXHR, textStatus, errorThrown);
        }
    });
  }
