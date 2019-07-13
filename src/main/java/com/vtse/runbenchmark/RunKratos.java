package com.vtse.runbenchmark;

import jxl.write.WriteException;
import com.vtse.app.verification.ExportExcel;
import com.vtse.app.verification.FileVerification;
import com.vtse.app.verification.report.VerificationReport;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RunKratos {

    public static void main(String[] args) throws WriteException, IOException {
        ExportExcel exportExcel = new ExportExcel("VTSE Report.xls");

        File file = new File("/home/va/data/Vtse-VerificationTool/src/main/resources/benchmark/develope");
        FileVerification fv = new FileVerification();
        List<VerificationReport> reportList = fv.verifyDirectory(file);
        exportExcel.writeExcel(reportList);
    }
}
