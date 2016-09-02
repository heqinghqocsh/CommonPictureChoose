package com.study.commonpicturechoose.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.study.commonpicturechoose.R;


/**
 * Created on 2016/1/11.
 * @description 弹出从相册选取还是相机拍照的窗口
 */
public class ChooseImgPopupWindow extends PopupWindow implements View.OnClickListener{

    private Context mContext;
    private OnClickListener mOnClickListener;

    public ChooseImgPopupWindow(Context context, OnClickListener onClickListener){
        mContext = context;
        mOnClickListener = onClickListener;
        initPopupWindow();
    }

    private void initPopupWindow() {
        View defaultView = LayoutInflater.from(mContext).inflate(R.layout.choose_img_popup, null);

        TextView album = (TextView) defaultView.findViewById(R.id.choose_from_album);
        TextView camera = (TextView) defaultView.findViewById(R.id.choose_from_camera);
        TextView cancel = (TextView) defaultView.findViewById(R.id.choose_cancel);

        album.setOnClickListener(this);
        camera.setOnClickListener(this);
        cancel.setOnClickListener(this);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        defaultView.setLayoutParams(layoutParams);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(defaultView);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setBackgroundDrawable(new ColorDrawable(mContext.getResources()
                    .getColor(android.R.color.transparent)));
        } else {
            setBackgroundDrawable(new ColorDrawable(mContext.getResources()
                    .getColor(android.R.color.transparent, null)));
        }

        defaultView.setFocusableInTouchMode(true);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null){
            switch (v.getId()){
                case R.id.choose_from_album:
                    mOnClickListener.chooseFromAlbum();
                    break;
                case R.id.choose_from_camera:
                    mOnClickListener.chooseFromCamera();
                    break;
                case R.id.choose_cancel:
                    mOnClickListener.chooseCancel();
                    break;
            }
        }
    }

    public  interface OnClickListener{
        void chooseFromAlbum();
        void chooseFromCamera();
        void chooseCancel();
    }

}
