
package test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
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
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit;
import org.eclipse.cdt.internal.core.model.FunctionDeclaration;

import cfg.build.ASTGenerator;
import cfg.build.VtseCFG;
import cfg.utils.Variable;
import cfg.utils.VariableManager;


public class Parser {
	public static void  main(String[] args) throws Exception {
		String fileLocation =  "./bai1.cpp";
		int count = 0;
		FileContent fileContent = FileContent.createForExternalFileLocation(fileLocation);
		IncludeFileContentProvider includeFile = IncludeFileContentProvider.getEmptyFilesProvider();
		IParserLogService log = new DefaultLogService(); 
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(new HashMap<String, String>(), includePaths);
		IASTTranslationUnit translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, includeFile, null, 0, log);

		IASTDeclaration[] declarations = translationUnit.getDeclarations();
		ASTGenerator ast = new ASTGenerator(fileLocation);
		IASTFunctionDefinition func = ast.getFunction(0);
		
		VtseCFG cfg = new VtseCFG(func);
		cfg.DFS();
		
		// test variable manager			
//		VariableManager vm = new VariableManager(func);
//		Variable var = vm.getVariable("i");
//		System.out.println( var.getVariableWithIndex());

		//printTree(func, 1);
		}
	
	private static void printTree(IASTNode node, int index) {
		IASTNode[] children = node.getChildren();

		boolean printContents = true;

		if ((node instanceof CPPASTTranslationUnit)) {
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

		System.out.println(String.format(new StringBuilder("%1$").append(index * 2).append("s").toString(), new Object[] { "-" }) + node.getClass().getSimpleName() + offset + " -> " + (printContents ? node.getRawSignature().replaceAll("\n", " \\ ") : node.getRawSignature().subSequence(0, 5)));

		for (IASTNode iastNode : children)
			printTree(iastNode, index + 2);
	}

}

