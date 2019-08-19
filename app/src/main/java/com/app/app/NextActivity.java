package com.app.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NextActivity extends AppCompatActivity {

    ListView a;
    ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        SharedPreferences share = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String num = share.getString("mobileno","");

        a = findViewById(R.id.tv);

        String pos = Usersavedlist.listPosition;
        final DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Savedlist").child(num).child(pos);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    String data = (String) ds.getValue();
                    arr.add("\n"+data+"\n");
                    //arr.add("");
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NextActivity.this,android.R.layout.simple_list_item_1,arr);
                a.setAdapter(arrayAdapter);
                a.setBackgroundColor(Color.rgb(224, 224, 224));
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
