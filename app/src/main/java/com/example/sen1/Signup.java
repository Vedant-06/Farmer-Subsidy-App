package com.example.sen1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Signup extends AppCompatActivity
{


    private EditText name, username, password, birthdate, city, state , expertise , contact;
    private CheckBox checkBox;
    TextView register;
    DatabaseReference reference;
    private TextView mDisplayDate,clickme;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.signup);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        birthdate = findViewById(R.id.birthdate);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        register = findViewById(R.id.register);
        checkBox = findViewById(R.id.checkbox);
        expertise = findViewById(R.id.expertise);
        contact = findViewById(R.id.contact);
        clickme=findViewById(R.id.alreadylogin);
        clickme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Signup.this,Login.class);
                startActivity(i);
            }
        });
        reference = FirebaseDatabase.getInstance().getReference();

        mDisplayDate = (TextView) findViewById(R.id.birthdate);
        mDisplayDate.setInputType(InputType.TYPE_NULL);
        mDisplayDate.requestFocus();

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Signup.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                mDisplayDate.setText(date);
            }
        };


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBox.isChecked())
                {
                    expertise.setVisibility(View.VISIBLE);
                }
                else
                    expertise.setVisibility(View.GONE);
                    expertise.setText("");
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> statearray = new ArrayList<>();
                statearray.add("gujarat");
                statearray.add("rajasthan");
                statearray.add("maharashtra");
                final ArrayList<String> Gujarat = new ArrayList<>();
                Gujarat.add("Ahmedabad");
                Gujarat.add("Gandhinagar");
                Gujarat.add("Junagadh");
                Gujarat.add("Lunavada");
                final ArrayList<String> Maharashtra = new ArrayList<>();
                Maharashtra.add("Mumbai");
                Maharashtra.add("Pune");
                Maharashtra.add("Nagpur");
                Maharashtra.add("Nashik");
                final ArrayList<String> Rajasthan = new ArrayList<>();
                Rajasthan.add("Jodhpur");
                Rajasthan.add("Jaipur");
                Rajasthan.add("Ajmer");
                Rajasthan.add("Jaisalmer");

                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(username.getText())
                        || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(birthdate.getText()) || TextUtils.isEmpty(city.getText()) ||
                        TextUtils.isEmpty(state.getText()))
                {
                    Toast.makeText(getApplicationContext(), "empty fields", Toast.LENGTH_LONG).show();
                }
                else if (contact.getText().toString().length() != 10)
                {
                    Toast.makeText(getApplicationContext() , "Enter valid mobile number" , Toast.LENGTH_SHORT).show();
                }
                else if (!statearray.contains(state.getText().toString().toLowerCase()))
                {
                    Toast.makeText(getApplicationContext() , "Enter valid state" , Toast.LENGTH_SHORT).show();
                }
                else if (checkBox.isChecked() && TextUtils.isEmpty(expertise.getText()))
                {
                    Toast.makeText(getApplicationContext() , "enter expertise" , Toast.LENGTH_SHORT).show();
                }


                else
                    {

                    final String n = name.getText().toString().trim();
                    final String u = username.getText().toString().trim();
                    final String p = password.getText().toString().trim();
                    final String b = birthdate.getText().toString().trim();
                    final String c = city.getText().toString().trim();
                    final String s = state.getText().toString().trim().toLowerCase();
                    final String con = contact.getText().toString().trim();
                    final String exp = expertise.getText().toString().trim();
                    final String img="";
                    Query query = reference.child("login").orderByKey().equalTo(u);
                    query.addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (!dataSnapshot.exists())
                            {
                                reference.child("farmer").child(u).setValue(new pojo_farmer(n, u, p, b, c, s,-1,-1,con,img));
                                reference.child("login").child(u).setValue(p);
                                reference.child("farmerconnect").child(s).child(u).setValue(new pojo_farmerconnect(n , con , exp));
                                Intent i = new Intent(getBaseContext(), Homepage.class);
                                startActivity(i);
                                finish();
                            } else
                                {

                                Toast.makeText(getApplicationContext(), "username exists", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });

                }
            }
        });

    }

}


