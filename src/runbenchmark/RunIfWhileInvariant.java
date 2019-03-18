package runbenchmark;

import app.verification.ExportExcel;
import app.verification.FileVerification;
import app.verification.FunctionVerification;
import app.verification.report.VerificationReport;
import invariant.LoopTemplate;
import jxl.write.WriteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class RunIfWhileInvariant {
    public static void  main(String[] args) throws FileNotFoundException, IOException, WriteException {
        String benchmark = "benchmark/invgen/template2/loops_crafted";
        ExportExcel exportExcel = new ExportExcel();
        File file = new File(benchmark);
        FileVerification fv = new FileVerification();
        LoopTemplate.generateInvariantDirectory(file, LoopTemplate.IFELSE_WHILE_TEMPLATE);
        List<VerificationReport> reportList = fv.verifyDirectory(file, FunctionVerification.INVARIANT_MODE);
        exportExcel.writeExcel(reportList);
    }
}
