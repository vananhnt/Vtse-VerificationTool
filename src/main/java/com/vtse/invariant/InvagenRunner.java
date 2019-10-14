package com.vtse.invariant;

import com.vtse.cfg.build.ASTFactory;
import main.farkas.entity.TransitionSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvagenRunner {
    private static String SMTINPUT_DIR = "smt/";

    public static List<String> run(String cfilepath, int template) {
        ASTFactory ast = new ASTFactory(cfilepath);
        //set template -> could change into a factory
        LoopTemplate loopTemplate;
        if (template == LoopTemplate.MONO_WHILE_TEMPLATE) {
            loopTemplate = LoopMonoWhileTemplate.getLoopElement(ast.getTranslationUnit());
        } else if (template == LoopTemplate.IFELSE_WHILE_TEMPLATE) {
            loopTemplate = LoopIfWhileTemplate.getLoopElement(ast.getTranslationUnit());
        } else {
            loopTemplate = LoopForTemplate.getLoopElement(ast.getTranslationUnit());
        }
        File dir = new File(SMTINPUT_DIR);
        if (!(dir.exists() && dir.isDirectory())) {
            dir.mkdir();
        }
        String filename = new File(cfilepath).getName();
        String path = (SMTINPUT_DIR  + filename).replace(".c", "_fak_inv.xml");
        List<String> result = new ArrayList<String>();
        if (loopTemplate == null) {
            System.err.println("Cannot generate java.invariant for: " + filename);
            return result;
        }
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(new File(path));
            InvagenXMLInput.printInputToXMLFarkas(loopTemplate, fo);
            TransitionSystem ts = new TransitionSystem(path);
//            System.err.println("Redlog formula: ");
//            ts.printRedlogFormulas();
            result = ts.getInvariants();
            if (result.isEmpty()) {
                result.add(loopTemplate.getLoopCondition().getRawSignature());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String benchmark = "benchmark/invgen/example/ase17_07.c";
        ASTFactory ast = new ASTFactory(benchmark);
        LoopMonoWhileTemplate monoWhileTemplate = LoopMonoWhileTemplate.getLoopElement(ast.getTranslationUnit());
        monoWhileTemplate.print();
    }

}
