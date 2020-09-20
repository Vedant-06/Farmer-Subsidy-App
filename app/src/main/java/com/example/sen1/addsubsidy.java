package com.example.sen1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addsubsidy extends AppCompatActivity {
    public EditText subsidyname,state1,criteria1,maxvalue1,perhectar1,detail1,document;
    private Button add,submit;
    String s;
    private DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubsidy);
//        getSupportActionBar().hide();
        subsidyname=findViewById(R.id.subsidyname);
        criteria1=findViewById(R.id.criteria);
        state1=findViewById(R.id.state);
        detail1=findViewById(R.id.details);
        document=findViewById(R.id.document);
        add=findViewById(R.id.add);
        submit= findViewById(R.id.submit);

        maxvalue1 =findViewById(R.id.maxvalue);
        final SharedPreferences preferences=getSharedPreferences("addsub",MODE_PRIVATE);
        int count1=preferences.getInt("count",1);
        count1++;
        final int count =count1;
        s=document.getText().toString();

        ref= FirebaseDatabase.getInstance().getReference("/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(document.getText()))
                    s+=document.getText().toString()+"\n";

                document.getText().clear();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {






            @Override
            public void onClick(View view)
            {

                if (!TextUtils.isEmpty(subsidyname.getText()) && !TextUtils.isEmpty(state1.getText()) && !TextUtils.isEmpty(detail1.getText())
                        && !TextUtils.isEmpty(criteria1.getText()) && !TextUtils.isEmpty(maxvalue1.getText())
                        && !TextUtils.isEmpty(s))
                {
                    String name = subsidyname.getText().toString();
                    String state = state1.getText().toString();
                    String detail = detail1.getText().toString();
                    float maxvalue = Float.valueOf(maxvalue1.getText().toString());

                    float criteria = Float.valueOf(criteria1.getText().toString());



                    ref.child("subsidy").child(String.valueOf(count)).child("mSubsidyName").setValue(name);
                    ref.child("subsidy").child(String.valueOf(count)).child("mDocuments").setValue(s);
                    ref.child("subsidy").child(String.valueOf(count)).child("mDetails").setValue(detail);
                    ref.child("subsidy").child(String.valueOf(count)).child("mMaxValue").setValue(maxvalue);
                    ref.child("subsidy").child(String.valueOf(count)).child("mState").setValue(state);
                    ref.child("subsidy").child(String.valueOf(count)).child("mCriteria").setValue(criteria);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putInt("count",count);
                    editor.apply();
                    Intent i = new Intent(addsubsidy.this,addsubsidy.class);
                    startActivity(i);
                    finish();


                }
                else
                    {
                    Toast.makeText(getBaseContext(),"empty fields",Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

}

