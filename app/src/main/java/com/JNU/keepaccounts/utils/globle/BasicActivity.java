package com.JNU.keepaccounts.utils.globle;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BasicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        // 日志输出
        Log.d("BasicActivity",getClass().getSimpleName());
        // AC管理添加
        ActivityController.addActivity(this);
        // 全面屏应用
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // 透明状态栏
        window.setStatusBarColor(Color.TRANSPARENT);

        // 添加背景
//        window.setBackgroundDrawableResource(R.drawable.bac);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // AC管理移除
        ActivityController.removeActivity(this);
    }


}
