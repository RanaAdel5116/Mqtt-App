package com.example.rana.mqttapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Tab4 extends Fragment {

    TextView topic;
    Switch hello ,bye;
    String clienId;
    ListView listview;
    Button display;
    CustomAdapter customAdapter;
    static Map<String,ArrayList<String> > megs = new HashMap<>();
    ArrayList<String> Mmegs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab4, container, false);

        clienId = NewConnection.getInstant().getId();
        MqttAndroidClient client = Connections.clients.get(clienId);

        topic = rootView.findViewById(R.id.topic);
        hello = rootView.findViewById(R.id.switch3);
        bye = rootView.findViewById(R.id.switch4);
        display = rootView.findViewById(R.id.dis);
        listview = rootView.findViewById(R.id.listview);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topic.getText().toString().isEmpty()) {
                }else {
                    if(!(megs.get(topic.getText().toString())== null)) {
                        Mmegs = megs.get(topic.getText().toString());
                        customAdapter = new CustomAdapter();
                        listview.setAdapter(customAdapter);
                    }
                    else listview.setAdapter(null);
                }
            }
        });


        final MqttServices mqttServices = new MqttServices(client);


        hello.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mqttServices.publish(topic.getText().toString(),"hello",0,false);
                }
            }
        });
        bye.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mqttServices.publish(topic.getText().toString(),"bye",0,false);
                }
            }
        });

        return rootView;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
             return Mmegs.size();
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

            TextView message = view.findViewById(R.id.meg);

            message.setText(Mmegs.get(i));

            return view;
        }
    }
}
