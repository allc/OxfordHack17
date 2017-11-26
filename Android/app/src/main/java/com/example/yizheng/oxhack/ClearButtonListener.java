package com.example.yizheng.oxhack;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class ClearButtonListener implements Button.OnClickListener {

    private EditText URLEdit;
    private ListView listView;


    public ClearButtonListener(EditText et,ListView listView){
        this.URLEdit = (EditText) et;
        this.listView = (ListView) listView;
    }

    @Override
    public void onClick(View v){
        URLEdit.setText("");
        clearSearchResults();
    }



    public  void clearSearchResults(){
        listView.setAdapter(null);
    }


}
