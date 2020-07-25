package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.gc.materialdesign.views.ButtonFlat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private FlowingDrawer mFlowingDrawer;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mFirebaseAuth;
    String userID;
    private long BackPressed;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ButtonFlat mButton = findViewById(R.id.button_logout);

        final TextView textView = findViewById(R.id.text_view_welcome);
        final TextView username = findViewById(R.id.user_name);



        /*Button showPicture = findViewById(R.id.showPicture);
        showPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Picture.class));
            }
        });
*/
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        String userEmail = mFirebaseAuth.getCurrentUser().getEmail();
        textView.setText(userEmail);



        userID = mFirebaseAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username.setText( documentSnapshot.getString("Fname"));

            }
        });



        mFlowingDrawer = (FlowingDrawer)findViewById(R.id.drawerlayout);
        mFlowingDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mFlowingDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {

                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });


    }



    @Override
    public void onBackPressed() {

        if(BackPressed + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            moveTaskToBack(true);
            return;
        }
        else {

            backToast = Toasty.info(getBaseContext(), "Press Again to Close", Toast.LENGTH_SHORT);
            backToast.show();
        }
        BackPressed = System.currentTimeMillis();

    }



}
