package com.lucasurbas.listitemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.lucasurbas.listitemview.util.view.MenuView;

/**
 * Description.
 *
 * @author Lucas Urbas
 */
public class ListItemView extends FrameLayout {

    private static final int DEFAULT_MENU_ITEMS_ROOM = 2;

    private static final String TAG = "ListItemView";

    private TextView mTitle;

    private TextView mDescription;

    private ImageView mIcon;

    // private CircularIconView mCircularIcon;

    private ImageView mAvatar;

    private MenuView mMenuView;

    private int mMenuId = -1;

    private int mMenuItemsRoom;

    /**
     * Interface for implementing a listener to listen
     * when an menu item has been selected.
     */
    public interface OnMenuItemClickListener {

        /**
         * Called when a menu item has been selected.
         *
         * @param item the selected menu item.
         */
        void onActionMenuItemSelected(MenuItem item);
    }

    public ListItemView(@NonNull final Context context) {
        super(context);
        init(null);
    }

    public ListItemView(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(final AttributeSet attrs) {

        inflate(getContext(), R.layout.liv_list_item_layout, this);

        mMenuView = (MenuView) findViewById(R.id.menu_layout);

        if (attrs != null) {
            applyAttrs(attrs);
        }

        setupView();
    }

    private void applyAttrs(final AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ListItemView);

        try {

            mMenuId = a.getResourceId(R.styleable.ListItemView_liv_menu, -1);

            mMenuItemsRoom = a.getInteger(R.styleable.ListItemView_liv_menuItemsRoom,
                    DEFAULT_MENU_ITEMS_ROOM);


        } finally {
            a.recycle();
        }
    }

    private void setupView() {

        inflateMenu(mMenuId);

    }

    private void inflateMenu(final int menuId) {
        mMenuId = menuId;
        mMenuView.reset(menuId, mMenuItemsRoom);
    }
}
