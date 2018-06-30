package com.example.rana.mqttapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList <String> connections = new ArrayList<>();
    Button newConnection;
    ListView listview;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(connections.isEmpty()){
            Toast.makeText(MainActivity.this,"No Connections found!",Toast.LENGTH_LONG).show();
        }

        listview = findViewById(R.id.listview);
        customAdapter = new CustomAdapter();
        listview.setAdapter(customAdapter);

        newConnection = findViewById(R.id.new_conn);

        newConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewConnection.class);
                startActivityForResult(intent,1);
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(MainActivity.this,ConnectionDetails.class);
                startActivity(i);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String conn = data.getStringExtra("conn");
                connections.add(conn);
                listview.setAdapter(customAdapter);
            }
        }
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() { //length of custom list view
            return connections.size();
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
            view = getLayoutInflater().inflate(R.layout.layout,null);

            TextView clientId = view.findViewById(R.id.client_id);
            clientId.setText(connections.get(i));

            return view;
        }
    }

}

