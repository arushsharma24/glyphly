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
        runGlyphBtn = findViewById(R.id.runGlyphBtn);
        runGlyphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runGlyphs(view);
            }
        });
    }
    public void runGlyphs(View view) {
        System.out.println("Run glyphs now");
        GlyphFrame.Builder builder = mGM.getGlyphFrameBuilder();
        GlyphFrame frame = builder.buildChannel(24).build();
        mGM.toggle(frame);
        System.out.println("Animation should be done by now");
    }
}
