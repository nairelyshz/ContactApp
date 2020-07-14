package com.NH.technerdstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    DatabaseReference mDatabase;
    EditText email ;
    EditText firstName ;
    EditText lastName;
    EditText contactNumber;
    ArrayList<Contact> contacts = new ArrayList<Contact>();
    RecyclerView contactsView;
    Map<String, User> mapContact = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDatabase = FirebaseDatabase.getInstance().getReference("contacts");
        contactsView = findViewById(R.id.contacts_list);
        getContactList();
    }

    public void getContactList(){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Boolean isInList = false;
                        for(Contact contact : contacts){
                            if(contact.uid == snapshot.getKey()){
                                isInList=true;
                                break;
                            }
                        }
                        if(!isInList){
                            Contact contact = snapshot.getValue(Contact.class);
                            contact.uid = snapshot.getKey();
                            contacts.add(contact);
                        }
                    }
                }
                AdapterList.ListListener listener = new AdapterList.ListListener() {
                    @Override
                    public void editItem(Contact contact, int position) {
                        Intent intent = new Intent(Home.this, EditActivity.class);
                        intent.putExtra("firstName", contact.firstName);
                        intent.putExtra("lastName", contact.lastName);
                        intent.putExtra("email", contact.email);
                        intent.putExtra("contactNumber", contact.contactNumber);
                        intent.putExtra("uid", contact.uid);
                        startActivity(intent);
                    }
                };

                AdapterList adap = new AdapterList(contacts, listener);
                contactsView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                contactsView.setAdapter(adap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Home.this, LoginActivity.class));
    }

    public void addContact(View view){
        email = findViewById(R.id.email);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        contactNumber = findViewById(R.id.contactNumber);

        User user = new User(firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                null,
                contactNumber.getText().toString());
        String key = mDatabase.child("contacts").push().getKey();
        mDatabase.child(key).setValue(user);

    }




}
