package com.example.sen1;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class Myproducts extends AppCompatActivity {


    private DatabaseReference reference;
    private RecyclerView productsubsidyrecycler;
    private ArrayList<Products> list;
    adapterproducts adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        final String s = getIntent().getStringExtra("username");
        final String p = getIntent().getStringExtra("phoneno");

        Intent intent = new Intent();
        intent.putExtra("username", s);
        setResult(RESULT_OK, intent);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#e6ffe6"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        reference = FirebaseDatabase.getInstance().getReference("/").child("product");

        productsubsidyrecycler = findViewById(R.id.productsubsidyrecycler);

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                list = new ArrayList<Products>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Products p = dataSnapshot1.getValue(Products.class);
                    list.add(p);

                }

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
                productsubsidyrecycler.setLayoutManager(layoutManager);
                adapter = new adapterproducts(Myproducts.this, list, s, p);
                productsubsidyrecycler.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.menusearch);
        SearchView searchView=(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed()
    {

        super.onBackPressed();
        finish();
    }
}
