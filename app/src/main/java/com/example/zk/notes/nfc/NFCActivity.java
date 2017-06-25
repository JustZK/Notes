package com.example.zk.notes.nfc;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

import java.io.IOException;

public class NFCActivity extends BaseSlideCloseActivity {

    private TextView nfc_tv;

    private NfcAdapter mNfcAdapter;

    private int mCount = 0;
    private String info = "";
    private int bIndex;
    private int bCount;

    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;//过滤器
    private String[][] mTechLists;//NFC技术列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        checkNFCFunction();

        init();

        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // Setup an intent filter for all MIME based dispatches
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mFilters = new IntentFilter[]{ndef,};

        // 根据标签类型设置
        mTechLists = new String[][]{new String[]{NfcA.class.getName()}};

    }

    private void init() {
        nfc_tv = (TextView) findViewById(R.id.nfc_tv);
        findViewById(R.id.nfc_read_btn).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nfc_read_btn:
                    break;
                case R.id.nfc_write_btn:
                    break;
                case R.id.nfc_delete_btn:
                    break;
                default:
                    break;

            }
        }
    };

    private void checkNFCFunction() {
        // TODO Auto-generated method stub
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        // check the NFC adapter first
        if (mNfcAdapter == null) {
            // mTextView.setText("NFC apdater is not available");
            Dialog dialog = null;
            AlertDialog.Builder customBuilder = new AlertDialog.Builder(
                    this);
            customBuilder
                    .setTitle("很遗憾")
                    .setMessage("没发现NFC设备，请确认您的设备支持NFC功能!")
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
            dialog = customBuilder.create();
            dialog.setCancelable(false);// back
            dialog.setCanceledOnTouchOutside(false);
            SetDialogWidth(dialog).show();
            return;
        } else {
            if (!mNfcAdapter.isEnabled()) {
                Dialog dialog = null;
                AlertDialog.Builder customBuilder = new AlertDialog.Builder(
                        this);
                customBuilder
                        .setTitle("提示")
                        .setMessage("请确认NFC功能是否开启!")
                        .setPositiveButton("现在去开启......",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        Intent setnfc = new Intent(
                                                Settings.ACTION_NFC_SETTINGS);
                                        startActivity(setnfc);
                                    }
                                });
                dialog = customBuilder.create();
                dialog.setCancelable(false);// back
                dialog.setCanceledOnTouchOutside(false);
                SetDialogWidth(dialog).show();
                return;
            }
        }
    }

    private Dialog SetDialogWidth(Dialog dialog) {
        // TODO 自动生成的方法存根
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        if (screenWidth > screenHeight) {
            params.width = (int) (((float) screenHeight) * 0.875);

        } else {
            params.width = (int) (((float) screenWidth) * 0.875);
        }
        dialog.getWindow().setAttributes(params);

        return dialog;
    }


    @Override
    protected void onResume() {
        // TODO 自动生成的方法存根
        super.onResume();
        enableForegroundDispatch();
//         mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
//         mTechLists);
    }

    private void enableForegroundDispatch() {
        // TODO 自动生成的方法存根
        if (mNfcAdapter != null) {
            // 这行代码是添加调度，效果是读标签的时候不会弹出候选程序，直接用本程序处理
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
                    mFilters, mTechLists);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        disableForegroundDispatch();
    }

    private void disableForegroundDispatch() {
        // TODO 自动生成的方法存根
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO 自动生成的方法存根
        super.onNewIntent(intent);
        nfc_tv.append("发现新的 Tag:  " + ++mCount + "\n");// mCount 计数
        String intentActionStr = intent.getAction();// 获取到本次启动的action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intentActionStr)// NDEF类型
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intentActionStr)// 其他类型
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intentActionStr)) {// 未知类型

            // 在intent中读取Tag id
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] bytesId = tag.getId();// 获取id数组
            info = "";
            info += ByteArrayToHexString(bytesId);
            nfc_tv.append("标签UID:  " + info + "\n");
            nfc_tv.append("读取成功! " + readTag(tag) + "\n" + "\n");
        }
    }

    // //读取数据
    public String readTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        for (String tech : tag.getTechList()) {
            System.out.println(tech);// 显示设备支持技术
        }
        boolean auth = false;
        // 读取TAG

        try {
            // metaInfo.delete(0, metaInfo.length());//清空StringBuilder;
            StringBuilder metaInfo = new StringBuilder();
            // Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();// 获取TAG的类型
            int sectorCount = mfc.getSectorCount();// 获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;

            }
            metaInfo.append("  卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize()
                    + "B\n");
            for (int j = 0; j < sectorCount; j++) {
                // Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_NFC_FORUM);// 逐个获取密码
                /*
                 * byte[]
                 * codeByte_Default=MifareClassic.KEY_DEFAULT;//FFFFFFFFFFFF
                 * byte[]
                 * codeByte_Directory=MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY
                 * ;//A0A1A2A3A4A5 byte[]
                 * codeByte_Forum=MifareClassic.KEY_NFC_FORUM;//D3F7D3F7D3F7
                 */
                if (auth) {
                    metaInfo.append("Sector " + j + ":验证成功\n");
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo.append("Block " + bIndex + " : "
                                + ByteArrayToHexString(data)
                                + "\n");
                        bIndex++;
                    }
                } else {
                    metaInfo.append("Sector " + j + ":验证失败\n");
                }
            }
            return metaInfo.toString();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally {
            if (mfc != null) {
                try {
                    mfc.close();
                } catch (IOException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
        return null;

    }

    public static String ByteArrayToHexString(byte[] bytesId) {   //Byte数组转换为16进制字符串
        // TODO 自动生成的方法存根
        int i, j, in;
        String[] hex = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
        };
        String output = "";

        for (j = 0; j < bytesId.length; ++j) {
            in = bytesId[j] & 0xff;
            i = (in >> 4) & 0x0f;
            output += hex[i];
            i = in & 0x0f;
            output += hex[i];
        }
        return output;
    }
}
