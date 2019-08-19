package com.app.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private EditText userno;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userno = findViewById(R.id.userno);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.verifybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = userno.getText().toString().trim();

                SharedPreferences share = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("mobileno",number);
                editor.apply();

                if (number.length() > 6) {
                    if (number.length() > 10 || number.length() < 10) {
                        userno.setError("Valid number is required");
                        userno.requestFocus();
                        return;
                    }
                    String phonenumber = "+" + 91 + number;
                    Intent intent = new Intent(Register.this, Activity2.class);
                    intent.putExtra("phonenumber", phonenumber);
                    startActivity(intent);

                }else if (number.equals("112084")){
                    Intent intent = new Intent(Register.this,Admin.class);
                    startActivity(intent);
                }else if (number.isEmpty()){
                    userno.setError("Valid number is required");
                    userno.requestFocus();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser()!= null){
            Intent intent = new Intent(this,UserHome.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            finish();
            startActivity(intent);
        }
    }
}
