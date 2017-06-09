package com.example.zk.notes.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

public class DrawableActivityTwo extends BaseSlideCloseActivity {

    private static final String TAG = "DrawableActivityTwo";
    private ImageView drawable_two_iv;
    private Bitmap mBitmap;
    private EditText drawable_two_et;
    private Spinner drawable_two_sp_size, drawable_two_sp_align;
    private float mTextSize;
    private int mTextAlign;
    private MarkDrawable drawable;
    private float[] mSizeArray = {50f, 40f, 30f};
    private String[] mSizeDescArray = {"大", "中", "小"};
    private int[] mAlignArray = {MarkDrawable.ALIGN_TOP, MarkDrawable.ALIGN_CENTER, MarkDrawable.ALIGN_BOTTOM};
    private String[] mAlignDescArray = {"顶部对齐", "居中对齐", "底部对齐"};
    private ArrayAdapter<String> sizeAdapter;
    private ArrayAdapter<String> alignAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_two);

        init();
    }

    private void init(){
        findViewById(R.id.drawable_two_btn).setOnClickListener(mOnClickListener);

        drawable_two_iv = (ImageView) findViewById(R.id.drawable_two_iv);
        drawable_two_et = (EditText) findViewById(R.id.drawable_two_et);
        drawable_two_sp_size = (Spinner) findViewById(R.id.drawable_two_sp_size);
        drawable_two_sp_align = (Spinner) findViewById(R.id.drawable_two_sp_align);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.xiaohuangren);
        drawable = new MarkDrawable(getResources(), mBitmap);

        drawable_two_et.setText("这是水印文字");

        sizeAdapter = new ArrayAdapter<>(this, R.layout.item_drawable_activity_two_spinner, mSizeDescArray);
        alignAdapter = new ArrayAdapter<>(this, R.layout.item_drawable_activity_two_spinner, mAlignDescArray);

        sizeAdapter.setDropDownViewResource(R.layout.item_drawable_activity_two_spinner_dropdown);
        drawable_two_sp_size.setPrompt("请选择文本大小");
        drawable_two_sp_size.setAdapter(sizeAdapter);
        drawable_two_sp_size.setOnItemSelectedListener(mSizeSelectedListener);
        drawable_two_sp_size.setSelection(1);

        alignAdapter.setDropDownViewResource(R.layout.item_drawable_activity_two_spinner_dropdown);
        drawable_two_sp_align.setPrompt("请选择对齐方式");
        drawable_two_sp_align.setAdapter(alignAdapter);
        drawable_two_sp_align.setOnItemSelectedListener(mAlignSelectedListener);
        drawable_two_sp_align.setSelection(0);


    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.drawable_two_btn:
                    showMark();
                    break;
                default:
                    break;
            }
        }
    };

    private AdapterView.OnItemSelectedListener mSizeSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mTextSize = mSizeArray[position];
            showMark();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private AdapterView.OnItemSelectedListener mAlignSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            mTextAlign = mAlignArray[position];
            showMark();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void showMark() {
        drawable = new MarkDrawable(getResources(), mBitmap);
        drawable.setTextSize(mTextSize);
        drawable.setTextAlign(mTextAlign);
        drawable.setText(drawable_two_et.getText().toString());
        drawable_two_iv.setImageDrawable(drawable);
    }

}
