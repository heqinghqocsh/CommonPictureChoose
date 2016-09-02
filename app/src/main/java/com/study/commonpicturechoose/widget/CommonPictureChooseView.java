package com.study.commonpicturechoose.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

import com.study.commonpicturechoose.adapter.CommonPictureChooseViewAdapter;
import com.study.commonpicturechoose.base.BaseApplication;
import com.study.commonpicturechoose.utils.ImageUtils;
import com.study.commonpicturechoose.utils.Tools;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created on 2016/9/1.
 *
 * @description
 */
public class CommonPictureChooseView extends RecyclerView
        implements CommonPictureChooseViewAdapter.OnItemClickListener
        ,ChooseImgPopupWindow.OnClickListener,ChooseImgPopupWindow.OnDismissListener{
    private static final String FILE_NAME_TIME_STEMP = "yyyyMMddHHmmss";
    public static final int CHOOSE_FROM_ALBUM = 0XFF01;
    public static final int CHOOSE_FROM_CAMERA = 0XFF02;
    private String PICTURE_FILE_SUFFIX = ".jpg";

    private GridLayoutManager mGridLayoutManager;
    private ArrayList<String> mPathList = new ArrayList<>();
    private CommonPictureChooseViewAdapter mAdapter;

    private ChooseImgPopupWindow mChooseImgPopupWindow = null;
    private Context mContext;
    private String mOutputPath;

    public CommonPictureChooseView(Context context) {
        super(context);
        init(context);
    }

    public CommonPictureChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mGridLayoutManager = new GridLayoutManager(getContext(),3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,HORIZONTAL,false);
        addItemDecoration(new SpaceItemDecoration(mContext, 4));
        setLayoutManager(linearLayoutManager);
        mAdapter = new CommonPictureChooseViewAdapter(getContext(),mPathList);
        mAdapter.setOnItemClickListener(this);
        setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(CommonPictureChooseViewAdapter.ViewHolder viewHolder, int position) {
        if (position == mPathList.size()){
            if (null == mChooseImgPopupWindow) {
                mChooseImgPopupWindow = new ChooseImgPopupWindow(mContext, this);
                mChooseImgPopupWindow.setOnDismissListener(this);
                mChooseImgPopupWindow.setOutsideTouchable(true);
            }
            mChooseImgPopupWindow.showAtLocation(((Activity)mContext).getWindow().getDecorView()
                    , Gravity.CENTER, 0, 0);
            setBackGroundAlpha(0.5f);
        }else{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.parse(mPathList.get(position));
            intent.setDataAndType(uri,"image/*");
            mContext.startActivity(intent);
//            removeItemAtIndex(4);
        }
    }

    /**
     * 从相册选取
     */
    @Override
    public void chooseFromAlbum() {
        mChooseImgPopupWindow.dismiss();
        Intent intent = new Intent(Intent.ACTION_PICK
                , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity)mContext).startActivityForResult(intent, CHOOSE_FROM_ALBUM);
//        mCurrentFragment.startActivityForResult(intent, CHOOSE_FROM_ALBUM);
    }

    /**
     * 从相机拍照
     */
    @Override
    public void chooseFromCamera() {
        mChooseImgPopupWindow.dismiss();
        if (!Tools.hasSDCard()) {
            Toast.makeText(mContext, "SD卡未挂载!", Toast.LENGTH_SHORT).show();
            return;
        }
        File saveDir = new File(BaseApplication.PICTURE_SAVE_PATH);
        if (!saveDir.exists()){
            saveDir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat(FILE_NAME_TIME_STEMP).format(new Date());
        String pictureName = timeStamp + PICTURE_FILE_SUFFIX;
        File pictureFile = new File(BaseApplication.PICTURE_SAVE_PATH,pictureName);
        mOutputPath = pictureFile.getAbsolutePath();

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pictureFile));
        ((Activity)mContext).startActivityForResult(intent, CHOOSE_FROM_CAMERA);
//        mCurrentFragment.startActivityForResult(intent, CHOOSE_FROM_CAMERA);
    }

    @Override
    public void chooseCancel() {
        mChooseImgPopupWindow.dismiss();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_CANCELED){
            return;
        }
        switch (requestCode){
            case CHOOSE_FROM_ALBUM:
                getImagePathFromAlbum(data);
                break;
            case CHOOSE_FROM_CAMERA:
                new CompressTask().execute(mOutputPath, new File(mOutputPath).getName());
                break;
        }

    }

    private void getImagePathFromAlbum(Intent data){
        if (null == data){
            Toast.makeText(mContext, "获取照片路径失败!", Toast.LENGTH_SHORT).show();
            return;
        }
        String filePath = ImageUtils.getImagePathFromUri(mContext, data.getData());
        if (filePath == null ){
            Toast.makeText(mContext,"找不到你选择的图片文件!",Toast.LENGTH_SHORT).show();
            return;
        }
        String timeStamp = new SimpleDateFormat(FILE_NAME_TIME_STEMP).format(new Date());
        String pictureName = timeStamp + PICTURE_FILE_SUFFIX;
        new CompressTask().execute(filePath, pictureName);
    }

    @Override
    public void onDismiss() {
        setBackGroundAlpha(1.0f);
    }

    public void setOnlyShowImg(boolean onlyShowImg){
        mAdapter.setOnlyShow(onlyShowImg);
    }

    public void addImgPath(String path){
        mPathList.add(path);
        mAdapter.notifyItemInserted(mPathList.size());
        mAdapter.notifyDataSetChanged();
    }

    public void removeItemAtIndex(int position){
        if (position < 0 || position >= mPathList.size()){
            return;
        }
        mPathList.remove(position);
        mAdapter.notifyItemRemoved(position);
        mAdapter.notifyDataSetChanged();
    }

    private void setBackGroundAlpha(float alpha) {
        WindowManager.LayoutParams layoutParams = ((Activity)mContext).getWindow().getAttributes();
        layoutParams.alpha = alpha;
        ((Activity)mContext).getWindow().setAttributes(layoutParams);
    }

    /**
     * 压缩文件任务，对图片进行压缩保存到SD卡
     */
    private class CompressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            //第一个参数表示源文件全路径，第二个只是生成的唯一文件名
            try {
                String path = BaseApplication.PICTURE_SAVE_PATH + params[1];
                String savePath = BaseApplication.PICTURE_SAVE_PATH;
                File saveDir = new File(savePath);
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }
                ImageUtils.compressImgFile(params[0], path);
                return Uri.fromFile(new File(path)).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s == null) {
                return;
            }
            addImgPath(s);
            return;
        }
    }
}
