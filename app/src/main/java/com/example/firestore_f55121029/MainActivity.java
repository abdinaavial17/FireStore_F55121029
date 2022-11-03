package com.example.firestore_f55121029;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText mMainText, mMainText1;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainText = findViewById(R.id.mainText);
        mMainText1 = findViewById(R.id.mainText2);
        Button mSaveBtn = findViewById(R.id.saveBtn);
        TextView mListText = findViewById(R.id.textView1);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        FirebaseApp.initializeApp(this);

        mFirestore = FirebaseFirestore.getInstance();

        mSaveBtn.setOnClickListener(v -> {
            String username = mMainText.getText().toString();
            String stambuk = mMainText1.getText().toString();

            Map<String, String> userMap = new HashMap<>();

            userMap.put("nama", username);
            userMap.put("stambuk", stambuk);

            progressBar.setVisibility(View.VISIBLE);
            if (!username.equals("") && !stambuk.equals("")) {

                mFirestore.collection("pengguna").add(userMap).addOnSuccessListener(documentReference -> {
                    Toast.makeText(MainActivity.this, "Pengguna Ditambahkan di Firestore", Toast.LENGTH_SHORT).show();

                    mMainText.setText("");
                    mMainText1.setText("");
                    progressBar.setVisibility(View.GONE);
                }).addOnFailureListener(e -> {
                    String error = e.getMessage();
                    Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                });
            } else {
                Toast.makeText(MainActivity.this, "Username dan Stambuk tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
                });

        mListText.setOnClickListener(v -> {
            Intent registerIntent = new Intent(MainActivity.this, ListActivity.class);
            startActivity(registerIntent);
            Toast.makeText(MainActivity.this, "Buka Activity yang menampilkan ListView", Toast.LENGTH_SHORT).show();
        });
    }
}