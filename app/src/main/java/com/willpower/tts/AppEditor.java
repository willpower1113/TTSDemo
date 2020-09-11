package com.willpower.tts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIDeviceHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIKeyboardHelper;

public class AppEditor extends android.support.v7.widget.AppCompatEditText{
    private boolean hasFocus = false;
    private Paint mPaint;
    private RectF rectF;
    private int paintSize;

    public AppEditor(Context context) {
        super(context);
        init();
    }

    public AppEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSize = QMUIDisplayHelper.dp2px(getContext(),2);
        mPaint.setStrokeWidth(paintSize);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hasFocus) {
            mPaint.setColor(getResources().getColor(R.color.qmui_btn_blue_border));
        } else {
            mPaint.setColor(Color.GRAY);
        }
        if (rectF == null) {
            rectF = new RectF(paintSize, paintSize, getWidth() - paintSize, getHeight() - paintSize);
        }
        canvas.drawRoundRect(rectF, QMUIDisplayHelper.dp2px(getContext(),8), QMUIDisplayHelper.dp2px(getContext(),8), mPaint);
        super.onDraw(canvas);
    }

    public void focusChanged(boolean hasFocus){
        this.hasFocus = hasFocus;
    }
}
