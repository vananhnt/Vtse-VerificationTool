package cfg.build;

import java.util.HashMap;

import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
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

/**
 * Get IASTFunctionDefinition 
 * IASTFunctionDefiniton func = (new ASTGenerator(filelocation)).getFunction(index);
 * filelocation: String
 * index: int
 * @author va
 *
 */
public class ASTGenerator {

	private IASTTranslationUnit translationUnit;
	
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
		this.translationUnit = translationUnit;
	}
	
	/*
	 * functionDef 
	 * chỉ lấy function đầu tiên
	 * chưa xét trường hợp cho chọn các func khác nhau 
	 */
	IASTFunctionDefinition getFunction(int index) {
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
		boolean printContents = true;
		if ((node instanceof IASTTranslationUnit)) {
			printContents = false;
		}
		String offset = "";
		try {
			offset = node.getSyntax() != null ? " (offset: " + node.getFileLocation().getNodeOffset() + "," + node.getFileLocation().getNodeLength() + ")" : "";
			printContents = node.getFileLocation().getNodeLength() < 30;
		} catch (ExpansionOverlapsBoundaryException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			offset = "UnsupportedOperationException";
		}
		//System.out.println(String.format(new StringBuilder("%1$").append(index * 2).append("s").toString(), new Object[] { "-" }) + node.getClass().getSimpleName() + offset + " -> " + (printContents ? node.getRawSignature().replaceAll("\n", " \\ ") : node.getRawSignature().subSequence(0, 5)));
		System.out.println(node.getRawSignature());
		for (IASTNode iastNode : children)
			printTree(iastNode, index + 2);
	}

	
	
}
