package helper;

public class IsNumberic {
    public static Boolean isNumberic(String number){
        if (number == null){
            return false;
        }
        try {
            Integer d = Integer.parseInt(number);
        } catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}
