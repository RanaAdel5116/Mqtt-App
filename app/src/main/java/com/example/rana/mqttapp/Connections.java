package com.example.rana.mqttapp;


import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Connections {
    private String clientID,server,name,pass;
    private int port;

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
