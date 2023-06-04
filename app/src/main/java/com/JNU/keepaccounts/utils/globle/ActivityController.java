package com.JNU.keepaccounts.utils.globle;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {
    // 存放所有打开的Activity
    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    // 关闭所有Activity
    public static void finishAll() {
        for (Activity a : activities) {
            if (!a.isFinishing()) {
                a.finish();
            }
        }
        activities.clear();
    }
}
