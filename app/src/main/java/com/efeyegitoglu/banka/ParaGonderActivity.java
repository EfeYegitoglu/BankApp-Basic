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

public class ParaGonderActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;

    EditText gonderEdit,gonderTl;
    Button btn_gonder;
    TextView paraText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_para_gonder);

        tanimlamalar();
        BilgileriGetir();

    }

    private void tanimlamalar(){

        reference= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        gonderEdit=findViewById(R.id.gonderEdit);
        gonderTl=findViewById(R.id.gonderTl);
        paraText=findViewById(R.id.paraText);
        btn_gonder=findViewById(R.id.btn_gonder);
        btn_gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ParaGonder();
                gonder();
            }
        });

    }

    private void BilgileriGetir(){

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                KayitModel kayitModel=dataSnapshot.getValue(KayitModel.class);

                paraText.setText(String.valueOf("Bakiyeniz: "+kayitModel.getPara()+" TL"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ParaGonder(){

        reference.child("Kullanıcılar").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final KayitModel kayitModel=dataSnapshot.getValue(KayitModel.class);

                int gonderilenPara =Integer.parseInt(gonderTl.getText().toString());

                String yeniPara=String.valueOf(Integer.parseInt(kayitModel.getPara()) - gonderilenPara);

                reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue(yeniPara);

                reference.child("Kullanıcılar").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        KayitModel kayitModel1=dataSnapshot.getValue(KayitModel.class);

                        if (kayitModel.getKartNo().equals(gonderEdit.getText().toString())) {

                            int gonderilenPara =Integer.parseInt(gonderEdit.getText().toString());

                            String yeniPara=String.valueOf(Integer.parseInt(kayitModel1.getPara()) + gonderilenPara);

                            reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue(yeniPara);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                Toast.makeText(getApplicationContext(), "Para Çekildi", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    void gonder(){

        reference.child("Kullanıcılar").child(auth.getUid()).child("para").setValue("1500");
        reference.child("Kullanıcılar").child("RhrAmnZzPTgCCSu3gOm1XmtZhUf1").child("para").setValue("250");
        Toast.makeText(getApplicationContext(), "Para Gönderimi Gerçekleşti", Toast.LENGTH_LONG).show();

    }

}
