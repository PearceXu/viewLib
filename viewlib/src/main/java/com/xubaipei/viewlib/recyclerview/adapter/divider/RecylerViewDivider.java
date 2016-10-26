package com.xubaipei.viewlib.recyclerview.adapter.divider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by user on 2016/10/19.
 */
public class RecylerViewDivider extends RecyclerView.ItemDecoration {
    Context mContext;
    public RecylerViewDivider(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}
