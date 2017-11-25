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

public class ImageInputFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        View view = inflater.inflate(R.layout.image_input_fragment,container,false);

        return view;
    }
}
