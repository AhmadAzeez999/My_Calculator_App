package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView problem, solution;
    MaterialButton buttonC, buttonAC, buttonPercentage;
    MaterialButton buttonDivide, buttonMultiply, buttonMinus, buttonPlus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8;
    MaterialButton button9, buttonDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        problem = findViewById(R.id.problem_tv);
        solution = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.button_c);
        assignId(buttonAC, R.id.button_ac);
        assignId(buttonPercentage, R.id.button_perc);
        assignId(buttonDivide, R.id.button_divide);
        assignId(buttonMultiply, R.id.button_multiply);
        assignId(buttonMinus, R.id.button_minus);
        assignId(buttonPlus, R.id.button_plus);
        assignId(buttonEquals, R.id.button_equals);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonDot, R.id.button_dot);



    }

    void assignId(MaterialButton btn, int id)
    {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String problemToCalculate = problem.getText().toString();


        if (buttonText.equals("÷"))
        {
            buttonText = "/";
        }
        else if (buttonText.equals("×"))
        {
            buttonText = "*";
        }

        if (buttonText.equals("AC"))
        {
            problem.setText("");
            solution.setText("0");
            return;
        }
        if (buttonText.equals("="))
        {
            problem.setText(solution.getText());
            return;
        }
        if (buttonText.equals("C"))
        {
            if (problemToCalculate.isEmpty())
                problemToCalculate = "0";
            else {
                problemToCalculate = problemToCalculate.substring(0, problemToCalculate.length() - 1);
                if (problemToCalculate.isEmpty()) problemToCalculate = "0";
            }

        }
        else {
            problemToCalculate = problemToCalculate + buttonText;
            if (problemToCalculate.charAt(0) == '0')
                problemToCalculate = problemToCalculate.substring(problemToCalculate.length() - 1);
        }

        problem.setText(problemToCalculate);

        String finalResult = getResult(problemToCalculate);

        if(!finalResult.equals("ERROR!!!"))
        {
            solution.setText(finalResult);
        }
    }

    String getResult(String data)
    {
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0"))   // to remove the ".0" in the answer text view
            {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        }
        catch(Exception e)
        {
            return "ERROR!!!";
        }
    }
}