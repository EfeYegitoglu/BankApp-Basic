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

public class DovizActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;

    TextView paraText;
    EditText yatirTl;
    Button dovizBtn, faizBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doviz);

        tanimlamalar();
        BilgiGetir();
    }

    private void tanimlamalar() {

        reference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        paraText = findViewById(R.id.paraText);
        yatirTl = findViewById(R.id.yatirTl);
        dovizBtn = findViewById(R.id.dovizBtn);
        dovizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DovizeYatir();
                d();
            }
        });


        faizBtn = findViewById(R.id.faizBtn);
        faizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FaizeYatir();
                f();
            }
        });

    }

    private void BilgiGetir() {

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel = dataSnapshot.getValue(KayitModel.class);

                paraText.setText("Bakiyeniz: "+kayitModel.getPara()+" TL");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void DovizeYatir() {

        double kur = 7.10;

        int para = Integer.parseInt(yatirTl.getText().toString());


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doviz").child(auth.getUid());

        double doviz = para / kur;


        Map map = new HashMap();
        map.put("miktar", para);
        map.put("doviz", doviz);
        reference.setValue(map);
        Toast.makeText(getApplicationContext(), "Dövize Yatırıldı", Toast.LENGTH_LONG).show();

    }

    private void FaizeYatir() {

        double anapara = Integer.parseInt(yatirTl.getText().toString());
        double faizOrani = 0.08;
        double ay = 2;
        double faizTutari;

        faizTutari = anapara * faizOrani * ay;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Faiz").child(auth.getUid()).child("faiz");

        reference.setValue(faizTutari);
        Toast.makeText(getApplicationContext(), "Faize Yatırıldı", Toast.LENGTH_LONG).show();

    }

    void f(){
        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue("1000");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Faiz").child(auth.getUid()).child("faizdenKazanç");
        reference.setValue("580");

    }

    void d(){
        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue("500");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doviz").child(auth.getUid());



        Map map = new HashMap();
        map.put("miktar","500");
        map.put("doviz", "70.422");
        reference.setValue(map);
        Toast.makeText(getApplicationContext(), "Dövize Yatırıldı", Toast.LENGTH_LONG).show();
    }


}
