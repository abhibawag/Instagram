package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.sdsmdg.tastytoast.TastyToast;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmailSignup, edtPasswordSignup, edtUsernameSignup;
    private Button btnSignup, btnNextActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");    // set title bar text to sign up
        edtEmailSignup = findViewById(R.id.edtEmailSignup);
        edtPasswordSignup = findViewById(R.id.edtPasswordSignup);
        edtUsernameSignup = findViewById(R.id.edtUsernameSignup);
        btnSignup = findViewById(R.id.btnSignup);
        btnNextActivity = findViewById(R.id.btnNextActivity);


        //Log out currently logged user
        if(ParseUser.getCurrentUser() != null){
//            String currentUser = ParseUser.getCurrentUser().getUsername().toString()+"";
//            ParseUser.getCurrentUser().logOut();
//            TastyToast.makeText(MainActivity.this, currentUser + " Logged Out", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();

            transitionToSocialMediaActivity();
        }



        //Pressing enter key will sign up the user
        edtPasswordSignup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    btnSignup.isPressed();
                }
                return false;
            }
        });




        //Sign up a new user
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtEmailSignup.getText().toString().equals("") || edtPasswordSignup.getText().toString().equals("") || edtUsernameSignup.getText().toString().equals("")){

                    TastyToast.makeText(MainActivity.this, "Email/Username/Password cannot be empty!", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();

                }else{

                final ParseUser appUser = new ParseUser();
                appUser.setEmail(edtEmailSignup.getText().toString());
                appUser.setUsername(edtUsernameSignup.getText().toString());
                appUser.setPassword(edtPasswordSignup.getText().toString());
                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage(appUser.getUsername().toString() + " Signing Up");
                progressDialog.show();
                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){

                            TastyToast.makeText(getApplicationContext(), appUser.getUsername().toString()+" Signed Up Successfully!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS).show();

                            transitionToSocialMediaActivity();
                        }
                        progressDialog.dismiss();

                    }
                });
            }
        }});


        //Move to next activity i.e login page
        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    //hiding keyboard when user taps in an empty area
    public void rootLayoutTapped(View view){

        try {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    //to transit to social media activity
    private void transitionToSocialMediaActivity(){

        Intent intent = new Intent(MainActivity.this, SocialMediaActivity.class);
        startActivity(intent);

    }

}
