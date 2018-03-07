package com.lucasurbas.listitemviewsample;

import static com.jrummyapps.android.colorpicker.ColorPickerDialog.TYPE_PRESETS;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;
import com.lucasurbas.listitemview.ListItemView;
import com.lucasurbas.listitemview.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Sample of checkable attributes.
 *
 * @author Lucas Urbas
 */
public class CheckableAttrsActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private static final int ICON_CHECKED_COLOR_ID = 1;

    private static final int CIRCLE_CHECKED_COLOR_ID = 2;

    private static final int ACTION_MENU_CHECKED_COLOR_ID = 3;

    @BindView(R.id.list_item_view)
    ListItemView listItemView;

    @BindView(R.id.checkable_exampleNoIcon)
    ListItemView checkableExampleNoIcon;

    @BindView(R.id.checkable_exampleSimple)
    ListItemView checkableExampleSimple;

    @BindView(R.id.checkable_exampleTwoStates)
    ListItemView checkableExampleTwoStates;

    @BindView(R.id.checkable_exampleAnimated)
    ListItemView checkableExampleAnimated;

    @BindView(R.id.checkable_exampleCircular)
    ListItemView checkableExampleCircular;

    @BindView(R.id.checkable_exampleNoMenu)
    ListItemView checkableExampleNoMenu;

    @BindView(R.id.checkable_exampleSimpleMenu)
    ListItemView checkableExampleSimpleMenu;

    @BindView(R.id.checkable_exampleTwoStatesMenu)
    ListItemView checkableExampleTwoStatesMenu;

    @BindView(R.id.checkable_exampleAnimatedMenu)
    ListItemView checkableExampleAnimatedMenu;

    @BindView(R.id.attr_iconCheckedColor)
    ListItemView attributeIconCheckedColorView;

    @BindView(R.id.attr_circularIconColor)
    ListItemView attributeCircularIconColorView;

    @BindView(R.id.attr_actionMenuItemColor)
    ListItemView attributeActionMenuItemColorView;

    private AnimationHelper mAnimationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_attrs_checkable);
        ButterKnife.bind(this);

        mAnimationHelper = new AnimationHelper(this);

        listItemView.setOnClickListener(v -> {
            listItemView.toggle();
            listItemView.setSubtitle(listItemView.isChecked() ? R.string.checkable_checked
                    : R.string.checkable_unchecked);
        });
        listItemView.setOnMenuItemClickListener(item -> {
            listItemView.toggle();
            listItemView.setSubtitle(listItemView.isChecked() ? R.string.checkable_checked
                    : R.string.checkable_unchecked);
        });

        mAnimationHelper.setupRadioButton(checkableExampleNoIcon);
        checkableExampleNoIcon.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_STANDARD);
            listItemView.setIconResId(ListItemView.NULL);

            mAnimationHelper.setRadioButtonState(checkableExampleNoIcon, true);
            mAnimationHelper.setRadioButtonState(checkableExampleSimple, false);
            mAnimationHelper.setRadioButtonState(checkableExampleTwoStates, false);
            mAnimationHelper.setRadioButtonState(checkableExampleAnimated, false);
            mAnimationHelper.setRadioButtonState(checkableExampleCircular, false);
        });

        mAnimationHelper.setupRadioButton(checkableExampleSimple);
        checkableExampleSimple.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_ICON);
            listItemView.setIconResId(R.drawable.ic_call_24dp);

            mAnimationHelper.setRadioButtonState(checkableExampleNoIcon, false);
            mAnimationHelper.setRadioButtonState(checkableExampleSimple, true);
            mAnimationHelper.setRadioButtonState(checkableExampleTwoStates, false);
            mAnimationHelper.setRadioButtonState(checkableExampleAnimated, false);
            mAnimationHelper.setRadioButtonState(checkableExampleCircular, false);
        });

        mAnimationHelper.setupRadioButton(checkableExampleTwoStates);
        checkableExampleTwoStates.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_ICON);
            listItemView.setIconResId(R.drawable.selector_ic_check);

            mAnimationHelper.setRadioButtonState(checkableExampleNoIcon, false);
            mAnimationHelper.setRadioButtonState(checkableExampleSimple, false);
            mAnimationHelper.setRadioButtonState(checkableExampleTwoStates, true);
            mAnimationHelper.setRadioButtonState(checkableExampleAnimated, false);
            mAnimationHelper.setRadioButtonState(checkableExampleCircular, false);
        });

        mAnimationHelper.setupRadioButton(checkableExampleAnimated);
        checkableExampleAnimated.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_ICON);
            mAnimationHelper.setupCheckBox(listItemView);

            mAnimationHelper.setRadioButtonState(checkableExampleNoIcon, false);
            mAnimationHelper.setRadioButtonState(checkableExampleSimple, false);
            mAnimationHelper.setRadioButtonState(checkableExampleTwoStates, false);
            mAnimationHelper.setRadioButtonState(checkableExampleAnimated, true);
            mAnimationHelper.setRadioButtonState(checkableExampleCircular, false);
        });

        mAnimationHelper.setupRadioButton(checkableExampleCircular);
        checkableExampleCircular.setOnClickListener(v -> {
            listItemView.setDisplayMode(ListItemView.MODE_CIRCULAR_ICON);
            listItemView.setIconResId(R.drawable.ic_call_24dp);

            mAnimationHelper.setRadioButtonState(checkableExampleNoIcon, false);
            mAnimationHelper.setRadioButtonState(checkableExampleSimple, false);
            mAnimationHelper.setRadioButtonState(checkableExampleTwoStates, false);
            mAnimationHelper.setRadioButtonState(checkableExampleAnimated, false);
            mAnimationHelper.setRadioButtonState(checkableExampleCircular, true);
        });

        checkableExampleNoMenu.setOnClickListener(v -> {
            listItemView.inflateMenu(ListItemView.NULL);

            checkableExampleNoMenu.setChecked(true);
            checkableExampleSimpleMenu.setChecked(false);
            checkableExampleTwoStatesMenu.setChecked(false);
            checkableExampleAnimatedMenu.setChecked(false);
        });

        checkableExampleSimpleMenu.setOnClickListener(v -> {
            listItemView.inflateMenu(R.menu.single_action_menu);

            checkableExampleNoMenu.setChecked(false);
            checkableExampleSimpleMenu.setChecked(true);
            checkableExampleTwoStatesMenu.setChecked(false);
            checkableExampleAnimatedMenu.setChecked(false);
        });

        checkableExampleTwoStatesMenu.setOnClickListener(v -> {
            listItemView.inflateMenu(R.menu.checkable_action_menu);

            checkableExampleNoMenu.setChecked(false);
            checkableExampleSimpleMenu.setChecked(false);
            checkableExampleTwoStatesMenu.setChecked(true);
            checkableExampleAnimatedMenu.setChecked(false);
        });

        checkableExampleAnimatedMenu.setOnClickListener(v -> {
            listItemView.inflateMenu(R.menu.checkable_animated_action_menu);

            checkableExampleNoMenu.setChecked(false);
            checkableExampleSimpleMenu.setChecked(false);
            checkableExampleTwoStatesMenu.setChecked(false);
            checkableExampleAnimatedMenu.setChecked(true);
        });

        attributeIconCheckedColorView.setOnClickListener(v -> showColorPicker(ICON_CHECKED_COLOR_ID));

        attributeCircularIconColorView.setOnClickListener(v -> showColorPicker(CIRCLE_CHECKED_COLOR_ID));

        attributeActionMenuItemColorView.setOnClickListener(v -> showColorPicker(ACTION_MENU_CHECKED_COLOR_ID));

        if (savedInstanceState == null) {
            checkableExampleSimple.setChecked(true);
            checkableExampleNoMenu.setChecked(true);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
        int defaultColor = ViewUtils.getDefaultColor(this);
        int[][] states;
        int[] colors;
        switch (dialogId) {
            case ICON_CHECKED_COLOR_ID:
                states = new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                };
                colors = new int[]{
                        color,
                        defaultColor
                };
                listItemView.setIconColorList(new ColorStateList(states, colors));
                attributeIconCheckedColorView.setMenuActionColor(color);
                break;

            case CIRCLE_CHECKED_COLOR_ID:
                states = new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                };
                colors = new int[]{
                        color,
                        defaultColor
                };
                listItemView.setCircularIconColorList(new ColorStateList(states, colors));
                attributeCircularIconColorView.setMenuActionColor(color);
                break;

            case ACTION_MENU_CHECKED_COLOR_ID:
                states = new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                };
                colors = new int[]{
                        color,
                        defaultColor
                };
                listItemView.setMenuActionColorList(new ColorStateList(states, colors));
                attributeActionMenuItemColorView.setMenuActionColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(final int dialogId) {
        //empty
    }
}
