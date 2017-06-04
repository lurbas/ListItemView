package com.lucasurbas.listitemview.util;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.lucasurbas.listitemview.R;

/**
 * Static methods to help with views and dimensions.
 *
 * @author Lucas Urbas
 */
public class ViewUtils {

    ViewUtils() {
    }

    /**
     * Returns whether or not the view has been laid out
     **/
    public static boolean isLaidOut(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return view.isLaidOut();
        }

        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    /**
     * Executes the given {@link Runnable} when the view is laid out
     **/
    public static void onLaidOut(final View view, final Runnable runnable) {
        if (isLaidOut(view)) {
            runnable.run();
            return;
        }

        final ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ViewTreeObserver trueObserver;

                if (observer.isAlive()) {
                    trueObserver = observer;
                } else {
                    trueObserver = view.getViewTreeObserver();
                }

                removeGlobalLayoutObserver(trueObserver, this);
                runnable.run();
            }
        });
    }


    /**
     * Get the secondary text color of the theme
     */
    @ColorInt
    public static int getDefaultColor(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{android.R.attr.textColorSecondary});
        try {
            return a.getColor(0, ContextCompat.getColor(context, R.color.liv_gray_active_icon));
        } finally {
            a.recycle();
        }
    }

    public static void setIconColor(ImageView iconHolder, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(iconHolder.getDrawable());
        DrawableCompat.setTint(wrappedDrawable, color);
        iconHolder.setImageDrawable(wrappedDrawable);
        iconHolder.invalidate();
    }

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (dp * metrics.density);
    }

    public static float spToPixel(float sp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    public static void removeGlobalLayoutObserver(final ViewTreeObserver observer,
            ViewTreeObserver.OnGlobalLayoutListener layoutListener) {
        if (Build.VERSION.SDK_INT < 16) {
            observer.removeGlobalOnLayoutListener(layoutListener);
        } else {
            observer.removeOnGlobalLayoutListener(layoutListener);
        }
    }
}
