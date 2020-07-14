package com.NH.technerdstest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    Contact contact;
    EditText email ;
    EditText firstName ;
    EditText lastName;
    EditText contactNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        Bundle parameters = getIntent().getExtras();
        contact = new Contact();
        contact.uid = parameters.getString("uid");
        contact.firstName = parameters.getString("firstName");
        contact.lastName = parameters.getString("lastName");
        contact.email = parameters.getString("email");
        contact.contactNumber = parameters.getString("contactNumber");
        setContactInfo();

    }

    public void setContactInfo(){
        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        contactNumber = findViewById(R.id.contactNumber);

        email.setText(contact.email);
        firstName.setText(contact.firstName);
        lastName.setText(contact.lastName);
        contactNumber.setText(contact.contactNumber);
    }

    public void editContact(View view){
        contact.firstName = firstName.getText().toString();
        contact.lastName = lastName.getText().toString();
        contact.email = email.getText().toString();
        contact.contactNumber = contactNumber.getText().toString();
        FirebaseDatabase.getInstance().getReference("contacts").child(contact.uid).setValue(contact);
        Toast.makeText(EditActivity.this, "Contact edited", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditActivity.this, Home.class));
    }
}
