package com.study.commonpicturechoose.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.study.commonpicturechoose.adapter.CommonPictureChooseViewAdapter;

import java.util.ArrayList;

/**
 * Created on 2016/9/1.
 *
 * @description
 */
public class CommonPictureChooseView extends RecyclerView{

    private GridLayoutManager mGridLayoutManager;
    private ArrayList<String> mPathList = new ArrayList<>();
    private CommonPictureChooseViewAdapter mAdapter;

    public CommonPictureChooseView(Context context) {
        super(context);
        init();
    }

    public CommonPictureChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mGridLayoutManager = new GridLayoutManager(getContext(),3);
        setLayoutManager(mGridLayoutManager);
        mAdapter = new CommonPictureChooseViewAdapter(getContext(),mPathList);
        setAdapter(mAdapter);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);

    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);

    }

    public void setOnlyShowImg(boolean onlyShowImg){
        mAdapter.setOnlyShow(onlyShowImg);
    }

    public void addImgPath(String path){
        mPathList.add(path);
        mAdapter.notifyDataSetChanged();
    }


}
