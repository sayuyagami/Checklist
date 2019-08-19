package com.app.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersavedlist);

        listView = findViewById(R.id.lv);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        usersdRef = rootRef.child("Savedlist");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {


                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Usersavedlist.this,android.R.layout.simple_list_item_1,feed);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);

    }
}
