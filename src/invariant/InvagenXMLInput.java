package invariant;

import cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;

import java.io.*;

public class InvagenXMLInput {

    public static void printInputToXMLFarkas(LoopMonoWhileTemplate loop, OutputStream os) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(os));
        out.append("<TransitionSystem>\n" +
                "    <Variables>\n");
        for (IASTName v : loop.getVariables()) {
            out.append("        <v>" + v + "</v>\n");
        }
        out.append("    </Variables>\n" +
                "    <Initiation>\n");
        for (IASTExpressionStatement init : loop.getInitiation()) {
            out.append("            <Constraint>" + ExpressionHelper.toString(init)
                                                .replaceAll("\\(", "")
                                                .replaceAll("\\)", "")
                                +"</Constraint>\n");
        }
        out.append("    </Initiation>\n" +
                "    <Transitions>\n" +
                "        <Transition>\n");
        for (IASTExpressionStatement con : loop.getConsecution()) {
            out.append("            <Constraint>" + ConsecutionFormat.formatFarkas(con)
                                                .replaceAll("\\(", "")
                                                .replaceAll("\\)", "")
                                +"</Constraint>\n");
        }
        out.append("        </Transition>\n" +
                "    </Transitions>\n");
        out.append("</TransitionSystem>\n");
        out.flush();
        out.close();
    }

}
