package com.JNU.keepaccounts.db.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.JNU.keepaccounts.bean.AccountItem;
import com.JNU.keepaccounts.db.DatabaseHelper;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;

import java.util.ArrayList;
import java.util.List;

public class AccountItemMapper {
    public static final String INSERT_ACCOUNT_ITEM = "insert into t_account_item \n" +
            "(income_or_expenditure, tid, sum, remark, account_time, if_borrow_or_lend, bid, eid)\n" +
            "values\n" +
            "(?,?,?,?,?,?,?,?)";
    public static final String SELECT_AID_BY_ALL = "select\n" +
            "    aid\n" +
            "from\n" +
            "    t_account_item\n" +
            "where\n" +
            "    income_or_expenditure = ? and\n" +
            "    tid = ? and\n" +
            "    sum = ? and\n" +
            "    remark = ? and\n" +
            "    account_time = ? and\n" +
            "    if_borrow_or_lend = ? and\n" +
            "    bid = ? and\n" +
            "    eid = ?";
    public static final String DELETE_ACCOUNT_ITEM = "delete from t_account_item where aid = ?";

    public static final String UPDATE_ACCOUNT_ITEM = "update\n" +
            "    t_account_item\n" +
            "set\n" +
            "    income_or_expenditure = ?,\n" +
            "    tid = ?,\n" +
            "    sum = ?,\n" +
            "    remark = ?,\n" +
            "    account_time = ?,\n" +
            "    if_borrow_or_lend = ?,\n" +
            "    bid = ?,\n" +
            "    eid = ?\n" +
            "where\n" +
            "    aid = ?";
    public static final String SELECT_BY_AID = "select * from t_account_item where aid = ?";
    public static final String SELECT_DESC_PAGE = "select * from t_account_item where bid = ?  order by account_time desc limit ?,?";
    public static final String SELECT_DESC = "select * from t_account_item order by account_time desc";

    DatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    public AccountItemMapper(DatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();
    }

