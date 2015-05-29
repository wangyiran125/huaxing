function HS_DateAdd(interval,number,date){
	number = parseInt(number);
	if (typeof(date)=="string"){var date = new Date(date.split("-")[0],date.split("-")[1],date.split("-")[2])}
	if (typeof(date)=="object"){var date = date}
	var date0 = new Date();
	switch(interval){
	case "y":return new Date(date.getFullYear()+number,date.getMonth(),date.getDate()); break;
	case "m":return date.getFullYear() == date0.getFullYear() && date.getMonth() == date0.getMonth() && number < 0 ? 
				new Date(date.getFullYear(),date.getMonth(),checkDate(date.getFullYear(),date.getMonth()+number,date.getDate())) :
				new Date(date.getFullYear(),date.getMonth()+number,checkDate(date.getFullYear(),date.getMonth()+number,date.getDate())); break;
	case "d":return new Date(date.getFullYear(),date.getMonth(),date.getDate()+number); break;
	case "w":return new Date(date.getFullYear(),date.getMonth(),7*number+date.getDate()); break;
	}
}
function checkDate(year,month,date){
	var enddate = ["31","28","31","30","31","30","31","31","30","31","30","31"];
	var returnDate = "";
	if (year%4==0){enddate[1]="29"}
	if (date>enddate[month]){returnDate = enddate[month]}else{returnDate = date}
	return returnDate;
}

