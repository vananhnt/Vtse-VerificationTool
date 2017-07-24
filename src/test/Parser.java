
package test;
import java.util.HashMap;

import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
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

import cfg.build.ASTGenerator;
import cfg.build.SMTInput;
import cfg.build.VtseCFG;


public class Parser {
	public static void  main(String[] args) throws Exception {
		String fileLocation =  "./TestInput.c";		
		ASTGenerator ast = new ASTGenerator(fileLocation);
		
		for (IASTNode run : ast.getTranslationUnit().getDeclarations()){
			VtseCFG cfg = new VtseCFG((IASTFunctionDefinition) run);
			cfg.unfold();
			cfg.index();
			//cfg.printGraph();
			cfg.printMeta();
			cfg.printFormular(System.out);
			
			SMTInput input = new SMTInput(cfg.getVm().getVariableList(), cfg.createFormular());
			input.printInput();
			System.out.print("\n~ 0.o ~\n");
			break;
		}
		//IASTFunctionDefinition func = ast.getFunction(0);	
		
		
		//printTree(func, 1);
		}
	
	@SuppressWarnings("unused")
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

