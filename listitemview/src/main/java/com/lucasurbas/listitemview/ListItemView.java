package com.lucasurbas.listitemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lucasurbas.listitemview.util.ViewUtils;
import com.lucasurbas.listitemview.util.view.CircularIconView;
import com.lucasurbas.listitemview.util.view.MenuView;


/**
 * Implementation of List Item from Material Design guidelines.
 *
 * @author Lucas Urbas
 */
public class ListItemView extends FrameLayout {

    // CONSTANTS

    public static final int NULL = -1;

    private static final String TAG = "ListItemView";

    private static final int DEFAULT_MENU_ITEMS_ROOM = 2;

    private static final int SINGLE_LINE_ITEM_HEIGHT_DP = 48;

    private static final int SINGLE_LINE_AVATAR_ITEM_HEIGHT_DP = 56;

    private static final int TWO_LINE_ITEM_HEIGHT_DP = 72;

    private static final int AVATAR_WIDTH_DP = 40;

    private static final int ACTION_ICON_PADDING_DP = 12;

    private static final int ICON_WIDTH_DP = 24;

    private static final int TITLE_LEADING_SP = 24;

    private static final int SUBTITLE_LEADING_SP = 20;

    // CHILD VIEWS

    private LinearLayout mItemLayout;

    private LinearLayout mTextsLayout;

    private TextView mTitleView;

    private TextView mSubtitleView;

    private ImageView mIconView;

    private CircularIconView mCircularIconView;

    private ImageView mAvatarView;

    private MenuView mMenuView;

    // VARIABLES

    @MenuRes
    private int mMenuId;

    private int mMenuItemsRoom;

    @ColorInt
    private int mMenuActionColor;

    @ColorInt
    private int mMenuOverflowColor;

    private OnMenuItemClickListener mActionMenuItemListener;

    private String mTitle;

    private String mSubtitle;

    private boolean mIsMultiline;

    private int mPaddingEnd;

    private int mPaddingStart;

    private int mPaddingVertical;

    private int mKeyline;

    private boolean mForceKeyline;

    private int mAvatarWidth;

    private int mIconWidth;

    private boolean mIsAvatar;

    @DrawableRes
    private int mIconResId;

    private Drawable mIconDrawable;

    @ColorInt
    private int mIconColor;

    private boolean mIsCircularIcon;

    @ColorInt
    private int mCircularIconColor;

    @ColorInt
    private int mDefaultColor;

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
        mIconView = (ImageView) findViewById(R.id.icon_view);
        mTextsLayout = (LinearLayout) findViewById(R.id.texts_layout);
        mCircularIconView = (CircularIconView) findViewById(R.id.circular_icon_view);
        mAvatarView = (ImageView) findViewById(R.id.avatar_view);

        mDefaultColor = ViewUtils.getDefaultColor(getContext());
        mPaddingEnd = getResources().getDimensionPixelSize(R.dimen.liv_padding_end);
        mPaddingStart = getResources().getDimensionPixelSize(R.dimen.liv_padding_start);
        mKeyline = getResources().getDimensionPixelSize(R.dimen.liv_keyline);

        mAvatarWidth = (int) ViewUtils.dpToPixel(AVATAR_WIDTH_DP);
        mIconWidth = (int) ViewUtils.dpToPixel(ICON_WIDTH_DP);

        if (attrs != null) {
            applyAttrs(attrs);
        }

