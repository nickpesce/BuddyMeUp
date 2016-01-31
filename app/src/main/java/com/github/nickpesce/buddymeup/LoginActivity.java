package com.github.nickpesce.buddymeup;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import packets.LoginPacket;
import packets.LoginResponsePacket;
import packets.Packet;

public class LoginActivity extends AppCompatActivity implements PacketHandler {

    private Button bLogin, bRegister;
    private EditText etName, etPass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etName = (EditText)findViewById(R.id.etLoginName);

        etPass = (EditText)findViewById(R.id.etLoginPassword);

        bLogin = (Button)findViewById(R.id.bLogin);
        MainActivity.networking.setPacketHandler(this);
        bLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.networking.send(new LoginPacket("", -1, etName.getText().toString(), etName.getText().toString(), etPass.getText().toString()));

            }
        });

        bRegister = (Button)findViewById(R.id.bRegister);
        bRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void handlePacket(Packet p) {
        switch(p.getPacketType())
        {
            case Packet.LOGIN_RESPONSE:
                LoginResponsePacket lrp = (LoginResponsePacket)p;
                if(lrp.getStatusCode() == LoginResponsePacket.Result.INCORRECT_INFO)
                {
                    LoginActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_LONG).show();
                        }
                    });
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                intent.putExtra("Name", lrp.getName());
                intent.putExtra("Id", lrp.getId());
                intent.putExtra("Friends", lrp.getFriends());

                startActivity(intent);
                break;
        }
    }
}

