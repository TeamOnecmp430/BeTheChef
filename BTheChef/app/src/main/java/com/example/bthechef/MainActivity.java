package com.example.bthechef;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Ref;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private EditText editText;
    private Button addIngredientBtn;
    TextView listOfIngredients;
    private String[] allIngredients;
    Button testDbBtn;
    TextView showDb;


    // firebase implementation.
    DatabaseReference myDataRef = FirebaseDatabase.getInstance().getReference();
    //testing db
    DatabaseReference mConditionRef = myDataRef.child("tacos");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //assigning to test database
        showDb = (TextView) findViewById(R.id.viewDb);
        testDbBtn = (Button)findViewById(R.id.buttonTestDb);


        listOfIngredients = findViewById(R.id.listOfIngredsTView);
        editText = findViewById(R.id.ingredEditText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                allIngredients = null;
                String userInput = editText.getText().toString();
                // String currentList = listOfIngredients.getText().toString();
                String delimiter = "[,][, ]";
                allIngredients = userInput.split(delimiter);
                listOfIngredients.setText("Your ingredients:" + getAllIngredients(allIngredients));
            }


        });
        //addIngredientBtn = findViewById(R.id.addIngredBtn);
    }


    //call function for the database.
    @Override
    protected void onStart(){
        super.onStart();
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                showDb.setText(text);
            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }

    public String getAllIngredients(String[] array) {
        String temp = "";
        for (int i = 0; i < array.length; ++i) {
            temp += "\n- " + array[i];
        }
        return temp;
    }

    public void addIngredToList(View view) {
        Log.d(TAG, "inside addIngredToList");
        String userInput = editText.getText().toString();
        String currentList = listOfIngredients.getText().toString();
        listOfIngredients.setText(currentList + "\n- " + userInput);
        Log.d(TAG, "end of addIngredToList");
    }
}

