package com.JNU.keepaccounts;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.JNU.keepaccounts.activity.guide.GuideStartPageActivity;
import com.JNU.keepaccounts.activity.home.HomePageActivity;
import com.JNU.keepaccounts.bean.AccountBook;
import com.JNU.keepaccounts.bean.SettingInfo;
import com.JNU.keepaccounts.data.DatabaseHelper;
import com.JNU.keepaccounts.data.mapper.AccountBookMapper;
import com.JNU.keepaccounts.data.mapper.SettingInfoMapper;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalConstant;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 判断是否第一次使用该软件
                // preferences 存储 第一次使用的标志
                SharedPreferences songguoPreferences = Utils.getSongGuoSharedPreferences();
                // 如果没有该值,则为第一次启动
                boolean isFirstUse = songguoPreferences.getBoolean(GlobalConstant.isFirstUse,true);
                if(isFirstUse){
                    // 如果为第一次启动,则进入引导界面,引导流程结束后再更改标识
                    GuideStartPageActivity.startThisActivity(MainActivity.this);

                }else {
                    // 如果不是第一次启动,取出SID
                    int sid = songguoPreferences.getInt(GlobalConstant.SID, 0);
                    // 初始化全局类
                    DatabaseHelper databaseHelper = DatabaseHelper.getDatabaseHelper(MainActivity.this);

                    // 设置
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(databaseHelper);
                    SettingInfo settingInfo = settingInfoMapper.selectBySid(sid);
                    GlobalInfo.settingInfo = settingInfo;

                    // 账本
                    AccountBookMapper accountBookMapper = new AccountBookMapper(databaseHelper);
                    Integer currentAccountBookBid = settingInfo.getCurrentAccountBookBid();
                    AccountBook accountBook = accountBookMapper.selectByBid(currentAccountBookBid);
                    GlobalInfo.currentAccountBook = accountBook;

                    // 进入主页
                    HomePageActivity.startThisActivity(MainActivity.this);

                }

                MainActivity.this.finish();

            }
        }).start();
    }
}