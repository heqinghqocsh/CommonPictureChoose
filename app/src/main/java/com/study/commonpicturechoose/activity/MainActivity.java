package com.study.commonpicturechoose.activity;

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
        mPictureChooseView = (CommonPictureChooseView)findViewById(R.id.common_picture_choose);
        mPictureChooseView.setOnlyShowImg(false);
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/1.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/2.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/3.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/333.jpg");

        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/1.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/2.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/3.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/22.jpg");

        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/1.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/2.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/3.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/4.jpg");
        mPictureChooseView.addImgPath("http://192.168.191.1:8080/PictureUrlData/picture/22.jpg");
    }
}
