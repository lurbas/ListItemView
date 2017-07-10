package com.lucasurbas.listitemview.util.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.AttributeSet;
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
 * View to represent action items on the right.
 *
 * @author urbl
 */
@SuppressWarnings("RestrictedApi")
public class MenuView extends LinearLayout {

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


    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        int color = ViewUtils.getDefaultColor(getContext());
        mActionIconColor = color;
        mOverflowIconColor = color;
    }

    public void setActionIconColor(final int actionColor) {
        this.mActionIconColor = actionColor;
        refreshColors();
    }

    public void setOverflowColor(final int overflowColor) {
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
    public void setMenuCallback(final MenuBuilder.Callback menuCallback) {
        this.mMenuCallback = menuCallback;
    }


    /**
     * Resets the the view to fit into a new available width.
     * <p>
     * This clears and then re-inflates the menu items, removes all of its associated action
     * views, and re-creates the menu and action items to fit in the new width.
     * </p>
     *
     * @param menuBuilder   builder containing the items to display
     * @param menuItemsRoom the number of the menu items to show. If
     *                      there is room, menu items that are flagged with
     *                      android:showAsAction="ifRoom" or android:showAsAction="always"
     *                      will show as actions.
     */
    public void reset(@NonNull final MenuBuilder menuBuilder, int menuItemsRoom) {

        //clean view and re-inflate
        removeAllViews();

        mMenuBuilder = menuBuilder;
        mMenuPopupHelper = new MenuPopupHelper(getContext(), mMenuBuilder, this);

        mActionShowAlwaysItems = new ArrayList<>();
        mActionItems = new ArrayList<>();
        mMenuItems = new ArrayList<>();

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

    /**
     * Resets the the view to fit into a new available width.
     * <p>
     * This clears and then re-inflates the menu items, removes all of its associated action
     * views, and re-creates the menu and action items to fit in the new width.
     * </p>
     *
     * @param menuItemsRoom the number of the menu items to show. If
     *                      there is room, menu items that are flagged with
     *                      android:showAsAction="ifRoom" or android:showAsAction="always"
     *                      will show as actions.
     */
    public void reset(final int menu, int menuItemsRoom) {
        mMenuResId = menu;

        //clean view and re-inflate
        removeAllViews();

        if (mMenuResId == -1) {
            return;
        }

        final MenuBuilder builder = new MenuBuilder(getContext());
        getMenuInflater().inflate(mMenuResId, builder);
        reset(builder, menuItemsRoom);
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

