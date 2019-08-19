package com.app.app;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dataview extends AppCompatActivity {

    TextView a;
    ImageView b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataview);

        a = findViewById(R.id.a);
        //b =findViewById(R.id.b);

        int pos = CustomAdapter.num;
        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Viewdatalist").child(String.valueOf(pos));
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String data = dataSnapshot.child("txt").getValue(String.class);
                String img = dataSnapshot.child("image").getValue(String.class);
                //Uri mUri = Uri.parse(img);

                a.setText(data);
               // b.setImageURI(mUri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
