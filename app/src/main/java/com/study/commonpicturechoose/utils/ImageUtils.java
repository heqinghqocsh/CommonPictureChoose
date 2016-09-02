package com.study.commonpicturechoose.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 何清 on 2016/3/17.
 *
 * @description
 */
public final class ImageUtils {
    private static final int DEFAULT_IMAGE_WIDTH = 480;
    private static final int DEFAULT_IMAGE_HEIGHT = 800;

    /**
     * 根据Uri取得图片的路径
     * @param context
     * @param uri
     * @return
     */
    public static String getImagePathFromUri(Context context,Uri uri){
        String path = null;
        if (uri.toString().startsWith("file:///")){
            return uri.toString().replace("file:///","");
        }else if(uri.toString().startsWith("content://")){
            String[] field = {MediaStore.Images.Media.DATA};
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri,field,null,null,null);
            cursor.moveToFirst();
            int column_1 = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            path = cursor.getString(column_1);
            if(!new File(path).exists()){
                return null;
            }
            return path;
        }
        return null;
    }

    public static Bitmap getBitmapFromPath(String filePath, int reqWidth, int reqHeight) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        if (!new File(filePath).exists()) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap;
        reqWidth = reqWidth > DEFAULT_IMAGE_WIDTH || reqWidth <= 0 ? DEFAULT_IMAGE_WIDTH : reqWidth;
        reqHeight = reqHeight > DEFAULT_IMAGE_HEIGHT || reqHeight <= 0 ? DEFAULT_IMAGE_HEIGHT : reqHeight;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(filePath, options);
        Log.i("testsize", "宽：" + reqWidth + "高：" + reqHeight + "比例：" + inSampleSize
                + "大小M：" + bitmap.getRowBytes() / 1048576.0);
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 图片的原始宽高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 压缩图片文件
     * @param srcFilePath  源文件地址
     * @param destPath 目标文件地址
     * @throws IOException
     */
    public static void compressImgFile(String srcFilePath, String destPath)
            throws IOException{
        if (srcFilePath == null || destPath == null){
            throw new IOException("parameter in and destPath is null");
        }
        if (!new File(srcFilePath).exists()){
            throw new IOException("File not fond");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(srcFilePath, options);
        int inSampleSize = calculateInSampleSize(options, DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(srcFilePath, options);
        if (bitmap == null){
            throw new IOException("decode bitmap failed");
        }

        File file = new File(destPath);
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
            outputStream.flush();
        } finally {
            if (outputStream != null){
                outputStream.close();
            }
            bitmap.recycle();
        }
    }

    public static String img2Base64(String filePath) {
        final int WIDTH = 800;
        final int HEIGHT = 800;
        final int QUANLITY = 80;

        if(filePath == null){
            return null;
        }
        Bitmap bitmap = getBitmapFromPath(filePath,WIDTH,HEIGHT);
        Log.i("testsize", "原始大小M：" + new File(filePath).length() / 1048576.0);
        if(bitmap == null) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUANLITY, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte [] imgBytes = out.toByteArray();
        Log.i("testsize","压缩后大小M："+imgBytes.length / 1048576.0);
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    public static String img2Base64(InputStream inputStream){
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte [] imgBytes = out.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }

}
