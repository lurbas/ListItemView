package com.lucasurbas.listitemviewsample;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lucasurbas.listitemview.ListItemView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.list_item_view_1)
    ListItemView listItemView1;

    @BindView(R.id.list_item_view_2)
    ListItemView listItemView2;

    @BindView(R.id.list_item_view_3)
    ListItemView listItemView3;

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

    @BindView(R.id.attr_circularIcon)
    ListItemView attributeCircularIconView;

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
        attributeCircularIconView.setOnMenuItemClickListener(new ListItemView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(final MenuItem item) {
                onAttrCircularIconClicked();
            }
        });
    }

    private void onAttrTitleClicked() {
        attrTitle = !attrTitle;
        attributeTitleView.inflateMenu(attrTitle ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView1.setTitle(attrTitle ? getString(R.string.title) : null);
        listItemView2.setTitle(attrTitle ? getString(R.string.title) : null);
        listItemView3.setTitle(attrTitle ? getString(R.string.title) : null);
    }

    private void onAttrSubtitleClicked() {
        attrSubtitle = !attrSubtitle;
        attributeSubtitleView.inflateMenu(attrSubtitle ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView1.setSubtitle(attrSubtitle ? getString(R.string.subtitle_long) : null);
        listItemView2.setSubtitle(attrSubtitle ? getString(R.string.subtitle_long) : null);
        listItemView3.setSubtitle(attrSubtitle ? getString(R.string.subtitle_long) : null);
    }

    private void onAttrMultilineClicked() {
        multiline = !multiline;
        attributeMultilineView.inflateMenu(multiline ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView1.setMultiline(multiline);
        listItemView2.setMultiline(multiline);
        listItemView3.setMultiline(multiline);
    }

    private void onAttrForceKeylineClicked() {
        forceKeyline = !forceKeyline;
        attributeForceKeylineView.inflateMenu(
                forceKeyline ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView1.forceKeyline(forceKeyline);
        listItemView2.forceKeyline(forceKeyline);
        listItemView3.forceKeyline(forceKeyline);
    }

    private void onAttrIconClicked() {
        icon = !icon;
        attributeIconView.inflateMenu(icon ? R.menu.uncheck_menu : R.menu.check_menu);
        Drawable iconDrawable = icon ? ContextCompat.getDrawable(this, R.drawable.ic_call_24dp)
                : null;
        listItemView1.setIcon(iconDrawable);
        listItemView2.setIcon(iconDrawable);
        listItemView3.setIcon(iconDrawable);
    }

    private void onAttrCircularIconClicked() {
        circularIcon = !circularIcon;
        attributeCircularIconView.inflateMenu(circularIcon ? R.menu.uncheck_menu : R.menu.check_menu);
        listItemView1.useCircularIcon(circularIcon);
        listItemView2.useCircularIcon(circularIcon);
        listItemView3.useCircularIcon(circularIcon);
    }
}
