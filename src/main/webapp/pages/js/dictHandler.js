//获取字典表数据
if(!sessionStorage.zgwzdict) {
    getDictData();
}
var data = JSON.parse(sessionStorage.zgwzdict);

//货物名称字典数据(下拉列表)
function listGoodsName(){
	var goodsNames = [];
		goodsNames = data.DictGoodsName;
	for(var i = 0; i < goodsNames.length; i++){
		$("#goodsName").append("<option value='"+goodsNames[i].code+"'>"+goodsNames[i].name+"</option>");
	}
}

//货物类型字典数据(下拉列表)
function listGoodsType(){
	var goodsTypes = [];
	goodsTypes = data.DictGoodsType;
	for(var i = 0; i < goodsTypes.length; i++){
		$("#goodsType").append("<option value='"+goodsTypes[i].code+"'>"+goodsTypes[i].name+"</option>");
	}
}

//货物类型字典数据(搜索项)
function listGoodsType4Search(){
	var goodsTypes = [];
	goodsTypes = data.DictGoodsType;
	for(var i = 0; i < goodsTypes.length; i++){
		$("#goodsType").append("<li onclick='updGoodsTypeCss(this)' value='"+goodsTypes[i].code+"' class='choose_a'>"+goodsTypes[i].name+"</li>");
	}
}


//货物重量字典数据（搜索项）
function listGoodsWeight(){
	var goodsWeights = [];
	goodsWeights = data.DictGoodsWeight;
	var name = '';
	for(var i = 0; i < goodsWeights.length; i++){
		if(i != goodsWeights.length - 1){
			name = goodsWeights[i].name+'吨';
		}else{
			name = goodsWeights[i].name+'吨以上';
		}
		$("#goodsWeight").append("<li onclick='updGoodsWeightCss(this)' value='"+goodsWeights[i].code+"' class='choose_a'>"+name+"</li>");
	}
}

//货物体积字典数据（搜索项）
function listGoodsVolume(){
	var goodsVolumes = [];
	goodsVolumes = data.DictGoodsVolume;
	var name = '';
	for(var i = 0; i < goodsVolumes.length; i++){
		if(i != goodsVolumes.length - 1){
			name = goodsVolumes[i].name+'立方米';
		}else{
			name = goodsVolumes[i].name+'立方米以上';
		}
		$("#goodsVolume").append("<li onclick='updGoodsVolumeCss(this)' value='"+goodsVolumes[i].code+"' class='choose_a'>"+name+"</li>");
	}
}

//运输类型字典数据（下拉列表）
function listShippingType(){
	var shippingTypes = [];
	shippingTypes = data.DictShippingType;
	for(var i = 0; i < shippingTypes.length; i++){
		$("#shippingType").append("<option value='"+shippingTypes[i].code+"'>"+shippingTypes[i].name+"</option>");
	}
}
//运输类型字典数据（搜索项）
function listShippingType4Search(){
	var shippingTypes = [];
	shippingTypes = data.DictShippingType;
	for(var i = 0; i < shippingTypes.length; i++){
		$("#shippingType").append("<li onclick='updShippingTypeCss(this)' value='"+shippingTypes[i].code+"' class='choose_a'>"+shippingTypes[i].name+"</li>");
	}
}

//仓库类型字典数据
function loadWarehouseType_sel(){
	var warehouseTypes = [];
	warehouseTypes = data.DictWarehouseType;
	for(var i = 0; i < warehouseTypes.length; i++){
		$("#warehouseType").append("<option value='"+warehouseTypes[i].code+"'>"+warehouseTypes[i].name+"</option>");
	}
}

//仓库类型字典数据
function viewWarehouseType(){
	var warehouseTypes = [];
	warehouseTypes = data.DictWarehouseType;
	for(var i = 0; i < warehouseTypes.length; i++){
		$("#warehouseType").append("<li onclick='updWarehouseTypeCss(this)' value = '"+warehouseTypes[i].code+"'class='choose_a'>"+warehouseTypes[i].name+"</li>");
	}
}


