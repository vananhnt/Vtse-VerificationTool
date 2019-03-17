package invariant;

import cfg.build.ASTFactory;
import main.farkas.entity.TransitionSystem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InvagenRunner {
    private static String SMTINPUT_DIR = "smt/";
    public static List<String> run(String cfilepath) {
        ASTFactory ast = new ASTFactory(cfilepath);
        LoopTemplate loopTemplate = LoopTemplate.getLoopElement(ast.getTranslationUnit());
        String filename = new File(cfilepath).getName();
        String path = (SMTINPUT_DIR  + filename).replace(".c", "_fak_inv.xml");
        List<String> result = new ArrayList<String>();
        FileOutputStream fo;
        try {
            fo = new FileOutputStream(new File(path));
            InvagenXMLInput.printInputToXMLFarkas(loopTemplate, fo);
            TransitionSystem ts = new TransitionSystem(path);
            result  = ts.getInvariants();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
