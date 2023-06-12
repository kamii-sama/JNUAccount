package com.JNU.keepaccounts.bean;

import java.io.Serializable;

public class AccountBook implements Serializable {
    private Integer bid;
    // 账本名
    private String accountBookName;

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getAccountBookName() {
        return accountBookName;
    }

    public void setAccountBookName(String accountBookName) {
        this.accountBookName = accountBookName;
    }
}
