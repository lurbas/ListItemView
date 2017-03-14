package com.lucasurbas.listitemview.util.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.lucasurbas.listitemview.R;
import com.lucasurbas.listitemview.util.ViewUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Description.
 *
 * @author urbl
 */
public class MenuView extends LinearLayout {

    private static final float ACTION_BUTTON_SIZE_DP = 48;

    private final int mActionButtonSize;

    private int mMenuResId = -1;

    private MenuBuilder mMenuBuilder;

    private SupportMenuInflater mMenuInflater;

    private MenuPopupHelper mMenuPopupHelper;

    private MenuBuilder.Callback mMenuCallback;

    private int mActionIconColor;

    private int mOverflowIconColor;

    //all menu items
    private List<MenuItemImpl> mMenuItems;

    //items that are currently presented as actions
    private List<MenuItemImpl> mActionItems = new ArrayList<>();

    private List<MenuItemImpl> mActionShowAlwaysItems = new ArrayList<>();

    private boolean mHasOverflow = false;

    private int mVisibleWidth;


    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActionButtonSize = (int) ViewUtils.dpToPixel(ACTION_BUTTON_SIZE_DP);
        init();
    }

    private void init() {
        mMenuBuilder = new MenuBuilder(getContext());
        mMenuPopupHelper = new MenuPopupHelper(getContext(), mMenuBuilder, this);
        int color = ViewUtils.getDefaultColor(getContext());
        mActionIconColor = color;
        mOverflowIconColor = color;
    }

    public void setActionIconColor(int actionColor) {
        this.mActionIconColor = actionColor;
        refreshColors();
    }

    public void setOverflowColor(int overflowColor) {
        this.mOverflowIconColor = overflowColor;
        refreshColors();
    }

    private void refreshColors() {
        for (int i = 0; i < getChildCount(); i++) {
            ViewUtils.setIconColor(((ImageView) getChildAt(i)), mActionIconColor);
            if (mHasOverflow && i == getChildCount() - 1) {
                ViewUtils.setIconColor(((ImageView) getChildAt(i)), mOverflowIconColor);
            }
        }
    }

    /**
     * Set the callback that will be called when menu
     * items a selected.
     */
    public void setMenuCallback(MenuBuilder.Callback menuCallback) {
        this.mMenuCallback = menuCallback;
    }

    /**
     * Resets the the view to fit into a new
     * available width.
     * <p/>
     * <p>This clears and then re-inflates the menu items
     * , removes all of its associated action views, and re-creates
     * the menu and action items to fit in the new width.</p>
     *
     * @param menuItemsRoom the number of the menu items to show. If
     *                      there is room, menu items that are flagged with
     *                      android:showAsAction="ifRoom" or android:showAsAction="always"
     *                      will show as actions.
     */
    public void reset(int menu, int menuItemsRoom) {
        mMenuResId = menu;
        if (mMenuResId == -1) {
            return;
        }

        mActionShowAlwaysItems = new ArrayList<>();
        mActionItems = new ArrayList<>();
        mMenuItems = new ArrayList<>();
        mMenuBuilder = new MenuBuilder(getContext());
        mMenuPopupHelper = new MenuPopupHelper(getContext(), mMenuBuilder, this);

        //clean view and re-inflate
        removeAllViews();
        getMenuInflater().inflate(mMenuResId, mMenuBuilder);

        mMenuItems = mMenuBuilder.getActionItems();
        mMenuItems.addAll(mMenuBuilder.getNonActionItems());

        Collections.sort(mMenuItems, new Comparator<MenuItemImpl>() {
            @Override
            public int compare(MenuItemImpl lhs, MenuItemImpl rhs) {
                return ((Integer) lhs.getOrder()).compareTo(rhs.getOrder());
            }
        });

        List<MenuItemImpl> localActionItems = filter(mMenuItems, new MenuItemImplPredicate() {
            @Override
            public boolean apply(MenuItemImpl menuItem) {
                return menuItem.getIcon() != null && (menuItem.requiresActionButton()
                        || menuItem.requestsActionButton());
            }
        });

        //determine if to show overflow menu
        boolean addOverflowAtTheEnd = false;
        if (((localActionItems.size() < mMenuItems.size())
                || menuItemsRoom < localActionItems.size())) {
            addOverflowAtTheEnd = true;
            menuItemsRoom--;
        }

        ArrayList<Integer> actionItemsIds = new ArrayList<>();
        if (menuItemsRoom > 0) {
            for (int i = 0; i < localActionItems.size(); i++) {

                final MenuItemImpl menuItem = localActionItems.get(i);
                if (menuItem.getIcon() != null) {

                    ImageView action = createActionView();
                    action.setImageDrawable(menuItem.getIcon());
                    ViewUtils.setIconColor(action, mActionIconColor);
                    addView(action);
                    mActionItems.add(menuItem);
                    actionItemsIds.add(menuItem.getItemId());

                    action.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (mMenuCallback != null) {
                                mMenuCallback.onMenuItemSelected(mMenuBuilder, menuItem);
                            }
                        }
                    });

                    menuItemsRoom--;
                    if (menuItemsRoom == 0) {
                        break;
                    }
                }
            }
        }

        mHasOverflow = addOverflowAtTheEnd;
        if (addOverflowAtTheEnd) {

            ImageView overflowAction = getOverflowActionView();
            overflowAction.setImageResource(R.drawable.ic_more_vert_black_24dp);
            ViewUtils.setIconColor(overflowAction, mOverflowIconColor);
            addView(overflowAction);

            overflowAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMenuPopupHelper.show();
                }
            });

            mMenuBuilder.setCallback(mMenuCallback);
        }

        //remove all menu items that will be shown as icons (the action items) from the overflow menu
        for (int id : actionItemsIds) {
            mMenuBuilder.removeItem(id);
        }
        actionItemsIds = null;
    }

    public int getVisibleWidth() {
        return mVisibleWidth;
    }

    private ImageView createActionView() {
        return (ImageView) LayoutInflater.from(getContext())
                .inflate(R.layout.liv_action_item_layout, this, false);
    }

    private ImageView getOverflowActionView() {
        return (ImageView) LayoutInflater.from(getContext())
                .inflate(R.layout.liv_action_item_overflow_layout, this, false);
    }

    private interface MenuItemImplPredicate {

        boolean apply(MenuItemImpl menuItem);
    }

    private List<MenuItemImpl> filter(List<MenuItemImpl> target, MenuItemImplPredicate predicate) {
        List<MenuItemImpl> result = new ArrayList<>();
        for (MenuItemImpl element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    private MenuInflater getMenuInflater() {
        if (mMenuInflater == null) {
            mMenuInflater = new SupportMenuInflater(getContext());
        }
        return mMenuInflater;
    }
}

