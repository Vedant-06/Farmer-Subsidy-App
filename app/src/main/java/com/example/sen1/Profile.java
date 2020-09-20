package com.example.sen1;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Profile extends AppCompatActivity
{

    TextView username;
    EditText name,birthdate,income,land,city,state,phone;
    ImageView button;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private static final int GalleryPick = 1;
    FirebaseStorage storage;
    StorageReference storageReference;
    Button changes,upload;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name=findViewById(R.id.name);
        username=findViewById(R.id.username);
        birthdate=findViewById(R.id.birthdate);
        income=findViewById(R.id.income);
        land=findViewById(R.id.land);
        city=findViewById(R.id.city);
        state=findViewById(R.id.state);
        phone=findViewById(R.id.phone);
        button=findViewById(R.id.image);
        changes=findViewById(R.id.changes);
        upload=findViewById(R.id.upload);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Button logout=findViewById(R.id.logoutt);
        getSupportActionBar().hide();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("rememberme","false");
                editor.apply();
                Intent i=new Intent(Profile.this,Login.class);
                startActivity(i);
                finish();

            }
        });


        final  String  u = getIntent().getStringExtra("username");

        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("/");

        final pojo_farmer[] prof = {new pojo_farmer()};
        final AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });

        changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String a=name.getText().toString();
                final String b=username.getText().toString();
                final String c=birthdate.getText().toString();
                final double d=Double.parseDouble(income.getText().toString());
                final double e=Double.parseDouble(land.getText().toString());
                final String f=city.getText().toString();
                final String g=state.getText().toString();
                final String h=phone.getText().toString();
                String img=prof[0].img;

                reference1.child("farmer").child(u).setValue(new pojo_farmer(a, b, prof[0].password, c, f,g,d,e,h,img));


            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ImageUri != null) {



                    final ProgressDialog progressDialog = new ProgressDialog(Profile.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();

                    final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
                    ref.putFile(ImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Profile.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Profile.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (15.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                            .getTotalByteCount());
                                    progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                }
                            });

                    final UploadTask uploadTask = ref.putFile(ImageUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            String message = e.toString();
                            Toast.makeText(Profile.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            Toast.makeText(Profile.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                                {
                                    if (!task.isSuccessful())
                                    {
                                        throw task.getException();
                                    }

                                    downloadImageUrl = ref.getDownloadUrl().toString();
                                    return ref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        downloadImageUrl = task.getResult().toString();
                                        reference1.child("farmer").child(u).setValue(new pojo_farmer(prof[0].name, prof[0].username, prof[0].password, prof[0].state, prof[0].city,prof[0].state,prof[0].income,prof[0].land,prof[0].contact,downloadImageUrl));

                                    }
                                }
                            });
                        }
                    });
                }

            }



            });

        reference1.child("farmer").child(u).addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        prof[0] = dataSnapshot.getValue(pojo_farmer.class);
                        String b=prof[0].img;


                        if (prof[0].income == -1.0) {
                            builder.setTitle("Important").setMessage("Please verify you account to start availing your subsides")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                            if (!Profile.this.isFinishing()) {
                                builder.create().show();


                            }
                        }
                        name.setText(prof[0].name);
                        username.setText(prof[0].username);
                        birthdate.setText(prof[0].birthdate);
                        income.setText(prof[0].income + " ");
                        land.setText(prof[0].land + "");
                        city.setText(prof[0].city);
                        state.setText(prof[0].state);
                        phone.setText(prof[0].contact);
                        Glide.with(Profile.this).load(b).into(button);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }




    private void opengallery()
    {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK&&requestCode ==GalleryPick
                && data != null
               )
        {
            ImageUri = data.getData();
            button.setImageURI(ImageUri);
        }

    }
        }
