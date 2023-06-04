package com.JNU.keepaccounts.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

public class GuidePasswordPageActivity extends BasicActivity {
    private EditText passwordEditText;
    private TextView backTextView;
    private TextView continueTextView;

    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuidePasswordPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_password_page);

        passwordEditText = findViewById(R.id.activity_guide_password_page_password);
        backTextView = findViewById(R.id.activity_guide_password_page_back);
        continueTextView = findViewById(R.id.activity_guide_password_page_continue);

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuidePasswordPageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.notEmptyString(passwordEditText.getText().toString())){
                    Utils.showOneToast(GuidePasswordPageActivity.this,"密码不能为空");
                    return;
                }
                GlobalInfo.guideInfo.setPassword(passwordEditText.getText().toString());
                GuideRepasswordPageActivity.startThisActivity(GuidePasswordPageActivity.this);
            }
        });
    }
}