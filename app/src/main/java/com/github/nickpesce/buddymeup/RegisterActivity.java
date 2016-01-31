package com.github.nickpesce.buddymeup;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import packets.NewUserPacket;
import packets.Packet;

public class RegisterActivity extends AppCompatActivity {

    private Button bLogin, bRegister;
    private EditText etName, etDisplayName, etPass;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etDisplayName = (EditText)findViewById(R.id.etRegisterDisplayName);

        etName = (EditText)findViewById(R.id.etRegisterName);

        etPass = (EditText)findViewById(R.id.etRegisterPass);

        bLogin = (Button)findViewById(R.id.bLogin);

        bRegister = (Button)findViewById(R.id.bRegister2);
        bRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.networking.send(new NewUserPacket("", -1, etName.getText().toString(), etDisplayName.getText().toString(), etPass.getText().toString()));
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

