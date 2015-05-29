function loadHistoryOrderList(from, max){
  $.ajax({
    url: '../api/orders?from='+from+'&max='+max+'&t='+new Date().getTime(),
    success: function(json){
      if(json.code == 0){
        $("#historyorder-box").html("");
        var list = json.payload.list;
        if(!list.length > 0){
    		$('#historyorder-box').html('<center style="padding:30px;color:#969696;">您还没有历史运单！</center>');
    		$('#page_container').empty();
    	 }else{
    		 if(json.payload.total > PAGE_SIZE){
	     			showPages(json.payload.total, from, max, "loadHistoryOrderList", "page_container");
	     	 }
    		 for (var i = 0; i < list.length; i++) {//TODO 
    	          var picture = list[i].goods.picture;
    	          picture = (picture == null || picture == "") ? "images/invalid_pic1.png" : picture.split(',')[0];
    	          $('\
    	          	<li>\
    	              <div class="content_list_left"><a href="./truck_historyorder_item.html?id='+list[i].logisticsOrder.id+'"><img width="125px" src="'+picture+'" /></a></div>\
    	              <div class="content_list_right">\
    	                <h2> <a href="./truck_historyorder_item.html?id='+list[i].logisticsOrder.id+'"><img src="images/pic_3.png" />&nbsp;'+parseToDistrictName(list[i].goods.departureProvinceCode)+' ' +parseToDistrictName(list[i].goods.departureCityCode)+' &nbsp;→  &nbsp; <img src="images/pic_3.png" />&nbsp;'+parseToDistrictName(list[i].goods.destinationProvinceCode)+' ' + parseToDistrictName(list[i].goods.destinationCityCode)+'</a> </h2>\
    	                <div class="cont_right_list">\
    	                  <ol>\
    	                    <li style="padding-left:0px;">'+parseToGoodsName(list[i].goods.goodsName)+'</li>\
    	                    <li>'+parseToGoodsType(list[i].goods.goodsType)+'</li>\
	                         <li>'+parseToShippingType(list[i].goods.shippingType)+'</li>\
	                         <li>'+list[i].goods.quantity+'件</li>\
	                         <li>'+list[i].goods.weight+'吨</li>\
    	                  </ol>\
    	                </div>\
    	                <p> 完成时间：'+formatterDate(list[i].logisticsOrder.orderTime)+'</p>\
    	                <p>说明：'+(list[i].goods.remark == (null || "") ? "暂无" : list[i].goods.remark)+'</p>\
    	              </div>\
    	              <div class="mycar_xiangqing"><a href="./truck_historyorder_item.html?id='+list[i].logisticsOrder.id+'">查看详情</a></div>\
    	            </li>\
    	          	').appendTo('#historyorder-box');
    	          //<div class="mycar_lost"><a href="./truck_historyorder_comment.html?id='+list[i].logisticsOrder.id+'">评价</a></div>\
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