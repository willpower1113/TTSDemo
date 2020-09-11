package com.willpower.tts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressView extends View {
    Paint mPaint;
    int left, right, top, bottom;
    int scale;
    int strokeWidth;

    public ProgressView(Context context) {
        super(context);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(getResources().getColor(R.color.qmui_btn_blue_border));
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        left = getWidth() / 9;
        right = getWidth() * 8 / 9;
        top = getHeight() / 9;
        bottom = getHeight() * 8 / 9;
        strokeWidth = getWidth() / 10;
        mPaint.setStrokeWidth(strokeWidth);
        scale = (int) (getHeight() / 3 * Math.random());

        for (int i = 0; i < 5; i++) {
            canvas.drawLine(left + strokeWidth * 2 * i,
                    top + (int) (getHeight() / 3 * Math.random()),
                    left + strokeWidth * 2 * i,
                    bottom - (int) (getHeight() / 3 * Math.random()),
                    mPaint);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE) {
            stop();
        } else {
            start();
        }
    }

    Timer timer;

    public void start() {
        if (timer == null)
            timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 100, 100);

    }

    public void stop() {
        if (timer != null)
            timer.cancel();
        timer = null;
    }
}
