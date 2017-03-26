package com.example.drs.tb17app;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private SignInButton mGoogleSignInButton;
    private LoginButton mFacebookSignInButton;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mFacebookCallbackManager;

    public String facebook_ID;
    public String username;
    public String email;
    public String google_ID;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        if(PrefUtils.getCurrentUser(LoginActivity.this) != null){
            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(homeIntent);
            finish();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mFacebookCallbackManager = CallbackManager.Factory.create();
        mFacebookSignInButton = (LoginButton) findViewById(R.id.facebook_sign_in_button);
        mFacebookSignInButton.setReadPermissions("public_profile", "email","user_friends");
        mFacebookSignInButton.registerCallback(mFacebookCallbackManager, mCallBack);

        mGoogleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_button);
        mGoogleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
    }

    public FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {

                            Log.e("response: ", response + "");
                            try {
                                user = new User();
                                facebook_ID = object.getString("id").toString();
                                user.setFacebook_ID(facebook_ID);
                                username = object.getString("name").toString();
                                user.setUsername(username);
                                email = object.getString("email").toString();
                                user.setEmail(email);

                                PrefUtils.setCurrentUser(user,LoginActivity.this);
                                Toast.makeText(getApplicationContext(), "Name " + user.getUsername(), Toast.LENGTH_LONG).show();

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            GoogleSignInAccount account = result.getSignInAccount();

            user = new User();
            username = account.getDisplayName();
            user.setUsername(username);
            google_ID = account.getId();
            user.setGoogle_ID(google_ID);
            email = account.getEmail();
            user.setEmail(email);

            if (result.isSuccess()) {
                //final GoogleApiClient client = mGoogleApiClient;
                PrefUtils.setCurrentUser(user,LoginActivity.this);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        } else {
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signInWithGoogle() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient); //final
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
}
