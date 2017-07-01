package com.avi13smartestgmail.otp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Acces_Database extends AppCompatActivity {


    Button buttonSign;
    String temp_key;
    TextView tv;

    EditText editTextname;
    EditText editTextnumber;
    EditText editTextmail;
    SwipeRefreshLayout swipeRefreshLayout;

    DatabaseReference root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acces__database);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


/*       swipeRefreshLayout.setOnRefreshListener(
               new SwipeRefreshLayout.OnRefreshListener() {
                   @Override
                   public void onRefresh() {
                        DoYourUpdate();
                   }
               }
       );   */



        root = FirebaseDatabase.getInstance().getReference();
        tv = (TextView) findViewById(R.id.textViewdat);
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                newChild(dataSnapshot);
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                newChild(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                newChild(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


 //   private void DoYourUpdate(){
//
  //      swipeRefreshLayout.setRefreshing(false);

//    }


    private void newChild(DataSnapshot dataSnapshot) {
        String nm, em, num;
        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            em = (String) ((DataSnapshot) i.next()).getValue();
            nm = (String) ((DataSnapshot) i.next()).getValue();
            num = (String) ((DataSnapshot) i.next()).getValue();
            tv.append(em + "\n" + nm + "\n" + num + "\n\n");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
