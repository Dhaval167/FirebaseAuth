package com.example.authentication;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.dd.CircularProgressButton;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class Login extends AppCompatActivity {


    private EditText mEmail;
    private EditText mPassword;
    private Button mSignUp;
    private Button mLogin;
    private ProgressBar mProgressBarLogin;
    private FirebaseAuth mAuth;
    private TextView mForgotPassword;
    private CheckBox toggle;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.email_login);
        mPassword = findViewById(R.id.password_login);
        mSignUp = (Button) findViewById(R.id.sign_up_account);
        mLogin =  findViewById(R.id.login_button);

        mProgressBarLogin = findViewById(R.id.progressbar_login);
        mForgotPassword = findViewById(R.id.forgotpassword);
        toggle = findViewById(R.id.toggle);
        mAuth = FirebaseAuth.getInstance();
        toggle.setVisibility(View.VISIBLE);


        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //show password
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    buttonView.setButtonDrawable(R.drawable.ic_visibility_off_black_24dp);
                } else {
                    //hide password
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    buttonView.setButtonDrawable(R.drawable.ic_visibility_black_24dp);
                }
            }
        });


        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();





            if(TextUtils.isEmpty(email))

            {
                mEmail.setError("Email Is Required");
                return;
            }

                if(TextUtils.isEmpty(password))

            {
                mPassword.setError("Password Must Be Required");
                return;
            } else if(password.length() < 6)

            {
                mPassword.setError("Password Miss Match");
            }

            Sprite doubleBounce = new ThreeBounce();
                mProgressBarLogin.setIndeterminateDrawable(doubleBounce);
                mProgressBarLogin.setVisibility(View.VISIBLE);




            loginUser();

        }
    });

        mSignUp.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        startActivity(new Intent(getApplicationContext(), Registration.class));

    }
    });

        mForgotPassword.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        startActivity(new Intent(getApplicationContext(), ForgotPassword.class));

    }
    });
}

    private void loginUser() {


        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Login.this, "Error : " + task.getException().getMessage()
                            , Toast.LENGTH_SHORT).show();
                    mProgressBarLogin.setVisibility(View.GONE);
                }

            }
        });
    }

    public void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) Login.this.getSystemService(Login.this.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) Login.this.getSystemService(Login.this.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


}