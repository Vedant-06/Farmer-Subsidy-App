package com.example.sen1;



import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class susbidydetails extends AppCompatActivity {
    Button apply;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidydetails);
        TextView introduction= findViewById(R.id.introduction);
        TextView details= findViewById(R.id.details);
        TextView documents=findViewById(R.id.document);
        apply=(Button)findViewById(R.id.apply);
        ref= FirebaseDatabase.getInstance().getReference();
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences preferences1=getSharedPreferences("applysub",MODE_PRIVATE);
        int count1=preferences1.getInt("count",0);
        count1++;
        final int count=count1;
        final SharedPreferences.Editor editor=preferences.edit();
        final String username=preferences.getString("username","");
        final String intro=getIntent().getStringExtra("mSubsidy");
        String detail=getIntent().getStringExtra("mDetails");
        String document=getIntent().getStringExtra("mDocuments");
        final String amount=getIntent().getStringExtra("mAmount");
        introduction.setText(intro);
        details.setText(detail);
        documents.setText(document);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("subsidyget").child(username).child(String.valueOf(count)).child("amount").setValue(amount);
                ref.child("subsidyget").child(username).child(String.valueOf(count)).child("status").setValue("-1");
                ref.child("subsidyget").child(username).child(String.valueOf(count)).child("subsidyname").setValue(intro);
                editor.putInt("count",count);
                editor.apply();

            }
        });
    }
}
