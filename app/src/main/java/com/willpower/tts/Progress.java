//package com.willpower.tts;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.AnimatorSet;
//import android.animation.ValueAnimator;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.view.View;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Progress {
//    final int lineSize = 5;
//    List<Float> lines;
//    List<Float> oldLines;
//    Paint mPaint;
//    View target;
//    volatile boolean run;
//
//    public Progress(View target) {
//        this.target = target;
//        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        this.mPaint.setColor(Color.parseColor("#d81e06"));
//        this.mPaint.setStrokeWidth(10);
//        this.mPaint.setStyle(Paint.Style.FILL);
//        lines = new ArrayList<>(lineSize);
//        draw();
//    }
//
//    public void start() {
//        run = true;
//        oldLines = lines;
//        resetLine();
//        ValueAnimator[] animators = new ValueAnimator[lineSize];
//        for (int i = 0; i < lineSize; i++) {
//            int finalI = i;
//            ValueAnimator animator = ValueAnimator.ofFloat(oldLines.get(finalI), lines.get(finalI));
//            animator.addUpdateListener(animation -> {
//                lines.set(finalI, animation.getAnimatedFraction());
//                target.postInvalidate();
//            });
//            animators[finalI] = animator;
//        }
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(1000);
//        set.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                if (run)
////                    start();
//            }
//        });
//        set.playTogether(animators);
//        set.start();
//    }
//
//    public void stop() {
//        run = false;
//    }
//
//    private void draw() {
//        resetLine();
//        target.postInvalidate();
//    }
//
//    public void onDraw(Canvas canvas){
//        for (int i = 0; i < lineSize; i++) {
//            canvas.drawLine(target.getWidth() / lineSize * (i + 1),
//                    (target.getHeight() - lines.get(i)) / 2,
//                    target.getWidth() / lineSize * (i + 1),
//                    (target.getHeight() - lines.get(i)) / 2 + lines.get(i),
//                    mPaint);
//        }
//    }
//
//    private void resetLine() {
//        for (int i = 0; i < lineSize; i++) {
//            lines.add((float) (Math.random() * target.getHeight()));
//        }
//    }
//}
