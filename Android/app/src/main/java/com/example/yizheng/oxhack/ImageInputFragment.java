package com.example.yizheng.oxhack;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Yi Zheng on 11/25/2017.
 */

public class ImageInputFragment extends Fragment {

    private Button searchButton, clearButton, takePicButton;
    private EditText URLEditText;
    private ImageView previewImage;
    private File imageFile = null;
    private Button localImageButton;
    private ListView dataList;
    private View view;
    private final int cameraCode = 2017;
    private final int localCode = 2018;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        view = inflater.inflate(R.layout.image_input_fragment,container,false);

        URLEditText = (EditText) view.findViewById(R.id.URL_edit_text);
        searchButton = (Button) view.findViewById(R.id.image_search_button);
        clearButton = (Button) view.findViewById(R.id.image_clear_button);
        takePicButton = (Button) view.findViewById(R.id.image_take_button);
        previewImage = (ImageView) view.findViewById(R.id.previewImage);
        localImageButton = (Button) view.findViewById(R.id.local_image_button);
        dataList = (ListView) getActivity().findViewById(R.id.data_list);
        addListeners();

        return view;
    }

    private void addListeners(){
        searchButton.setOnClickListener(new ImageSearchButtonListener());
        clearButton.setOnClickListener(new ClearButtonListener(URLEditText,dataList));
        takePicButton.setOnClickListener(new CameraListener(previewImage));
        localImageButton.setOnClickListener(new LocalImageOpenListener());
    }

    public File getBufferedImage(){
        if(imageFile != null){
            return imageFile;
        }
        else{
            System.err.println("NO FILE LOADED!!!");
            return null;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (requestCode == cameraCode) {

            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());

            previewImage.setImageBitmap(rotationFix(bitmap));
        }
        else if(requestCode == localCode && data!=null){
            Uri uri = data.getData();

            Log.d("local",uri.toString());
            ContentResolver cr = getActivity().getContentResolver();
            Bitmap bitmap;
            try{
                imageFile =  new File(getPath(uri,cr));
                Bitmap bitmap2 = BitmapFactory.decodeFile(imageFile.getPath());
                previewImage.setImageBitmap(rotationFix(bitmap2));
            }
            catch (Exception e){
                e.printStackTrace();
            }
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


    // Method to fix the rotation problem of images.
    private Bitmap rotationFix(Bitmap bitmap){
        ExifInterface exif  = null;

        try{
            exif= new ExifInterface(imageFile.getPath());
        }
        catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }

        int degree=0;
        if(exif != null){
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch(ori){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }

            if (degree != 0) {
                Matrix m = new Matrix();
                m.postRotate(degree);

                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),
                        bitmap.getHeight(), m, true);
            }
            return bitmap;
        }
        return null;
    }


    // Listeners

    class LocalImageOpenListener implements Button.OnClickListener{


        @Override
        public void onClick(View v){
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            PackageManager manager = getActivity().getPackageManager();
            List<ResolveInfo> apps = manager.queryIntentActivities(intent,
                    0);
            if (apps.size() > 0) {
                startActivityForResult(intent, localCode);
            }
        }
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


        public void setList(HashMap<String,String> data){

            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.list_item, new ArrayList<String>(data.keySet()));
            dataList.setAdapter(listAdapter);
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
            startActivityForResult(photoIntent,cameraCode);

        }






    }

}
