package com.lucasurbas.listitemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lucasurbas.listitemview.util.ViewUtils;
import com.lucasurbas.listitemview.util.view.MenuView;

/**
 * Description.
 *
 * @author Lucas Urbas
 */
public class ListItemView extends FrameLayout {

    private static final int DEFAULT_MENU_ITEMS_ROOM = 2;

    private static final int SINGLE_LINE_ITEM_HEIGHT_DP = 48;

    private static final int SINGLE_LINE_AVATAR_ITEM_HEIGHT_DP = 56;

    private static final int TWO_LINE_ITEM_HEIGHT_DP = 72;

    private static final String TAG = "ListItemView";

    private LinearLayout mItemLayout;

    private TextView mTitleView;

    private TextView mSubtitleView;

    private ImageView mIconView;

    // private CircularIconView mCircularIconView;

    private ImageView mAvatarView;

    private MenuView mMenuView;

    // VARIABLES

    private int mMenuId = -1;

    private int mMenuItemsRoom;

    private String mTitle;

    private String mSubtitle;

    private boolean mIsMultiline;

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

        mItemLayout = (LinearLayout) findViewById(R.id.item_layout);
        mMenuView = (MenuView) findViewById(R.id.menu_view);
        mTitleView = (TextView) findViewById(R.id.title_view);
        mSubtitleView = (TextView) findViewById(R.id.subtitle_view);

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
            mTitle = a.getString(R.styleable.ListItemView_liv_title);
            mSubtitle = a.getString(R.styleable.ListItemView_liv_subtitle);
            mIsMultiline = a.getBoolean(R.styleable.ListItemView_liv_multiline, false);


        } finally {
            a.recycle();
        }
    }

    private void setupView() {

        setMultiline(mIsMultiline);

        setupTextView(mTitleView, (int) ViewUtils.spToPixel(24), 1);
        setupTextView(mSubtitleView, (int) ViewUtils.spToPixel(20), 1);

        setTitle(mTitle);
        setSubtitle(mSubtitle);
        inflateMenu(mMenuId);

    }

    private void setupTextView(final TextView textView, final int leading, final int step) {
        // This is to make the behavior more deterministic: remove extra top/bottom padding
        textView.setIncludeFontPadding(false);

        // Get font metrics and calculate required inter-line extra
        Paint.FontMetricsInt metrics = textView.getPaint().getFontMetricsInt();
        final int extra = leading - metrics.descent + metrics.ascent;
        textView.setLineSpacing(extra, 1);

        // Determine minimum required top extra so that the view lands on the grid
        final int alignTopExtra = (step + metrics.ascent % step) % step;
        // Determine minimum required bottom extra so that view bounds are aligned with the grid
        final int alignBottomExtra = (step - metrics.descent % step) % step;

        textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop() + alignTopExtra,
                textView.getPaddingRight(), textView.getPaddingBottom() + alignBottomExtra);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if (mIsMultiline) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int finalHeight = 0;
            if (mTitleView.getVisibility() != GONE && mSubtitleView.getVisibility() != GONE) {
                finalHeight = (int) ViewUtils.dpToPixel(TWO_LINE_ITEM_HEIGHT_DP);
            } else if (mAvatarView != null && mAvatarView.getVisibility() != GONE) {
                finalHeight = (int) ViewUtils.dpToPixel(SINGLE_LINE_AVATAR_ITEM_HEIGHT_DP);
            } else {
                finalHeight = (int) ViewUtils.dpToPixel(SINGLE_LINE_ITEM_HEIGHT_DP);
            }
            super.onMeasure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
        }
    }

    /**
     * Set a title that will appear in the first line.
     *
     * @param title a string title
     */
    public void setTitle(final String title) {
        mTitle = title;
        mTitleView.setText(title);
        mTitleView.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
    }

    /**
     * Set a subtitle that will appear in the second line.
     *
     * @param subtitle a string subtitle
     */
    public void setSubtitle(final String subtitle) {
        mSubtitle = subtitle;
        mSubtitleView.setText(subtitle);
        mSubtitleView.setVisibility(TextUtils.isEmpty(subtitle) ? GONE : VISIBLE);
    }

    /**
     * Inflates the menu items from
     * an xml resource.
     *
     * @param menuId a menu xml resource identifier
     */
    public void inflateMenu(final int menuId) {
        mMenuId = menuId;
        mMenuView.reset(menuId, mMenuItemsRoom);
    }

    /**
     * Allows multiline for title and subtitle.
     *
     * @param isMultiline a multiline flag
     */
    public void setMultiline(final boolean isMultiline) {
        mIsMultiline = isMultiline;
        if (isMultiline) {
            int padding = (int) ViewUtils.dpToPixel(4);
            mItemLayout.setGravity(Gravity.TOP);
            mTitleView.setMaxLines(Integer.MAX_VALUE);
            mSubtitleView.setMaxLines(Integer.MAX_VALUE);
            mItemLayout.setPadding(0, padding, 0, padding);
        } else {
            mItemLayout.setGravity(Gravity.CENTER_VERTICAL);
            mTitleView.setMaxLines(1);
            mSubtitleView.setMaxLines(1);
            mItemLayout.setPadding(0, 0, 0, 0);
        }
    }
}
