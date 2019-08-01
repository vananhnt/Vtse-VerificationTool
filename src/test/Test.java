package test;

import java.io.*;
import java.util.Properties;

import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.CPPNodeFactory;

/**
 * @author va
 *
 */
public class Test {
	private static String getBenchmark(String props) throws IOException {
		String path = System.getProperty("user.dir") + "/src/test/benchmark.properties";
		FileInputStream is = new FileInputStream(new File(path));
		Properties benchmarkProps = new Properties();
		benchmarkProps.load(is);
		return benchmarkProps.getProperty("invgen");
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
	public static void  main(String[] args) throws FileNotFoundException, IOException {
		String benchmark = "benchmark/kratos/loop_1";
//		ASTFactory ast = new ASTFactory(benchmark);
		//VtseCFG cfg = new VtseCFG(ast.getFunction(0), ast);
		ASTFactory ast = new ASTFactory(benchmark + "/token_ring_13.c");

//		VtseCFG cfg = new VtseCFG(ast.getFunction("token_ring_13"), ast);
		VtseCFG cfg = new VtseCFG(ast.getFunction("is_master_triggered"), ast);

//		ast.print();
//		cfg.index();
		cfg.unfold();
		cfg.printGraph();

	}
}
