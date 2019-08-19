package com.app.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;

public class Admin extends AppCompatActivity {
    TextInputEditText adminmail;
    TextInputEditText adminpass;
    Button adminbtn;
    FirebaseAuth Auth;
    ProgressBar adminbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        adminmail = findViewById(R.id.adminmail);
        adminpass = findViewById(R.id.adminpass);
        adminbtn = (Button) findViewById(R.id.adminbtn);
        adminbar = (ProgressBar)findViewById(R.id.adminbar);

        Auth = FirebaseAuth.getInstance();

        adminbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = adminmail.getText().toString().trim();
                String pass = adminpass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Admin.this, "Please Enter Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(Admin.this, "Please enter password", Toast.LENGTH_LONG);
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(Admin.this, "Password too short", Toast.LENGTH_LONG).show();
                    return;
                }
                Auth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    adminbar.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(getApplicationContext(), Adminhome.class));
                                    finish();
                                } else {
                                    Toast.makeText(Admin.this, "Login Failed !!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    //actionbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.taskar, menu);
        return true;
    }

    //handle actionbar handle clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {

            Intent intent = new Intent(Admin.this,Register.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
