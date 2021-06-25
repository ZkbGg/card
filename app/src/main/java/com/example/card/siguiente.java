package com.example.card;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class siguiente extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siguiente);
        TextView t1,t2;
        t1 = findViewById(R.id.t1);
        t2 =findViewById(R.id.t2);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
        String fecha = sp.getString("fecha","");
        String tarjeta = sp.getString(" tarjeta", "");

        t1.setText(tarjeta);
        t2.setText(fecha);
    }
}
