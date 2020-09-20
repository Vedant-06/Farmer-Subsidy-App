package com.example.sen1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Governmentmain extends AppCompatActivity{
        private CardView update;
        private  CardView addsubsidy,verifysubsidy,agroshops,market;


        @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governmentmain);

            addsubsidy=findViewById(R.id.addsubsidy);
            verifysubsidy=findViewById(R.id.verifysubsidy);
            market=findViewById(R.id.market);

            getSupportActionBar().hide();



            addsubsidy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getBaseContext(),addsubsidy.class);
                    startActivity(i);
                }
            });

            verifysubsidy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getBaseContext(),verifysubsidy.class);
                    startActivity(i);
                }
            });

            market.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getBaseContext(),governmentaddproduct.class);
                    startActivity(i);
                }
            });



        }

    @Override
    public void onBackPressed() {
        finishAffinity();
            finish();
        super.onBackPressed();
    }
}
