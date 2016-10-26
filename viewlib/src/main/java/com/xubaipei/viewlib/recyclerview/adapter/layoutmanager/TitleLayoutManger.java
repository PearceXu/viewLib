package com.xubaipei.viewlib.recyclerview.adapter.layoutmanager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

/**
 * Created by xiuhu on 2016/10/20.
 */
public class TitleLayoutManger extends RecyclerView.LayoutManager {
    public TitleLayoutManger() {
    }
    //保存所有的Item的上下左右的偏移量信息
    private SparseArray<Rect> mItemRect = new SparseArray<>();
    //记录Item是否出现过屏幕且还没有回收。true表示出现过屏幕上，并且还没被回收
    private SparseBooleanArray mHasAttachedItems = new SparseBooleanArray();
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //如果没有item，直接返回
        if (getItemCount() <= 0) return;
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        int increment = 0;
        for (int i = 0 ;i<getItemCount();i++) {
            View childView = recycler.getViewForPosition(i);
            addView(childView);

            measureChild(childView,0,0);
            int width = getDecoratedMeasuredWidth(childView);
            int height = getDecoratedMeasuredHeight(childView);
            Rect frame = mItemRect.get(i);
            if (frame == null) {
                frame = new Rect();
            }
            frame.set(0, increment, width, increment + height);
            // 将当前的Item的Rect边界数据保存
            mItemRect.put(i, frame);
            // 由于已经调用了detachAndScrapAttachedViews，因此需要将当前的Item设置为未出现过
            mHasAttachedItems.put(i, false);
//            layoutDecorated(childView,0,increment,width,height +  increment);
            increment += height;
        }
        mTotalHeight = Math.max(increment, getVerticalSpace());
        recycleAndFillItems(recycler,state);
    }
    /**
     * 回收不需要的Item，并且将需要显示的Item从缓存中取出
     */
    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) { // 跳过preLayout，preLayout主要用于支持动画
            return;
        }
        Rect displayFrame = new Rect(0, verticalScrollOffset, getHorizontalSpace(), verticalScrollOffset + getVerticalSpace());

        /**
         * 将滑出屏幕的Items回收到Recycle缓存中
         */
        Rect childFrame = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childFrame.left = getDecoratedLeft(child);
            childFrame.top = getDecoratedTop(child);
            childFrame.right = getDecoratedRight(child);
            childFrame.bottom = getDecoratedBottom(child);
            //如果Item没有在显示区域，就说明需要回收
            if (!Rect.intersects(displayFrame, childFrame)) {
                //回收掉滑出屏幕的View
                removeAndRecycleView(child, recycler);
            }
        }

        //重新显示需要出现在屏幕的子View
        for (int i = 0; i < getItemCount(); i++) {

            if (Rect.intersects(displayFrame, mItemRect.get(i))) {

                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);

                Rect frame = mItemRect.get(i);
                //将这个item布局出来
                layoutDecorated(scrap,
                        frame.left,
                        frame.top - verticalScrollOffset,
                        frame.right,
                        frame.bottom - verticalScrollOffset);
            }
        }
    }



    int mTotalHeight = 0;
    int verticalScrollOffset= 0 ;
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //先detach掉所有的子View
        detachAndScrapAttachedViews(recycler);
        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > mTotalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = mTotalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 平移容器内的item
        offsetChildrenVertical(-travel);
        recycleAndFillItems(recycler, state);
        return travel;
    }

    public int getVerticalSpace(){
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }
    public int getHorizontalSpace(){
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
    @Override
    public boolean canScrollVertically() {
        return true;
    }
}
