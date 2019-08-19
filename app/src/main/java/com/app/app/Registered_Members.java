package com.app.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Registered_Members extends AppCompatActivity {

    public ArrayList<String> feed = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered__members);

        //data = FirebaseDatabase.getInstance().getReference("FeedbackMembers");
        //data = database.getReference("FeedbackMembers");

        final ListView listView = (ListView)findViewById(R.id.userslist);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersdRef = rootRef.child("Registered");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String un = ds.child("phonenum").getValue(String.class);

                    Log.d("TAG", un);
                    feed.add("\n"+"Mobile no :" +un+"\n");
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Registered_Members.this,android.R.layout.simple_list_item_1,feed);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersdRef.addListenerForSingleValueEvent(eventListener);
    }
}
