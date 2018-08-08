package com.example.administrator.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import org.litepal.crud.DataSupport;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatTextView address;
    private AppCompatTextView birthday;
    private AppCompatTextView sex;
    private AppCompatTextView count;

    private AppCompatButton query;
    private AppCompatButton batch_query;

    private AppCompatCheckBox checkSex;

    private AppCompatEditText ID;

    private NumberProgressBar numberProgressBar;
    private static int progress = 0;

    private final static String[] citys = new String[] {"11", "北京", "12", "天津", "13", "河北", "14", "山西",
    "15", "内蒙古", "21", "辽宁", "22", "吉林", "23", "黑龙江", "31", "上海", "32", "江苏", "33", "浙江省",
    "34", "安徽", "35", "福建", "36", "江西", "37", "山东", "41", "河南", "42", "湖北", "43", "湖南",
    "44", "广东", "45", "广西", "46", "海南", "50", "重庆", "51", "四川", "52", "贵州", "53", "云南", "54", "西藏",
    "61", "陕西", "62", "甘肃", "63", "青海", "64", "宁夏", "", "65", "新疆", "71", "台湾", "81", "香港", "82", "澳门"};

    //原始要批量处理的身份证文件
    private final static String FILEBEFORE = Environment.getExternalStorageDirectory().getPath() + "/sfz.txt";
    //处理完需要的文件
    private final static String FILEDONE = Environment.getExternalStorageDirectory().getPath() + "/sfzok.txt";
    //读写权限申请
    private final static int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100;
    //读写行数
    private static int ROW = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:numberProgressBar.setProgress(progress);
                    break;
                case 1:numberProgressBar.setProgress(100);
                    break;
                case 2:count.setText(String.valueOf(ROW));
                    break;
                case 3:Toast.makeText(MainActivity.this, "处理完毕!", Toast.LENGTH_LONG).show();
                    break;
                case 4:Toast.makeText(MainActivity.this, "读写文件出错,任务失败!", Toast.LENGTH_LONG).show();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        address = findViewById(R.id.address);
        birthday = findViewById(R.id.birthday);
        sex = findViewById(R.id.sex);
        count = findViewById(R.id.count);
        query = findViewById(R.id.query);
        batch_query = findViewById(R.id.batch_query);
        ID = findViewById(R.id.ID);
        checkSex = findViewById(R.id.checkSex);
        query.setOnClickListener(this);
        batch_query.setOnClickListener(this);
        //先将assets里面的address.txt文件每一行数据添加到数据库里面
        //在通过数据库进行查询
        progress = 0;
        numberProgressBar = findViewById(R.id.number_progress_bar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AddressInfo> addressInfos = DataSupport.findAll(AddressInfo.class);
                if (addressInfos != null && addressInfos.size() >= 3122) {
                    progress = 100;
                    handler.sendEmptyMessage(1);
                } else {
                    DataSupport.deleteAll(AddressInfo.class);
                    readAssetsTxt(MainActivity.this, "address.txt");
                }
                Log.d("TAG", addressInfos.size() + "===========");
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query:
                if (progress != 100) {
                    Toast.makeText(MainActivity.this, "等待加载到100%再操作!", Toast.LENGTH_LONG).show();
                    return;
                }
                String id = ID.getText().toString();
                if (TextUtils.isEmpty(id) && (id.length() != 18)) {
                    Toast.makeText(MainActivity.this, "请正确输入18位身份证号码!", Toast.LENGTH_LONG).show();
                } else {
                    PersonInfo personInfo = queryFunction(id);
                    if (personInfo != null) {
                        String city = functionCity(id.substring(0, 2));
                        String s = personInfo.getAddress();
                        address.setText(String.valueOf("地区: " + city + s));
                        birthday.setText(String.valueOf("出生日期: " + personInfo.getBirthday()));
                        sex.setText(String.valueOf("性别: " + personInfo.getSex()));
                    } else {
                        Toast.makeText(MainActivity.this, "查询失败!", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.batch_query:
                if (progress != 100) {
                    Toast.makeText(MainActivity.this, "等待加载到100%再操作!", Toast.LENGTH_LONG).show();
                    return;
                }
                //先申请读写文件权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    batchQueryFunction();
                }
                break;
        }
    }

    private void batchQueryFunction() {
        ROW = 0;
        Toast.makeText(MainActivity.this, "开始批量查询!", Toast.LENGTH_SHORT).show();
        //首先确定两个文件没有问题
        final File filebefore = new File(FILEBEFORE);
        if (filebefore != null && !filebefore.exists()) {
            Toast.makeText(MainActivity.this, "sfz.txt文件不存在!\n任务将不执行!", Toast.LENGTH_LONG).show();
            return;
        }
        final File filedone = new File(FILEDONE);
        if (filedone != null && filedone.exists()) {
            filedone.delete();
        }
        try {
            filedone.createNewFile();
            //Toast.makeText(MainActivity.this, "sfzok.txt文件创建完毕!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //读取一条处理一条在sfzok.txt文件写一条
                readAndWrite(filebefore, filedone);
            }
        }).start();
    }

    private void readAndWrite(File filebefore, File filedone) {
        try {
            //读取进来
            FileReader fileReader = new FileReader(filebefore);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            //写出去
            FileWriter fileWriter = new FileWriter(filedone);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //读写一行处理一行，写进一行
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                ROW++;
                handler.sendEmptyMessage(2);
                PersonInfo personInfo = queryFunction(line.trim());
                if (personInfo == null) {
                    personInfo = new PersonInfo("未知", "未知", "未知");
                }
                String city = functionCity(line.substring(0, 2));
                String s = personInfo.getAddress();
                String ok = city + s;
                if (checkSex.isChecked()) {
                    ok = ok + "    " + personInfo.getSex();
                }
                //Log.d("line", line + "============" + ok);
                //写进去
                bufferedWriter.write(ok + "\n");
                bufferedWriter.flush();
            }
            fileReader.close();
            fileWriter.close();
            handler.sendEmptyMessage(3);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(4);
        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(4);
        }
    }

    //查询对应的省市
    private String functionCity(String sub) {
        for (int i = 0; i < citys.length; i++, i++) {
            if (sub.equals(citys[i])) return citys[i + 1];
        }
        return "";
    }

    private PersonInfo queryFunction(String id) {
        PersonInfo personInfo = new PersonInfo();
        String s = id.substring(0, 6);
        List<AddressInfo> addressInfos = DataSupport.where("number = ?", s).find(AddressInfo.class);
        if (addressInfos != null && addressInfos.size() > 0) {
            personInfo.setAddress(addressInfos.get(0).getName());
            String d = id.substring(6, 14);
            personInfo.setBirthday(d);
            int x = Integer.parseInt(String.valueOf(id.charAt(16)));
            if (x % 2 == 0) {
                //女
                personInfo.setSex("女");
            } else {
                //男
                personInfo.setSex("男");
            }
            return personInfo;
        } else {
            return null;
        }
    }

    public void readAssetsTxt(Context context, String fileName){
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);

            String code = getCode(is);
            Log.d("TAG", code);
            is = am.open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line = null;
            int i = 1;
            int last = 0;
            while((line = br.readLine()) != null){
                //Log.d("TAG", line);
                //将每一行插入到数据库里面
                String number = line.split(" 　　")[0];
                String name = line.split(" 　　")[1];
                AddressInfo addressInfo = new AddressInfo(number, name.trim());
                addressInfo.save();
                //Log.d("TAG", number + "-->" + name);
                progress = (int)((i / 3122.0) * 100);
                if (progress != last) {
                    last = progress;
                    handler.sendEmptyMessage(0);
                }

                if (i >= 3122) {
                    Log.d("TAG", "====================================");
                    break;
                }
                i++;
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getCode(InputStream is){
        try {
            BufferedInputStream bin = new BufferedInputStream(is);
            int p;

            p = (bin.read() << 8) + bin.read();

            String code = null;

            switch (p) {
                case 0xefbb:
                    code = "UTF-8";
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";
            }
            is.close();
            return code;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                batchQueryFunction();
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "您拒绝了访问存储卡权限，程序将不执行功能!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
