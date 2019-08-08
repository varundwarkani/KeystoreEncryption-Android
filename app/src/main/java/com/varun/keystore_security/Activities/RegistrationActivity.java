package com.varun.keystore_security.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.varun.keystore_security.R;
import com.varun.keystore_security.RoomDB.AppDatabase;
import com.varun.keystore_security.RoomDB.UserData;
import com.varun.keystore_security.RoomDB.UserDataInitializer;
import com.varun.keystore_security.Utils.Cryptor;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Objects;

import javax.crypto.NoSuchPaddingException;

import static com.varun.keystore_security.Activities.MainActivity.SHAREDPREF;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputLayout tiUserName;
    private TextInputLayout tiPassword;
    private TextInputEditText etUserName;
    private TextInputEditText etPassword;
    private MaterialButton btCreate;
    private SharedPreferences prefs;
    private String userName;
    private String password;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setViews();
        setOnClicks();
    }

    private void setViews() {

        tiUserName = findViewById(R.id.tiUserName);
        tiPassword = findViewById(R.id.tiPassword);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btCreate = findViewById(R.id.btCreate);

        prefs = getSharedPreferences(SHAREDPREF, Context.MODE_PRIVATE);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    }

    private void setOnClicks() {

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndCreateUser();
            }
        });
    }

    private void validateAndCreateUser() {
        userName = Objects.requireNonNull(etUserName.getText()).toString().trim();
        password = Objects.requireNonNull(etPassword.getText()).toString().trim();
        if (userName.length() <= 0) {
            tiUserName.setError(getString(R.string.no_username));
        } else if (password.length() <= 0) {
            tiPassword.setError(getString(R.string.no_password));
        } else {
            checkAndInsert();
        }
    }

    private void checkAndInsert() {

        int userExists = appDatabase.userDao().checkUserExists(userName,password);
        if (userExists > 0) {
            Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.user_exists, Snackbar.LENGTH_LONG);

            snackBar.setAction(getString(R.string.login), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            snackBar.show();
        } else {
            UserData userData = new UserData();
            userData.setUsername(userName);
            userData.setPassword(password);
            addinDB_User(userData);

            Cryptor cryptor = new Cryptor();
            try {
                cryptor.setIv();
                prefs.edit().putString("encryptedKey", cryptor.encryptText(userName + password)).apply();
                prefs.edit().putString("keyIv", cryptor.getIv_string()).apply();

                Intent intent = new Intent(RegistrationActivity.this, HomeScreen.class);
                startActivity(intent);
                finish();

            } catch (NoSuchPaddingException e) {
                unexpectedError();
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                unexpectedError();
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                unexpectedError();
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                unexpectedError();
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                unexpectedError();
                e.printStackTrace();
            }
        }
    }

    private void unexpectedError() {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), R.string.unexpected_error, Snackbar.LENGTH_LONG);
        snackBar.show();
    }

    private void addinDB_User(UserData userData) {
        UserDataInitializer.populateAsync(AppDatabase.getAppDatabase(getApplicationContext()),userData);
    }
}
