package jubb.udemy.com.jubbadaycalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by jubbaday on 21.06.2018.
 */

public class ValueHandler
{
    private String _separator = ".";
    private String _value = "";

    public ValueHandler() {}

    public void addDigit(String a)
    {
        _value = _value + a;
    }

    public void addComa()
    {
        if (_value.equals("")) _value = "0" + _separator;
        if (!_value.contains(_separator)) _value = _value + _separator;
    }
    public void addMinus()
    {
        if (_value.equals("")) _value = "-";
    }

    public void resetValue()
    {
        _value = "";
    }

    public void setValue(double value)
    {
        this._value = Double.toString(value);
        if (_value.substring(_value.length()-2).equals(".0"))
            _value = _value.substring(0,_value.length()-2);
    }

    public String getValueString()
    {
        return _value;
    }

    public Double getValueDouble()
    {
        /*
        return BigDecimal
                .valueOf(Double.valueOf(_value))
                .setScale(10, BigDecimal.ROUND_HALF_UP)
                .doubleValue();*/
        return Double.valueOf(_value);
    }
}
