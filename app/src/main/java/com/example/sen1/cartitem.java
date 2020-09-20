package com.example.sen1;



import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.UUID;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class cartitem extends AppCompatActivity
{


    private DatabaseReference reference,ref2;
    private RecyclerView productsubsidyrecycler;
    private ArrayList<Productscart> list;
    adaptercart adapter;
    long f=0;
    //String s = getIntent().getStringExtra("username");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_cartitem);
        final String s = getIntent().getStringExtra("username");
        final String p = getIntent().getStringExtra("phoneno");
         Integer amountt=0;
         f=0;

        System.out.println(s);
        getSupportActionBar().hide();



        reference = FirebaseDatabase.getInstance().getReference("/").child("cart").child(s);

        productsubsidyrecycler=findViewById(R.id.producttsubsidyrecycler);

        final ArrayList<Productscart>listt  = new ArrayList<Productscart>();



        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                list  = new ArrayList<Productscart>();

                for ( DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {


                    Productscart p = dataSnapshot1.getValue(Productscart.class);




                    list.add(p);
                    f++;



                    Log.d("bo", list.size() + "");

                }

                int amount=0;
                for(int i=0;i<list.size();i++)
                {
                     amount+=Integer.parseInt(list.get(i).getProductprice());
                }

                TextView txt=(TextView)findViewById(R.id.total);
                txt.setText(String.valueOf(amount));


                final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                productsubsidyrecycler.setLayoutManager(layoutManager);
                adapter = new adaptercart(cartitem.this,list,s);
                productsubsidyrecycler.setAdapter(adapter);


            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(getBaseContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

                  final String no=p;
                  final String m=UUID.randomUUID().toString();
                  final  String msg="Thankk you for placing the order"+"Your order Id is"+m;
                  Button place=findViewById(R.id.placeorder);


                    place.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {


                            reference = FirebaseDatabase.getInstance().getReference("/").child("cart").child(s);
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    reference = FirebaseDatabase.getInstance().getReference("/");
                                    f=dataSnapshot.getChildrenCount();
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        Productscart p = dataSnapshot1.getValue(Productscart.class);
                                        String id = UUID.randomUUID().toString();
                                        reference.child("order").child(s).child(m).child(p.productname).setValue(new Productscart(p.productname, p.productprice, "", p.image, 1));


                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            reference = FirebaseDatabase.getInstance().getReference("/").child("cart").child(s);
                            reference.removeValue();

                            if(f!=0)
                            {



                                Intent i=new Intent();
                                Toast.makeText(getApplicationContext(), "Message Sent successfully!"+"your order ID is"+m,
                                        Toast.LENGTH_LONG).show();

                                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                                SmsManager sms = SmsManager.getDefault();

                                sms.sendTextMessage( no, null, msg, pi,null);
                                sms.sendTextMessage( "9427217949", null, msg, pi,null);


                                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                                intent.putExtra("username",s);

                                startActivity(intent);
                            }

                        }
                    });

    }

}
