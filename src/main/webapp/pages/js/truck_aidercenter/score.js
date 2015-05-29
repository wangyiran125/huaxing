function loadScoreList(from, max){
	$("#score-box").empty();
	$("#score_page_container1").empty();
	$.ajax({
		type:'get',
		url:'../api/scores?t='+new Date().getTime(),
		success:function(json){
			if(json.code == 0){
				$("#total-score").html(json.payload);
				$.ajax({
					    url: '../api/scores?from='+from+'&max='+max+'&t='+new Date().getTime(),
					    success: function(json){
					      if(json.code == 0){
					        var list = json.payload.list;
					        // console.log(list);
					        if(! list.length > 0){
					        	$('#score-box').empty();
					        }else{
					        	if(json.payload.total > PAGE_SIZE){
					        		showPages(json.payload.total, from, max, "loadScoreList", "score_page_container1");
					        	}
					        	$('\
					        			<tr style="height:40px;">\
					                    <td style="width:20%;">时间</td>\
					                    <td style="width:20%;">分数</td>\
					                    <td style="width:50%;">说明</td>\
					                  </tr>\
					        			').appendTo('#score-box');
					        	for (var i = 0; i < list.length; i++) {//TODO 
					                $('\
					                	<tr>\
					                    <td >'+formatterDate(list[i].recordTime)+'</td>\
					                    <td >'+list[i].points+'</td>\
					                    <td >'+list[i].description+'</td>\
					                  </tr>\
					                	').appendTo('#score-box');
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
			}else{
				logisticsError(json.description);
			}
		}
	})
}