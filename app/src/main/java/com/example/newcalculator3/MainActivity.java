package com.example.newcalculator3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static String first_key = "FIRST_KEY";
    public static String second_key = "SECOND_KEY";
    public static String operation_key = "OPERATION_KEY";
    public static String result_key = "RESULT_KEY";
    public static String rawOperand_key = "RAWOPERAND_KEY";
    private ActionSender actionSender;
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private Fragment fragment;

    TextView textView;

    String rawOperand = "";
    Double firstValues;
    Double secondValues;
    String operation;
    Double result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.result_txt_view);

        recyclerView = findViewById(R.id.recycler_view);
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


        if (savedInstanceState != null) {

            firstValues = savedInstanceState.getDouble(first_key);
            secondValues = savedInstanceState.getDouble(second_key);
            operation = savedInstanceState.getString(operation_key);
            result = savedInstanceState.getDouble(result_key);
            rawOperand = savedInstanceState.getString(rawOperand_key);
        }
        fragment= new HistoryFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container ,fragment).commit();
    }
    public void passVal(ActionSender fragmentCommunicator) {
        this.actionSender = fragmentCommunicator;

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (firstValues != null) {
            outState.putDouble(first_key, firstValues);
        }
        if (secondValues != null) {
            outState.putDouble(second_key, secondValues);
        }
        if (operation != null) {
            outState.putString(operation_key, operation);
        }
        if (result != null) {
            outState.putDouble(result_key, result);
        }
        if (rawOperand != null) {
            outState.putString(rawOperand_key, rawOperand);
        }
    }

    public void onNumbersClick(View view) {
        switch (view.getId()) {
            case R.id.zero:
                textView.append("0");
                break;
            case R.id.one:
                textView.append("1");
                break;
            case R.id.two:
                textView.append("2");
                break;
            case R.id.three:
                textView.append("3");
                break;
            case R.id.four:
                textView.append("4");
                break;
            case R.id.five:
                textView.append("5");
                break;
            case R.id.six:
                textView.append("6");
                break;
            case R.id.seven:
                textView.append("7");
                break;
            case R.id.eight:
                textView.append("8");
                break;
            case R.id.nine:
                textView.append("9");
                break;
            case R.id.dot:
                if (textView != null && textView.length() > 0) {
                    textView.append(".");
                } else {
                    textView.setText("");
                }

                break;
            case R.id.clear:
                textView.setText("");
                break;

        }
    }

    public void onOperationsClick(View view) {

        try {
            if (firstValues == null) {
                firstValues = Double.parseDouble(rawOperand);
            } else {
                secondValues = Double.parseDouble(rawOperand);
            }
            rawOperand = "";
        } catch (Exception e) {
            e.printStackTrace();
        }


        switch (view.getId()) {
            case R.id.plus:
                operation = "+";
                firstValues = Double.valueOf(textView.getText().toString());
                textView.setText(firstValues + "+");
                break;
            case R.id.minus:
                operation = "-";
                firstValues = Double.valueOf(textView.getText().toString());
                textView.setText(firstValues + "-");
                break;
            case R.id.multiply:
                operation = "*";
                firstValues = Double.valueOf(textView.getText().toString());
                textView.setText(firstValues + "*");
                break;
            case R.id.divide:
                operation = "/";
                firstValues = Double.valueOf(textView.getText().toString());
                textView.setText(firstValues + "/");
                break;
            case R.id.procent:
                operation = "%";
                firstValues = Double.valueOf(textView.getText().toString());
                textView.setText(firstValues + "%");
                break;

            case R.id.equal:
                if (operation != null) {
                    String second = textView.getText().toString().replace(firstValues + operation + "", "");
                    secondValues = Double.valueOf(second);

                    switch (operation) {
                        case "+":
                            result = firstValues + secondValues;
                            textView.setText(firstValues + "+" + secondValues + "=" + result);
                            break;
                        case "-":
                            result = firstValues - secondValues;
                            textView.setText(firstValues + "-" + secondValues + "=" + result);
                            break;
                        case "*":
                            result = firstValues * secondValues;
                            textView.setText(firstValues + "*" + secondValues + "=" + result);
                            break;
                        case "/":
                            result = firstValues / secondValues;
                            textView.setText(firstValues + "/" + secondValues + "=" + result);
                            break;
                    }
                }
            default:
                break;
        }

    }

    public void fragment_ch_Buttons(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new ButtonsFragment());
        transaction.commit();
    }

    public void fragment_ch_History(View view) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new HistoryFragment());
        transaction.commit();


    }
    public void send(View view) {
        actionSender.send(textView.getText().toString());
    }
}

