package com.example.bthechef;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Ref;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private EditText editText;
    private Button addIngredientBtn;
    TextView listOfIngredients;
    private String[] allIngredients;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");



         myRef.setValue("Hello, World!");


         myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            String value = dataSnapshot.getValue(String.class);
            Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });





    /*// Read from the database
    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            String value = dataSnapshot.getValue(String.class);
            Log.d(TAG, "Value is: " + value);
        }
        @Override
        public void onCancelled(DatabaseError error) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException());
        }
    });
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

