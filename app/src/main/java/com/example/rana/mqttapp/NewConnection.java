package com.example.rana.mqttapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class NewConnection extends AppCompatActivity {

    EditText id,server,name,pass,port,timeout,keepAlive;
    Button submit;
    static NewConnection instant;
    String clientId;
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


        submit = findViewById(R.id._submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clientId = id.getText().toString();
                MqttAndroidClient client =
                        new MqttAndroidClient(NewConnection.this.getApplicationContext(), "tcp://broker.hivemq.com:"+port.getText().toString(),
                                clientId,new MemoryPersistence(), MqttAndroidClient.Ack.AUTO_ACK);


                final Connections connections = new Connections(id.getText().toString(),
                server.getText().toString(),Integer.parseInt(port.getText().toString()),
                name.getText().toString(),pass.getText().toString(),client);



                    final MqttServices ms = new MqttServices(client);

                    Toast.makeText(NewConnection.this,client.getClientId().toString(),Toast.LENGTH_LONG).show();
                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // We are connected
                            Toast.makeText(NewConnection.this, "onSuccess", Toast.LENGTH_SHORT).show();

                            ms.connect();
                            Intent intent = new Intent();
                            intent.putExtra("conn",id.getText().toString());
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            // Something went wrong e.g. connection timeout or firewall problems
                            Toast.makeText(NewConnection.this, exception.toString(), Toast.LENGTH_SHORT).show();
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
