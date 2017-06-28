package core;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.ExpansionOverlapsBoundaryException;
import org.eclipse.cdt.core.dom.ast.IASTAttribute;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IScope;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPASTTranslationUnit;

public class Parser {
	public static void main(String[] args) throws Exception {
		String fileLocation =  "./test.h";
		int count = 0;
		FileContent fileContent = FileContent.createForExternalFileLocation(fileLocation);
		IncludeFileContentProvider includeFile = IncludeFileContentProvider.getEmptyFilesProvider();
		IParserLogService log = new DefaultLogService(); 
		String[] includePaths = new String[0];
		IScannerInfo info = new ScannerInfo(new HashMap<String, String>(), includePaths);
		IASTTranslationUnit translationUnit = GPPLanguage.getDefault().getASTTranslationUnit(fileContent, info, includeFile, null, 0, log);

/*		IASTDeclaration[] iter = translationUnit.getDeclarations(); 
		IASTNode[] node = translationUnit.getChildren();
		
		IASTPreprocessorIncludeStatement[] include = translationUnit.getIncludeDirectives();
		for (IASTPreprocessorIncludeStatement i:include) {
			System.out.println(count++);
			System.out.println(i.getRawSignature());
		}
		
		count = 0;
		IASTDeclaration[] iter = translationUnit.getDeclarations();
		for (IASTDeclaration i:iter) {
			System.out.println(count++);
			if ((i instanceof IASTFunctionDefinition)) {
				System.out.println(i.getRawSignature());
				IASTNode[] com = i.getChildren();
				for (IASTNode icom : com) {
					if (icom instanceof IASTFunctionDeclarator){
						System.out.println("=======");
						System.out.println(icom.getRawSignature());
					}
					if (icom instanceof IASTDeclSpecifier) {
						System.out.println("=======");
						System.out.println(icom.getRawSignature());
					}
				}
			}
		}*/
		

		
		IASTPreprocessorIncludeStatement[] includes = translationUnit.getIncludeDirectives();
			for (IASTPreprocessorIncludeStatement include : includes) {
		       System.out.println("include - " + include.getName());
		    }
		printTree(translationUnit, 1);
		System.out.println("-----------------------------------------------------");
		System.out.println("-----------------------------------------------------");
		System.out.println("-----------------------------------------------------");
	
		ASTVisitor visitor = new ASTVisitor() {
			public int visit(IASTName name){
				if ((name.getParent() instanceof CPPASTFunctionDeclarator)) {
					System.out.println("IASTName: " + name.getClass().getSimpleName() + "(" + name.getRawSignature() + ") - > parent: " + name.getParent().getClass().getSimpleName());
					System.out.println("-- isVisible: " + Parser.isVisible(name));
				}
				return 3;
			}
		/*     */ 
		/*     */       public int visit(IASTDeclaration declaration)
		/*     */       {
		/*  83 */         System.out.println("declaration: " + declaration + " ->  " + declaration.getRawSignature());
		/*     */ 
		/*  85 */         if ((declaration instanceof IASTSimpleDeclaration)) {
		/*  86 */           IASTSimpleDeclaration ast = (IASTSimpleDeclaration)declaration;
		/*     */           try
		/*     */           {
		/*  89 */             System.out.println("--- type: " + ast.getSyntax() + " (childs: " + ast.getChildren().length + ")");
		/*  90 */             IASTNode typedef = ast.getChildren().length == 1 ? ast.getChildren()[0] : ast.getChildren()[1];
		/*  91 */             System.out.println("------- typedef: " + typedef);
		/*  92 */             IASTNode[] children = typedef.getChildren();
		/*  93 */             if ((children != null) && (children.length > 0))
		/*  94 */               System.out.println("------- typedef-name: " + children[0].getRawSignature());
		/*     */           }
		/*     */           catch (ExpansionOverlapsBoundaryException e)
		/*     */           {
		/*  98 */             e.printStackTrace();
		/*     */           }
		/*     */ 
		/* 101 */           IASTDeclarator[] declarators = ast.getDeclarators();
		/* 102 */           for (IASTDeclarator iastDeclarator : declarators) {
		/* 103 */             System.out.println("iastDeclarator > " + iastDeclarator.getName());
		/*     */           }
		/*     */ 
		/* 106 */           IASTAttribute[] attributes = ast.getAttributes();
		/* 107 */           for (IASTAttribute iastAttribute : attributes) {
		/* 108 */             System.out.println("iastAttribute > " + iastAttribute);
		/*     */           }
		/*     */ 
		/*     */         }
		/*     */ 
		/* 113 */         if ((declaration instanceof IASTFunctionDefinition)) {
		/* 114 */           IASTFunctionDefinition ast = (IASTFunctionDefinition)declaration;
		/* 115 */           IScope scope = ast.getScope();
		/*     */           try
		/*     */           {
		/* 118 */             System.out.println("### function() - Parent = " + scope.getParent().getScopeName());
		/* 119 */             System.out.println("### function() - Syntax = " + ast.getSyntax());
		/*     */           }
		/*     */           catch (DOMException e) {
		/* 122 */             e.printStackTrace();
		/*     */           } catch (ExpansionOverlapsBoundaryException e) {
		/* 124 */             e.printStackTrace();
		/*     */           }
		/* 126 */           ICPPASTFunctionDeclarator typedef = (ICPPASTFunctionDeclarator)ast.getDeclarator();
		/* 127 */           System.out.println("------- typedef: " + typedef.getName());
		/*     */         }
		/*     */ 
		/* 131 */         return 3;
		/*     */       }
		/*     */ 
		/*     */       public int visit(IASTTypeId typeId)
		/*     */       {
		/* 136 */         System.out.println("typeId: " + typeId.getRawSignature());
		/* 137 */         return 3;
		/*     */       }
		/*     */ 
		/*     */       public int visit(IASTStatement statement)
		/*     */       {
		/* 142 */         System.out.println("statement: " + statement.getRawSignature());
		/* 143 */         return 3;
		/*     */       }
		/*     */ 
		/*     */       public int visit(IASTAttribute attribute)
		/*     */       {
		/* 149 */         return 3;
		/*     */       }
		/*     */     };
		/* 154 */     visitor.shouldVisitNames = true;
		/* 155 */     visitor.shouldVisitDeclarations = false;
		/*     */ 
		/* 157 */     visitor.shouldVisitDeclarators = true;
		/* 158 */     visitor.shouldVisitAttributes = true;
		/* 159 */     visitor.shouldVisitStatements = false;
		/* 160 */     visitor.shouldVisitTypeIds = true;
		/*     */ 
		/* 162 */     translationUnit.accept(visitor);
		/*     */   }
		/*     */ 
		/*     */   private static void printTree(IASTNode node, int index) {
		/* 166 */     IASTNode[] children = node.getChildren();
		/*     */ 
		/* 168 */     boolean printContents = true;
		/*     */ 
		/* 170 */     if ((node instanceof CPPASTTranslationUnit)) {
		/* 171 */       printContents = false;
		/*     */     }
		/*     */ 
		/* 174 */     String offset = "";
		/*     */     try {
		/* 176 */       offset = node.getSyntax() != null ? " (offset: " + node.getFileLocation().getNodeOffset() + "," + node.getFileLocation().getNodeLength() + ")" : "";
		/* 177 */       printContents = node.getFileLocation().getNodeLength() < 30;
		/*     */     } catch (ExpansionOverlapsBoundaryException e) {
		/* 179 */       e.printStackTrace();
		/*     */     } catch (UnsupportedOperationException e) {
		/* 181 */       offset = "UnsupportedOperationException";
		/*     */     }
		/*     */ 
		/* 184 */     System.out.println(String.format(new StringBuilder("%1$").append(index * 2).append("s").toString(), new Object[] { "-" }) + node.getClass().getSimpleName() + offset + " -> " + (printContents ? node.getRawSignature().replaceAll("\n", " \\ ") : node.getRawSignature().subSequence(0, 5)));
		/*     */ 
		/* 186 */     for (IASTNode iastNode : children)
		/* 187 */       printTree(iastNode, index + 1);
		/*     */   }
		
	public static boolean isVisible(IASTNode current) {
		IASTNode declator = current.getParent().getParent();
		IASTNode[] children = declator.getChildren();
		for (IASTNode iastNode : children) {
			if ((iastNode instanceof ICPPASTVisibilityLabel)) {
				return 1 == ((ICPPASTVisibilityLabel)iastNode).getVisibility();
			}
	    }
		return false;
		}
		 
	}

