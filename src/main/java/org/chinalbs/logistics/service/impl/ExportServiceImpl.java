package org.chinalbs.logistics.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.chinalbs.logistics.service.ChartService;
import org.chinalbs.logistics.service.ExportService;
import org.chinalbs.logistics.utils.Consts;
import org.chinalbs.logistics.vo.OrderChartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExportServiceImpl implements ExportService{
	
	@Autowired
	private ChartService chartService;

	public static final String[] GOODS_TITLE = {"货主用户名","货主姓名","货物名称","出发地","到达地","发货时间","运价"};
	public static final String[] ORDER_TITLE = {"货主用户名","货主姓名","货物名称","货物体积","重量","出发地","到达地","发货时间",
											"运价","车主用户名","车主姓名","车牌号"};
	public static final String[] INTENT_TITLE = {"货主用户名","货主姓名","货物名称","出发地","到达地","发货时间","运价",
											"车主用户名","车主姓名","车牌号"};
	@Override
	public void exportExcel(int status, String type, HttpServletRequest request, HttpServletResponse response)throws Exception{
		exportExcelByUserId(null, null, null, status, type, request, response);
	}
	@Override
	public void exportExcelByUserId(Long userId, String startTime, String endTime, int status, String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<OrderChartInfo> list = null;
		if (userId == null) {
			list = chartService.getOrderByStatusAndType(status, type);
		}else{
			list = chartService.getOrderByUserId(userId, startTime, endTime, status);
		}
		String[] title = {};
		switch (status) {
			case Consts.Order.STATUS_FINISHED:
				title = ORDER_TITLE;
				break;
			case Consts.Order.STATUS_ORDER_INTENT:
				title = INTENT_TITLE;
				break;
			case Consts.Order.STATUS_PUBLISHED:
				title = GOODS_TITLE;
				break;
		}
		
		OutputStream os = null;
		BufferedOutputStream bos = null;
		try {
			os = response.getOutputStream();
			bos = new BufferedOutputStream(os);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=data.xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建Excel工作薄
		WritableWorkbook wwb = Workbook.createWorkbook(bos);
		// 添加第一个工作表并设置第一个sheet的名字
		WritableSheet sheet = wwb.createSheet("result", 0);

		// 设置列的默认宽度
		sheet.getSettings().setDefaultColumnWidth(20);
		WritableFont font = new WritableFont(WritableFont.ARIAL, 14,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
				Colour.WHITE);
		WritableCellFormat cellFormat = new WritableCellFormat(font);
		cellFormat.setBackground(Colour.BLUE_GREY);
		cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		Label label = null;
		// 准备设置excel工作表的标题
		for (int i = 0; i < title.length; i++) {
			// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行，单元格的内容是y

			// 在Label对象的子对象中指明单元格的位置和内容

			label = new Label(i, 0, title[i], cellFormat);

			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

		}
		if (list == null || list.size() == 0) {
			sheet.addCell(new Label(0, 1, "暂无记录！"));
		}else {
			try {
				int x = 0,y=0;
				/*
				 * "id","accessToken","accuracy","extension",
				 * "failReason","isSuccess","lat","locationType","lon",
				 * "sign","time","timeDelay"
				 */
				for (OrderChartInfo info : list) {// 遍历集合对象
					x++; // 控制行数
					sheet.addCell(new Label(y, x, info.getGoodsOwnerUsername()));
					sheet.addCell(new Label(++y, x, info.getGoodsOwnerName()));
					sheet.addCell(new Label(++y, x, info.getGoodsName()));
					if (status == Consts.Order.STATUS_FINISHED) {
						sheet.addCell(new Label(++y, x, String.valueOf(info.getVolume())));
						sheet.addCell(new Label(++y, x, String.valueOf(info.getWeight())));
					}
					sheet.addCell(new Label(++y, x, info.getDeparture()));
					sheet.addCell(new Label(++y, x, info.getDestination()));
					sheet.addCell(new Label(++y, x, info.getDepartureTime()));
					sheet.addCell(new Label(++y, x, String.valueOf(info.getShippingPrice())));
					if (status != Consts.Order.STATUS_PUBLISHED) {
						sheet.addCell(new Label(++y, x, info.getTruckOwnerUsername()));
						sheet.addCell(new Label(++y, x, info.getTruckOwnerName()));
						sheet.addCell(new Label(++y, x, info.getLicensePlateNumbers()));
					}
					y = 0;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		wwb.write(); // 写入数据
		wwb.close(); // 关闭文件
	}
}
