package com.efeyegitoglu.banka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GirisYapActivity extends AppCompatActivity {

    DatabaseReference reference;
    FirebaseAuth auth;

    EditText mail_edit,pass_edit;
    Button girisYap_btn;
    TextView kaydolText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        tanimlamalar();

    }

    private  void tanimlamalar(){
        reference=FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();

        mail_edit=findViewById(R.id.input_login_mail);
        pass_edit=findViewById(R.id.input_login_password);
        girisYap_btn=findViewById(R.id.login_button);
        girisYap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mail_edit.getText().toString();
                String password=pass_edit.getText().toString();
                if (!mail.isEmpty() && !password.isEmpty()){
                    girisYap(mail,password);
                } else {
                    Toast.makeText(getApplicationContext(), "Bilgileri Kontrol Ediniz", Toast.LENGTH_LONG).show();
                }
            }
        });

        kaydolText=findViewById(R.id.kaydolText);
        kaydolText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),KayitOlActivity.class);
                startActivity(intent);
            }
        });


    }

    private void girisYap(String mail,String password){

        auth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Hata Meydana Geldi", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}
