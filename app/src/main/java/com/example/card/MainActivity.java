package com.example.card;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {

    private static final int SCAN_RESULT = 100;
    private TextView fecha;
    private TextView tarjeta;
    Button button2;
    SharedPreferences sp;
    String tarjetasr, fechasr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        tarjeta = (TextView) findViewById(R.id.tarjeta);
        fecha = (TextView) findViewById(R.id.Fecha);
        button2 =findViewById(R.id.button2);
        sp = getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
    button2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tarjetasr = tarjeta.getText().toString();
            fechasr = fecha.getText().toString();

            SharedPreferences.Editor editor = sp.edit();

            editor.putString("tarjeta",tarjetasr);
            editor.putString("fecha",fechasr);
            editor.commit();
            Toast.makeText(MainActivity.this, "Tarjeta guardada", Toast.LENGTH_SHORT).show();

            Intent intent  = new Intent(MainActivity.this, siguiente.class);
            startActivity(intent);
        }
    });

    }

    public void scanearTarjeta(View view) {
        Intent intent = new Intent(this, CardIOActivity.class);
        intent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY,true);
        intent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV,false);
        intent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE,false);
        startActivityForResult(intent,SCAN_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SCAN_RESULT){
            if(data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
            {
                CreditCard scanresult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                tarjeta.setText(scanresult.getRedactedCardNumber());

                if(scanresult.isExpiryValid()){
                    String mes = String.valueOf(scanresult.expiryMonth);
                    String an = String.valueOf(scanresult.expiryYear);
                    fecha.setText(mes + "/"+an);

                }
            }
    }
}

}