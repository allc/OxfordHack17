package com.example.yizheng.oxhack;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class VideoInputFragment extends Fragment {

    private Button searchButton, clearButton;
    private EditText URLEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        View view = inflater.inflate(R.layout.video_input_fragment,container,false);


        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);

        searchButton = (Button) view.findViewById(R.id.video_search_button);
        clearButton = (Button) view.findViewById(R.id.video_clear_button);
        addListeners();
        return view;
    }

    private void addListeners(){
        searchButton.setOnClickListener(new VideoSearchButtonListener());
        clearButton.setOnClickListener(new ClearButtonListener(URLEditText));
    }


    class VideoSearchButtonListener implements Button.OnClickListener{

        String URL;

        @Override
        public void onClick(View v){
            URL = URLEditText.getText().toString();

            // Do the HTTP query here.
            String msg= "Video URL Read: ";
            Log.d(msg,URL);
        }

    }
}
