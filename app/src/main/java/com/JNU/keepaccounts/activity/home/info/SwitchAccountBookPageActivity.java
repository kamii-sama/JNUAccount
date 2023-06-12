package com.JNU.keepaccounts.activity.home.info;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.adapter.SwitchAccountBookAdapter;
import com.JNU.keepaccounts.bean.AccountBook;
//import com.JNU.keepaccounts.db.DatabaseHelper;
//import com.JNU.keepaccounts.db.mapper.AccountBookMapper;
import com.JNU.keepaccounts.utils.globle.BasicActivity;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SwitchAccountBookPageActivity extends BasicActivity{
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

        //为了能够用espresso录制，添加此段初始化代码
        GlobalInfo.accountBooks = new ArrayList<>();
        GlobalInfo.accountBooks.addAll(Load(this));
        //用于生成测试
//        AccountBook book = new AccountBook();
//        book.setAccountBookName("1");
//        GlobalInfo.accountBooks.add(book);

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
                                    //                                    //创建账本实例
                                    AccountBook accountBook=new AccountBook();
                                    accountBook.setAccountBookName(editText.getText().toString());
                                    GlobalInfo.currentAccountBook=accountBook;
                                    GlobalInfo.accountBooks.add(accountBook);
                                    Save((Context) SwitchAccountBookPageActivity.this, (ArrayList<AccountBook>) GlobalInfo.accountBooks);
                                    refreshAccountBook1();
                                    Utils.showOneToast(SwitchAccountBookPageActivity.this,"添加成功，当前账本为《"+accountBook+"》");

                                }
                                else{
                                    Utils.showOneToast(SwitchAccountBookPageActivity.this,"账本添加失败");
                                }
                            }
                        }
                ).setNegativeButton("取消", null).show();

    }
    public void Save(Context context, ArrayList<AccountBook> data)
    {
        try {

            FileOutputStream dataStream=context.openFileOutput("mydata.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            Log.v("dataSave", String.valueOf(data));
            out.close();
            dataStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @NonNull
    public ArrayList<AccountBook> Load(Context context)
    {
        ArrayList<AccountBook> data=new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput("mydata.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (ArrayList<AccountBook>) in.readObject();
            Log.v("dataLoad", String.valueOf(data));
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public void refreshAccountBook() {
        AccountBook init_book = new AccountBook();
        init_book.setAccountBookName("1");
//        AccountBook init_book2 = new AccountBook();
//        init_book2.setAccountBookName("2");
        List<AccountBook> myList = new ArrayList<>();
        myList.add(init_book);
//        myList.add(init_book2);
        // 全局账本清空
        GlobalInfo.accountBooks.clear();
        // 全局账本重新添加
//        AccountBookMapper accountBookMapper = new AccountBookMapper(DatabaseHelper.getDatabaseHelper(SwitchAccountBookPageActivity.this));
        GlobalInfo.accountBooks.addAll(myList);
        //        GlobalInfo.accountBooks.add(init_book);
//        GlobalInfo.accountBooks.add(init_book2);
        switchAccountBookAdapter.notifyDataSetChanged();
    }
    public void refreshAccountBook1(){
        switchAccountBookAdapter.notifyDataSetChanged();
    }
}