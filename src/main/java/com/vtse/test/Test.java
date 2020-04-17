package com.vtse.test;

import java.io.*;
import java.util.Properties;

import com.vtse.cfg.build.ASTFactory;
import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.build.VtseCFG;

import com.vtse.graph.GraphGenerator;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * @author va
 *
 */
public class Test {
	private static String getBenchmark(String props) throws IOException {
		String path = System.getProperty("user.dir") + "/src/main/java/com/vtse/test/benchmark.properties";
		FileInputStream is = new FileInputStream(new File(path));
		Properties benchmarkProps = new Properties();
		benchmarkProps.load(is);
		return benchmarkProps.getProperty(props);
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

	public static void  main(String[] args) throws IOException {
		ASTFactory ast = new ASTFactory("./src/main/resources/benchmark/example/graph.c");
		VtseCFG cfg = new VtseCFG(ast.getFunction("main"), ast);
		UnfoldCFG unfoldCFG = new UnfoldCFG(cfg);
//		cfg = cfg.pr
	    //ast.print();
		//cfg.invariant();
		//cfg.ungoto();
		//cfg.unfold(1);
		//cfg.index();
		//java.cfg.printMeta();
		unfoldCFG.printGraph();
        GraphGenerator graphGenerator = new GraphGenerator(unfoldCFG);
        graphGenerator.printGraph();
		try {
			File file = new File("./graph.dot");
			InputStream dot = new FileInputStream(file);
			MutableGraph g = new Parser().read(dot);
			Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("./a.png"));
		} catch(Exception e){
            System.out.println(e.toString());
		}
	}
}
