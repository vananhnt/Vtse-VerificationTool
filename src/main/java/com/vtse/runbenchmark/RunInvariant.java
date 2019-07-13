package com.vtse.runbenchmark;

import com.vtse.app.verification.ExportExcel;
import com.vtse.app.verification.FileVerification;
import com.vtse.app.verification.report.VerificationReport;
import com.vtse.invariant.LoopTemplate;
import jxl.write.WriteException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RunInvariant {
    public static void  main(String[] args) throws IOException, WriteException {
        String benchmark = "benchmark/invgen/template2/loop-acceleration/simple_3_2_true.c";
        ExportExcel exportExcel = new ExportExcel();
        File file = new File(benchmark);
        FileVerification fv = new FileVerification();
        LoopTemplate.generateInvariantDirectory(file);
        List<VerificationReport> reportList = fv.verifyDirectory(file);
        exportExcel.writeExcel(reportList);
    }
}