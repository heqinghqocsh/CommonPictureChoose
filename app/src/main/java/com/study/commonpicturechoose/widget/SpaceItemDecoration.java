package com.study.commonpicturechoose.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.study.commonpicturechoose.utils.Tools;

/**
 * Created on 2016/9/2.
 *
 * @description
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(Context context,int dp){
        space = Tools.dip2px(context,dp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = parent.getAdapter().getItemCount() - 1;
        int position = parent.getChildAdapterPosition(view);
//        outRect.left = 0;
//        outRect.top = 0;
//        outRect.bottom = 0;
        if (position != itemCount){
            outRect.right = space;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.drawColor(0xff0000);
    }
}
