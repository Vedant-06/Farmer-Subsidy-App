package com.example.sen1;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mysubsidy extends AppCompatActivity
{
    private DatabaseReference reference;
    private RecyclerView mysubsidyrecycler;
    private ArrayList<pojo_mysubsidy> list;
    adaptermysubsidy adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysubsidy);
        String s = getIntent().getStringExtra("username");
        getSupportActionBar().hide();
        System.out.println(s);

        reference = FirebaseDatabase.getInstance().getReference("/").child("subsidyget").child(s);
        mysubsidyrecycler=findViewById(R.id.mysubsidyrecycler);

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                list  = new ArrayList<pojo_mysubsidy>();

            for ( DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
            {
                System.out.println(dataSnapshot1);

                pojo_mysubsidy p = dataSnapshot1.getValue(pojo_mysubsidy.class);


                if (p.getStatus().equals("1"))
                    list.add(p);

            }

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                mysubsidyrecycler.setLayoutManager(layoutManager);
                adapter = new adaptermysubsidy(Mysubsidy.this,list);
                mysubsidyrecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}


