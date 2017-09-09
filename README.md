### What is XRadioGroup-Android?
This is a Ultra RadioGroup for Android.That mean you can do something awesome
The AOSP RadioGroup can't.

### What does XRadioGroup-Android can do?
1. Add a layout(like LinearLayout ect.) to a RadioGroup.
2. Set some item state "fixed". The "fixed" items will stay their state until be clicked.
3. Something other you want, you can open issue let me know.

### How to use it?

You can use it like this in your layout xml. 
And check the app module for the detail
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:xradio="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.jiakaiyang.xradiogroup.MainActivity">

    <com.jiakaiyang.xradiogroup.lib.groups.XLinearRadioGroup
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/bg_item"
            android:clickable="true"
            android:orientation="horizontal"
            xradio:checked="true">

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:text="John Lennon"
                android:textColor="@android:color/white" />

        </com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem>

        <com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/bg_item"
            android:clickable="true"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:text="Paul McCartney"
                android:textColor="@android:color/white" />

        </com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem>

        <com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/bg_item"
            android:clickable="true"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:text="George Harrison"
                android:textColor="@android:color/white" />

        </com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem>

        <com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/bg_item"
            android:clickable="true"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@mipmap/ic_launcher" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:text="Ringo Starr"
                android:textColor="@android:color/white" />

        </com.jiakaiyang.xradiogroup.lib.items.XLinearRadioItem>

    </com.jiakaiyang.xradiogroup.lib.groups.XLinearRadioGroup>

</FrameLayout>

```

### Change Log
v0.9 
1. Add base function for XRadioGroup.
2. Add LinearLayout implement for XRadioGroup and XRadioItem.