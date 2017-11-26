package com.example.yizheng.oxhack;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yi Zheng on 11/26/2017.
 */

public class ListItemClickListener implements AdapterView.OnItemClickListener {

    private Map<String,String> data;
    private Activity act;
    private ListView listView;

    public ListItemClickListener(Map<String,String> data, Activity act, ListView listView){
        this.data = data;
        this.act =act;
        this.listView = (ListView) listView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("click:" , listView.getItemAtPosition(position).toString());

        Uri uri=Uri.parse(data.get(listView.getItemAtPosition(position).toString()));
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        act.startActivity(intent);

    }
}
