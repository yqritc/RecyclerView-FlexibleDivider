# RecyclerView-FlexibleDivider
Android library providing simple way to control divider items of RecyclerView


# Gradle (not ready yet)
```
repositories {
    jcenter()
}

dependencies {
    compile 'com.yqritc:recyclerview-flexibledivider:1.0.0'
}
```

# Usage

The following is the simplest usage.
Drawing a divider drawable retrived from android.R.attr.listDivider between each cell.
```
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this))
```

If you want to set color, size and margin values 
You can specify as the followings.
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

If you want to cusomize divider depending on the position, implement the following interfaces.

### List of provider
The following providers can be implemented and controllable for each divider drawn between cells.

- ColorProvider  
If non of color is specified, default divider retrieved from android.R.attr.listDivider will be used.

- SizeProvider  
Provide height for horizontal divider, width for vertical divider.

- VisibilityProvider  
Enables you to control the visibiity of dividers.

- MarginProvider for horizontal divider (vertical list)  
Enables you to specify left and right margin of divider.

- MarginProvider for vertical divider (horizontal list)  
Enables you to specify top and bottom margin of divider.


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
