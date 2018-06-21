package com.leotech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	/** 总行数 */
    private int totalRows = 0;
    /** 总列数 */
    private int totalCells = 0;
    /** 错误信息 */
    private String errorInfo;
    /** 构造方法 */
    public ReadExcel() {
    }


    public int getTotalRows() {
        return totalRows;
    }
    public int getTotalCells() {
        return totalCells;
    }
    public String getErrorInfo() {
        return errorInfo;
    }
    
	public List<List<String>> read(String name,String path) throws IOException{
		List<List<String>> dataLst = new ArrayList<List<String>>();
		boolean Excel2007 = false;    //判断excel格式  
		InputStream is = null;
		try {
			if(name.endsWith("xlsx")){
				Excel2007 = true;
	        }
	        File file = new File(path);
	        is = new FileInputStream(file);
	        dataLst = read(is, Excel2007);
	        is.close();
	        file.delete();
		}catch(IOException e){
			e.printStackTrace();
		} finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
		return dataLst;
	}
	
	public List<List<String>> read(InputStream is,boolean Excel2007) throws IOException{
		List<List<String>> dataLst = null;
		Workbook wb = null;
		try {
			if (Excel2007) {
	            wb = new XSSFWorkbook(is);
	        } else {
	            wb = new HSSFWorkbook(is);
	        }
			dataLst = read(wb);
		}catch(IOException e){
			e.printStackTrace();
		}
		return dataLst;
	}
	
	private List<List<String>> read(Workbook wb) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		Sheet sheet = wb.getSheetAt(0);
		this.totalRows = sheet.getPhysicalNumberOfRows();
        if (this.totalRows >= 1 && sheet.getRow(0) != null) {
            this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }
        for (int i = 0; i < this.totalRows; i++) {
        	Row row = sheet.getRow(i);
        	if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
        	for (int n = 0; n < this.getTotalCells(); n++) {
        		Cell cell = row.getCell(n);
                String cellValue = "";
                if (null != cell) {
                	switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        cellValue = cell.getNumericCellValue() + "";
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        cellValue = cell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        cellValue = cell.getBooleanCellValue() + "";
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        cellValue = cell.getCellFormula() + "";
                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                        cellValue = "";
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        cellValue = "非法字符";
                        break;
                    default:
                        cellValue = "未知类型";
                        break;
                    }
                }
                rowLst.add(cellValue);
        	}
        	dataLst.add(rowLst);
        }
		return dataLst;
	}
}