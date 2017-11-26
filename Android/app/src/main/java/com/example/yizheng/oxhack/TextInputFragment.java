package com.example.yizheng.oxhack;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class TextInputFragment extends Fragment {

    private Button searchButton, clearButton;
    private EditText URLEditText;
    private ListView dataList;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        view = inflater.inflate(R.layout.text_input_fragment,container,false);

        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);
        searchButton = (Button) view.findViewById(R.id.text_search_button);
        clearButton = (Button) view.findViewById(R.id.text_clear_button);
        dataList = (ListView) getActivity().findViewById(R.id.data_list);

        addListeners();
        return view;
    }

    private void addListeners(){
        searchButton.setOnClickListener(new TextSearchButtonListener());
        clearButton.setOnClickListener(new ClearButtonListener(URLEditText,dataList));
    }

    class TextSearchButtonListener implements Button.OnClickListener{

        String URL;

        @Override
        public void onClick(View v){
            URL = URLEditText.getText().toString();

            // Do the HTTP query here.
            String msg= "URL Read: ";
            Log.d(msg,URL);

            String[] urls = {URL};
            Map<String, String> data = new TextProcess().doQuery(URL);

            setList(data);

        }

        public void setList(Map<String,String> data){

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.list_item, new ArrayList<String>(data.keySet()));
            dataList.setAdapter(listAdapter);
        }
    }

}
