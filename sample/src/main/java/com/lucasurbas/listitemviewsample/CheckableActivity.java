package com.lucasurbas.listitemviewsample;

import static com.jrummyapps.android.colorpicker.ColorPickerDialog.TYPE_PRESETS;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.lucasurbas.listitemview.ListItemView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * @author lurbas on 25/02/2018
 */
public class CheckableActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private static final int ICON_COLOR_ID = 1;

    private static final int CIRCLE_COLOR_ID = 2;

    private static final int ACTION_MENU_COLOR_ID = 3;

    private static final String KEY_TITLE = "key_title";

    private static final String KEY_SUBTITLE = "key_subtitle";

    private static final String KEY_MULTILINE = "key_multiline";

    private static final String KEY_KEYLINE = "key_keyline";

    private static final String AVATAR_URL = "https://s-media-cache-ak0.pinimg.com/originals/a8/eb/5e/a8eb5e1e919fa1784d621549f3c2c259.jpg";

    @BindView(R.id.list_item_view)
    ListItemView listItemView;

    @BindView(R.id.attr_displayMode)
    ListItemView attributeDisplayModeView;

    @BindView(R.id.attr_iconColor)
    ListItemView attributeIconColorView;

    @BindView(R.id.attr_circularIconColor)
    ListItemView attributeCircularIconColorView;

    @BindView(R.id.attr_actionMenu)
    ListItemView attributeActionMenuView;

    @BindView(R.id.attr_actionMenuItemColor)
    ListItemView attributeActionMenuItemColorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkable);
        ButterKnife.bind(this);

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemView.toggle();
                listItemView.setTitle(listItemView.isChecked() ? R.string.title_checked : R.string.title);
            }
        });

        attributeDisplayModeView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrDisplayModeClicked(item.getItemId());
                    }
                });
        attributeIconColorView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        showColorPicker(ICON_COLOR_ID);
                    }
                });
        attributeCircularIconColorView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        showColorPicker(CIRCLE_COLOR_ID);
                    }
                });
        attributeActionMenuView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrActionMenuClicked(item.getItemId());
                    }
                });
        attributeActionMenuItemColorView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        showColorPicker(ACTION_MENU_COLOR_ID);
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void onAttrDisplayModeClicked(int itemId) {
        switch (itemId) {
            default:
            case R.id.action_modeStandard:
                listItemView.setDisplayMode(ListItemView.MODE_STANDARD);
                listItemView.setIconResId(ListItemView.NULL);
                attributeDisplayModeView.setSubtitle(R.string.attr_modeStandard);
                break;

            case R.id.action_modeIcon:
                listItemView.setDisplayMode(ListItemView.MODE_ICON);
                listItemView.setIconResId(R.drawable.ic_call_24dp);
                attributeDisplayModeView.setSubtitle(R.string.attr_modeIcon);
                break;

            case R.id.action_modeCircularIcon:
                listItemView.setDisplayMode(ListItemView.MODE_CIRCULAR_ICON);
                listItemView.setIconResId(R.drawable.ic_call_24dp);
                attributeDisplayModeView.setSubtitle(R.string.attr_modeCircularIcon);
                break;

            case R.id.action_modeAvatar:
                listItemView.setDisplayMode(ListItemView.MODE_AVATAR);
                listItemView.setIconResId(ListItemView.NULL);
                attributeDisplayModeView.setSubtitle(R.string.attr_modeAvatar);

                OkHttpClient client = new OkHttpClient();
                Picasso picasso = new Picasso.Builder(this).loggingEnabled(true)
                        .downloader(new OkHttp3Downloader(client))
                        .listener(new Picasso.Listener() {
                            @Override
                            public void onImageLoadFailed(final Picasso picasso, final Uri uri,
                                    final Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .build();

                picasso.load(AVATAR_URL)
                        .placeholder(R.drawable.placeholder)
                        .transform(new CircleTransform())
                        .into(listItemView.getAvatarView());
                break;
        }
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
        }
    }

    @Override
    public void onDialogDismissed(final int dialogId) {
        //empty
    }
}
