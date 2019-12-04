package com.example.borschelindproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Search extends AppCompatActivity implements View.OnClickListener {
    EditText editTextZipCodeSearch;
    TextView textViewBirdName, textViewName, textViewImportance;
    Button buttonSearch, buttonImportance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextZipCodeSearch = findViewById(R.id.editTextZipCodeSearch);
        textViewBirdName = findViewById(R.id.textViewBirdName);
        textViewName = findViewById(R.id.textViewName);
        textViewImportance = findViewById(R.id.textViewImportance);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(this);

        buttonImportance = findViewById(R.id.buttonImportance);
        buttonImportance.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == buttonSearch) {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("MyBird");

            String findZip = editTextZipCodeSearch.getText().toString();

            myRef.orderByChild("ZipCode").equalTo(findZip).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Birds foundBird = dataSnapshot.getValue(Birds.class);
                    String findEmail = foundBird.Email;
                    String findBirdName = foundBird.BirdName;
                    Integer findImportance = foundBird.Score;

                    String Score = findImportance.toString();

                    textViewName.setText(findEmail);
                    textViewBirdName.setText(findBirdName);
                    textViewImportance.setText(Score);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("MyBird");

            String findZip = editTextZipCodeSearch.getText().toString();

            myRef.orderByChild("ZipCode").equalTo(findZip).limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Birds foundBird = dataSnapshot.getValue(Birds.class);
                    String findEmail = foundBird.Email;
                    String findBirdName = foundBird.BirdName;
                    Integer findImportance = foundBird.Score;

                    findImportance = findImportance +1;

                    //push to firebase
                    //           myRef().push().setValue(Score+1);
                    String Score = findImportance.toString();

                    textViewName.setText(findEmail);
                    textViewBirdName.setText(findBirdName);
                    textViewImportance.setText(Score);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
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
