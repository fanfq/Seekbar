package com.elseplus.lib.seekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BidirectionalSeekBar extends View {

    private Paint paintWhite;
    private Paint paintAccent;

    public BidirectionalSeekBar(Context context) {
        this(context, null);
    }

    public BidirectionalSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private float padding = 50;//内边距
    private int textSize = 40;//字体大小
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private float centerX;
    private float centerY;
    private int width;
    private int height;
    private int progress;

    private void init() {
        paintWhite = new Paint();
        paintWhite.setColor(getContext().getResources().getColor(R.color.gnt_gray_l));
        paintWhite.setStyle(Paint.Style.FILL);
        paintWhite.setStrokeCap(Paint.Cap.ROUND);
        paintWhite.setStrokeWidth(5);
        paintWhite.setTextSize(textSize);
        paintWhite.setAntiAlias(true);
        //
        paintAccent = new Paint();
        //
        paintAccent.setColor(getContext().getResources().getColor(R.color.gnt_blue));
        //paintAccent.setColor(getContext().getResources().getColor(R.color.colorAccent));
        paintAccent.setStyle(Paint.Style.FILL);
        paintAccent.setStrokeCap(Paint.Cap.ROUND);
        paintAccent.setStrokeWidth(5);
        paintAccent.setTextSize(textSize);
        paintAccent.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        startX = padding;
        startY = height - padding;
        stopX = width - padding;
        stopY = height - padding;

        width = (int) (width - padding * 2);

        centerX = (startX + stopX) / 2;
        centerY = (startY + stopY) / 2;

        //绘制白线
        canvas.drawLine(startX, startY, stopX, stopY, paintWhite);
        //绘制3个刻度
        //canvas.drawCircle(startX, startY, 10, paintWhite);
        canvas.drawCircle(centerX, centerY, 10, paintAccent);
        //canvas.drawCircle(stopX, stopY, 10, paintWhite);
        //绘制红色的移动点，和指示器
        drawPop(canvas);
    }

    private void drawPop(Canvas canvas) {

        //根据progress计算出currentX
        float currentX = width / 2 * progress / 100 + centerX;

        //绘制移动的红点
        canvas.drawCircle(currentX, centerY, 15, paintAccent);

        //绘制数字指示器
        float redIndicatorY = centerY - 50;
        //canvas.drawCircle(currentX, redIndicatorY, 50, paintAccent);

        //绘制指示器中间的文字
        progress = (int) ((currentX - centerX) / width * 2 * 100);
        float textWidth = paintWhite.measureText(String.valueOf(progress));//获取文本的宽度，但是是一个比较粗略的结果
        Paint.FontMetrics fontMetrics = paintWhite.getFontMetrics();
        float baselineY = redIndicatorY + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(String.valueOf(progress/2), currentX - textWidth / 2, baselineY, paintAccent);

        //绘制中心点和指示器的连线
        canvas.drawLine(centerX, centerY, currentX, centerY, paintAccent);

    }

    private float pointX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pointX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                pointX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        //应该在这里计算出progress，并返回
        if (pointX < startX) {
            pointX = startX;
        } else if (pointX > stopX) {
            pointX = stopX;
        }
        progress = (int) ((pointX - centerX) / width * 2 * 100);
        if (callBack != null) {
            callBack.currentProgress(progress/2);
        }
        postInvalidate();
        return true;
    }

    public interface CallBackListener {
        void currentProgress(int progress);
    }

    private CallBackListener callBack;

    public void setOnProgressCallBackListener(CallBackListener callBack) {
        this.callBack = callBack;
    }

    public void setProgress(int progress) {
        this.progress = progress*2;
    }
}
