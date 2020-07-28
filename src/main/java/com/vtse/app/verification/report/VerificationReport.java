package com.vtse.app.verification.report;

import com.vtse.app.solver.SMTInput;
import com.vtse.app.verification.FunctionVerification;

import java.util.List;

public class VerificationReport {

//	public static final int NOT_ALWAYS_TRUE = 0;
//	public static final int ALWAYS_TRUE = 1;
//	public static final int TIMEOUT = 2;
//	public static final int UNKNOWN = 3;

    public static final String FALSE = "false";
    public static final String ALWAYS_TRUE = "true";
    public static final String NOT_ALWAYS_TRUE = "true*";
    public static final String TIMEOUT = "timeout";
    public static final String UNKNOWN = "unknown";

    private String functionName;
    private String preCondition;
    private String postCondition;
    private String status;
    private List<DefineFun> parameters;
    private DefineFun ret;    // return
    private List<String> errors;
    private int solverTime;
    private int generateConstraintTime;
    private SMTInput smtInput;
    private List<String> result;


    /**
     * @return the functionName
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * @param functionName the functionName to set
     */
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    /**
     * @return the preCondition
     */
    public String getPreCondition() {
        return preCondition;
    }

    /**
     * @param preCondition the preCondition to set
     */
    public void setPreCondition(String preCondition) {
        this.preCondition = preCondition;
    }

    /**
     * @return the postCondition
     */
    public String getPostCondition() {
        return postCondition;
    }

    /**
     * @param postCondition the postCondition to set
     */
    public void setPostCondition(String postCondition) {
        this.postCondition = postCondition;
    }

    public boolean isAlwaysTrue() {
        return ALWAYS_TRUE.equalsIgnoreCase(status);
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    public void setStatus(String SMTStatus) {
        if ("sat".equals(SMTStatus)) {
            status = NOT_ALWAYS_TRUE;
        } else if ("unsat".equals(SMTStatus)) {
            status = ALWAYS_TRUE;
        } else {
            status = SMTStatus;
        }
    }

    public void setStatus(String SMTStatus, int mode) {
        if ("sat".equals(SMTStatus)) {
            status = FALSE;
        } else if ("unsat".equals(SMTStatus)) {
            if (mode == FunctionVerification.INVARIANT_MODE) {
                status = ALWAYS_TRUE;
            } else {
                status = NOT_ALWAYS_TRUE;
            }
        } else {
            status = SMTStatus;
        }
    }
    /**
     * @return the errors
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    /**
     * @return the solverTime
     */
    public int getSolverTime() {
        return solverTime;
    }

    /**
     * @param solverTime the solverTime to set
     */
    public void setSolverTime(int solverTime) {
        this.solverTime = solverTime;
    }


    /**
     * @return the parameters
     */
    public List<DefineFun> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<DefineFun> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the ret
     */
    public DefineFun getReturn() {
        return ret;
    }

    /**
     * @param ret the ret to set
     */
    public void setReturn(DefineFun ret) {
        this.ret = ret;
    }

    /**
     * @return the generateConstraintTime
     */
    public int getGenerateConstraintTime() {
        return generateConstraintTime;
    }

    /**
     * @param generateConstraintTime the generateConstraintTime to set
     */
    public void setGenerateConstraintTime(int generateConstraintTime) {
        this.generateConstraintTime = generateConstraintTime;
    }

    public String getCounterEx() {
        String result = "";
        if (parameters != null) {
            for (DefineFun param : parameters) {
                result += param.getExpression() + "\n\t";
            }
            if (ret != null) {
                result = result + ret.getExpression();
            }
        }

        return result;
    }

    public void print() {
        System.out.println("Status: " + status);
        if (errors != null) {
            for (String err : errors) {
                //System.out.println("error: " + err);
            }
        }

        if (parameters != null) {
            System.out.println("Counter example:");
            for (DefineFun param : parameters) {
                System.out.println("	+ parameter: " + param);
            }
        }

        if (ret != null) {
            System.out.println("	+ " + ret.getExpression());
        }
        //System.out.println("constraint time: " + generateConstraintTime + " (ms)");
        float total_time = generateConstraintTime + solverTime;
        System.out.println("Total time: " + total_time + " (ms)");
    }

    public DefineFun getRet() {
        return ret;
    }

    public void setRet(DefineFun ret) {
        this.ret = ret;
    }

    public SMTInput getSmtInput() {
        return smtInput;
    }

    public void setSmtInput(SMTInput smtInput) {
        this.smtInput = smtInput;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
