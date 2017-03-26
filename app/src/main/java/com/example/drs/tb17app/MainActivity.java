package com.example.drs.tb17app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity {

    private ImageView photoImageView;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView btnLogout;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=PrefUtils.getCurrentUser(MainActivity.this);
        //photoImageView = (ImageView) findViewById(R.id.photoImageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);

        nameTextView.setText(user.username);
        emailTextView.setText(user.email);

        btnLogout = (TextView) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.clearCurrentUser(MainActivity.this);

                if(user.facebook_ID!=null && !user.facebook_ID.isEmpty()){
                    LoginManager.getInstance().logOut();
                    Intent i= new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                } else if(user.google_ID!=null && !user.google_ID.isEmpty()) {
                    Intent i= new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}