function WeekDay(date){
	var theDate;
	if (typeof(date)=="string"){theDate = new Date(date.split("-")[0],date.split("-")[1],date.split("-")[2]);}
	if (typeof(date)=="object"){theDate = date}
	return theDate.getDay();
}
function HS_calender(){
	var lis = "";
	var style = "";
	style +="<style type='text/css'>";
	style +=".calender {width:170px; height:auto; font-size:12px; margin-right:14px; background-color:#3AAD6E; border:1px solid #397EAE; padding:1px}";
	style +=".calender ul {list-style-type:none; margin:0; padding:0;}";
	style +=".calender .day { background-color:#EDF5FF; height:20px;}";
	style +=".calender .day li,.calender .date li{ float:left; width:14%; height:20px; line-height:20px; text-align:center}";
	style +=".calender li a { text-decoration:none; font-family:Tahoma; font-size:11px; color:#333}";
	style +=".calender li a:hover { color:#f30; text-decoration:underline}";
	style +=".calender li a.hasArticle {font-weight:bold; color:#f60 !important}";
	style +=".lastMonthDate {color:#bbb;font-size:11px}";
	style +=".selectThisYear a, .selectThisMonth a{text-decoration:none; margin:0 2px; color:#000; font-weight:bold}";
	style +=".calender .LastMonth, .calender .NextMonth{ text-decoration:none; color:#000; font-size:18px; font-weight:bold; line-height:16px;}";
	style +=".calender .LastMonth { float:left;}";
	style +=".calender .NextMonth { float:right;}";
	style +=".calenderBody {clear:both}";
	style +=".calenderTitle {text-align:center;height:20px; line-height:20px; clear:both}";
	style +=".today { background-color:#ffffaa;border:1px solid #f60; padding:2px}";
	style +=".today a { color:#f30; }";
	style +=".calenderBottom {clear:both; border-top:1px solid #ddd; padding: 3px 0; text-align:left}";
	style +=".calenderBottom a {text-decoration:none; margin:2px !important; font-weight:bold; color:#000}";
	style +=".calenderBottom a.closeCalender{float:right}";
	style +=".closeCalenderBox {float:right; border:1px solid #000; background:#fff; font-size:9px; width:11px; height:11px; line-height:11px; text-align:center;overflow:hidden; font-weight:normal !important}";
	style +=".calender .date .date-past {color:#bbb;}";
	style +="</style>";

	var now;
	if (typeof(arguments[0])=="string"){
		selectDate = arguments[0].split("-");
		var year = selectDate[0];
		var month = parseInt(selectDate[1])-1+"";
		var date = selectDate[2];
		now = new Date(year,month,date);
	}else if (typeof(arguments[0])=="object"){
		now = arguments[0];
	}
	var lastMonthEndDate = HS_DateAdd("d","-1",now.getFullYear()+"-"+now.getMonth()+"-01").getDate();
	var lastMonthDate = WeekDay(now.getFullYear()+"-"+now.getMonth()+"-01");
	var thisMonthLastDate = HS_DateAdd("d","-1",now.getFullYear()+"-"+(parseInt(now.getMonth())+1).toString()+"-01");
	var thisMonthEndDate = thisMonthLastDate.getDate();
	var thisMonthEndDay = thisMonthLastDate.getDay();
	var todayObj = new Date();
	today = todayObj.getFullYear()+"-"+todayObj.getMonth()+"-"+todayObj.getDate();
	
	for (i=0; i<lastMonthDate; i++){  // Last Month's Date 当前显示月的上月的日期

		var nowLiDate = new Date();
		nowLiDate.setFullYear(now.getFullYear());
		nowLiDate.setMonth(now.getMonth()-1);
		nowLiDate.setDate(lastMonthEndDate);

		//切换月以后，如果日期是今天以前的日期，不可点击，如果是今天以后的日期，虽然是显示栏里的上个月，其实还是未来
		if(nowLiDate.getTime()< todayObj.getTime()){//过去
			lis = "<li><a href=javascript:void(0) class='date-past' title='"+now.getFullYear()+"-"+(parseInt(now.getMonth()))+"-"+lastMonthEndDate+"'>"+lastMonthEndDate+"</a></li>"+ lis;
		}else{
			lis = "<li><a href=javascript:void(0) class='future' onclick='_selectThisDay(this)' title='"+now.getFullYear()+"-"+(parseInt(now.getMonth()))+"-"+lastMonthEndDate+"'>"+lastMonthEndDate+"</a></li>"+ lis;
		}
		
		lastMonthEndDate--;
	}
	for (i=1; i<=thisMonthEndDate; i++){ // Current Month's Date
		// var nowLiDate = new Date(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+i);
		//在chrome里面可以用new Date('2015-4-25');的方式创建日期，在IE里面不行，IE11都不行
		var nowLiDate = new Date();
		nowLiDate.setFullYear(now.getFullYear());
		nowLiDate.setMonth(now.getMonth());
		nowLiDate.setDate(i);

		if(today == now.getFullYear()+"-"+now.getMonth()+"-"+i){//今天
			var todayString = now.getFullYear()+"-"+((parseInt(now.getMonth())+1) < 10 ? "0"+(parseInt(now.getMonth())+1) : (parseInt(now.getMonth())+1))+"-"+(i < 10 ? '0'+i : i);
			lis += "<li><a href=javascript:void(0) class='today' onclick='_selectThisDay(this)' title='"+now.getFullYear()+"-"+((parseInt(now.getMonth())+1) < 10 ? "0"+(parseInt(now.getMonth())+1) : (parseInt(now.getMonth())+1))+"-"+(i < 10 ? '0'+i : i)+"'>"+i+"</a></li>";
		}else if(nowLiDate.getTime()< todayObj.getTime()){//过去
			lis += "<li><a href=javascript:void(0) class='date-past' title='"+now.getFullYear()+"-"+((parseInt(now.getMonth())+1) < 10 ? "0"+(parseInt(now.getMonth())+1) : (parseInt(now.getMonth())+1))+"-"+(i < 10 ? '0'+i : i)+"'>"+i+"</a></li>";
		}else if(nowLiDate.getTime()> todayObj.getTime()){//未来
			lis += "<li><a href=javascript:void(0) class='future' onclick='_selectThisDay(this)' title='"+now.getFullYear()+"-"+((parseInt(now.getMonth())+1) < 10 ? "0"+(parseInt(now.getMonth())+1) : (parseInt(now.getMonth())+1))+"-"+(i < 10 ? '0'+i : i)+"'>"+i+"</a></li>";
		}
	}
	var j=1;
	for (i=thisMonthEndDay; i<6; i++){  // Next Month's Date
		lis += "<li><a href=javascript:void(0) class='future' onclick='_selectThisDay(this)' title='"+now.getFullYear()+"-"+(parseInt(now.getMonth())+2)+"-"+j+"'>"+j+"</a></li>";
		j++;
	}
	lis += style;

	var CalenderTitle = "<a href='javascript:void(0)' class='NextMonth' onclick=HS_calender(HS_DateAdd('m',1,'"+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDate()+"'),this) title='下月'>&raquo;</a>";
	CalenderTitle += "<a href='javascript:void(0)' class='LastMonth' onclick=HS_calender(HS_DateAdd('m',-1,'"+now.getFullYear()+"-"+now.getMonth()+"-"+now.getDate()+"'),this) title='上月'>&laquo;</a>";
	CalenderTitle += "<span class='selectThisYear'><a href='javascript:void(0)' onclick='CalenderselectYear(this)' title='Click here to select other year' >"+now.getFullYear()+"</a></span>年<span class='selectThisMonth'><a href='javascript:void(0)' onclick='CalenderselectMonth(this)' title='Click here to select other month'>"+(parseInt(now.getMonth())+1).toString()+"</a></span>月"; 

	if (arguments.length>1){
		arguments[1].parentNode.parentNode.getElementsByTagName("ul")[1].innerHTML = lis;
		arguments[1].parentNode.innerHTML = CalenderTitle;

	}else{
		var CalenderBox = style+"<div class='calender'><div class='calenderTitle'>"+CalenderTitle+"</div><div class='calenderBody'><ul class='day'><li>日</li><li>一</li><li>二</li><li>三</li><li>四</li><li>五</li><li>六</li></ul><ul class='date' id='thisMonthDate'>"+lis+"</ul></div><div class='calenderBottom'><a href='javascript:void(0)' class='closeCalender' onclick='closeCalender(this)'>×</a><span><span><a href=javascript:void(0) onclick='_selectThisDay(this)' title='"+todayString+"'>Today</a></span></span></div></div>";
		return CalenderBox;
	}
}
function _selectThisDay(d){
	var boxObj = d.parentNode.parentNode.parentNode.parentNode.parentNode;
		boxObj.targetObj.value = d.title;
		boxObj.parentNode.removeChild(boxObj);
}
function closeCalender(d){
	var boxObj = d.parentNode.parentNode.parentNode;
		boxObj.parentNode.removeChild(boxObj);
}

