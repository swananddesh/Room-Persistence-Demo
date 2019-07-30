package com.stalwart.notes.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

public class LinedEditText extends AppCompatEditText {

    private Rect rect;
    private Paint paint;

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(0xFFFFD966);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int height = ((View) this.getParent()).getHeight(); // Height of the screen
        int lineHeight = getLineHeight();
        int numberOfLinesToDraw = height / lineHeight;

        Rect r = rect;
        Paint p = paint;

        int baseLine = getLineBounds(0, r);

        for (int index = 0; index < numberOfLinesToDraw; index++) {
            canvas.drawLine(r.left, baseLine + 1, r.right, baseLine + 1, p);

            baseLine += lineHeight;
        }
        super.onDraw(canvas);
    }
}
