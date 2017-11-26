package com.example.yizheng.oxhack;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class ClearButtonListener implements Button.OnClickListener {

    private EditText URLEdit;

    public ClearButtonListener(EditText et){
        this.URLEdit = (EditText) et;
    }

    @Override
    public void onClick(View v){
        URLEdit.setText("");

    }


}
