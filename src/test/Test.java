package test;

import java.io.*;
import java.util.Properties;

import cfg.build.ASTFactory;
import cfg.build.VtseCFG;
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

	public static void  main(String[] args) throws FileNotFoundException, IOException {
		String benchmark = getBenchmark("invgen") + "inv01.c";
		ASTFactory ast = new ASTFactory(benchmark);
		VtseCFG cfg = new VtseCFG(ast.getFunction(0), ast);
//		cfg.unfold();
//		cfg.index();
		cfg.invariant();
		//ast.print();
		cfg.printGraph();

	}
}
