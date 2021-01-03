package com.efeyegitoglu.banka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class KayitOlActivity extends AppCompatActivity {

    EditText mail_kayit,password_kayit,isim_kayit,no_kayit;
    Button btn_kayit;

    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        tanimlamalar();
    }

    private void tanimlamalar(){

        auth=FirebaseAuth.getInstance();

        mail_kayit=findViewById(R.id.mail_kayit);
        password_kayit=findViewById(R.id.password_kayit);
        isim_kayit=findViewById(R.id.isim_kayit);
        no_kayit=findViewById(R.id.no_kayit);
        btn_kayit=findViewById(R.id.btn_kayit);
        btn_kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mail_kayit.getText().toString();
                String password=password_kayit.getText().toString();
                String isim=isim_kayit.getText().toString();
                String kartNo=no_kayit.getText().toString();
                if (kartNo.length() == 16 && !isim.isEmpty() && !mail.isEmpty() && !password.isEmpty()){
                    KayitOl(mail,password);
                }else {
                    Toast.makeText(getApplicationContext(),"Bigileri kontrol ediniz",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void KayitOl(String mail,String password){

        auth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    String isim=isim_kayit.getText().toString();
                    String mail=mail_kayit.getText().toString();
                    String kartNo=no_kayit.getText().toString();

                    reference= FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(auth.getUid());

                    Map map=new HashMap();
                    map.put("id",auth.getUid());
                    map.put("kartNo",kartNo);
                    map.put("isim",isim);
                    map.put("mail",mail);
                    map.put("para","0");
                    reference.setValue(map);


                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),"Hata",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
