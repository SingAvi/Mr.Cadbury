package com.avi13smartestgmail.otp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.app.ProgressDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avi13smartestgmail.otp.Manifest.permission;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.R.id.message;


public class MainActivity extends AppCompatActivity {


    EditText editTextname;
    EditText editTextnumber;
    EditText editTextmail;
    Button buttonSign;


    String  temp_key;

    ProgressDialog progressDialog;
    DatabaseReference root;
    TextView tv;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonSign = (Button) findViewById(R.id.signInBtn);
        tv=(TextView)findViewById(R.id.textViewdat);




//FIREBASE
        firebaseAuth = FirebaseAuth.getInstance();
        tv=(TextView)findViewById(R.id.textViewdat);
        root = FirebaseDatabase.getInstance().getReference();

        //END


        editTextname = (EditText) findViewById(R.id.name);
        if (editTextname.equals("")) {
            Toast.makeText(this, "plz enter your name ", Toast.LENGTH_SHORT).show();
            return;
        }
        editTextmail = (EditText)findViewById(R.id.email);
        if (editTextmail.equals("")) {
            Toast.makeText(this,"plz enter email-id",Toast.LENGTH_SHORT).show();
            return;
        }
        editTextnumber = (EditText) findViewById(R.id.numb);
        if (editTextnumber.equals("")) {
            Toast.makeText(this, "plz enter your number ", Toast.LENGTH_SHORT).show();
            return;
        }
        buttonSign = (Button) findViewById(R.id.signInBtn);

        progressDialog = new ProgressDialog(this);




        //attaching listener to button
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temp_key=root.push().getKey();
                DatabaseReference ab=root.child(temp_key);


                Map<String,Object> map = new HashMap<>();
                map.put("email",editTextmail.getText().toString());
                map.put("name",editTextname.getText().toString());
                map.put("number",editTextnumber.getText().toString());
                ab.updateChildren(map);
                onClicks();

            }
        });


    }






    private void registerUser() {

        //getting email and password from edit texts
        String name = editTextname.getText().toString().trim();
        String mail = editTextmail.getText().toString().trim();
        String number = editTextnumber.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(this, "Please enter your Email id", Toast.LENGTH_LONG).show();
            return;
        }


        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Please enter number", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(mail,number)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                        } else {
                            //display some message here
                            Toast.makeText(MainActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
         }


        public void onClicks (){
            //calling register method on click
            registerUser();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.det) {

            startActivity(new Intent(MainActivity.this,Acces_Database.class));




        }

        return super.onOptionsItemSelected(item);
    }



}
