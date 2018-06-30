package com.example.rana.mqttapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;

import static android.content.ContentValues.TAG;

public class Tab3Subscribe extends Fragment {

    private Button sub;
    private TextView topic;
    private RadioButton r0,r1,r2;
    private int qos = 0;
    private String clienId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        clienId = NewConnection.getInstant().getId();

        MqttAndroidClient client = Connections.clients.get(clienId);

        final MqttServices mqttServices = new MqttServices(client);

        topic = rootView.findViewById(R.id.topic);
        r0 = rootView.findViewById(R.id.radioButton0);
        r1 = rootView.findViewById(R.id.radioButton1);
        r2 = rootView.findViewById(R.id.radioButton2);

        sub = rootView.findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(r0.isChecked()){
                    qos = 0;
                }else if(r1.isChecked()){
                    qos = 1;
                }else qos = 2;

                mqttServices.subscribe(topic.getText().toString(),qos);
                topic.setText("");
                Log.d(TAG,"succ"+qos);
            }
        });
        return rootView;
    }


}
