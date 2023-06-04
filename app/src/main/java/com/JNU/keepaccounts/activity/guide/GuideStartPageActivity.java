package com.JNU.keepaccounts.activity.guide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.bean.GuideInfo;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;

public class GuideStartPageActivity extends BasicActivity {
    TextView continueTextView;
    /**
     * 启动此活动
     * @param context
     */
    public static void startThisActivity(Context context){
        Intent intent = new Intent(context, GuideStartPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_start_page);
        continueTextView = findViewById(R.id.guide_start_page_continue_text_view);
        continueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalInfo.guideInfo = new GuideInfo();
                GuideUsernamePageActivity.startThisActivity(GuideStartPageActivity.this);
                GuideStartPageActivity.this.finish();
            }
        });
    }
}