    /**
     * 插入一条账目
     *
     * @param accountItem
     * @return
     */
    public AccountItem insertAccountItem(AccountItem accountItem) {
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_ITEM, new String[]{
                accountItem.getIncomeOrExpenditure(), String.valueOf(accountItem.getTid()),
                String.valueOf(accountItem.getSum()), accountItem.getRemark(),
                String.valueOf(accountItem.getAccountTime()), accountItem.getIfBorrowOrLend(),
                String.valueOf(accountItem.getBid()), String.valueOf(accountItem.getEid())
        });
        accountItem.setAid(selectAidByAll(accountItem));
        return accountItem;
    }

    /**
     * 按照账目的所有信息查询aid
     *
     * @param accountItem
     * @return
     */
    public Integer selectAidByAll(AccountItem accountItem) {
        int aid = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_AID_BY_ALL, new String[]{
                accountItem.getIncomeOrExpenditure(), String.valueOf(accountItem.getTid()),
                String.valueOf(accountItem.getSum()), accountItem.getRemark(),
                String.valueOf(accountItem.getAccountTime()), accountItem.getIfBorrowOrLend(),
                String.valueOf(accountItem.getBid()), String.valueOf(accountItem.getEid())
        });
        if (cursor.moveToFirst()) {
            aid = cursor.getInt(cursor.getColumnIndex("aid"));
        }
        cursor.close();
        return aid;
    }

    /**
     * 删除账目
     *
     * @param accountItem
     */
    public void deleteAccountItem(AccountItem accountItem) {
        sqLiteDatabase.execSQL(DELETE_ACCOUNT_ITEM, new String[]{String.valueOf(accountItem.getAid())});
    }

    /**
     * 更新账目
     * @param accountItem
     * @return
     */
    public AccountItem updateAccountItem(AccountItem accountItem) {
        sqLiteDatabase.execSQL(UPDATE_ACCOUNT_ITEM, new String[]{
                accountItem.getIncomeOrExpenditure(), String.valueOf(accountItem.getTid()),
                String.valueOf(accountItem.getSum()), accountItem.getRemark(),
                String.valueOf(accountItem.getAccountTime()), accountItem.getIfBorrowOrLend(),
                String.valueOf(accountItem.getBid()), String.valueOf(accountItem.getEid()),
                String.valueOf(accountItem.getAid())

        });
        return selectByAid(accountItem.getAid());
    }


    /**
     * 通过aid查账目
     * @param aid
     * @return
     */
    public AccountItem selectByAid(Integer aid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_AID, new String[]{String.valueOf(aid)});
        AccountItem accountItem = new AccountItem();
        if(cursor.moveToFirst()){
            aid =
                    cursor.getInt(cursor.getColumnIndex("aid"));
            String incomeOrExpenditure =
                    cursor.getString(cursor.getColumnIndex("income_or_expenditure"));
            Integer tid =
                    cursor.getInt(cursor.getColumnIndex("tid"));
            Double sum =
                    cursor.getDouble(cursor.getColumnIndex("sum"));
            String remark =
                    cursor.getString(cursor.getColumnIndex("remark"));
            Long accountTime =
                    cursor.getLong(cursor.getColumnIndex("account_time"));
            String ifBorrowOrLend =
                    cursor.getString(cursor.getColumnIndex("if_borrow_or_lend"));
            Integer bid =
                    cursor.getInt(cursor.getColumnIndex("bid"));
            Integer eid =
                    cursor.getInt(cursor.getColumnIndex("eid"));
            accountItem.setAid(aid);
            accountItem.setIncomeOrExpenditure(incomeOrExpenditure);
            accountItem.setTid(tid);
            accountItem.setSum(sum);
            accountItem.setRemark(remark);
            accountItem.setAccountTime(accountTime);
            accountItem.setIfBorrowOrLend(ifBorrowOrLend);
            accountItem.setBid(bid);
            accountItem.setEid(eid);
        }
        cursor.close();
        return accountItem;

    }

    /**
     * 倒叙查找页面
     * @param page
     * @param size
     * @return
     */
    public List<AccountItem> selectDescPage(Integer page, Integer size){
        // 页面转指针
        int index = (page - 1) * size;

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DESC_PAGE,new String[]{String.valueOf(GlobalInfo.currentAccountBook.getBid()),String.valueOf(index), String.valueOf(size)});
        List<AccountItem> accountItems = new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                AccountItem accountItem = new AccountItem();
                Integer aid =
                        cursor.getInt(cursor.getColumnIndex("aid"));
                String incomeOrExpenditure =
                        cursor.getString(cursor.getColumnIndex("income_or_expenditure"));
                Integer tid =
                        cursor.getInt(cursor.getColumnIndex("tid"));
                Double sum =
                        cursor.getDouble(cursor.getColumnIndex("sum"));
                String remark =
                        cursor.getString(cursor.getColumnIndex("remark"));
                Long accountTime =
                        cursor.getLong(cursor.getColumnIndex("account_time"));
                String ifBorrowOrLend =
                        cursor.getString(cursor.getColumnIndex("if_borrow_or_lend"));
                Integer bid =
                        cursor.getInt(cursor.getColumnIndex("bid"));
                Integer eid =
                        cursor.getInt(cursor.getColumnIndex("eid"));
                accountItem.setAid(aid);
                accountItem.setIncomeOrExpenditure(incomeOrExpenditure);
                accountItem.setTid(tid);
                accountItem.setSum(sum);
                accountItem.setRemark(remark);
                accountItem.setAccountTime(accountTime);
                accountItem.setIfBorrowOrLend(ifBorrowOrLend);
                accountItem.setBid(bid);
                accountItem.setEid(eid);
                accountItems.add(accountItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return accountItems;

    }
    /**
     * 倒叙查找
     *
     * @return
     */
    public List<AccountItem> selectDesc(){

        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_DESC,null);
        List<AccountItem> accountItems = new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                AccountItem accountItem = new AccountItem();
                Integer aid =
                        cursor.getInt(cursor.getColumnIndex("aid"));
                String incomeOrExpenditure =
                        cursor.getString(cursor.getColumnIndex("income_or_expenditure"));
                Integer tid =
                        cursor.getInt(cursor.getColumnIndex("tid"));
                Double sum =
                        cursor.getDouble(cursor.getColumnIndex("sum"));
                String remark =
                        cursor.getString(cursor.getColumnIndex("remark"));
                Long accountTime =
                        cursor.getLong(cursor.getColumnIndex("account_time"));
                String ifBorrowOrLend =
                        cursor.getString(cursor.getColumnIndex("if_borrow_or_lend"));
                Integer bid =
                        cursor.getInt(cursor.getColumnIndex("bid"));
                Integer eid =
                        cursor.getInt(cursor.getColumnIndex("eid"));
                accountItem.setAid(aid);
                accountItem.setIncomeOrExpenditure(incomeOrExpenditure);
                accountItem.setTid(tid);
                accountItem.setSum(sum);
                accountItem.setRemark(remark);
                accountItem.setAccountTime(accountTime);
                accountItem.setIfBorrowOrLend(ifBorrowOrLend);
                accountItem.setBid(bid);
                accountItem.setEid(eid);
                accountItems.add(accountItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return accountItems;

    }

}
