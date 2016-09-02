package com.study.commonpicturechoose.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.study.commonpicturechoose.R;
import com.study.commonpicturechoose.base.BaseApplication;

import java.util.List;

/**
 * Created on 2016/9/1.
 *
 * @description
 */
public class CommonPictureChooseViewAdapter
        extends RecyclerView.Adapter<CommonPictureChooseViewAdapter.ViewHolder>{
    private final int PICTURE_WIDTH = 200;
    private final int PICTURE_HEIGHT = 200;

    private DisplayImageOptions imageOptions;

    private Context mContext;
    private List<String> mPathList;
    private boolean mOnlyShow = true;//是否仅仅显示图片，不能选择

    public CommonPictureChooseViewAdapter(Context context,List<String> pathList){
        mContext = context;
        mPathList = pathList;
        init();
    }

    private void init(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = PICTURE_HEIGHT;
        options.outWidth = PICTURE_WIDTH;
        imageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.on_loading_picture)
                .showImageOnFail(R.mipmap.load_filed)
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .decodingOptions(options).build();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(mContext);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(10,10,10,10);
        ViewHolder viewHolder = new ViewHolder(imageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mOnlyShow){
            BaseApplication.mImageLoader.displayImage(mPathList.get(position)
                    ,holder.imageView,imageOptions);
        }else{
            if (position == mPathList.size()){
                holder.imageView.setImageResource(R.mipmap.select_picture);
            }else{
                BaseApplication.mImageLoader.displayImage(mPathList.get(position)
                        ,holder.imageView,imageOptions);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mOnlyShow){
            count = mPathList.size();
        }else{
            count = mPathList.size()+1;
        }
        return count;
    }

    public void setOnlyShow(boolean onlyShow){
        mOnlyShow = onlyShow;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ViewHolder(ImageView itemView) {
            super(itemView);
            imageView = itemView;
        }
    }


}
