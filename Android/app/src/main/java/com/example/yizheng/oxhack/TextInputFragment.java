package com.example.yizheng.oxhack;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class TextInputFragment extends Fragment {

    private String content;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        View view = inflater.inflate(R.layout.text_input_fragment,container,false);
        TextView text = (TextView) view.findViewById(R.id.txt_content);
        text.setText(content);
        return view;
    }

    public void setContent(String content){
        this.content = content;
    }

}
