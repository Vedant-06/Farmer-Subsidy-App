package com.example.sen1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    boolean flag;
    TextView login;
    DatabaseReference reference;
    private CheckBox saveLoginCheckBox;
    TextView register;
    CheckBox showPass,rememberme;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        String pass=password.getText().toString();
        String user=username.getText().toString();
        TextView a=findViewById(R.id.alreadysignup);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Login.this,Signup.class);
                startActivity(i);
                finish();
            }
        });

        showPass = (CheckBox) findViewById(R.id.showPass);
        rememberme=(CheckBox)findViewById(R.id.saveLoginCheckBox);

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox=preferences.getString("rememberme","");
        String checkuser=preferences.getString("username","");
        String checkpass=preferences.getString("password","");
        if(checkbox.equals("true"))
        {
                Intent i=new Intent(Login.this,Homepage.class);
                i.putExtra("username",checkuser);
                startActivity(i);
                finish();
        }
        else
        {

        }

        rememberme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("rememberme","true");
                    editor.apply();
                }
                else if(!buttonView.isChecked()){
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putString("rememberme","false");
                    editor.apply();
                }
            }
        });

        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!isChecked)
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                else
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });


        login = findViewById(R.id.login);


        reference = FirebaseDatabase.getInstance().getReference();

        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final String s = username.getText().toString();
                final String p = password.getText().toString();
                if(s.equals("government")&&p.equals("12345"))
                {
                    Intent i=new Intent(Login.this,Governmentmain.class);
                    SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    flag=false;
                    editor.putString("rememberme","false");
                    editor.apply();
                    startActivity(i);
                    finish();

                }


                flag=false;
                try {
                    reference.child("login").addListenerForSingleValueEvent(new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            for(DataSnapshot users: dataSnapshot.getChildren())
                            {
                                String username = users.getKey().toString();

                                if(username.equals(s))
                                {
                                    flag = true;
                                    String pass = dataSnapshot.child(username).getValue(String.class);

                                    if(pass.equals(p))
                                    {
                                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getBaseContext(), Homepage.class);
                                        i.putExtra("username",s);


                                        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                                        SharedPreferences.Editor editor=preferences.edit();
                                        editor.putString("username",s);
                                        editor.putString("password",p);
                                        editor.apply();

                                        startActivity(i);
                                        finish();

                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                                        break;

                                    }
                                }
                            }
                            if(!flag)
                                Toast.makeText(getApplicationContext(),"Invalid Username or Password",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "error "+e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}


