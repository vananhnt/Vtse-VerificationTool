package invariant;

import cfg.utils.ExpressionHelper;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;

import java.io.*;
import java.util.List;

public class InvagenXMLInput {

    public static void printInputToXMLFarkas(LoopTemplate loopTemplate, OutputStream os) throws  IOException {
        if (loopTemplate instanceof LoopMonoWhileTemplate) {
            printInputToXMLFarkas((LoopMonoWhileTemplate) loopTemplate, os);
        } else if (loopTemplate instanceof LoopIfWhileTemplate) {
            printInputToXMLFarkas((LoopIfWhileTemplate) loopTemplate, os);
        }
    }
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
            out.append("            <Constraint>" + TransitionFormat.formatInitiation(init)
                                                .replaceAll("\\(", "")
                                                .replaceAll("\\)", "")
                                +"</Constraint>\n");
        }
        out.append("    </Initiation>\n" +
                "    <Transitions>\n" +
                "        <Transition>\n");
        for (IASTExpressionStatement con : loop.getConsecution()) {
            out.append("            <Constraint>" + TransitionFormat.formatFarkas(con)
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

    public static void printInputToXMLFarkas(LoopIfWhileTemplate loop, OutputStream os) throws IOException {
        Writer out = new BufferedWriter(new OutputStreamWriter(os));
        out.append("<TransitionSystem>\n" +
                "    <Variables>\n");
        for (IASTName v : loop.getVariables()) {
            out.append("        <v>" + v + "</v>\n");
        }
        out.append("    </Variables>\n" +
                "    <Initiation>\n");
        for (IASTExpressionStatement init : loop.getInitiation()) {
            out.append("            <Constraint>" + TransitionFormat.formatInitiation(init)
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    +"</Constraint>\n");
        }
        out.append("    </Initiation>\n" +
                "    <Transitions>\n");
        for (List<IASTNode> cons : loop.getConsecutions()) {
            out.append("        <Transition>\n");
            for (IASTNode constraint : cons) {
                if (TransitionFormat.formatFarkas(constraint) != null)
                out.append("            <Constraint>" + TransitionFormat.formatFarkas(constraint)
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    +"</Constraint>\n");
            }
            out.append("        </Transition>\n");
        }
        out.append("    </Transitions>\n");
        out.append("</TransitionSystem>\n");
        out.flush();
        out.close();
    }

}