//仓库面积字典数据
function viewWarehouseArea(){
	var warehouseAreas = [];
	warehouseAreas = data.DictWarehouseArea;
	var name = '';
	for(var i = 0; i < warehouseAreas.length; i++){
		if(i != warehouseAreas.length-1){
			name = warehouseAreas[i].name+'平方米'
		}else{
			name = warehouseAreas[i].name+'平方米以上'
		}
		$("#warehouseArea").append("<li onclick='updWarehouseAreaCss(this)' value = '"+warehouseAreas[i].code+"'class='choose_a'>"+name+"</li>");
	}
}

//仓库容量字典数据
function viewWarehouseVolume(){
	var warehouseVolumes = [];
	warehouseVolumes = data.DictWarehouseVolume;
	var name = '';
	for(var i = 0; i < warehouseVolumes.length; i++){
		if(i != warehouseVolumes.length - 1){
			name = warehouseVolumes[i].name+'立方米'
		}else{
			name = warehouseVolumes[i].name+'立方米以上'
		}
		$("#warehouseVolume").append("<li onclick='updWarehouseVolumeCss(this)' value = '"+warehouseVolumes[i].code+"'class='choose_a'>"+name+"</li>");
	}
}

//车辆类型字典数据
function viewTruckType(){
	var truckTypes = [];
	truckTypes = data.DictTruckType;
	for(var i = 0; i < truckTypes.length; i++){
		$("#truckType").append("<li onclick='updTruckTypeCss(this)' value = '"+truckTypes[i].code+"'class='choose_a'>"+truckTypes[i].name+"</li>");
	}
}

//车主添加车辆页面 车辆类型字典数据
function chezhuViewTruckType(){
	var truckTypes = [];
	truckTypes = data.DictTruckType;
	for(var i = 0; i < truckTypes.length; i++){
		$("#truckType").append('<option value="'+truckTypes[i].code+'">'+truckTypes[i].name+'</option>');
	}
}

//车辆状况字典数据
function viewTruckCondition(){
	var truckConditions = [];
	truckConditions = data.DictTruckCondition;
	var name = '';
	for(var i = 0; i < truckConditions.length; i++){
		if(i != truckConditions.length - 1){
			
		}
		$("#truckCondition").append("<li onclick='updTruckConditionCss(this)' value = '"+truckConditions[i].code+"'class='choose_a'>"+truckConditions[i].name+"</li>");
	}
}

//车主添加车辆页面 车辆状况字典数据
function chezhuViewTruckCondition(){
	var truckConditions = [];
	truckConditions = data.DictTruckCondition;
	var name = '';
	for(var i = 0; i < truckConditions.length; i++){
		if(i != truckConditions.length - 1){
			
		}
		$("#truckCondition").append('<option value="'+truckConditions[i].code+'">'+truckConditions[i].name+'</option>');
	}
}
//车辆长度字典数据
function viewTruckLength(){
	var truckLengths = [];
	truckLengths = data.DictTruckLength;
	var name = '';
	for(var i = 0; i < truckLengths.length; i++){
		if(i != truckLengths.length - 1){
			name = truckLengths[i].name+'米';
		}else{
			name = truckLengths[i].name+'米以上';
		}
		$("#truckLength").append("<li onclick='updTruckLengthCss(this)' value = '"+truckLengths[i].code+"'class='choose_a'>"+name+"</li>");
	}
}



//车辆载重字典数据
function viewTruckLoad(){
	var truckLoads = [];
	truckLoads = data.DictTruckLoad;
	var name= '';
	for(var i = 0; i < truckLoads.length; i++){
		if(i != truckLoads.length - 1){
			name = truckLoads[i].name+'吨';
		}else{
			name = truckLoads[i].name+'吨以上';
		}
		$("#truckLoad").append("<li onclick='updTruckLoadCss(this)' value = '"+truckLoads[i].code+"'class='choose_a'>"+name+"</li>");
	}
}

