package dataDrivenExcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelData {

	public ArrayList<String> excelData(String testCaseName, String sheetName) throws IOException {
		
		ArrayList<String> a = new ArrayList<String>();
		FileInputStream fis = new FileInputStream("C:\\Users\\Lenovo\\eclipse-workspace\\APIdemoProject\\TestData.xlsx");
		
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		
		int sheets = workbook.getNumberOfSheets();
		
		for(int i=0; i<sheets; i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				
				int column = 0;
				int j = 0;
				int flagColumn = 0;
				int k= 0;
				
				XSSFSheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rows = sheet.iterator();
				Row row = rows.next();
				Iterator<Cell>cells = row.iterator();
				
				while(cells.hasNext())
				{
					Cell cell = cells.next();
					if(cell.getStringCellValue().equalsIgnoreCase("testcase"))
					{
						column = j;
					}
					j++;
				}
				
				while(rows.hasNext()) {
					Row rd = rows.next();
					if(rd.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> dataCell = rd.cellIterator();
						while(dataCell.hasNext()) {
							Cell cd = dataCell.next();
							if(cd.getCellType() == CellType.STRING) {
								a.add(cd.getStringCellValue());
							}
							else if(cd.getCellType() == CellType.NUMERIC) {
								a.add(NumberToTextConverter.toText(cd.getNumericCellValue()));
							}
							else {
								a.add(null);
							}
						}
					}
				}
			}
		}
		return a;
	}

}
