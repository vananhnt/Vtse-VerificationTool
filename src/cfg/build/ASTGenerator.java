package cfg.build;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTInitializerExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.core.runtime.CoreException;

import cfg.utils.FunctionHelper;

/**
 * Get IASTFunctionDefinition 
 * IASTFunctionDefiniton func = (new ASTGenerator(filelocation)).getFunction(index);
 * filelocation: String
 * index: int
 * @author va
 *
 */
public class ASTGenerator {

	private static IASTTranslationUnit translationUnit;
	private String filelocation = "./test.c";
	
	public ASTGenerator() {
		FileContent fileContent = FileContent.createForExternalFileLocation(filelocation);
		IncludeFileContentProvider includeFile = IncludeFileContentProvider.getEmptyFilesProvider();
		IParserLogService log = new DefaultLogService(); 
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(new HashMap<String, String>(), includePaths);
		try {
			translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, includeFile, null, 0, log);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	
	public ASTGenerator(String filelocation) {
		FileContent fileContent = FileContent.createForExternalFileLocation(filelocation);
		IncludeFileContentProvider includeFile = IncludeFileContentProvider.getEmptyFilesProvider();
		IParserLogService log = new DefaultLogService(); 
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(new HashMap<String, String>(), includePaths);
		try {
			translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, includeFile, null, 0, log);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * getters & setters
	 */
	public IASTTranslationUnit getTranslationUnit() {
		return translationUnit;
	}
	public void setTranslationUnit(IASTTranslationUnit translationUnit) {
		translationUnit = translationUnit;
	}
	public void setFileLocation(String fileName) {
		filelocation = fileName;
	}
	
	public static IASTFunctionDefinition getFunction(String name) {
		String funcName = null;
		ArrayList<IASTFunctionDefinition> funcList = getListFunction();
		for (IASTFunctionDefinition func : funcList) {
			funcName = func.getDeclarator().getName().toString();
			if (name.equals(funcName)) {
				return func;
			}
		}
		return null;
	}
	public static IASTFunctionDefinition getMain() {
		return FunctionHelper.getFunction(getListFunction(), "main");
	}
	public static ArrayList<IASTFunctionDefinition> getListFunction(){
		if (translationUnit == null) return null;
		ArrayList<IASTFunctionDefinition> funcList = new ArrayList<>();
		for (IASTNode run : translationUnit.getDeclarations()){
			if (run instanceof IASTFunctionDefinition){
				funcList.add((IASTFunctionDefinition) run);
			}
		}
		return funcList;
	}
	
	
	/*
	 * functionDef 
	 * chá»‰ láº¥y function Ä‘áº§u tiĂªn
	 * chÆ°a xĂ©t trÆ°á»�ng há»£p cho chá»�n cĂ¡c func khĂ¡c nhau 
	 */
	public IASTFunctionDefinition getFunction(int index) {
//		int count = 0;
		IASTFunctionDefinition funcDef = null;
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		for(IASTDeclaration d : declarations){	
			if (d instanceof IASTFunctionDefinition) {
//				funcDef = (count == index) ? (IASTFunctionDefinition) d : null;
//				count ++;
				funcDef = (IASTFunctionDefinition) d;
				break;
			}	
		}
		return funcDef;
	}
	
	public void print() {
		IASTDeclaration[] iter = translationUnit.getDeclarations();
		for (IASTDeclaration i:iter) {
			printTree(i, 0);
		}		
	}
	
	private static void printTree(IASTNode node, int index) {
		IASTNode[] children = node.getChildren();
		
		for (int i = 0; i < index; i++) {
			System.out.print(" ");
		}
		
		System.out.println("-" + node.getClass().getSimpleName() + " -> " + node.getRawSignature());
		for (IASTNode iastNode : children)
			printTree(iastNode, index + 2);
	}

	
	
}