//字典表数据转换--货物名(通用的如何写)
function parseToGoodsName(code){
	var name = "";
	for(var i = 0; i < data.DictGoodsName.length; i++){
		if(code == data.DictGoodsName[i].code){
			name = data.DictGoodsName[i].name;
			break;
		}
	}
	return name;
}

//货物类型
function parseToGoodsType(code){
	var name = "";
	for(var i = 0; i < data.DictGoodsType.length; i++){
		if(code == data.DictGoodsType[i].code){
			name = data.DictGoodsType[i].name;
			break;
		}
	}
	return name;
}

//货物重量
function parseToGoodsWeight(code){
	var name = "";
	for(var i = 0; i < data.DictGoodsWeight.length; i++){
		if(code == data.DictGoodsWeight[i].code){
			name = data.DictGoodsWeight[i].name;
			break;
		}
	}
	return name;
}

//货物体积
function parseToGoodsVolume(code){
	var name = "";
	for(var i = 0; i < data.DictGoodsVolume.length; i++){
		if(code == data.DictGoodsVolume[i].code){
			name = data.DictGoodsVolume[i].name;
			break;
		}
	}
	return name;
}


//货物类型
function parseToShippingType(code){
	var name = "";
	for(var i = 0; i < data.DictShippingType.length; i++){
		if(code == data.DictShippingType[i].code){
			name = data.DictShippingType[i].name;
			break;
		}
	}
	return name;
}
//仓库类型
function parseToWarehouseType(code){
	var name = "";
	for(var i = 0; i < data.DictWarehouseType.length; i++){
		if(code == data.DictWarehouseType[i].code){
			name = data.DictWarehouseType[i].name;
			break;
		}
	}
	return name;
}

//车辆类型
function parseToTruckType(code){
	var name = "";
	for(var i = 0; i < data.DictTruckType.length; i++){
		if(code == data.DictTruckType[i].code){
			name = data.DictTruckType[i].name;
			break;
		}
	}
	return name;
}

//车辆状况
function parseToTruckCondition(code){
	var name = "";
	for(var i = 0; i < data.DictTruckCondition.length; i++){
		if(code == data.DictTruckCondition[i].code){
			name = data.DictTruckCondition[i].name;
			break;
		}
	}
	return name;
}

//车辆长度
function parseToTruckLength(code){
	var name = "";
	for(var i = 0; i < data.DictTruckLength.length; i++){
		if(code == data.DictTruckLength[i].code){
			name = data.DictTruckLength[i].name;
			break;
		}
	}
	return name;
}

//车辆载重
function parseToTruckLoad(code){
	var name = "";
	for(var i = 0; i < data.DictTruckLoad.length; i++){
		if(code == data.DictTruckLoad[i].code){
			name = data.DictTruckLoad[i].name;
			break;
		}
	}
	return name;
}



function loadDistrict(code,obj){
	$.ajax({
		type:'get',
		url:'../api/dicts/DictDistrict/'+code,
		async:false, //改为同步
		success:function(data){
			if(data.code == 0 && data.payload){
				data = data.payload;
				for(var i = 0; i < data.length; i++){
					obj.append("<option value='"+data[i].code+"'>"+data[i].name+"</option>");
				}
			}else{
				logisticsError(data.description);
			}
		},
        error: function(jqXHR, textStatus, errorThrown){
            ajaxError(jqXHR, textStatus, errorThrown);
        }
	})
};

function parseToDistrictName(code){
	var name = "";
	for(var i = 0; i < data.DictDistrict.length; i++){
		if(code == data.DictDistrict[i].code){
			name = data.DictDistrict[i].name;
			break;
		}
	}
	return name;
}

//修改下拉框的默认值
function updSelectDefualt(sel,val){
	sel.each(function(){
		if($(this).val() == val){
			$(this).attr("selected","true");
			//跳出循环
			return false;
		}
	})	
}
//注意事项
var notice = [
              {"code":1,"name":"向上"},
              {"code":2,"name":"防潮"},
              {"code":3,"name":"易碎"},
              {"code":4,"name":"危险品"}
             ];

