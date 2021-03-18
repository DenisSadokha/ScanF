package com.example.scanf;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DataCenter extends RealmObject {
    private String name;
    private String count;
    private String code;
    private String balanceFromServer;
    private String maxCount;
    @PrimaryKey
    private int id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public String getBalanceFromServer() {
        return balanceFromServer;
    }

    public void setBalanceFromServer(String balanceFromServer) {
        this.balanceFromServer = balanceFromServer;
    }
}
