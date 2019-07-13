package com.vtse.app.gui.component;

import java.io.File;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

public class SourceCodeTextArea extends RSyntaxTextArea {
	public SourceCodeTextArea() {
		setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		setCodeFoldingEnabled(true);
		
		scrollToLine(10);
	}
	
	/**
	 * sét file mã nguồn, và hiển thị nội dung file
	 * @param path: đường dẫn tới file mã nguồn
	 */
	public void setSourceCode(String path) {
		
	}
	
	/**
	 * sét file mã nguồn, và hiển thị nội dung file
	 * @param file: file mã nguồn
	 */
	public void setSourceCode(File file) {
		
	}
	
	public void scrollToLine(int lineNumber) {
   		String[] lines = getText().split("\n");
   		
   		if (lines.length < lineNumber)
   			return;
   		
   		int position = lineNumber-1;	// cho (lineNumber - 1) đấu xuống dòng
   		
   		for (int i = 0; i < lineNumber; i++) {
   			position += lines[i].length();
   		}
   		
   		setCaretPosition(position);
   	}

}
