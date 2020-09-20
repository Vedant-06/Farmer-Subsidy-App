package com.example.sen1;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class Homepage extends AppCompatActivity {

        DatabaseReference reference;

        CardView profile, mysubsidy, marketyard, allsubsidy, agroshops, farmerconnect;
        TextView textview;


    String s="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String s= data.getStringExtra("username");
            }
        }
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_homepage);
            getSupportActionBar().hide();


            profile = findViewById(R.id.profile);
            mysubsidy = findViewById(R.id.mysubsidy);

            reference = FirebaseDatabase.getInstance().getReference("/");


            final String s = getIntent().getStringExtra("username");

            final String w;

            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    reference.child("farmer").child(s).addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                           pojo_farmer f = dataSnapshot.getValue(pojo_farmer.class);

                            Intent i = new Intent(getBaseContext(), Profile.class);
                            i.putExtra("username", f.username);
                            reference.removeEventListener(this);
                            startActivityForResult(i,1);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });





        CardView farmerConnect = findViewById(R.id.farmerconnect);
        farmerConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , farmerconnect.class);
                startActivity(intent);
            }
        });



        mysubsidy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    reference.child("farmer").child(s).addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {

                            pojo_farmer f = dataSnapshot.getValue(pojo_farmer.class);

                            Intent i = new Intent(getBaseContext(), Mysubsidy.class);
                            i.putExtra("username", f.username);
                            reference.removeEventListener(this);
                            startActivity(i);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });
                }
            });

            CardView allsubsidy = findViewById(R.id.allsubsidy);
            allsubsidy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(getBaseContext(),allsubsidy.class);
                    startActivity(i);

                }
            });

            CardView customerservice = findViewById(R.id.marketyard);
            customerservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext() , Contactus.class);
                startActivity(intent);
            }
        });

            CardView agroshop = findViewById(R.id.agroshops);
            agroshop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.child("farmer").child(s).addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            pojo_farmer f = dataSnapshot.getValue(pojo_farmer.class);

                            Intent i = new Intent(getBaseContext(), Myproducts.class);
                            i.putExtra("username", f.username);
                            i.putExtra("phoneno",f.contact);
                            reference.removeEventListener(this);
                            startActivity(i);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            final ImageView cart=(ImageView)findViewById(R.id.cart);
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reference.child("farmer").child(s).addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            pojo_farmer f = dataSnapshot.getValue(pojo_farmer.class);

                            Intent i = new Intent(getBaseContext(), cartitem.class);
                            i.putExtra("username", f.username);
                            i.putExtra("phoneno",f.contact);
                            reference.removeEventListener(this);
                            startActivity(i);
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {

                        }
                    });
                }
            });

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
            {
                NotificationChannel n = new NotificationChannel("MyNotification1", "MyNotification1", NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager nm = getSystemService(NotificationManager.class);
                nm.createNotificationChannel(n);

            }
            FirebaseMessaging.getInstance().subscribeToTopic("General");




        }








}
