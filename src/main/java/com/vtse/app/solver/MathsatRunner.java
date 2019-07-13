package com.vtse.app.solver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MathsatRunner {
    public static List<String> run(String filename)
            throws IOException {
        List<String> result = new ArrayList<String>();
        String s;

        //System.err.println(filename);
       if (System.getProperty("os.name").equalsIgnoreCase("Linux")) {
            try {
                Process p = Runtime.getRuntime().exec("mathsat " + filename);

                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((s = br.readLine()) != null) {
                    //System.out.println("line: " + s);
                    result.add(s);
                    //System.err.println(s);
                }
                try {
                    p.waitFor();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // System.out.println ("exit: " + p.exitValue());

                //System.out.println("size: " + result.size());

            } catch (Exception e) {
            }
        } else {
//    		List<String> result = new ArrayList<String>();
            String pathToZ3 = "z3\\bin\\z3.exe";
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", pathToZ3 + " -smt2 -st -T:1 " + filename);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            //System.err.println("p: " + p);
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                result.add(line);
            }
        }


        //p.destroy();
        return result;
    }

    public static List<String> run(String filename, int time)
            throws IOException {
        List<String> result = new ArrayList<String>();
        String pathToZ3 = "z3\\bin\\z3.exe";
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", pathToZ3 + " -smt2 -st -T:900" + time + filename);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        while (true) {
            line = r.readLine();
            if (line == null) {
                break;
            }
            result.add(line);
        }
        return result;
    }
}
