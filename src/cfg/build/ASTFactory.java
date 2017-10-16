package cfg.build;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.core.runtime.CoreException;

import cfg.utils.ErrorPrompt;
import cfg.utils.FunctionHelper;

/**
 * Get IASTFunctionDefinition 
 * IASTFunctionDefiniton func = (new ASTGenerator(filelocation)).getFunction(index);
 * filelocation: String
 * index: int
 * @author va
 *
 */
public class ASTFactory {

	private static IASTTranslationUnit translationUnit;
	private String filelocation = "./test.c";
	
	public ASTFactory() {
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
	
	public ASTFactory(IASTTranslationUnit ast) {
		translationUnit = ast;
	}
	
	public ASTFactory(String filelocation) {
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
	
	/**
	 * getters & setters
	 */
	public IASTTranslationUnit getTranslationUnit() {
		return translationUnit;
	}
	public void setTranslationUnit(IASTTranslationUnit tranUnit) {
		translationUnit = tranUnit;
	}
	public void setFileLocation(String fileName) {
		filelocation = fileName;
	}
	
	
	public ArrayList<IASTFunctionDefinition> getListFunction(){
		if (translationUnit == null) return null;
		ArrayList<IASTFunctionDefinition> funcList = new ArrayList<>();
		for (IASTNode run : translationUnit.getDeclarations()){
			if (run instanceof IASTFunctionDefinition){
				funcList.add((IASTFunctionDefinition) run);
			}
		}
		return funcList;
	}
	public ArrayList<String> getGlobarVarStrList() {
		ArrayList<String> result = new ArrayList<>();
		for (IASTDeclaration decl: this.getGlobarVarList()) {
			 IASTDeclarator[] declarators = ((IASTSimpleDeclaration) decl).getDeclarators();
			 for (IASTDeclarator declarator : declarators) {
				 String tmp = declarator.getName().toString();
				 result.add(tmp);
			 }
		}
		return result;
	}
	
	public ArrayList<IASTDeclaration> getGlobarVarList() {
		if (translationUnit == null) return null;
		ArrayList<IASTDeclaration> varList = new ArrayList<>();
		for (IASTDeclaration run : translationUnit.getDeclarations()) {
			if (run instanceof IASTSimpleDeclaration) {
				boolean isVar = true;
				IASTDeclarator[] declors = ((IASTSimpleDeclaration) run).getDeclarators();
				for (IASTDeclarator decl : declors) {
					if (decl instanceof IASTStandardFunctionDeclarator) {
						isVar = false;
					}
				if (isVar) varList.add(run);
				}	
			}
		}
		return varList;
	}
	
	/**
	 * functionDef 
	 */
	public IASTFunctionDefinition getFunction(int index) {
//		int count = 0;
		IASTFunctionDefinition funcDef = null;
		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		for(IASTDeclaration d : declarations){	
			if (d instanceof IASTFunctionDefinition) {
				funcDef = (IASTFunctionDefinition) d;
				break;
			}	
		}
		return funcDef;
	}
	public IASTFunctionDefinition getFunction(String name) {
		String funcName = null;
		ArrayList<String> funcNameList = new ArrayList<>();
		ArrayList<IASTFunctionDefinition> funcList = getListFunction();
		for (IASTFunctionDefinition func : funcList) {
			funcName = func.getDeclarator().getName().toString();
			funcNameList.add(funcName);
			if (name.equals(funcName)) {
				return func;
			}
		}
		//Bao loi neu khong tim duoc function
		System.out.println("- Function list: ");
		for (String str : funcNameList) {
			System.out.println("   ." + str);
		}
		ErrorPrompt.FunctionNotFound("<" + name + ">");
		return null;
	}
	
	public IASTFunctionDefinition getMain() {
		return FunctionHelper.getFunction(this.getListFunction(), "main");
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
			//System.out.print(" ");
		}
		if (node instanceof IASTFunctionCallExpression) {
			System.out.println(((IASTFunctionCallExpression) node).getExpressionType().toString());
			
		}
		//System.out.println("-" + node.getClass().getSimpleName() + " -> " + node.getRawSignature());
		for (IASTNode iastNode : children)
			printTree(iastNode, index + 2);
	}

	
	
}
