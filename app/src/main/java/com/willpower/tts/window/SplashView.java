package com.willpower.tts.window;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.RotateAnimation;

import com.willpower.tts.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class SplashView extends View {
    private int radio1 = 0;
    private int radio2 = 0;
    private int radio3 = 0;
    private int radio4 = 0;
    private Paint mPaint;

    private int radio1Scale = 0;
    private int radio2Scale = 0;
    private int radio3Scale = 0;
    private int radio4Scale = 0;

    public SplashView(Context context) {
        super(context);
        init();
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radio1Scale = getWidth() / 5;
        radio2Scale = getWidth() / 5;
        radio3Scale = getWidth() / 5;
        radio4Scale = getWidth() / 3;

        mPaint.setColor(getResources().getColor(R.color.colorSplash4));
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, radio4, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorSplash2));
        canvas.drawCircle(getWidth() * 3 / 4, getWidth() / 3, radio2, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorSplash1));
        canvas.drawCircle(getWidth() / 4, getWidth() / 4, radio1, mPaint);

        mPaint.setColor(getResources().getColor(R.color.colorSplash3));
        canvas.drawCircle(getWidth() / 2, getWidth() * 3 / 4, radio3, mPaint);

    }

    public void start() {
        ValueAnimator animator1 = ValueAnimator.ofInt(0, radio1Scale);
        ValueAnimator animator2 = ValueAnimator.ofInt(0, radio2Scale);
        ValueAnimator animator3 = ValueAnimator.ofInt(0, radio3Scale);
        ValueAnimator animator4 = ValueAnimator.ofInt(0, radio4Scale);

        animator1.addUpdateListener(animation -> {
            radio1 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator2.addUpdateListener(animation -> {
            radio2 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator3.addUpdateListener(animation -> {
            radio3 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator4.addUpdateListener(animation -> {
            radio4 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playSequentially(animator4,animator1, animator2, animator3);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                second();
            }
        });
        set.start();
    }

    private void second(){
        ValueAnimator animator1 = ValueAnimator.ofInt(radio1Scale,0);
        ValueAnimator animator2 = ValueAnimator.ofInt(radio2Scale,0);
        ValueAnimator animator3 = ValueAnimator.ofInt(radio3Scale,0);
        ValueAnimator animator4 = ValueAnimator.ofInt(radio4Scale,0);

        animator1.addUpdateListener(animation -> {
            radio1 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator2.addUpdateListener(animation -> {
            radio2 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator3.addUpdateListener(animation -> {
            radio3 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        animator4.addUpdateListener(animation -> {
            radio4 = (int) animation.getAnimatedValue();
            postInvalidate();
        });
        AnimatorSet set = new AnimatorSet();
        set.setDuration(600);
        set.playSequentially(animator1, animator2, animator3,animator4);
        set.start();
        third();
    }

    private void third(){
        RotateAnimation animation  = new RotateAnimation(0,1080,RELATIVE_TO_SELF,0.5f,RELATIVE_TO_SELF,0.5f);
        animation.setDuration(1800);
        startAnimation(animation);
    }
}
