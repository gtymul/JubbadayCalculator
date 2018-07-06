package jubb.udemy.com.jubbadaycalculator;

import java.math.BigDecimal;

/**
 * Created by jubbaday on 21.06.2018.
 */

public class Calculator extends ArithmeticException
{
    private static int _precission = 10;

    public static double addition(double a, double b)
    {
        return doubleFix(a + b, _precission);
    }

    public static double subtraction(double a, double b)
    {
        return doubleFix(a - b, _precission);
    }

    public static double multiply(double a, double b) { return doubleFix(a*b, _precission); }

    public static double division(double a, double b)
    {
        if (b == 0) throw new ArithmeticException();
        return doubleFix(a/b, _precission);
    }
    //public static double percent(double a, double b) { return a * b * 0.01; }

    private static double doubleFix(double x, int precission)
    {
        if (x == Double.NEGATIVE_INFINITY || x == Double.POSITIVE_INFINITY) return x;

        return BigDecimal
                .valueOf( new Double(x) )
                .setScale(precission, BigDecimal.ROUND_HALF_UP)
                .doubleValue();
    }
}
