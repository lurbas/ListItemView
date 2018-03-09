package com.lucasurbas.listitemviewsample;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.widget.ImageView;

import com.lucasurbas.listitemview.ListItemView;

import java.util.List;

/**
 * @author lurbas on 07/03/2018
 */
public class AnimationHelper {

    private final Context mContext;

    public AnimationHelper(Context context){
        mContext = context;
    }

    public void setupRadioButton(ListItemView listItemView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedStateListDrawable asl = new AnimatedStateListDrawable();
            asl.addState(
                    new int[]{android.R.attr.state_checked},
                    AppCompatResources.getDrawable(mContext, R.drawable.vd_radiobutton_checked),
                    R.id.checked);
            asl.addState(
                    new int[0],
                    AppCompatResources.getDrawable(mContext, R.drawable.vd_radiobutton_unchecked),
                    R.id.unchecked);
            asl.addTransition(
                    R.id.unchecked,
                    R.id.checked,
                    AnimatedVectorDrawableCompat.create(mContext, R.drawable.avd_radiobutton_unchecked_to_checked),
                    false);
            asl.addTransition(
                    R.id.checked,
                    R.id.unchecked,
                    AnimatedVectorDrawableCompat.create(mContext, R.drawable.avd_radiobutton_checked_to_unchecked),
                    false);
            listItemView.setIconDrawable(asl);
        } else {
            listItemView.setIconResId(R.drawable.selector_ic_radio);
        }
    }

    public void setRadioButtonState(ListItemView listItemView, boolean checked) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP &&
                listItemView.isChecked() != checked) {
            Drawable drawable = checked ?
                    AnimatedVectorDrawableCompat
                            .create(mContext, R.drawable.avd_radiobutton_unchecked_to_checked) :
                    AnimatedVectorDrawableCompat
                            .create(mContext, R.drawable.avd_radiobutton_checked_to_unchecked);
            listItemView.setIconDrawable(drawable);
            ((Animatable) drawable).start();
        }
        listItemView.setChecked(checked);
    }

    public void setupCheckBox(ListItemView listItemView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedStateListDrawable asl = new AnimatedStateListDrawable();
            asl.addState(
                    new int[]{android.R.attr.state_checked},
                    AppCompatResources.getDrawable(mContext, R.drawable.vd_checkbox_checked),
                    R.id.checked);
            asl.addState(
                    new int[0],
                    AppCompatResources.getDrawable(mContext, R.drawable.vd_checkbox_unchecked),
                    R.id.unchecked);
            asl.addTransition(
                    R.id.unchecked,
                    R.id.checked,
                    AnimatedVectorDrawableCompat.create(mContext, R.drawable.avd_checkbox_unchecked_to_checked),
                    false);
            asl.addTransition(
                    R.id.checked,
                    R.id.unchecked,
                    AnimatedVectorDrawableCompat.create(mContext, R.drawable.avd_checkbox_checked_to_unchecked),
                    false);
            listItemView.setIconDrawable(asl);
        } else {
            listItemView.setIconResId(R.drawable.selector_ic_check);
        }
    }

    public void setupCheckBoxMenu(ListItemView listItemView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimatedStateListDrawable asl = new AnimatedStateListDrawable();
            asl.addState(
                    new int[]{android.R.attr.state_checked},
                    AppCompatResources.getDrawable(mContext, R.drawable.vd_checkbox_checked),
                    R.id.checked);
            asl.addState(
                    new int[0],
                    AppCompatResources.getDrawable(mContext, R.drawable.vd_checkbox_unchecked),
                    R.id.unchecked);
            asl.addTransition(
                    R.id.unchecked,
                    R.id.checked,
                    AnimatedVectorDrawableCompat.create(mContext, R.drawable.avd_checkbox_unchecked_to_checked),
                    false);
            asl.addTransition(
                    R.id.checked,
                    R.id.unchecked,
                    AnimatedVectorDrawableCompat.create(mContext, R.drawable.avd_checkbox_checked_to_unchecked),
                    false);

            listItemView.inflateMenu(R.menu.checkable_action_menu);
            ImageView imageView = (ImageView) listItemView.findMenuItem(R.id.action_checkable).getActionView();
            imageView.setImageDrawable(asl);
            asl.jumpToCurrentState();
        } else {
            listItemView.inflateMenu(R.menu.checkable_action_menu);
        }
    }

    public void toggleCheckBox(ListItemView listItemView, boolean toggle) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = listItemView.isChecked() ?
                    AnimatedVectorDrawableCompat
                            .create(mContext, R.drawable.avd_checkbox_checked_to_unchecked) :
                    AnimatedVectorDrawableCompat
                            .create(mContext, R.drawable.avd_checkbox_unchecked_to_checked);
            listItemView.setIconDrawable(drawable);
            ((Animatable) drawable).start();
        }
        if (toggle) {
            listItemView.toggle();
        }
    }

    public void toggleCheckBoxMenu(ListItemView listItemView, boolean toggle) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = listItemView.isChecked() ?
                    AnimatedVectorDrawableCompat
                            .create(mContext, R.drawable.avd_checkbox_checked_to_unchecked) :
                    AnimatedVectorDrawableCompat
                            .create(mContext, R.drawable.avd_checkbox_unchecked_to_checked);
            ImageView imageView =
                    (ImageView) listItemView.findMenuItem(R.id.action_checkable).getActionView();
            imageView.setImageDrawable(drawable);
            ((Animatable) drawable).start();
        }
        if (toggle) {
            listItemView.toggle();
        }
    }
}
