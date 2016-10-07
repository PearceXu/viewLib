package com.xubaipei.viewlib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.WindowDecorActionBar;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/10/6.
 */
public class CircleImageView extends ImageView {
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        BitmapDrawable drawable =(BitmapDrawable) getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if (widthSpecMode == MeasureSpec.AT_MOST){
            width =  bitmap.getWidth();
        }
        if (heightSpecMode == MeasureSpec.AT_MOST){
            height = bitmap.getHeight();
        }
        int min = Math.min(width,height);
        setMeasuredDimension(min,min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);

        Bitmap circleBitmap = getCircleBitmap();

        canvas.drawBitmap(circleBitmap, 0, 0, paint);
    }

    public void drawCircle(Canvas canvas){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
    }

    private void setShader(Bitmap bitmap,Paint paint){
        int bitMinSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float scale =  getWidth() * 1.0f/bitMinSize;
        Matrix matrix = new Matrix();
        matrix.setScale(scale,scale);

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }

    public Bitmap getCircleBitmap( ){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Bitmap outBitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);

        BitmapDrawable drawable =(BitmapDrawable) getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        setShader(bitmap, paint);

        Canvas canvas = new Canvas(outBitmap);
        drawCircle(canvas);
        Rect destRect = new Rect(0,0,getWidth(),getHeight());
        Rect  srcRect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,srcRect,destRect,paint);
        return outBitmap;
    }
}
