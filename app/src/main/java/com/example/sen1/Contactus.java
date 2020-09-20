package com.example.sen1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Contactus extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        getSupportActionBar().hide();
        Button c=(Button)findViewById(R.id.c);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String number="9427217949";
                callIntent.setData(Uri.parse("tel:"+number));
                if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(Contactus.this, "Please Grant Permission", Toast.LENGTH_SHORT).show();
                    requestpermission();
                }
                else
                {
                    startActivity(callIntent);
                }


            }
        });

        ((Button) findViewById(R.id.btnOK)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String to = "";
                String sub = ((EditText)findViewById(R.id.txtSubject)).getText().toString();
                String mess = ((EditText)findViewById(R.id.txtMessage)).getText().toString();
                Intent mail = new Intent(Intent.ACTION_SEND);
                to="parammodi05@gmail.com";
                mail.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
                mail.putExtra(Intent.EXTRA_SUBJECT, sub);
                mail.putExtra(Intent.EXTRA_TEXT, mess);
                mail.setType("message/rfc822");
                startActivity(Intent.createChooser(mail, "Send email via:"));
            }
        });
    }
    private void requestpermission()
    {
        ActivityCompat.requestPermissions(Contactus.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }

}