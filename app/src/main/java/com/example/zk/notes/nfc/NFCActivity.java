package com.example.zk.notes.nfc;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zk.notes.R;
import com.example.zk.notes.slidingpanelayout.BaseSlideCloseActivity;

import java.io.IOException;
import java.nio.charset.Charset;

public class NFCActivity extends BaseSlideCloseActivity {

    private int a;

    private TextView nfc_tv;
    private EditText nfc_edt_write;

    private NfcAdapter mNfcAdapter;

    private int mCount = 0;
    private String info = "";
    private int bIndex;
    private int bCount;

    private RadioButton nfc_rb_read, nfc_rb_write, nfc_rb_delete;

    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;//过滤器
    private String[][] mTechLists;//NFC技术列表

    private byte[]  b0;
    byte[] code=MifareClassic.KEY_DEFAULT;//读写标签中每个块的密码
    int block[] = { 4, 5, 6, 8, 9, 10, 12, 13, 14, 16, 17, 18, 20, 21, 22, 24,
            25, 26, 28, 29, 30, 32, 33, 34, 36, 37, 38, 40, 41, 42, 44, 45, 46,
            48, 49, 50, 52, 53, 54, 56, 57, 58, 60, 61, 62 };

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
        nfc_edt_write = (EditText) findViewById(R.id.nfc_edt_write);
        nfc_rb_read = (RadioButton) findViewById(R.id.nfc_rb_read);
        nfc_rb_write = (RadioButton) findViewById(R.id.nfc_rb_write);
        nfc_rb_delete = (RadioButton) findViewById(R.id.nfc_rb_delete);
        findViewById(R.id.nfc_btn).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.nfc_btn:
                    nfc_tv.setText("");
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
        String intentActionStr = intent.getAction();// 获取  到本次启动的action
        Toast.makeText(this, "检测到nfc", Toast.LENGTH_LONG)
                .show();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intentActionStr)// NDEF类型
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(intentActionStr)// 其他类型
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intentActionStr)) {// 未知类型

            // 在intent中读取Tag id
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] bytesId = tag.getId();// 获取id数组
            info = "";
            info += ByteArrayToHexString(bytesId);
            nfc_tv.append("标签UID:  " + info + "\n");

            if (nfc_rb_read.isChecked()) {
                nfc_tv.append(readTag(tag) + "\n");
            }else if(nfc_rb_write.isChecked()){
                writeTag(tag,nfc_edt_write.getText().toString().trim());
            }else if(nfc_rb_delete.isChecked()){
            }
        }
    }

    private String readTag(String Type, Tag tag){
        String str = "";
        if (tag != null) {
            //解析Tag获取到NDEF实例
            NfcA nfcA = NfcA.get(tag);
            //打开连接
            try {
                nfcA.connect();
                byte[] mAtqa = nfcA.getAtqa();
                str += "nfcA.getAtqa()  :"+ ByteArrayToHexString(mAtqa) + "\n";
                str += "nfcA.getMaxTransceiveLength()  :" + nfcA.getMaxTransceiveLength() + "\n";
                str += "nfcA.getSak()  :" + nfcA.getSak() + "\n";
                str += "nfcA.getTimeout()  :" + nfcA.getTimeout() + "\n";
                str += "nfcA.getTag()  :" + nfcA.getTag() + "\n";
                str += "nfcA.toString()  :" + nfcA.toString() + "\n";
                byte[] cmd =new byte[]{(byte) 0x60,(byte) 0x08,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff};
                str+="Card Number  :"+nfcA.transceive(cmd) + "\n";


                nfcA.close();
            } catch (IOException e) {
                str+="Card Number  : ERROR \n";
                e.printStackTrace();
            }
        } else {
            Toast.makeText(NFCActivity.this, "设备与nfc卡连接断开，请重新连接...",
                    Toast.LENGTH_SHORT).show();
        }
        return str;
    }

    // 读取方法
    private String read(Tag tag) throws IOException, FormatException {
        if (tag != null) {
            //解析Tag获取到NDEF实例
            Ndef ndef = Ndef.get(tag);
            //打开连接
            ndef.connect();
            //获取NDEF消息
            NdefMessage message = ndef.getNdefMessage();
            //将消息转换成字节数组
            byte[] data = message.toByteArray();
            //将字节数组转换成字符串
            String str = new String(data, Charset.forName("UTF-8"));
            //关闭连接
            ndef.close();
            return str;
        } else {
            Toast.makeText(NFCActivity.this, "设备与nfc卡连接断开，请重新连接...",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
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
            metaInfo.append("卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
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
                if (!auth) {
                    auth = mfc.authenticateSectorWithKeyA(j,
                            MifareClassic.KEY_MIFARE_APPLICATION_DIRECTORY);
                }

                if (!auth) {
                    auth = mfc.authenticateSectorWithKeyA(j,
                            MifareClassic.KEY_DEFAULT);
                }

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

    public String ByteArrayToHexString(byte[] bytesId) {   //Byte数组转换为16进制字符串
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

    /**
     * byte[] 转中文
     */
    private String getChinese(byte[] b) throws IOException  {
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            res.append(Integer.toBinaryString(b[i] & 0xff) + " ");
        }
        String[] strArr = res.toString().split(" ");

        byte[] bb = new byte[strArr.length];

        for (int j = 0; j < bb.length; j++) {
            bb[j] = (byte) Integer.parseInt(strArr[j], 2);
        }
        return (new String(bb, "UTF-8"));
    }

    /**
     * <把字节数组封装成的字符串转换成原来的字符串>
     * <功能详细描述>
     * @param stc
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String stringToChinese(String stc)
    {
        // 如果传递的字符串为空则直接返回空
        if (stc.equals(""))
        {
            return "";
        }
        else
        {
            // 分割字符串
            String[] s = stc.split("@");
            if (s.length > 0)
            {
                // 循环构造BYTE数组
                byte[] b = new byte[s.length];
                for (int i = 0; i < s.length; i++)
                {
                    b[i] = (byte)Integer.parseInt(s[i]);
                }

                // 根据BYTE数组构造字符串
                return new String(b);
            }
            else
            {
                return "";
            }
        }
    }

    // 写数据
    public void writeTag(Tag tag, String str) {
        MifareClassic mfc = MifareClassic.get(tag);

        try {
            if (mfc != null) {
                mfc.connect();
            } else {
                Toast.makeText(NFCActivity.this, "写入失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.i("write", "----connect-------------");


            boolean CodeAuth = false;


            byte[] data_write = str.getBytes();


            if (data_write.length <= 720) {

                int num = data_write.length / 16;
                System.out.println("num= " + num);

                int next = data_write.length / 48 + 1;

                System.out.println("扇区next的值为" + next);

                b0 = new byte[16];
                if (!(data_write.length % 16 == 0)) {
                    for (int i = 1, j = 1; i <= num; i++) {
                        CodeAuth = mfc.authenticateSectorWithKeyA(j, code);
                        System.arraycopy(data_write, 16 * (i - 1), b0, 0, 16);
                        mfc.writeBlock(block[i - 1], b0);
                        if (i % 3 == 0) {
                            j++;
                        }
                    }
                    //Log.d("下一个模块", "测试");
                    CodeAuth = mfc.authenticateSectorWithKeyA(next,// 非常重要------
                            code);
                    //Log.d("获取第5块的密码", "---成功-------");
                    byte[] b2 = { 0 };
                    b0 = new byte[16];
                    System.arraycopy(data_write, 16 * num, b0, 0, data_write.length % 16);
                    System.arraycopy(b2, 0, b0, data_write.length % 16, b2.length);
                    mfc.writeBlock(4, b0);mfc.writeBlock(5, b0);mfc.writeBlock(6, b0);mfc.writeBlock(13, b0);
                    mfc.close();
                    Toast.makeText(this, "写入成功0", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    for (int i = 1, j = 1; i <= num; i++) {
                        if (i % 3 == 0) {
                            j++;
                            System.out.println("扇区j的值为：" + j);
                        }
                        CodeAuth = mfc.authenticateSectorWithKeyA(j,// 非常重要---------
                                code);
                        System.arraycopy(data_write, 16 * (i - 1), b0, 0, 16);
                        mfc.writeBlock(block[i - 1], b0);
                        str += ByteArrayToHexString(b0);
                        System.out.println("Block" + i + ": " + str);
                    }
                    mfc.close();
                    Toast.makeText(this, "写入成功1", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                Toast.makeText(getBaseContext(), "字符过长，内存不足",Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                mfc.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
