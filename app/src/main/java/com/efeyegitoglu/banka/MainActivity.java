package com.efeyegitoglu.banka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;

    TextView isimText, kartNoText, paraText;
    Button YatırCek,ekleHesap,transfer,cıkısYap,vadeli_btn,paraGonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), GirisYapActivity.class);
            startActivity(intent);
            finish();
        }

        tanimlamalar();
        BilgileriGetir();
    }

    private void tanimlamalar() {
        reference = FirebaseDatabase.getInstance().getReference();


        isimText = findViewById(R.id.isim);
        kartNoText = findViewById(R.id.kartNo);
        paraText = findViewById(R.id.para);

        YatırCek = findViewById(R.id.YatırCek);
        YatırCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParaYatirCekActivity.class);
                startActivity(intent);
            }
        });

        ekleHesap=findViewById(R.id.ekleHesap);
        ekleHesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HesapEkleActivity.class);
                startActivity(intent);
            }
        });

        transfer=findViewById(R.id.transfer);
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParaTransferiActivity.class);
                startActivity(intent);
            }
        });

        cıkısYap=findViewById(R.id.cıkısYap);
        cıkısYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(getApplicationContext(), GirisYapActivity.class);
                startActivity(intent);
                finish();

            }
        });
        vadeli_btn=findViewById(R.id.vadeli_btn);
        vadeli_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DovizActivity.class);
                startActivity(intent);
            }
        });

        paraGonder=findViewById(R.id.paraGonder);
        paraGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ParaGonderActivity.class);
                startActivity(intent);
            }
        });



    }

    private void BilgileriGetir() {



        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel = dataSnapshot.getValue(KayitModel.class);


                isimText.setText("Ad-Soyad: "+kayitModel.getIsim());
                kartNoText.setText("Kart No: "+kayitModel.getKartNo());
                paraText.setText("Bakiye: "+kayitModel.getPara()+" "+"Türk Lirası");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
