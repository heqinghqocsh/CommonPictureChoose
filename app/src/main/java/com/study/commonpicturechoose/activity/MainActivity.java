package com.study.commonpicturechoose.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.study.commonpicturechoose.R;
import com.study.commonpicturechoose.widget.CommonPictureChooseView;

public class MainActivity extends AppCompatActivity {

    private CommonPictureChooseView mPictureChooseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPictureChooseView = (CommonPictureChooseView) findViewById(R.id.common_picture_choose);
        mPictureChooseView.setOnlyShowImg(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPictureChooseView.onActivityResult(requestCode, resultCode, data);
    }
}
