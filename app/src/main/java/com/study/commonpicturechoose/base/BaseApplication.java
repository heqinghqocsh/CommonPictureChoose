package com.study.commonpicturechoose.base;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created on 2016/9/1.
 *
 * @description
 */
public class BaseApplication extends Application{

    public static ImageLoader mImageLoader;
    public static String PICTURE_SAVE_PATH;

    @Override
    public void onCreate() {
        super.onCreate();
        PICTURE_SAVE_PATH = getApplicationContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES)+File.separator;
        initImageLoader();
    }

    private void initImageLoader(){
        File cacheDir = StorageUtils.getCacheDirectory(this);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .threadPoolSize(10)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .writeDebugLogs()
                .build();

        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config);
    }
}
