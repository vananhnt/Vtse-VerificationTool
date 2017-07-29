package test;

import java.io.File;
import java.io.IOException;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;

import app.verification.ExportExcel;
import app.verification.FileVerification;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TestVerification {
	public static void main(String[] args) throws RowsExceededException, WriteException, IOException {
		FileVerification fv = new FileVerification();
		ExportExcel.init();
		try {
			fv.verify(new File("float-cdfpl/newton_1_2_true_unreach_call.c"));
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
		ExportExcel.write();
		
//		File file = new File("TestSpoon.java");
//		
//		String extension = FilenameUtils.getExtension(file.getAbsolutePath());
//		String name = FilenameUtils.getName(file.getAbsolutePath());
//		name = FilenameUtils.getBaseName(file.getAbsolutePath());
//		System.out.println("extension: " + extension);
//		System.out.println("name: " + name);
	}
}
