# ListItemView
[![Build Status](https://travis-ci.org/lurbas/ListItemView.svg?branch=master)](https://travis-ci.org/lurbas/ListItemView)

Implementation of List Item from Material Design [guidelines](https://material.io/guidelines/components/lists.html#lists-specs).

### Screenshot
![](https://github.com/lurbas/ListItemView/blob/master/readme/cover.png)

### Usage
```sh
<com.lucasurbas.listitemview.ListItemView
        android:id="@+id/list_item_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:liv_title="@string/title"
        app:liv_subtitle="@string/subtitle"
        app:liv_icon="@drawable/ic_call_24dp"
        app:liv_menu="@menu/single_action_menu"
        app:liv_multiline="false"
        app:liv_displayMode="icon"/>
```

#### Action Menu
Right action icon is configured from xml menu file similar to toolbar configuration.
```sh
app:liv_menu="@menu/multiple_action_menu"
```
xml file
```sh
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/action_heart"
        android:icon="@drawable/ic_favorite_24dp"
        android:title="@string/action_heart"
        app:showAsAction="always" />
    <item
        android:id="@+id/action_info"
        android:icon="@drawable/ic_info_24dp"
        android:title="@string/action_info"
        app:showAsAction="ifRoom" />
    <item
        android:id="@+id/action_remove"
        android:title="@string/action_remove"
        app:showAsAction="never" />
</menu>
```
add onClick listener
```sh
listItemView.setOnMenuItemClickListener(new ListItemView.OnMenuItemClickListener() {
        @Override
        public void onActionMenuItemSelected(final MenuItem item) {
            // click
        }
    });
```

#### Padding and Keyline
```sh
app:liv_paddingStart="@dimen/padding_start"
app:liv_paddingEnd="@dimen/padding_end"
app:liv_keyline="@dimen/keyline"
```
By default `liv_paddingStart` and `liv_paddingEnd` are set to **16dp** on mobile and **24dp** on tablet.
Keyline `liv_keyline` is a line to which text will be offset when icon or avatar is present. Default value is **72dp** on mobile and **80dp** on tablet.
The view is RTL ready.

It is possible to offset text without setting icon, by using **liv_forceKeyline** flag
```sh
app:liv_forceKeyline="true"
```
![](https://github.com/lurbas/ListItemView/blob/master/readme/keyline.png)

#### Circular Icon
```sh
app:liv_displayMode="circularIcon"
```
![](https://github.com/lurbas/ListItemView/blob/master/readme/circular_icon.png)

#### Avatar
```sh
app:liv_displayMode="avatar"
```
next you can get the `ImageView` and download into it an image using library of your choice (Picasso, Glide, etc).
<pre>
Picasso.with(context)
        .load(avaratUrl)
        .placeholder(R.drawable.placeholder)
        .transform(new CropCircleTransform())
        .into(<b>listItemView.getAvatarView()</b>);
</pre>

#### Styling

Title color uses `?android:textColorPrimary` and Subtitle uses `?android:textColorSecondary`. All icons by default are also tinted
with `?android:textColorSecondary` color. This means view can handle respectfully light and dark theme.

To change icons color use accordingly:
```sh
app:liv_iconColor="@color/icon_color"
app:liv_circularIconColor="@color/icon_color"
app:liv_menuActionColor="@color/icon_color"
app:liv_menuOverflowColor="@color/icon_color"
```

### Download (JCenter)
```sh
dependencies {
    compile 'com.lucasurbas:listitemview:1.0.4'
}
```

### License
```sh
Copyright 2017 Lucas Urbas

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
