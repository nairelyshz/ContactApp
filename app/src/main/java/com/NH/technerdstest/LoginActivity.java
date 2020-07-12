package com.NH.technerdstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email ;
    EditText password ;
    Button registerBtn;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        registerBtn = findViewById(R.id.register);
        loginBtn = findViewById(R.id.login);
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, Home.class));
        }
    }

    public void login(View view){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        String emailText = email.getText().toString();
        String passText = password.getText().toString();
        Log.d("email",emailText);
        Log.d("password",passText);

        mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
            .addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.d("login","onSuccess");
                }
            })
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("login","success");
                    if(task.isSuccessful()){
                        startActivity( new Intent(LoginActivity.this, Home.class ));

                    }else{
                        Toast.makeText(LoginActivity.this,"Error! check information", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnCanceledListener(this, new OnCanceledListener() {
                @Override
                public void onCanceled() {
                    Log.d("login","failed");
                }
        });

    }

    public void register(View view){
        Log.d("login","to register");
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


}
