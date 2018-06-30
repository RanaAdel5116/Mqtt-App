package com.example.rana.mqttapp;


import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import java.util.ArrayList;

public class ConnectionsCache {
    public static final String COMPLAINT_TYPES_TAG = "complaintTypes";


    private int Count;
    private ArrayList<String> Content;

    public static ConnectionsCache fromCash(SharedPreferences sPrefs) {
        ConnectionsCache connectionsCache = null;
        if (!TextUtils.isEmpty(sPrefs.getString(COMPLAINT_TYPES_TAG, ""))) {
            connectionsCache = new Gson().fromJson(sPrefs.getString(COMPLAINT_TYPES_TAG, ""), ConnectionsCache.class);
        }
        return connectionsCache;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public ArrayList<String> getContent() {
        return Content;
    }

    public void setContent(ArrayList<String> content) {
        Content = content;
    }

    public void cashCompliantTypes(SharedPreferences sPrefs) {
        sPrefs.edit().putString(COMPLAINT_TYPES_TAG, new Gson().toJson(ConnectionsCache.this)).apply();
    }
}
