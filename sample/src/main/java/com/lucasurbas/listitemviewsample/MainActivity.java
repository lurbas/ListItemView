package com.lucasurbas.listitemviewsample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.lucasurbas.listitemview.ListItemView;

import static com.jrummyapps.android.colorpicker.ColorPickerDialog.TYPE_PRESETS;


public class MainActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private static final int ICON_COLOR_ID = 1;

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

    @BindView(R.id.attr_icon)
    ListItemView attributeIconView;

    @BindView(R.id.attr_iconColor)
    ListItemView attributeIconColorView;

    @BindView(R.id.attr_circularIcon)
    ListItemView attributeCircularIconView;

    @BindView(R.id.attr_actionMenu)
    ListItemView attributeActionMenuView;

    @BindView(R.id.attr_actionMenuRoom)
    ListItemView attributeActionMenuRoomView;

    private boolean attrTitle = true;

    private boolean attrSubtitle = true;

    private boolean multiline;

    private boolean forceKeyline;

    private boolean icon;

    private boolean circularIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        attributeTitleView.setOnMenuItemClickListener(new ListItemView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(final MenuItem item) {
                onAttrTitleClicked();
            }
        });
        attributeSubtitleView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrSubtitleClicked();
                    }
                });
        attributeMultilineView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrMultilineClicked();
                    }
                });
        attributeForceKeylineView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrForceKeylineClicked();
                    }
                });
        attributeIconView.setOnMenuItemClickListener(new ListItemView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(final MenuItem item) {
                onAttrIconClicked();
            }
        });
        attributeIconColorView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrIconColorClicked();
                    }
                });
        attributeCircularIconView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrCircularIconClicked();
                    }
                });
        attributeActionMenuView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrActionMenuClicked(item.getItemId());
                    }
                });
        attributeActionMenuRoomView.setOnMenuItemClickListener(
                new ListItemView.OnMenuItemClickListener() {
                    @Override
                    public void onActionMenuItemSelected(final MenuItem item) {
                        onAttrActionMenuRoomClicked(item.getItemId());
                    }
                });
    }

    private void onAttrTitleClicked() {
        attrTitle = !attrTitle;
        attributeTitleView.inflateMenu(attrTitle ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView.setTitle(attrTitle ? getString(R.string.title) : null);
    }

    private void onAttrSubtitleClicked() {
        attrSubtitle = !attrSubtitle;
        attributeSubtitleView.inflateMenu(attrSubtitle ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView.setSubtitle(attrSubtitle ? getString(R.string.subtitle_long) : null);
    }

    private void onAttrMultilineClicked() {
        multiline = !multiline;
        attributeMultilineView.inflateMenu(multiline ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView.setMultiline(multiline);
    }

    private void onAttrForceKeylineClicked() {
        forceKeyline = !forceKeyline;
        attributeForceKeylineView.inflateMenu(
                forceKeyline ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView.forceKeyline(forceKeyline);
    }

    private void onAttrIconClicked() {
        icon = !icon;
        attributeIconView.inflateMenu(icon ? R.menu.uncheck_menu : R.menu.check_menu);
        Drawable iconDrawable = icon ? ContextCompat.getDrawable(this, R.drawable.ic_call_24dp)
                : null;
        listItemView.setIcon(iconDrawable);
    }

    private void onAttrIconColorClicked() {
        ColorPickerDialog.newBuilder()
                .setDialogType(TYPE_PRESETS)
                .setDialogId(ICON_COLOR_ID)
                .setAllowCustom(false)
                .setShowAlphaSlider(true)
                .show(this);
    }

    private void onAttrCircularIconClicked() {
        circularIcon = !circularIcon;
        attributeCircularIconView.inflateMenu(
                circularIcon ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView.useCircularIcon(circularIcon);
    }

    private void onAttrActionMenuClicked(int itemId) {
        switch (itemId) {
            default:
            case R.id.action_none:
                listItemView.inflateMenu(ListItemView.NO_ACTION_MENU);
                attributeActionMenuView.setSubtitle(R.string.attr_menu_none);
                break;

            case R.id.action_single:
                listItemView.inflateMenu(R.menu.single_action_menu);
                attributeActionMenuView.setSubtitle(R.string.attr_menu_single);
                break;

            case R.id.action_multiple:
                listItemView.inflateMenu(R.menu.multiple_action_menu);
                attributeActionMenuView.setSubtitle(R.string.attr_menu_multiple);
                break;
        }
    }

    private void onAttrActionMenuRoomClicked(int itemId) {
        switch (itemId) {
            default:
            case R.id.action_room_1:
                listItemView.setMenuItemsRoom(1);
                attributeActionMenuRoomView.setSubtitle(R.string.attr_room_1);
                break;

            case R.id.action_room_2:
                listItemView.setMenuItemsRoom(2);
                attributeActionMenuRoomView.setSubtitle(R.string.attr_room_2);
                break;

            case R.id.action_room_3:
                listItemView.setMenuItemsRoom(3);
                attributeActionMenuRoomView.setSubtitle(R.string.attr_room_3);
                break;
        }
    }

    @Override
    public void onColorSelected(final int dialogId, @ColorInt final int color) {
        switch (dialogId) {
            case ICON_COLOR_ID:
                listItemView.setIconColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(final int dialogId) {
        //empty
    }
}
