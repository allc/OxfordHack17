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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class VideoInputFragment extends Fragment {

    private Button searchButton, clearButton;
    private EditText URLEditText;
    private ListView dataList;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        view = inflater.inflate(R.layout.video_input_fragment,container,false);


        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);

        searchButton = (Button) view.findViewById(R.id.video_search_button);
        clearButton = (Button) view.findViewById(R.id.video_clear_button);
        dataList = (ListView) getActivity().findViewById(R.id.data_list);
        addListeners();
        return view;
    }

    private void addListeners(){
        searchButton.setOnClickListener(new VideoSearchButtonListener());
        clearButton.setOnClickListener(new ClearButtonListener(URLEditText,dataList));
    }


    class VideoSearchButtonListener implements Button.OnClickListener{

        String URL;

        @Override
        public void onClick(View v){
            URL = URLEditText.getText().toString();

            // Do the HTTP query here.
            String msg= "Video URL Read: ";
            Log.d(msg,URL);

            // Get data method here.


            HashMap<String, String> data = new HashMap<>();


            // Must have http:// otherwise Activity not found.
            data.put("Google","http://www.google.com");
            data.put("Amazon","http://www.amazon.co.uk");
            data.put("Test","http://www.github.com");

            setList(data);

        }

        public void setList(HashMap<String,String> data){

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.list_item, new ArrayList<String>(data.keySet()));
            dataList.setAdapter(listAdapter);
            dataList.setOnItemClickListener(new ListItemClickListener(data,getActivity(),dataList));

        }

    }
}
