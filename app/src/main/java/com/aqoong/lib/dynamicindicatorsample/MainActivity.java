package com.aqoong.lib.dynamicindicatorsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.aqoong.lib.dynamicindicator.DynamicIndicator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    int curPosition = 0;
    DynamicIndicator dynamicIndicator;

    String[] messages = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};

    CheckBox cbStrTrace;
    CheckBox cbMode;
    EditText etMaxSize;
    EditText etStrSelected;
    EditText etItemWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dynamicIndicator = findViewById(R.id.dynamic_indicator);
        dynamicIndicator.setTextList(messages);

        //default select
        dynamicIndicator.selectDot(curPosition);

        //for test
        findViewById(R.id.btn_prev).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_reset).setOnClickListener(this);

        cbStrTrace = findViewById(R.id.check_str_trace);
        cbMode = findViewById(R.id.check_mode);
        etMaxSize = findViewById(R.id.edit_maxsize);
        etItemWidth = findViewById(R.id.edit_itemWidth);
        etStrSelected = findViewById(R.id.edit_spot_str);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btn_prev:
                curPosition--;
                if(curPosition < 0){
                    curPosition = 0;
                }

                break;
            case R.id.btn_next:
                curPosition++;
                if(curPosition > dynamicIndicator.getMaxSize()-1){
                    curPosition = dynamicIndicator.getMaxSize()-1;
                }
                break;
        }

        if(etStrSelected.getText().length() > 0)
            dynamicIndicator.selectDot(curPosition, etStrSelected.getText().toString());
        else{
            dynamicIndicator.selectDot(curPosition);
        }

        if(v.getId() == R.id.btn_reset){
            curPosition = 0;
            dynamicIndicator
                    .setItemWidth(Integer.parseInt(etItemWidth.getText().toString()))
                    .setStringTraceMode(cbStrTrace.isChecked())
                    .setMode(cbMode.isChecked() ? DynamicIndicator.MODE_LINE : DynamicIndicator.MODE_DOT)
                    .setMaxSize(Integer.parseInt(etMaxSize.getText().toString()));

            dynamicIndicator.refreshIndicator();
            dynamicIndicator.selectDot(curPosition);
        }
    }

}
