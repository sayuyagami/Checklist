package com.app.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class UserHome extends AppCompatActivity {

    FirebaseAuth mauth;
    DatabaseReference reff,userRef;

    String dd;
    private static int arr;
    private ListView lv;
    private ArrayList<Viewdatalist> modelArrayList;
    private CustomAdapter customAdapter;
    private Button btnselect, btndeselect, btnnext;
    Savedlist info;

    public ArrayList<String> feed = new ArrayList<>();
    public ArrayList<String> dlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        lv = findViewById(R.id.lv);
        info = new Savedlist();
        btnselect = (Button) findViewById(R.id.select);
        btndeselect = (Button) findViewById(R.id.deselect);
        btnnext = (Button) findViewById(R.id.next);
        mauth = FirebaseAuth.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("Savedlist");

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Viewdatalist");
        final ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    final Integer cid = ds.child("txtid").getValue(int.class);
                    dd = ds.child("txt").getValue(String.class);

                    if (dd.length()>25) {
                        dd=dd.substring(0,25)+".....";
                        //result.setText(Html.fromHtml(dd+"<font color='red'> <u>View More</u></font>"));
                    }else{
                        dd = dd;
                    }
                    if (cid != 0){
                        feed.add("\n" + dd + "\n" + "");
                        modelArrayList = getViewdatalist(false);
                        customAdapter = new CustomAdapter(UserHome.this,modelArrayList);
                        lv.setAdapter(customAdapter);
                        //arr = new String[]{dd};
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        userRef.addListenerForSingleValueEvent(eventListener);

            btnselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelArrayList = getViewdatalist(true);
                    customAdapter = new CustomAdapter(UserHome.this,modelArrayList);
                    lv.setAdapter(customAdapter);
                }
            });
            btndeselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelArrayList = getViewdatalist(false);
                    customAdapter = new CustomAdapter(UserHome.this,modelArrayList);
                    lv.setAdapter(customAdapter);
                }
            });
            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //final Button replybtn = feed.get(position);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UserHome.this);
                    builder.setTitle("Save As");

                    // Set up the input
                    final EditText input = new EditText(UserHome.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    input.setText(" list 1");
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int listposition) {
                            final String m_text = input.getText().toString().trim();
                            for (int i = 0; i < CustomAdapter.modelArrayList.size(); i++){
                                if(CustomAdapter.modelArrayList.get(i).getSelected()) {
                                    arr = i+1;
                                    final int finalI = i;
                                    userRef.orderByChild("txtid").equalTo(arr).limitToFirst(1).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                String slist = ds.child("txt").getValue(String.class);
                                                SharedPreferences share = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                                                String num = share.getString("mobileno","");

                                                dlist.add(slist);
                                                reff.child(num).child(m_text).child(String.valueOf(finalI)).setValue(dlist.get(finalI));
                                                Toast.makeText(UserHome.this,m_text+" saved",Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            });
    }



    private ArrayList<Viewdatalist> getViewdatalist(boolean isSelect) {
        ArrayList<Viewdatalist> list = new ArrayList<>();
        for (int i = 0; i < feed.size(); i++) {

            Viewdatalist model = new Viewdatalist();
            model.setSelected(isSelect);
            model.setTxt(feed.get(i));
            list.add(model);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        menu.getItem(0).getSubMenu().getItem(0).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.saved){
            Intent intent = new Intent(UserHome.this,Usersavedlist.class);
            startActivity(intent);
        }
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(UserHome.this,Register.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            Toast.makeText(this,"Successfully Logged Out",Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
}
