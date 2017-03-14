package com.lucasurbas.listitemview.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import com.lucasurbas.listitemview.util.ViewUtils;

/**
 * Description.
 *
 * @author urbl
 */
public class CircularIconView extends AppCompatImageView {

    private Paint mPaint;

    private RectF mRect;

    public CircularIconView(final Context context) {
        super(context);
        init();
    }

    public CircularIconView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(ViewUtils.getDefaultColor(getContext()));
        setScaleType(ScaleType.CENTER_INSIDE);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect = new RectF(0, 0, w, h);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        if (canvas.getHeight() > 0 && canvas.getWidth() > 0) {
            canvas.drawOval(mRect, mPaint);
        }
        super.onDraw(canvas);
    }

    public void setIcon(final Drawable iconDrawable) {
        setImageDrawable(iconDrawable);
    }
}
