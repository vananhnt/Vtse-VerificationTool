package com.vtse.runbenchmark;

import com.vtse.app.verification.ExportExcel;
import com.vtse.app.verification.FileVerification;
import com.vtse.app.verification.FunctionVerification;
import com.vtse.app.verification.report.VerificationReport;
import com.vtse.invariant.LoopTemplate;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RunInvariant {
    public static void  main(String[] args) throws IOException, WriteException {
        String benchmark = "/home/va/data/Vtse-VerificationTool/src/main/resources/benchmark/invgen/template2/loop-acceleration/overflow_1_1_false.c";
        ExportExcel exportExcel = new ExportExcel();
        File file = new File(benchmark);
        FileVerification fv = new FileVerification();
        LoopTemplate.generateInvariantDirectory(file);
        List<VerificationReport> reportList = fv.verifyDirectory(file, FunctionVerification.UNFOLD_MODE);
        exportExcel.writeExcel(reportList);
    }
}