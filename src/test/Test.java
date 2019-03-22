package test;

import java.io.*;
import java.util.List;
import java.util.Properties;

import app.verification.ExportExcel;
import app.verification.FileVerification;
import app.verification.FunctionVerification;
import app.verification.report.VerificationReport;
import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
import invariant.LoopTemplate;
import jxl.write.WriteException;
import org.eclipse.cdt.core.dom.ast.IASTNode;

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

	public static void  main(String[] args) throws FileNotFoundException, IOException, WriteException {
		String benchmark = "benchmark/invgen/template2/loops_crafted/Mono1_1.c";
		ASTFactory ast = new ASTFactory(benchmark);
		VtseCFG cfg = new VtseCFG(ast.getFunction(0), ast);
		cfg.invariant();
		cfg.index();
		cfg.printGraph();
		cfg.printSMTFormula(System.out);

	}
}
