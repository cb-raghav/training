package util;

import java.util.regex.*;

public class UtilFunctions {
    public static boolean isNullEmpty(String param) {
        boolean result = false;
        if(param == null || param.isEmpty()) {
            result = true;
        }
        else { 
            result = false; 
        }
        return result;
    }
    
    public static boolean matchRegex(String param, String regex) {
        boolean result = false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(param);
        result = matcher.matches();
        return result;
    }
}