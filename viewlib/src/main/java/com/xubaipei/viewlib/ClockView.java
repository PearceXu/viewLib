package com.xubaipei.viewlib;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by xubaipei on 2016/10/4.
 */
public class ClockView extends View {
    private int DEFAULT_SIZE = 100;
    private int mRadius = 200;

    public ClockView(Context context) {
        this(context,null);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST){
            width = DEFAULT_SIZE;
        }

        if (heightSpecMode == MeasureSpec.AT_MOST){
            height = DEFAULT_SIZE;
        }

        mRadius = Math.min(width,height)/2;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawCircle(canvas,paint);
        drawLine(canvas,paint);
        drawNum(canvas,paint);
        drawPoint(canvas,paint);
        drawHour(canvas,paint);
        drawMin(canvas,paint);
        drawSecond(canvas,paint);
        postInvalidate();
    }
    public void drawCircle(Canvas canvas, Paint paint){
        canvas.drawCircle(mRadius,mRadius,mRadius,paint);
    }


    public void drawPoint(Canvas canvas, Paint paint){
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mRadius,mRadius,mRadius/25,paint);
    }
    // 画小时
    public void drawHour(Canvas canvas, Paint paint){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        Calendar time = Calendar.getInstance();
        int hour =  time.get(Calendar.HOUR_OF_DAY);//获取小时
        time.get(Calendar.MINUTE);//获取分钟
        int min =  time.get(Calendar.MINUTE);//获取分钟
        time.get(Calendar.SECOND);//获取秒
        int second =  time.get(Calendar.SECOND);//获取秒

        long currentSecond = second + min * 60 + hour * 3600;
        float degree =   currentSecond * 30 /3600 ;

        canvas.rotate(degree,mRadius,mRadius);
        canvas.drawLine(mRadius,mRadius,mRadius,2*mRadius/5,paint);
        canvas.rotate(-degree,mRadius,mRadius);
    }
    // 画分
    public void drawMin(Canvas canvas, Paint paint){
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(4);

        Calendar time = Calendar.getInstance();
        time.get(Calendar.MINUTE);//获取分钟
        int min =  time.get(Calendar.MINUTE);//获取小时
        time.get(Calendar.SECOND);//获取分钟
        int second =  time.get(Calendar.SECOND);//获取小时

        long currentSecond = min * 60 + second;
        float degree = currentSecond * 6 / 60;
        canvas.rotate(degree,mRadius,mRadius);
        canvas.drawLine(mRadius,mRadius,mRadius,mRadius/5,paint);
        canvas.rotate(-degree,mRadius,mRadius);

    }
    // 画秒
    public void drawSecond(Canvas canvas, Paint paint){
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);

        Calendar time = Calendar.getInstance();

        time.get(Calendar.SECOND);//获取分钟
        int second =  time.get(Calendar.SECOND);//获取小时
        int degree = second * 6;

        canvas.rotate(degree,mRadius,mRadius);
        canvas.drawLine(mRadius,mRadius,mRadius,3*mRadius/20,paint);
        canvas.rotate(-degree,mRadius,mRadius);
    }

    // 画刻度
    public void drawLine(Canvas canvas, Paint paint){
        for (int i = 0 ;i <60 ;i++) {
            if (i % 5 == 0) {
                canvas.drawLine(mRadius, 0, mRadius, 3*mRadius/20, paint);
            } else {
                canvas.drawLine(mRadius, 0, mRadius, mRadius/20, paint);
            }
            canvas.rotate(6, mRadius, mRadius);
        }
    }
    // 画数字
    public void drawNum(Canvas canvas, Paint paint) {
        paint.setTextSize(mRadius/5);
        Rect rect = new Rect();

        for (int i = 12 ;i >=1 ;i--){
            if (i == 12){
                paint.getTextBounds(String.valueOf(12), 0, String.valueOf(12).length(), rect);
                canvas.drawText(String.valueOf(12), mRadius - rect.width() / 2, 3*mRadius/20 + rect.height(), paint);
                canvas.rotate(30,mRadius,mRadius);
            }else{
                canvas.rotate(- 30*(12 -i) ,mRadius,3*mRadius/20 + rect.height()/2);
                paint.getTextBounds(String.valueOf(12 -i), 0, String.valueOf(12 - i).length(), rect);
                canvas.drawText(String.valueOf(12 - i), mRadius - rect.width() / 2, 3*mRadius/20 + rect.height(), paint);
                canvas.rotate( 30*(12 -i) ,mRadius,3*mRadius/20 + rect.height()/2);
                canvas.rotate(30,mRadius,mRadius);
            }
        }
    }

}