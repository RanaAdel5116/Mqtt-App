package com.example.rana.mqttapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;

import static android.content.ContentValues.TAG;


public class Tab2Publish extends Fragment {

    TextView topic,meg;
    RadioButton r0,r1,r2;
    CheckBox ret;
    Button publish;
    int qos = 0;
    String clienId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        clienId = NewConnection.getInstant().getId();


        MqttAndroidClient client = Connections.clients.get(clienId);

        final MqttServices mqttServices = new MqttServices(client);

        topic = rootView.findViewById(R.id.topic);
        meg = rootView.findViewById(R.id.mes);
        r0 = rootView.findViewById(R.id.radioButton0);
        r1 = rootView.findViewById(R.id.radioButton1);
        r2 = rootView.findViewById(R.id.radioButton2);

        ret = rootView.findViewById(R.id.ret);

        publish = rootView.findViewById(R.id.publ);
        publish.setOnClickListener(new View.OnClickListener() {
            boolean ret1;
            @Override
            public void onClick(View v) {
                if(r0.isChecked()){
                    qos = 0;
                }else if(r1.isChecked()){
                    qos = 1;
                }else qos = 2;

                if(ret.isChecked()){
                    ret1 = true;
                }else ret1 = false;

                mqttServices.publish(topic.getText().toString(),meg.getText().toString(),qos,ret1);
                Log.d(TAG,"succ pub" + qos);
            }
        });

        return rootView;
    }
}
