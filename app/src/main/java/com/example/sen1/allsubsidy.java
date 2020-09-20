package com.example.sen1;



import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class allsubsidy extends AppCompatActivity {


    private RecyclerView subsidyrecycler;
    public ArrayList<pojo_allsubsidy> list;
    adapterallsubsidy adapter;
    DatabaseReference reference;
    String x;
    String state[];
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsubsidy);
        state= new String[3];
        state[0]="Gujarat";
        state[1]="Maharashtra";
        state[2]="All";
        submit= findViewById(R.id.submit);
        getSupportActionBar().hide();

        Spinner spin = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> madapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state);

        madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(madapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                x= state[i];
                x=x.toLowerCase();
                x=x.trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






        reference = FirebaseDatabase.getInstance().getReference("/").child("subsidy");

        subsidyrecycler=findViewById(R.id.subsidyrecycler);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        list = new ArrayList<pojo_allsubsidy>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {
                            pojo_allsubsidy p = dataSnapshot1.getValue(pojo_allsubsidy.class);
                            if((p.getmState().toLowerCase().trim()).equals(x))
                                list.add(p);
                            Log.d("boss",list.size()+"");
                        }
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                        subsidyrecycler.setLayoutManager(layoutManager);
                        adapter = new adapterallsubsidy(allsubsidy.this,list);
                        subsidyrecycler.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(getBaseContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}


