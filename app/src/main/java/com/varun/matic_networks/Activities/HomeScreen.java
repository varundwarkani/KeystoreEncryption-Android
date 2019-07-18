package com.varun.matic_networks.Activities;

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
import com.varun.matic_networks.R;
import com.varun.matic_networks.Utils.Cryptor;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.varun.matic_networks.Activities.MainActivity.SHAREDPREF;

public class HomeScreen extends AppCompatActivity {

    private MaterialButton btHash;
    private RecyclerView recyclerView;

    private SharedPreferences prefs;

    private ArrayList<String> tokenName = new ArrayList<>();
    private ArrayList<String> tokenCode = new ArrayList<>();
    private ArrayList<Integer> tokenValue = new ArrayList<>();
    private ArrayList<Integer> tokenImage = new ArrayList<>();

    private android.app.AlertDialog alertDialog;
    private android.app.AlertDialog.Builder builder;
    private ViewGroup viewGroup = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        setViews();
        setOnClicks();
        setRecyclerView();
    }

    private void setViews() {
        btHash = findViewById(R.id.btHash);
        recyclerView = findViewById(R.id.recyclerView);
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

    private void setRecyclerView() {
        addDummyData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        TokenAdapter tokenAdapter = new TokenAdapter(tokenName, tokenCode, tokenValue, tokenImage);
        recyclerView.setAdapter(tokenAdapter);
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

    private void addDummyData() {

        tokenName.clear();
        tokenCode.clear();
        tokenValue.clear();
        tokenImage.clear();

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));

        tokenName.add("BitCoin");
        tokenCode.add("btc");
        tokenValue.add(13000);
        tokenImage.add(getResources().getIdentifier("btc", "drawable", getPackageName()));
    }

    public class TokenAdapter extends RecyclerView.Adapter<TokenAdapter.ViewHolder> {

        private ArrayList<String> tokenName_adapter;
        private ArrayList<String> tokenCode_adapter;
        private ArrayList<Integer> tokenValue_adapter;
        private ArrayList<Integer> tokenImage_adapter;

        TokenAdapter(ArrayList<String> tokenName, ArrayList<String> tokenCode,
                     ArrayList<Integer> tokenValue, ArrayList<Integer> tokenImage){

            this.tokenName_adapter = tokenName;
            this.tokenCode_adapter = tokenCode;
            this.tokenValue_adapter = tokenValue;
            this.tokenImage_adapter = tokenImage;
        }

        @NonNull
        @Override
        public TokenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tokens, parent, false);
            return new TokenAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final TokenAdapter.ViewHolder holder, int position) {
            holder.tvTokenName.setText(tokenName_adapter.get(position));
            holder.tvTokenCode.setText(tokenCode_adapter.get(position));
            holder.tvTokenValue.setText(String.valueOf(tokenValue_adapter.get(position)));
            Picasso.get().load(tokenImage_adapter.get(position)).into(holder.ivToken);

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showSpecificDialog(tokenName_adapter.get(holder.getAdapterPosition()),tokenImage_adapter.get(holder.getAdapterPosition()));
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return tokenCode_adapter.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final ImageView ivToken;
            final TextView tvTokenName;
            final TextView tvTokenCode;
            final TextView tvTokenValue;

            ViewHolder(View itemView) {
                super(itemView);
                ivToken = itemView.findViewById(R.id.ivToken);
                tvTokenName = itemView.findViewById(R.id.tvTokenName);
                tvTokenCode = itemView.findViewById(R.id.tvTokenCode);
                tvTokenValue = itemView.findViewById(R.id.tvTokenValue);
            }
        }
    }

    private void showSpecificDialog(String name, int image) {
        closeDialog();
        builder = new android.app.AlertDialog.Builder(HomeScreen.this);
        View view = LayoutInflater.from(HomeScreen.this).inflate(R.layout.layout_specific_token,viewGroup,false);
        builder.setView(view);
        builder.setCancelable(false);

        TextView tvTokenName;
        TextView tvClose;
        CircleImageView ivToken;

        tvTokenName = view.findViewById(R.id.tvTokenName);
        tvClose = view.findViewById(R.id.tvClose);
        ivToken = view.findViewById(R.id.ivToken);

        tvTokenName.setText(name);
        Picasso.get().load(image).into(ivToken);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });

        showDialog();
    }

    private void closeDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.cancel();
        }
    }

    private void showDialog() {
        alertDialog = builder.create();
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 70);
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.Animation_WindowSlideUpDown;
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(inset);
        alertDialog.show();
    }
}
