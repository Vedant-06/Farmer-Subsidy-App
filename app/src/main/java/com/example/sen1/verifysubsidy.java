package com.example.sen1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class verifysubsidy extends AppCompatActivity
{
    private DatabaseReference reference;
    private RecyclerView notifysubsidyrecycler;
    private ArrayList<pojo_subsidy> list;
    public EditText uname;
    private Button find;
    adapterverifysubsidy adapter;
    HashMap<String ,String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifysubsidy);
        reference = FirebaseDatabase.getInstance().getReference("/");
        notifysubsidyrecycler=findViewById(R.id.notifysubsidyrecycler);
        find = findViewById(R.id.find);
        uname= findViewById(R.id.verifyusername);
        getSupportActionBar().hide();
        name= new HashMap<String,String>();
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("subsidyget").child(uname.getText().toString().trim()).addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list  = new ArrayList<pojo_subsidy>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                        {

                            pojo_subsidy p = dataSnapshot1.getValue(pojo_subsidy.class);
                            if((p.getStatus()).equals("-1")){
                                list.add(p);
                            }
                        }
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                        notifysubsidyrecycler.setLayoutManager(layoutManager);
                        adapter = new adapterverifysubsidy(verifysubsidy
                                .this,list,uname.getText().toString().trim(),uname);
                        notifysubsidyrecycler.setAdapter(adapter);
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
