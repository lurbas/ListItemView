package com.lucasurbas.listitemview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Description.
 *
 * @author Lucas Urbas
 */
public class ListItemView extends FrameLayout {

    public ListItemView(@NonNull final Context context) {
        super(context);
    }

    public ListItemView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }
}
