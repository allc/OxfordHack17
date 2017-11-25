package com.example.yizheng.oxhack;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class TextInputFragment extends Fragment {

    private String content;
    private Button searchButton, clearButton;
    private EditText URLEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        View view = inflater.inflate(R.layout.text_input_fragment,container,false);
        TextView text = (TextView) view.findViewById(R.id.txt_content);
        text.setText(content);

        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);

        searchButton = (Button) view.findViewById(R.id.text_search_button);
        searchButton.setOnClickListener(new TextSeawrchButtonListener());

        return view;
    }

    public void setContent(String content){
        this.content = content;
    }

    class TextSeawrchButtonListener implements Button.OnClickListener{

        String URL;

        @Override
        public void onClick(View v){
            URL = URLEditText.getText().toString();
            String msg= "URL Read: ";
            Log.d(msg,URL);
        }

    }

}
