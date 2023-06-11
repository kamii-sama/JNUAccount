package com.JNU.keepaccounts.activity.home.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.bean.SettingInfo;
import com.JNU.keepaccounts.data.DatabaseHelper;
import com.JNU.keepaccounts.data.mapper.SettingInfoMapper;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

public class UpdateUsernamePageActivity extends BasicActivity {
    TextView backTextView;
    TextView saveTextView;
    EditText usernameEditText;


    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, UpdateUsernamePageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_username_page);
        findView();
        init();
        setListener();
    }

    private void init() {
        usernameEditText.setText(GlobalInfo.settingInfo.getUsername());
    }

    private void findView(){
        backTextView = findViewById(R.id.activity_update_username_page_back_textview);
        saveTextView = findViewById(R.id.activity_update_username_page_save);
        usernameEditText = findViewById(R.id.activity_update_username_page_uername);
    }
    private void setListener(){
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUsernamePageActivity.this.finish();
            }
        });
        saveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户名
                String usernameS = usernameEditText.getText().toString();
                if(Utils.notEmptyString(usernameS)){
                    SettingInfoMapper settingInfoMapper = new SettingInfoMapper(DatabaseHelper.getDatabaseHelper(UpdateUsernamePageActivity.this));
                    SettingInfo settingInfo = GlobalInfo.settingInfo;
                    settingInfo.setUsername(usernameS);
                    settingInfoMapper.updateBySid(settingInfo);

                    UpdateUsernamePageActivity.this.finish();
                    Utils.showOneToast(UpdateUsernamePageActivity.this,"修改成功");
                }else {
                    Utils.showOneToast(UpdateUsernamePageActivity.this,"用户名不能为空");
                }

            }
        });
    }
}