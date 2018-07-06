package jubb.udemy.com.jubbadaycalculator;

import android.support.annotation.NonNull;

/**
 * Created by jubbaday on 29.06.2018.
 */

public class HistoryHandler {
    private String _historyString = "";
    private ValueHandler _previousResult;
    private OperationType _previousOperation;

    private int _maxHistoryLength = 59;

    private String _add = " + ";
    private String _subtract = " - ";
    private String _multiply = " * ";
    private String _divide = " : ";

    public void updateHistory(OperationType operation, ValueHandler operant, ValueHandler result)
    {
        if (_historyString.equals("")) _historyString = result.getValueString();
        if (_previousOperation == OperationType.PERCENT) _historyString = result.getValueString();

        switch (operation)
        {
            case ADDITION:
                additionOrSubtraction(operation, operant);
                break;
            case SUBTRACTION:
                additionOrSubtraction(operation, operant);
                break;
            case MULTIPLICATION:
                multiplicationOrDivision(operation, operant);
                break;
            case DIVISION:
                multiplicationOrDivision(operation, operant);
                break;
            case PERCENT:
                _historyString = operant.getValueString() + "% of " + result.getValueString();
                break;
        }

        if (_historyString.length() > _maxHistoryLength)
        {
            switch (operation)
            {
                case ADDITION:
                    _historyString = _previousResult.getValueString() + _add + operant.getValueString();
                    break;
                case SUBTRACTION:
                    _historyString = _previousResult.getValueString() + _subtract + operant.getValueString();
                    break;
                case MULTIPLICATION:
                    _historyString = _previousResult.getValueString() + _multiply + operant.getValueString();
                    break;
                case DIVISION:
                    _historyString = _previousResult.getValueString() + _divide + operant.getValueString();
                    break;
            }
        }
        _previousOperation = operation;
        _previousResult = result;
    }

    //If operation is of same priority ads it to history
    //otherwise gives result with new priority operations
    private void multiplicationOrDivision(OperationType operation, ValueHandler operant)
    {
        if (_previousOperation == OperationType.ADDITION
                || _previousOperation == OperationType.SUBTRACTION
                || _previousOperation == OperationType.PERCENT)
        {
            switch (operation)
            {
                case MULTIPLICATION:
                    _historyString = addBrackets(_multiply, _previousResult.getValueString(), operant);
                    break;
                case DIVISION:
                    _historyString = addBrackets(_divide, _previousResult.getValueString(), operant);
                    break;
            }
        }
        else
        {
            switch (operation)
            {
                case MULTIPLICATION:
                    _historyString = addBrackets(_multiply, _historyString, operant);
                    break;
                case DIVISION:
                    _historyString = addBrackets(_divide, _historyString, operant);
                    break;
            }
        }
    }

    //If operation is of same priority ads it to history
    //otherwise gives result with new priority operations
    private void additionOrSubtraction(OperationType operation, ValueHandler operant)
    {
        if (_previousOperation == OperationType.MULTIPLICATION
            || _previousOperation == OperationType.DIVISION
            || _previousOperation == OperationType.PERCENT)
        {
            switch (operation)
            {
                case ADDITION:
                    _historyString = addBrackets(_add, _previousResult.getValueString(), operant);
                    break;
                case SUBTRACTION:
                    _historyString = addBrackets(_subtract, _previousResult.getValueString(), operant);

                    break;
            }
        }
        else
        {
            switch (operation)
            {
                case ADDITION:
                    _historyString = addBrackets(_add, _historyString, operant);
                    break;
                case SUBTRACTION:
                    _historyString = addBrackets(_subtract, _historyString, operant);

                    break;
            }
        }
    }

    //return history of operations adding brackets, depending on positive or negative operant.
    @NonNull
    private String addBrackets(String operationString, String resultString, ValueHandler operant)
    {
        if (resultString.equals("") || resultString.equals(null)) return "";

        if (operant.getValueDouble()<0)
            return resultString + operationString +"(" + operant.getValueString() + ")";
        else
            return resultString + operationString + operant.getValueString();
    }

    public void resetHistory()
    {
        _historyString = "";
        _previousOperation = null;
        _previousResult = null;
    }

    public String getHistory()
    {
        if (_historyString.length() != 0) return _historyString + " =";
        else return _historyString;
    }
}
