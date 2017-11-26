package com.example.yizheng.oxhack;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class ImageInputFragment extends Fragment {

    private Button searchButton, clearButton, takePicButton;
    private EditText URLEditText;
    private ImageView previewImage;
    private File imageFile = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        View view = inflater.inflate(R.layout.image_input_fragment,container,false);

        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);
        searchButton = (Button) view.findViewById(R.id.image_search_button);
        clearButton = (Button) view.findViewById(R.id.image_clear_button);
        takePicButton = (Button) view.findViewById(R.id.image_take_button);
        previewImage = (ImageView) view.findViewById(R.id.previewImage);
        addListeners();

        return view;
    }

    private void addListeners(){
        searchButton.setOnClickListener(new ImageSearchButtonListener());
        clearButton.setOnClickListener(new ClearButtonListener(URLEditText));
        takePicButton.setOnClickListener(new CameraListener(previewImage));
    }

    class ImageSearchButtonListener implements Button.OnClickListener{

        String URL;

        @Override
        public void onClick(View v){
            URL = URLEditText.getText().toString();

            // Do the HTTP query here.
            String msg= "URL Read: ";
            Log.d(msg,URL);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == Activity.DEFAULT_KEYS_DIALER){
            previewImage.setImageURI(Uri.fromFile(imageFile));
        }
    }

    class CameraListener implements Button.OnClickListener {

        private ImageView previewImage;

        public CameraListener(ImageView imageView){
            this.previewImage = imageView;
        }


        @Override
        public void onClick(View v){
            File dir = new File(Environment.getExternalStorageDirectory(),"pictures");
            String msg = "SD: ";
            Log.d(msg,Environment.getExternalStorageDirectory().toString());
            if(!dir.exists()){
                dir.mkdir();
            }
            imageFile = new File(dir, System.currentTimeMillis()+".jpg");
            if(!imageFile.exists()){
                try{
                    imageFile.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            startActivityForResult(photoIntent,Activity.DEFAULT_KEYS_DIALER);

        }




    }

}
