package com.app.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Usersavedlist extends AppCompatActivity {
    ArrayList<String> feed = new ArrayList<>();
    DatabaseReference usersdRef;
    ListView listView;
    public static String listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersavedlist);

        listView = findViewById(R.id.lv);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        SharedPreferences share = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String num = share.getString("mobileno","");

        usersdRef = rootRef.child("Savedlist").child(num);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = ds.getKey();
                    feed.add(name);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Usersavedlist.this,android.R.layout.simple_list_item_1,feed);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i,
                                    final long id) {
                listPosition = feed.get(i);
                Intent intent = new Intent(Usersavedlist.this,NextActivity.class);
                startActivity(intent);
            }
        });
    }
}
