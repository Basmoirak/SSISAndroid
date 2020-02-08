package com.team11.ssisandroid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.interfaces.UserClient;
import com.team11.ssisandroid.models.AccessToken;
import com.team11.ssisandroid.models.UserRole;
import com.team11.ssisandroid.util.LoginParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    //Login Task
    private UserLoginTask mAuthTask = null;

    // Declaring layout button, edit texts
    Button loginBtn;
    EditText mEmailView, mPasswordView;
    // End Declaring layout button, edit texts

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        // If token has not yet expired, continue to main screen
//        long currentTime = System.currentTimeMillis();
//        long loginTime = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).getLong("loginTime", 0);
//        int expiresIn = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).getInt("expiresIn", 0);
//        String accessToken = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).getString("accessToken", "");
//
//        if (!accessToken.equals("") && currentTime <= loginTime + expiresIn * 1000) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }

        // Getting values from button, texts and progress bar
        loginBtn = findViewById(R.id.btnLogin);
        mEmailView = findViewById(R.id.etUsername);
        mPasswordView = findViewById(R.id.etPassword);
        // End Getting values from button, texts and progress bar

        // Setting up the function when button login is clicked
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        // End Setting up the function when button login is clicked

    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_incorrect_password));
            focusView = mPasswordView;
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

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    // Represents an asynchronous login task to authenticate the user
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private AccessToken token;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            token = null;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Get Access Token from the Login Method
            token = LoginParser.postLoginStream(mEmail, mPassword, getString(R.string.hostname) + "/token");

            if (token == null)
                return false;

            //Store login authentication information in shared preferences
            final SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit();
            editor.putString("email", mEmail);
            editor.putString("accessToken", token.get("accessToken").toString());
            editor.putInt("expiresIn", Integer.parseInt(token.get("expiresIn").toString()));
            editor.putLong("loginTime", System.currentTimeMillis());

            if (token.containsKey("error"))
                return false;

            getUserDetails("Bearer " + token.get("accessToken").toString(), mEmail);

            // Apply Shared Preferences
            editor.apply();

            Log.i("LOGIN TOKEN", token.toString());
            return token != null && !token.containsKey("error");

        }

        //Store departmentId and user role in shared preferences
        private void getUserDetails(String token, String email){
            Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();
            UserClient userClient = retrofit.create(UserClient.class);

            UserRole userRole = new UserRole(email, null, null);
            Call<UserRole> call = userClient.getUserRole(token, userRole);

            try {
                UserRole model = call.execute().body();
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE).edit();
                editor.putString("departmentId", model.getDepartmentId());
                editor.putString("role", model.getRoleName());
                editor.apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

            @Override
            protected void onPostExecute ( final Boolean success){
                mAuthTask = null;

                if (success) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }

            @Override
            protected void onCancelled () {
                mAuthTask = null;
            }
    }
}

