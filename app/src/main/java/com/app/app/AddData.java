package com.app.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddData extends AppCompatActivity {

    DatabaseReference reff;
    Viewdatalist list;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        final EditText txt = findViewById(R.id.txt);
        submit = findViewById(R.id.submit);
        list = new Viewdatalist();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                reff = rootRef.child("Viewdatalist");
                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            int un = ds.child("txtid").getValue(int.class);
                            if (un != 0){
                                int num = un + 1;
                                String data = txt.getText().toString().trim();
                                list.setTxtid(num);
                                list.setTxt(data);
                                reff.child(String.valueOf(num)).setValue(list);
                                Toast.makeText(AddData.this,"Data added successfully!!",Toast.LENGTH_LONG).show();
                                txt.setText("");
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                };
                reff.addListenerForSingleValueEvent(eventListener);

            }
        });
    }

}
