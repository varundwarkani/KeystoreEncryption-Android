package com.varun.keystore_security.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;
import com.varun.keystore_security.R;
import com.varun.keystore_security.Utils.Cryptor;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.varun.keystore_security.Activities.MainActivity.SHAREDPREF;

public class HomeScreen extends AppCompatActivity {

    private MaterialButton btHash;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        setViews();
        setOnClicks();
    }

    private void setViews() {
        btHash = findViewById(R.id.btHash);
        prefs = getSharedPreferences(SHAREDPREF, Context.MODE_PRIVATE);
    }

    private void setOnClicks() {
        btHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHash();
            }
        });
    }

    private void showHash() {
        String iv = prefs.getString("keyIv", "null");
        String encrypted = prefs.getString("encryptedKey", "");

        try {
            Cryptor cryptor = new Cryptor();
            cryptor.initKeyStore();
            String decrypted = cryptor.decryptText(encrypted, iv);

            new AlertDialog.Builder(HomeScreen.this)
                    .setTitle("Hash")
                    .setMessage("Encrypted: "+ encrypted +"\nDecrypted: "+ decrypted)
                    .setCancelable(false)
                    .setPositiveButton("Close",null)
                    .show();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
