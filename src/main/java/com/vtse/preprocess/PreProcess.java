package com.vtse.preprocess;

import com.vtse.cfg.build.ASTFactory;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PreProcess {

    public static void filterToFile(IASTTranslationUnit ast, String newFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile));

        for (IASTNode child : ast.getChildren()) {
            writer.append(filterToString(child));
            writer.newLine();
        }
        writer.close();

    }

    private static String filterToString(IASTNode node) {
        if (node instanceof IASTFunctionDefinition) {
            List<String> funcLines = filterLine((IASTFunctionDefinition) node);
            String funcStr = "";
            for (String funcLine : funcLines) {
                funcStr += funcLine + "\n";
            }
            return funcStr;
        } else {
            return node.getRawSignature();
        }
    }
    private static List<String> filterLine(IASTFunctionDefinition func) {
        return filterBracket(filterGoto(func));
    }

    private static List<String> filterGoto(IASTFunctionDefinition func) {
        List<String> lines = new ArrayList<>(Arrays.asList(func.getRawSignature().split("\\r?\\n")));
        List<String> res = new ArrayList<>();
        res = filterGoto(lines);
        res = filterGoto(res);
        return res;
    }
    private static List<String> filterGoto(List<String> lines) {
        List<String> res = new ArrayList<>();
        List<String> tmp = new ArrayList<>();

        List<String> unchangeLines = new ArrayList<>(lines);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            //filter through each lines
            if (line.contains("goto")) {

                //go-to
                String labelName = findLabel(line);

                String labelToEnd;
                int currentLine = lines.indexOf(line);
                int elsePosition = -1;
                if (labelName.equals("return_label")) {

                }
                if (!labelName.equals("")) { //if there is a valid label
                    int startLabel = getAfterLabel(lines, labelName);//get last position of label
                    if (startLabel > -1) {
                        //labelToEnd = removeRedundants(lines.subList(startLabel, lines.size() - 1)).trim();
                        labelToEnd = removeRedundants(unchangeLines.subList(startLabel, unchangeLines.size()-1)).trim();
                        lines.set(currentLine, labelToEnd);
                        res.add(labelToEnd);
                    }
                    if (!(currentLine < lines.size() - 1 && !lines.get(currentLine + 1).contains("else"))) {
                     continue;
                    }
                    for (int j = i; j < lines.size(); j++) {
                        if (lines.get(j).contains("else")) {
                            elsePosition = j;
                            break;
                        }
                    }

                    i = (elsePosition > 1) ? elsePosition - 1: i;
                }

            }
            else if (line.trim().equals("")) {
            }
                // remove CIL label
            else {
                res.add(line);
            }
        }

        //re-break lines
        for (String line: res) {
            String[] subLines = line.split("\\r?\\n");
            for (String subLine : subLines) {
                tmp.add(subLine);
            }
        }
        return tmp;
    }
    private static List<String> filterBracket(List<String> lines) {
        List<String> res = new ArrayList<>();
        int openBracket = 0;
        for (String line : lines) {
            if (line.contains("{") && !(line.contains("}"))) {
                res.add(line);
                openBracket ++;
            } else if (line.contains("}") && !line.contains("{")) {
                if (openBracket > 0) {
                    res.add(line);
                    openBracket --;
                }
            }  else { //contains both or none
                 res.add(line);
            }
        }
        while (openBracket > 0) {
            res.add("}");
            openBracket --;
        }
        return res;
    }

    private static String addBracketGotoBlock(String gotoLine) {
        String[] lines = gotoLine.split("\\r?\\n");

        List<String> res = new ArrayList<>();
        String tmp = "";
        int openBracket = 2;
        for (String line : lines) {
            if (line.contains("{") && !(line.contains("}"))) {
                res.add(line);
                //openBracket ++;
            } else if (line.contains("}") && !line.contains("{")) {
                    res.add(line);
                   // openBracket --;
            }  else { //contains both or none
                res.add(line);
            }
        }
        while (openBracket > 0) {
            res.add("}");
            openBracket --;
        }
        for (String funcLine : res) {
            if (!funcLine.trim().equals(""))
                tmp += funcLine + "\n";
        }
        return tmp;
    }

    private static List<String> filterLabel(List<String> lines) {
        List<String> res = new ArrayList<>();
        for (String line : lines) {
            if (!line.contains("Label")) {
                res.add(line);
            }
        }
        return res;
    }

    private static int getAfterLabel(List<String> lines, String labelName) {
        int position = -1;
        for (int i = lines.size() - 1; i >= 0; i--) {
            if (lines.get(i).contains(labelName.replace(";", ":"))) {
                position = i;
                break;
            }
        }
        return position;
    }

    private static String removeRedundants(List<String> lines) {
        List<String> res = new ArrayList<>();
      //  List<String> lines = Arrays.asList(fullGoto.split("\\r?\\n")); //in lines does not exist another goto
        int openBracket = 0;
        for (String line : lines) {
            if (line.contains("{") && !(line.contains("}"))) {
                res.add(line);
                openBracket ++;
            } else if (line.contains("}") && !line.contains("{")) {
                if (openBracket > 0) {
                    res.add(line);
                    openBracket --;
                }
            } else { //contains both or none
                if (!line.trim().equals("")) res.add(line);
            }
        }
        String funcStr = "";
        for (String funcLine : res) {
            if (!funcLine.trim().equals(""))
            funcStr += funcLine + "\n";
        }
        return funcStr;
    }

    private static String findLabel(String gotoLine) {
        return (gotoLine.trim().split(" ")[0].equals("goto")) ? gotoLine.trim().split(" ")[1] : "";
    }

    public static void main(String[] args) {
        String linkToFile = "./src/main/resources/benchmark/kratos/transmitter_1.c";
        ASTFactory ast = new ASTFactory(linkToFile);
        File oldFile = new File(linkToFile);
        try {
            filterToFile(ast.getTranslationUnit(), oldFile.getParent() + "/newFile.c");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
