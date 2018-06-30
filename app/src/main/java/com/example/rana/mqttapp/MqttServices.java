package com.example.rana.mqttapp;

import android.util.Log;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MqttServices {

    private String meg;
    private MqttAndroidClient client;
    public MqttServices(MqttAndroidClient client){
        this.client = client;
    }

    public void connect(){
        meg = "Client: " +client.getClientId() +" has connected";
        Tab1History.megs.add(meg);
    }


    public void subscribe(final String topic, int qos) {

    try {
        IMqttToken subToken = client.subscribe(topic, qos);
        subToken.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                // The message was published

                meg = "Subscribed to " + topic;
                Tab1History.megs.add(meg);
                Log.d(TAG, "Success");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken,
                                  Throwable exception) {
                // The subscription could not be performed, maybe the user was not
                // authorized to subscribe on the specified topic e.g. using wildcards
                Log.d(TAG, "Failure");
            }
        });
    } catch (MqttException e) {
        e.printStackTrace();
    }
}

public void publish(String topic,String mes,int qos,boolean ret){

    byte[] encodedPayload = new byte[0];

    try {
        encodedPayload = mes.getBytes("UTF-8");
        MqttMessage message = new MqttMessage(encodedPayload);
        message.setQos(qos);
        if(ret){
            message.setRetained(true);
        }
        client.publish(topic, message);

        meg = "Published message: " +mes+ " to " + topic ;
        Tab1History.megs.add(meg);

        ArrayList<String> arr = new ArrayList<>();

        if(!Tab4.megs.isEmpty()) {
            arr = Tab4.megs.get(topic);
        }
            arr.add(mes);
            Tab4.megs.put(topic, arr);
    } catch (UnsupportedEncodingException | MqttException e) {
        e.printStackTrace();
    }
}

}
