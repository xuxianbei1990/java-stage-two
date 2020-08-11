package excelChapter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelDemo {

	/**
	 * 填充表格数据
	 * 
	 * @param wb
	 *            WorkBook
	 * @param title
	 *            Sheet页名称
	 * @param headerNames
	 *            表头名称集合
	 * @param headerIds
	 *            表头ID结合
	 * @param dtoList
	 *            数据列表
	 * @param startRowNum
	 *            起始填充行
	 * @return 结束填充行
	 */
	public static int fillTableContent(Workbook wb, String title, String[] headerNames, String[] headerIds,
			List<? extends Object> dtoList, int startRowNum) {
		Sheet sheet = wb.getSheet(title);
		if (sheet == null) {
			// LOGGER.debug("找不到制定Sheet页，重新创建Sheet页");
			sheet = wb.createSheet(title);
		}
		// 生成表头
		Row row = sheet.createRow(startRowNum);
		for (int i = 0; i < headerNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(headerNames[i]);
		}
		startRowNum++;

		if (dtoList != null && dtoList.size() > 0) {
			// 填充数据
			for (Object dto : dtoList) {
				row = sheet.createRow(startRowNum);
				for (int i = 0; i < headerNames.length; i++) {
					String headId = headerIds[i];
					String methodName = getMethodName(headId);
					String textVal = getCellValue(dto, methodName);
					row.createCell(i).setCellValue(textVal);
				}
				startRowNum++;
			}
		}

		return startRowNum;
	}

	/**
	 * 获取 getter 方法
	 * 
	 * @param headName
	 *            表头字段ID
	 * @return
	 */
	private static String getMethodName(String headId) {
		String methodName = "get" + headId.substring(0, 1).toUpperCase() + headId.substring(1);
		return methodName;
	}

	/**
	 * 获取单元格文本值
	 * 
	 * @param dto
	 *            DTO对象
	 * @param methodName
	 *            getter方法名称
	 * @return
	 */
	private static String getCellValue(Object dto, String methodName) {
		String textVal = null;
		Class<? extends Object> clazz = dto.getClass();
		try {
			Method getterMethod = clazz.getMethod(methodName, new Class[] {});
			Object val = getterMethod.invoke(dto, new Object[] {});
			textVal = getCellValue(val);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			// LOGGER.error("获取单元格文本值出错", e);
		}

		return textVal;
	}

	/**
	 * 获取单元格文本值
	 * 
	 * @param val
	 * @return
	 */
	private static String getCellValue(Object val) {
		String textVal = null;
		if (val != null) {
			if (val instanceof Date) {
				Date date = (Date) val;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				textVal = sdf.format(date);
			} else {
				textVal = val.toString();
			}
		} else {
			textVal = null;
		}

		return textVal;
	}

	/**
	 * 设置响应
	 * 
	 * @param response
	 *            HTTP响应
	 * @param wb
	 *            WorkBook
	 * @param fileName
	 *            文件名
	 * @throws IOException
	 */
	public static void setResponse(HttpServletResponse response, Workbook wb, String fileName) {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + ".xlsx" + "\"");
			response.setContentType("application/ms-excel;");
			wb.write(os);
		} catch (IOException e) {
			// LOGGER.error("设置Excel导出HTTP响应时出错", e);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					os = null;
				}
			}
		}
	}

	private Row combineCell(int rowIndex, String value, CellStyle cellStyle, Sheet sheet) {
		Row row = sheet.createRow(rowIndex);
		Cell cell = row.createCell(0);
		cell.setCellValue(value);
		cell.setCellStyle(cellStyle);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));
		return row;
	}

	@SuppressWarnings("deprecation")
	public void styleExcel() {
		String[] headerNames = { "单位名称", "部门", "工号", "姓名", "证照号码", "收入额", "免税项目合计", "允许扣除的税费", "减免税额", "应补(退)税额" };
		String[] headerIds = { "kjywrmc", "bmmc", "gh", "xm", "zzhm", "sre", "msxmhj", "yxkcsf", "jmse", "sjynse" };

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("申报汇总表");

		int rowCount = 0;

		Row row = sheet.createRow(rowCount);

		row.setHeightInPoints(50);
		Cell cell = row.createCell(0);
		cell.setCellValue("纳税人收入统计清单");

		// 垂直水平居中
		CellStyle styleCenter = wb.createCellStyle();
		// cell中水平的对齐方式
		styleCenter.setAlignment(HorizontalAlignment.CENTER);
		// cell中垂直方向的对齐方式
		styleCenter.setVerticalAlignment(VerticalAlignment.CENTER);

		// 居左
		CellStyle styleLeft = wb.createCellStyle();
		styleLeft.setAlignment(HorizontalAlignment.LEFT);

		// 居左 加黑
		CellStyle styleLeftHeader = wb.createCellStyle();
		styleLeftHeader.setAlignment(HorizontalAlignment.LEFT);
		Font boldFont = wb.createFont();
		boldFont.setFontName("Arial");
		boldFont.setFontHeightInPoints((short)10);
		boldFont.setBold(true);
		styleLeftHeader.setFont(boldFont);
		styleLeftHeader.setBorderBottom(BorderStyle.THIN); // 下边框  
		styleLeftHeader.setBorderLeft(BorderStyle.THIN);// 左边框  
		styleLeftHeader.setBorderTop(BorderStyle.THIN);// 上边框  
		styleLeftHeader.setBorderRight(BorderStyle.THIN);// 右边框

		// 居右
		CellStyle styleRight = wb.createCellStyle();
		styleRight.setAlignment(HorizontalAlignment.RIGHT);

		// 居右 加黑
		CellStyle styleRightHeader = wb.createCellStyle();
		styleRightHeader.setAlignment(HorizontalAlignment.RIGHT);
		styleRightHeader.setFont(boldFont);
		styleRightHeader.setBorderBottom(BorderStyle.THIN); // 下边框  
		styleRightHeader.setBorderLeft(BorderStyle.THIN);// 左边框  
		styleRightHeader.setBorderTop(BorderStyle.THIN);// 上边框  
		styleRightHeader.setBorderRight(BorderStyle.THIN);// 右边框

		// 标题字体
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBold(true);
		styleCenter.setFont(titleFont);

		// 列头字体
		Font headerFont = wb.createFont();
		headerFont.setBold(true);

		cell.setCellStyle(styleCenter);
		styleCenter.setBorderBottom(BorderStyle.THIN); // 下边框  
		styleCenter.setBorderLeft(BorderStyle.THIN);// 左边框  
		styleCenter.setBorderTop(BorderStyle.THIN);// 上边框  
		styleCenter.setBorderRight(BorderStyle.THIN);// 右边框
		// 合并单元格
		CellRangeAddress cellRangeAddress = new CellRangeAddress(rowCount, rowCount, 0, headerNames.length - 1);
		sheet.addMergedRegion(cellRangeAddress);
		for (int i = 1; i < headerNames.length; i ++) {
			cell = row.createCell(i);
			cell.setCellStyle(styleCenter);
		}
		
		rowCount++;

		row = combineCell(rowCount, "生成报表日期：" + DateFormatUtils.format(new Date(), "yyyy-MM-dd"), styleLeft, sheet);

		cell = row.createCell(headerNames.length - 1);
		cell.setCellValue("单位：元");
		cell.setCellStyle(styleRight);
		rowCount++;

		row = combineCell(rowCount, "扣缴义务人编码:" + "310115999400048", styleLeft, sheet);
		cell = row.createCell(headerNames.length - 4);
		cell.setCellValue("扣缴义务人名称：" + "上海外服（集团）有限公司");
		cell.setCellStyle(styleRight);
		//合并单元格 水平
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, headerNames.length - 4, headerNames.length - 1));
		rowCount++;

		row = combineCell(rowCount, "所得月份起：2017-07-01", styleLeft, sheet);
		cell = row.createCell(headerNames.length - 4);
		cell.setCellValue("所得月份止：2017-07-31");
		cell.setCellStyle(styleRight);
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, headerNames.length - 4, headerNames.length - 1));
		rowCount++;
	
		// cell
		fillTableContent(wb, "申报汇总表", headerNames, headerIds, null, rowCount);
		sheet.setColumnWidth(1, 20*256);
