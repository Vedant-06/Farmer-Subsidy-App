package com.example.sen1;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class productdetails extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference();

        TextView introduction= findViewById(R.id.proname);
        TextView details= findViewById(R.id.proprice);
        TextView documents=findViewById(R.id.prodes);
        ImageView image=findViewById(R.id.proimg);

        final String name=getIntent().getStringExtra("mName");
        final String price=getIntent().getStringExtra("mPrice");
        final String dis=getIntent().getStringExtra("mDetails");
        final String img=getIntent().getStringExtra("mImage");
        final String username=getIntent().getStringExtra("username");
        final String phoneno=getIntent().getStringExtra("phoneno");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().hide();
        introduction.setText(name);
        details.setText(price);
        documents.setText(dis);
        Glide.with(productdetails.this)
                .load(img)
                .into(image);

        Button addtocart=(Button)findViewById(R.id.addtocart);
        reference.child("cart").child(username);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child("cart").child(username).child(username+name).setValue(new Productscart(name,price,dis,img,1));
                Intent i=new Intent(productdetails.this,Myproducts.class);
                i.putExtra("username",username);
                i.putExtra("phoneno",phoneno);
                startActivity(i);
                finish();

            }
        });
    }
}
