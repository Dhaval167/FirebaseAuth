package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText mFullName;
    private EditText mEmail;
    private EditText mpassword;
    private EditText mContact;
    private TextView mLoginText;
    private ProgressBar mProgressBar;
    private Button mRegisterButton;
    private FirebaseFirestore mFireStore;
    private FirebaseAuth mAuth;

    private CircleImageView mProfilePicture;
    private Uri mImageUri;

    String userID;
    String email;
    String password;
    String FirstName;
    String PhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mFullName = findViewById(R.id.firstname);
        mEmail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);

        mContact = findViewById(R.id.contact);
        mLoginText = findViewById(R.id.alreadyhave_account);
        mProgressBar = findViewById(R.id.progressbar);
        mRegisterButton = findViewById(R.id.registration);

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();


        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        /*mProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
*/
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString().trim();
                password = mpassword.getText().toString().trim();
                FirstName = mFullName.getText().toString().trim();
                PhoneNumber = mContact.getText().toString().trim();


                if (FirstName.isEmpty()) {
                    mFullName.setError("Name is requird");
                    return;
                }
                if (PhoneNumber.isEmpty()) {
                    mContact.setError("phone number is requird ");
                }


                if (email.isEmpty()) {
                    mEmail.setError("Email Is Requried");
                    return;
                }

                if (password.isEmpty()) {
                    mpassword.setError("Password Must Be Requried");
                    return;
                } else if (password.length() < 6) {
                    mpassword.setError("Password Must Be 6 Or More Character");
                }

                Sprite doubleBounce = new ThreeBounce();
                mProgressBar.setIndeterminateDrawable(doubleBounce);
                mProgressBar.setVisibility(View.VISIBLE);
                RegisterUser();

            }
        });

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

    private void RegisterUser() {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference documentReference = mFireStore.collection("users").document(userID);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Fname", FirstName);
                            user.put("ContactNumber", PhoneNumber);
                            user.put("Email", email);


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess: Register Successful");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure: some error during registration");
                                }
                            });

                            Toast.makeText(Registration.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Registration.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }

                    }
                });
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mProfilePicture);
        }

    }*/


}







