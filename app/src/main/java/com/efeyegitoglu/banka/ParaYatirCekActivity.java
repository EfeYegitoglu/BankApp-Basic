package com.efeyegitoglu.banka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ParaYatirCekActivity extends AppCompatActivity {

    EditText yatirText, cekText;
    Button yatirBtn, cekBtn;
    TextView mevcutPara;

    DatabaseReference reference;
    FirebaseAuth auth;

    String sonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_yatir_cek);

        tanimlamalar();
        mevcutPara();

    }

    private void tanimlamalar() {
        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        yatirText = findViewById(R.id.yatirText);
        cekText = findViewById(R.id.cekText);
        mevcutPara = findViewById(R.id.mevcutPara);
        yatirBtn = findViewById(R.id.yatirBtn);
        cekBtn = findViewById(R.id.cekBtn);

        yatirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yatir();
                //ParaYatir();
            }
        });

        cekBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ParaCek();
                cek();
            }
        });


    }

    private void mevcutPara() {

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel = dataSnapshot.getValue(KayitModel.class);


                mevcutPara.setText(String.valueOf(kayitModel.getPara()+" TL"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ParaYatir() {

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel = dataSnapshot.getValue(KayitModel.class);


                int para = Integer.parseInt(yatirText.getText().toString());

                sonuc = String.valueOf(Integer.parseInt(kayitModel.getPara()) + para);




                Toast.makeText(getApplicationContext(), "Para Yatırıldı", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue(sonuc);
    }

    private void ParaCek() {

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel = dataSnapshot.getValue(KayitModel.class);


                int para = Integer.parseInt(cekText.getText().toString());

                sonuc = String.valueOf(Integer.parseInt(kayitModel.getPara()) - para);


                reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue(sonuc);

                Toast.makeText(getApplicationContext(), "Para Çekildi", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void yatir(){

        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue("2500");
        yatirText.setText("");
        Toast.makeText(getApplicationContext(), "Para Yatırıldı", Toast.LENGTH_LONG).show();

    }

    void cek(){

        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue("2000");
        cekText.setText("");
        Toast.makeText(getApplicationContext(), "Para Çekildi", Toast.LENGTH_LONG).show();

    }
}
