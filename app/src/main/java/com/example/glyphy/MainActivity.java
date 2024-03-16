package com.example.glyphy;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// import nothing packages
import com.nothing.ketchum.Common;
import com.nothing.ketchum.GlyphException;
import com.nothing.ketchum.GlyphFrame;
import com.nothing.ketchum.GlyphManager;

public class MainActivity extends AppCompatActivity {
    private GlyphManager mGM = null;
    private GlyphManager.Callback mCallback = null;
    private Button runGlyphBtn = null;
    private Button turnOffGlyphBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Starting app");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Expanded main xml");
        init();
        System.out.println("Defined callback");
        mGM = GlyphManager.getInstance(getApplicationContext());
        System.out.println("Got an instance of GlyphManager");
        mGM.init(mCallback);
        System.out.println("Called the callback");
//        channelCBtn = findViewById(R.id.channelCBtn);
        initView();
        System.out.println("Init view done");
    }

    @Override
    public void onDestroy() {
        try {
            mGM.closeSession();
        } catch (GlyphException e) {
            Log.e(TAG, e.getMessage());
        }
        mGM.unInit();
        super.onDestroy();
    }

    private void init() {
        mCallback = new GlyphManager.Callback() {
            @Override
            public void onServiceConnected(ComponentName componentName) {
                if (Common.is20111()) {
                    mGM.register(Common.DEVICE_20111);
                    System.out.println("Phone 1");
                }
                if (Common.is22111()) {
                    mGM.register(Common.DEVICE_22111);
                    System.out.println("Phone 2");
                }
                if (Common.is23111()) {
                    mGM.register(Common.DEVICE_23111);
                    System.out.println("Phone 2a");
                }
                try {
                    mGM.openSession();
                } catch(GlyphException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                try {
                    mGM.closeSession();
                } catch (GlyphException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
    private void initView() {
        runGlyphBtn = findViewById(R.id.action1);
        turnOffGlyphBtn = findViewById(R.id.turnOff);
        runGlyphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runGlyphs(view);
            }
        });
        turnOffGlyphBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view) {
                turnOffGlyphs(view);
            }
        });
    }
    public void runGlyphs(View view) {
        System.out.println("Run glyphs now");
        GlyphFrame.Builder builder = mGM.getGlyphFrameBuilder();
        // to select multiple channels, chain the commands.
        GlyphFrame frame1 = builder.buildChannel(24).buildChannel(25).buildPeriod().build();
        mGM.toggle(frame1);
        // Observations:-
        // 1. mGM.animate does not seem to have any effect. This part of the code is run and shows in the logs but the lights do not turn on.
        // 2. mGM.toggle works in a very limited way, pressing the button turns selected frames on but clicking the button again does not work.
        // 3. mGM.turnOff() seems to work as expected
        // 4. buildPeriod(2000) doesn't seem to work, lights do not turn off after 2s
        System.out.println("Animation should be done by now");
    }

    public void turnOffGlyphs(View view){
        System.out.println("Turning off glyphs now");
        GlyphFrame.Builder builder = mGM.getGlyphFrameBuilder();
        mGM.turnOff();
    }
}
