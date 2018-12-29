## Introduction

This project is a App for Android, developed in the Android Studio.

This first version is simple, it show a list of upcoming movies, provided by [TMDB](https://www.themoviedb.org/)

## Configuration

Inside the file [strings.xml](https://github.com/cristianoborgescardoso/codechallenge/blob/master/app/src/main/res/values/strings.xml) you will note a stretch, as below

```xml
<!--Country and Language-->
    <bool name="use_default_language_and_region" translatable="false">true
    </bool> <!--region == country-->
    <!--if (use_default_language_and_region == true), otherwise the language and region will be acquire from the operational system-->
    <string name="default_region" translatable="false">US</string> <!-- BR, US, ES ...-->
    <string name="default_language" translatable="false">en-US</string> <!-- pt-BR, en-US, es-ES ...-->
```

if you set the variable *use_default_language_and_region* as *true* the Language and Region will be acquired from the Operational System, otherwise, this app will use the Region and Language defined at the subsequent variables.

## Libraries:

In this project we added some libraries, such as:

	//To load data from WebService
    'com.squareup.retrofit2:retrofit:2.5.0'
    
	//To serialize JSON to Java Obj
    'com.squareup.retrofit2:converter-gson:2.5.0'
    'com.google.code.gson:gson:2.8.2'
	
	//To load image from url
    'com.squareup.picasso:picasso:2.71828'
    
	//Paging from Android JetPack - To Manage RecyclerView Items
    "android.arch.paging:runtime:1.0.1"
    "android.arch.lifecycle:extensions:1.1.1"
    'com.android.support:design:28.0.0'
	
	//To generate toString() 
	'org.apache.commons:commons-lang3:3.6'
