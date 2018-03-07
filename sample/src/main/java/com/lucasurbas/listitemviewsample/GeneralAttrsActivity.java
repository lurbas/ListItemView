package com.lucasurbas.listitemviewsample;

import static com.jrummyapps.android.colorpicker.ColorPickerDialog.TYPE_PRESETS;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.lucasurbas.listitemview.ListItemView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * Sample of general attributes.
 *
 * @author Lucas Urbas
 */
public class GeneralAttrsActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private static final int ICON_COLOR_ID = 1;

    private static final int CIRCLE_COLOR_ID = 2;

    private static final int ACTION_MENU_COLOR_ID = 3;

    private static final int OVERFLOW_COLOR_ID = 4;

    private static final String AVATAR_URL =
            "https://s-media-cache-ak0.pinimg"
                    + ".com/originals/a8/eb/5e/a8eb5e1e919fa1784d621549f3c2c259.jpg";

    @BindView(R.id.list_item_view)
    ListItemView listItemView;

    @BindView(R.id.attr_title)
    ListItemView attributeTitleView;

    @BindView(R.id.attr_subtitle)
    ListItemView attributeSubtitleView;

    @BindView(R.id.attr_multiline)
    ListItemView attributeMultilineView;

    @BindView(R.id.attr_forceKeyline)
    ListItemView attributeForceKeylineView;

    @BindView(R.id.attr_displayModeStandard)
    ListItemView attributeDisplayModeStandardView;

    @BindView(R.id.attr_displayModeIcon)
    ListItemView attributeDisplayModeIconView;

    @BindView(R.id.attr_displayModeCircularIcon)
    ListItemView attributeDisplayModeCircularIconView;

    @BindView(R.id.attr_displayModeAvatar)
    ListItemView attributeDisplayModeAvatarView;

    @BindView(R.id.attr_iconColor)
    ListItemView attributeIconColorView;

    @BindView(R.id.attr_circularIconColor)
    ListItemView attributeCircularIconColorView;

    @BindView(R.id.attr_actionMenu)
    ListItemView attributeActionMenuView;

    @BindView(R.id.attr_actionMenuRoom)
    ListItemView attributeActionMenuRoomView;

    @BindView(R.id.attr_actionMenuItemColor)
    ListItemView attributeActionMenuItemColorView;

    @BindView(R.id.attr_actionMenuOverflowColor)
    ListItemView attributeActionMenuOverflowColorView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_attrs_general);
        ButterKnife.bind(this);

        listItemView.setOnClickListener(v -> {});
        listItemView.setOnMenuItemClickListener(item -> {});

        attributeTitleView.setOnClickListener(v -> onAttrTitleClicked());

        attributeSubtitleView.setOnClickListener(v -> onAttrSubtitleClicked());

        attributeMultilineView.setOnClickListener(v -> onAttrMultilineClicked());

        attributeForceKeylineView.setOnClickListener(v -> onAttrForceKeylineClicked());

        setupCheckableRadioButton(attributeDisplayModeStandardView);
        attributeDisplayModeStandardView.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_STANDARD);
            listItemView.setIconResId(ListItemView.NULL);

            setCheckableRadioButtonState(attributeDisplayModeStandardView, true);
            setCheckableRadioButtonState(attributeDisplayModeIconView, false);
            setCheckableRadioButtonState(attributeDisplayModeCircularIconView, false);
            setCheckableRadioButtonState(attributeDisplayModeAvatarView, false);
        });

        setupCheckableRadioButton(attributeDisplayModeIconView);
        attributeDisplayModeIconView.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_ICON);
            listItemView.setIconResId(R.drawable.ic_call_24dp);

            setCheckableRadioButtonState(attributeDisplayModeStandardView, false);
            setCheckableRadioButtonState(attributeDisplayModeIconView, true);
            setCheckableRadioButtonState(attributeDisplayModeCircularIconView, false);
            setCheckableRadioButtonState(attributeDisplayModeAvatarView, false);
        });

        setupCheckableRadioButton(attributeDisplayModeCircularIconView);
        attributeDisplayModeCircularIconView.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_CIRCULAR_ICON);
            listItemView.setIconResId(R.drawable.ic_call_24dp);

            setCheckableRadioButtonState(attributeDisplayModeStandardView, false);
            setCheckableRadioButtonState(attributeDisplayModeIconView, false);
            setCheckableRadioButtonState(attributeDisplayModeCircularIconView, true);
            setCheckableRadioButtonState(attributeDisplayModeAvatarView, false);
        });

        setupCheckableRadioButton(attributeDisplayModeAvatarView);
        attributeDisplayModeAvatarView.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_AVATAR);
            listItemView.setIconResId(ListItemView.NULL);

            setCheckableRadioButtonState(attributeDisplayModeStandardView, false);
            setCheckableRadioButtonState(attributeDisplayModeIconView, false);
            setCheckableRadioButtonState(attributeDisplayModeCircularIconView, false);
            setCheckableRadioButtonState(attributeDisplayModeAvatarView, true);

            OkHttpClient client = new OkHttpClient();
            Picasso picasso = new Picasso.Builder(this).loggingEnabled(true)
                    .downloader(new OkHttp3Downloader(client))
                    .build();

            picasso.load(AVATAR_URL)
                    .placeholder(R.drawable.placeholder)
                    .transform(new CircleTransform())
                    .into(listItemView.getAvatarView());
        });

        attributeIconColorView.setOnClickListener(v -> showColorPicker(ICON_COLOR_ID));

        attributeCircularIconColorView.setOnClickListener(v -> showColorPicker(CIRCLE_COLOR_ID));

        attributeActionMenuView.setOnMenuItemClickListener(
                item -> onAttrActionMenuClicked(item.getItemId()));

        attributeActionMenuRoomView.setOnMenuItemClickListener(
                item -> onAttrActionMenuRoomClicked(item.getItemId()));

        attributeActionMenuItemColorView.setOnClickListener(
                v -> showColorPicker(ACTION_MENU_COLOR_ID));

        attributeActionMenuOverflowColorView.setOnClickListener(
                v -> showColorPicker(OVERFLOW_COLOR_ID));

        if (savedInstanceState == null) {
            attributeTitleView.setChecked(true);
            attributeSubtitleView.setChecked(true);
            attributeDisplayModeStandardView.setChecked(true);
        }
    }

    private void onAttrTitleClicked() {
        attributeTitleView.toggle();
        listItemView.setTitle(attributeTitleView.isChecked() ? getString(R.string.title) : null);
    }

    private void onAttrSubtitleClicked() {
        attributeSubtitleView.toggle();
        listItemView.setSubtitle(
                attributeSubtitleView.isChecked() ? getString(R.string.subtitle_long) : null);
    }

    private void onAttrMultilineClicked() {
        attributeMultilineView.toggle();
        listItemView.setMultiline(attributeMultilineView.isChecked());
    }

    private void onAttrForceKeylineClicked() {
        attributeForceKeylineView.toggle();
        listItemView.forceKeyline(attributeForceKeylineView.isChecked());
    }

    private void onAttrActionMenuClicked(int itemId) {
        switch (itemId) {
            default:
            case R.id.action_none:
                listItemView.inflateMenu(ListItemView.NULL);
                attributeActionMenuView.setSubtitle(R.string.attr_menuNone);
                break;

            case R.id.action_single:
                listItemView.inflateMenu(R.menu.single_action_menu);
                attributeActionMenuView.setSubtitle(R.string.attr_menuSingle);
                break;

            case R.id.action_multiple:
                listItemView.inflateMenu(R.menu.multiple_action_menu);
                attributeActionMenuView.setSubtitle(R.string.attr_menuMultiple);
                break;
        }
    }

    private void onAttrActionMenuRoomClicked(int itemId) {
        switch (itemId) {
            default:
            case R.id.action_room1:
                listItemView.setMenuItemsRoom(1);
                attributeActionMenuRoomView.setSubtitle(R.string.attr_room1);
                break;

            case R.id.action_room2:
                listItemView.setMenuItemsRoom(2);
                attributeActionMenuRoomView.setSubtitle(R.string.attr_room2);
                break;

            case R.id.action_room3:
                listItemView.setMenuItemsRoom(3);
                attributeActionMenuRoomView.setSubtitle(R.string.attr_room3);
                break;
        }
    }

    private void showColorPicker(final int pickerId) {
        ColorPickerDialog.newBuilder()
                .setDialogType(TYPE_PRESETS)
                .setDialogId(pickerId)
                .setAllowCustom(false)
                .setShowAlphaSlider(true)
                .show(this);
    }

    @Override
    public void onColorSelected(final int dialogId, @ColorInt final int color) {
        switch (dialogId) {
            case ICON_COLOR_ID:
                listItemView.setIconColor(color);
                attributeIconColorView.setMenuActionColor(color);
                break;

            case CIRCLE_COLOR_ID:
                listItemView.setCircularIconColor(color);
                attributeCircularIconColorView.setMenuActionColor(color);
                break;

            case ACTION_MENU_COLOR_ID:
                listItemView.setMenuActionColor(color);
                attributeActionMenuItemColorView.setMenuActionColor(color);
                break;

            case OVERFLOW_COLOR_ID:
                listItemView.setMenuOverflowColor(color);
                attributeActionMenuOverflowColorView.setMenuActionColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(final int dialogId) {
        //empty
    }

    private void setupCheckableRadioButton(ListItemView listItemView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedStateListDrawable asl = new AnimatedStateListDrawable();
            asl.addState(
                    new int[]{android.R.attr.state_checked},
                    AppCompatResources.getDrawable(this, R.drawable.vd_radiobutton_checked),
                    R.id.checked);
            asl.addState(
                    new int[0],
                    AppCompatResources.getDrawable(this, R.drawable.vd_radiobutton_unchecked),
                    R.id.unchecked);
            asl.addTransition(
                    R.id.unchecked,
                    R.id.checked,
                    AnimatedVectorDrawableCompat.create(this, R.drawable.avd_radiobutton_unchecked_to_checked),
                    false);
            asl.addTransition(
                    R.id.checked,
                    R.id.unchecked,
                    AnimatedVectorDrawableCompat.create(this, R.drawable.avd_radiobutton_checked_to_unchecked),
                    false);
            listItemView.setIconDrawable(asl);
        } else {
            listItemView.setIconResId(R.drawable.selector_ic_radio);
        }
    }

    private void setCheckableRadioButtonState(ListItemView listItemView, boolean checked) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP &&
                listItemView.isChecked() != checked) {
            Drawable drawable = checked ?
                    AnimatedVectorDrawableCompat
                            .create(this, R.drawable.avd_radiobutton_unchecked_to_checked) :
                    AnimatedVectorDrawableCompat
                            .create(this, R.drawable.avd_radiobutton_checked_to_unchecked);
            listItemView.setIconDrawable(drawable);
            ((Animatable) drawable).start();
        }
        listItemView.setChecked(checked);
    }
}
