package com.example.wollf.togather;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText name;
    private EditText email;
    private EditText mPasswordView;
    private EditText iban;
    private EditText phone;
    private View mProgressView;
    private View mLoginFormView;

    private static final int REQUEST_READ_CONTACTS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email_register);
        iban = findViewById(R.id.iban);
        phone = findViewById(R.id.phone_number);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_register);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Button register_btn = findViewById(R.id.register_me_btn);
        register_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void register() throws JSONException {
        DataBase db = new DataBase();
        Tikkie tikkie = new Tikkie(this);
        JSONObject tikkie_user = tikkie.add_user(name.getText().toString(),
                phone.getText().toString(),
                iban.getText().toString());
        if (!tikkie_user.getString("response_code").equals("201")){
            Log.d("Error", "Cant create tikkie user");
        }
        JSONArray bank_details = tikkie_user.getJSONArray("bankAccounts");
        String user_token  = tikkie_user.getString("userToken");
        String iban_token = bank_details.getJSONObject(0).getString("bankAccountToken");
        User new_user = new User(name.getText().toString(),
                email.getText().toString(),
                mPasswordView.getText().toString(),
                iban.getText().toString(),
                phone.getText().toString(),
                user_token,
                iban_token
        );
        db.addUser(new_user);
        for(User u : DataBase.getUsers()){
            System.out.println(u.getName() + "\n");
        }
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        name.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        iban.setError(null);
        phone.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name_string = name.getText().toString();
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String iban_number = iban.getText().toString();
        String phone_number = phone.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(iban_number) && !isIBANValid(iban_number)) {
            System.out.println("IBAN NOT CORRECT");
            iban.setError("IBAN empty or not correct");
            focusView = iban;
            cancel = true;
        }

        if (!TextUtils.isEmpty(phone_number) && !isPhoneNumberValid(phone_number)) {
            phone.setError("Phone number empty or not correct");
            focusView = phone;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if(TextUtils.isEmpty(name_string)){
            mPasswordView.setError("This field is required");
            focusView = name;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            try {
                register();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEmailValid(String email) {
        //Official RFC 5322 standard
        Pattern pattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        return pattern.matcher(email).matches();
        //return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isIBANValid(String iban) {
        return iban.length() == 18;
    }

    private boolean isPhoneNumberValid(String number) {
        return number.substring(0,2).equals("06");
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Register.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }
}

