package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView resultView, currentNumberInputView;
    private String currentInput = "";
    private String previousInput = "";
    private String operator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        resultView = findViewById(R.id.resultView);
        currentNumberInputView = findViewById(R.id.currentNumberInputView);

        // Set up number buttons
        setNumberButtonClickListener(R.id.zeroButton, "0");
        setNumberButtonClickListener(R.id.oneButton, "1");
        setNumberButtonClickListener(R.id.twoButton, "2");
        setNumberButtonClickListener(R.id.threeButton, "3");
        setNumberButtonClickListener(R.id.fourButton, "4");
        setNumberButtonClickListener(R.id.fiveButton, "5");
        setNumberButtonClickListener(R.id.sixButton, "6");
        setNumberButtonClickListener(R.id.sevenButton, "7");
        setNumberButtonClickListener(R.id.eightButton, "8");
        setNumberButtonClickListener(R.id.nineButton, "9");

        // Set up operation buttons
        setOperationButtonClickListener(R.id.plusButton, "+");
        setOperationButtonClickListener(R.id.minusButton, "-");
        setOperationButtonClickListener(R.id.multiplyButton, "×");
        setOperationButtonClickListener(R.id.divisionButton, "÷");
        setOperationButtonClickListener(R.id.modulueButton, "%");
        setOperationButtonClickListener(R.id.plusMinusButton, "+/−");

        // Set up special buttons
        findViewById(R.id.equalButton).setOnClickListener(this::onEqualButtonClick);
        findViewById(R.id.clearScreenButton).setOnClickListener(this::onClearButtonClick);
        findViewById(R.id.deleteLastDigitButton).setOnClickListener(this::onDeleteButtonClick);
        findViewById(R.id.dotButton).setOnClickListener(v -> appendToCurrentInput("."));
        findViewById(R.id.plusMinusButton).setOnClickListener(v -> clickOnPlusMinus());
    }

    private void setNumberButtonClickListener(int buttonId, String value) {
        findViewById(buttonId).setOnClickListener(v -> appendToCurrentInput(value));
    }

    private void setOperationButtonClickListener(int buttonId, String operation) {
        findViewById(buttonId).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                if (!previousInput.isEmpty() && !operator.isEmpty()) {
                    calculateResult();
                } else {
                    previousInput = currentInput;
                }
                operator = operation;
                currentInput = "";
                updateDisplay();
            }
        });
    }

    private void clickOnPlusMinus() {
        double currentValue = Double.parseDouble(currentInput);
        currentValue *= -1;
        currentInput = Double.toString(currentValue);
        updateDisplay();
    }

    private void appendToCurrentInput(String value) {
        if (isNewOperation) {
            currentInput = "";
            isNewOperation = false;
        }

        if (value.equals(".") && currentInput.contains(".")) {
            Toast.makeText(this, "Cannot add more than one decimal point", Toast.LENGTH_SHORT).show();
            return;
        }

        currentInput += value;
        updateDisplay();
    }

    private void onEqualButtonClick(View v) {
        if (!previousInput.isEmpty() && !currentInput.isEmpty() && !operator.isEmpty()) {
            calculateResult();
            operator = "";
            isNewOperation = true;
        }
    }

    private void onClearButtonClick(View v) {
        currentInput = "";
        previousInput = "";
        operator = "";
        updateDisplay();
    }

    private void onDeleteButtonClick(View v) {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            updateDisplay();
        }
    }

    private void calculateResult() {
        double num1 = Double.parseDouble(previousInput);
        double num2 = Double.parseDouble(currentInput);
        double result = 0;

        switch (operator) {
            case "%":
                result = num1 % num2;
                break;
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "×":
                result = num1 * num2;
                break;
            case "÷":
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    currentInput = "Error";
                    previousInput = "";
                    operator = "";
                    updateDisplay();
                    return;
                }
                break;
        }

        currentInput = String.valueOf(result);
        previousInput = String.valueOf(result);
        updateDisplay();
    }

    private void updateDisplay() {
        resultView.setText(previousInput + " " + operator);
        currentNumberInputView.setText(currentInput);
    }
}