function CalenderselectYear(obj){
		var opt = "";
		var thisYear = obj.innerHTML;
		for (i=1970; i<=2020; i++){
			if (i==thisYear){
				opt += "<option value="+i+" selected>"+i+"</option>";
			}else{
				opt += "<option value="+i+">"+i+"</option>";
			}
		}
		opt = "<select onblur='selectThisYear(this)' onchange='selectThisYear(this)' style='font-size:11px'>"+opt+"</select>";
		obj.parentNode.innerHTML = opt;
}

function selectThisYear(obj){
	HS_calender(obj.value+"-"+obj.parentNode.parentNode.getElementsByTagName("span")[1].getElementsByTagName("a")[0].innerHTML+"-1",obj.parentNode);
}

function CalenderselectMonth(obj){
		var opt = "";
		var thisMonth = obj.innerHTML;
		for (i=1; i<=12; i++){
			if (i==thisMonth){
				opt += "<option value="+i+" selected>"+i+"</option>";
			}else{
				opt += "<option value="+i+">"+i+"</option>";
			}
		}
		opt = "<select onblur='selectThisMonth(this)' onchange='selectThisMonth(this)' style='font-size:11px'>"+opt+"</select>";
		obj.parentNode.innerHTML = opt;
}
function selectThisMonth(obj){
	HS_calender(obj.parentNode.parentNode.getElementsByTagName("span")[0].getElementsByTagName("a")[0].innerHTML+"-"+obj.value+"-1",obj.parentNode);
}
function HS_setDate(inputObj){
	var calenderObj = document.createElement("span");
	calenderObj.innerHTML = HS_calender(new Date());
	calenderObj.style.position = "absolute";
	calenderObj.targetObj = inputObj;
	inputObj.parentNode.insertBefore(calenderObj,inputObj.nextSibling);
}