package cfg.utils;

public class ErrorPrompt {
    public static void FunctionNotFound(String func) {
        System.err.println("Cannot find function: " + func);
        System.exit(1);
    }

}
