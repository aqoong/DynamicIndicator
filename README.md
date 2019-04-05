# DynamicIndicator

![Alt Text](https://github.com/aqoong/DynamicIndicator/raw/master/readme_images/sample.gif)

[Custom View]
using .xml file

## How to Use
- xml
  - [attrs.xml]
  ```
    <attr name="maxSize" format="integer"/>
    <attr name="android:entries"/>
    <attr name="mode" format="flags">
        <flag name="dot" value="10001"/>
        <flag name="line" value="10002"/>
    </attr>
    <attr name="iconOff" format="reference"/>   <!-- have a higher priority than color -->
    <attr name="iconOn" format="reference"/>    <!-- have a higher priority than color -->
    <attr name="colorOff" format="color"/>      <!-- line, dot -->
    <attr name="colorOn" format="color"/>       <!-- line, dot -->
    <attr name="itemWidth" format="dimension"/>
    <attr name="strTraceEnable" format="boolean"/>
  ```
  
 
  - [.xml]
  ```
  <com.aqoong.lib.dynamicindicator.DynamicIndicator
    android:id="@+id/dynamic_indicator"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    app:maxSize="5"
    app:itemWidth="70dp"
    app:strTraceEnable="false"
    app:mode="dot"
    app:colorOff="@color/indicator_off"
    app:colorOn="@color/indicator_on">
  </com.aqoong.lib.dynamicindicator.DynamicIndicator>
  ```

  
  - [.java]
  ```
    DynamicIndicator dynamicIndicator;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		    //connect
        dynamicIndicator = findViewById(R.id.dynamic_indicator);
		    //Select ver.1
        dynamicIndicator.selectDot(position);
		    //Select ver.2
		    dynamicIndicator.selectDot(position, "Custom Text Label");
        ///////////////////////////////////////////////
        //if you want change using java source
        dynamicIndicator.setItemWidth({int})
                .setStringTraceMode({boolean})
                .setMode({boolean})
                .setMaxSize({int})
                .setTextList({String[]})
                .setColumnCount({int});		//last
        //Draw a newly set object.
        dynamicIndicator.refreshIndicator();
    }
    ```
## Add DynamicIndicator Lib

  - project build.gradle
  ```
    allprojects {
      repositories {
        google()
        jcenter()
	...
        maven { url "https://jitpack.io"}
	...
      }
    }
  ```
  - app build.gradle [![release](https://img.shields.io/badge/version-1.0.2-yellow.svg)](https://semver.org)
  ```
    dependencies {
      ...
      implementation 'com.github.aqoong:DynamicIndicator:x.y.z'
      ...
    }
  ```
