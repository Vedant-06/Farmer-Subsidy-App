package com.example.sen1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class governmentaddproduct extends AppCompatActivity {
    public EditText gpname,gprice,gdis;
    private Button submit;
   private ImageButton gb;
    private Uri ImageUri;
    private DatabaseReference ref,ref1;
    private static final int GalleryPick = 1;
    private String productRandomKey, downloadImageUrl;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governmentaddproduct);
        gpname=(EditText)findViewById(R.id.gpname);
        gprice=(EditText)findViewById(R.id.gprice);
        gdis=(EditText)findViewById(R.id.gdis);
        gb=(ImageButton)findViewById(R.id.gimage);
        submit=(Button)findViewById(R.id.submit);
        ref= FirebaseDatabase.getInstance().getReference("/");
        ref1= FirebaseDatabase.getInstance().getReference("/");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        getSupportActionBar().hide();
        final SharedPreferences preferences=getSharedPreferences("addpro",MODE_PRIVATE);
        int count1=preferences.getInt("count",1);
        count1++;
        final int count =count1;
        gb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {



            String z=String.valueOf(ImageUri);
            String image="";






            @Override
            public void onClick(View view)
            {
                if (!TextUtils.isEmpty(gpname.getText()) && !TextUtils.isEmpty(gprice.getText()) && !TextUtils.isEmpty(gdis.getText())
                &&ImageUri!=null)
                        {
                            final String productname = gpname.getText().toString();
                          final String  productprice = gprice.getText().toString();
                          final   String productdiscription = gdis.getText().toString();
                            if(ImageUri!=null) {
                                final ProgressDialog progressDialog = new ProgressDialog(governmentaddproduct.this);
                                progressDialog.setTitle("Uploading...");
                                progressDialog.show();

                                final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
                                ref.putFile(ImageUri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                progressDialog.dismiss();
                                                Toast.makeText(governmentaddproduct.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(governmentaddproduct.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (15.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                                        .getTotalByteCount());
                                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                            }
                                        });

                                final UploadTask uploadTask = ref.putFile(ImageUri);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        String message = e.toString();
                                        Toast.makeText(governmentaddproduct.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(governmentaddproduct.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                            @Override
                                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                if (!task.isSuccessful()) {
                                                    throw task.getException();
                                                }

                                                downloadImageUrl = ref.getDownloadUrl().toString();
                                                return ref.getDownloadUrl();
                                            }
                                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if (task.isSuccessful()) {
                                                    downloadImageUrl = task.getResult().toString();
                                                    image = downloadImageUrl;
                                                    ref1.child("product").child(String.valueOf(count)).child("image").setValue(downloadImageUrl);
                                                    ref1.child("product").child(String.valueOf(count)).child("productname").setValue(productname);
                                                    ref1.child("product").child(String.valueOf(count)).child("productprice").setValue(productprice);
                                                    ref1.child("product").child(String.valueOf(count)).child("productdiscription").setValue(productdiscription);
                                                    gpname.setText("");
                                                    gprice.setText("");
                                                    gdis.setText("");
                                                    gb.setImageURI(null);
                                                    SharedPreferences.Editor editor=preferences.edit();
                                                    editor.putInt("count",count);
                                                    editor.apply();
                                                    Intent i=new Intent(governmentaddproduct.this,governmentaddproduct.class);
                                                    startActivity(i);
                                                    finish();


                                                }
                                            }
                                        });
                                    }
                                });
                            }



                }
                else{
                    Toast.makeText(getBaseContext(),"empty fields",Toast.LENGTH_SHORT).show();
                }
            }

        });



    }
    private void opengallery() {


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
                && data != null)
        {


            ImageUri = data.getData();
            gb.setImageURI(ImageUri);
            System.out.println(ImageUri);
        }

    }
}
