package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmailLogin, edtPasswordLogin;
    private Button btnLogin, btnPreviousActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Log In");
        edtEmailLogin = findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnPreviousActivity = findViewById(R.id.btnPreviousActivity);


        //Log in the user
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(edtEmailLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            TastyToast.makeText(LoginActivity.this, user.getUsername().toString() + " Logged In Successfully!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                            transitionToSocialMediaActivity();
                        }else{
                            TastyToast.makeText(LoginActivity.this, e.getMessage(), TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                        }
                    }
                });

            }
        });


        //Go to previous activity i.e Sign Up page
        btnPreviousActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                ParseUser.logOut();
            }
        });
    }


    //hiding keyboard when user taps in an empty area
    public void loginLayoutTapped(View view){

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //to transit to social media activity
    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);

    }

}
