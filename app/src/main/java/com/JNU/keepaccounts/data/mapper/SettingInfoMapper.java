package com.JNU.keepaccounts.data.mapper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.JNU.keepaccounts.bean.SettingInfo;
import com.JNU.keepaccounts.data.DatabaseHelper;

public class SettingInfoMapper {
    public static final String INSERT_SETTING_INFO = "insert into t_setting_info \n" +
            "(username,password, if_enable_password_check, defult_launch_page, defult_add_page,current_account_book_bid)\n" +
            "values\n" +
            "(?,?,?,?,?,?)";
    public static final String UPDATE_BY_SID = "update t_setting_info\n" +
            "set username = ?, password = ?, if_enable_password_check = ?, defult_launch_page = ?, defult_add_page = ?, current_account_book_bid = ?\n" +
            "where sid = ?";

    public static final String SELECT_BY_SID = "select * from t_setting_info where sid = ?";
    public static final String SELECT_All = "select * from t_setting_info";



    DatabaseHelper songGuoDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;

    /**
     * 构造方法传入helper
     *
     * @param songGuoDatabaseHelper
     */
    public SettingInfoMapper(DatabaseHelper songGuoDatabaseHelper) {
        this.songGuoDatabaseHelper = songGuoDatabaseHelper;
        sqLiteDatabase = songGuoDatabaseHelper.getWritableDatabase();

    }

    /**
     * 插入设置信息
     * 并返回插入后的设置对象
     *
     * @param settingInfo
     * @return
     */
    public SettingInfo insertSettingInfo(SettingInfo settingInfo) {
        sqLiteDatabase.execSQL(INSERT_SETTING_INFO, new String[]{settingInfo.getUsername(),settingInfo.getPassword(), settingInfo.getIfEnablePasswordCheck(), settingInfo.getDefultLaunchPage(), settingInfo.getDefultAddPage(), String.valueOf(settingInfo.getCurrentAccountBookBid())});
        return selectTheFirstSetting();
    }

    /**
     * 通过sid更新
     *
     * @param settingInfo
     * @return
     */
    public SettingInfo updateBySid(SettingInfo settingInfo) {
        sqLiteDatabase.execSQL(UPDATE_BY_SID, new String[]{settingInfo.getUsername(),settingInfo.getPassword(), settingInfo.getIfEnablePasswordCheck(), settingInfo.getDefultLaunchPage(), settingInfo.getDefultAddPage(), String.valueOf(settingInfo.getCurrentAccountBookBid()), String.valueOf(settingInfo.getSid())});
        return selectBySid(settingInfo.getSid());
    }


    /**
     * 通过sid搜索设置
     *
     * @param sid
     * @return
     */
    public SettingInfo selectBySid(Integer sid) {
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_BY_SID, new String[]{String.valueOf(sid)});
        SettingInfo settingInfo = new SettingInfo();
        settingInfo.setSid(sid);
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String ifEnablePasswordCheck = cursor.getString(cursor.getColumnIndex("if_enable_password_check"));
            String defultLaunchPage = cursor.getString(cursor.getColumnIndex("defult_launch_page"));
            String defultAddPage = cursor.getString(cursor.getColumnIndex("defult_add_page"));
            Integer currentAccountBookBid = cursor.getInt(cursor.getColumnIndex("current_account_book_bid"));
            settingInfo.setUsername(username);
            settingInfo.setPassword(password);
            settingInfo.setIfEnablePasswordCheck(ifEnablePasswordCheck);
            settingInfo.setDefultLaunchPage(defultLaunchPage);
            settingInfo.setDefultAddPage(defultAddPage);
            settingInfo.setCurrentAccountBookBid(currentAccountBookBid);
        }
        cursor.close();
        return settingInfo;
    }

    /**
     * 查找第一条(唯一一条设置)
     * @return
     */
    private SettingInfo selectTheFirstSetting(){
        Cursor cursor = sqLiteDatabase.rawQuery(SELECT_All, null);
        SettingInfo settingInfo = new SettingInfo();
        if(cursor.moveToFirst()){
            Integer sid = cursor.getInt(cursor.getColumnIndex("sid"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            String ifEnablePasswordCheck = cursor.getString(cursor.getColumnIndex("if_enable_password_check"));
            String defultLaunchPage = cursor.getString(cursor.getColumnIndex("defult_launch_page"));
            String defultAddPage = cursor.getString(cursor.getColumnIndex("defult_add_page"));
            Integer currentAccountBookBid = cursor.getInt(cursor.getColumnIndex("current_account_book_bid"));
            settingInfo.setSid(sid);
            settingInfo.setUsername(username);
            settingInfo.setPassword(password);
            settingInfo.setIfEnablePasswordCheck(ifEnablePasswordCheck);
            settingInfo.setDefultLaunchPage(defultLaunchPage);
            settingInfo.setDefultAddPage(defultAddPage);
            settingInfo.setCurrentAccountBookBid(currentAccountBookBid);
        }
        cursor.close();
        return settingInfo;
    }

}
