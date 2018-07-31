package excelChapter;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelDemo {
	private File xlsFile;
	private Workbook workbook;
	private Sheet[] sheets;

	public static void main(String[] args) {
		new ExcelDemo().readExcel();

	}

	boolean readExcel() {
		xlsFile = new File("resources/12万以上个人所得税自行申报表.xls");

		try {
			workbook = Workbook.getWorkbook(xlsFile);
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Sheet[] sheets = workbook.getSheets();

		if (sheets != null) {
			for (Sheet sheet : sheets) {
				// 获得行数
				int rows = sheet.getRows();
				// 获得列数
				int cols = sheet.getColumns();
				// 读取数据
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						System.out.printf("%10s", sheet.getCell(col, row).getContents());
					}
					System.out.println();
				}
			}
		}
		workbook.close();
		return true;
	}

}
