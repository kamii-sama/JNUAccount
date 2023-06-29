package com.JNU.keepaccounts.activity.home.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.bean.SettingInfo;
import com.JNU.keepaccounts.db.DatabaseHelper;
import com.JNU.keepaccounts.db.mapper.SettingInfoMapper;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

import java.util.Objects;

public class UpdatePasswordPageActivity extends BasicActivity {
    TextView backTextView;
    TextView saveTextView;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, UpdatePasswordPageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password_page);
        findView();
    }


    private void findView(){
        backTextView = findViewById(R.id.activity_update_password_page_back_textview);
        saveTextView = findViewById(R.id.activity_update_password_page_save);
        passwordEditText = findViewById(R.id.activity_update_password_page_password);
        confirmPasswordEditText = findViewById(R.id.activity_update_password_page_confirm_password);
    }
    private void setListener(){
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePasswordPageActivity.this.finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取密码
                String passwordS = passwordEditText.getText().toString();
                // 获取确认密码
                String confirmPasswordS = confirmPasswordEditText.getText().toString();

                if(!Utils.notEmptyString(passwordS)){
                    Utils.showOneToast(UpdatePasswordPageActivity.this,"密码不能为空");
                    return;
                }else if(!Objects.equals(passwordS,confirmPasswordS)){
                    Utils.showOneToast(UpdatePasswordPageActivity.this,"两次密码不同");
                    return;
                }else{
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(DatabaseHelper.getDatabaseHelper(UpdatePasswordPageActivity.this));
                    SettingInfo settingInfo = GlobalInfo.settingInfo;
                    settingInfo.setPassword(passwordS);
                    settingInfoMapper.updateBySid(settingInfo);
                    UpdatePasswordPageActivity.this.finish();
                    Utils.showOneToast(UpdatePasswordPageActivity.this,"修改成功");
                }


            }
        });
    }
}