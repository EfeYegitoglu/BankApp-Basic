package com.efeyegitoglu.banka;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class HesapEkleActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;

    EditText isimGir,kartNoGir;
    Button yeniEkle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hesap_ekle);

        tanimlamalar();
    }

    private void tanimlamalar(){


        auth=FirebaseAuth.getInstance();

        isimGir=findViewById(R.id.isimGir);
        kartNoGir=findViewById(R.id.kartNoGir);
        yeniEkle=findViewById(R.id.yeniEkle);
        yeniEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kartNoGir.length()==16 && !isimGir.equals("")){
                    YeniHesapEkle();
                }

            }
        });

    }

    private void YeniHesapEkle(){

        String isim=isimGir.getText().toString();
        String kartNo=kartNoGir.getText().toString();

        reference=FirebaseDatabase.getInstance().getReference().child("YeniHesap").child(auth.getUid());

        Map map=new HashMap();
        map.put("para","0");
        map.put("isim",isim);
        map.put("kartNo",kartNo);
        reference.setValue(map);
        Toast.makeText(getApplicationContext(), "Hesap Eklendi", Toast.LENGTH_LONG).show();


    }
}
