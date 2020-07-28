package com.vtse.app.solver;

public class SMTTypeConvertion {
    public static String getSMTType(String type) {
        String smtType = null;
        if (type.equals("bool"))
            smtType = "Bool";
        else if (type.equals("int") || type.equals("short"))
            smtType = "Int";
        else if (type.equals("float") || type.equals("double"))
            smtType = "Real";

        return smtType;
    }
}
