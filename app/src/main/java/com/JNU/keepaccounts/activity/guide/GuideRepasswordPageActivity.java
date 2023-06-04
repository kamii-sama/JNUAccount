package com.JNU.keepaccounts.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;

import java.util.Objects;

public class GuideRepasswordPageActivity extends BasicActivity {
    private EditText repasswordEditText;
    private TextView backTextView;
    private TextView continueTextView;

    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuideRepasswordPageActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_repassword_page);

        repasswordEditText = findViewById(R.id.activity_guide_repassword_page_repassword);
        backTextView = findViewById(R.id.activity_guide_repassword_page_back);
        continueTextView = findViewById(R.id.activity_guide_repassword_page_continue);

        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuideRepasswordPageActivity.this.finish();
            }
        });
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 验证两次密码是否相同
                String password2 = repasswordEditText.getText().toString();
                String password1 = GlobalInfo.guideInfo.getPassword();
                if(Objects.equals(password1,password2)){
                    //开启下一个活动
                    GuideEnablePasswordCheckPageActivity.startThisActivity(GuideRepasswordPageActivity.this);
                }else {
                    Toast.makeText(GuideRepasswordPageActivity.this,"两次输入的密码不同",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}