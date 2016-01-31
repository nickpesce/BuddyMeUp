package com.github.nickpesce.buddymeup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import packets.NewUserPacket;
import packets.PairRequestPacket;

public class ActivityAddFriend extends AppCompatActivity {

    EditText etName;
    Button bAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        etName = (EditText)findViewById(R.id.etFriendName);
        bAdd = (Button)findViewById(R.id.bAddFriend);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("Friend", etName.getText().toString());
                setResult(0, data);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
