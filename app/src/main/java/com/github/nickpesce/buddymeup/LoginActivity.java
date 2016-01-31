package com.github.nickpesce.buddymeup;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button bLogin;
    private EditText etName, etPhone;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etName = (EditText)findViewById(R.id.etLoginName);

        etPhone = (EditText)findViewById(R.id.etLoginPhone);

        bLogin = (Button)findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Phone", etPhone.getText().toString());
                intent.putExtra("Name", etName.getText().toString());
                startActivity(intent);
            }
        });
    }
}

