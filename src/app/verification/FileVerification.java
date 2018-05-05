package app.verification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import app.verification.report.VerificationReport;
import app.verification.userassertion.AssertionMethod;
import cfg.build.ASTFactory;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FileVerification {
	
	public static final String C_TAG = ".c";
	public static final String CPP_TAG = ".cpp";
	public static final String PP_FILE_TAG = ".xml";
	
	public FileVerification() throws RowsExceededException, WriteException, IOException {
		
	}
	
	public List<VerificationReport> verifyDirectory(File directory) throws WriteException, IOException {
		List<VerificationReport> reportList = new ArrayList<>();
		List<VerificationReport> reports;
		
		if (directory == null) {
			return reportList;
		}
		else if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			
			for (File f: files) {
				reports = verifyDirectory(f);
				reportList.addAll(reports);
			}
		}
		else {
			reports = verify(directory);
			reportList.addAll(reports);
		}
		
		return reportList;
	}
	
	@SuppressWarnings("unused")
	public List<VerificationReport> verify(File file) {
		
		List<VerificationReport> reportList = new ArrayList<>();
		
		if (file == null) {
			System.out.println("file is null");
			return reportList;
		}
		
		String filePath = file.getAbsolutePath();
		
		//System.out.println(filePath);
		
		String CPPFilename = getCFilename(file);
		String PPPathFile;
		
		if (CPPFilename == null) {
			System.out.println("not cpp file");
			return reportList;
		}
		else {
			PPPathFile = CPPFilename + PP_FILE_TAG;
		}
		
		ASTFactory ast = new ASTFactory(filePath);
		
		
		@SuppressWarnings("static-access")
		ArrayList<IASTFunctionDefinition> listFunction = ast.getListFunction();
		
		FunctionVerification mv = new FunctionVerification();
		
		//System.err.println("file: " + file.getPath());
		
		
		File PPFile = new File(PPPathFile);
		if (!PPFile.exists()) {
			System.err.println("file is not exist: " + PPPathFile);
			return null;
		}
		
		List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);
	
		VerificationReport report;
		
		for (AssertionMethod am: listAssertion) {
			//System.err.println("***Verification report:");
			System.out.println("-Method name: " + am.getMethodName());
			int nLoops = Integer.parseInt(am.getLoopCount());
			System.out.println(nLoops);
			for (IASTFunctionDefinition function: listFunction) {
				String functionName = getFunctionName(function);
//				System.err.println("function name: " + functionName);
				if (functionName.equals(am.getMethodName())) {
					try {
						long start = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//						System.err.println("function: " + functionName);
						report = mv.verify(ast,function, am.getPreCondition(), am.getPostCondition(), nLoops);
						if (report != null) {
							reportList.add(report);
						}
						long end = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
		return reportList;
		
	}
	
	private String getCFilename(File file) {
		String filename = file.getAbsolutePath();
		int index = filename.lastIndexOf(CPP_TAG);
		if (index >= 0) {
			filename = filename.substring(0, index);
		}
		else {
			index = filename.lastIndexOf(C_TAG);
			if (index >= 0) {
				filename = filename.substring(0, index);
			}
			else {
				filename = null;
			}
		}
		
		return filename;
	}
	
	private String getFunctionName(IASTFunctionDefinition function) {
		if (function == null) 
			return null;		
		return 
			function.getDeclarator().getName().toString();
	}
	
//	public void verify(File file) throws RowsExceededException, WriteException, IOException {
//		if (file == null) {
//			System.out.println("file is null");
//			return;
//		}
//		
//		String javaPathFile = file.getPath();
//		
//		String PPPathFile = null;
//		int index = javaPathFile.lastIndexOf(CPP_TAG);
//		if (index < 0) {
//			return;
//		}
//		else {
//			PPPathFile = javaPathFile.substring(0, index);
//			PPPathFile += PP_FILE_TAG;
//		}
//		
//		ASTGenerator ast = new ASTGenerator(fileLocation);
//		
//		launcherSpoon.addInputResource(file);
//		launcherSpoon.buildModel();
//		
//		List<CtMethod> listMethod = launcherSpoon.getMethods();
//		
//		FunctionVerification mv = new FunctionVerification();
//		
//		System.err.println("file: " + file.getPath());
//		
//		
//		File PPFile = new File(PPPathFile);
//		if (!PPFile.exists()) {
//			System.err.println("file is not exist: " + PPPathFile);
//			return;
//		}
//		
//		List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);
//		
//		VerificationReport report;
//		
//		int id = 1;
//		for (AssertionMethod am: listAssertion) {
//			System.err.println("hello");
//			System.err.println("am: " + am.getMethodName());
//			for (FunctionDeclaration method: listFunction) {
//				System.err.println("method name: " + method.getSimpleName());
//				if (method.getSimpleName().equals(am.getMethodName())) {
//					try {
//						long start = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//						System.err.println("method: " + method.getSimpleName());
//						report = mv.verify(method, am.getPreCondition(), am.getPostCondition());
//						long end = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//						ExportExcel.add(report.getMethodName(), report.getPreCondition(), report.getPostCondition(), 
//								report.getStatus(), String.valueOf((double) (report.getSolverTime()/1000.0) + (double) (report.getGenerateConstraintTime()/1000.0)),
//								"unknown", report.getCounterEx());
//						report.print();
//						id++;
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			
//		}
//		
//	}

}
