package Lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelDataConfig {
	
	HSSFWorkbook wb;
	HSSFSheet sheet;
	
	public ExcelDataConfig(String excelPath)
	{	
	try {
		File src=new File(excelPath);

		FileInputStream fis = new FileInputStream(src);
		wb = new HSSFWorkbook(fis);
		
	} 
	
	catch (Exception e) {
		System.out.println(e.getMessage());
		
	}  
		
}
	
	public String getData(int sheetNumber, int row, int column)
	{
		sheet = wb.getSheetAt(sheetNumber);
		String data = sheet.getRow(row).getCell(column).getStringCellValue();
	return data;
	}
	
	public int getRowCount(int sheetIndex)
	{
		int row = wb.getSheetAt(sheetIndex).getLastRowNum();
		row=row+1;
		return row;
	}
}
