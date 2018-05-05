package testbenchmark;

import java.io.File;
import java.io.IOException;
import java.util.List;

import app.verification.ExportExcel;
import app.verification.FileVerification;
import app.verification.report.VerificationReport;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class RunKratos {
	static String FLOAT_CDFPL = "./benchmark/float-cdfpl-func";
	static String FLOAT_BENCHS = "./benchmark/float-benchs";
	static String KRATOS = "./benchmark/kratos/loop_10";
	static String ECA_RERS = "./benchmark/eca-rers2012";
	
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException {	
		ExportExcel exportExcel = new ExportExcel();
		
		File file = new File(KRATOS);
		FileVerification fv = new FileVerification();	
		List<VerificationReport> reportList = fv.verifyDirectory(file);
		exportExcel.writeExcel(reportList);
	}
}