        setupView();
    }

    private void applyAttrs(final AttributeSet attrs) {

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ListItemView);

        try {

            mMenuId = a.getResourceId(R.styleable.ListItemView_liv_menu, NULL);
            mMenuItemsRoom = a.getInteger(R.styleable.ListItemView_liv_menuItemsRoom,
                    DEFAULT_MENU_ITEMS_ROOM);
            mMenuActionColor = a.getColor(R.styleable.ListItemView_liv_menuActionColor,
                    Color.TRANSPARENT);
            mMenuOverflowColor = a.getColor(R.styleable.ListItemView_liv_menuOverflowColor,
                    Color.TRANSPARENT);

            mTitle = a.getString(R.styleable.ListItemView_liv_title);
            mSubtitle = a.getString(R.styleable.ListItemView_liv_subtitle);
            mIsMultiline = a.getBoolean(R.styleable.ListItemView_liv_multiline, false);

            mPaddingEnd = a.getDimensionPixelSize(R.styleable.ListItemView_liv_paddingEnd,
                    mPaddingEnd);
            mPaddingStart = a.getDimensionPixelSize(R.styleable.ListItemView_liv_paddingStart,
                    mPaddingStart);
            mKeyline = a.getDimensionPixelSize(R.styleable.ListItemView_liv_keyline, mKeyline);
            mForceKeyline = a.getBoolean(R.styleable.ListItemView_liv_forceKeyline, false);

            mIconDrawable = a.getDrawable(R.styleable.ListItemView_liv_icon);
            mIconColor = a.getColor(R.styleable.ListItemView_liv_iconColor, Color.TRANSPARENT);
            mIsCircularIcon = a.getBoolean(R.styleable.ListItemView_liv_circularIcon, false);
            mCircularIconColor = a.getColor(R.styleable.ListItemView_liv_circularIconColor,
                    Color.TRANSPARENT);

        } finally {
            a.recycle();
        }
    }

    private void setupView() {

        assertPadding();

        setupTextView(mTitleView, (int) ViewUtils.spToPixel(TITLE_LEADING_SP), 1);
        setupTextView(mSubtitleView, (int) ViewUtils.spToPixel(SUBTITLE_LEADING_SP), 1);

        setCircularIconColor(mCircularIconColor);
        setIconDrawable(mIconDrawable);
        setMultiline(mIsMultiline);
        setTitle(mTitle);
        setSubtitle(mSubtitle);
        setMenuActionColor(mMenuActionColor);
        setMenuOverflowColor(mMenuOverflowColor);
        inflateMenu(mMenuId);
    }

    private void assertPadding() {
        if (hasAvatar() || hasCircularIcon()) {
            if (mKeyline - mAvatarWidth < mPaddingStart) {
                throw new IllegalArgumentException("keyline value is to small");
            }
        } else if (hasIcon()) {
            if (mKeyline - mIconWidth < mPaddingStart) {
                throw new IllegalArgumentException("keyline value is to small");
            }
        } else {
            if (mKeyline < mPaddingStart) {
                throw new IllegalArgumentException("keyline value is to small");
            }
        }
    }

    private boolean useKeyline() {
        return mForceKeyline || hasIcon() || hasCircularIcon() || hasAvatar();
    }

    private void adjustPadding() {
        int paddingEnd = mPaddingEnd - (hasActionMenu() ? (int) ViewUtils.dpToPixel(
                ACTION_ICON_PADDING_DP) : 0);
        mItemLayout.setPaddingRelative(useKeyline() ? mKeyline : mPaddingStart, mPaddingVertical,
                paddingEnd, mPaddingVertical);
        ((MarginLayoutParams) mIconView.getLayoutParams()).setMarginStart(mPaddingStart);
        ((MarginLayoutParams) mCircularIconView.getLayoutParams()).setMarginStart(mPaddingStart);
        ((MarginLayoutParams) mAvatarView.getLayoutParams()).setMarginStart(mPaddingStart);
        MarginLayoutParams textsLayoutParams = (MarginLayoutParams) mTextsLayout.getLayoutParams();
        textsLayoutParams.setMarginEnd(hasActionMenu() ? (int) ViewUtils.dpToPixel(4) : 0);
        textsLayoutParams.resolveLayoutDirection(textsLayoutParams.getLayoutDirection());
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

    private void setIconDrawable(Drawable iconDrawable) {
        mIconDrawable = iconDrawable;
        if (!mIsCircularIcon) {
            mIconView.setImageDrawable(mIconDrawable);
            mIconView.setVisibility(mIconDrawable == null ? GONE : VISIBLE);
            mCircularIconView.setVisibility(GONE);
            mAvatarView.setVisibility(GONE);
        } else {
            mCircularIconView.setIconDrawable(mIconDrawable);
            mCircularIconView.setVisibility(mIconDrawable == null ? GONE : VISIBLE);
            mIconView.setVisibility(GONE);
            mAvatarView.setVisibility(GONE);
        }
        setIconColor(mIconColor);
        adjustPadding();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        if (mIsMultiline) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int finalHeight = 0;
            if (mTitleView.getVisibility() != GONE && mSubtitleView.getVisibility() != GONE) {
                finalHeight = (int) ViewUtils.dpToPixel(TWO_LINE_ITEM_HEIGHT_DP);
            } else if ((mAvatarView != null && mAvatarView.getVisibility() != GONE) || (
                    mCircularIconView.getVisibility() != GONE)) {
                finalHeight = (int) ViewUtils.dpToPixel(SINGLE_LINE_AVATAR_ITEM_HEIGHT_DP);
            } else {
                finalHeight = (int) ViewUtils.dpToPixel(SINGLE_LINE_ITEM_HEIGHT_DP);
            }
            super.onMeasure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);

        savedState.menuId = this.mMenuId;
        savedState.menuItemsRoom = this.mMenuItemsRoom;
        savedState.menuActionColor = this.mMenuActionColor;
        savedState.menuOverflowColor = this.mMenuOverflowColor;
        savedState.title = this.mTitle;
        savedState.subtitle = this.mSubtitle;
        savedState.isMultiline = this.mIsMultiline;
        savedState.forceKeyline = this.mForceKeyline;
        savedState.iconColor = this.mIconColor;
        savedState.isCircularIcon = this.mIsCircularIcon;
        savedState.circularIconColor = this.mCircularIconColor;
        savedState.iconResId = this.mIconResId;
        savedState.isAvatar = this.mIsAvatar;
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        this.mMenuId = savedState.menuId;
        this.mMenuItemsRoom = savedState.menuItemsRoom;
        this.mMenuActionColor = savedState.menuActionColor;
        this.mMenuOverflowColor = savedState.menuOverflowColor;
        this.mTitle = savedState.title;
        this.mSubtitle = savedState.subtitle;
        this.mIsMultiline = savedState.isMultiline;
        this.mForceKeyline = savedState.forceKeyline;
        this.mIconColor = savedState.iconColor;
        this.mIsCircularIcon = savedState.isCircularIcon;
        this.mCircularIconColor = savedState.circularIconColor;
        this.mIconResId = savedState.iconResId;
        if (this.mIconResId != 0) {
            setIconResId(mIconResId);
        }
        this.mIsAvatar = savedState.isAvatar;
        setupView();
    }

    /**
     * Set a title that will appear in the first line.
     *
     * @param title a string title
     */
    public void setTitle(final String title) {
        mTitle = title;
        mTitleView.setText(mTitle);
        mTitleView.setVisibility(TextUtils.isEmpty(mTitle) ? GONE : VISIBLE);
    }

    /**
     * Set a title that will appear in the first line.
     *
     * @param titleResId a string title res id
     */
    public void setTitle(@StringRes final int titleResId) {
        mTitle = getContext().getString(titleResId);
        mTitleView.setText(mTitle);
        mTitleView.setVisibility(TextUtils.isEmpty(mTitle) ? GONE : VISIBLE);
    }

    /**
     * Set a subtitle that will appear in the second line.
     *
     * @param subtitle a string subtitle
     */
    public void setSubtitle(final String subtitle) {
        mSubtitle = subtitle;
        mSubtitleView.setText(mSubtitle);
        mSubtitleView.setVisibility(TextUtils.isEmpty(mSubtitle) ? GONE : VISIBLE);
    }

    /**
     * Set a subtitle that will appear in the second line.
     *
     * @param subtitleResId a string subtitle res id
     */
    public void setSubtitle(@StringRes final int subtitleResId) {
        mSubtitle = getContext().getString(subtitleResId);
        mSubtitleView.setText(mSubtitle);
        mSubtitleView.setVisibility(TextUtils.isEmpty(mSubtitle) ? GONE : VISIBLE);
    }

    /**
     * Inflates the menu items from
     * an xml resource.
     *
     * @param menuId a menu xml resource identifier
     */
    public void inflateMenu(@MenuRes final int menuId) {
        mMenuId = menuId;
        mMenuView.setMenuCallback(new MenuBuilder.Callback() {

            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                if (mActionMenuItemListener != null) {
                    mActionMenuItemListener.onActionMenuItemSelected(item);
                }
                return true;
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }

        });
        mMenuView.reset(menuId, mMenuItemsRoom);
        adjustPadding();
    }

    /**
     * Set the available space for menu items
     *
     * @param menuItemsRoom a number of menu actions visible
     */
    public void setMenuItemsRoom(final int menuItemsRoom) {
        this.mMenuItemsRoom = menuItemsRoom;
        mMenuView.reset(mMenuId, mMenuItemsRoom);
        adjustPadding();
    }

    /**
     * Allows multiline for title and subtitle.
     *
     * @param isMultiline a multiline flag
     */
    public void setMultiline(final boolean isMultiline) {
        mIsMultiline = isMultiline;
        if (isMultiline) {
            mPaddingVertical = (int) ViewUtils.dpToPixel(4);
            mItemLayout.setGravity(Gravity.TOP);
            ((LayoutParams) mIconView.getLayoutParams()).gravity = Gravity.TOP;
            ((LayoutParams) mCircularIconView.getLayoutParams()).gravity = Gravity.TOP;
            ((LayoutParams) mAvatarView.getLayoutParams()).gravity = Gravity.TOP;
            mTitleView.setMaxLines(Integer.MAX_VALUE);
            mSubtitleView.setMaxLines(Integer.MAX_VALUE);
        } else {
            mPaddingVertical = 0;
            mItemLayout.setGravity(Gravity.CENTER_VERTICAL);
            ((LayoutParams) mIconView.getLayoutParams()).gravity = Gravity.CENTER_VERTICAL
                    | Gravity.START;
            ((LayoutParams) mCircularIconView.getLayoutParams()).gravity = Gravity.CENTER_VERTICAL
                    | Gravity.START;
            ((LayoutParams) mAvatarView.getLayoutParams()).gravity = Gravity.CENTER_VERTICAL
                    | Gravity.START;
            mTitleView.setMaxLines(1);
            mSubtitleView.setMaxLines(1);
        }
        adjustPadding();
    }

    /**
     * Force a keyline offset of title and subtitle.
     *
     * @param forceKeyline a keyline flag
     */
    public void forceKeyline(final boolean forceKeyline) {
        mForceKeyline = forceKeyline;
        adjustPadding();
    }

    /**
     * Set an icon on left side.
     *
     * @param iconResId a icon resource id
     */
    public void setIconResId(@DrawableRes final int iconResId) {
        mIconResId = iconResId;
        setIconDrawable(
                mIconResId != NULL ? ContextCompat.getDrawable(getContext(), mIconResId) : null);
    }

    /**
     * Set a color of icon on left side.
     *
     * @param iconColor a icon color
     */
    public void setIconColor(@ColorInt final int iconColor) {
        mIconColor = iconColor;
        if (!mIsCircularIcon && mIconView.getDrawable() != null) {
            ViewUtils.setIconColor(mIconView,
                    Color.alpha(mIconColor) == 0 ? mDefaultColor : mIconColor);

        } else if (mCircularIconView.getIconDrawable() != null) {
            mCircularIconView.setMask(Color.alpha(mIconColor) == 0);
            Drawable wrappedDrawable = DrawableCompat.wrap(mCircularIconView.getIconDrawable());
            DrawableCompat.setTint(wrappedDrawable,
                    Color.alpha(mIconColor) == 0 ? Color.WHITE : mIconColor);
            mCircularIconView.setIconDrawable(wrappedDrawable);
        }
    }

    /**
     * Set an icon on left in circle.
     *
     * @param isCircularIcon a circular icon flag
     */
    public void setCircularIcon(final boolean isCircularIcon) {
        mIsCircularIcon = isCircularIcon;
        setIconDrawable(mIconDrawable);
    }

    /**
     * Set a color of circular icon on left side.
     *
     * @param circularIconColor a icon color
     */
    public void setCircularIconColor(@ColorInt final int circularIconColor) {
        mCircularIconColor = Color.alpha(circularIconColor) == 0 ? mDefaultColor
                : circularIconColor;
        mCircularIconView.setCircleColor(mCircularIconColor);
    }

    /**
     * Set a color of an action icon in menu.
     *
     * @param menuActionColor a icon color
     */
    public void setMenuActionColor(@ColorInt final int menuActionColor) {
        mMenuActionColor = Color.alpha(menuActionColor) == 0 ? mDefaultColor : menuActionColor;
        mMenuView.setActionIconColor(mMenuActionColor);
    }

    /**
     * Set a color of an overflow icon in menu.
     *
     * @param overflowColor a icon color
     */
    public void setMenuOverflowColor(@ColorInt final int overflowColor) {
        mMenuOverflowColor = Color.alpha(overflowColor) == 0 ? mDefaultColor : overflowColor;
        mMenuView.setOverflowColor(mMenuOverflowColor);
    }

    /**
     * Sets the listener that will be called when
     * an item in the overflow menu is clicked.
     *
     * @param listener listener to listen to menu item clicks
     */
    public void setOnMenuItemClickListener(final OnMenuItemClickListener listener) {
        this.mActionMenuItemListener = listener;
    }

    /**
     * Show avatar view.
     *
     * @param isAvatar an avatar flag
     */
    public void setAvatar(boolean isAvatar) {
        this.mIsAvatar = isAvatar;
        if (mIsAvatar) {
            mAvatarView.setVisibility(VISIBLE);
            mCircularIconView.setVisibility(GONE);
            mIconView.setVisibility(GONE);
        } else {
            mAvatarView.setVisibility(GONE);
        }
        adjustPadding();
    }

    /**
     * Check if item should display avatar.
     *
     * @return if item has avatar
     */
    public boolean hasAvatar() {
        return mIsAvatar;
    }

    /**
     * Check if item should display circular icon.
     *
     * @return if item has circular icon
     */
    public boolean hasCircularIcon() {
        return mIconDrawable != null && mIsCircularIcon;
    }

    /**
     * Check if item should display icon.
     *
     * @return if item has icon
     */
    public boolean hasIcon() {
        return mIconDrawable != null;
    }

    /**
     * Check if item should display action menu.
     *
     * @return if item has action menu
     */
    public boolean hasActionMenu() {
        return mMenuId != NULL;
    }

    /**
     * Getter for title.
     *
     * @return a title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Getter for subtitle.
     *
     * @return a subtitle
     */
    public String getSubtitle() {
        return mSubtitle;
    }

    /**
     * Getter for action menu items room.
     *
     * @return a number of action items visible
     */
    public int getMenuItemsRoom() {
        return mMenuItemsRoom;
    }

    /**
     * Getter for icon color.
     *
     * @return a icon color
     */
    @ColorInt
    public int getIconColor() {
        return mIconColor;
    }

    /**
     * Getter for circular icon color.
     *
     * @return a circular icon color
     */
    @ColorInt
    public int getCircularIconColor() {
        return mCircularIconColor;
    }

    /**
     * Getter for menu action color.
     *
     * @return a menu action color
     */
    @ColorInt
    public int getMenuActionColor() {
        return mMenuActionColor;
    }

    /**
     * Getter for menu overflow color.
     *
     * @return a menu overflow color
     */
    @ColorInt
    public int getMenuOverflowColor() {
        return mMenuOverflowColor;
    }

    /**
     * Getter for avatar view. Used for loading image.
     *
     * @return an avatar image view
     */
    public ImageView getAvatarView() {
        return mAvatarView;
    }

    private static class SavedState extends BaseSavedState {

        private int menuId;

        private int menuItemsRoom;

        private int menuActionColor;

        private int menuOverflowColor;

        private String title;

        private String subtitle;

        private boolean isMultiline;

        private boolean forceKeyline;

        private int iconColor;

        private boolean isCircularIcon;

        private int circularIconColor;

        private int iconResId;

        private boolean isAvatar;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            menuId = in.readInt();
            menuItemsRoom = in.readInt();
            menuActionColor = in.readInt();
            menuOverflowColor = in.readInt();
            title = in.readString();
            subtitle = in.readString();
            isMultiline = in.readInt() == 1;
            forceKeyline = in.readInt() == 1;
            iconColor = in.readInt();
            isCircularIcon = in.readInt() == 1;
            circularIconColor = in.readInt();
            iconResId = in.readInt();
            isAvatar = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(menuId);
            out.writeInt(menuItemsRoom);
            out.writeInt(menuActionColor);
            out.writeInt(menuOverflowColor);
            out.writeString(title);
            out.writeString(subtitle);
            out.writeInt(isMultiline ? 1 : 0);
            out.writeInt(forceKeyline ? 1 : 0);
            out.writeInt(iconColor);
            out.writeInt(isCircularIcon ? 1 : 0);
            out.writeInt(circularIconColor);
            out.writeInt(iconResId);
            out.writeInt(isAvatar ? 1 : 0);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
