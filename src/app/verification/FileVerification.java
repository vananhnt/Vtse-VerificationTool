package app.verification;

import app.verification.report.VerificationReport;
import app.verification.userassertion.AssertionMethod;
import cfg.build.ASTFactory;
import invariant.LoopTemplate;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileVerification {

    public static final String C_TAG = ".c";
    public static final String CPP_TAG = ".cpp";
    public static final String PP_FILE_TAG = ".xml";

    public FileVerification() throws RowsExceededException, WriteException, IOException {

    }
    public List<VerificationReport> verifyDirectory(File directory) throws WriteException, IOException {
            return verifyDirectory(directory, FunctionVerification.UNFOLD_MODE);
    }
    public List<VerificationReport> verifyDirectory(File directory, int mode) throws WriteException, IOException {
        List<VerificationReport> reportList = new ArrayList<>();
        List<VerificationReport> reports;

        if (directory == null) {
            return reportList;
        } else if (directory.isDirectory()) {
            File[] files = directory.listFiles();

            for (File f : files) {
                reports = verifyDirectory(f, mode);
                reportList.addAll(reports);
            }
        } else {
            reports = verify(directory, mode);
            reportList.addAll(reports);
        }

        return reportList;
    }

    public List<VerificationReport> verify(File file, int mode) {
        List<VerificationReport> reportList = new ArrayList<>();

        if (file == null) {
            System.out.println("file is null");
            return reportList;
        }

        String filePath = file.getAbsolutePath();
        String CPPFilename = getCFilename(file);
        String PPPathFile;

        if (CPPFilename == null) {
            System.out.println("not cpp file");
            return reportList;
        } else {
            PPPathFile = CPPFilename + PP_FILE_TAG;
        }
        ASTFactory ast = new ASTFactory(filePath);
        ArrayList<IASTFunctionDefinition> listFunction = ast.getListFunction();
        FunctionVerification mv = new FunctionVerification();

        File PPFile = new File(PPPathFile);
        if (!PPFile.exists()) {
            System.err.println("file is not exist: " + PPPathFile);
            return null;
        }

        List<AssertionMethod> listAssertion = AssertionMethod.getUserAssertions(PPFile);

        VerificationReport report;

        for (AssertionMethod am : listAssertion) {
            //System.err.println("***Verification report:");
            System.out.println("-Method name: " + am.getMethodName());
            int nLoops;
            if (am.getLoopCount() != null) {
                nLoops = Integer.parseInt(am.getLoopCount());
            } else {
                nLoops = 100;
            }
            for (IASTFunctionDefinition function : listFunction) {
                String functionName = getFunctionName(function);
                if (functionName.equals(am.getMethodName())) {
                    try {
                        long start = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//						System.err.println("function: " + functionName);
                        report = mv.verify(ast, function, am.getPreCondition(), am.getPostCondition(), nLoops, mode);
                        if (report != null) {
                            reportList.add(report);
                        }
                        long end = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return reportList;

    }

    private String getCFilename(File file) {
        String filename = file.getAbsolutePath();
        int index = filename.lastIndexOf(CPP_TAG);
        if (index >= 0) {
            filename = filename.substring(0, index);
        } else {
            index = filename.lastIndexOf(C_TAG);
            if (index >= 0) {
                filename = filename.substring(0, index);
            } else {
                filename = null;
            }
        }

        return filename;
    }

    private String getFunctionName(IASTFunctionDefinition function) {
        if (function == null)
            return null;
        return
                function.getDeclarator().getName().toString();
    }

}