//		sheet.setDefaultColumnWidth(12);
		
		// 设置样式
		row = sheet.getRow(rowCount);
		Font bottomFont = wb.createFont();
        bottomFont.setFontName("Arial");
        bottomFont.setColor((short)0xDA);
        bottomFont.setFontHeightInPoints((short) 10);
        
		CellStyle styleBottomFont = wb.createCellStyle();
        styleBottomFont.setFont(bottomFont);
		
		row.setRowStyle(styleBottomFont);
		for (int i = 0; i < headerNames.length; i++) {
			cell = row.getCell(i);
			if (i < 3)
				cell.setCellStyle(styleBottomFont);
			else
				cell.setCellStyle(styleRightHeader);
		}
		
		rowCount++;
		
		row = sheet.createRow(rowCount);
		//金额     设置单元格格式  http://cyxinda.blog.163.com/blog/static/3659618620121364143435/
		cell = row.createCell(0);
		cell.setCellValue(Double.parseDouble("20000.01"));

		CellStyle csMoney = wb.createCellStyle();
		DataFormat dfMoney = wb.createDataFormat();
		csMoney.setDataFormat(dfMoney.getFormat("0.00")); 
//		HSSFDataFormat.getBuiltinFormat("0.00")
		
		cell.setCellStyle(csMoney);
		cell = row.createCell(1);
		cell.setCellValue(Double.parseDouble("0"));
		cell.setCellStyle(csMoney);
		
		rowCount++;
		row = sheet.createRow(rowCount);
		cell = row.createCell(0);
		cell.setCellValue("124");
		CellStyle csbackgroud = wb.createCellStyle();
//		csbackgroud.setFillForegroundColor(HSSFColorPredefined.PALE_BLUE.getIndex());
//		csbackgroud.setFillBackgroundColor(HSSFColorPredefined.PALE_BLUE.getIndex());
//		csbackgroud.setFillBackgroundColor((short)0x11);
		csbackgroud.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		csbackgroud.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(csbackgroud);
		rowCount++;
		sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount + 1, 0, 0));
		row = sheet.createRow(rowCount);
		cell = row.createCell(0);
		cell.setCellStyle(styleLeftHeader);
		
		row = sheet.createRow(rowCount + 1);
		cell = row.createCell(0);
		cell.setCellStyle(styleLeftHeader);
		try {
			FileOutputStream fout = new FileOutputStream("E:/税友/00删除/test.xls");
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		PoiExcelDemo ped = new PoiExcelDemo();
		ped.styleExcel();
		System.out.print("success");
	}

}
