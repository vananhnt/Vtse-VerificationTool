package app.verification;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportExcel {
	static WritableWorkbook workbook = null;
	static WritableSheet sheet = null;
	public static int k = 4;
	public static int id = 1;

	public static void main(String[] args) throws RowsExceededException, WriteException, IOException {
		ExportExcel ee = new ExportExcel();
		
		ExportExcel.init();
		ee.write();
	}
	
	public static void init() throws IOException, RowsExceededException, WriteException{
		workbook = Workbook.createWorkbook(new File("abc1.xls"));
		sheet = workbook.createSheet("floats-cdfpl", 0);
		
		sheet.addCell(new Label(5, 0, "Ket qua thuc nghiem")); // add a String to cell A1
		sheet.addCell(new Label(0, 3, "ID"));
		sheet.addCell(new Label(1, 3, "function"));
		sheet.addCell(new Label(2, 3, "pre-condition"));
		sheet.addCell(new Label(3, 3, "post-condition"));
		sheet.addCell(new Label(4, 3, "status"));
		sheet.addCell(new Label(5, 3, "cputime (s)"));
		sheet.addCell(new Label(6, 3, "memUsage (MB)"));
		k = 4;
	}
	
	public static void add(String function, String pre, String post, String status, String cputime, String memU, String counterEx) throws RowsExceededException, WriteException{
		 WritableCellFormat cellFormat = new WritableCellFormat();
	     cellFormat.setAlignment(Alignment.LEFT);
	     cellFormat.setWrap(true);
	     
	    if (sheet == null) {
	    	try {
				ExportExcel.init();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    System.err.println(sheet);
		sheet.addCell(new Label(0, k, String.valueOf(id++)));
		sheet.addCell(new Label(1, k, function));
		sheet.addCell(new Label(2, k, pre));
		sheet.addCell(new Label(3, k, post));
		sheet.addCell(new Label(4, k, status));
		sheet.addCell(new Label(5, k, cputime));
		sheet.addCell(new Label(6, k, memU));
		sheet.addCell(new Label(7, k, counterEx, cellFormat));
		k++;
		System.out.println("k: " + k);
	}
	
	public static void write() throws IOException, WriteException{
		workbook.write();
		workbook.close();
	}

}
