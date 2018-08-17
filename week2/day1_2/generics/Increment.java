package week2.day1_2.generics;

public class Increment {
    public static <N extends Number> N increment(N param) {
        if(param instanceof Double) {
            Double number = param.doubleValue();
            param = (N) (++number);
        }
        else if(param instanceof Integer) {
            Integer number = param.intValue();
            param = (N) (++number);
        }
        else if(param instanceof Float) {
            Float number = param.floatValue();
            param = (N) (++number);
        }
        else if(param instanceof Short) {
            Short number = param.shortValue();
            param = (N) (++number);
        }
        else if(param instanceof Long) {
            Long number = param.longValue();
            param = (N) (++number);
        }
        return param;
    }
    
    
    public static void main(String[] args) {
        Integer i = new Integer(5);
        Double d = new Double(4.5656456); d = increment(d);
        Float f = new Float(5.65);
        Short s = (short) 1;
        Long l = new Long(43457);
        
        System.out.println(i + ", " + increment(i));
        System.out.println(d + ", " + increment(d));
        System.out.println(f + ", " + increment(f));
        System.out.println(s + ", " + increment(s));
        System.out.println(l + ", " + increment(l));
    }
}