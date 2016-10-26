package com.xubaipei.viewlib.recyclerview.adapter.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by xiuhu on 2016/10/26.
 */
public abstract class UnCertainLayoutManger extends GridLayoutManager {
    public UnCertainLayoutManger(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayoutManger();
    }

    public UnCertainLayoutManger(Context context, int spanCount) {
        super(context, spanCount);
        initLayoutManger();
    }

    public UnCertainLayoutManger(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        initLayoutManger();
    }
    public void initLayoutManger(){
        setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return getIndexCloumn(position);
            }
        });
    }
    public abstract int getIndexCloumn(int position);
}
