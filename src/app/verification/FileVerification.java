package app.verification;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.LanguageCallback;

import org.eclipse.cdt.internal.core.model.FunctionDeclaration;

import app.verification.report.VerificationReport;
import app.verification.userassertion.AssertionMethod;
import cfg.build.ASTGenerator;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class FileVerification {
	
	public static final String CPP_TAG = ".cpp";
	public static final String PP_FILE_TAG = ".xml";
	
	public FileVerification() throws RowsExceededException, WriteException, IOException {
		
	}
	
	public void verifyDirectory(File directory) throws WriteException, IOException {
		
		if (directory == null) {
			return;
		}
		else if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			
			for (File f: files) {
				verifyDirectory(f);
			}
		}
		else {
			try {
				verify(directory);
			} catch (RowsExceededException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void verify(String fileLocation) 
			throws RowsExceededException, WriteException, IOException {
		if (fileLocation == null) {
			System.out.println("file is null");
			return;
		}
		
		
		String PPPathFile = null;
//		int index = javaPathFile.lastIndexOf(CPP_TAG);
		if (index < 0) {
			return;
		}
		else {
//			PPPathFile = javaPathFile.substring(0, index);
			PPPathFile += PP_FILE_TAG;
		}
		
		ASTGenerator ast = new ASTGenerator(fileLocation);
		
		
		List<CtMethod> listMethod = launcherSpoon.getMethods();
		
		FunctionVerification mv = new FunctionVerification();
		
		System.err.println("file: " + file.getPath());
		
		
		File PPFile = new File(PPPathFile);
		if (!PPFile.exists()) {
			System.err.println("file is not exist: " + PPPathFile);
			return;
		}
		
		List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);
		
		VerificationReport report;
		
		int id = 1;
		for (AssertionMethod am: listAssertion) {
			System.err.println("hello");
			System.err.println("am: " + am.getMethodName());
			for (FunctionDeclaration method: listFunction) {
				System.err.println("method name: " + method.getSimpleName());
				if (method.getSimpleName().equals(am.getMethodName())) {
					try {
						long start = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
						System.err.println("method: " + method.getSimpleName());
						report = mv.verify(method, am.getPreCondition(), am.getPostCondition());
						long end = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
						ExportExcel.add(report.getMethodName(), report.getPreCondition(), report.getPostCondition(), 
								report.getStatus(), String.valueOf((double) (report.getSolverTime()/1000.0) + (double) (report.getGenerateConstraintTime()/1000.0)),
								"unknown", report.getCounterEx());
						report.print();
						id++;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
		
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
