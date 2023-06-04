package com.JNU.keepaccounts.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.utils.globle.ActivityController;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

public class GuideUsernamePageActivity extends BasicActivity {
    // 用户名输入框
    private EditText usernameEditText;
    // 退出按钮
    private TextView exitTextView;
    // 继续按钮
    private TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuideUsernamePageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_username_page);

        usernameEditText = findViewById(R.id.activity_guide_username_page_username);
        exitTextView = findViewById(R.id.activity_guide_username_page_exit);
        continueTextView = findViewById(R.id.activity_guide_username_page_continue);
        // 退出按钮监听器
        exitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityController.finishAll();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.notEmptyString(usernameEditText.getText().toString())){
                    Utils.showOneToast(GuideUsernamePageActivity.this,"用户名不能为空");
                    return;
                }
                // 保存用户名到全局类
                GlobalInfo.guideInfo.setUsername(usernameEditText.getText().toString());
                // 前往下一个页面
                GuidePasswordPageActivity.startThisActivity(GuideUsernamePageActivity.this);
            }
        });
    }
}