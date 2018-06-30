package com.example.rana.mqttapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Tab1History extends Fragment {

    private ListView listview;
    private CustomAdapter customAdapter;
    static ArrayList<String> megs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1history, container, false);

        listview = rootView.findViewById(R.id.listview);
        customAdapter = new CustomAdapter();
        listview.setAdapter(customAdapter);

        return rootView;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() { //length of custom list view
            return megs.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.layout1,null);

            TextView mes = view.findViewById(R.id.meg);
            mes.setText(megs.get(i));

            return view;
        }
    }

}
