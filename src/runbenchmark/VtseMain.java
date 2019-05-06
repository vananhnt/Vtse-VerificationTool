package runbenchmark;

import app.verification.ExportExcel;
import app.verification.FileVerification;
import app.verification.FunctionVerification;
import app.verification.report.VerificationReport;
import invariant.LoopTemplate;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VtseMain {
    String filePath;
    boolean unfold = true;//if false use invariant
    int loopCount = -1;
    boolean isLong = false;

    public VtseMain(String[] args) {
        setOption(args);
    }
    private void run() {
        if (filePath == null) {
            System.out.println("Can not find file/directory for verification.");
        }
        try {
            ExportExcel exportExcel = new ExportExcel();
            File file = new File(filePath);
            FileVerification fv = new FileVerification();
            if (unfold) {
                if (loopCount != -1) fv.setLoopCount(loopCount);
                List<VerificationReport> reportList = fv.verifyDirectory(file, FunctionVerification.UNFOLD_MODE);
                exportExcel.writeExcel(reportList);
            } else {
                LoopTemplate.generateInvariantDirectory(file);
                List<VerificationReport> reportList = fv.verifyDirectory(file, FunctionVerification.INVARIANT_MODE);
                exportExcel.writeExcel(reportList);
            }
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOption(String[] args) {
        filePath = args[0];
        for (int i = 1; i < args.length; i++) {
             if (args[i].contains(".c")) {
                 filePath = args[i];
             } else if (args[i].contains("--unfold")) {
                 unfold = true;
                 if (args[i + 1].matches("\\d+")) {
                    loopCount = Integer.getInteger(args[i+1]);
                 } else {
                     loopCount = -1;
                 }
             } else if (args[i].contains("--invariant")) {
                 unfold = false;
             } else if (args[i].contains("--full")) {
                 isLong = true;
             }
        }
    }

    public static void main(String[] args) throws IOException, WriteException {
        VtseMain vtseMain = new VtseMain(args);
        vtseMain.run();
    }
}
