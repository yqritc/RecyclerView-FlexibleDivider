# RecyclerView-FlexibleDivider
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RecyclerView--FlexibleDivider-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1418)  

Android library providing simple way to control divider items of RecyclerView

 ![Simple Divider](/sample/sample1.gif) ![Complex Divider](/sample/sample2.gif)

# Release Note

[Release Note] (https://github.com/yqritc/RecyclerView-FlexibleDivider/releases)

# Gradle
```
repositories {
    jcenter()
}

dependencies {
    compile 'com.yqritc:recyclerview-flexibledivider:1.0.1'
}
```

# Usage

The following is the simplest usage.
Drawing a divider drawable retrieved from android.R.attr.listDivider between each cell.
```
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this))
```

If you want to set color, size and margin values, you can specify as the followings.
```
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
recyclerView.addItemDecoration(
        new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.RED)
                .size(getResources().getDimensionPixelSize(R.dimen.divider))
                .margin(getResources().getDimensionPixelSize(R.dimen.leftmargin),
                        getResources().getDimensionPixelSize(R.dimen.rightmargin))
                .build());
```


Instead of setting color and size, you can set paint object.
```
Paint paint = new Paint();
paint.setStrokeWidth(5);
paint.setColor(Color.BLUE);
paint.setAntiAlias(true);
paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
recyclerView.addItemDecoration(
        new HorizontalDividerItemDecoration.Builder(this).paint(paint).build());
```

Also 9patch drawable can be used for drawing divider.
```
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
        .drawable(R.drawable.sample)
        .size(15)
        .build());
```

If you want to customize divider depending on the position, implement the following interfaces.

### List of provider
The following providers can be implemented and controllable for each divider drawn between cells.

- ColorProvider
Provide color for divider

- PaintProvider
Provide paint object for divider line to draw.

- DrawableDivider
Provide drawable object for divider line

- SizeProvider
Provide height for horizontal divider, width for vertical divider.

- VisibilityProvider  
Enables you to control the visibility of dividers.

- MarginProvider for horizontal divider (vertical list)  
Enables you to specify left and right margin of divider.

- MarginProvider for vertical divider (horizontal list)  
Enables you to specify top and bottom margin of divider.

### Note
- When neither of color, paint, drawable is set, default divider retrieved from android.R.attr.listDivider will be used.
- When you set Paint, you must use setColor and setStrokeWidth methods of paint class.


# License
```
Copyright 2015 yqritc

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
