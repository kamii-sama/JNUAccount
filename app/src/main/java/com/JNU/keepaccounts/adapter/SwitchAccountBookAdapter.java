package com.JNU.keepaccounts.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.activity.home.info.SwitchAccountBookPageActivity;
import com.JNU.keepaccounts.bean.AccountBook;
import com.JNU.keepaccounts.data.DatabaseHelper;
import com.JNU.keepaccounts.data.mapper.AccountBookMapper;
import com.JNU.keepaccounts.data.mapper.SettingInfoMapper;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;
import com.JNU.keepaccounts.utils.globle.Utils;

import java.util.List;

public class SwitchAccountBookAdapter extends RecyclerView.Adapter<SwitchAccountBookAdapter.AccountBookViewHolder>{
    List<AccountBook> accountBooks;
    SwitchAccountBookPageActivity activity;
    static class AccountBookViewHolder extends RecyclerView.ViewHolder {
        View thisView;
        TextView accountBookName;
        public AccountBookViewHolder(@NonNull View itemView) {
            super(itemView);
            thisView = itemView;
            accountBookName = itemView.findViewById(R.id.item_switch_account_book_account_book_name);
        }
    }

    public SwitchAccountBookAdapter(SwitchAccountBookPageActivity activity, List<AccountBook> accountBooks){
        this.activity = activity;
        this.accountBooks = accountBooks;
    }
    @NonNull
    @Override
    public AccountBookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_switch_account_book,viewGroup,false);
        AccountBookViewHolder accountBookViewHolder = new AccountBookViewHolder(view);
        accountBookViewHolder.thisView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = accountBookViewHolder.getAdapterPosition();
                AccountBook accountBook = accountBooks.get(adapterPosition);
                // 设置全局账本
                GlobalInfo.currentAccountBook = accountBook;
                // 更新设置信息
                GlobalInfo.settingInfo.setCurrentAccountBookBid(accountBook.getBid());
                SettingInfoMapper settingInfoMapper =
                        new SettingInfoMapper(DatabaseHelper.getDatabaseHelper(viewGroup.getContext()));
                settingInfoMapper.updateBySid(GlobalInfo.settingInfo);
                Utils.showOneToast(activity,"切换成功,当前帐本为《"+accountBook.getAccountBookName()+"》");
//                activity.finish();

            }
        });
        accountBookViewHolder.thisView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int adapterPosition = accountBookViewHolder.getAdapterPosition();
                AccountBook accountBook = accountBooks.get(adapterPosition);
                if (accountBook.getBid() == GlobalInfo.currentAccountBook.getBid()){
                    Utils.showOneToast(activity,"不能删除当前账本");
                    return true;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("你确定要删除此账本吗");
                builder.setMessage("删除后不可恢复,包括该账本中的所有账单与事件");
                builder.setCancelable(true);
                builder.setPositiveButton("确认删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 删除该账本
                        AccountBookMapper accountBookMapper = new AccountBookMapper(DatabaseHelper.getDatabaseHelper(activity));
                        accountBookMapper.deleteAccountBook(accountBook);
                        activity.refreshAccountBook();
                        // 删除该账本下的所有账单
                        // 删除该账本下的所有记事
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.show();

                return true;
            }
        });
        return accountBookViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountBookViewHolder accountBookViewHolder, int i) {
        accountBookViewHolder.accountBookName.setText(accountBooks.get(i).getAccountBookName());
    }

    @Override
    public int getItemCount() {
        return accountBooks.size();
    }
}
