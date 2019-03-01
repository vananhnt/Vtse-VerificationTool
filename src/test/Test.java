package test;

import java.io.*;
import java.util.List;
import java.util.Properties;

import app.verification.ExportExcel;
import app.verification.FileVerification;
import app.verification.report.VerificationReport;
import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

/**
 * @author va
 *
 */
public class Test {
	private static String getBenchmark(String props) throws IOException {
		String path = System.getProperty("user.dir") + "/src/test/benchmark.properties";
		FileInputStream is = new FileInputStream(new File(path));
		Properties benchmarkProps = new Properties();
		benchmarkProps.load(is);
		return benchmarkProps.getProperty("invgen");
	}

	public static void  main(String[] args) throws Exception {
		String benchmark = getBenchmark("invgen") + "inv01.c";
		ASTFactory ast = new ASTFactory(benchmark);
		VtseCFG cfg = new VtseCFG(ast.getFunction(0), ast);
		//cfg.invariant();
		//cfg.printGraph();
		//cfg.printMeta();
		
		ExportExcel exportExcel = new ExportExcel();
		File file = new File(benchmark);
		FileVerification fv = new FileVerification();
		List<VerificationReport> reportList = fv.verifyDirectory(file);
		exportExcel.writeExcel(reportList);

	}
}
