$(function(){
	$(conf.sendBtn).click(function(){
		console.log(logisticsUserinfo);
		console.log(conf);
	  var score='', comment='', owner = '', commentLevel='';
	  switch(logisticsUserinfo.role){
	    case 2: 
	    case 3:
	      score='scoreFromGoodsOwner'; comment='commentFromGoodsOwner'; owner = 'goods'; commentLevel='commentLevelFromGoodsOwner';
	      break;
	    case 4: 
	    case 5:
	      score='scoreFromTruckOwner'; comment='commentFromTruckOwner'; owner = 'truck'; commentLevel='commentLevelFromTruckOwner';
	      break;
	  }

	  var scoreLabel = $(conf.commentForm).serialize().replace('comment=','');// a好评 b中评 c差评
	  if (scoreLabel=="") {
	  	$("#comment-form_promt").html("请先评价");
	  	return;
	  };
	  var level = 0;
	  switch(scoreLabel){
	  	case 'a':
	  		scoreLabel= (owner=='truck') ? 3 : 6;
	  		level = 1;
	  		break;
  		case 'b':
  			scoreLabel= (owner=='truck') ? 4 : 7;
  			level = 2;
  			break;
		case 'c':
			scoreLabel= (owner=='truck') ? 5 : 8;
			level = 3;
			break;
	  }
	  console.log(scoreLabel);
	  var jsonData = {
	    'id':conf.id
	  };
	  jsonData[score] = scoreLabel;
	  if ($(conf.commentBox).val()=="") {
	  	jsonData[comment]="用户未填写评价信息";
	  } else{
	  	jsonData[comment] = $(conf.commentBox).val();
	  };
	  
	  jsonData[commentLevel] = level;
	  console.log(jsonData);
	  $.ajax({
	      url: '../api/orders',
	      contentType: 'application/json',
	      data: JSON.stringify(jsonData),
	      type: 'PUT',
	      success: function () {
	          logisticsAlert("评论成功");
	          window.location.href=conf.successLocation;
	      },
          error: function(jqXHR, textStatus, errorThrown){
              ajaxError(jqXHR, textStatus, errorThrown);
          }
	  });
	});
});