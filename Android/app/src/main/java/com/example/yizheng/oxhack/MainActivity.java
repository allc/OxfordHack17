package com.example.yizheng.oxhack;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TabActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView topVideo;
    private TextView topAudio;
    private TextView topImage;
    private TextView topText;
    private ConstraintLayout inputLayout;

    private InputFragement inputfg1, inputfg2,inputfg3,inputfg4;
    private FragmentManager fManager;


    /*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.bottom_tab1);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.bottom_tab2);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.bottom_tab3);
                    return true;
            }
            return false;
        }
    };
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fManager = getFragmentManager();
        bindListeners();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void bindListeners(){
        topVideo = (TextView) findViewById(R.id.top_tab1);
        topAudio = (TextView) findViewById(R.id.top_tab2);
        topImage = (TextView) findViewById(R.id.top_tab3);
        topText = (TextView) findViewById(R.id.top_tab4);

        inputLayout = (ConstraintLayout) findViewById(R.id.input_layout);

        clickListener listener;
        listener = new clickListener();

        topVideo.setOnClickListener(listener);
        topAudio.setOnClickListener(listener);
        topImage.setOnClickListener(listener);
        topText.setOnClickListener(listener);

    }

    class clickListener implements View.OnClickListener{

        @Override
        public void onClick(View v){
            String msg = "asdfasdf";
            Log.d(msg,"asdfasdf");
            FragmentTransaction fTransaction = fManager.beginTransaction();
            hideAllFragement(fTransaction);
            switch(v.getId()){
                case R.id.top_tab1:
                    setSelected();
                    topVideo.setSelected(true);
                    if(inputfg1 == null){
                        inputfg1 = new InputFragement();
                        inputfg1.setContent("Video");
                        fTransaction.add(R.id.input_layout,inputfg1);
                    }
                    else{
                        fTransaction.show(inputfg1);
                    }
                    break;
                case R.id.top_tab2:
                    setSelected();
                    topAudio.setSelected(true);
                    if(inputfg2 == null){
                        inputfg2 = new InputFragement();
                        inputfg2.setContent("Audio");
                        fTransaction.add(R.id.input_layout,inputfg2);
                    }
                    else{
                        fTransaction.show(inputfg2);
                    }
                    break;
                case R.id.top_tab3:
                    setSelected();
                    topImage.setSelected(true);
                    if(inputfg3 == null){
                        inputfg3 = new InputFragement();
                        inputfg3.setContent("Image");
                        fTransaction.add(R.id.input_layout,inputfg3);
                    }
                    else{
                        fTransaction.show(inputfg3);
                    }
                    break;
                case R.id.top_tab4:
                    setSelected();
                    topText.setSelected(true);
                    if(inputfg4 == null){
                        inputfg4 = new InputFragement();
                        inputfg4.setContent("Text");
                        fTransaction.add(R.id.input_layout,inputfg4);
                        }
                    else{
                        fTransaction.show(inputfg4);
                    }

                    break;
            }
            fTransaction.commit();
        }

        private void setSelected(){
            topVideo.setSelected(false);
            topAudio.setSelected(false);
            topImage.setSelected(false);
            topText.setSelected(false);
        }


        private void hideAllFragement(FragmentTransaction fgTransaction){
            if(inputfg1 != null){
                fgTransaction.hide(inputfg1);
            }
            if(inputfg2 != null){
                fgTransaction.hide(inputfg2);
            }
            if(inputfg3 != null){
                fgTransaction.hide(inputfg3);
            }
            if(inputfg4 != null){
                fgTransaction.hide(inputfg4);
            }
        }

    }

}
