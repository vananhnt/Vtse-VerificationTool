package com.vtse.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.vtse.app.verification.FileVerification;
import com.vtse.app.verification.FunctionVerification;
import com.vtse.app.verification.report.VerificationReport;
import com.vtse.cfg.build.ASTFactory;
import com.vtse.cfg.build.UnfoldCFG;
import com.vtse.cfg.build.VtseCFG;

import com.vtse.cfg.node.CFGNode;
import com.vtse.graph.GraphGenerator;
import com.vtse.visualize.AddMoreInformation;
import com.vtse.visualize.PathExecutionVisualize;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import jxl.write.WriteException;
import org.antlr.v4.runtime.misc.Pair;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
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

	public static void  main(String[] args) throws IOException, WriteException {
////		FileVerification fileVerification = new FileVerification();
//		fileVerification.verify(new File("./src/main/resources/benchmark/example/example_7.c"), FunctionVerification.UNFOLD_MODE);
		ASTFactory ast = new ASTFactory("./src/main/resources/benchmark/example/graph.c");
		IASTFunctionDefinition main_func = ast.getFunction(0);
		System.out.println(main_func.toString());
		VtseCFG cfg = new VtseCFG(ast.getFunction(0), ast);
		cfg.unfold(1);
		cfg.index();
//		String pre_condition = "a = 5";
		String pre_condition = "";
		String post_condition = "return = 0";
		int nLoops = 2;
		int mode = FunctionVerification.UNFOLD_MODE;
		File smtFile = File.createTempFile("temp", ".txt");
		System.out.println(smtFile.getAbsolutePath());
		VerificationReport vr = FunctionVerification.verify(ast, main_func, pre_condition, post_condition, nLoops, mode, smtFile);
		PathExecutionVisualize pathExecutionVisualize = new PathExecutionVisualize(cfg, vr);
		List<CFGNode> nodes = pathExecutionVisualize.findPathToFail();
		AddMoreInformation addMoreInformation = new AddMoreInformation(cfg, vr);
		Map<String, String> listParameters = addMoreInformation.parseParameters();
        GraphGenerator graphGenerator = new GraphGenerator(cfg, listParameters);
        graphGenerator.printGraph(false);
		if(vr.getStatus() == VerificationReport.ALWAYS_TRUE){
			graphGenerator.fillColor(nodes, false, true);
		} else {
			graphGenerator.fillColor(nodes, true, true);
		}
		try {
			File file = new File("./graph.dot");
			InputStream dot = new FileInputStream(file);
			MutableGraph g = new Parser().read(dot);
			Graphviz.fromGraph(g).width(500).render(Format.PNG).toFile(new File("./a1.png"));
		} catch(Exception e){
            System.out.println(e.toString());
		}
	}
}
