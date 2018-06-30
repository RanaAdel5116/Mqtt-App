package com.example.rana.mqttapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.UnsupportedEncodingException;

public class NewConnection extends AppCompatActivity {

    private EditText id,server,name,pass,port,timeout,keepAlive,ssl;
    private TextView topic,meg;
    private RadioButton r0,r1,r2;
    private CheckBox ret;
    private Button submit;
    private Switch cleanS,adv,lastwill;
    static NewConnection instant;
    private String clientId;
    private boolean ret1 = false;
    private int qos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);

        instant = this;
        id = findViewById(R.id.cli_id);
        server = findViewById(R.id.server);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        port = findViewById(R.id.port);
        ssl = findViewById(R.id.ssl);
        timeout = findViewById(R.id.timeout);
        keepAlive = findViewById(R.id.keep_alive);
        cleanS = findViewById(R.id.switch1);
        adv = findViewById(R.id.adv);
        lastwill = findViewById(R.id.lastwill);

        topic = findViewById(R.id.topic);
        meg = findViewById(R.id.mes);
        r0 = findViewById(R.id.radioButton0);
        r1 = findViewById(R.id.radioButton1);
        r2 = findViewById(R.id.radioButton2);

        ret = findViewById(R.id.ret);

        adv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    keepAlive.setFocusableInTouchMode(true);
                    timeout.setFocusableInTouchMode(true);
                    name.setFocusableInTouchMode(true);
                    pass.setFocusableInTouchMode(true);
                    ssl.setFocusableInTouchMode(true);

                } else {
                        keepAlive.setFocusableInTouchMode(false);
                        timeout.setFocusableInTouchMode(false);
                        name.setFocusableInTouchMode(false);
                        pass.setFocusableInTouchMode(false);
                        ssl.setFocusableInTouchMode(false);
                    }
                }
            });

        lastwill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    topic.setFocusableInTouchMode(true);
                    meg.setFocusableInTouchMode(true);
                    ret.setFocusableInTouchMode(true);
                    r0.setFocusableInTouchMode(true);
                    r1.setFocusableInTouchMode(true);
                    r2.setFocusableInTouchMode(true);
                }else{
                    topic.setFocusableInTouchMode(false);
                    meg.setFocusableInTouchMode(false);
                    ret.setFocusableInTouchMode(false);
                    r0.setFocusableInTouchMode(false);
                    r1.setFocusableInTouchMode(false);
                    r2.setFocusableInTouchMode(false);
                }
            }
        });

        submit = findViewById(R.id._submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                clientId = id.getText().toString();
                String uri="tcp://";
                uri += server.getText().toString();
                if(!ssl.getText().toString().isEmpty()){
                    uri = "ssl://";
                    uri += ssl.getText().toString();
                }
                uri += ":" +port.getText().toString();

                final MqttConnectOptions options = new MqttConnectOptions();


                cleanS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                           options.setCleanSession(true);
                        }
                    }
                });

                adv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            if (keepAlive.getText().toString().isEmpty() || timeout.getText().toString().isEmpty() ||
                                    name.getText().toString().isEmpty() || pass.getText().toString().isEmpty())
                                Toast.makeText(NewConnection.this, "Fill all fields", Toast.LENGTH_LONG).show();
                            else {
                                options.setKeepAliveInterval(Integer.parseInt(keepAlive.getText().toString()));
                                options.setConnectionTimeout(Integer.parseInt(timeout.getText().toString()));
                                options.setUserName(name.getText().toString());
                                options.setPassword(pass.getText().toString().toCharArray());
                            }
                        }
                    }
                });


                lastwill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked) {
                            if (topic.getText().toString().isEmpty() || meg.getText().toString().isEmpty()) {
                                Toast.makeText(NewConnection.this, "Fill all fields", Toast.LENGTH_LONG).show();
                            } else {
                                if (r0.isChecked()) {
                                    qos = 0;
                                } else if (r1.isChecked()) {
                                    qos = 1;
                                } else qos = 2;

                                if (ret.isChecked()) {
                                    ret1 = true;
                                } else ret1 = false;

                                try {
                                    options.setWill(topic.getText().toString(), meg.getText().toString().getBytes("UTF-8"), qos, ret1);
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }});




                MqttAndroidClient client =
                        new MqttAndroidClient(NewConnection.this.getApplicationContext(), uri,
                                clientId,new MemoryPersistence(), MqttAndroidClient.Ack.AUTO_ACK);


               final Connections connections = new Connections(id.getText().toString(),
                server.getText().toString(),Integer.parseInt(port.getText().toString()),
                name.getText().toString(),pass.getText().toString(),client);



                    final MqttServices ms = new MqttServices(client);

                try {
                    IMqttToken token = client.connect(options);
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            Toast.makeText(NewConnection.this, "Success", Toast.LENGTH_SHORT).show();
                            Tab1History.megs.add("Client: " +id.getText().toString() +" has created");
                            ms.connect();
                            Intent intent = new Intent();
                            intent.putExtra("conn",id.getText().toString());
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Toast.makeText(NewConnection.this, exception.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static NewConnection getInstant(){
        return instant;
    }
    public String getId(){
        return this.clientId;
    }
}
