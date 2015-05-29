function loadTruckList(from, max){//下载我的车辆列表
  $('#truck-list-box').empty();
  $('#truck_page_container').empty();
  $.ajax({
    url: '../api/trucks?from='+from+'&max='+max+'&t='+new Date().getTime(),
    success: function(json){
      if(json.code == 0){
        var list = json.payload.list;
        conf.truckList = list;
        if(!list.length > 0){
    		 $('#truck-list-box').html('<center style="padding:30px;color:#969696;">您还没有添加车辆！</center>');
    	}else{
    		 for (var i = 0; i < list.length; i++) {//TODO 删除车辆按钮的样式需要在main.css里面修改
    			 if(json.payload.total > PAGE_SIZE){
    				 showPages(json.payload.total, from, max, "loadTruckList", "truck_page_container");
		     	 }
    			 var truckpicture = list[i].truckPicture;
    			 truckpicture = truckpicture == null ? 'images/invalid_pic1.png' : decodeURIComponent(truckpicture).split(',')[0];
           var statusStr = '';
           switch(list[i].truckStatus){
            case 1:
              statusStr = '空载';
              break;
            case 2:
              statusStr = '半载';
              break;
            case 3:
              statusStr = '满载';
              break;
           }
    	          $('\
    	              <li>\
    	                <div class="content_list_left"><a href="./truck_edit.html?id='+list[i].id+'"><img width="125px" src="'+truckpicture+'" /></a></div>\
    	                <div class="content_list_right">\
    	                  <div class="cont_right_list">\
    	                    <ol>\
    	                      <li style="padding-left:0px;">'+decodeURI(list[i].licensePlateNumber)+'</li>\
    	                      <li>'+parseToTruckType(list[i].type)+'</li>\
    	                      <li>'+parseToTruckCondition(list[i].truckCondition)+'</li>\
    	                      <li>长'+decodeURI(list[i].truckLength)+'米</li>\
    	                      <li>载重'+decodeURI(list[i].truckLoad)+'吨</li>\
                            <li>状态：'+statusStr+'</li>\
    	                    </ol>\
    	                  </div>\
    	                  <p> 司机：'+decodeURI(list[i].patcher)+'&nbsp;&nbsp;常住地：'+((list[i].truckStation == null || list[i].truckStation == "") ? "不详" : decodeURI(list[i].truckStation))+' </p>\
    	                </div>\
    	                <div class="mycar_xiangqing" data-index="'+i+'"><a href="./truck_edit.html?id='+list[i].id+'">修改信息</a></div>\
    	                <div class="mycar_lost" data-role="delete-truck-btn"><a href="javascript:;" onclick="conf.editTruckId='+list[i].id+';">删除信息</a></div>\
    	              </li>\
    	            ').appendTo('#truck-list-box');
    	        };
    	}
       
      }else{
          logisticsError(json.description);
      }
    },
      error: function(jqXHR, textStatus, errorThrown){
          ajaxError(jqXHR, textStatus, errorThrown);
      }
  });
}

/*function deleteTruckInfo(id){
  conf.editTruckId = id;//保存当前更改的车辆的id
}*/

function truckRealConfirm(){
  $.ajax({
      type: 'DELETE',
      contentType: 'application/json',
      url: '../api/trucks/'+conf.editTruckId,
      success: function(json){
        if(json.code == 0){
              logisticsAlert('车辆删除成功');
              modalNameSpace.truckDeleteModal.cancelHandler();
              $('#truck-list-box').html('');//TODO 目前是全部刷新列表，要改为不刷新列表，只改单项item
              loadTruckList(0, PAGE_SIZE);
        }else{
            logisticsError(json.description);
        }
      },
      error: function(jqXHR, textStatus, errorThrown){
          ajaxError(jqXHR, textStatus, errorThrown);
      }
  });
}

;(function(){
  $(function(){
    $('#truck-list-box').delegate('.mycar_xiangqing', 'click', function(){
      sessionStorage.edittingTruck = JSON.stringify(conf.truckList[$(this).attr('data-index')]);
    });
  });
})();
