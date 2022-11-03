package com.example.firestore_f55121029;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class ListActivity extends AppCompatActivity {
    private ArrayList<String> dataNama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

        dataNama = new ArrayList<>();

        ListView listView = findViewById(R.id.list_view);

        mFireStore.collection("pengguna")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            dataNama.add(document.getString("nama") + " - " + document.getString("stambuk"));
                        }
                        ArrayAdapter<String> adapter = new
                                ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dataNama);
                        listView.setAdapter(adapter);
                    } else {
                        Log.w("Lihat", "Error getting documents.", task.getException());
                    }
                });
    }
}