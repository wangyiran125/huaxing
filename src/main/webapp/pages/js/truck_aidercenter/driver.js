function loadDriverList(from, max){
  $('#driver-box').empty();
  $('#driver_page_container').empty();
  $.ajax({
    url: '../api/drivers?from='+from+'&max='+max+'&t='+new Date().getTime(),
    success: function(json){
      if(json.code == 0){
        var list = json.payload.list;
        conf.driverList = list;
         //console.log(list);
        //logisticsUser信息主要用于司机的登录账号等，driver信息主要是用于显示司机的信息
        if(! list.length > 0){
        	$('#driver-box').html('<center style="padding:30px;color:#969696;">您还没有添加司机！</center>');
        }else{
        	if(json.payload.total > PAGE_SIZE){
     			showPages(json.payload.total, from, max, "loadDriverList", "driver_page_container");
     		}
            for (var i = 0; i < list.length; i++) {//TODO 删除车辆按钮的样式需要在main.css里面修改
              $('\
              	<li>\
                  <div class="driver_1">'+decodeURI(list[i].logisticsUser.username)+'</div>\
              	  <div class="driver_1">'+decodeURI(list[i].driver.name)+'</div>\
              	  <div class="driver_2">电话：'+decodeURI(list[i].driver.mobile)+'</div>\
              	  <div class="driver_3">车牌：'+decodeURI(list[i].licensePlateNumber)+'</div>\
              	  <div class="driver_4">&nbsp;&nbsp;&nbsp;</div>\
              	  <div class="driver_5">\
              	    <div class="mycar_xiangqing" data-index="'+i+'"><a href="./truck_edit_driver.html?id='+list[i].logisticsUser.id+'">修改信息</a></div>\
              	    <div class="mycar_lost" data-role="delete-driver-btn" onclick="conf.editDriverId='+list[i].logisticsUser.id+';"><a href="javascript:;">删除信息</a></div>\
              	  </div>\
              	</li>\
              	').appendTo('#driver-box');
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

function driverRealConfirm(){
  $.ajax({
    url: '../api/drivers/'+conf.editDriverId,
    type: 'DELETE',
    success: function(json){
      if(json.code == 0){
      	logisticsAlert('删除司机成功');
       	window.location.href = './truck_aidercenter.html?tar=driver';
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
		/*$('#driver-box').delegate('.mycar_lost', 'click', function(){
			deletDriver($(this).attr('data-id'));
		});
*/
		$('#driver-box').delegate('.mycar_xiangqing', 'click', function(){
			sessionStorage.edittingDriver = JSON.stringify(conf.driverList[$(this).attr('data-index')]);
		});
	});
})();