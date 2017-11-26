package com.example.yizheng.oxhack;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class VideoInputFragment extends Fragment {

    private Button searchButton, clearButton,recordButton,loadVideoButton;
    private EditText URLEditText;
    private ListView dataList;
    private View view;
    private final int VIDEO_CODE = 2019;
    private final int LOCAL_Code = 2020;
    private File videoFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        view = inflater.inflate(R.layout.video_input_fragment,container,false);


        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);

        searchButton = (Button) view.findViewById(R.id.video_search_button);
        clearButton = (Button) view.findViewById(R.id.video_clear_button);
        recordButton =(Button) view.findViewById(R.id.record_video_button);
        loadVideoButton = (Button) view.findViewById(R.id.local_video_button);

        dataList = (ListView) getActivity().findViewById(R.id.data_list);
        addListeners();
        return view;
    }

    private void addListeners(){
        searchButton.setOnClickListener(new VideoSearchButtonListener());
        clearButton.setOnClickListener(new ClearButtonListener(URLEditText,dataList));
        recordButton.setOnClickListener(new RecordListener());
        loadVideoButton.setOnClickListener(new LocalVideoListener());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == Activity.RESULT_OK && requestCode == VIDEO_CODE){
                Uri uri = data.getData();

            }
            else if(requestCode == LOCAL_Code){
                Uri uri = data.getData();
                ContentResolver cr = getActivity().getContentResolver();
                try {
                    videoFile = new File(getPath(uri, cr));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if(videoFile != null){
                    URLEditText.setText(videoFile.getPath());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String getPath(Uri uri, ContentResolver cr)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }


    class LocalVideoListener implements Button.OnClickListener{

        @Override
        public void onClick(View v){
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("video/*");
            PackageManager manager = getActivity().getPackageManager();
            List<ResolveInfo> apps = manager.queryIntentActivities(intent,
                    0);
            if (apps.size() > 0) {
                startActivityForResult(intent, LOCAL_Code);
            }
        }
    }

    class RecordListener implements Button.OnClickListener{

        @Override
        public void onClick(View v){

            File dir = new File(Environment.getExternalStorageDirectory(),"videos");
            if(!dir.exists()){
                dir.mkdir();
            }
            videoFile = new File(dir, System.currentTimeMillis()+".mp4");

            Uri uri = Uri.fromFile(videoFile);

            Intent intent = new Intent();
            intent.setAction("android.media.action.VIDEO_CAPTURE");
            //intent.putExtra (MediaStore.EXTRA_DURATION_LIMIT,15);

            //intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            startActivityForResult (intent, VIDEO_CODE);
        }
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
