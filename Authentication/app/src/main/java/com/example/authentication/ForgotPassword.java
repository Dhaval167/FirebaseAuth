package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText mSentEmail;
    private Button mSentButton;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mSentEmail = findViewById(R.id.sent_email);
        mProgressBar = findViewById(R.id.progressbar_forgot);
        mSentButton = findViewById(R.id.button_sent);

        mAuth = FirebaseAuth.getInstance();

        mSentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mSentEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mSentEmail.setError("Email Is Required");
                    return;
                }


                Sprite doubleBounce = new ThreeBounce();
                mProgressBar.setIndeterminateDrawable(doubleBounce);
                mProgressBar.setVisibility(View.VISIBLE);
                sendEmail();
            }
        });
    }

    public void sendEmail(){

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this,"Email is Sent",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Login.class));
                        }else{
                            Toast.makeText(ForgotPassword.this,"Error : "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }


}
