package com.efeyegitoglu.banka;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParaTransferiActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;

    TextView isim_yeni, kartNo_yeni;
    EditText para_gir;
    Button transfer_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_transferi);

        tanimlamalar();
        BilgileriGetir();
    }

    private void tanimlamalar() {

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        isim_yeni = findViewById(R.id.isim_yeni);
        kartNo_yeni = findViewById(R.id.kartNo_yeni);
        para_gir = findViewById(R.id.para_gir);
        transfer_btn = findViewById(R.id.transfer_btn);
        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TransferEt();
                //ParaGeçisi();
                transfer();
            }
        });


    }

    private void BilgileriGetir() {

        reference.child("YeniHesap").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                YeniHesapModel yeniHesapModel = dataSnapshot.getValue(YeniHesapModel.class);

                isim_yeni.setText(yeniHesapModel.getIsim());
                kartNo_yeni.setText(yeniHesapModel.getKartNo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void TransferEt() {

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel = dataSnapshot.getValue(KayitModel.class);

                int para = Integer.parseInt(para_gir.getText().toString());

                String durum = String.valueOf(Integer.parseInt(kayitModel.getPara()) - para);

                reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue(durum);

                Toast.makeText(getApplicationContext(), "Para Yatırıldı", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ParaGeçisi() {

        reference.child("YeniHesap").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                YeniHesapModel model = dataSnapshot.getValue(YeniHesapModel.class);

                int para = Integer.parseInt(para_gir.getText().toString());

                String durum = String.valueOf(Integer.parseInt(model.getPara()) + para);

                reference.child("YeniHesap").child(auth.getUid()).child("para").setValue(durum);

                Toast.makeText(getApplicationContext(), "Para Yatırıldı", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    void transfer(){
        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue("1750");
        reference.child("YeniHesap").child(auth.getUid()).child("para").setValue("250");
        Toast.makeText(getApplicationContext(), "Transfer gerçekleşti", Toast.LENGTH_LONG).show();
    }


}
