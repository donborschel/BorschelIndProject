package com.example.borschelindproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Submit extends AppCompatActivity implements View.OnClickListener{

    EditText editTextZipCode, editTextBirdName;
    TextView textViewEmail;
     private FirebaseAuth mAuth;

    Button buttonSubmit;
//Note, I am no longer collecting the users Name since we're using the email from the login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        textViewEmail = findViewById(R.id.textViewEmail);
        editTextBirdName = findViewById(R.id.editTextBirdName);
        editTextZipCode = findViewById(R.id.editTextZipCode);

        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(this);
             mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Name, email address, and profile photo Url
        //       String name = user.getDisplayName();
        String email = user.getEmail();
        //       Uri photoUrl = user.getPhotoUrl();

        textViewEmail.setText(email);
    }

    @Override
    public void onClick(View view) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("MyBird");

        String createName = textViewEmail.getText().toString();
        String createBirdName = editTextBirdName.getText().toString();
        String createZipCode = editTextZipCode.getText().toString();

        Birds createBird = new Birds(createBirdName, createName, createZipCode, 0);
        myRef.push().setValue(createBird);

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        editTextZipCode.setText("");
        //   editTextName.setText("");
        editTextBirdName.setText("");
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.ItemSearch) {

            //go to search
            Intent SearchIntent = new Intent(this,Search.class);
            startActivity(SearchIntent);

        } else if (item.getItemId() == R.id.ItemSubmit){
            //go to submit
            Intent submitIntent = new Intent(this,Submit.class);
            startActivity(submitIntent);
        } else {
            Intent LogOutIntent = new Intent(this,MainActivity.class);
            startActivity(LogOutIntent);
            FirebaseAuth.getInstance().signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater Inflater = getMenuInflater();
        Inflater.inflate(R.menu.mainmenu, menu);

        return super.onCreateOptionsMenu(menu);
    }



}
