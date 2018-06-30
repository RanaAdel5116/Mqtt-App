package com.example.rana.mqttapp;


import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Connections {
    String clientID,server,name,pass;
    int port;

    //static MqttAndroidClient [] clients = new MqttAndroidClient[1];
    static Map<String, MqttAndroidClient> clients = new HashMap<>();

    public Connections(String clientID,String server, int port,String name,String pass,MqttAndroidClient client){
        this.clientID = clientID;
        this.server = server;
        this.port = port;
        this.name = name;
        this.pass = pass;
        clients.put(clientID,client);
    }
}