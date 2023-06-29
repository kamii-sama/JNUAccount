package com.JNU.keepaccounts.activity.home.info;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accounts.Account;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.adapter.SwitchAccountBookAdapter;
import com.JNU.keepaccounts.bean.AccountBook;
//import com.JNU.keepaccounts.db.DatabaseHelper;
//import com.JNU.keepaccounts.db.mapper.AccountBookMapper;
import com.JNU.keepaccounts.db.DatabaseHelper;
import com.JNU.keepaccounts.db.mapper.AccountBookMapper;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SwitchAccountBookPageActivity extends BasicActivity {
    TextView backTextView;
    TextView addTextView;
    LinearLayout addLinearLayout;
    RecyclerView recyclerView;
    SwitchAccountBookAdapter switchAccountBookAdapter;

    /**
     * 启动此活动
     *
     * @param context
     */
    public static void startThisActivity(Context context) {
        Intent intent = new Intent(context, SwitchAccountBookPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_account_book_page);
        findView();
        init();
        setListener();
    }


    private void findView() {
        backTextView = findViewById(R.id.activity_switch_account_book_page_back_textview);
        addTextView = findViewById(R.id.activity_switch_account_book_page_add);
        addLinearLayout = findViewById(R.id.activity_switch_account_book_page_add_linear);
        recyclerView = findViewById(R.id.activity_switch_account_book_page_recycler_view);
    }

    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SwitchAccountBookPageActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        // 获取所有账本
//        AccountBookMapper accountBookMapper = new AccountBookMapper(DatabaseHelper.getDatabaseHelper(SwitchAccountBookPageActivity.this));
//        GlobalInfo.accountBooks = accountBookMapper.selectAll();

//        //为了能够用espresso录制，添加此段初始化代码
        AccountBook init_book = new AccountBook();23
        init_book.setAccountBookName("1");
        GlobalInfo.accountBooks.add(init_book);

        switchAccountBookAdapter = new SwitchAccountBookAdapter(SwitchAccountBookPageActivity.this, GlobalInfo.accountBooks);
        recyclerView.setAdapter(switchAccountBookAdapter);

    }

    private void setListener() {
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.this.finish();
            }
        });
        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.this.addAccountBook();
            }
        });
        addLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchAccountBookPageActivity.this.addAccountBook();
            }
        });
    }

    /**
     * 新建账本并作为当前账本
     */
    private void addAccountBook() {
        // 弹窗输入框输入账本名
        EditText editText = new EditText(SwitchAccountBookPageActivity.this);
        new AlertDialog.Builder(SwitchAccountBookPageActivity.this).setTitle("请输入账本名")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(editText)
                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(Utils.notEmptyString(editText.getText().toString())){

                                }
                                else{
                                    Utils.showOneToast(SwitchAccountBookPageActivity.this,"账本添加失败");
                                }
                            }
                        }
                ).setNegativeButton("取消", null).show();

    }

    public void refreshAccountBook() {
        AccountBook init_book = new AccountBook();
        init_book.setAccountBookName("1");
        AccountBook init_book2 = new AccountBook();
        init_book2.setAccountBookName("2");
        List<AccountBook> myList = new ArrayList<>();
        myList.add(init_book2);
        myList.add(init_book);
        // 全局账本清空
        GlobalInfo.accountBooks.clear();
        // 全局账本重新添加
//        AccountBookMapper accountBookMapper = new AccountBookMapper(DatabaseHelper.getDatabaseHelper(SwitchAccountBookPageActivity.this));
        GlobalInfo.accountBooks.addAll(myList);
        //        GlobalInfo.accountBooks.add(init_book);
//        GlobalInfo.accountBooks.add(init_book2);
        switchAccountBookAdapter.notifyDataSetChanged();
    }
}