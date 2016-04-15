News
======

- Apr. 13, 2016.  [FIRST DEMO!!](https://sourceforge.net/projects/itsnat/files/itsnat_droid_demo/apptest-debug-0.5.0.0.1.apk) and the associated [ItsNat Library](https://sourceforge.net/projects/itsnat/files/itsnat_droid/itsnatdroid-debug-0.5.0.0.1.aar) 
- Jul. 16, 2015.  Article about native vs hybrid with a reference to ItsNat Droid [Native Mobile vs. Hybrid Mobile: The Eternal Question](https://dzone.com/articles/native-mobile-vs-hybrid-mobile-itsnat-droid).

ItsNat Droid Client SDK
======

ItsNat Droid Client SDK is a Java based Android development library to develop dynamic Android applications on demand downloading native Android UI layouts similar to the web paradigm.

Downloads: Demo and SDK library
------

[DEMO](https://sourceforge.net/projects/itsnat/files/itsnat_droid_demo/apptest-debug-0.5.0.0.1.apk) : the application used to test ItsNat Droid, all features are used here.

[ItsNat Library](https://sourceforge.net/projects/itsnat/files/itsnat_droid/itsnatdroid-debug-0.5.0.0.1.aar) : the library to be includes in your application providing a public and simple API.

Requirement: Android +4.0.3

The source code of the server side (an ItsNat based web application) [is here](https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev)

What is ItsNat Droid?
------

ItsNat Droid makes possible to use Android following the web paradigm but using only native Android APIs and UIs.

ItsNat Droid was born to compete with PhoneGap providing similar approaches but using native Android layouts/resources and Beanshell as scripting language instead of clumsy
HTML/CSS/JavaScript trying to simulate native components and behavior.

It tries to break the "Amazon shop app dilemma": fully exploit of Android native UI capabilities vs UI flexible maintenance in server without app upgrade (just to change a color)
using web tech.

http://www.theserverside.com/news/2240174316/How-Amazon-discovered-hybrid-HTML5-Java-Android-app-development

*"the most compelling reason to incorporate HTML5 into your mobile applications is to get the ability to update the app without requiring an upgrade on the device user's side.
This capability makes it both easier and safer to manage apps -- permitting developers to roll out or draw back updates as needed. In the brave new world of continuous deployment
and live testing, that's a huge advantage"*

ItsNat Droid breaks the "impossibility" of loading native Android resources (layouts, drawables, animations...) dynamically:

http://stackoverflow.com/questions/6575965/theoretical-question-load-external-xml-layout-file-in-android
http://stackoverflow.com/questions/18641798/looking-for-xmlresourceparser-implementation


Beanshell, "scripting Java < 5" with some extensions (like using var as in JavaScript),  is used for scripting, because it is a JVM based language has a direct and tight
integration with Android native APIs without an explicit API provided by the framework, that is, through Beanshell scripts you can call any Java code of your application as it
was normal Java native code, and the reverse is true, you can access Beanshell objects and methods from Java native code. By this way you can decide how much native Java code
"built-in" functionality is in your application to get the max speed and how much is in Beanshell (slower) without a pre-build rigid bridge necessary in Android WebView
(in a WebView the Java API able to be called by JavaScript must be previously declared through Java interfaces method to method) used in HTML/CSS/JavaScript mobile solutions.

ItsNat Droid makes a big effort to mimic the Android conventions to build Android applications specifically UI but extended and moved to a remote side, where Android resources are delivered,
events are received following using a sort of AJAX, behaviour is transported to client as scripts and many actions can be executed in server side including remote UI manipulation. 
As you can see later, the level of remote UI and behaviour is up to you (from almost fully remote to something remote).


Interoperatibility levels
------

In spite of ItsNat Droid Client contains the name "ItsNat", it can be used outside ItsNat server.

ItsNat Droid client has several levels of interoperatibility:

1. Parsing a XML file containing an Android layout, this XML can be read from any source by you (assets folder is recommended). Dependent native resources and/or resources in "asset" directory are loaded when specified in layout.

    The application is fully local according to UI but organization based on assets provides more flexibility. Folders and file organization in `assets/` folder can be similar
    to the standard `res/` folder but this is not mandatory.

    Take a look to this layout example in `assets/` folder:
   
    https://github.com/jmarranz/itsnat_droid/blob/master/apptest/src/main/assets/res/layout/test_local_layout_asset_1.xml

    Take a look to references like `@assets:layout/res/layout/test_include_2_asset.xml` or `@assets:layout/res/values/test_values_asset.xml:test_layout_include_3`.
    The prefix `@assets:` indicates ItsNat Droid must search the required resource into the folder `assets/`, as you can see native references to standard `res/` and android O.S. 
    provided resources are possible. The second example `@assets:layout/res/values/test_values_asset.xml:test_layout_include_3` access to the `test_values_asset.xml` file
    containing a `<resources></resources>` XML locating the specified variable `test_layout_include_3`.  
      

2. Built-in "page" system similar to the web paradigm of page, instead an HTML page you download an Android layout in XML form, Android styling is possible and can be defined remotely instead
 of CSS. By using a simple API you can download remote native Android layouts from ANY web server and be able of doing Single Page Interface with Android layouts.

   Take a look to this very simple, raw and ugly example of a SPI application web server agnostic.

   https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/java/test/ItsNatDroidServletNoItsNat.java

   As you can see &lt;script&gt; elements containing Beanshell scripts are an extension to Android layouts similar to JavaScript. Beanshell is a sort of Java interpreted, 
   Beanshell code can directly access to native Java APIs, there is no need to define custom "bridges" like in JavaScript-WebView.
   
   There are some other extensions like using the "id" standard attribute as alternative to android:id (valid also).

  Take a look to resource references like `@remote:drawable/droid/res/drawable/test_nine_patch_remote.xml` 

3. Total integration with ItsNat server.

  Yes, Android layout is a XML format so can be managed as DOM by ItsNat server. Everything is said, you can programming server centric stateful or stateless Android applications
  the same way you're used with ItsNat Web. This is not totally new for you because ItsNat Web already supports raw XML, XUL and SVG layouts including events. The main difference
  is you send Beanshell code instead JavaScript in your &lt;script&gt; elements and addCodeToSend() APIs, and Android client events may be sent to server converted to DOM events
  (you decide what events are processed in server side).

  For instance (layout and layout fragment) in a web server:

  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/WEB-INF/pages/droid/test/test_droid_core.xml
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/WEB-INF/pages/droid/test/fragment/test_droid_core_and_stateless_fragment.xml

  You can find resource references like this `@remote:dimen/droid/res/values/test_values_remote.xml:test_dimen_layout_height` there is no much to explain, the referenced resource
   is the remote archive `test_values_remote.xml` (expected a `<resources></resources>` format) and read the variable `test_dimen_layout_height`.

  Some server side code snippets manipulating layouts:

  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/java/test/droid/core/TestDroidFragmentInsertionInnerXML.java
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/java/test/droid/core/TestDroidFragmentInsertionUsingDOMAPI.java
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/java/test/droid/core/TestDroidToDOM.java

  Do you need Drawables?
  
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/WEB-INF/pages/droid/test/test_droid_remote_drawables.xml 
  
  Some examples of drawable XML declarations (linked in the previous layout example):
  
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/droid/res/drawable/test_gradient_drawable_remote.xml
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/droid/res/drawable/test_layer_drawable_remote.xml

  Yes inside XML resources you can reference other remote XML resources like in `@remote:drawable/list_selector_background_focused_light_remote.9.png`    
  
  Do you need animations?
  
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/WEB-INF/pages/droid/test/test_droid_remote_animations.xml
 
  Some examples of animations in XML (linked in the previous layout example):  
  
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/droid/res/animator/test_object_animator_remote.xml
  https://github.com/jmarranz/itsnat_server/blob/master/itsnat_dev/src/main/webapp/droid/res/animator/test_value_animator_remote.xml
  
  Do you remember the powerful Remote Control capability of ItsNat Web? Yes you can do the same with Android, another Android device can be monitor an Android layout of another
  device (with permission of course), most of non-web stuff of ItsNat server is supported.

  There is no "ItsNat modes" (web/Android) in ItsNat server, Android layouts can coexist in the same web application with Web layouts, the main difference is when an Android layout
  (registered with MIME android/layout) is requested  ItsNat manages this type with some differences regarding to web layouts (all of them using JavaScript).


Finally as you can see ItsNat Droid allows construction of remote Android applications with different levels of remoting, from almost fully remote to some stuff remote. The assets
alternative was initially designed to help testing, but it can be used when res/ standard format is too rigid. 
  

On development
------

ItsNat Droid Client SDK (and server side code in ItsNat Server) is on heavy development, there is no release in spite you can download a demo and play or build from source code using Android Studio (master branch)

The published app demo (apptest) points to the server http://www.innowhere.com/itsnat_dev/ . Android XML layouts are generated using a MIME not recognized by web browsers. 

Source code:

https://github.com/jmarranz/itsnat_droid

Development happens on the development branch, this is the branch to get the latest:

https://github.com/jmarranz/itsnat_droid/tree/development

Android client code is Java 6 compatible and supports Android +4.0.3 devices, developed with Android Studio

You can play with the "apptest" Android application with many visual tests (also there're a lot of tests in code), no you're not going to find here something pretty, just
systematic testing. You must configure the URL of your ItsNat server (when loading this app click the Back button of your device for URL configuration, save your URL and then
click on "GO TO TESTS" button). Some examples like "TEST LOCAL X" are local and can run without ItsNat server, the key to understand these tests is by clicking "RELOAD" button
you get the same layout but processed dynamically (same files as compiled XMLs but as asset files).

All said before is already done and running like layouts (including <include> tag support) drawables and some work on animations is done, but ambitious new things are pending like be able of download and all types of Android resources 
and higher level components attached to DOM elements for easier management like components in ItsNat Web.

License of ItsNat Droid Client (ItsNat server is LGPL v3)
------

[Apache v2](LICENSE-2.0.txt)

Nomenclature
------

Because there is a new kid on the block, new names arise to clarify:

- ItsNat Server: when referring only to the server part of ItsNat.
- ItsNat Droid: when referring to client and Droid part of ItsNat server for Android programming
- ItsNat Droid Client: when referring only to the Android library for development Android applications based on ItsNat
- ItsNat Web: when referring only to web (raw XML/HTML/SVG/XUL + JavaScript) development, not Android.

Specific Google Group
------

For ItsNat Droid only stuff, a new Goggle Group has been created:

https://groups.google.com/forum/#!forum/itsnat-droid

For Droid only discussions use this specific group.

Articles
------

- Jul. 16, 2015.  Article about native vs hybrid with a reference to ItsNat Droid [Native Mobile vs. Hybrid Mobile: The Eternal Question](https://dzone.com/articles/native-mobile-vs-hybrid-mobile-itsnat-droid).
