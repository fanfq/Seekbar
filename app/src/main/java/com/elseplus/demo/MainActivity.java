package com.elseplus.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.elseplus.lib.seekbar.BidirectionalSeekBar;
import com.elseplus.lib.seekbar.NormalSeekBar;

public class MainActivity extends AppCompatActivity {

    private NormalSeekBar sb_hue;//色调

    private BidirectionalSeekBar sb_saturation;//饱和度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //色调
        sb_hue = findViewById(R.id.sb_hue);

        //饱和度
        sb_saturation = findViewById(R.id.sb_saturation);

        sb_hue.setOnProgressCallBackListener(new NormalSeekBar.CallBackListener() {
            @Override
            public void currentProgress(int progress) {
                Log.e("TAG","sb_hue"+progress);
            }
        });

        sb_hue.setProgress(20);

        sb_saturation.setOnProgressCallBackListener(new BidirectionalSeekBar.CallBackListener() {
            @Override
            public void currentProgress(int progress) {
                Log.e("TAG","sb_saturation_"+progress);
            }
        });

        sb_saturation.setProgress(-20);
    }

//    //色调进度监听,进度条从中间往两边调整
//    SeekBar.OnSeekBarChangeListener sb_hue_listener = new SeekBar.OnSeekBarChangeListener(){
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            //进度是从-50~50的,但是seekbar.getmin()有限制,所以这里用0~100 -50;
//            int text = progress -50;
//            //设置文本显示
//            txt_hue_value.setText(String.valueOf(text));
//
//            //获取文本宽度
//            float textWidth = txt_hue_value.getWidth();
//
//            //获取seekbar最左端的x位置
//            float left = seekBar.getLeft();
//
//            //进度条的刻度值
//            float max =Math.abs(seekBar.getMax());
//
//            //这不叫thumb的宽度,叫seekbar距左边宽度,实验了一下，seekbar 不是顶格的，两头都存在一定空间，所以xml 需要用paddingStart 和 paddingEnd 来确定具体空了多少值,我这里设置15dp;
//            float thumb = dip2px(MainActivity.this,15);
//
//            //每移动1个单位，text应该变化的距离 = (seekBar的宽度 - 两头空的空间) / 总的progress长度
//            float average = (((float) seekBar.getWidth())-2*thumb)/max;
//
//            //int to float
//            float currentProgress = progress;
//
//            //textview 应该所处的位置 = seekbar最左端 + seekbar左端空的空间 + 当前progress应该加的长度 - textview宽度的一半(保持居中作用)
//            float pox = left - textWidth/2 +thumb + average * currentProgress;
//            txt_hue_value.setX(pox);
//
//            //跟新图片色调
////            mHue = (progress - MID_VALUE) * 1.0f / MID_VALUE * 180;
////            mBitmap = BitmapUtils.handleImageEffect(mBitmap,mHue,mSaturation,mLum);
////            img_main.setImageBitmap(mBitmap);
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//
//        }
//    };



    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}