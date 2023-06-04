package com.JNU.keepaccounts.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.activity.home.info.SwitchAccountBookPageActivity;
import com.JNU.keepaccounts.activity.home.info.UpdatePasswordPageActivity;
import com.JNU.keepaccounts.activity.home.info.UpdateUsernamePageActivity;
import com.JNU.keepaccounts.db.DatabaseHelper;
import com.JNU.keepaccounts.db.mapper.SettingInfoMapper;
import com.JNU.keepaccounts.utils.globle.BasicFragment;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;

public class ShowMyInfoPageFragment extends BasicFragment {
    private View thisView;
    private LinearLayout usernameLayout;
    private TextView usernameTextView;
    private LinearLayout enablePasswordLayout;
    private Switch enablePasswordSwitch;
    private LinearLayout changePasswordLayout;
    private LinearLayout switchAccountBookLayout;
    private LinearLayout aboutLayout;
    private LinearLayout moreLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.my_info_page, container, false);
        return thisView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 绑定相应的组件
        findView();

        // 添加点击监听器
        setLinstener();
    }

    /**
     * findViewById
     */
    private void findView() {
        usernameLayout = thisView.findViewById(R.id.my_info_page_username_layout);
        usernameTextView = thisView.findViewById(R.id.my_info_page_username);
        switchAccountBookLayout = thisView.findViewById(R.id.my_info_page_switch_account_book_layout);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 加载用户名
        usernameTextView.setText(GlobalInfo.settingInfo.getUsername());


    }

    /**
     * 绑定监听器
     */
    private void setLinstener() {
        DatabaseHelper songGuoDatabaseHelper = DatabaseHelper.getDatabaseHelper(getContext());
        usernameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 修改用户名界面
                UpdateUsernamePageActivity.startThisActivity(getActivity());
            }
        });
        switchAccountBookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.startThisActivity(getActivity());
                // 切换账本页面
            }
        });


    }
}
