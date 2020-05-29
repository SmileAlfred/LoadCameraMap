package com.example.loadcameramap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

/**
 * bitmap 和 ImageView 相互转化：
 * Bitmap bmMeIcon = ((BitmapDrawable) ivMeIcon.getDrawable()).getBitmap();
 * ivMeIcon.setImageBitmap(bitmap);
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;
    private ImageView ivMeIcon;
    private RelativeLayout rlMeIcon;
    private TextView tvMeName;
    private RelativeLayout rlMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initTitle();
    }

    public void findViews() {
        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
        ivTitleSetting = findViewById(R.id.iv_title_setting);

        ivMeIcon = findViewById(R.id.iv_me_icon);
        rlMeIcon = findViewById(R.id.rl_me_icon);
        tvMeName = findViewById(R.id.tv_me_name);
        rlMe = findViewById(R.id.rl_me);

        ivTitleSetting.setOnClickListener(this);
        ivMeIcon.setOnClickListener(this);
        tvMeName.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.iv_title_setting:
            case R.id.iv_me_icon:
            case R.id.tv_me_name:
                //启动用户信息界面的Activity
                intent = new Intent(MainActivity.this, UserInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //读取本地保存的图片;注意这里的生命周期
        readImage();
    }

    private boolean readImage() {
        File filesDir;
        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = getExternalFilesDir("");
        } else {//手机内部存储
            //路径：data/data/包名/files
            filesDir = getFilesDir();
        }
        File file = new File(filesDir, "icon.png");
        if (file.exists()) {
            //存储--->内存
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivMeIcon.setImageBitmap(bitmap);
            return true;
        }
        return false;
    }

    protected void initTitle() {
        ivTitleBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("设置头像");
        ivTitleSetting.setVisibility(View.VISIBLE);
    }

    public int dp2px(int dp) {
        //获取手机密度
        float density = getResources().getDisplayMetrics().density;
        //实现四舍五入
        return (int) (dp * density + 0.5);
    }
}
