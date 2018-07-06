package jubb.udemy.com.jubbadaycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button _btn_1;
    private Button _btn_2;
    private Button _btn_3;
    private Button _btn_4;
    private Button _btn_5;
    private Button _btn_6;
    private Button _btn_7;
    private Button _btn_8;
    private Button _btn_9;
    private Button _btn_0;


    private Button _btn_equals;
    private Button _btn_coma;

    private Button _btn_divide;
    private Button _btn_multiply;
    private Button _btn_subtraction;
    private Button _btn_addition;
    private Button _btn_posNeg;
    private Button _btn_percent;

    private TextView _txtHistory;
    private TextView _txtCurrentValue;

    private HistoryHandler _historyHandler = new HistoryHandler();
    private ValueHandler _result = new ValueHandler();
    private ValueHandler _operant = new ValueHandler();

    private OperationType _operation;

    private boolean _lastOperation = true;

    private String _div0 = "division by 0 error";
    private String _range = "result over double range";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonInitialize(_btn_1, R.id.btn_1);
        buttonInitialize(_btn_2, R.id.btn_2);
        buttonInitialize(_btn_3, R.id.btn_3);
        buttonInitialize(_btn_4, R.id.btn_4);
        buttonInitialize(_btn_5, R.id.btn_5);
        buttonInitialize(_btn_6, R.id.btn_6);
        buttonInitialize(_btn_7, R.id.btn_7);
        buttonInitialize(_btn_8, R.id.btn_8);
        buttonInitialize(_btn_9, R.id.btn_9);
        buttonInitialize(_btn_0, R.id.btn_0);

        buttonInitialize(_btn_divide, R.id.btn_divide);
        buttonInitialize(_btn_multiply, R.id.btn_multiply);
        buttonInitialize(_btn_subtraction, R.id.btn_subtraction);
        buttonInitialize(_btn_addition, R.id.btn_addition);
        buttonInitialize(_btn_posNeg, R.id.btn_posNeg);
        buttonInitialize(_btn_percent, R.id.btn_percent);

        buttonInitialize(_btn_equals, R.id.btn_equals);
        buttonInitialize(_btn_coma, R.id.btn_coma);

        _txtHistory = findViewById(R.id.txtHistory);
        _txtCurrentValue = findViewById(R.id.txtCurrentValue);
    }

    private void buttonInitialize(Button btn, int R_id)
    {
        btn = findViewById(R_id);
        btn.setOnClickListener(this);
    }

    private void buttonDigitPress(String a)
    {
        if (_lastOperation)
        {
            _result.resetValue();
            _operant.resetValue();
            _historyHandler.resetHistory();
            _txtHistory.setText("");
        }

        _operant.addDigit(a);
        _txtCurrentValue.setText(_operant.getValueString());
        _lastOperation = false;
    }

    public void buttonOperation(OperationType operation)
    {
        if (_result.getValueString().equals("") && !_operant.getValueString().equals(""))
        {
            _result.setValue(_operant.getValueDouble());
        }

        _operation = operation;
        _operant.resetValue();
        _lastOperation = false;
    }

    public void equalsOperation(OperationType operation)
    {
        if (_result.getValueString().equals("") || _operant.getValueString().equals("") ) return;

        switch (operation)
        {
            case ADDITION:
                _result.setValue(Calculator.addition(_result.getValueDouble(),_operant.getValueDouble()));
                break;
            case SUBTRACTION:
                _result.setValue(Calculator.subtraction(_result.getValueDouble(), _operant.getValueDouble()));
                break;
            case DIVISION:
                try
                {
                    _result.setValue(Calculator.division(_result.getValueDouble(), _operant.getValueDouble()));
                }
                catch (ArithmeticException e)
                {
                    _result.resetValue();
                    _operant.resetValue();
                    _txtHistory.setText(_div0);
                    _txtCurrentValue.setText(_result.getValueString());
                    return;
                }
                break;
            case MULTIPLICATION:
                _result.setValue(Calculator.multiply(_result.getValueDouble(), _operant.getValueDouble()));
                break;
            case PERCENT:
                _result.setValue(Calculator.multiply(_result.getValueDouble(), 0.01 * _operant.getValueDouble()));
                break;
        }

        if (_result.getValueDouble() == Double.NEGATIVE_INFINITY
            || _result.getValueDouble() == Double.POSITIVE_INFINITY)
        {
            _txtHistory.setText(_range);
            _result.resetValue();
        }
        _txtCurrentValue.setText(_result.getValueString());
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_1:
                buttonDigitPress("1");
                break;
            case R.id.btn_2:
                buttonDigitPress("2");
                break;
            case R.id.btn_3:
                buttonDigitPress("3");
                break;
            case R.id.btn_4:
                buttonDigitPress("4");
                break;
            case R.id.btn_5:
                buttonDigitPress("5");
                break;
            case R.id.btn_6:
                buttonDigitPress("6");
                break;
            case R.id.btn_7:
                buttonDigitPress("7");
                break;
            case R.id.btn_8:
                buttonDigitPress("8");
                break;
            case R.id.btn_9:
                buttonDigitPress("9");
                break;
            case R.id.btn_0:
                buttonDigitPress("0");
                break;
            case R.id.btn_addition:
                buttonOperation(OperationType.ADDITION);
                break;
            case R.id.btn_subtraction:
                buttonOperation(OperationType.SUBTRACTION);
                break;
            case R.id.btn_multiply:
                buttonOperation(OperationType.MULTIPLICATION);
                break;
            case R.id.btn_divide:
                buttonOperation(OperationType.DIVISION);
                break;
            case R.id.btn_percent:
                buttonOperation(OperationType.PERCENT);
                break;
            case R.id.btn_coma:
                _operant.addComa();
                _lastOperation = false;
                break;
            case R.id.btn_posNeg:
                _operant.addMinus();
                _lastOperation = false;
                break;
            case R.id.btn_equals:
                if (_operation != null
                    && !_operant.getValueString().equals(""))
                {
                    _historyHandler.updateHistory(_operation, _operant, _result);
                    _txtHistory.setText(_historyHandler.getHistory());

                    equalsOperation(_operation);
                    _lastOperation = true;
                }
                break;
        }
    }
}
