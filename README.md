# glyphly

Attempting to create an app using the [Nothing Glyph Developer Kit](https://github.com/Nothing-Developer-Programme/Glyph-Developer-Kit).

Will have to overcome quite a number of challenges considering I don't have experience with Android Development, nor tbh Java/Kotlin either

### Demo

https://github.com/arushsharma24/glyphly/assets/46960231/74010798-4b2a-4ae2-a884-b73fb2bf821e

### App screenshot
<img src="https://github.com/arushsharma24/glyphly/assets/46960231/495ef90e-cdc7-45ca-b8a8-ae44c82bfed8.png" width="270" height="600">

### Plans
 - [ ] Build the app to a usable apk (get API key first)
 - [ ] Figure out the workings/shortcomings of the toggle, animate etc functions.
 - [x] Attempt to control a glyph from the app
 - [x] Import the Glyphs SDK properly and connect to the Glyphs service.
 - [x] Build an android app and run it on my device

##### Newbie guide
I plan on adding the steps that I needed to learn from the beginning as a newbie so that can serve as a guide to someone who might suffer from the same difficulties that I am.

1. Create a new project in Android Studio (install if you haven't already). I started with the Empty Activity template. 
2. Choose Java as the language (no particular reason except that the Glyph developer kit's README has examples in Java), and API 34.
3. Import the following nothing packages
```java
// import nothing packages
import com.nothing.ketchum.Common;
import com.nothing.ketchum.GlyphException;
import com.nothing.ketchum.GlyphFrame;
import com.nothing.ketchum.GlyphManager;
```
4. We will define class level functions for 
5. Now, the `onCreate()` function is the function that is run first in the MainActivity class. In this, we are going to do the following things:
```java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        // not sure what this does
        
        setContentView(R.layout.activity_main); 
        // does something called "expanding" the XML
        
        init(); 
        // see below
        
        mGM = GlyphManager.getInstance(getApplicationContext()); 
        // mGM is the Glyph Manager object, which we use to communicate with the Glyphs and give it instructions using it's functions such as toggle, animate, etc.
        
        mGM.init(mCallback); 
        // initialize the Glyph manager using the defined callback function in which we register the device, and if successful, open a session.
        
        initView();
        // this is a function I created, it's where I am putting the code for what happens on the user's interaction with the view
```
The init function can be defined as follows, as demonstrated in Example 1 of the README of the Glyph developer kit.
```java
    private void init() {
        mCallback = new GlyphManager.Callback() {
            @Override
            public void onServiceConnected(ComponentName componentName) {
                if (Common.is20111()) mGM.register(Common.DEVICE_20111);
                if (Common.is22111()) mGM.register(Common.DEVICE_22111);
                if (Common.is23111()) mGM.register(Common.DEVICE_23111);
                try {
                    mGM.openSession();
                } catch(GlyphException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mGM.closeSession();
            }
        };
    }
```
Once we connect the user's interaction with the view (a button in my case) by adding an eventListener to it, and then defining the next behaviour. 

To be continued. Will add the steps to interact with the glyphs here next.
