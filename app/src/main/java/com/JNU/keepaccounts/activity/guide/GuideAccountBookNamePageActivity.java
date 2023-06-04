package com.JNU.keepaccounts.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.activity.home.HomePageActivity;
import com.JNU.keepaccounts.bean.AccountBook;
import com.JNU.keepaccounts.bean.GuideInfo;
import com.JNU.keepaccounts.bean.SettingInfo;
import com.JNU.keepaccounts.db.DatabaseHelper;
import com.JNU.keepaccounts.db.mapper.AccountBookMapper;
import com.JNU.keepaccounts.db.mapper.SettingInfoMapper;
import com.JNU.keepaccounts.db.mapper.TagMapper;
import com.JNU.keepaccounts.utils.globle.ActivityController;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalConstant;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

public class GuideAccountBookNamePageActivity extends BasicActivity {
    // 账本输入框
    private EditText accountBookNameEditText;
    // 退出按钮
    private TextView backTextView;
    // 继续按钮
    private TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context,GuideAccountBookNamePageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_account_book_name_page);

        accountBookNameEditText = findViewById(R.id.activity_guide_account_book_name_page_account_book_name);
        backTextView = findViewById(R.id.activity_guide_account_book_name_page_back);
        continueTextView = findViewById(R.id.activity_guide_account_book_name_page_continue);

        // 退出按钮监听器
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideAccountBookNamePageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.notEmptyString(accountBookNameEditText.getText().toString())) {
                    Utils.showOneToast(GuideAccountBookNamePageActivity.this, "账本名不能为空");
                    return;
                }
                // 防止用户多次点击
                continueTextView.setClickable(false);
                // 全局信息中保存账本名
                GlobalInfo.guideInfo.setAccountBookName(accountBookNameEditText.getText().toString());

                // 开始解析guide信息
                GuideInfo guideInfo = GlobalInfo.guideInfo;

                // 用户名
                String username = guideInfo.getUsername();
                // 密码
                String password = guideInfo.getPassword();
                // 是否启用密码验证
                String ifEnablePasswordCheck = guideInfo.getIfEnablePasswordCheck();
                // 账本名
                String accountBookName = guideInfo.getAccountBookName();


                // 初始化Helper
                DatabaseHelper songGuoDatabaseHelper = DatabaseHelper.getDatabaseHelper(GuideAccountBookNamePageActivity.this);

                // 初始化偏好设置
                SharedPreferences songGuoSharedPreferences = Utils.getSongGuoSharedPreferences();
                SharedPreferences.Editor editor = songGuoSharedPreferences.edit();

                // 新建账本并保存到数据库
                AccountBook accountBook = new AccountBook();
                accountBook.setAccountBookName(accountBookName);
                AccountBookMapper accountBookMapper = new AccountBookMapper(songGuoDatabaseHelper);
                accountBook = accountBookMapper.insertAccountBook(accountBook);

                // 新建设置信息保存到数据库
                SettingInfo settingInfo = SettingInfo.getDefultSettingInfo();
                settingInfo.setUsername(username);
                settingInfo.setPassword(password);
                settingInfo.setIfEnablePasswordCheck(ifEnablePasswordCheck);
                settingInfo.setCurrentAccountBookBid(accountBook.getBid());
                SettingInfoMapper settingInfoMapper = new SettingInfoMapper(songGuoDatabaseHelper);
                settingInfo = settingInfoMapper.insertSettingInfo(settingInfo);

                // 存储设置的id
                editor.putInt(GlobalConstant.SID,settingInfo.getSid());
                // 更改第一次使用标识
                editor.putBoolean(GlobalConstant.isFirstUse,false);
                editor.apply();
                // Tag初始化
                TagMapper tagMapper = new TagMapper(songGuoDatabaseHelper);
                tagMapper.init();

                // 初始化全局类
                // 设置
                GlobalInfo.settingInfo = settingInfo;
                // 账本
                GlobalInfo.currentAccountBook = accountBook;

                // bug:进入主界面还能返回引导界面
                // 解决:结束之前所有活动,防止用户返回
                ActivityController.finishAll();

                // 跳转到home界面
                HomePageActivity.startThisActivity(GuideAccountBookNamePageActivity.this);
            }
        });
    }


}